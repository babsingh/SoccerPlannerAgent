public class Executor extends Converter {
	public Executor() {
		super();
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

	private boolean pass(SendCommand sendCommand, VisualInfo info, Memory memory) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean kick(SendCommand sendCommand, VisualInfo info, Memory memory) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean locate_ball(SendCommand sendCommand, VisualInfo info,
			Memory memory) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean intercept_ball(SendCommand sendCommand, VisualInfo info,
			Memory memory) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean locate_goal(SendCommand sendCommand, VisualInfo info,
			Memory memory) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean score_goal(SendCommand sendCommand, VisualInfo info,
			Memory memory) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean ball_in_possession(SendCommand sendCommand,
			VisualInfo info, Memory memory) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean is_ball_visible(SendCommand sendCommand, VisualInfo info,
			Memory memory) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean is_being_blocked(SendCommand sendCommand, VisualInfo info,
			Memory memory) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean can_pass(SendCommand sendCommand, VisualInfo info,
			Memory memory) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean is_goal_scored(SendCommand sendCommand, VisualInfo info,
			Memory memory) {
		// TODO Auto-generated method stub
		return false;
	}
}
