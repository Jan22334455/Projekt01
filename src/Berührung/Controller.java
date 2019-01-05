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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.awt.*;
import java.util.*;

public class Controller {
    private ArrayList<Media> hit1 = new ArrayList<Media>();
    private ArrayList<Media> vid1 = new ArrayList<Media>();
    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;
    private MediaPlayer videoPlayer;

    private String tmp = "";
    private int i = 0;
    //MoveBox
    private double richtungX = -1;
    private double richtnungY = 1;

    private double bewegungx = 15;

    //private double bewegungx = 5;

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

    //MK1
    private Main main1;
    private Stage windowMain;
    private Pane[][] arr1 = new Pane[20][20];
    private ArrayList<Pane> arrayListPane;
    private int xBackgound = 0;
    private int yBackgound = 0;
    private int multipikatorBackground = 1;
    private boolean PaneVisibility = true;
    private boolean jaNeinKP = false;
    private int zähler = 0;

    //MK2
    private Label[][] arr2 = new Label[200][200];
    private ArrayList<Label> arrayListLabel;

    //Menue
    private static int auswahl;


    @FXML
    Button ButtonSGS;
    @FXML
    Button ButtonSGP;
    @FXML
    Button ButtonPGP;


    //TEST
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

    //Start Methoden

    public void Laden(){
        VideoLaden();
        MusikLaden();
        Media();
        BackgroundMovementLoad();
    }

    public void start() {
        //System.out.println(auswahl);
        switch (auswahl) {
            case 1: // Script gegen Script
                if (!start) {
                    System.out.println("test 1");

                    //MusikPrank();

                    BackGroundMovementRandome();
                    BackGroundMovementRandomeLabel();

                    AutoMove2();
                    movePingPong();

                    start = true;
                } else {
                    BackgroundMovement();
                    movingBox.setLayoutY(960);
                    movingBox.setLayoutX(540);
                }
                break;
            case 2: //Script gegen Player
                if (!start) {
                    Moveing();
                    OnePlayer();



                    //BackgroundMovement();
                    BackGroundMovementRandome();



                    movePingPong();
                    start = true;
                } else {
                    movingBox.setLayoutY(220);
                    movingBox.setLayoutX(335);
                }
                break;
            case 3: //Player gegen Player
                if (!start) {
                    //Moveing 2 Players TODO


                    Moveing();
                    MausMovement();
                    movePingPong();
                    start = true;
                } else {
                    movingBox.setLayoutY(220);
                    movingBox.setLayoutX(335);
                }
                break;
        }

    }

    public void AuswahlSpieMode() throws Exception {

        SceneWechsel2();

        wertUebergabe(1);

    }

    public void AuswahlSpieMode2() throws Exception {

        SceneWechsel2();
        wertUebergabe(2);
    }

    public void AuswahlSpieMode3() throws Exception {

        SceneWechsel2();
        wertUebergabe(3);
    }

    public void wertUebergabe(int übergabe) {
        auswahl = übergabe;

    }

    //Bewegung Script und Maussteuerung

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
                    Label2.setText(SpielerLinks.getLayoutY() + " :Y");

                    //System.out.println("Step:1");
                    if (SpielerLinks.getLayoutY() > 960) {
                        auswahlAutoMove2 = 1;
                        IstZulaessigAutoMove2 = false;
                    }
                    if (SpielerLinks.getLayoutY() < 0) {
                        auswahlAutoMove2 = 2;
                        IstZulaessigAutoMove2 = false;
                    }
                    if (movingBox.getLayoutX() < 300 && richtungX == -1) {
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


                    if (SpielerRechts.getLayoutY() > 960) {
                        auswahlAutoMove3 = 1;
                        IstZulaessigAutoMove2 = false;
                    }
                    if (SpielerRechts.getLayoutY() < 0) {
                        auswahlAutoMove3 = 2;
                        IstZulaessigAutoMove2 = false;
                    }

                    if (movingBox.getLayoutX() > 1620 && richtungX == 1) {

                        if (movingBox.getLayoutX() > 350 && richtungX == 1) {
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


                    }
                });
            }
        }, 0, 6);
    }


    public void MausMovement() {
        MainAnchorPane.addEventHandler(MouseEvent.ANY, event -> {
                    SpielerLinks.setLayoutY(event.getY());
//                    movingBox.setLayoutY(event.getY());
//                    movingBox.setLayoutX(event.getX());
                }
        );
    }

    public void OnePlayer() {
        Timer timer2 = new Timer();
        timer2.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    IstZulaessigAutoMove2 = true;
//50


                    if (SpielerRechts.getLayoutY() > 960) {


                        auswahlAutoMove3 = 1;
                        IstZulaessigAutoMove2 = false;
                    }
                    if (SpielerRechts.getLayoutY() < 0) {
                        auswahlAutoMove3 = 2;
                        IstZulaessigAutoMove2 = false;
                    }

                    if (movingBox.getLayoutX() > 1500 && richtungX == 1) {

                        if (movingBox.getLayoutX() > 350 && richtungX == 1) {

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
                                    SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() - 10);

                                    auswahlAutoMove3 = 0;
                                } else {
                                    //SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() + (movingBox.getLayoutY() / 80));
                                    SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() + 10);

                                    auswahlAutoMove3 = 0;
                                }
                                break;
                            default:

                                break;
                        }

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
                    if (!(SpielerRechts.getLayoutY() < 0)) {
                        SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() - 20);
                        //System.out.println("W Pressed");
                        tmp = "";
                    }
                    break;
                case "s":
                    if (!(SpielerRechts.getLayoutY() > 960)) {
                        SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() + 20);
                        //System.out.println("S Pressed");
                        tmp = "";
                    }
                    break;
            }
        });


    }

    public void Moveing2() {


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

                            movingBox.setLayoutX(960);
                            movingBox.setLayoutY(540);

//                            richtnungY = getRandomNumberInRange(-2, -1);
//                            richtungX = getRandomNumberInRange(-2, -1);


                            break;
                        case 2:
                            System.out.println("Links");
                            punkteRechts++;
                            Label2.setText("Punkte R: " + String.valueOf(punkteRechts + " Punkte L: " + punkteLinks) + " Geschwindigkeit: " + bewegungx);
                            movingBox.setLayoutX(960);
                            movingBox.setLayoutY(540);

//                            richtnungY = getRandomNumberInRange(1, 2);
//                            richtungX = getRandomNumberInRange(1, 2);


//                            richtnungY = getRandomNumberInRange(1, 2);
//                            richtungX = getRandomNumberInRange(1, 2);


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

    //Methoden die gebraucht werden Kollision oder RandomeInts

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

    public boolean collidesWith(Rectangle border, Rectangle border2) {
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

    public String getKeyText(int keyCode) {
//        if (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9 || keyCode >= KeyEvent.VK_A
//                && keyCode <= KeyEvent.VK_Z) {
//            return String.valueOf((char) keyCode);
//        }
        return null;
    }

    //Musik usw.

    public void MusikLautStärke() {
        if (!(mediaPlayer == null)) {
            mediaPlayer.setMute(volume.isSelected());
        }
//        if (!(videoPlayer == null)) {
//            videoPlayer.setMute(volume.isSelected());
//        }

    }

    public void MusikLaden() {
        try {

            hit1.add(new Media(getClass().getClassLoader().getResource("Musik/background.mp3").toURI().toString())); //0
            hit1.add(new Media(getClass().getClassLoader().getResource("Musik/ChineseRap.mp3").toURI().toString())); //1
            hit1.add(new Media(getClass().getClassLoader().getResource("Musik/Darude.mp3").toURI().toString())); //2
            hit1.add(new Media(getClass().getClassLoader().getResource("Musik/HilariousChinese.mp3").toURI().toString())); //3
            hit1.add(new Media(getClass().getClassLoader().getResource("Musik/PİMPMY.mp3").toURI().toString())); //4
            hit1.add(new Media(getClass().getClassLoader().getResource("Musik/Numb.mp3").toURI().toString())); //5
            hit1.add(new Media(getClass().getClassLoader().getResource("Musik/movie_1.mp3").toURI().toString())); //6

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
                            "PIMPMY",
                            "Numb"

                    );

            ComboBoxMusik.setItems(options);
            ComboBoxMusik.setVisible(true);
            System.out.println("");

        } catch (Throwable into_the_garbage_bin) {
            into_the_garbage_bin.printStackTrace();
        }
    }

    public void MusikPrank() {
        mediaPlayer2 = new MediaPlayer(hit1.get(6));
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    for (int x = 0; x < 31; x++) {
                        for (int y = 0; y < 18; y++) {
                            if (collidesWith(movingBox, arr2[x][y])) {
                                mediaPlayer2.play();
                                mediaPlayer2 = null;
                                mediaPlayer2 = new MediaPlayer(hit1.get(6));
                            }
                        }
                    }

                });

            }

        }, 0, 1000);


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
        if (!(mediaPlayer == null)) {
            mediaPlayer.setVolume(LauterLeiserSlider.getValue());
        }


    }

    //MediaView

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
        }, 0, 68000);
    }

    //Scenen und Exit

    public void SceneWechsel() throws Exception {
        Parent FXMLDING = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
        Scene scene2 = new Scene(FXMLDING);
        Stage window = main1.getS1();

        window.setScene(scene2);
        window.show();
        window.setFullScreenExitHint("");
        window.setFullScreen(true);
        try {
            videoPlayer.stop();
            mediaPlayer.stop();
        } catch (Exception ex) {
            //System.out.println("Normal");
        }

    }

    public void SceneWechsel2() throws Exception {
        Parent FXMLDING2 = FXMLLoader.load(getClass().getResource("Berührung.fxml"));

        Scene scene3 = new Scene(FXMLDING2);
        Stage window = main1.getS1();

        window.setScene(scene3);
        window.setFullScreenExitHint("");
        window.show();
        window.setFullScreen(true);

    }


    public void Exit() {
        System.exit(0);
    }

    //BackGround

    public void BackgroundMovementLoad() {


        int xWert = 0;
        int yWert = 0;

        for (int x = 0; x < 20; x++) {

            for (int y = 0; y < 11; y++) {

                //System.out.println("test");
                arr1[x][y] = new Pane();
                arr1[x][y].setMaxWidth(50);
                arr1[x][y].setMinWidth(50);
                arr1[x][y].setMaxHeight(50);
                arr1[x][y].setMinHeight(50);

                arr1[x][y].setLayoutX(50 + xWert);
                arr1[x][y].setLayoutY(20 + yWert);
                arr1[x][y].setStyle("-fx-background-color: #35c49e;");
                arr1[x][y].setOpacity(0.40);

                String felder = "Zeile " + (x + 1) + " Spalte " + (y + 1) + " (Index[" + x + "][" + y + "]) Feld " + i;
                arr1[x][y].setId(felder);
                arrayListPane = new ArrayList<Pane>();
                arrayListPane.add(arr1[x][y]);
                arr1[x][y].setVisible(false);
                MainAnchorPane.getChildren().add(arr1[x][y]);

                yWert += 100;
            }
            yWert = 0;
            xWert += 100;

        }

        int xWert2 = 0;
        int yWert2 = 0;

        for (int x = 0; x < 31; x++) {

            for (int y = 0; y < 18; y++) {

                //System.out.println("test");
                arr2[x][y] = new Label();
                arr2[x][y].setMaxWidth(30);
                arr2[x][y].setMinWidth(30);
                arr2[x][y].setMaxHeight(30);
                arr2[x][y].setMinHeight(30);

                arr2[x][y].setLayoutX(50 + xWert2);
                arr2[x][y].setLayoutY(20 + yWert2);
                arr2[x][y].setStyle("-fx-background-color: #35c49e;");
                arr2[x][y].setOpacity(0.40);
                arr2[x][y].setText("Buh");
                arr2[x][y].setFont(new Font(10));

                String felder = "Zeile " + (x + 1) + " Spalte " + (y + 1) + " (Index[" + x + "][" + y + "]) Feld " + i;
                arr2[x][y].setId(felder);
                arrayListLabel = new ArrayList<Label>();
                arrayListLabel.add(arr2[x][y]);
                arr2[x][y].setVisible(false);
                MainAnchorPane.getChildren().add(arr2[x][y]);

                yWert2 += 60;
            }
            yWert2 = 0;
            xWert2 += 60;

        }

    }

    public void BackgroundMovement() {
        PaneVisibility = true;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    //Zeilen __ Spalten //x Max =20 y Max = 11
                    //System.out.println("X: " + xBackgound + " Y: " + yBackgound + " : " + zähler);
                    arr1[xBackgound][yBackgound].setVisible(PaneVisibility);
                    xBackgound++;
                    yBackgound++;
                    if (yBackgound == 11) {
                        //System.out.println("Über 11y");
                        yBackgound = 0;
                        xBackgound = multipikatorBackground;

                        multipikatorBackground++;
                    }
                    if (xBackgound == 19) {
                        xBackgound = 0;
                        yBackgound = 0;
                        multipikatorBackground = 0;
                        BackgoundMovementSetInVisible();
                        cancel();

//
                    }
                });
            }
        }, 0, 100);

    }

    public void BackgoundMovementSetInVisible() {
        PaneVisibility = false;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    //Zeilen __ Spalten //x Max =20 y Max = 11
                    //System.out.println("X: " + xBackgound + " Y: " + yBackgound + " : " + zähler);
                    arr1[xBackgound][yBackgound].setVisible(PaneVisibility);
                    xBackgound++;
                    yBackgound++;
                    if (yBackgound == 11) {
                        //System.out.println("Über 11y");
                        yBackgound = 0;
                        xBackgound = multipikatorBackground;

                        multipikatorBackground++;
                    }
                    if (xBackgound == 19) {
                        xBackgound = 0;
                        yBackgound = 0;
                        multipikatorBackground = 0;
                        BackgroundMovement();
                        cancel();

                    }
                });
            }
        }, 0, 100);
    }

    public void BackGroundMovementRandome() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {

                    arr1[getRandomNumberInRange(0, 19)][getRandomNumberInRange(0, 10)].setVisible(true);

                });
            }
        }, 0, 100);

        Timer timer2 = new Timer();
        timer2.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    arr1[getRandomNumberInRange(0, 19)][getRandomNumberInRange(0, 10)].setVisible(false);
                });
            }
        }, 0, 25);
    }

    public void BackGroundMovementRandomeLabel() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {

                    arr2[getRandomNumberInRange(0, 30)][getRandomNumberInRange(0, 17)].setVisible(true);

                });
            }
        }, 0, 100);

        Timer timer2 = new Timer();
        timer2.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    arr2[getRandomNumberInRange(0, 30)][getRandomNumberInRange(0, 17)].setVisible(false);
                });
            }
        }, 0, 10);
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

    //Test

    private void Test() {

    }

    @Override
    public String toString() {
        return "Controller{" +
                "hit1=" + hit1 +
                ", vid1=" + vid1 +
                ", mediaPlayer=" + mediaPlayer +
                ", mediaPlayer2=" + mediaPlayer2 +
                ", videoPlayer=" + videoPlayer +
                ", tmp='" + tmp + '\'' +
                ", i=" + i +
                ", richtungX=" + richtungX +
                ", richtnungY=" + richtnungY +
                ", bewegungx=" + bewegungx +
                ", DeusRichtungX=" + DeusRichtungX +
                ", DeusRichtungY=" + DeusRichtungY +
                ", Deusbewegungx=" + Deusbewegungx +
                ", auswahlAutoMove2=" + auswahlAutoMove2 +
                ", auswahlAutoMove3=" + auswahlAutoMove3 +
                ", punkteRechts=" + punkteRechts +
                ", punkteLinks=" + punkteLinks +
                ", warteBisSpielBegin=" + warteBisSpielBegin +
                ", start=" + start +
                ", tmp1=" + tmp1 +
                ", musikgeladen=" + musikgeladen +
                ", IstZulaessigAutoMove2=" + IstZulaessigAutoMove2 +
                ", main1=" + main1 +
                ", windowMain=" + windowMain +
                ", arr1=" + Arrays.toString(arr1) +
                ", arrayListPane=" + arrayListPane +
                ", xBackgound=" + xBackgound +
                ", yBackgound=" + yBackgound +
                ", multipikatorBackground=" + multipikatorBackground +
                ", PaneVisibility=" + PaneVisibility +
                ", jaNeinKP=" + jaNeinKP +
                ", zähler=" + zähler +
                ", arr2=" + Arrays.toString(arr2) +
                ", arrayListLabel=" + arrayListLabel +
                ", ButtonSGS=" + ButtonSGS +
                ", ButtonSGP=" + ButtonSGP +
                ", ButtonPGP=" + ButtonPGP +
                ", movingBox=" + movingBox +
                ", MainAnchorPane=" + MainAnchorPane +
                ", button1=" + button1 +
                ", Label2=" + Label2 +
                ", paneaBackground=" + paneaBackground +
                ", InfoLabel=" + InfoLabel +
                ", Label1Rechts=" + Label1Rechts +
                ", Label3Links=" + Label3Links +
                ", Label4Unten=" + Label4Unten +
                ", Label5Top=" + Label5Top +
                ", SpielerRechts=" + SpielerRechts +
                ", SpielerLinks=" + SpielerLinks +
                ", DeusVult=" + DeusVult +
                ", volume=" + volume +
                ", canves1=" + canves1 +
                ", ComboBoxMusik=" + ComboBoxMusik +
                ", Button2=" + Button2 +
                ", ButtonMinus=" + ButtonMinus +
                ", ButtonPlus=" + ButtonPlus +
                ", LauterLeiserSlider=" + LauterLeiserSlider +
                ", MediaView1=" + MediaView1 +
                ", Button3=" + Button3 +
                '}';

    }

}