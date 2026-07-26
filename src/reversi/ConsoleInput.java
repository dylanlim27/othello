package reversi;

import java.util.Scanner;

/**
 * Console input reader utility for text-based interface mode.
 */
public class ConsoleInput
{
    private static Scanner keyboardScanner;

    private static Scanner getScanner()
    {
        if (keyboardScanner == null)
        {
            keyboardScanner = new Scanner(System.in);
        }
        return keyboardScanner;
    }

    public static boolean hasInt()
    {
        return getScanner().hasNextInt();
    }

    public static int nextInt()
    {
        if (!getScanner().hasNextInt())
        {
            return Integer.MIN_VALUE;
        }
        return getScanner().nextInt();
    }

    public static String nextString()
    {
        return getScanner().next();
    }
}
