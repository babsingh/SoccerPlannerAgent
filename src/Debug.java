public class Debug {
	public static boolean enabledDebugging = true;

	public static void print(String string) {
		if (enabledDebugging) {
			System.out.println(string);
		}
	}
}
