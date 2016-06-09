import java.util.HashMap;
import java.util.Iterator;

/* 
 * In this class, agent actions and environment properties are listed.
 * They are represented in String format. Each String format has a 
 * corresponding int format. The String format is used to parse 
 * agent actions that are described in AgentActions.txt. Since 
 * String manipulation is expensive, the agent uses the corresponding
 * int format for internally processing and evaluating actions.   
 */
public class Converter {
	/* Actions defined in String */
	public final String PASS_ACTION = "pass"; 
	public final String LOCATE_BALL_ACTION = "locate_ball"; 
	public final String INTERCEPT_BALL_ACTION = "intercept_ball";
	public final String LOCATE_GOAL_ACTION = "locate_goal"; 
	public final String SCORE_GOAL_ACTION = "score_goal";
	
	/* Environment properties defined in String */
	public final String BALL_IN_POSSESSION = "ball_in_possession";
	public final String IS_BALL_VISIBLE = "is_ball_visible";
	public final String IS_BEING_BLOCKED = "is_being_blocked";
	public final String IS_BALL_INSIDE_GOAL = "ball_inside_goal";
	public final String IS_GOAL_VISIBLE = "is_goal_visible";
	
	/* Attempted using hashCode to generate a unique int for each String */
//	public final int CODE_PASS_ACTION = PASS_ACTION.hashCode(); 
//	public final int CODE_LOCATE_BALL_ACTION = LOCATE_BALL_ACTION.hashCode(); 
//	public final int CODE_INTERCEPT_BALL_ACTION = INTERCEPT_BALL_ACTION.hashCode();
//	public final int CODE_LOCATE_GOAL_ACTION = LOCATE_GOAL_ACTION.hashCode(); 
//	public final int CODE_SCORE_GOAL_ACTION = SCORE_GOAL_ACTION.hashCode();
//	
//	public final int CODE_BALL_IN_POSSESSION = BALL_IN_POSSESSION.hashCode();
//	public final int CODE_IS_BALL_VISIBLE = IS_BALL_VISIBLE.hashCode();
//	public final int CODE_IS_BEING_BLOCKED = IS_BEING_BLOCKED.hashCode();
//	public final int CODE_IS_BALL_INSIDE_GOAL = IS_BALL_INSIDE_GOAL.hashCode();
//	public final int CODE_IS_GOAL_VISIBLE = IS_GOAL_VISIBLE.hashCode();
	
	/* Agent actions defined in int */
	public final int CODE_PASS_ACTION = 1; 
	public final int CODE_LOCATE_BALL_ACTION = 2; 
	public final int CODE_INTERCEPT_BALL_ACTION = 3;
	public final int CODE_LOCATE_GOAL_ACTION = 4; 
	public final int CODE_SCORE_GOAL_ACTION = 5;
	
	/* Environment properties defined in int */
	public final int CODE_BALL_IN_POSSESSION = 6;
	public final int CODE_IS_BALL_VISIBLE = 7;
	public final int CODE_IS_BEING_BLOCKED = 8;
	public final int CODE_IS_BALL_INSIDE_GOAL = 9;
	public final int CODE_IS_GOAL_VISIBLE = 10;
	
	/* Stores mapping between string format of agent actions with the corresponding int format */
	public HashMap<String, Integer> actionMapping;
	
	/* Stores mapping between string format of environment properties with the corresponding int format */
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
	
	/* Initialize HashMaps: actionMapping and generateMapping */
	public void generateMapping() {
		this.actionMapping.put(PASS_ACTION, CODE_PASS_ACTION);
		this.actionMapping.put(LOCATE_BALL_ACTION, CODE_LOCATE_BALL_ACTION);
		this.actionMapping.put(INTERCEPT_BALL_ACTION, CODE_INTERCEPT_BALL_ACTION);
		this.actionMapping.put(LOCATE_GOAL_ACTION, CODE_LOCATE_GOAL_ACTION);
		this.actionMapping.put(SCORE_GOAL_ACTION, CODE_SCORE_GOAL_ACTION);
		
		this.sensoryMapping.put(BALL_IN_POSSESSION, CODE_BALL_IN_POSSESSION);
		this.sensoryMapping.put(IS_BALL_VISIBLE, CODE_IS_BALL_VISIBLE);
		this.sensoryMapping.put(IS_BEING_BLOCKED, CODE_IS_BEING_BLOCKED);
		this.sensoryMapping.put(IS_BALL_INSIDE_GOAL, CODE_IS_BALL_INSIDE_GOAL);
		this.sensoryMapping.put(IS_GOAL_VISIBLE, CODE_IS_GOAL_VISIBLE);
	}
	
	/* Used to make sure that int IDs are unique when using the hashCode() approach */
	public void checkForSimilarMappings() {
		Iterator<String> actionAterator = actionMapping.keySet().iterator();
		Iterator<String> sensoryIterator = sensoryMapping.keySet().iterator();
		Integer oldValue = null;
		
		while (actionAterator.hasNext()) {
			String key = actionAterator.next().toString();
			Integer value = actionMapping.get(key);
			  
			Debug.print("Mapping:  " + key + " -> (" + value + ")");
			
			if ((null != value) && (null != oldValue) && (value.compareTo(oldValue) == 0) && (value.compareTo(-1 * oldValue.intValue()) == 0)) {
				System.out.print("ERROR: Two similar int IDs found");
				System.exit(1);
			}
			
			oldValue = value;
		}
		
		while (sensoryIterator.hasNext()) {
			String key = sensoryIterator.next().toString();
			Integer value = sensoryMapping.get(key);
			  
			Debug.print("Mapping:  " + key + " -> (" + value + ")");
			
			if ((null != value) && (null != oldValue) && (value.compareTo(oldValue) == 0) && (value.compareTo(-1 * oldValue.intValue()) == 0)) {
				System.out.print("ERROR: Two similar int IDs found");
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
