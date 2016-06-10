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
		this.invalidateSensoryInformation();
	}

	/* When new information is received from the server, then reset sensory information. */
	public synchronized void invalidateSensoryInformation() {
		Debug.print("INVALIDATING SENSORY INFO");
		for (Integer propertyID : executor.sensoryMapping.values()) {
			sensoryInformation.put(propertyID, null);
		}
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

	/* Evaluate sensory property about the environment using the Executor.run method */
	private boolean evaluateSensoryInfo(Integer property, Memory memory) {
		boolean result = executor.run(property, memory);
		Debug.print("EVALUATING environment property");
		sensoryInformation.put(property, result);
		return result;
	}
	
	/* 
	 * Evaluates a property (true or false) by calling Executor.run method or 
	 * by reading a cached value from HashMap sensoryInformation.
	 * If alwaysEvaluate is true, it means that the values in HashMap sensoryInformation
	 * are stale. In this case, we always evaluate the property by calling Executor.run method. 
	 */
	public boolean getSensoryInfo(Integer property, boolean alwaysEvaluate) {
		boolean result = false;
		
		if (sensoryInformation.containsKey(property)) {
			 Boolean propertyStatus = sensoryInformation.get(property);
			 if ((!alwaysEvaluate) && (null != propertyStatus)) {
				 result = propertyStatus.booleanValue();
			 } else {
				 result = evaluateSensoryInfo(property, memory);
			 }
		} else if (sensoryInformation.containsKey(-1 * property.intValue())) {
			Boolean propertyStatus = sensoryInformation.get(-1 * property.intValue());
			if ((!alwaysEvaluate) && (null != propertyStatus)) {
				result = !propertyStatus.booleanValue();
			} else {
				result = !evaluateSensoryInfo(property, memory);
			}
		} else {
			System.out.println("ERROR: Unkown mapping provided");
			System.exit(1);
		}
		
		Debug.print("Property: " + property + " - " + result);
		return result;
	}
}
