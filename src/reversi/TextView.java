package reversi;

/**
 * Command-line text representation view for Reversi.
 */
public class TextView implements IView
{
    private IModel model;
    private IController controller;
    private String player1Message = "";
    private String player2Message = "";

    public TextView()
    {
    }

    @Override
    public void initialise(IModel model, IController controller)
    {
        this.model = model;
        this.controller = controller;

        new Thread(() -> loopHandlingInput()).start();
    }

    public void loopHandlingInput()
    {
        while (true)
        {
            String input = ConsoleInput.nextString().toLowerCase();
            System.out.println("Processing command: '" + input + "'");

            if (input.equals("exit"))
            {
                System.out.println("Exiting game. Goodbye!");
                System.exit(0);
            }
            else if (input.equals("reset"))
            {
                controller.startup();
            }
            else if (input.equals("auto1"))
            {
                controller.doAutomatedMove(1);
            }
            else if (input.equals("auto2"))
            {
                controller.doAutomatedMove(2);
            }
            else if (input.length() >= 6 && input.charAt(0) == 'p')
            {
                String[] vals = input.substring(1).split(",");
                if (vals.length == 3)
                {
                    try
                    {
                        int player = Integer.parseInt(vals[0]);
                        int x = Integer.parseInt(vals[1]);
                        int y = Integer.parseInt(vals[2]);
                        System.out.println("Player " + player + " played position (" + x + ", " + y + ")");
                        controller.squareSelected(player, x, y);
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Invalid input format. Expected format: P<player>,<x>,<y> (e.g. P1,3,4)");
                    }
                }
            }
            else
            {
                System.out.println("Unrecognized command: '" + input + "'");
            }
        }
    }

    public String[] buildStrings()
    {
        int width = model.getBoardWidth();
        int height = model.getBoardHeight();
        String[] returnArray = new String[height + 1];

        StringBuilder s = new StringBuilder("    ");
        for (int x = 0; x < width; x++)
        {
            s.append(x).append("  ");
        }
        returnArray[0] = s.toString();

        for (int y = 0; y < height; y++)
        {
            s = new StringBuilder(y + " :");
            for (int x = 0; x < width; x++)
            {
                switch (model.getBoardContents(x, y))
                {
                    case 1:
                        s.append(" W ");
                        break;
                    case 2:
                        s.append(" B ");
                        break;
                    default:
                        s.append(" . ");
                        break;
                }
            }
            returnArray[y + 1] = s.toString();
        }
        return returnArray;
    }

    public String[] buildReverseStrings()
    {
        int width = model.getBoardWidth();
        int height = model.getBoardHeight();
        String[] returnArray = new String[height + 1];

        StringBuilder s = new StringBuilder("    ");
        for (int x = 0; x < width; x++)
        {
            s.append(width - x - 1).append("  ");
        }
        returnArray[0] = s.toString();

        for (int y = height - 1; y >= 0; y--)
        {
            s = new StringBuilder(y + " :");
            for (int x = width - 1; x >= 0; x--)
            {
                switch (model.getBoardContents(x, y))
                {
                    case 1:
                        s.append(" W ");
                        break;
                    case 2:
                        s.append(" B ");
                        break;
                    default:
                        s.append(" . ");
                        break;
                }
            }
            returnArray[height - y] = s.toString();
        }

        return returnArray;
    }

    @Override
    public void refreshView()
    {
        System.out.println("\n--- Player 1 (White) View ---");
        String[] output = buildStrings();
        for (String line : output)
        {
            System.out.println(line);
        }
        System.out.println(player1Message);

        System.out.println("\n--- Player 2 (Black) View ---");
        output = buildReverseStrings();
        for (String line : output)
        {
            System.out.println(line);
        }
        System.out.println(player2Message);

        System.out.println("\nCommands: 'P1,3,4' (play move), 'auto1', 'auto2', 'reset', 'exit'");
    }

    @Override
    public void feedbackToUser(int player, String message)
    {
        if (player == 1)
        {
            player1Message = message;
        }
        else if (player == 2)
        {
            player2Message = message;
        }
        System.out.println("\n[Player " + player + " Notification] " + message);
    }
}
