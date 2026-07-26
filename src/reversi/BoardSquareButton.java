package reversi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * Custom Swing JButton representing a Reversi board cell.
 * Renders anti-aliased smooth game discs for White and Black players.
 */
@SuppressWarnings("serial")
public class BoardSquareButton extends JButton
{
    private Color drawColor;
    private Color borderColor;
    private int borderSize;

    private int piece = IModel.PLAYER_NONE;

    public BoardSquareButton(int width, int height, Color color, int borderWidth, Color borderCol)
    {
        this.borderSize = borderWidth;
        this.drawColor = color;
        this.borderColor = borderCol;

        setPreferredSize(new Dimension(width, height));
        setBackground(drawColor);
        setOpaque(true);
        setBorderPainted(true);
        setFocusPainted(false);
    }

    public BoardSquareButton(int width, int height, Color color)
    {
        this(width, height, color, 1, Color.BLACK);
    }

    public BoardSquareButton(Action a)
    {
        super(a);
    }

    public BoardSquareButton(Icon icon)
    {
        super(icon);
    }

    public BoardSquareButton(String text, Icon icon)
    {
        super(text, icon);
    }

    public BoardSquareButton(String text)
    {
        super(text);
    }

    public void setPiece(int piece)
    {
        this.piece = piece;
        repaint();
    }

    public int getPiece()
    {
        return piece;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (piece == IModel.PLAYER_NONE)
        {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int pad = 4;
        int size = Math.min(getWidth(), getHeight()) - (pad * 2);

        if (piece == IModel.PLAYER_WHITE)
        {
            // Drop shadow effect
            g2.setColor(new Color(0, 0, 0, 40));
            g2.fillOval(pad + 2, pad + 2, size, size);

            // Piece fill
            g2.setColor(Color.WHITE);
            g2.fillOval(pad, pad, size, size);

            // Piece border outline
            g2.setColor(Color.DARK_GRAY);
            g2.drawOval(pad, pad, size, size);
        }
        else if (piece == IModel.PLAYER_BLACK)
        {
            // Drop shadow effect
            g2.setColor(new Color(0, 0, 0, 40));
            g2.fillOval(pad + 2, pad + 2, size, size);

            // Piece fill
            g2.setColor(new Color(30, 30, 30));
            g2.fillOval(pad, pad, size, size);

            // Piece border outline
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawOval(pad, pad, size, size);
        }

        g2.dispose();
    }
}
