package fr.nathanael2611.kyrgon.launcher;

import fr.litarvan.openauth.AuthenticationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private TextField usernamefield;
    @FXML private TextField passwordfield;
    @FXML private Button connectButton;
    @FXML private Hyperlink notHaveMojangAccount;

    @FXML
    protected void connectHandler(ActionEvent event) {
        String username = usernamefield.getText();
        String password = passwordfield.getText();
        if(username.length()>=1 && password.length()>=1){
            try {
                Helpers.auth(username, password);
                UserInfos.setInfos(username, password);

                Main.switchScene("sample.fxml");
                Main.stage.getScene().getStylesheets().add((this.getClass().getResource("style.css").toExternalForm()));

            } catch (AuthenticationException e) {
                showError("Impossible de se connecter! "+e.getErrorModel().getErrorMessage());
                e.printStackTrace();
            }
        }else{
            showError("Vous devez compléter tous les champs!");
        }
    }

    public static void showError(String error){
        JOptionPane.showMessageDialog(new JPanel(), error, "Erreur !", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Kyrgon Launcher V2 - Started: FIFOU");
        usernamefield.setText(UserInfos.getUsername());
        passwordfield.setText(UserInfos.getPassword());
        connectButton.setOnAction(this::connectHandler);
        notHaveMojangAccount.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(new URI("https://my.minecraft.net/fr-fr/store/minecraft/#register"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }
}
