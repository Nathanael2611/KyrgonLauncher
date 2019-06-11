package fr.nathanael2611.kyrgon.launcher;

import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import re.alwyn974.openlauncherlib.LaunchException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Slider ramSlider;

    @FXML
    private ImageView imageView;

    @FXML
    private Button playButton;

    @FXML
    private Label downloadLabel;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label ramLabel;

    @FXML
    private Hyperlink disconnectLink;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Kyrgon Launcher V2 - Started: FIFOU");
        ramSlider.setValue(UserInfos.getRam());

        Image image = new Image("https://crafatar.com/avatars/"+Main.authInfos.getUuid());
        imageView.setImage(image);


        Rectangle clip = new Rectangle(
                imageView.getFitWidth(), imageView.getFitHeight()
        );
        clip.setArcWidth(500);
        clip.setArcHeight(500);
        imageView.setClip(clip);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage img = imageView.snapshot(parameters, null);
        imageView.setClip(null);
        imageView.setEffect(new DropShadow(30, Color.BLACK));
        imageView.setImage(img);

        playButton.setOnAction(this::playHandler);

        Task<Void> updateRamTask = new Task<Void>(){
            @Override
            public Void call(){

                while (!isDone()){
                    BigDecimal n = new BigDecimal(String.valueOf(ramSlider.getValue()));
                    n = n.setScale(1, RoundingMode.FLOOR);

                    updateMessage("RAM: "+n.toString());
                }

                return null;
            }
        };
        ramLabel.textProperty().bind(updateRamTask.messageProperty());
        Thread th = new Thread(updateRamTask);
        th.setDaemon(true);
        th.start();

        disconnectLink.setOnAction(e ->{
            UserInfos.disconnect();
            Parent root = null;
                Main.switchScene("connexion.fxml");
                Main.stage.getScene().getStylesheets().add((this.getClass().getResource("style.css").toExternalForm()));


        });
    }

    private Service<Void> downloadThread;

    public int calculatePercentage(long obtained, long total) {
        return (int)((obtained * 100.0f) / total);
    }

    private void playHandler(ActionEvent actionEvent){
        BigDecimal n = new BigDecimal(String.valueOf(ramSlider.getValue()));
        n = n.setScale(1, RoundingMode.FLOOR);
        UserInfos.setRam(n.doubleValue());
        SUpdate su = new SUpdate("http://kyrgon.fr/launcher/su-launcher/", Main.KG_DIR);
        su.getServerRequester().setRewriteEnabled(true);
        su.addApplication(new FileDeleter());
        Task<Void> task = new Task<Void>(){
            @Override
            public Void call(){
                new Thread(()->{
                    boolean hasStarted = false;
                    while (!isDone()){
                        if(BarAPI.getNumberOfTotalBytesToDownload()>0){
                            hasStarted = true;
                            updateMessage((int)calculatePercentage(BarAPI.getNumberOfTotalDownloadedBytes(), BarAPI.getNumberOfTotalBytesToDownload()) +"%");
                            updateProgress(BarAPI.getNumberOfTotalDownloadedBytes(), BarAPI.getNumberOfTotalBytesToDownload());
                        }else if(hasStarted){
                            updateMessage("Téléchargement terminé ! Lancement...");
                        }else{
                            updateMessage("Vérification des fichiers en cours...");
                        }
                    }
                }).start();
                try {
                    su.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Main.launchMinecraft();
                } catch (LaunchException e) {
                    e.printStackTrace();
                }
                this.done();
                return null;
            }
        };
        downloadLabel.textProperty().bind(task.messageProperty());
        progressBar.progressProperty().bind(task.progressProperty());
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }


    private class DownloadTask extends Task<Void>{


        @Override
        protected Void call() throws Exception {
            for(int i = 0; i<1000; i++){
                downloadLabel.setText(i+"%");
            }
            return null;
        }
    }
}