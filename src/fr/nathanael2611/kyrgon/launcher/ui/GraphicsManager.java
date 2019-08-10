package fr.nathanael2611.kyrgon.launcher.ui;

import fr.arinonia.launcherlib.authlib.exceptions.AuthenticationUnavailableException;
import fr.arinonia.launcherlib.authlib.exceptions.RequestException;
import fr.nathanael2611.kyrgon.launcher.Helpers;
import fr.nathanael2611.kyrgon.launcher.KyrgonLauncher;
import fr.nathanael2611.kyrgon.launcher.auth.AccountManager;
import org.jdesktop.swingx.JXFrame;

import javax.swing.*;

/**
 * The graphics manager.
 *   - Will manage all the UI
 */
public class GraphicsManager extends JXFrame {

    public GraphicsManager(){
    }

    public void start(){
        ResourceManager.setResourcePath("/fr/nathanael2611/kyrgon/launcher/ui/resources/");
        Helpers.sendMessageInConsole("Starting the graphics-manager...", false);
        setTitle("Kyrgon Launcher");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        KyrgonLauncher launcher = KyrgonLauncher.getInstance();
        try {
            new AccountManager.Account(
                    launcher.getUserInfos().getUsername(),
                    launcher.getUserInfos().getPassword()
            ).login();
            setContentPaneAndRepaint(new LandingPanel());
        } catch (AuthenticationUnavailableException | RequestException e) {
            setContentPaneAndRepaint(new LoginPanel());
        }

        setVisible(true);
    }

    public void setContentPaneAndRepaint(JComponent newPanel){
        setContentPane(newPanel);
        repaint();
        revalidate();
    }

}
