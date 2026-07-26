package reversi;

import java.awt.GridLayout;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Diagnostic test harness UI for testing game state transitions, edge case board states,
 * and automated AI playouts.
 */
public class SimpleTestModel extends SimpleModel
{
    private Random rand = new Random();

    public SimpleTestModel()
    {
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        testFrame.setTitle("Reversi Diagnostic Test Control Suite");

        testFrame.getContentPane().setLayout(new GridLayout(11, 1, 5, 5));

        JButton button1 = new JButton("Test Full Board (No Moves Left)");
        button1.addActionListener(e -> {
            clear(1);
            setBoardContents(0, 0, 0);
            setBoardContents(1, 1, 0);
            setFinished(false);
            if (view != null) view.refreshView();
            if (controller != null) controller.update();
        });
        testFrame.getContentPane().add(button1);

        JButton button2 = new JButton("Test Near-Full Board (Valid Moves Left)");
        button2.addActionListener(e -> {
            clear(1);
            setBoardContents(0, 0, 0);
            setBoardContents(1, 1, 2);
            setFinished(false);
            if (view != null) view.refreshView();
            if (controller != null) controller.update();
        });
        testFrame.getContentPane().add(button2);

        JButton button3 = new JButton("Clear Board Completely");
        button3.addActionListener(e -> {
            clear(0);
            setFinished(false);
            if (view != null) view.refreshView();
            if (controller != null) controller.update();
        });
        testFrame.getContentPane().add(button3);

        JButton button4 = new JButton("Fill Board with Black Pieces");
        button4.addActionListener(e -> {
            clear(2);
            setFinished(false);
            if (view != null) view.refreshView();
            if (controller != null) controller.update();
        });
        testFrame.getContentPane().add(button4);

        JButton button5 = new JButton("Random Fill (White / Black Only)");
        button5.addActionListener(e -> {
            for (int x = 0; x < getBoardWidth(); x++)
            {
                for (int y = 0; y < getBoardHeight(); y++)
                {
                    setBoardContents(x, y, 1 + rand.nextInt(2));
                }
            }
            setFinished(false);
            if (view != null) view.refreshView();
            if (controller != null) controller.update();
        });
        testFrame.getContentPane().add(button5);

        JButton button6 = new JButton("Random Fill (Empty / White / Black)");
        button6.addActionListener(e -> {
            for (int x = 0; x < getBoardWidth(); x++)
            {
                for (int y = 0; y < getBoardHeight(); y++)
                {
                    setBoardContents(x, y, rand.nextInt(3));
                }
            }
            setFinished(false);
            if (view != null) view.refreshView();
            if (controller != null) controller.update();
        });
        testFrame.getContentPane().add(button6);

        JButton button7 = new JButton("Set Turn to Player 1 (White)");
        button7.addActionListener(e -> {
            setPlayer(1);
            setFinished(false);
            if (view != null) view.refreshView();
            if (controller != null) controller.update();
        });
        testFrame.getContentPane().add(button7);

        JButton button8 = new JButton("Set Turn to Player 2 (Black)");
        button8.addActionListener(e -> {
            setPlayer(2);
            setFinished(false);
            if (view != null) view.refreshView();
            if (controller != null) controller.update();
        });
        testFrame.getContentPane().add(button8);

        JButton button9 = new JButton("Set Finished Flag = True");
        button9.addActionListener(e -> {
            setFinished(true);
            if (view != null) view.refreshView();
            if (controller != null) controller.update();
        });
        testFrame.getContentPane().add(button9);

        JButton button10 = new JButton("Set Finished Flag = False");
        button10.addActionListener(e -> {
            setFinished(false);
            if (view != null) view.refreshView();
            if (controller != null) controller.update();
        });
        testFrame.getContentPane().add(button10);

        JButton button11 = new JButton("Run Full AI vs AI Game Playout");
        button11.addActionListener(e -> {
            while (!hasFinished())
            {
                if (controller != null)
                {
                    controller.doAutomatedMove(1);
                    controller.doAutomatedMove(2);
                    controller.update();
                }
                if (view != null) view.refreshView();
            }
        });
        testFrame.getContentPane().add(button11);

        testFrame.pack();
        testFrame.setVisible(true);
    }
}
