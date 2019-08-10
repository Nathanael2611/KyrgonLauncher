package fr.nathanael2611.kyrgon.launcher.ui.components.swinger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class AbstractButton extends JComponent implements MouseListener {

    /**
     * The button text
     */
    private String text;

    /**
     * The color of the text
     */
    private Color textColor;

    /**
     * If the mouse is on the button
     */
    private boolean hover = false;

    public AbstractButton() {
        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    public Runnable action;
    @Override
    public void mouseReleased(MouseEvent e) {
        // If the button is enabled
        if(this.isEnabled() && action != null){
            action.run();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        hover = true;

        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        hover = false;

        repaint();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        repaint();
    }

    /**
     * Set the text of the button
     *
     * @param text
     *            The new button text
     */
    public void setText(String text) {
        // If the given text is null, throwing an Illegal Argument Exception, else setting it
        if(text == null)
            throw new IllegalArgumentException("text == null");
        this.text = text;

        repaint();
    }

    /**
     * Return the text of the button
     *
     * @return The button text
     */
    public String getText() {
        return text;
    }

    /**
     * Set the text color
     *
     * @param textColor
     *            The new string color
     */
    public void setTextColor(Color textColor) {
        // If the given string color is null, throwing an Illegal Argument Exception, else setting it
        if(textColor == null)
            throw new IllegalArgumentException("textColor == null");
        this.textColor = textColor;

        repaint();
    }

    /**
     * Return the text color (default is null)
     *
     * @return The text color
     */
    public Color getTextColor() {
        return textColor;
    }





    /**
     * Return if the mouse is on the button
     *
     * @return If the button is hover
     */
    public boolean isHover() {
        return this.hover;
    }

}