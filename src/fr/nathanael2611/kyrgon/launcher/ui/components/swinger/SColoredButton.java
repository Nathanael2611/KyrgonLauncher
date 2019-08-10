package fr.nathanael2611.kyrgon.launcher.ui.components.swinger;

import fr.nathanael2611.kyrgon.launcher.Helpers;

import java.awt.*;

public class SColoredButton extends AbstractButton {

    /**
     * The button main color
     */
    private Color color;

    /**
     * The button color when the mouse is on
     */
    private Color colorHover;

    /**
     * The button color when it is disabled
     */
    private Color colorDisabled;

    /**
     * The SColoredButton
     *
     * <p>
     *     When the mouse will be on the button, the button will be
     *     a little more white, and when it will be disabled, it will
     *     be a little more gray.
     * </p>
     *
     * @param color
     *            The button color
     */
    public SColoredButton(Color color) {
        this(color, null, null);
    }

    Font font = new Font("Arial", Font.PLAIN, 10);

    @Override
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * The SColoredButton
     *
     * <p>
     *     When the mouse will be on the button, the button texture will
     *     become the given 'colorHover' color, and when it will be disabled,
     *     it will be a little more gray.
     * </p>
     *
     * @param color
     *            The button color
     * @param colorHover
     *            The button color when the mouse is on it
     */
    public SColoredButton(Color color, Color colorHover) {
        this(color, colorHover, null);
    }

    /**
     * The SColoredButton
     *
     * <p>
     *     When the mouse will be on the button, the button texture will
     *     become the given 'colorHover' color, and when it will be disabled,
     *     the texture will become the given 'colorDisabled' color.
     * </p>
     *
     * @param color
     *            The button color
     * @param colorHover
     *            The button color when the mouse is on it
     * @param colorDisabled
     *            The button color when it is disabled
     */
    public SColoredButton(Color color, Color colorHover, Color colorDisabled) {
        // If the color is null, throwing an Illegal Argument Exception, else setting it
        if(color == null)
            throw new IllegalArgumentException("Color == null");
        this.color = color;

        // If the color hover is null, creating it, else, setting it
        if(colorHover == null)
            this.colorHover = color.brighter();
        else
            this.colorHover = colorHover;

        // If the color disabled is null, creating it, else, setting it
        if(colorDisabled == null)
            this.colorDisabled = color.darker();
        else
            this.colorDisabled = colorDisabled;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Getting the corresponding color
        Color color;
        if(!this.isEnabled())
            color = colorDisabled;
        else if (super.isHover())
            color = colorHover;
        else
            color = this.color;

        // Drawing the button
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());

        // If the text is not null
        if(getText() != null) {
            // Activating the anti alias
            Helpers.activateAntialias(g);


            // Picking the string color
            if (getTextColor() != null)
                g.setColor(getTextColor());

            // Drawing the text, centered
            g.setFont(font);
            Helpers.drawCenteredString(g, getText(), this.getBounds());
        }
    }

    /**
     * Set the button color
     *
     * @param color
     *            The new button color
     */
    public void setColor(Color color) {
        // If the given color is null, throwing an Illegal Argument Exception, else setting it
        if(color == null)
            throw new IllegalArgumentException("Color == null");
        this.color = color;

        repaint();
    }

    /**
     * Return the button color
     *
     * @return The button color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Set the button color when the mouse is on
     *
     * @param colorHover
     *            The new button hover color
     */
    public void setColorHover(Color colorHover) {
        if(colorHover == null)
            throw new IllegalArgumentException("colorHover == null");
        this.colorHover = colorHover;

        repaint();
    }

    public Color getColorHover() {
        return colorHover;
    }

    public void setColorDisabled(Color colorDisabled) {
        // If the given disabled color is null, throwing an Illegal Argument Exception, else setting it
        if(colorDisabled == null)
            throw new IllegalArgumentException("colorDisabled == null");
        this.colorDisabled = colorDisabled;

        repaint();
    }

    public Color getColorDisabled() {
        return colorDisabled;
    }
}