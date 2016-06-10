import java.util.Vector;

/*
 * The purpose of this class is to evaluate environment properties 
 * and execute agent actions. 
 */
public class Executor extends Converter {
	public SendCommand sendCommand;
	public char side = 'l';
	public String teamName = "";

	public Executor(SendCommand sendCommand, String teamName, char side) {
		super();
		this.sendCommand = sendCommand;
		this.teamName = teamName;
		this.side = side;
	}

	/*
	 * Based upon the id passed to this method, the corresponding agent action
	 * or environment property is executed or evaluated respectively.
	 */
	public boolean run(Integer id, Memory memory) {

		boolean result = false;
		int idValue = id.intValue();

		if (idValue == CODE_PASS_ACTION) {
			result = pass(sendCommand, memory);
			Debug.print("Executing Action: " + PASS_ACTION + " Output: " + result);
		} else if (idValue == CODE_LOCATE_BALL_ACTION) {
			result = locate_ball(sendCommand, memory);
			Debug.print("Executing Action: " + LOCATE_BALL_ACTION + " Output: " + result);
		} else if (idValue == CODE_INTERCEPT_BALL_ACTION) {
			result = intercept_ball(sendCommand, memory);
			Debug.print("Executing Action: " + INTERCEPT_BALL_ACTION + " Output: " + result);
		} else if (idValue == CODE_LOCATE_GOAL_ACTION) {
			result = locate_goal(sendCommand, memory);
			Debug.print("Executing Action: " + LOCATE_GOAL_ACTION + " Output: " + result);
		} else if (idValue == CODE_SCORE_GOAL_ACTION) {
			result = score_goal(sendCommand, memory);
			Debug.print("Executing Action: " + SCORE_GOAL_ACTION + " Output: " + result);
		} else if (idValue == CODE_BALL_IN_POSSESSION) {
			result = ball_in_possession(sendCommand, memory);
			Debug.print("Executing Property: " + BALL_IN_POSSESSION + " Output: " + result);
		} else if (idValue == CODE_IS_BALL_VISIBLE) {
			result = is_ball_visible(sendCommand, memory);
			Debug.print("Executing Property: " + IS_BALL_VISIBLE + " Output: " + result);
		} else if (idValue == CODE_IS_BEING_BLOCKED) {
			result = is_being_blocked(sendCommand, memory);
			Debug.print("Executing Property: " + IS_BEING_BLOCKED + " Output: " + result);
		} else if (idValue == CODE_IS_BALL_INSIDE_GOAL) {
			result = is_ball_inside_goal(sendCommand, memory);
			Debug.print("Executing Property: " + IS_BALL_INSIDE_GOAL + " Output: " + result);
		} else if (idValue == CODE_IS_GOAL_VISIBLE) {
			result = is_goal_visible(sendCommand, memory);
			Debug.print("Executing Property: " + IS_GOAL_VISIBLE + " Output: " + result);
		} else {
			System.out.println("ERROR: Unrecognized action id - " + idValue);
		}

		return result;
	}

	private void sleepOneCycle() {
		// sleep one step to ensure that we will not send
		// two commands in one cycle.
		try {
			Thread.sleep(2 * SoccerParams.simulator_step);
		} catch (Exception e) {
		}
	}

	// Pass doesn't make sense, all agent are moving towards the same direction
	// ASSUMES Ball is in Possession
	// ASSUMES
	private boolean pass(SendCommand sendCommand, Memory memory) {
		int closeDistance = 10;
		int MAX_SHOT = 100;
		Vector<PlayerInfo> players = memory.getPlayers();
		if (null != players) {
			for (PlayerInfo player : players) {
				if (teamName.equals(player.getTeamName())) {
					if (player.m_distance <= closeDistance) {
						sendCommand.kick((MAX_SHOT - (MAX_SHOT / player.getDistance())), player.getDirection());
						sleepOneCycle();
						break;
					}
				}
			}
			return true;
		} else {
			Debug.print("No players found to pass");
		}
		return false;
	}

	// ASSUMES We don't have the Ball
	// ASSUMES Ball is Visible, although it checks.
	// It only locates the Ball. It just Finds ball, it doesn't go for the ball.
	// To Go for the Ball, Call intercept_ball
	private boolean locate_ball(SendCommand sendCommand, Memory memory) {
		BallInfo ball = null;

		while (null == ball) {
			ball = (BallInfo) memory.getObject("ball");
			if (null == ball) {
				sendCommand.turn(40);
				memory.waitForNewInfo();
			}
		}

		return false;
	}

	// ASSUMES We don't have the ball
	// ASSUMES We have Located Ball
	// Goes to the Ball.
	private boolean intercept_ball(SendCommand sendCommand, Memory memory) {
		BallInfo ball = (BallInfo) memory.getObject("ball");

		if (null == ball) {
			return false;
		}

		float distance = ball.getDistance();
		float direction = ball.getDirection();

		if (distance > 1.0) {
			if (0 != direction) {
				sendCommand.turn(direction);
				sleepOneCycle();
			}
			sendCommand.dash(10 * ball.getDistance());
			sleepOneCycle();
		}

		return false;
	}

	// ASSUMES we have side and located goal
	private boolean locate_goal(SendCommand sendCommand, Memory memory) {
		GoalInfo goal = null;

		while (null == goal) {
			if (side == 'l') {
				goal = (GoalInfo) memory.getObject("goal r");
			} else {
				goal = (GoalInfo) memory.getObject("goal l");
			}

			if (null == goal) {
				sendCommand.turn(40);
				memory.waitForNewInfo();
			}
		}

		return true;
	}

	// ASSUMES we located goal
	private boolean score_goal(SendCommand sendCommand, Memory memory) {
		GoalInfo goal = null;

		if (side == 'l') {
			goal = (GoalInfo) memory.getObject("goal r");
		} else {
			goal = (GoalInfo) memory.getObject("goal l");
		}

		if (null == goal) {
			return false;
		}

		sendCommand.kick(100, goal.m_direction);
		sleepOneCycle();
		return true;
	}

	// ASSUMES Ball is visible
	// ASSUMES
	private boolean ball_in_possession(SendCommand sendCommand, Memory memory) {
		BallInfo ball = (BallInfo) memory.getObject("ball");

		if (null == ball) {
			return false;
		}

		if (ball.getDistance() < 1.0) {
			return true;
		}

		return false;
	}

	// Checks if goal is located
	private boolean is_goal_visible(SendCommand sendCommand, Memory memory) {
		GoalInfo goal = null;

		if (side == 'l') {
			goal = (GoalInfo) memory.getObject("goal r");
		} else {
			goal = (GoalInfo) memory.getObject("goal l");
		}

		if (null != goal) {
			return true;
		}

		return false;
	}

	// Checks if we can see ball. Either Ball is Far or Near or with us.
	// It only cares if we can see the ball
	private boolean is_ball_visible(SendCommand sendCommand, Memory memory) {
		BallInfo ball = (BallInfo) memory.getObject("ball");

		if (null != ball) {
			return true;
		}

		return false;
	}

	private boolean is_being_blocked(SendCommand sendCommand, Memory memory) {
		// TODO Auto-generated method stub
		return false;
	}

	// ASSUMES we Can see Ball and Can Locate Goal
	private boolean is_ball_inside_goal(SendCommand sendCommand, Memory memory) {
		GoalInfo goal = null;

		if (side == 'l') {
			goal = (GoalInfo) memory.getObject("goal r");
		} else {
			goal = (GoalInfo) memory.getObject("goal l");
		}

		if (null == goal) {
			return false;
		}

		BallInfo ball = (BallInfo) memory.getObject("ball");

		if (null == ball) {
			return false;
		}

		if ((goal.getDistance() == ball.getDistance()) && (goal.getDirection() == ball.getDirection())) {
			return true;
		}

		return false;
	}
}
