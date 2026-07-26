package reversi;

/**
 * Interface representing the game controller logic for Reversi.
 */
public interface IController
{
    /**
     * Initialise the controller with references to the Model and View.
     * 
     * @param model The game model.
     * @param view  The game view.
     */
    void initialise(IModel model, IView view);

    /**
     * Resets and starts a new game session.
     */
    void startup();

    /**
     * Evaluates and updates game state, turn status, and win conditions.
     */
    void update();

    /**
     * Handles user interaction when a board square is selected.
     * 
     * @param player 1 = White, 2 = Black.
     * @param x      Column index.
     * @param y      Row index.
     */
    void squareSelected(int player, int x, int y);

    /**
     * Triggers an automated move for the specified player using the Greedy AI algorithm.
     * 
     * @param player 1 = White, 2 = Black.
     */
    void doAutomatedMove(int player);
}
