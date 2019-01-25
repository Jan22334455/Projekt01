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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

public class Controller {
    private ArrayList<Media> hit1 = new ArrayList<Media>();
    private ArrayList<Media> vid1 = new ArrayList<Media>();
    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;
    private MediaPlayer mediaPlayer3;
    private MediaPlayer oof;
    private MediaPlayer videoPlayer;

    private String tmp = "";
    private int i = 0;
    private boolean MausMove = true;
    //MoveBox
    private double richtungX = -0.01; //Eventuell noch ändern unten
    private double richtnungY = 0.01;

    private double bewegungx = 50;

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
    private boolean start2 = false;
    private boolean tmp1 = true;
    private boolean musikgeladen = false;
    private boolean IstZulaessigAutoMove2 = true;
    private boolean WallBugFixMK1 = true;

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
    private boolean buttonumbennenung = true;


    //Bewegung
    private boolean w = false;
    private boolean s = false;
    private boolean a = false;
    private boolean d = false;
    private double x; // Move
    private double y = 540;
    private double x2 = 1904; // Move2
    private double y2 = 540;
    private static Label Move = new Label();
    private static Label Move2 = new Label();
    private static boolean Tastaturbewegung = false;
    private static boolean Tastaturbewegung2Spieler = false;

    //Scene
    private Scene scene3;

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
    Button StartButton;
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
    @FXML
    Label AttLabel;

    //Main

    //Start Methoden

    public void Laden() {
        if (!start2) {
            VideoLaden();
            MusikLaden();
            Media2();
            BackgroundMovementLoad();

            start2 = true;
        }
    }

    public void start() {
        if (buttonumbennenung) {
            StartButton.setText("Rest Ball/Cube");
            StartButton.setLayoutX(StartButton.getLayoutX() - 40);
            buttonumbennenung = false;
            Runnable runnable1 = new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(10);
                        } catch (Exception ex) {
                        }
                        WallBugFixMK1 = true;
                    }
                }
            };
            Thread WallBugFix = new Thread(runnable1);
            WallBugFix.start();

        }
        switch (auswahl) {
            case 1: // Script gegen Script
                if (!start) {
                    //MusikPrank();
                    //BackGroundMovementRandome();
                    //BackGroundMovementRandomeLabel();
                    AutoMove2();
                    movePingPong();


                    start = true;
                } else {
                    movingBox.setLayoutY(960);
                    movingBox.setLayoutX(540);
                }
                break;
            case 2: //Script gegen Player
                StartButton.setText("Rest Ball/Cube");
                if (!start) {
                    System.out.println("SpielModus 2");
                    setTastaturbewegung(true);

                    Move.relocate(SpielerLinks.getLayoutX(), SpielerLinks.getLayoutY());
                    //Move.setText("Move");
                    Move.setMaxWidth(SpielerLinks.getMaxWidth());
                    Move.setMaxHeight(SpielerLinks.getMaxHeight());
                    Move.setMinWidth(SpielerLinks.getMinWidth());
                    Move.setMinHeight(SpielerLinks.getMinHeight());

                    MainAnchorPane.getChildren().add(Move);


                    OnePlayer();
                    movePingPong();
                    start = true;
                } else {
                    movingBox.setLayoutY(960);
                    movingBox.setLayoutX(540);
                }
                break;
            case 3: //Player gegen Player
                StartButton.setText("Rest Ball/Cube");

                if (!start) {
                    Tastaturbewegung2Spieler = true;
                    Tastaturbewegung = true;
                    Move.relocate(SpielerLinks.getLayoutX(), SpielerLinks.getLayoutY());
                    //Move.setText("Move");
                    Move.setMaxWidth(SpielerLinks.getMaxWidth());
                    Move.setMaxHeight(SpielerLinks.getMaxHeight());
                    Move.setMinWidth(SpielerLinks.getMinWidth());
                    Move.setMinHeight(SpielerLinks.getMinHeight());

                    MainAnchorPane.getChildren().add(Move);


                    Move2.relocate(SpielerRechts.getLayoutX(), SpielerRechts.getLayoutY());
                    //Move2.setText("Move2");
                    Move2.setMaxWidth(SpielerRechts.getMaxWidth());
                    Move2.setMaxHeight(SpielerRechts.getMaxHeight());
                    Move2.setMinWidth(SpielerRechts.getMinWidth());
                    Move2.setMinHeight(SpielerRechts.getMinHeight());

                    MainAnchorPane.getChildren().add(Move2);

                    movePingPong();

                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        public void run() {
                            Platform.runLater(() -> {
                                SpielerLinks.relocate(Move.getLayoutX(), Move.getLayoutY());
                                SpielerRechts.relocate(Move2.getLayoutX(), Move2.getLayoutY());
                            });
                        }
                    }, 0, 4);



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
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    Platform.runLater(() -> {
                        InfoLabel.setText(String.valueOf(SpielerLinks.getLayoutY()) + " " + String.valueOf(movingBox.getLayoutY()));

                        if (SpielerRechts.getLayoutY() < SpielerRechts.getHeight() / 1.5) {

                            SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() + 20);
                        }
                        if (SpielerRechts.getLayoutY() > MainAnchorPane.getHeight() - SpielerRechts.getHeight() / 1.5) {

                            SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() - 20);
                        }

                        if (movingBox.getLayoutX() > 375 && richtungX == +1) {

                            if (movingBox.getLayoutY() < SpielerRechts.getLayoutY()) {

                                if (SpielerRechts.getLayoutY() > SpielerRechts.getHeight() / 2 && SpielerRechts.getLayoutY() < MainAnchorPane.getHeight() - SpielerRechts.getHeight() / 2) {

                                    SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() - ((movingBox.getLayoutY() - 60) / 80));
                                }

                            } else {
                                if (SpielerRechts.getLayoutY() > SpielerRechts.getHeight() / 1.5 && SpielerRechts.getLayoutY() < MainAnchorPane.getHeight() - SpielerRechts.getHeight() / 1.5) {

                                    SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() + ((movingBox.getLayoutY() - 60) / 80));
                                }
                                if (SpielerRechts.getLayoutY() < SpielerRechts.getHeight() / 2) {

                                    SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() + 20);
                                }
                                if (SpielerRechts.getLayoutY() / 2 > MainAnchorPane.getHeight() - SpielerRechts.getHeight()) {

                                    SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() - 20);
                                }
                            }

                        }

                        if (movingBox.getLayoutX() < 375 && richtungX == -1) {

                            if (movingBox.getLayoutY() < SpielerLinks.getLayoutY()) {

                                SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() - (movingBox.getLayoutY() / 80));//HIer

                                if (SpielerLinks.getLayoutY() < 0) {

                                    SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() + 20);
                                }
                                if (SpielerLinks.getLayoutY() > 360) {

                                    SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() - 20);
                                }

                            } else {

                                SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() + (movingBox.getLayoutY() / 80));//Hier

                                if (SpielerLinks.getLayoutY() < 0) {

                                    SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() - 20);
                                }
                                if (SpielerLinks.getLayoutY() / 2 > MainAnchorPane.getHeight() - SpielerLinks.getHeight()) {

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
                    //Label2.setText(SpielerLinks.getLayoutY() + " :Y");

                    if (SpielerLinks.getLayoutY() > 960) {
                        auswahlAutoMove2 = 1;
                        IstZulaessigAutoMove2 = false;
                    }
                    if (SpielerLinks.getLayoutY() < 0) {
                        auswahlAutoMove2 = 2;
                        IstZulaessigAutoMove2 = false;
                    }
                    if (movingBox.getLayoutX() < 300 && richtungX == -0.01) {
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
                            if (movingBox.getLayoutY() - 50 < SpielerLinks.getLayoutY()) {
                                SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() - 10);
                                auswahlAutoMove2 = 0;
                            } else {
                                SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() + 10);
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
                    if (movingBox.getLayoutX() > 1620 && richtungX == 0.01) {

                        //if (movingBox.getLayoutX() > 350 && richtungX == 1) {
                        if (IstZulaessigAutoMove2) {
                            auswahlAutoMove3 = 3;
                        }
                        //}

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
                                if (movingBox.getLayoutY() - 50 < SpielerRechts.getLayoutY()) {
                                    SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() - 10);
                                    auswahlAutoMove3 = 0;
                                } else {
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

    public void SchleifeTasteGedrückt() throws IOException {

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {

                    if (isW()) {
                        if (Tastaturbewegung) {
                            Move.relocate(x, y);
                            y -= 2;
                        }
                    }
                    if (isS()) {
                        if (Tastaturbewegung) {
                            Move.relocate(x, y);
                            y += 2;
                        }
                    }
                    if (isA()) { // Up
                        if (Tastaturbewegung2Spieler) {
                            Move2.relocate(x2, y2);
                            y2 -= 2;
                        }
                    }
                    if (isD()) { // Down
                        if (Tastaturbewegung2Spieler) {
                            Move2.relocate(x2, y2);
                            y2 += 2;
                        }
                    }

                });
            }
        }, 0, 4);

    }


    public void AutoMove3() {

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    IstZulaessigAutoMove2 = true;
                    //Label2.setText(SpielerLinks.getLayoutY() + " :Y");

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
                            if (movingBox.getLayoutY() - 50 < SpielerLinks.getLayoutY()) {
                                SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() - 1);
                                auswahlAutoMove2 = 0;
                            } else {
                                SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() + 1);
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
                                if (movingBox.getLayoutY() - 50 < SpielerRechts.getLayoutY()) {
                                    SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() - 2);
                                    auswahlAutoMove3 = 0;
                                } else {
                                    SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() + 2);
                                    auswahlAutoMove3 = 0;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        }, 0, 1);
    }

    public void MausMovement() {
        //MainAnchorPane.addEventHandler(MouseEvent.ANY, event -> {
//                    MausMove = true;
//                    if (SpielerLinks.getLayoutY() > 960) {
//                        MausMove = false;
//                        SpielerLinks.setLayoutY(960);
//                    }
//                    if (SpielerLinks.getLayoutY() < 0) {
//                        MausMove = false;
//                        SpielerLinks.setLayoutY(0);
//                    }
//                    if (MausMove) {
//                        SpielerLinks.setLayoutY(event.getY() - 60);
//
//                    }
////                    movingBox.setLayoutY(event.getY());
////                    movingBox.setLayoutX(event.getX());
//                }
//        );
    }

    public void OnePlayer() {
        Timer timer2 = new Timer();
        timer2.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    SpielerLinks.relocate(Move.getLayoutX(), Move.getLayoutY());


                    IstZulaessigAutoMove2 = true;

                    if (SpielerRechts.getLayoutY() > 960) {
                        auswahlAutoMove3 = 1;
                        IstZulaessigAutoMove2 = false;
                    }
                    if (SpielerRechts.getLayoutY() < 0) {
                        auswahlAutoMove3 = 2;
                        IstZulaessigAutoMove2 = false;
                    }

                    if (movingBox.getLayoutX() > 1500 && richtungX == 0.01) {
                        if (movingBox.getLayoutX() > 350 && richtungX == 0.01) {
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
                                if (movingBox.getLayoutY() - 50 < SpielerRechts.getLayoutY()) {
                                    SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() - 10);

                                    auswahlAutoMove3 = 0;
                                } else {
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
        }, 0, 13);
    }

    public void movePingPong() {
        //Label2.setText("Punkte R: " + String.valueOf(punkteRechts + " Punkte L: " + punkteLinks) + " Geschwindigkeit: " + bewegungx);
        AttLabel.setText(Attribute());


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    AttLabel.setText(toString());
                    if (isWallBugFixMK1()) {
                        int tmp = 0;
                        if (collidesWith(movingBox, Label1Rechts)) {
                            tmp = 1;
                            setWallBugFixMK1(false);
                        }
                        if (collidesWith(movingBox, Label3Links)) {
                            tmp = 2;
                            setWallBugFixMK1(false);
                        }
                        if (collidesWith(movingBox, Label4Unten)) {
                            tmp = 3;
                            setWallBugFixMK1(false);
                        }
                        if (collidesWith(movingBox, Label5Top)) {
                            tmp = 4;
                            setWallBugFixMK1(false);
                        }
                        if (collidesWith(movingBox, SpielerLinks)) {
                            tmp = 5;
                            setWallBugFixMK1(false);
                        }
                        if (collidesWith(movingBox, SpielerRechts)) {
                            tmp = 6;
                            setWallBugFixMK1(false);
                        }

                        switch (tmp) {
                            case 1:
                                punkteLinks++;
                                Label2.setText("Punkte R: " + String.valueOf(punkteRechts + " Punkte L: " + punkteLinks) + " Geschwindigkeit: " + bewegungx);
                                movingBox.setLayoutX(960);
                                movingBox.setLayoutY(540);
                                //richtnungY = getRandomNumberInRange(-2, -1);
                                richtungX *= -1;
                                break;
                            case 2:
                                //System.out.println("Links");
                                punkteRechts++;
                                Label2.setText("Punkte R: " + String.valueOf(punkteRechts + " Punkte L: " + punkteLinks) + " Geschwindigkeit: " + bewegungx);
                                movingBox.setLayoutX(960);
                                movingBox.setLayoutY(540);
                                richtungX *= -1;
                                //richtnungY = getRandomNumberInRange(1, 2);
                                break;
                            case 3:
                                //Unten
                                richtnungY *= -1;
                                movingBox.setLayoutX(movingBox.getLayoutX() + (bewegungx * richtungX)); // + 10
                                movingBox.setLayoutY(movingBox.getLayoutY() + (bewegungx * richtnungY)); // - 10
                                break;
                            case 4:
                                //Oben
                                richtnungY *= -1;
                                movingBox.setLayoutX(movingBox.getLayoutX() + (bewegungx * richtungX));
                                movingBox.setLayoutY(movingBox.getLayoutY() + (bewegungx * richtnungY));
                                break;
                            case 5:
                                richtungX *= -1;
                                movingBox.setLayoutX(movingBox.getLayoutX() + (bewegungx * richtungX));
                                movingBox.setLayoutY(movingBox.getLayoutY() + (bewegungx * richtnungY));
                                try {
                                    oof = new MediaPlayer(new Media(getClass().getClassLoader().getResource("Musik/Roblox Death Sound - OOF  Sound Effect.mp3").toURI().toString()));
                                } catch (Exception ex) {
                                }
                                //oof.play();
                                break;
                            case 6:
                                richtungX *= -1;
                                movingBox.setLayoutX(movingBox.getLayoutX() + (bewegungx * richtungX));
                                movingBox.setLayoutY(movingBox.getLayoutY() + (bewegungx * richtnungY));
                                try {
                                    oof = new MediaPlayer(new Media(getClass().getClassLoader().getResource("Musik/Roblox Death Sound - OOF  Sound Effect.mp3").toURI().toString()));
                                } catch (Exception ex) {
                                }
                                //oof.play();
                                break;
                            default:
//                                int Chaos = getRandomNumberInRange(1, 2);
//                                int Chaos2 = getRandomNumberInRange(1, 2);
                                movingBox.setLayoutX(movingBox.getLayoutX() + (bewegungx * richtungX));
                                movingBox.setLayoutY(movingBox.getLayoutY() + (bewegungx * richtnungY));
                                break;
                        }

                    }
                });
            }
        }, 0, 1);
    }

    public void movePingPong2(Label beweglichesTeil) {
        //Label2.setText("Punkte R: " + String.valueOf(punkteRechts + " Punkte L: " + punkteLinks) + " Geschwindigkeit: " + bewegungx);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {

                    int tmp = 0;
                    if (collidesWith(beweglichesTeil, Label1Rechts)) {
                        tmp = 1;
                    }
                    if (collidesWith(beweglichesTeil, Label3Links)) {
                        tmp = 2;
                    }
                    if (collidesWith(beweglichesTeil, Label4Unten)) {
                        tmp = 3;
                    }
                    if (collidesWith(beweglichesTeil, Label5Top)) {
                        tmp = 4;
                    }
                    if (collidesWith(beweglichesTeil, SpielerLinks)) {
                        tmp = 5;
                    }
                    if (collidesWith(beweglichesTeil, SpielerRechts)) {
                        tmp = 6;
                    }

                    switch (tmp) {
                        case 1:
                            richtnungY *= -1;
                            beweglichesTeil.setLayoutX(beweglichesTeil.getLayoutX() + (bewegungx * richtungX));
                            beweglichesTeil.setLayoutY(beweglichesTeil.getLayoutY() + (bewegungx * richtnungY));
//                            beweglichesTeil.setLayoutX(960);
//                            beweglichesTeil.setLayoutY(540);

                            richtungX *= -1;
                            break;
                        case 2:
                            richtnungY *= -1;
                            beweglichesTeil.setLayoutX(beweglichesTeil.getLayoutX() + (bewegungx * richtungX));
                            beweglichesTeil.setLayoutY(beweglichesTeil.getLayoutY() + (bewegungx * richtnungY));
//                            beweglichesTeil.setLayoutX(960);
//                            beweglichesTeil.setLayoutY(540);
                            richtungX *= -1;

                            break;
                        case 3:
                            //Unten
                            richtnungY *= -1;
                            beweglichesTeil.setLayoutX(beweglichesTeil.getLayoutX() + (bewegungx * richtungX)); // + 10
                            beweglichesTeil.setLayoutY(beweglichesTeil.getLayoutY() + (bewegungx * richtnungY)); // - 10
                            break;
                        case 4:
                            //Oben
                            richtnungY *= -1;
                            beweglichesTeil.setLayoutX(beweglichesTeil.getLayoutX() + (bewegungx * richtungX));
                            beweglichesTeil.setLayoutY(beweglichesTeil.getLayoutY() + (bewegungx * richtnungY));
                            break;
                        case 5:
                            richtungX *= -1;
                            beweglichesTeil.setLayoutX(beweglichesTeil.getLayoutX() + (bewegungx * richtungX));
                            beweglichesTeil.setLayoutY(beweglichesTeil.getLayoutY() + (bewegungx * richtnungY));
                            try {
                                oof = new MediaPlayer(new Media(getClass().getClassLoader().getResource("Musik/Roblox Death Sound - OOF  Sound Effect.mp3").toURI().toString()));
                            } catch (Exception ex) {
                            }
                            //oof.play();
                            break;
                        case 6:
                            richtungX *= -1;
                            beweglichesTeil.setLayoutX(beweglichesTeil.getLayoutX() + (bewegungx * richtungX));
                            beweglichesTeil.setLayoutY(beweglichesTeil.getLayoutY() + (bewegungx * richtnungY));
                            try {
                                oof = new MediaPlayer(new Media(getClass().getClassLoader().getResource("Musik/Roblox Death Sound - OOF  Sound Effect.mp3").toURI().toString()));
                            } catch (Exception ex) {
                            }
                            //oof.play();
                            break;
                        default:
                            int Chaos = getRandomNumberInRange(1, 2);
                            int Chaos2 = getRandomNumberInRange(1, 2);
                            beweglichesTeil.setLayoutX(beweglichesTeil.getLayoutX() + (bewegungx * richtungX + Chaos));
                            beweglichesTeil.setLayoutY(beweglichesTeil.getLayoutY() + (bewegungx * richtnungY + Chaos2));
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

    //Musik usw.

    public void MusikLautStärke() {
        if (!(mediaPlayer == null)) {
            mediaPlayer.setMute(volume.isSelected());
        }
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
        }, 0, 2000);
    }

    public void MusikPrank2() {
        while (true) {
            for (int x = 0; x < 31; x++) {
                for (int y = 0; y < 18; y++) {
                    if (collidesWith(movingBox, arr2[x][y]) && arr2[x][y].isVisible()) {
                        //System.out.println(arr2[x][y].getLayoutY() + " : " + arr2[x][y].getLayoutY());
                        mediaPlayer2 = new MediaPlayer(hit1.get(6));
                        mediaPlayer2.play();
                        try {
                            Thread.sleep((long) hit1.get(6).getDuration().toMillis());
                        } catch (Exception ex) {
                        }
                    }
                }
            }
        }
    }

    public void MusikAuswahl() {
        if (!(mediaPlayer == null)) {
            mediaPlayer.setMute(true);
            mediaPlayer = new MediaPlayer(hit1.get(ComboBoxMusik.getSelectionModel().getSelectedIndex()));
            mediaPlayer.setVolume(LauterLeiserSlider.getValue());
            mediaPlayer.play();
        } else {
            //mediaPlayer.setMute(false);
            mediaPlayer = new MediaPlayer(hit1.get(ComboBoxMusik.getSelectionModel().getSelectedIndex()));
            mediaPlayer.setVolume(LauterLeiserSlider.getValue());
            mediaPlayer.play();
        }
    }

    public void MusikLautstärkeLauter() {
        //System.out.println("Lauter " + mediaPlayer.getVolume());
        try {
            if (!(mediaPlayer == null)) {
                mediaPlayer.setVolume(LauterLeiserSlider.getValue());
            }
        } catch (Exception ex) {
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
        int periodeMedia = 0;
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

    public void Media2() {
        Runnable mediaRun = new Runnable() {
            @Override
            public void run() {
                videoPlayer = new MediaPlayer(vid1.get(1));
                MediaView1.setMediaPlayer(videoPlayer);
                videoPlayer.setMute(true);
                videoPlayer.play();
            }
        };
        Thread mediaThread = new Thread(mediaRun);
        mediaThread.start();
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
        scene3 = new Scene(FXMLDING2);


        Stage window = main1.getS1();
        window.setScene(scene3);
        window.setFullScreenExitHint("");
        window.show();
        window.setFullScreen(true);

        scene3.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.W) {
                setW(true);
            }
            if (event.getCode() == KeyCode.S) {
                setS(true);
            }
            if (event.getCode() == KeyCode.O) {
                setA(true);
            }
            if (event.getCode() == KeyCode.L) {
                setD(true);
            }
        });

        scene3.setOnKeyReleased(event -> {

            if (event.getCode() == KeyCode.W)
                setW(false);
            if (event.getCode() == KeyCode.S)
                setS(false);
            if (event.getCode() == KeyCode.O)
                setA(false);
            if (event.getCode() == KeyCode.L)
                setD(false);

        });
        SchleifeTasteGedrückt();
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
                    arr1[xBackgound][yBackgound].setVisible(PaneVisibility);
                    xBackgound++;
                    yBackgound++;
                    if (yBackgound == 11) {
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
                    arr1[xBackgound][yBackgound].setVisible(PaneVisibility);
                    xBackgound++;
                    yBackgound++;
                    if (yBackgound == 11) {
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
                            break;
                        case 2:
                            //Links
                            DeusRichtungX *= -1;
                            DeusVult.setLayoutX(DeusVult.getLayoutX() + (Deusbewegungx * DeusRichtungX) + 10);
                            DeusVult.setLayoutY(DeusVult.getLayoutY() + (Deusbewegungx * DeusRichtungY) - 10);
                            DeusVult.setRotate(0);
                            break;
                        case 3:
                            //Unten
                            DeusRichtungY *= -1;
                            DeusVult.setLayoutX(DeusVult.getLayoutX() + (Deusbewegungx * DeusRichtungX) + 10);
                            DeusVult.setLayoutY(DeusVult.getLayoutY() + (Deusbewegungx * DeusRichtungY) - 10);
                            break;
                        case 4:
                            //System.out.println("Oben");
                            DeusRichtungY *= -1;
                            DeusVult.setLayoutX(DeusVult.getLayoutX() + (Deusbewegungx * DeusRichtungX) - 10);
                            DeusVult.setLayoutY(DeusVult.getLayoutY() + (Deusbewegungx * DeusRichtungY) + 10);
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

    public String Attribute() {
        return "Controller{" + "\n" +
                "hit1=" + hit1 + "\n" +
                ", vid1=" + vid1 + "\n" +
                ", mediaPlayer=" + getMediaPlayer() + "\n" +
                ", mediaPlayer2=" + mediaPlayer2 + "\n" +
                ", mediaPlayer3=" + mediaPlayer3 + "\n" +
                ", oof=" + oof + "\n" +
                ", videoPlayer=" + videoPlayer + "\n" +
                ", tmp='" + tmp + '\'' + "\n" +
                ", i=" + i + "\n" +
                ", MausMove=" + MausMove + "\n" +
                ", richtungX=" + richtungX + "\n" +
                ", richtnungY=" + richtnungY + "\n" +
                ", bewegungx=" + bewegungx + "\n" +
                ", DeusRichtungX=" + DeusRichtungX + "\n" +
                ", DeusRichtungY=" + DeusRichtungY + "\n" +
                ", Deusbewegungx=" + Deusbewegungx + "\n" +
                ", auswahlAutoMove2=" + auswahlAutoMove2 + "\n" +
                ", auswahlAutoMove3=" + auswahlAutoMove3 + "\n" +
                ", punkteRechts=" + punkteRechts + "\n" +
                ", punkteLinks=" + punkteLinks + "\n" +
                ", warteBisSpielBegin=" + warteBisSpielBegin + "\n" +
                ", start=" + start + "\n" +
                ", start2=" + start2 + "\n" +
                ", tmp1=" + tmp1 + "\n" +
                ", musikgeladen=" + musikgeladen + "\n" +
                ", IstZulaessigAutoMove2=" + IstZulaessigAutoMove2 + "\n" +
                ", WallBugFixMK1=" + WallBugFixMK1 + "\n" +
                ", main1=" + main1 + "\n" +
                ", windowMain=" + windowMain + "\n" +
                ", arr1=" + Arrays.toString(arr1) + "\n" +
                ", arrayListPane=" + arrayListPane + "\n" +
                ", xBackgound=" + xBackgound + "\n" +
                ", yBackgound=" + yBackgound + "\n" +
                ", multipikatorBackground=" + multipikatorBackground + "\n" +
                ", PaneVisibility=" + PaneVisibility + "\n" +
                ", jaNeinKP=" + jaNeinKP + "\n" +
                ", zähler=" + zähler + "\n" +
                ", arr2=" + Arrays.toString(arr2) + "\n" +
                ", arrayListLabel=" + arrayListLabel + "\n" +
                ", buttonumbennenung=" + buttonumbennenung + "\n" +
                ", ButtonSGS=" + ButtonSGS + "\n" +
                ", ButtonSGP=" + ButtonSGP + "\n" +
                ", ButtonPGP=" + ButtonPGP + "\n" +
                ", movingBox=" + movingBox + "\n" +
                ", MainAnchorPane=" + MainAnchorPane + "\n" +
                ", button1=" + button1 + "\n" +
                ", Label2=" + Label2 + "\n" +
                ", paneaBackground=" + paneaBackground + "\n" +
                ", InfoLabel=" + InfoLabel + "\n" +
                ", Label1Rechts=" + Label1Rechts + "\n" +
                ", Label3Links=" + Label3Links + "\n" +
                ", Label4Unten=" + Label4Unten + "\n" +
                ", Label5Top=" + Label5Top + "\n" +
                ", SpielerRechts=" + SpielerRechts + "\n" +
                ", SpielerLinks=" + SpielerLinks + "\n" +
                ", DeusVult=" + DeusVult + "\n" +
                ", volume=" + volume + "\n" +
                ", canves1=" + canves1 + "\n" +
                ", ComboBoxMusik=" + ComboBoxMusik + "\n" +
                ", Button2=" + Button2 + "\n" +
                ", StartButton=" + StartButton + "\n" +
                ", ButtonMinus=" + ButtonMinus + "\n" +
                ", ButtonPlus=" + ButtonPlus + "\n" +
                ", LauterLeiserSlider=" + LauterLeiserSlider + "\n" +
                ", MediaView1=" + MediaView1 + "\n" +
                ", Button3=" + Button3 + "\n" +
                '}';
    }

    //get Set Not Needed

    public boolean isW() {
        return w;
    }

    public void setW(boolean w) {
        this.w = w;
    }

    public boolean isS() {
        return s;
    }

    public void setS(boolean s) {
        this.s = s;
    }

    public ArrayList<Media> getHit1() {
        return hit1;
    }

    public void setHit1(ArrayList<Media> hit1) {
        this.hit1 = hit1;
    }

    public ArrayList<Media> getVid1() {
        return vid1;
    }

    public void setVid1(ArrayList<Media> vid1) {
        this.vid1 = vid1;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public MediaPlayer getMediaPlayer2() {
        return mediaPlayer2;
    }

    public void setMediaPlayer2(MediaPlayer mediaPlayer2) {
        this.mediaPlayer2 = mediaPlayer2;
    }

    public MediaPlayer getMediaPlayer3() {
        return mediaPlayer3;
    }

    public void setMediaPlayer3(MediaPlayer mediaPlayer3) {
        this.mediaPlayer3 = mediaPlayer3;
    }

    public MediaPlayer getOof() {
        return oof;
    }

    public void setOof(MediaPlayer oof) {
        this.oof = oof;
    }

    public MediaPlayer getVideoPlayer() {
        return videoPlayer;
    }

    public void setVideoPlayer(MediaPlayer videoPlayer) {
        this.videoPlayer = videoPlayer;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public boolean isMausMove() {
        return MausMove;
    }

    public void setMausMove(boolean mausMove) {
        MausMove = mausMove;
    }

    public double getRichtungX() {
        return richtungX;
    }

    public void setRichtungX(double richtungX) {
        this.richtungX = richtungX;
    }

    public double getRichtnungY() {
        return richtnungY;
    }

    public void setRichtnungY(double richtnungY) {
        this.richtnungY = richtnungY;
    }

    public double getBewegungx() {
        return bewegungx;
    }

    public void setBewegungx(double bewegungx) {
        this.bewegungx = bewegungx;
    }

    public double getDeusRichtungX() {
        return DeusRichtungX;
    }

    public void setDeusRichtungX(double deusRichtungX) {
        DeusRichtungX = deusRichtungX;
    }

    public double getDeusRichtungY() {
        return DeusRichtungY;
    }

    public void setDeusRichtungY(double deusRichtungY) {
        DeusRichtungY = deusRichtungY;
    }

    public double getDeusbewegungx() {
        return Deusbewegungx;
    }

    public void setDeusbewegungx(double deusbewegungx) {
        Deusbewegungx = deusbewegungx;
    }

    public int getAuswahlAutoMove2() {
        return auswahlAutoMove2;
    }

    public void setAuswahlAutoMove2(int auswahlAutoMove2) {
        this.auswahlAutoMove2 = auswahlAutoMove2;
    }

    public int getAuswahlAutoMove3() {
        return auswahlAutoMove3;
    }

    public void setAuswahlAutoMove3(int auswahlAutoMove3) {
        this.auswahlAutoMove3 = auswahlAutoMove3;
    }

    public int getPunkteRechts() {
        return punkteRechts;
    }

    public void setPunkteRechts(int punkteRechts) {
        this.punkteRechts = punkteRechts;
    }

    public int getPunkteLinks() {
        return punkteLinks;
    }

    public void setPunkteLinks(int punkteLinks) {
        this.punkteLinks = punkteLinks;
    }

    public int getWarteBisSpielBegin() {
        return warteBisSpielBegin;
    }

    public void setWarteBisSpielBegin(int warteBisSpielBegin) {
        this.warteBisSpielBegin = warteBisSpielBegin;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isStart2() {
        return start2;
    }

    public void setStart2(boolean start2) {
        this.start2 = start2;
    }

    public boolean isTmp1() {
        return tmp1;
    }

    public void setTmp1(boolean tmp1) {
        this.tmp1 = tmp1;
    }

    public boolean isMusikgeladen() {
        return musikgeladen;
    }

    public void setMusikgeladen(boolean musikgeladen) {
        this.musikgeladen = musikgeladen;
    }

    public boolean isIstZulaessigAutoMove2() {
        return IstZulaessigAutoMove2;
    }

    public void setIstZulaessigAutoMove2(boolean istZulaessigAutoMove2) {
        IstZulaessigAutoMove2 = istZulaessigAutoMove2;
    }

    public boolean isWallBugFixMK1() {
        return WallBugFixMK1;
    }

    public void setWallBugFixMK1(boolean wallBugFixMK1) {
        WallBugFixMK1 = wallBugFixMK1;
    }

    public Main getMain1() {
        return main1;
    }

    public void setMain1(Main main1) {
        this.main1 = main1;
    }

    public Stage getWindowMain() {
        return windowMain;
    }

    public void setWindowMain(Stage windowMain) {
        this.windowMain = windowMain;
    }

    public Pane[][] getArr1() {
        return arr1;
    }

    public void setArr1(Pane[][] arr1) {
        this.arr1 = arr1;
    }

    public ArrayList<Pane> getArrayListPane() {
        return arrayListPane;
    }

    public void setArrayListPane(ArrayList<Pane> arrayListPane) {
        this.arrayListPane = arrayListPane;
    }

    public int getxBackgound() {
        return xBackgound;
    }

    public void setxBackgound(int xBackgound) {
        this.xBackgound = xBackgound;
    }

    public int getyBackgound() {
        return yBackgound;
    }

    public void setyBackgound(int yBackgound) {
        this.yBackgound = yBackgound;
    }

    public int getMultipikatorBackground() {
        return multipikatorBackground;
    }

    public void setMultipikatorBackground(int multipikatorBackground) {
        this.multipikatorBackground = multipikatorBackground;
    }

    public boolean isPaneVisibility() {
        return PaneVisibility;
    }

    public void setPaneVisibility(boolean paneVisibility) {
        PaneVisibility = paneVisibility;
    }

    public boolean isJaNeinKP() {
        return jaNeinKP;
    }

    public void setJaNeinKP(boolean jaNeinKP) {
        this.jaNeinKP = jaNeinKP;
    }

    public int getZähler() {
        return zähler;
    }

    public void setZähler(int zähler) {
        this.zähler = zähler;
    }

    public Label[][] getArr2() {
        return arr2;
    }

    public void setArr2(Label[][] arr2) {
        this.arr2 = arr2;
    }

    public ArrayList<Label> getArrayListLabel() {
        return arrayListLabel;
    }

    public void setArrayListLabel(ArrayList<Label> arrayListLabel) {
        this.arrayListLabel = arrayListLabel;
    }

    public static int getAuswahl() {
        return auswahl;
    }

    public static void setAuswahl(int auswahl) {
        Controller.auswahl = auswahl;
    }

    public boolean isButtonumbennenung() {
        return buttonumbennenung;
    }

    public void setButtonumbennenung(boolean buttonumbennenung) {
        this.buttonumbennenung = buttonumbennenung;
    }

    public Button getButtonSGS() {
        return ButtonSGS;
    }

    public void setButtonSGS(Button buttonSGS) {
        ButtonSGS = buttonSGS;
    }

    public Button getButtonSGP() {
        return ButtonSGP;
    }

    public void setButtonSGP(Button buttonSGP) {
        ButtonSGP = buttonSGP;
    }

    public Button getButtonPGP() {
        return ButtonPGP;
    }

    public void setButtonPGP(Button buttonPGP) {
        ButtonPGP = buttonPGP;
    }

    public Label getMovingBox() {
        return movingBox;
    }

    public void setMovingBox(Label movingBox) {
        this.movingBox = movingBox;
    }

    public AnchorPane getMainAnchorPane() {
        return MainAnchorPane;
    }

    public void setMainAnchorPane(AnchorPane mainAnchorPane) {
        MainAnchorPane = mainAnchorPane;
    }

    public Button getButton1() {
        return button1;
    }

    public void setButton1(Button button1) {
        this.button1 = button1;
    }

    public Label getLabel2() {
        return Label2;
    }

    public void setLabel2(Label label2) {
        Label2 = label2;
    }

    public Pane getPaneaBackground() {
        return paneaBackground;
    }

    public void setPaneaBackground(Pane paneaBackground) {
        this.paneaBackground = paneaBackground;
    }

    public Label getInfoLabel() {
        return InfoLabel;
    }

    public void setInfoLabel(Label infoLabel) {
        InfoLabel = infoLabel;
    }

    public Label getLabel1Rechts() {
        return Label1Rechts;
    }

    public void setLabel1Rechts(Label label1Rechts) {
        Label1Rechts = label1Rechts;
    }

    public Label getLabel3Links() {
        return Label3Links;
    }

    public void setLabel3Links(Label label3Links) {
        Label3Links = label3Links;
    }

    public Label getLabel4Unten() {
        return Label4Unten;
    }

    public void setLabel4Unten(Label label4Unten) {
        Label4Unten = label4Unten;
    }

    public Label getLabel5Top() {
        return Label5Top;
    }

    public void setLabel5Top(Label label5Top) {
        Label5Top = label5Top;
    }


    public ImageView getDeusVult() {
        return DeusVult;
    }

    public void setDeusVult(ImageView deusVult) {
        DeusVult = deusVult;
    }

    public CheckBox getVolume() {
        return volume;
    }

    public void setVolume(CheckBox volume) {
        this.volume = volume;
    }

    public Canvas getCanves1() {
        return canves1;
    }

    public void setCanves1(Canvas canves1) {
        this.canves1 = canves1;
    }

    public ComboBox getComboBoxMusik() {
        return ComboBoxMusik;
    }

    public void setComboBoxMusik(ComboBox comboBoxMusik) {
        ComboBoxMusik = comboBoxMusik;
    }

    public Button getButton2() {
        return Button2;
    }

    public void setButton2(Button button2) {
        Button2 = button2;
    }

    public Button getStartButton() {
        return StartButton;
    }

    public void setStartButton(Button startButton) {
        StartButton = startButton;
    }

    public Button getButtonMinus() {
        return ButtonMinus;
    }

    public void setButtonMinus(Button buttonMinus) {
        ButtonMinus = buttonMinus;
    }

    public Button getButtonPlus() {
        return ButtonPlus;
    }

    public void setButtonPlus(Button buttonPlus) {
        ButtonPlus = buttonPlus;
    }

    public Slider getLauterLeiserSlider() {
        return LauterLeiserSlider;
    }

    public void setLauterLeiserSlider(Slider lauterLeiserSlider) {
        LauterLeiserSlider = lauterLeiserSlider;
    }

    public MediaView getMediaView1() {
        return MediaView1;
    }

    public void setMediaView1(MediaView mediaView1) {
        MediaView1 = mediaView1;
    }

    public Button getButton3() {
        return Button3;
    }

    public void setButton3(Button button3) {
        Button3 = button3;
    }

    public Label getAttLabel() {
        return AttLabel;
    }

    public void setAttLabel(Label attLabel) {
        AttLabel = attLabel;
    }

    public void setTastaturbewegung(boolean tastaturbewegung) {
        Tastaturbewegung = tastaturbewegung;
    }

    public boolean isA() {
        return a;
    }

    public void setA(boolean a) {
        this.a = a;
    }

    public boolean isD() {
        return d;
    }

    public void setD(boolean d) {
        this.d = d;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public static Label getMove() {
        return Move;
    }

    public static void setMove(Label move) {
        Move = move;
    }

    public static boolean isTastaturbewegung() {
        return Tastaturbewegung;
    }

    public Scene getScene3() {
        return scene3;
    }

    public void setScene3(Scene scene3) {
        this.scene3 = scene3;
    }

    public Label getSpielerRechts() {
        return SpielerRechts;
    }

    public void setSpielerRechts(Label spielerRechts) {
        SpielerRechts = spielerRechts;
    }

    public Label getSpielerLinks() {
        return SpielerLinks;
    }

    public void setSpielerLinks(Label spielerLinks) {
        SpielerLinks = spielerLinks;
    }
}