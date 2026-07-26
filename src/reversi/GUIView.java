package reversi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Graphical User Interface for Reversi using Java Swing.
 * Renders dual synchronized windows for Player 1 (White) and Player 2 (Black),
 * with Player 2's board rotated 180 degrees for opposite-side perspective play.
 */
public class GUIView implements IView
{
    private IModel model;
    private IController controller;

    private JLabel message1 = new JLabel("", SwingConstants.CENTER);
    private JLabel message2 = new JLabel("", SwingConstants.CENTER);

    private JPanel board1 = new JPanel();
    private JPanel board2 = new JPanel();

    private JFrame frame1 = new JFrame();
    private JFrame frame2 = new JFrame();

    private BoardSquareButton[][] buttons1;
    private BoardSquareButton[][] buttons2;

    public GUIView()
    {
    }

    @Override
    public void initialise(IModel model, IController controller)
    {
        this.model = model;
        this.controller = controller;

        buildFrame1();
        buildFrame2();

        buttons1 = buildSquareButtons();
        buttons2 = buildReverseSquareButtons();

        addButtons();

        frame1.pack();
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);

        frame2.pack();
        // Offset player 2 frame slightly to display side-by-side cleanly
        if (frame1.getLocation() != null)
        {
            frame2.setLocation(frame1.getX() + frame1.getWidth() + 20, frame1.getY());
        }
        else
        {
            frame2.setLocationRelativeTo(null);
        }
        frame2.setVisible(true);
    }

    private void buildFrame1()
    {
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setTitle("Reversi - White Player View");
        frame1.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(245, 245, 245));
        frame1.add(panel);

        message1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        message1.setForeground(new Color(40, 40, 40));
        message1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        panel.add(message1, BorderLayout.NORTH);

        board1.setLayout(new GridLayout(8, 8, 2, 2));
        board1.setPreferredSize(new Dimension(440, 440));
        board1.setBackground(new Color(34, 139, 34)); // Classic felt green board color
        panel.add(board1, BorderLayout.CENTER);

        JPanel south = new JPanel(new GridLayout(1, 2, 10, 0));
        south.setOpaque(false);

        JButton ai = new JButton("Greedy AI (Play White)");
        ai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        ai.setFocusPainted(false);
        ai.addActionListener(e -> controller.doAutomatedMove(IModel.PLAYER_WHITE));

        JButton restart = new JButton("Restart Game");
        restart.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        restart.setFocusPainted(false);
        restart.addActionListener(e -> controller.startup());

        south.add(ai);
        south.add(restart);

        panel.add(south, BorderLayout.SOUTH);
    }

    private void buildFrame2()
    {
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setTitle("Reversi - Black Player View");
        frame2.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(245, 245, 245));
        frame2.add(panel);

        message2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        message2.setForeground(new Color(40, 40, 40));
        message2.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        panel.add(message2, BorderLayout.NORTH);

        board2.setLayout(new GridLayout(8, 8, 2, 2));
        board2.setPreferredSize(new Dimension(440, 440));
        board2.setBackground(new Color(34, 139, 34)); // Classic felt green board color
        panel.add(board2, BorderLayout.CENTER);

        JPanel south = new JPanel(new GridLayout(1, 2, 10, 0));
        south.setOpaque(false);

        JButton ai = new JButton("Greedy AI (Play Black)");
        ai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        ai.setFocusPainted(false);
        ai.addActionListener(e -> controller.doAutomatedMove(IModel.PLAYER_BLACK));

        JButton restart = new JButton("Restart Game");
        restart.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        restart.setFocusPainted(false);
        restart.addActionListener(e -> controller.startup());

        south.add(ai);
        south.add(restart);

        panel.add(south, BorderLayout.SOUTH);
    }

    private BoardSquareButton[][] buildSquareButtons()
    {
        int width = model.getBoardWidth();
        int height = model.getBoardHeight();
        Color boardGreen = new Color(34, 139, 34);

        BoardSquareButton[][] buttons = new BoardSquareButton[height][width];

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                final int fx = x;
                final int fy = y;

                buttons[y][x] = new BoardSquareButton(50, 50, boardGreen, 1, Color.DARK_GRAY);
                buttons[y][x].addActionListener(e -> controller.squareSelected(IModel.PLAYER_WHITE, fx, fy));
            }
        }

        return buttons;
    }

    private BoardSquareButton[][] buildReverseSquareButtons()
    {
        int width = model.getBoardWidth();
        int height = model.getBoardHeight();
        Color boardGreen = new Color(34, 139, 34);

        BoardSquareButton[][] buttons = new BoardSquareButton[height][width];

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int mx = width - 1 - x;
                int my = height - 1 - y;

                final int fx = mx;
                final int fy = my;

                buttons[y][x] = new BoardSquareButton(50, 50, boardGreen, 1, Color.DARK_GRAY);
                buttons[y][x].addActionListener(e -> controller.squareSelected(IModel.PLAYER_BLACK, fx, fy));
            }
        }

        return buttons;
    }

    private void addButtons()
    {
        for (int y = 0; y < model.getBoardHeight(); y++)
        {
            for (int x = 0; x < model.getBoardWidth(); x++)
            {
                board1.add(buttons1[y][x]);
                board2.add(buttons2[y][x]);
            }
        }
    }

    @Override
    public void refreshView()
    {
        int width = model.getBoardWidth();
        int height = model.getBoardHeight();

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                buttons1[y][x].setPiece(model.getBoardContents(x, y));

                int rx = width - 1 - x;
                int ry = height - 1 - y;

                buttons2[y][x].setPiece(model.getBoardContents(rx, ry));
            }
        }

        board1.repaint();
        board2.repaint();
        frame1.repaint();
        frame2.repaint();
    }

    @Override
    public void feedbackToUser(int player, String message)
    {
        if (player == IModel.PLAYER_WHITE)
        {
            message1.setText(message);
        }
        else if (player == IModel.PLAYER_BLACK)
        {
            message2.setText(message);
        }
    }
}
