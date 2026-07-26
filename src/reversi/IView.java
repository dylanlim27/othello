package reversi;

/**
 * Interface representing the view presentation layer for Reversi.
 */
public interface IView
{
    /**
     * Initialise the view with references to the Model and Controller.
     * 
     * @param model      The game model.
     * @param controller The game controller.
     */
    void initialise(IModel model, IController controller);

    /**
     * Refreshes the board visual display.
     */
    void refreshView();

    /**
     * Displays status or feedback messages to a specific player.
     * 
     * @param player  1 = White, 2 = Black.
     * @param message Message string to present.
     */
    void feedbackToUser(int player, String message);
}
