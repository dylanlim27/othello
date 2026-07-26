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
import javax.swing.JTextArea;

/**
 * Text-area Swing preview component demonstrating integrated ASCII board visualization.
 */
public class FakeTextView implements IView
{
    private IModel model;
    private IController controller;

    private JLabel message1 = new JLabel();
    private JLabel message2 = new JLabel();
    private JTextArea board1 = new JTextArea();
    private JTextArea board2 = new JTextArea();
    private JFrame frame1 = new JFrame();

    public FakeTextView()
    {
    }

    @Override
    public void initialise(IModel model, IController controller)
    {
        this.model = model;
        this.controller = controller;

        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setTitle("Reversi ASCII GUI Preview");
        frame1.setLocationRelativeTo(null);

        frame1.getContentPane().setLayout(new GridLayout(1, 2));

        board1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JPanel p1Panel = new JPanel();
        p1Panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
        p1Panel.setLayout(new BorderLayout());
        frame1.add(p1Panel);

        board2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JPanel p2Panel = new JPanel();
        p2Panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        p2Panel.setLayout(new BorderLayout());
        frame1.getContentPane().add(p2Panel);

        message1.setFont(new Font("Arial", Font.BOLD, 16));
        message2.setFont(new Font("Arial", Font.BOLD, 16));

        board1.setFont(new Font("Consolas", Font.BOLD, 16));
        board2.setFont(new Font("Consolas", Font.BOLD, 16));
        board1.setPreferredSize(new Dimension(400, 330));
        board2.setPreferredSize(new Dimension(400, 330));

        message1.setText("Player 1 (White)");
        p1Panel.add(message1, BorderLayout.NORTH);
        p1Panel.add(board1, BorderLayout.CENTER);

        JButton butAI1 = new JButton("AI Move (White)");
        butAI1.addActionListener(e -> controller.doAutomatedMove(1));
        p1Panel.add(butAI1, BorderLayout.SOUTH);

        message2.setText("Player 2 (Black)");
        p2Panel.add(message2, BorderLayout.NORTH);
        p2Panel.add(board2, BorderLayout.CENTER);

        JButton butAI2 = new JButton("AI Move (Black)");
        butAI2.addActionListener(e -> controller.doAutomatedMove(2));
        p2Panel.add(butAI2, BorderLayout.SOUTH);

        frame1.pack();
        frame1.setVisible(true);
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
        StringBuilder p1 = new StringBuilder();
        p1.append("Player 1 View:\r\n");
        String[] output = buildStrings();
        for (String line : output)
        {
            p1.append(line).append("\r\n");
        }
        board1.setText(p1.toString());

        StringBuilder p2 = new StringBuilder();
        p2.append("Player 2 View:\r\n");
        output = buildReverseStrings();
        for (String line : output)
        {
            p2.append(line).append("\r\n");
        }
        board2.setText(p2.toString());

        frame1.repaint();
    }

    @Override
    public void feedbackToUser(int player, String message)
    {
        if (player == 1)
        {
            message1.setText(message);
        }
        else if (player == 2)
        {
            message2.setText(message);
        }
    }
}
