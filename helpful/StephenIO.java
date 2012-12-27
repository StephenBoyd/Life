
// Provides prompt and readLine methods for console input

package helpful;

import java.io.*;

public class StephenIO {
  private static InputStreamReader streamOfInput = new InputStreamReader(System.in);
  private static BufferedReader bufferedInput = new BufferedReader(streamOfInput, 1);

  // Displays a string without terminating the current line
  public static void prompt(String q) {
    System.out.print(q);
    System.out.flush();
  }

  // Reads and returns a single line of input entered by the
  // user; terminates the program if an exception is thrown
  public static String readLine() {
    String line = null;
    try {
      line = bufferedInput.readLine();
    } catch (IOException e) {
      System.out.println("Error in StephenIO.readLine: " +
                         e.getMessage());
      System.exit(-1);
    }
    return line;
  }
}
