package fr.nathanael2611.kyrgon.launcher.ui.components;

import javax.swing.*;
import java.awt.*;

public class CustomProgressBar extends JProgressBar {

    private String text = "";
    private Font font = new Font("Arial", Font.PLAIN, 20);

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(font);
        g.drawString(
                text,
                getX(),
                getY()
        );

    }
}
