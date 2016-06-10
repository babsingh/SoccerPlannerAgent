/*
 * This class prints debug information to stdout.
 * Printing debug information can be turned on and off via this class.
 * For better performance, debugging will be turned off during competition.
 */
public class Debug {
	public static boolean enabledDebugging = true;

	/* Print debug information */
	public static void print(String string) {
		if (enabledDebugging) {
			System.out.println(string);
		}
	}
}
