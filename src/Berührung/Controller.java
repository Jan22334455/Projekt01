package Berührung;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    private ArrayList<Media> hit1 = new ArrayList<Media>();
    private ArrayList<Media> vid1 = new ArrayList<Media>();
    private MediaPlayer mediaPlayer;
    private MediaPlayer videoPlayer;

    private String tmp = "";
    private int i = 0;
    //MoveBox
    private double richtungX = -1;
    private double richtnungY = 1;
    private double bewegungx = 5;
    //DeusVult
    private double DeusRichtungX = -1;
    private double DeusRichtungY = 1;
    private double Deusbewegungx = 5;

    private int auswahlAutoMove2 = 0;
    private int auswahlAutoMove3 = 0;
    private int punkteRechts = 0;
    private int punkteLinks = 0;
    private int warteBisSpielBegin = 0;
    private boolean start = false;
    private boolean tmp1 = true;
    private boolean musikgeladen = false;
    private boolean IstZulaessigAutoMove2 = true;

    private Main main1;
    private Stage windowMain;

    @FXML
    Label movingBox;
    @FXML
    AnchorPane MainAnchorPane;
    @FXML
    Button button1;
    @FXML
    Label Label2;
    @FXML
    Pane paneaBackground;
    @FXML
    Label InfoLabel;
    @FXML
    Label Label1Rechts;
    @FXML
    Label Label3Links;
    @FXML
    Label Label4Unten;
    @FXML
    Label Label5Top;
    @FXML
    Label SpielerRechts;
    @FXML
    Label SpielerLinks;
    @FXML
    ImageView DeusVult;
    @FXML
    CheckBox volume;
    @FXML
    Canvas canves1;
    @FXML
    ComboBox ComboBoxMusik;
    @FXML
    Button Button2;
    @FXML
    Button ButtonMinus;
    @FXML
    Button ButtonPlus;
    @FXML
    Slider LauterLeiserSlider;
    @FXML
    MediaView MediaView1;
    @FXML
    Button Button3;

    public void start() {
        if (!start) {
            System.out.println("Test");
            DeusVult.setVisible(true);
            Moveing();
            //OnePlayer();
            DeusVult();

            VideoLaden();
            MusikLaden();
            AutoMove2();
            movePingPong();
            Media();
            start = true;

        } else {
            movingBox.setLayoutY(220);
            movingBox.setLayoutX(335);
        }
    }

    public void AutoMove() {
        //RechterSpieler

        if (!(movingBox.getLayoutY() == SpielerRechts.getLayoutY())) {

            System.out.println("Schleife");

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    Platform.runLater(() -> {
                        InfoLabel.setText(String.valueOf(SpielerLinks.getLayoutY()) + " " + String.valueOf(movingBox.getLayoutY()));

                        if (SpielerRechts.getLayoutY() < SpielerRechts.getHeight() / 1.5) {

                            //System.out.println("Schleife4");

                            SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() + 20);
                        }
                        if (SpielerRechts.getLayoutY() > MainAnchorPane.getHeight() - SpielerRechts.getHeight() / 1.5) {

                            //System.out.println("Schleife5");

                            SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() - 20);
                        }

                        if (movingBox.getLayoutX() > 375 && richtungX == +1) {

                            //System.out.println("Schleife1");

                            if (movingBox.getLayoutY() < SpielerRechts.getLayoutY()) {

                                //System.out.println("Schleife2");

                                if (SpielerRechts.getLayoutY() > SpielerRechts.getHeight() / 2 && SpielerRechts.getLayoutY() < MainAnchorPane.getHeight() - SpielerRechts.getHeight() / 2) {

                                    //System.out.println("Schleife3");


                                    SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() - ((movingBox.getLayoutY() - 60) / 80));
                                }


                            } else {
                                if (SpielerRechts.getLayoutY() > SpielerRechts.getHeight() / 1.5 && SpielerRechts.getLayoutY() < MainAnchorPane.getHeight() - SpielerRechts.getHeight() / 1.5) {

                                    //System.out.println("Schleife6");

                                    SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() + ((movingBox.getLayoutY() - 60) / 80));
                                }
                                if (SpielerRechts.getLayoutY() < SpielerRechts.getHeight() / 2) {

                                    //System.out.println("Schleife7");

                                    SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() + 20);
                                }
                                if (SpielerRechts.getLayoutY() / 2 > MainAnchorPane.getHeight() - SpielerRechts.getHeight()) {

                                    //System.out.println("Schleife8");

                                    SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() - 20);
                                }
                            }

                        }

                        if (movingBox.getLayoutX() < 375 && richtungX == -1) {

                            System.out.println("Schleife1");

                            if (movingBox.getLayoutY() < SpielerLinks.getLayoutY()) {

                                //System.out.println("Schleife2");


                                System.out.println("Schleife3");

                                SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() - (movingBox.getLayoutY() / 80));//HIer


                                if (SpielerLinks.getLayoutY() < 0) {

                                    System.out.println("Schleife4");

                                    SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() + 20);
                                }
                                if (SpielerLinks.getLayoutY() > 360) {

                                    System.out.println("Schleife5");

                                    SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() - 20);
                                }

                            } else {


                                System.out.println("Schleife6");

                                SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() + (movingBox.getLayoutY() / 80));//Hier

                                if (SpielerLinks.getLayoutY() < 0) {

                                    System.out.println("Schleife7");

                                    SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() - 20);
                                }
                                if (SpielerLinks.getLayoutY() / 2 > MainAnchorPane.getHeight() - SpielerLinks.getHeight()) {

                                    System.out.println("Schleife8");

                                    SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() - 20);
                                }
                            }

                        }

                    });
                }
            }, 0, 1);
        }
    }

    public void AutoMove2() {

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    IstZulaessigAutoMove2 = true;


                    if (SpielerLinks.getLayoutY() > 350) {
                        auswahlAutoMove2 = 1;
                        IstZulaessigAutoMove2 = false;
                    }
                    if (SpielerLinks.getLayoutY() < 0) {
                        auswahlAutoMove2 = 2;
                        IstZulaessigAutoMove2 = false;
                    }
                    if (movingBox.getLayoutX() < 375 && richtungX == -1) {
                        if (IstZulaessigAutoMove2) {
                            auswahlAutoMove2 = 3;
                        }
                    }

                    switch (auswahlAutoMove2) {
                        case 1:
                            SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() - 2);
                            auswahlAutoMove2 = 0;
                            break;
                        case 2:
                            SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() + 2);
                            auswahlAutoMove2 = 0;
                            break;
                        case 3:
                            if (movingBox.getLayoutY() < SpielerLinks.getLayoutY()) {
                                //SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() - (movingBox.getLayoutY() / 20));
                                SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() - 4);
                                auswahlAutoMove2 = 0;
                            } else {
                                //SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() + (movingBox.getLayoutY() / 80));
                                SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() + 4);
                                auswahlAutoMove2 = 0;
                            }
                            break;
                        default:

                            break;
                    }

                    IstZulaessigAutoMove2 = true;


                    if (SpielerRechts.getLayoutY() > 350) {
                        auswahlAutoMove3 = 1;
                        IstZulaessigAutoMove2 = false;
                    }
                    if (SpielerRechts.getLayoutY() < 0) {
                        auswahlAutoMove3 = 2;
                        IstZulaessigAutoMove2 = false;
                    }
                    if (movingBox.getLayoutX() > 375 && richtungX == 1) {
                        if (IstZulaessigAutoMove2) {
                            auswahlAutoMove3 = 3;
                        }
                    }

                    switch (auswahlAutoMove3) {
                        case 1:
                            SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() - 2);
                            auswahlAutoMove3 = 0;
                            break;
                        case 2:
                            SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() + 2);
                            auswahlAutoMove3 = 0;
                            break;
                        case 3:
                            if (movingBox.getLayoutY() < SpielerRechts.getLayoutY()) {
                                //SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() - (movingBox.getLayoutY() / 20));
                                SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() - 6);

                                auswahlAutoMove3 = 0;
                            } else {
                                //SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() + (movingBox.getLayoutY() / 80));
                                SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() + 6);

                                auswahlAutoMove3 = 0;
                            }
                            break;
                        default:

                            break;
                    }


                });
            }
        }, 0, 6);
    }

    public void MausMovement() {
//        MainAnchorPane.addEventHandler(MouseEvent.ANY, event -> {
//                    SpielerLinks.setLayoutY(event.getY());
//                }
//        );
    }

    public void OnePlayer() {
        Timer timer2 = new Timer();
        timer2.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    IstZulaessigAutoMove2 = true;
//50

                    if (SpielerRechts.getLayoutY() > 333) {
                        auswahlAutoMove3 = 1;
                        IstZulaessigAutoMove2 = false;
                    }
                    if (SpielerRechts.getLayoutY() < 0) {
                        auswahlAutoMove3 = 2;
                        IstZulaessigAutoMove2 = false;
                    }
                    if (movingBox.getLayoutX() > 375 && richtungX == 1) {
                        if (IstZulaessigAutoMove2) {
                            auswahlAutoMove3 = 3;
                        }
                    }

                    switch (auswahlAutoMove3) {
                        case 1:
                            SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() - 2);
                            auswahlAutoMove3 = 0;
                            break;
                        case 2:
                            SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() + 2);
                            auswahlAutoMove3 = 0;
                            break;
                        case 3:
                            if (movingBox.getLayoutY() < SpielerRechts.getLayoutY()) {
                                //SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() - (movingBox.getLayoutY() / 20));
                                SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() - 4);

                                auswahlAutoMove3 = 0;
                            } else {
                                //SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() + (movingBox.getLayoutY() / 80));
                                SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() + 4);

                                auswahlAutoMove3 = 0;
                            }
                            break;
                        default:

                            break;
                    }

                });
            }
        }, 0, 6);
    }

    public void Moveing() {

        MainAnchorPane.addEventHandler(KeyEvent.ANY, keyEvent2 -> {
            tmp = keyEvent2.getCharacter();

            switch (tmp) {
                case "w":
                    if (!(SpielerLinks.getLayoutY() < 0)) {
                        SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() - 2);
                        System.out.println("W Pressed");
                        tmp = "";
                    }
                    break;
                case "s":
                    if (!(SpielerLinks.getLayoutY() > 333)) {
                        SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() + 2);
                        System.out.println("S Pressed");
                        tmp = "";
                    }
                    break;
            }
        });


    }

    public void Moveing2() {


    }

    public String getKeyText(int keyCode) {
//        if (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9 || keyCode >= KeyEvent.VK_A
//                && keyCode <= KeyEvent.VK_Z) {
//            return String.valueOf((char) keyCode);
//        }
        return null;
    }

    public void CheckCollision() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {

                    Rectangle movingBoxR = new Rectangle(movingBox.getWidth(), movingBox.getHeight());
                    Rectangle testHindernisR = new Rectangle(SpielerRechts.getWidth(), SpielerRechts.getHeight());
                    Rectangle testHindernisL = new Rectangle(SpielerLinks.getWidth(), SpielerLinks.getHeight());

                    if (!collidesWith(movingBox, Label1Rechts)) {

                    } else {

                    }
                });
            }
        }, 0, 100);


    }

    public void movePingPong() {
        Label2.setText("Punkte R: " + String.valueOf(punkteRechts + " Punkte L: " + punkteLinks) + " Geschwindigkeit: " + bewegungx);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {

                    int tmp = 0;
                    if (collidesWith(movingBox, Label1Rechts)) {
                        tmp = 1;
                    }
                    if (collidesWith(movingBox, Label3Links)) {
                        tmp = 2;
                    }
                    if (collidesWith(movingBox, Label4Unten)) {
                        tmp = 3;
                    }
                    if (collidesWith(movingBox, Label5Top)) {
                        tmp = 4;
                    }
                    if (collidesWith(movingBox, SpielerLinks)) {
                        tmp = 5;
                    }
                    if (collidesWith(movingBox, SpielerRechts)) {
                        tmp = 6;
                    }

                    switch (tmp) {
                        case 1:
                            System.out.println("Rechts");

                            punkteLinks++;
                            Label2.setText("Punkte R: " + String.valueOf(punkteRechts + " Punkte L: " + punkteLinks) + " Geschwindigkeit: " + bewegungx);

                            movingBox.setLayoutX(350);
                            movingBox.setLayoutY(220);

                            richtnungY = getRandomNumberInRange(-2, -1);
                            richtungX = getRandomNumberInRange(-2, -1);


                            break;
                        case 2:
                            System.out.println("Links");

                            punkteRechts++;
                            Label2.setText("Punkte R: " + String.valueOf(punkteRechts + " Punkte L: " + punkteLinks) + " Geschwindigkeit: " + bewegungx);
                            movingBox.setLayoutX(350);
                            movingBox.setLayoutY(220);


                            richtnungY = getRandomNumberInRange(1, 2);
                            richtungX = getRandomNumberInRange(1, 2);

                            break;
                        case 3:
                            //Unten
                            richtnungY *= -1;
                            movingBox.setLayoutX(movingBox.getLayoutX() + (bewegungx * richtungX)); // + 10
                            movingBox.setLayoutY(movingBox.getLayoutY() + (bewegungx * richtnungY)); // - 10
                            //paneaBackground.setStyle("-fx-background-color: #15deed;");
                            break;
                        case 4:
                            //System.out.println("Oben");
                            richtnungY *= -1;
                            movingBox.setLayoutX(movingBox.getLayoutX() + (bewegungx * richtungX));
                            movingBox.setLayoutY(movingBox.getLayoutY() + (bewegungx * richtnungY));
                            //paneaBackground.setStyle("-fx-background-color: #35c49e;");

                            break;
                        case 5:
                            richtungX *= -1;
                            movingBox.setLayoutX(movingBox.getLayoutX() + (bewegungx * richtungX));
                            movingBox.setLayoutY(movingBox.getLayoutY() + (bewegungx * richtnungY));
                            break;
                        case 6:
                            richtungX *= -1;
                            movingBox.setLayoutX(movingBox.getLayoutX() + (bewegungx * richtungX));
                            movingBox.setLayoutY(movingBox.getLayoutY() + (bewegungx * richtnungY));
                            break;
                        default:
                            //System.out.println("Default");

                            movingBox.setLayoutX(movingBox.getLayoutX() + (bewegungx * richtungX));
                            movingBox.setLayoutY(movingBox.getLayoutY() + (bewegungx * richtnungY));
                            break;
                    }


                });
            }
        }, 0, 20);

    }

    public boolean collidesWith(Label border, Label border2) {
        double x = border2.getLayoutX();
        double y = border2.getLayoutY();

        double width = border2.getWidth();
        double height = border2.getHeight();

        return
                x < border.getLayoutX() + border.getWidth() &&
                        x + width > border.getLayoutX() &&
                        y < border.getLayoutY() + border.getHeight() &&
                        y + height > border.getLayoutY();


    }

    public boolean collidesWith(ImageView border, Label border2) {
        double x = border2.getLayoutX();
        double y = border2.getLayoutY();

        double width = border2.getWidth();
        double height = border2.getHeight();

        return
                x < border.getLayoutX() + border.getFitWidth() &&
                        x + width > border.getLayoutX() &&
                        y < border.getLayoutY() + border.getFitHeight() &&
                        y + height > border.getLayoutY();


    }

    public boolean collidesWithRectangle(Rectangle border, Rectangle border2) {
        double x = border2.getLayoutX();
        double y = border2.getLayoutY();

        double width = border2.getWidth();
        double height = border2.getHeight();

        return
                x < border.getLayoutX() + border.getWidth() &&
                        x + width > border.getLayoutX() &&
                        y < border.getLayoutY() + border.getHeight() &&
                        y + height > border.getLayoutY();


    }

    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Max muss größer als min sein");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public double wertUmkehren(double b) {
        return b *= -1;
    }

    public void MusikLautStärke() {
        if (!(mediaPlayer == null)) {
            mediaPlayer.setMute(volume.isSelected());
        }
        if (!(videoPlayer == null)) {
            videoPlayer.setMute(volume.isSelected());
        }

    }

    public void MusikLaden() {
        try {

            hit1.add(new Media(getClass().getClassLoader().getResource("Musik/background.mp3").toURI().toString()));
            hit1.add(new Media(getClass().getClassLoader().getResource("Musik/ChineseRap.mp3").toURI().toString()));
            hit1.add(new Media(getClass().getClassLoader().getResource("Musik/Darude.mp3").toURI().toString()));
            hit1.add(new Media(getClass().getClassLoader().getResource("Musik/HilariousChinese.mp3").toURI().toString()));
            hit1.add(new Media(getClass().getClassLoader().getResource("Musik/PİMPMY.mp3").toURI().toString()));

            for (Media m :
                    hit1) {
                System.out.println(m.getSource());
            }
            ObservableList<String> options =
                    FXCollections.observableArrayList(
                            "Background",
                            "Chinese Rap",
                            "Darude",
                            "Hilariouse Chinese",
                            "PIMPMY"
                    );

            ComboBoxMusik.setItems(options);
            ComboBoxMusik.setVisible(true);
            System.out.println("");

        } catch (Throwable into_the_garbage_bin) {
            into_the_garbage_bin.printStackTrace();
        }
    }

    public void DeusVult() {
        DeusVult.setRotationAxis(Rotate.Y_AXIS);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {

                    int tmp = 0;
                    if (collidesWith(DeusVult, Label1Rechts)) {
                        tmp = 1;
                    }
                    if (collidesWith(DeusVult, Label3Links)) {
                        tmp = 2;
                    }
                    if (collidesWith(DeusVult, Label4Unten)) {
                        tmp = 3;
                    }
                    if (collidesWith(DeusVult, Label5Top)) {
                        tmp = 4;
                    }

                    switch (tmp) {
                        case 1:
                            //Rechts
                            DeusRichtungX *= -1;
                            DeusVult.setLayoutX(DeusVult.getLayoutX() + (Deusbewegungx * DeusRichtungX) - 10);
                            DeusVult.setLayoutY(DeusVult.getLayoutY() + (Deusbewegungx * DeusRichtungY) + 10);
                            DeusVult.setRotate(180);
                            paneaBackground.setStyle("-fx-background-color: #15deed;");
                            break;
                        case 2:
                            //Links
                            DeusRichtungX *= -1;
                            DeusVult.setLayoutX(DeusVult.getLayoutX() + (Deusbewegungx * DeusRichtungX) + 10);
                            DeusVult.setLayoutY(DeusVult.getLayoutY() + (Deusbewegungx * DeusRichtungY) - 10);
                            DeusVult.setRotate(0);
                            paneaBackground.setStyle("-fx-background-color: #15deed;");
                            break;
                        case 3:
                            //Unten
                            DeusRichtungY *= -1;
                            DeusVult.setLayoutX(DeusVult.getLayoutX() + (Deusbewegungx * DeusRichtungX) + 10);
                            DeusVult.setLayoutY(DeusVult.getLayoutY() + (Deusbewegungx * DeusRichtungY) - 10);

                            paneaBackground.setStyle("-fx-background-color: #15deed;");
                            break;
                        case 4:
                            //System.out.println("Oben");
                            DeusRichtungY *= -1;
                            DeusVult.setLayoutX(DeusVult.getLayoutX() + (Deusbewegungx * DeusRichtungX) - 10);
                            DeusVult.setLayoutY(DeusVult.getLayoutY() + (Deusbewegungx * DeusRichtungY) + 10);

                            paneaBackground.setStyle("-fx-background-color: #15deed;");

                            break;
//
                        default:
                            //System.out.println("Default");

                            DeusVult.setLayoutX(DeusVult.getLayoutX() + (Deusbewegungx * DeusRichtungX));
                            DeusVult.setLayoutY(DeusVult.getLayoutY() + (Deusbewegungx * DeusRichtungY));
                            break;
                    }


                });
            }
        }, 0, 20);
    }

    public void MusikAuswahl() {
        if (!(mediaPlayer == null)) {
            mediaPlayer.setMute(true);
            mediaPlayer = new MediaPlayer(hit1.get(ComboBoxMusik.getSelectionModel().getSelectedIndex()));
            mediaPlayer.play();
        } else {
            //mediaPlayer.setMute(false);
            mediaPlayer = new MediaPlayer(hit1.get(ComboBoxMusik.getSelectionModel().getSelectedIndex()));
            mediaPlayer.play();
        }
    }

    public void MusikLautstärkeLauter() {
        System.out.println("Lauter " + mediaPlayer.getVolume());
        mediaPlayer.setVolume(LauterLeiserSlider.getValue());


    }

    public void VideoLaden() {
        try {
            //System.out.println("VideoLaden");
            vid1.add(new Media(getClass().getClassLoader().getResource("Videos/SevenNation.mp4").toURI().toString()));
            vid1.add(new Media(getClass().getClassLoader().getResource("Videos/neoncatoriginal.mp4").toURI().toString()));
            for (Media m :
                    vid1) {
                System.out.println(m.getSource());
            }

        } catch (Throwable into_the_garbage_bin) {
            into_the_garbage_bin.printStackTrace();
        }

    }

    public void Media() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    videoPlayer = new MediaPlayer(vid1.get(1));
                    MediaView1.setMediaPlayer(videoPlayer);
                    videoPlayer.setMute(true);
                    videoPlayer.play();

                });
            }
        }, 0,68000 );
    }

    public void SceneWechsel() throws Exception {
        Parent FXMLDING = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
        Scene scene2 = new Scene(FXMLDING);

        Stage window = main1.getS1();

        window.setScene(scene2);
        window.show();
        try {
            videoPlayer.stop();
            mediaPlayer.stop();
        } catch (Exception ex) {
            System.out.println("Normal");
        }

    }

}