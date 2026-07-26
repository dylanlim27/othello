package reversi;

/**
 * Main application entry point for the Reversi game.
 * Sets up and connects the Model, View, and Controller (MVC architecture).
 */
public class ReversiMain
{
    private IModel model;
    private IView view;
    private IController controller;

    public ReversiMain()
    {
        // Instantiate MVC components
        model = new SimpleModel();
        view = new GUIView();
        controller = new ReversiController();

        // Connect components (Dependency Injection / Wireup)
        model.initialise(8, 8, view, controller);
        controller.initialise(model, view);
        view.initialise(model, controller);

        // Start the game
        controller.startup();
    }

    public static void main(String[] args)
    {
        new ReversiMain();
    }
}
