import java.util.HashMap;
import java.util.Iterator;


public class Converter {
	private final String PASS_ACTION = "pass"; 
	private final String KICK_ACTION = "kick";
	private final String LOCATE_BALL_ACTION = "locate_ball"; 
	private final String INTERCEPT_BALL_ACTION = "intercept_ball";
	private final String LOCATE_GOAL_ACTION = "locate_goal"; 
	private final String SCORE_GOAL_ACTION = "score_goal";
	
	private final String BALL_IN_POSSESSION = "ball_in_possession";
	private final String IS_BALL_VISIBLE = "is_ball_visible";
	private final String IS_BEING_BLOCKED = "is_being_blocked";
	private final String CAN_PASS = "can_pass";
	private final String IS_BALL_INSIDE_GOAL = "ball_inside_goal";
	private final String IS_GOAL_VISIBLE = "is_goal_visible";
	
//	public final int CODE_PASS_ACTION = PASS_ACTION.hashCode(); 
//	public final int CODE_KICK_ACTION = KICK_ACTION.hashCode();
//	public final int CODE_LOCATE_BALL_ACTION = LOCATE_BALL_ACTION.hashCode(); 
//	public final int CODE_INTERCEPT_BALL_ACTION = INTERCEPT_BALL_ACTION.hashCode();
//	public final int CODE_LOCATE_GOAL_ACTION = LOCATE_GOAL_ACTION.hashCode(); 
//	public final int CODE_SCORE_GOAL_ACTION = SCORE_GOAL_ACTION.hashCode();
//	
//	public final int CODE_BALL_IN_POSSESSION = BALL_IN_POSSESSION.hashCode();
//	public final int CODE_IS_BALL_VISIBLE = IS_BALL_VISIBLE.hashCode();
//	public final int CODE_IS_BEING_BLOCKED = IS_BEING_BLOCKED.hashCode();
//	public final int CODE_CAN_PASS = CAN_PASS.hashCode();
//	public final int CODE_IS_GOAL_SCORED = IS_GOAL_SCORED.hashCode();
	
	public final int CODE_PASS_ACTION = 1; 
	public final int CODE_KICK_ACTION = 2;
	public final int CODE_LOCATE_BALL_ACTION = 3; 
	public final int CODE_INTERCEPT_BALL_ACTION = 4;
	public final int CODE_LOCATE_GOAL_ACTION = 8; 
	public final int CODE_SCORE_GOAL_ACTION = 9;
	
	public final int CODE_BALL_IN_POSSESSION = 10;
	public final int CODE_IS_BALL_VISIBLE = 11;
	public final int CODE_IS_BEING_BLOCKED = 12;
	public final int CODE_CAN_PASS = 13;
	public final int CODE_IS_BALL_INSIDE_GOAL = 14;
	public final int CODE_IS_GOAL_VISIBLE = 15;
	
	public HashMap<String, Integer> actionMapping;
	public HashMap<String, Integer> sensoryMapping;

	public HashMap<String, Integer> getActionMapping() {
		return actionMapping;
	}
	
	public HashMap<String, Integer> getSensoryMapping() {
		return sensoryMapping;
	}


	public Converter() {
		this.actionMapping = new HashMap<String, Integer>();
		this.sensoryMapping = new HashMap<String, Integer>();
		generateMapping();
		checkForSimilarMappings();
	}
	
	private void generateMapping() {
		this.actionMapping.put(PASS_ACTION, CODE_PASS_ACTION);
		this.actionMapping.put(KICK_ACTION, CODE_KICK_ACTION);
		this.actionMapping.put(LOCATE_BALL_ACTION, CODE_LOCATE_BALL_ACTION);
		this.actionMapping.put(INTERCEPT_BALL_ACTION, CODE_INTERCEPT_BALL_ACTION);
		this.actionMapping.put(LOCATE_GOAL_ACTION, CODE_LOCATE_GOAL_ACTION);
		this.actionMapping.put(SCORE_GOAL_ACTION, CODE_SCORE_GOAL_ACTION);
		
		this.sensoryMapping.put(BALL_IN_POSSESSION, CODE_BALL_IN_POSSESSION);
		this.sensoryMapping.put(IS_BALL_VISIBLE, CODE_IS_BALL_VISIBLE);
		this.sensoryMapping.put(IS_BEING_BLOCKED, CODE_IS_BEING_BLOCKED);
		this.sensoryMapping.put(CAN_PASS, CODE_CAN_PASS);
		this.sensoryMapping.put(IS_BALL_INSIDE_GOAL, CODE_IS_BALL_INSIDE_GOAL);
		this.sensoryMapping.put(IS_GOAL_VISIBLE, CODE_IS_GOAL_VISIBLE);
	}
	
	private void checkForSimilarMappings() {
		Iterator<String> actionAterator = actionMapping.keySet().iterator();
		Iterator<String> sensoryIterator = sensoryMapping.keySet().iterator();
		Integer oldValue = null;
		
		while (actionAterator.hasNext()) {
			String key = actionAterator.next().toString();
			Integer value = actionMapping.get(key);
			  
			Debug.print("Mapping:  " + key + " -> (" + value + ")");
			
			if ((null != value) && (null != oldValue) && (value.compareTo(oldValue) == 0) && (value.compareTo(-1 * oldValue.intValue()) == 0)) {
				System.out.print("ERROR: Two similar mappings found");
				System.exit(1);
			}
			
			oldValue = value;
		}
		
		while (sensoryIterator.hasNext()) {
			String key = sensoryIterator.next().toString();
			Integer value = sensoryMapping.get(key);
			  
			Debug.print("Mapping:  " + key + " -> (" + value + ")");
			
			if ((null != value) && (null != oldValue) && (value.compareTo(oldValue) == 0) && (value.compareTo(-1 * oldValue.intValue()) == 0)) {
				System.out.print("ERROR: Two similar mappings found");
				System.exit(1);
			}
			
			oldValue = value;
		}
	}

/* Local Testing */
//	public static void main(String[] args) {
//		Converter converter = new Converter();
//	}
}
