package reversi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JLabel;

/**
 * Custom JLabel helper component displaying colored filled rectangles with customizable borders.
 */
@SuppressWarnings("serial")
public class ColorLabel extends JLabel
{
    private Color drawColor;
    private Color borderColor;
    private int borderSize;

    public ColorLabel(int width, int height, Color color, int borderWidth, Color borderCol)
    {
        this.borderSize = borderWidth;
        this.drawColor = color;
        this.borderColor = borderCol;
        setMinimumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
    }

    public ColorLabel(int width, int height, Color color)
    {
        this(width, height, color, 0, null);
    }

    public Color getDrawColor()
    {
        return drawColor;
    }

    public void setDrawColor(Color drawColor)
    {
        this.drawColor = drawColor;
    }

    public Color getBorderColor()
    {
        return borderColor;
    }

    public void setBorderColor(Color borderColor)
    {
        this.borderColor = borderColor;
    }

    public int getBorderSize()
    {
        return borderSize;
    }

    public void setBorderSize(int borderSize)
    {
        this.borderSize = borderSize;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        if (borderColor != null)
        {
            g.setColor(borderColor);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        if (drawColor != null)
        {
            g.setColor(drawColor);
            g.fillRect(borderSize, borderSize, getWidth() - borderSize * 2, getHeight() - borderSize * 2);
        }
    }
}
