package fr.nathanael2611.kyrgon.launcher.ui;

import fr.arinonia.launcherlib.authlib.exceptions.AuthenticationUnavailableException;
import fr.arinonia.launcherlib.authlib.exceptions.InvalidCredentialsException;
import fr.arinonia.launcherlib.authlib.exceptions.RequestException;
import fr.nathanael2611.kyrgon.launcher.Helpers;
import fr.nathanael2611.kyrgon.launcher.KyrgonLauncher;
import fr.nathanael2611.kyrgon.launcher.auth.AccountManager;
import fr.nathanael2611.kyrgon.launcher.ui.components.swinger.SColoredButton;
import org.jdesktop.swingx.JXTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The login panel
 */
public class LoginPanel extends KyrgonPanel {

    public JXTextField usernameField = new JXTextField();
    public JPasswordField passwordField = new JPasswordField();
    public JButton signInButton = new JButton(
            "Connexion"
    );

    public Font font = new Font("Arial", Font.PLAIN, 20);

    public LoginPanel(){
        super();

        usernameField.setOpaque(false);
        usernameField.setBackground(new Color(0, 0, 0, 0));
        usernameField.setFont(font);
        usernameField.addKeyListener(new KeyListener() {
            @Override public void keyTyped(KeyEvent e) { }
            @Override public void keyPressed(KeyEvent e) { }
            @Override public void keyReleased(KeyEvent e) {
                if(!Helpers.isValid(usernameField.getText())){ usernameField.setForeground(Color.RED); }
                else{ usernameField.setForeground(Color.GREEN); }
            }
        });
        usernameField.addKeyListener(validationListener);
        add(usernameField);

        passwordField.setOpaque(true);
        passwordField.setFont(font);
        passwordField.addKeyListener(validationListener);
        add(passwordField);

        signInButton.setText("Connexion");
        signInButton.setFont(font);
        signInButton.setEnabled(false);
        //signInButton.action = getActionListener(signInButton);
        signInButton.addActionListener(getActionListener(signInButton));

        add(signInButton);
    }

    @Override
    public ActionListener getActionListener(JComponent component) {

        if(component == signInButton) {
            return e -> {
                KyrgonLauncher.getInstance().getAccountManager().setSelectedAccount(
                        new AccountManager.Account(
                                usernameField.getText(), passwordField.getText()
                        )
                );
                try {
                    KyrgonLauncher.getInstance().getAccountManager().getSelectedAccount().login();
                    KyrgonLauncher.getInstance().getGraphicsManager().setContentPaneAndRepaint(
                            new LandingPanel()
                    );
                } catch (AuthenticationUnavailableException | RequestException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                            this, "Impossible de se connecter ! :/ \nPeut être que vos identifiants sont incorrectes.\nIl est aussi possible que les serveurs d'auth de mojang soit down.", "Authentification error !", JOptionPane.ERROR_MESSAGE
                    );
                }
            };
        }
        return super.getActionListener(component);
    }

    public KeyListener validationListener = new KeyListener() {
        @Override public void keyTyped(KeyEvent e) { }@Override public void keyPressed(KeyEvent e) { }
        @Override public void keyReleased(KeyEvent e) {
            if(Helpers.isValid(usernameField.getText()) && passwordField.getText().length() > 0){
                signInButton.setEnabled(true);
            }else{
                signInButton.setEnabled(false);
            }
        }
    };

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(
                ResourceManager.getImage("logo.png"),
                getWidth() / 2 - 100, 10,
                200, 90, this
        );

        g.setColor(new Color(255, 255, 255, 20));
        usernameField.setBounds(
                10, 110, 380, 40
        );
        g.fillRect(
                usernameField.getX() - 5, usernameField.getY() - 5, usernameField.getWidth() + 5, usernameField.getHeight() + 5
        );
        passwordField.setBounds(
                10, 160, 380, 40
        );
        g.fillRect(
                passwordField.getX() - 5,
                passwordField.getY() - 5,
                passwordField.getWidth() + 5,
                passwordField.getHeight() + 5
        );

        signInButton.setBounds(
                getWidth() / 2 - 100, 220,
                200, 40
        );
    }
}
