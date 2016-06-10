import java.util.HashMap;

/* 
 * This class stores information about the environment.
 * The environment information is quantified in terms of 
 * the evaluation of properties which are used in AgentActions.txt.
 * The evaluation of properties either yields true or false.
 */
public class Environment {
	public Executor executor;
	public Memory memory;
	
	/* Stores the value for a property */
	public HashMap<Integer, Boolean> sensoryInformation;

	public Environment(Executor executor, Memory memory) {
		super();
		this.executor = executor;
		this.memory = memory;
		sensoryInformation = new HashMap<Integer, Boolean>();
	}

	/* Stores true for a property (adds a property from the environment) */
	public void addSensoryInfo(Integer property) {
		if (executor.sensoryMapping.containsValue(property)) {
			sensoryInformation.put(property, true);
			Debug.print("Property: " + property + " - " + true);
		} else {
			Integer negatedProperty = new Integer(-1 * property.intValue());
			if (executor.sensoryMapping.containsValue(negatedProperty)) {
				sensoryInformation.put(negatedProperty, false);
				Debug.print("Property: " + negatedProperty + " - " + false);
			} else {
				System.out.println("ERROR: Unkown mapping/property provided - " + property);
				System.exit(1);
			}
		}
	}

	/* Stores false for a property (deletes properties from the environment) */
	public void deleteSensoryInfo(Integer property) {
		if (executor.sensoryMapping.containsValue(property)) {
			sensoryInformation.put(property, false);
			Debug.print("Property: " + property + " - " + false);
		} else {
			Integer negatedProperty = new Integer(-1 * property.intValue());
			if (executor.sensoryMapping.containsValue(negatedProperty)) {
				sensoryInformation.put(negatedProperty, true);
				Debug.print("Property: " + negatedProperty + " - " + true);
			} else {
				System.out.println("ERROR: Unkown mapping/property provided - " + property);
				System.exit(1);
			}
		}
	}

	/* Evaluates a property (true or false) by calling Executor.run method */
	public boolean getSensoryInfo(Integer property) {
		Integer id = null;
		Integer negatedProperty = null;
		
		if (executor.sensoryMapping.containsValue(property)) {
			id = property;
		} else {
			negatedProperty = new Integer(-1 * property.intValue());
			if (executor.sensoryMapping.containsValue(negatedProperty)) {
				id = negatedProperty;
			}
			if (null == id) {
				System.out.println("ERROR: Unkown mapping provided");
				System.exit(1);
			}
		}
		boolean result = executor.run(id, memory);
		sensoryInformation.put(id, result);
		if ((null != negatedProperty) && (id.compareTo(negatedProperty) == 0)) {
			result = !result;
		}

		Debug.print("Property: " + property + " - " + result);
		return result;
	}
}
