import java.util.ArrayList;

/*
 * The purpose of this class is to parse an agent action from
 * AgentActions.txt, and store the extracted information about the 
 * agent action. The agent action has properties similar to STRIPS
 * representation: id (name), preconditions, additions and deletions.  
 */

public class AgentAction {
	/* Returns the unique id corresponding to the agent. */
	public Integer getID() {
		return id;
	}
	
	/* 
	 * Returns the properties which are to be added to the environment
	 * after executing the action. 
	 */
	public ArrayList<Integer> getAdditions() {
		return additions;
	}
	
	/* 
	 * Returns the properties which are to be deleted from the environment
	 * after executing the action. 
	 */
	public ArrayList<Integer> getDeletions() {
		return deletions;
	}
	
	/*
	 * Returns the properties which are supposed to be true before an action
	 * can be executed.
	 */
	public ArrayList<Integer> getPreconditions() {
		return preconditions;
	}
	
	/* Checks if the given property exists in additions array. */
	public boolean checkAdditions(Integer property) {
		boolean result = true;
		
		if ((null != additions) && !additions.contains(property)) {
			result = false;
		}
		
		return result;
	}

	/* Checks if the given property exists in deletions array. */
	public boolean checkDeletions(Integer property) {
		boolean result = true;
		
		if ((null != deletions) && !deletions.contains(property)) {
			result = false;
		}
		
		return result;
	}
	
	/* Checks if the given property exists in preconditions array. */
	public boolean checkPreconditions(Integer property) {
		boolean result = true;
		
		if ((null != preconditions) && !preconditions.contains(property)) {
			result = false;
		}
		
		return result;
	}

	/* Parses an agent action from AgentActions.txt and returns a new AgentAction object. */
	public static AgentAction newInstance(String name, String preconditionString,
			String additionString, String deletionString, Executor executor) {
		AgentAction action = null;
		Integer id = null;
		ArrayList<Integer> preconditions = null;
		ArrayList<Integer> additions = null;
		ArrayList<Integer> deletions = null;
		
		if (executor.actionMapping.containsKey(name)) {
			id = executor.actionMapping.get(name);
			Debug.print(name + " - " + id);
		}
		
		preconditions = parseConditions(preconditionString, executor);
		additions = parseConditions(additionString, executor);
		deletions = parseConditions(deletionString, executor);
		
		if (Debug.enabledDebugging) {
			printArrayList(preconditions, "pre");
			printArrayList(additions, "add");
			printArrayList(deletions, "del");
		}
		
		action = new AgentAction(id, additions, deletions, preconditions);
		
		return action;
	}
	
	/* Prints properties in the array - for debugging purposes */
	private static void printArrayList(ArrayList<Integer> properties, String type) {
		if (null != properties) {
			for (Integer condition : properties) {
				Debug.print(type + " condition: " + condition);
			}
		}
	}

	/*
	 * Parses properties from the input String, which represents a condition or a list. 
	 * Only following operators are allowed in the description: '!', ',' and '^' ('not', 'comma' and 'and').  
	 */
	public static ArrayList<Integer> parseConditions(String string, Executor executor) {
		ArrayList<Integer> conditions = new ArrayList<Integer>();
		String[] subStrings = string.split("\\s+");
		for (String element : subStrings) {
			if (element.length() > 0) {
				if (element.charAt(0) == '!') {
					element = element.substring(1);
					if (executor.sensoryMapping.containsKey(element)) {
						Integer code = executor.sensoryMapping.get(element);
						conditions.add(-1 * code.intValue());
					} else {
						System.out.println("ERROR: Unsupported syntax - " + element);
						System.exit(0);
					}
				} else if (executor.sensoryMapping.containsKey(element)) {
					conditions.add(executor.sensoryMapping.get(element));
				} else if (element.charAt(0) == '^') {
					
				} else if (element.charAt(0) == ',') {
					
				} else {
					System.out.println("ERROR: Unsupported syntax - " + element);
					System.exit(0);
				}
			}
		}
		return conditions;
	}
	
	public AgentAction(Integer id, ArrayList<Integer> additions,
			ArrayList<Integer> deletions, ArrayList<Integer> preconditions) {
		super();
		this.id = id;
		this.additions = additions;
		this.deletions = deletions;
		this.preconditions = preconditions;
	}

	private Integer id;
	private ArrayList<Integer> additions;
	private ArrayList<Integer> deletions;
	private ArrayList<Integer> preconditions;
}
