package reversi;

/**
 * Controller implementation governing rules, move validation, multi-directional capture flipping,
 * turn management, Greedy AI moves, and end-game score detection for Reversi.
 */
public class ReversiController implements IController
{
    private IModel model;
    private IView view;

    @Override
    public void initialise(IModel model, IView view)
    {
        this.model = model;
        this.view = view;
    }

    @Override
    public void startup()
    {
        int width = model.getBoardWidth();
        int height = model.getBoardHeight();

        // Clear board
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                model.setBoardContents(x, y, IModel.PLAYER_NONE);
            }
        }

        // Standard Reversi initial 4 pieces starting setup
        model.setBoardContents(3, 3, IModel.PLAYER_WHITE);
        model.setBoardContents(4, 3, IModel.PLAYER_BLACK);
        model.setBoardContents(3, 4, IModel.PLAYER_BLACK);
        model.setBoardContents(4, 4, IModel.PLAYER_WHITE);

        model.setPlayer(IModel.PLAYER_WHITE);
        model.setFinished(false);

        view.refreshView();
        update();
    }

    @Override
    public void squareSelected(int player, int x, int y)
    {
        if (model.hasFinished())
        {
            view.feedbackToUser(player, "Game is already over!");
            return;
        }

        if (player != model.getPlayer())
        {
            view.feedbackToUser(player, "It is not your turn!");
            return;
        }

        if (countCaptures(player, x, y) == 0)
        {
            view.feedbackToUser(player, "Invalid move! Selection must capture at least one opposing piece.");
            return;
        }

        model.setBoardContents(x, y, player);
        doCaptures(player, x, y);

        int nextPlayer = (player == IModel.PLAYER_WHITE) ? IModel.PLAYER_BLACK : IModel.PLAYER_WHITE;
        model.setPlayer(nextPlayer);

        view.refreshView();
        update();
    }

    @Override
    public void doAutomatedMove(int player)
    {
        if (model.hasFinished())
        {
            view.feedbackToUser(player, "Game is already over!");
            return;
        }

        if (player != model.getPlayer())
        {
            view.feedbackToUser(player, "It is not your turn!");
            return;
        }

        int bestX = -1;
        int bestY = -1;
        int maxCaptures = 0;
        int width = model.getBoardWidth();
        int height = model.getBoardHeight();

        // Greedy strategy: pick the move that captures the highest number of pieces
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                if (model.getBoardContents(x, y) == IModel.PLAYER_NONE)
                {
                    int captured = countCaptures(player, x, y);
                    if (captured > maxCaptures)
                    {
                        maxCaptures = captured;
                        bestX = x;
                        bestY = y;
                    }
                }
            }
        }

        if (bestX >= 0 && bestY >= 0)
        {
            squareSelected(player, bestX, bestY);
        }
        else
        {
            update();
        }
    }

    private boolean canPlayerPlay(int player)
    {
        int width = model.getBoardWidth();
        int height = model.getBoardHeight();

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                if (model.getBoardContents(x, y) == IModel.PLAYER_NONE)
                {
                    if (countCaptures(player, x, y) > 0)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int countCaptures(int player, int x, int y)
    {
        if (model.getBoardContents(x, y) != IModel.PLAYER_NONE)
        {
            return 0;
        }

        int total = 0;
        for (int dx = -1; dx <= 1; dx++)
        {
            for (int dy = -1; dy <= 1; dy++)
            {
                if (dx != 0 || dy != 0)
                {
                    total += capturesInDirection(player, x, y, dx, dy, false);
                }
            }
        }
        return total;
    }

    private void doCaptures(int player, int x, int y)
    {
        for (int dx = -1; dx <= 1; dx++)
        {
            for (int dy = -1; dy <= 1; dy++)
            {
                if (dx != 0 || dy != 0)
                {
                    capturesInDirection(player, x, y, dx, dy, true);
                }
            }
        }
    }

    private int capturesInDirection(int player, int startX, int startY, int dx, int dy, boolean doFlip)
    {
        int opponent = (player == IModel.PLAYER_WHITE) ? IModel.PLAYER_BLACK : IModel.PLAYER_WHITE;
        int width = model.getBoardWidth();
        int height = model.getBoardHeight();

        int count = 0;
        int cx = startX + dx;
        int cy = startY + dy;

        while (cx >= 0 && cx < width && cy >= 0 && cy < height && model.getBoardContents(cx, cy) == opponent)
        {
            count++;
            cx += dx;
            cy += dy;
        }

        if (count == 0 || cx < 0 || cx >= width || cy < 0 || cy >= height)
        {
            return 0;
        }

        if (model.getBoardContents(cx, cy) != player)
        {
            return 0;
        }

        if (doFlip)
        {
            int fx = startX + dx;
            int fy = startY + dy;
            for (int i = 0; i < count; i++)
            {
                model.setBoardContents(fx, fy, player);
                fx += dx;
                fy += dy;
            }
        }
        return count;
    }

    private void sendTurnMessages(int activePlayer)
    {
        if (activePlayer == IModel.PLAYER_WHITE)
        {
            view.feedbackToUser(IModel.PLAYER_WHITE, "White player - select a valid square to place your piece");
            view.feedbackToUser(IModel.PLAYER_BLACK, "Black player - waiting for White's move...");
        }
        else
        {
            view.feedbackToUser(IModel.PLAYER_WHITE, "White player - waiting for Black's move...");
            view.feedbackToUser(IModel.PLAYER_BLACK, "Black player - select a valid square to place your piece");
        }
    }

    private void sendEndMessages()
    {
        int width = model.getBoardWidth();
        int height = model.getBoardHeight();
        int white = 0;
        int black = 0;

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int c = model.getBoardContents(x, y);
                if (c == IModel.PLAYER_WHITE)
                {
                    white++;
                }
                else if (c == IModel.PLAYER_BLACK)
                {
                    black++;
                }
            }
        }

        String msg;
        if (white > black)
        {
            msg = "White won! (White: " + white + " - Black: " + black + "). Press Restart to play again.";
        }
        else if (black > white)
        {
            msg = "Black won! (Black: " + black + " - White: " + white + "). Press Restart to play again.";
        }
        else
        {
            msg = "Tie Game! Both players ended with " + white + " pieces. Press Restart to play again.";
        }

        view.feedbackToUser(IModel.PLAYER_WHITE, msg);
        view.feedbackToUser(IModel.PLAYER_BLACK, msg);
    }

    @Override
    public void update()
    {
        int current = model.getPlayer();
        int other = (current == IModel.PLAYER_WHITE) ? IModel.PLAYER_BLACK : IModel.PLAYER_WHITE;

        boolean currentCanPlay = canPlayerPlay(current);
        boolean otherCanPlay = canPlayerPlay(other);

        if (!currentCanPlay && !otherCanPlay)
        {
            model.setFinished(true);
            sendEndMessages();
        }
        else if (!currentCanPlay)
        {
            model.setFinished(false);
            model.setPlayer(other);
            sendTurnMessages(other);
        }
        else
        {
            model.setFinished(false);
            sendTurnMessages(current);
        }

        view.refreshView();
    }
}
