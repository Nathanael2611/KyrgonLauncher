package fr.nathanael2611.kyrgon.launcher.ui;

import fr.nathanael2611.kyrgon.launcher.Helpers;
import fr.nathanael2611.kyrgon.launcher.KyrgonLauncher;
import fr.nathanael2611.kyrgon.launcher.ui.components.swinger.SColoredButton;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KyrgonPanel extends JXPanel {


    public KyrgonPanel(){

    }

    public ActionListener getActionListener(JComponent component){
        return e -> {

        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Helpers.drawFullsizedImage(
                g, this, ResourceManager.getImage("bg.png")
        );

    }
}
