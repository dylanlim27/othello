package reversi;

/**
 * Standard grid-based implementation of the Reversi model.
 */
public class SimpleModel implements IModel
{
    private int[][] boardContents;
    private int width;
    private int height;
    private int player;
    private boolean finished;
    protected IView view;
    protected IController controller;

    @Override
    public void initialise(int width, int height, IView view, IController controller)
    {
        this.width = width;
        this.height = height;
        this.view = view;
        this.controller = controller;
        this.boardContents = new int[width][height];
    }

    @Override
    public void clear(int value)
    {
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                boardContents[x][y] = value;
            }
        }
    }

    @Override
    public int getBoardWidth()
    {
        return width;
    }

    @Override
    public int getBoardHeight()
    {
        return height;
    }

    @Override
    public int getBoardContents(int x, int y)
    {
        return boardContents[x][y];
    }

    @Override
    public void setBoardContents(int x, int y, int value)
    {
        boardContents[x][y] = value;
    }

    @Override
    public void setPlayer(int player)
    {
        this.player = player;
    }

    @Override
    public int getPlayer()
    {
        return player;
    }

    @Override
    public boolean hasFinished()
    {
        return finished;
    }

    @Override
    public void setFinished(boolean finished)
    {
        this.finished = finished;
    }
}
