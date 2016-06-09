public class Executor extends Converter {
	
	public char side = 'l';
	public String teamName="";
	public Executor(char _side, String teamname) {		
		super();
		side = _side;
		teamName = teamname;
	}

	public boolean run(Integer id, SendCommand sendCommand, VisualInfo info,
			Memory memory) {

		boolean result = false;
		int idValue = id.intValue();

		if (idValue == CODE_PASS_ACTION) {
			result = pass(sendCommand, info, memory);
		} else if (idValue == CODE_KICK_ACTION) {
			result = kick(sendCommand, info, memory);
		} else if (idValue == CODE_LOCATE_BALL_ACTION) {
			result = locate_ball(sendCommand, info, memory);
		} else if (idValue == CODE_INTERCEPT_BALL_ACTION) {
			result = intercept_ball(sendCommand, info, memory);
		} else if (idValue == CODE_LOCATE_GOAL_ACTION) {
			result = locate_goal(sendCommand, info, memory);
		} else if (idValue == CODE_SCORE_GOAL_ACTION) {
			result = score_goal(sendCommand, info, memory);
		} else if (idValue == CODE_BALL_IN_POSSESSION) {
			result = ball_in_possession(sendCommand, info, memory);
		} else if (idValue == CODE_IS_BALL_VISIBLE) {
			result = is_ball_visible(sendCommand, info, memory);
		} else if (idValue == CODE_IS_BEING_BLOCKED) {
			result = is_being_blocked(sendCommand, info, memory);
		} else if (idValue == CODE_CAN_PASS) {
			result = can_pass(sendCommand, info, memory);
		} else if (idValue == CODE_IS_GOAL_SCORED) {
			result = is_goal_scored(sendCommand, info, memory);
		} else {
			System.out.println("ERROR: Unrecognized action id - " + idValue);
		}

		return result;
	}

	// Pass doesn't make sense, all agent are moving towards the same direction 
	// ASSUMES Ball is in Possession
	// ASSUMES 
	private boolean pass(SendCommand sendCommand, VisualInfo info, Memory memory) {
		// TODO Auto-generated method stub
		int closeDistance=10; int MAX_SHOT=100;
		for(int c = 0 ; c < info.m_objects.size() ; c ++)
	    {
		ObjectInfo object = (ObjectInfo)info.m_objects.elementAt(c);
		if(object.m_type.compareTo("player") == 0)
		{
		  PlayerInfo player = (PlayerInfo) object;
		  if(player.m_teamName == teamName)
		  {
			  if(player.m_distance <= closeDistance )
			  {
				  
				  sendCommand.kick((MAX_SHOT-(MAX_SHOT/player.m_distance)), player.m_direction);
			  }
		  }
	    }
	    }
		return false;
	}

	//Assumes Player is with the ball
	private boolean kick(SendCommand sendCommand, VisualInfo info, Memory memory) {
		// TODO Auto-generated method stub
		return false;
	}

	// ASSUMES We don't have the Ball
	// ASSUMES Ball is Visible, although it checks.
	// It only locates the Ball. It just Finds ball, it doesn't go for the ball.
	// To Go for the Ball, Call intercept_ball
	private boolean locate_ball(SendCommand sendCommand, VisualInfo info,
			Memory memory) {
		// TODO Auto-generated method stub
		ObjectInfo ball = memory.getObject("ball");
		if(ball == null) return false;
		
		if(is_ball_visible(sendCommand, info, memory))
		{
			if (ball.m_direction != 0)
				sendCommand.turn(ball.m_direction);
			else sendCommand.turn(40);
		}
		else {}
		return false;
	}

	// ASSUMES We don't have the ball
	// ASSUMES We have Located  Ball
	// Goes to the Ball.
	private boolean intercept_ball(SendCommand sendCommand, VisualInfo info,
			Memory memory) {
		// TODO Auto-generated method stub
		ObjectInfo ball = memory.getObject("ball");
		if(ball == null) return false;
		sendCommand.dash(10 * ball.m_distance);
		return false;
	}

	// ASSUMES we have side and located goal
	private boolean locate_goal(SendCommand sendCommand, VisualInfo info,
			Memory memory) {
		// TODO Auto-generated method stub
		ObjectInfo goal;
		if (side == 'l')
			goal = memory.getObject("goal r");
		else
			goal = memory.getObject("goal l");

		if (goal == null) {
			sendCommand.turn(40);
		}
		return false;
	}

	// ASSUMES we located goal
	private boolean score_goal(SendCommand sendCommand, VisualInfo info,
			Memory memory) {
		// TODO Auto-generated method stub
		ObjectInfo goal;
		if (side == 'l')
			goal = memory.getObject("goal r");
		else
			goal = memory.getObject("goal l");

		if (goal == null) return false;
		sendCommand.kick(100, goal.m_direction);
		return false;
	}

	// ASSUMES Ball is visible
	// ASSUMES
	private boolean ball_in_possession(SendCommand sendCommand,
			VisualInfo info, Memory memory) {
		// TODO Auto-generated method stub
		ObjectInfo ball = memory.getObject("ball");
		if(ball == null) return false;
		if(is_ball_visible(sendCommand, info, memory))
		{
			if(ball.m_distance <= 1.0) return true;
		}
		return false;
	}
	
	// Checks if goal is located
	private boolean is_goal_located(SendCommand sendCommand, VisualInfo info,
				Memory memory) {
			// TODO Auto-generated method stub
			ObjectInfo goal;
			if (side == 'l')
				goal = memory.getObject("goal r");
			else
				goal = memory.getObject("goal l");

			if (goal != null) return true;
			return false;
		}
			
	//Checks if we can see ball. Either Ball is Far or Near or with us.
	// It only cares if we can see the ball
	private boolean is_ball_visible(SendCommand sendCommand, VisualInfo info,
			Memory memory) {
		// TODO Auto-generated method stub
		ObjectInfo ball = memory.getObject("ball");
		if (ball!=null){
		if (ball.m_direction != 0) return true;
		}
		return false;
	}

	private boolean is_being_blocked(SendCommand sendCommand, VisualInfo info,
			Memory memory) {
		// TODO Auto-generated method stub
		return false;
	}

	// ASSUMES our player has the Ball
	// ASSUMES all condition of ball
	private boolean can_pass(SendCommand sendCommand, VisualInfo info,
			Memory memory) {
		// TODO Auto-generated method stub
		return false;
	}

	// ASSUMES we Can see Ball and Can Locate Goal
	private boolean is_goal_scored(SendCommand sendCommand, VisualInfo info,
			Memory memory) {
		// TODO Auto-generated method stub
		ObjectInfo goal;
		if (side == 'l')
			goal = memory.getObject("goal r");
		else
			goal = memory.getObject("goal l");

		if (goal == null) return false;
		
		ObjectInfo ball = memory.getObject("ball");
		if(ball == null) return false;
		
		if(goal.m_distance == ball.m_distance) return true;
		return false;
	}
}
