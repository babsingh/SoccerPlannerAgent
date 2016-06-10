//
//	File:			Brain.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//
//    Modified by:	Paul Marlow

//    Modified by:      Edgar Acosta
//    Date:             March 4, 2008

import java.lang.Math;
import java.util.ArrayList;
import java.util.regex.*;

class Brain extends Thread implements SensorInput {
	// ---------------------------------------------------------------------------
	// This constructor:
	// - stores connection to krislet
	// - starts thread for this object
	public Brain(SendCommand krislet, String team, char side, int number,
			String playMode, Executor executor, ArrayList<AgentAction> agentActions, boolean useModifiedKrislet) {
		this.m_timeOver = false;
		this.sendCommand = krislet;
		this.m_memory = new Memory();
		// m_team = team;
		this.m_side = side;
		// m_number = number;
		this.m_playMode = playMode;
		this.executor = executor;
		this.agentActions = agentActions;
		this.useModifiedKrislet = useModifiedKrislet;
		this.env = new Environment(executor, this.m_memory);
		this.nextActions = new ArrayList<Integer>();
		start();
	}

	// ---------------------------------------------------------------------------
	// This is main brain function used to make decision
	// In each cycle we decide which command to issue based on
	// current situation. the rules are:
	//
	// 1. If you don't know where is ball then turn right and wait for new info
	//
	// 2. If ball is too far to kick it then
	// 2.1. If we are directed towards the ball then go to the ball
	// 2.2. else turn to the ball
	//
	// 3. If we dont know where is opponent goal then turn wait
	// and wait for new info
	//
	// 4. Kick ball
	//
	// To ensure that we don't send commands to often after each cycle
	// we waits one simulator steps. (This of course should be done better)

	// *************** Improvements ******************
	// Allways know where the goal is.
	// Move to a place on my side on a kick_off
	// ************************************************

	/* 
	 * This method decides whether to run Krislet's original or modified implementation 
	 * based upon the value assigned to variable useModifiedKrislet.
	 */
	public void run() {
		if (useModifiedKrislet) {
			modifiedKrislet();
		} else {
			originalKrislet();
		}
	}
	
	/* Adds properties to the environment */
	private void deletePropertiesFromEnvironment(ArrayList<Integer> deletions) {
		if (null != deletions) {
			for (Integer addProperty : deletions) {
				env.addSensoryInfo(addProperty);
			}
		}		
	}

	/* Deletes properties from the environment */
	private void addPropertiesToEnvironment(ArrayList<Integer> additions) {
		if (null != additions) {
			for (Integer addProperty : additions) {
				env.addSensoryInfo(addProperty);
			}
		}
	}

	/* 
	 * This method derives a set of actions to reach the specified list of goals.
	 * The goals/properties to be achieved are described in the ArrayList goals.
	 */
	public boolean developIntermediatePlans(ArrayList<Integer> goals) {
		boolean result = true;
		
		for (Integer goal : goals) {
			boolean propertyValid = env.getSensoryInfo(goal, alwaysEvaluate);
			Debug.print("Property validation - " + goal + " value - " + propertyValid);
			if (!propertyValid) {
				boolean actionExists = false;
				for (AgentAction agentAction : agentActions) {
					if (agentAction.checkAdditions(goal)) {
						if (developIntermediatePlans(agentAction.getPreconditions())) {
							nextActions.add(agentAction.getID());
							addPropertiesToEnvironment(agentAction.getAdditions());
							deletePropertiesFromEnvironment(agentAction.getDeletions());
							actionExists = true;
							Debug.print("Action chosen - " + agentAction.getID());
							break;
						}
					}
				}
				if (!actionExists) {
					result = false;
					Debug.print("No action found");
					break;
				}
			}
		}
		
		return result;
	}
	
	/* This method derives a set of actions to reach one final/ultimate goal. */
	public boolean developCompletePlan(Integer finalGoal) {
		ArrayList<Integer> initGoals = new ArrayList<Integer>();
		initGoals.add(finalGoal);
		return developIntermediatePlans(initGoals);
	}

	/* Executes the actions provided in the ArrayList */
	public void performActions() {
		for (Integer actionID : nextActions) {
			Debug.print("PERFORMING ACTION - " + actionID);
			executor.run(actionID, m_memory);
		}
	}

	/* 
	 * This method represents the modified Krislet implementation.
	 * This implementation uses a planner. The planner derives a
	 * set of actions to reach the specified goal. Once the plan or
	 * set of actions are derived, then this method executes those actions.   
	 */
	public void modifiedKrislet() {
		// first put it somewhere on my side
		if (Pattern.matches("^before_kick_off.*", m_playMode))
			sendCommand.move(-Math.random() * 52.5, 34 - Math.random() * 68.0);
		
		while (!m_timeOver) {
			/* Choose the final goal, plan and execute actions */
			boolean planDeveloped = developCompletePlan(executor.CODE_IS_BALL_INSIDE_GOAL);
			if (planDeveloped) {
				Debug.print("PLAN DEVELOPED: #actions = " + nextActions.size());
				performActions();
				alwaysEvaluate = false;
			} else {
				Debug.print("no plan developed");
				alwaysEvaluate = true;
			}
			nextActions.clear();
		}
		sendCommand.bye();
	}

	/*
	 * This method contains code for Krislet's original implementation.
	 */
	public void originalKrislet() {
		ObjectInfo object;

		// first put it somewhere on my side
		if (Pattern.matches("^before_kick_off.*", m_playMode))
			sendCommand.move(-Math.random() * 52.5, 34 - Math.random() * 68.0);

		while (!m_timeOver) {
			object = m_memory.getObject("ball");
			if (object == null) {
				// If you don't know where is ball then find it
				sendCommand.turn(40);
				m_memory.waitForNewInfo();
			} else if (object.m_distance > 1.0) {
				// If ball is too far then
				// turn to ball or
				// if we have correct direction then go to ball
				if (object.m_direction != 0)
					sendCommand.turn(object.m_direction);
				else
					sendCommand.dash(10 * object.m_distance);
			} else {
				// We know where is ball and we can kick it
				// so look for goal
				if (m_side == 'l')
					object = m_memory.getObject("goal r");
				else
					object = m_memory.getObject("goal l");

				if (object == null) {
					sendCommand.turn(40);
					m_memory.waitForNewInfo();
				} else
					sendCommand.kick(100, object.m_direction);
			}

			// sleep one step to ensure that we will not send
			// two commands in one cycle.
			try {
				Thread.sleep(2 * SoccerParams.simulator_step);
			} catch (Exception e) {
			}
		}
		sendCommand.bye();
	}

	// ===========================================================================
	// Here are suporting functions for implement logic

	// ===========================================================================
	// Implementation of SensorInput Interface

	// ---------------------------------------------------------------------------
	// This function sends see information
	public void see(VisualInfo info) {
		m_memory.store(info);
		if (useModifiedKrislet) {
			env.invalidateSensoryInformation();
		}
	}

	// ---------------------------------------------------------------------------
	// This function receives hear information from player
	public void hear(int time, int direction, String message) {
	}

	// ---------------------------------------------------------------------------
	// This function receives hear information from referee
	public void hear(int time, String message) {
		if (message.compareTo("time_over") == 0)
			m_timeOver = true;

	}

	// ===========================================================================
	// Private members
	private SendCommand sendCommand; // robot which is controled by this brain
	private Memory m_memory; // place where all information is stored
	private char m_side;
	volatile private boolean m_timeOver;
	private String m_playMode;
	private boolean useModifiedKrislet;
	private boolean alwaysEvaluate = false;
	public Executor executor;
	public ArrayList<AgentAction> agentActions;
	public Environment env;
	public ArrayList<Integer> nextActions;
}
