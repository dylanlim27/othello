package reversi;

/**
 * Interface representing the data model for the Reversi board game.
 */
public interface IModel
{
    /** Constant representing an empty square on the board */
    public static final int PLAYER_NONE = 0;

    /** Constant representing the White player */
    public static final int PLAYER_WHITE = 1;

    /** Constant representing the Black player */
    public static final int PLAYER_BLACK = 2;

    /**
     * Initialise the board to a specified size and store references to the View and Controller.
     * 
     * @param width      Width in squares (e.g. 8 for standard Reversi).
     * @param height     Height in squares (e.g. 8 for standard Reversi).
     * @param view       The view reference.
     * @param controller The controller reference.
     */
    void initialise(int width, int height, IView view, IController controller);

    /**
     * Sets the active player turn.
     * 
     * @param player 1 = White, 2 = Black.
     */
    void setPlayer(int player);

    /**
     * Gets the active player turn.
     * 
     * @return 1 = White, 2 = Black.
     */
    int getPlayer();

    /**
     * Checks if the game has finished.
     * 
     * @return true if the game is over, false otherwise.
     */
    boolean hasFinished();

    /**
     * Sets the game finished state.
     * 
     * @param finished true if the game is over, false otherwise.
     */
    void setFinished(boolean finished);

    /**
     * Clears all squares on the board to a specified value.
     * 
     * @param value The value to set (e.g., PLAYER_NONE).
     */
    void clear(int value);

    /**
     * Gets the board width in squares.
     * 
     * @return Board width.
     */
    int getBoardWidth();

    /**
     * Gets the board height in squares.
     * 
     * @return Board height.
     */
    int getBoardHeight();

    /**
     * Gets the piece value at a specified square.
     * 
     * @param x Column index (0-based).
     * @param y Row index (0-based).
     * @return Piece state: 0 = None, 1 = White, 2 = Black.
     */
    int getBoardContents(int x, int y);

    /**
     * Sets the piece value at a specified square.
     * 
     * @param x     Column index (0-based).
     * @param y     Row index (0-based).
     * @param value Piece state: 0 = None, 1 = White, 2 = Black.
     */
    void setBoardContents(int x, int y, int value);
}
