import java.util.ArrayList;


public class AgentAction {
	public Integer getID() {
		return id;
	}
	
	public ArrayList<Integer> getAdditions() {
		return additions;
	}
	
	public ArrayList<Integer> getDeletions() {
		return deletions;
	}
	
	public ArrayList<Integer> getPreconditions() {
		return preconditions;
	}
	
	public boolean checkAdditions (Integer action) {
		boolean result = false;
		
		if ((null != additions) && additions.contains(action)) {
			result = true;
		}
		
		return result;
	}

	public boolean checkDeletions (Integer action) {
		boolean result = false;
		
		if ((null != deletions) && deletions.contains(action)) {
			result = true;
		}
		
		return result;
	}
	
	public boolean checkPreconditions (Integer action) {
		boolean result = false;
		
		if ((null != preconditions) && preconditions.contains(action)) {
			result = true;
		}
		
		return result;
	}

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
		
		preconditions = parsePreConditions(preconditionString, executor);
		additions = parseOtherConditions(additionString, executor);
		deletions = parseOtherConditions(deletionString, executor);
		
		if (Debug.enabledDebugging) {
			printArrayList(preconditions, "pre");
			printArrayList(additions, "add");
			printArrayList(deletions, "del");
		}
		
		action = new AgentAction(id, additions, deletions, preconditions);
		
		return action;
	}
	
	private static void printArrayList(ArrayList<Integer> conditions, String type) {
		if (null != conditions) {
			for (Integer condition : conditions) {
				Debug.print(type + " condition: " + condition);
			}
		}
	}

	public static ArrayList<Integer> parsePreConditions(String string, Executor executor) {
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
				} else if (element.charAt(0) == 'v') {
					
				} else {
					System.out.println("ERROR: Unsupported syntax - " + element);
					System.exit(0);
				}
			}
		}
		return conditions;
	}
	
	public static ArrayList<Integer> parseOtherConditions(String string, Executor executor) {
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
