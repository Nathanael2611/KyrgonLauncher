package fr.nathanael2611.kyrgon.launcher.ui;

import fr.arinonia.launcherlib.launchlib.Launcher;
import fr.arinonia.launcherlib.updater.download.DownloadManager;
import fr.nathanael2611.kyrgon.launcher.Helpers;
import fr.nathanael2611.kyrgon.launcher.KyrgonLauncher;
import fr.nathanael2611.kyrgon.launcher.ui.components.swinger.SColoredBar;
import fr.nathanael2611.kyrgon.launcher.ui.components.swinger.SColoredButton;
import fr.nathanael2611.kyrgon.launcher.update.UpdateManager;
import org.jdesktop.swingx.JXButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class LandingPanel extends KyrgonPanel{

    private final String username;

    private BufferedImage skinHead;

    private JSlider ramSlider = new JSlider();

    private JXButton disconnectButton = new JXButton(
        "Déconnexion"
    );

    private JXButton playButton = new JXButton(
            "JOUER"
    );

    JProgressBar progressBar = new JProgressBar(
    );

    private JComboBox<String> useOpenLauncherLib = new JComboBox<>(new String[]{"OpenLauncherLib", "LauncherLib"});

    public LandingPanel(){
        username = KyrgonLauncher.getInstance().getAccountManager().getAuthResponse().getSelectedProfile().getName();
        try {
            skinHead = ImageIO.read(new URL("https://minotar.net/helm/" + username + "/100.png"));
        } catch (IOException e) {
            Helpers.sendErrorAndClose("Can't download the skin-head...");
            e.printStackTrace();
        }

        disconnectButton.addActionListener(getActionListener(disconnectButton));
        disconnectButton.setText("Déconnexion");
        add(disconnectButton);

        playButton.addActionListener(getActionListener(playButton));
        playButton.setText("JOUER");
        add(playButton);
        playButton.setEnabled(true);

        progressBar.setString("Cliques sur jouer ! ;)");
        progressBar.setFont(new Font("Arial", Font.PLAIN, 20));
        progressBar.setStringPainted(true);
        add(progressBar);

        ramSlider.setMinimum(1);
        ramSlider.setMaximum(8);
        ramSlider.setOrientation(JSlider.HORIZONTAL);
        ramSlider.setValue(2);
        ramSlider.setOpaque(false);
        ramSlider.setForeground(new Color(0, 0, 0, 0));
        ramSlider.setBorder(null);
        ramSlider.addChangeListener(e -> {
            Launcher.ram = ramSlider.getValue();
            KyrgonLauncher.getInstance().getUserInfos().setRam(ramSlider.getValue());
            repaint();
        });
        add(ramSlider);
        ramSlider.setEnabled(true);

        progressBar.setIndeterminate(true);

        if(KyrgonLauncher.getInstance().getUserInfos().useOpenLauncherLib()){
            useOpenLauncherLib.setSelectedIndex(0);
        }else{
            useOpenLauncherLib.setSelectedIndex(1);
        }
        add(useOpenLauncherLib);


        useOpenLauncherLib.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getItem().equals("OpenLauncherLib")){
                    KyrgonLauncher.getInstance().getUserInfos().setUseOpenLauncherLib(true);
                    KyrgonLauncher.getInstance().getLaunchManager().setOpenLauncherLib(true);
                }else{
                    KyrgonLauncher.getInstance().getLaunchManager().setOpenLauncherLib(false);
                    KyrgonLauncher.getInstance().getUserInfos().setUseOpenLauncherLib(false);
                    System.out.println("hello");
                }
            }
        });

    }

    @Override
    public ActionListener getActionListener(JComponent component) {

        if(component == disconnectButton) {
            return e -> {
                KyrgonLauncher.getInstance().getUserInfos().setInfos(
                        "", ""
                );
                KyrgonLauncher.getInstance().getGraphicsManager().setContentPaneAndRepaint(
                        new LoginPanel()
                );
            };
        } else if(component == playButton){

            playButton.setEnabled(false);
            ramSlider.setEnabled(false);
            progressBar.setString("Waiting...");
            return e -> {
                new Thread(()->{
                    Thread t = new Thread(){
                        @Override
                        public void run() {
                            KyrgonLauncher launcher = KyrgonLauncher.getInstance();
                            UpdateManager update = launcher.getUpdateManager();
                            int modsToDownload = launcher.getConfig().getMods().size();
                            super.run();
                            while(!isInterrupted()){
                                try{
                                    DownloadManager manager = update.getActualVersionDownloader().getDownloadManager();
                                    if(manager != null){
                                        if(!manager.isDownloadFinished()) {
                                            String str = update.getUpdateMessage() + " | " + manager.downloadedFile.size() + "/" + manager.filesToDownload.size();
                                            progressBar.setString(str);
                                            progressBar.setMaximum(manager.filesToDownload.size());
                                            progressBar.setValue(manager.downloadedFile.size());
                                        }else{
                                            String str = "Downloading mods: " + update.downloadedMods + "/" + modsToDownload;
                                            progressBar.setString(str);
                                            progressBar.setMaximum(modsToDownload);
                                            progressBar.setValue(update.downloadedMods);
                                        }
                                    }else{
                                        progressBar.setString(update.getUpdateMessage());
                                    }
                                }catch(NullPointerException e){
                                    progressBar.setString(update.getUpdateMessage());
                                }
                            }
                        }
                    };
                    t.start();
                    Launcher.ram = ramSlider.getValue();
                    KyrgonLauncher.getInstance().getUpdateManager().updateMinecraftFiles();
                    KyrgonLauncher.getInstance().getUpdateManager().updateMods();
                    t.interrupt();

                    KyrgonLauncher.getInstance().getLaunchManager().launch();
                    KyrgonLauncher.getInstance().getGraphicsManager().setVisible(false);
                    System.exit(0);
                }).start();
            };
        }
        return super.getActionListener(component);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0, 0, 0, 220));
        g.fillRect(0, 0, getWidth(), 50);

        g.drawImage(
                skinHead,
                5, 5,
                40, 40,
                this
        );

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.setColor(Color.WHITE);
        g.drawString(
                username,
                55, 32
        );

        disconnectButton.setBounds(
                getWidth() - 100, 5,
                95, 40
        );
        playButton.setBounds(
                getWidth() / 2 - 50, getHeight() - 100,
                100, 40
        );
        progressBar.setForeground(Color.GREEN);
        progressBar.setBounds(
                5, getHeight() - 45,
                getWidth() - 10, 40
        );
        ramSlider.setBounds(
                getWidth() / 2 - 150,
                getHeight() - 150,
                300, 20
        );

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString(
                "Ram: " + ramSlider.getValue() + "go",
                getWidth() / 2 - 150, getHeight() - 160
        );
        g.setFont(g.getFont().deriveFont(14f));
        g.drawString(
                "Lancer avec: ", getWidth() - 200, 100
        );
        useOpenLauncherLib.setBounds(getWidth() - 120, 80, 120, 25);

    }
}
