import java.util.HashMap;

public class Environment {
	public Executor executor;
	public Memory memory;
	public SendCommand sendCommand;
	public HashMap<Integer, Boolean> sensoryInformation;

	public Environment(Executor executor, Memory memory) {
		super();
		this.executor = executor;
		this.memory = memory;
		sensoryInformation = new HashMap<Integer, Boolean>();
		for (Integer property : executor.sensoryMapping.values()) {
			sensoryInformation.put(property, false);
		}
	}

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

	public boolean getSensoryInfo(Integer property) {
		Boolean result = null;

		if (sensoryInformation.containsKey(property)) {
			result = sensoryInformation.get(property);
		} else {
			Integer negatedProperty = new Integer(-1 * property.intValue());
			if (sensoryInformation.containsKey(negatedProperty)) {
				result = sensoryInformation.get(negatedProperty);
				if (null != result) {
					result = !result;
				}
			}

			if (null == result) {
				Integer id = null;
				if (executor.sensoryMapping.containsValue(property)) {
					id = property;
				}
				if (executor.sensoryMapping.containsValue(negatedProperty)) {
					id = negatedProperty;
				}
				if (null == id) {
					System.out.println("ERROR: Unkown mapping provided");
					System.exit(1);
				}
				boolean intermediateResult = executor.run(id, memory);
				sensoryInformation.put(id, result);
				if (id.compareTo(negatedProperty) == 0) {
					intermediateResult = !intermediateResult;
				}
				result = intermediateResult;
			}
		}

		boolean out = false;
		if (null != result) {
			out = result.booleanValue();
		}
		Debug.print("Property: " + property + " - " + out);
		return out;
	}
}
