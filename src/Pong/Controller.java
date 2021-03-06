package Pong;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.*;
import java.util.Timer;

public class Controller {
    private ArrayList<Media> hit1 = new ArrayList<>();
    private ArrayList<Media> vid1 = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private int i = 0;                  //Wird für die Generierung von Panes gebraucht BackgroundMovementLoad();

    //MoveBox
    private double richtungX = -0.01; //Eventuell noch ändern unten
    private double richtnungY = 0.01;
    private double bewegungx = 50;      //Geschwindigkeit

    //Weiterer sich bewegender Block unbeeinflusst von Spielern
    private int auswahlAutoMove2 = 0;   //Wird für die Überprüfung von der Spieler Position 
    private int auswahlAutoMove3 = 0;   //gebraucht damit der Würfel(SpielBall) nicht über den rand geht.
    private boolean IstZulaessigAutoMove2 = true;
    private boolean WallBugFixMK1 = true;
    private static int punkteRechts = 0;   //Punkte Rechts gewinnbringende Punkte
    private static int punkteLinks = 0;    //Punkte Links ""
    private boolean start = false; //Wiederholungs blocker damit der Start button nicht immer wieder das Spiel neu startet bzw. Schleifen und Thread mehrfach angesprochen werden
    private boolean start2 = false;

    //MK1
    private Main main1;// Ermöglicht stage wechsel
    private Stage windowMain;   //""


    //Labels die im hintergrund verwendet werden können
    private Label[][] arr2 = new Label[200][200];
    private ArrayList<Label> arrayListLabel = new ArrayList<Label>();

    //Menue mit überprüfung auf wiederholungen
    private static int auswahl;
    private boolean buttonumbennenung = true;

    //Bewegung Tastatur
    private boolean w = false;
    private boolean s = false;
    private boolean a = false; // Eigentlich O
    private boolean d = false; // Eigentlich L

    private double x; // Move  Position des Beweglichen teils
    private double y = 290;
    private double x2 = 985; // Move2 Position des 2 Beweglichen teils
    private double y2 = 290;
    private static Label Move = new Label();    //Anlegen der Beweglichen teile
    private static Label Move2 = new Label();
    private static boolean Tastaturbewegung = false; //Überprüfung ob 2 oder 1 Spieler
    private static boolean Tastaturbewegung2Spieler = false;

    //Hintergrundbewegungen
    private int xZähler = 0;
    private int yZähler = 3;
    private boolean setVisible = true;


    //Sieg
    private static boolean FinalerBool = true;

    //Scene HauptGame.fxml
    private Scene scene3;

    //FXML eingaben
    @FXML
    Button ButtonSGS; //S = Script P = Player
    @FXML
    Button ButtonSGP;
    @FXML
    Button ButtonPGP;

    @FXML
    Label movingBox;    // SpielBall
    @FXML
    AnchorPane MainAnchorPane; //HauptPane
    @FXML
    Label Label2; // Punkte anzeige + Geschwindigkeit
    @FXML
    Pane paneaBackground; // Packgroundpane
    @FXML
    Label InfoLabel;
    //Ränder Wiederstände Kollision mit dem Ball
    @FXML
    Label Label1Rechts;
    @FXML
    Label Label3Links;
    @FXML
    Label Label4Unten;
    @FXML
    Label Label5Top;
    //Spieler
    @FXML
    Label SpielerRechts;
    @FXML
    Label SpielerLinks;
    //Image View Deko
    @FXML
    ImageView DeusVult;
    //Volume Musik / Video
    @FXML
    CheckBox volume;
    //Musik auswahl
    @FXML
    ComboBox ComboBoxMusik;
    @FXML
    Button Button2;
    //Start button
    @FXML
    Button StartButton;
    //Lautstärke regler
    @FXML
    Slider LauterLeiserSlider;
    //Media View Neon Cat //TODO
    @FXML
    MediaView MediaView1;
    @FXML
    Button Button3;
    @FXML
    Label AttLabel;
    //end scene
    @FXML
    ImageView endScreen;
    @FXML
    Button retry;

    //Start Methoden

    public void Laden() {
        if (!start2) {
            MainAnchorPane.getStyleClass().add("root");
            MusikLaden();
            BackgroundMovementLoad();
            BackGroundMovementMuster(2);
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
                    AutoMove2();
                    movePingPong();
                    start = true;
                } else {
                    movingBox.setLayoutY(350);
                    movingBox.setLayoutX(500);
                }
                break;
            case 2: //Script gegen Player
                StartButton.setText("Rest Ball/Cube");
                if (!start) {
                    System.out.println("SpielModus 2");
                    setTastaturbewegung(true);
                    Move.relocate(SpielerLinks.getLayoutX(), SpielerLinks.getLayoutY());
                    Move.setMaxWidth(SpielerLinks.getMaxWidth());
                    Move.setMaxHeight(SpielerLinks.getMaxHeight());
                    Move.setMinWidth(SpielerLinks.getMinWidth());
                    Move.setMinHeight(SpielerLinks.getMinHeight());
                    MainAnchorPane.getChildren().add(Move);
                    OnePlayer();
                    movePingPong();
                    AttLabel.setText("Links_ " + SpielerLinks + " Rechts_ " + SpielerRechts);
                    start = true;
                } else {
                    movingBox.setLayoutY(500);
                    movingBox.setLayoutX(450);
                }
                break;
            case 3: //Player gegen Player
                StartButton.setText("Rest Ball/Cube");

                if (!start) {
                    Tastaturbewegung2Spieler = true;
                    Tastaturbewegung = true;
                    Move.relocate(SpielerLinks.getLayoutX(), SpielerLinks.getLayoutY());
                    Move.setMaxWidth(SpielerLinks.getMaxWidth());
                    Move.setMaxHeight(SpielerLinks.getMaxHeight());
                    Move.setMinWidth(SpielerLinks.getMinWidth());
                    Move.setMinHeight(SpielerLinks.getMinHeight());
                    MainAnchorPane.getChildren().add(Move);
                    Move2.relocate(SpielerRechts.getLayoutX(), SpielerRechts.getLayoutY());
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
                                if (getPunkteRechts() >= 5) {
                                    try {
                                        //System.out.println("SceneWechsel");
                                        EndSceneSwitch2P(1); //Lose
                                    } catch (Exception ex) {
                                    }
                                    timer.cancel();
                                }
                                if (getPunkteLinks() >= 5) {
                                    try {
                                        //System.out.println("SceneWechsel");
                                        EndSceneSwitch2P(0); //Win

                                    } catch (Exception ex) {
                                    }
                                    timer.cancel();
                                }
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

    public void AutoMove2() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    IstZulaessigAutoMove2 = true;
                    if (SpielerLinks.getLayoutY() > 800) {
                        auswahlAutoMove2 = 1;
                        IstZulaessigAutoMove2 = false;
                    }
                    if (SpielerLinks.getLayoutY() < 0) {
                        auswahlAutoMove2 = 2;
                        IstZulaessigAutoMove2 = false;
                    }
                    if (movingBox.getLayoutX() < 250 && richtungX == -0.01) {
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
                                SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() - 4);
                                auswahlAutoMove2 = 0;
                            } else {
                                SpielerLinks.setLayoutY(SpielerLinks.getLayoutY() + 4);
                                auswahlAutoMove2 = 0;
                            }
                            break;
                    }

                    IstZulaessigAutoMove2 = true;
                    if (SpielerRechts.getLayoutY() > 800) {
                        auswahlAutoMove3 = 1;
                        IstZulaessigAutoMove2 = false;
                    }
                    if (SpielerRechts.getLayoutY() < 0) {
                        auswahlAutoMove3 = 2;
                        IstZulaessigAutoMove2 = false;
                    }
                    if (movingBox.getLayoutX() > 750 && richtungX == 0.01) {
                        if (IstZulaessigAutoMove2) {
                            auswahlAutoMove3 = 3;
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
                                    SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() - 4);
                                    auswahlAutoMove3 = 0;
                                } else {
                                    SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() + 4);
                                    auswahlAutoMove3 = 0;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        }, 0, 5);
    }

    public void SchleifeTasteGedrückt() throws IOException {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    //Hier wird überprüft ob
                    if (isW()) {
                        if (Tastaturbewegung && !(Move.getLayoutY() < 0)) {
                            Move.relocate(x, y);
                            y -= 2;
                        }
                    }
                    if (isS()) {
                        if (Tastaturbewegung && !(Move.getLayoutY() > 585)) {
                            Move.relocate(x, y);
                            y += 2;
                        }
                    }
                    if (isA()) { // Up
                        if (Tastaturbewegung2Spieler && !(Move2.getLayoutY() < 0)) {
                            Move2.relocate(x2, y2);
                            y2 -= 2;
                        }
                    }
                    if (isD()) { // Down
                        if (Tastaturbewegung2Spieler && !(Move2.getLayoutY() > 585)) {
                            Move2.relocate(x2, y2);
                            y2 += 2;
                        }
                    }
                });
            }
        }, 0, 4);
    }

    public void OnePlayer() {
        setPunkteLinks(0);
        setPunkteRechts(0);
        Timer timer2 = new Timer();
        timer2.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    SpielerLinks.relocate(Move.getLayoutX(), Move.getLayoutY());
                    Label2.setText("Punkte R: " + String.valueOf(punkteRechts + " Punkte L: " + punkteLinks));
                    if (FinalerBool) {

                        if (getPunkteRechts() >= 5) {
                            try {
                                //System.out.println("SceneWechsel");
                                EndSceneSwitch(1); //Lose
                            } catch (Exception ex) {
                            }
                            timer2.cancel();
                        }

                        if (getPunkteLinks() >= 5) {
                            try {
                                //System.out.println("SceneWechsel");
                                EndSceneSwitch(0); //Win

                            } catch (Exception ex) {
                            }
                            timer2.cancel();
                        }
                    }

                    IstZulaessigAutoMove2 = true;

                    if (SpielerRechts.getLayoutY() > 800) {
                        auswahlAutoMove3 = 1;
                        IstZulaessigAutoMove2 = false;
                    }
                    if (SpielerRechts.getLayoutY() < 0) {
                        auswahlAutoMove3 = 2;
                        IstZulaessigAutoMove2 = false;
                    }

                    if (movingBox.getLayoutX() > 750 && richtungX == 0.01) {
                        if (movingBox.getLayoutX() > 750 && richtungX == 0.01) {
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
                                    SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() - 4);

                                    auswahlAutoMove3 = 0;
                                } else {
                                    SpielerRechts.setLayoutY(SpielerRechts.getLayoutY() + 4);

                                    auswahlAutoMove3 = 0;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        }, 0, 5);
    }

    public void movePingPong() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    //AttLabel.setText(String.valueOf(punkteRechts));
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
                                setPunkteLinks(getPunkteLinks() + 1);
                                Label2.setText("Punkte R: " + String.valueOf(punkteRechts + " Punkte L: " + punkteLinks));
                                movingBox.setLayoutX(500);
                                movingBox.setLayoutY(350);
                                richtungX *= -1;
                                break;
                            case 2:
                                int y = getPunkteLinks();
                                setPunkteRechts(getPunkteRechts() + 1);
                                Label2.setText("Punkte R: " + String.valueOf(punkteRechts + " Punkte L: " + punkteLinks));
                                movingBox.setLayoutX(500);
                                movingBox.setLayoutY(350);
                                richtungX *= -1;
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
                                break;
                            case 6:
                                richtungX *= -1;
                                movingBox.setLayoutX(movingBox.getLayoutX() + (bewegungx * richtungX));
                                movingBox.setLayoutY(movingBox.getLayoutY() + (bewegungx * richtnungY));
                                break;
                            default:
                                movingBox.setLayoutX(movingBox.getLayoutX() + (bewegungx * richtungX));
                                movingBox.setLayoutY(movingBox.getLayoutY() + (bewegungx * richtnungY));
                                break;
                        }
                    }
                });
            }
        }, 0, 1);
    }

    //Methoden die gebraucht werden Kollision oder RandomeInts


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
            hit1.add(new Media(getClass().getClassLoader().getResource("Musik/Numb.mp3").toURI().toString())); //5
            hit1.add(new Media(getClass().getClassLoader().getResource("Musik/movie_1.mp3").toURI().toString())); //6
            for (Media m :
                    hit1) {
                System.out.println(m.getSource());
            }
            ObservableList<String> options =
                    FXCollections.observableArrayList(
                            "Anime",
                            "Chinese Rap",
                            "Darude",
                            "Hilariouse Chinese",
                            "Numb"
                    );

            ComboBoxMusik.setItems(options);
            ComboBoxMusik.setVisible(true);
        } catch (Throwable into_the_garbage_bin) {
            into_the_garbage_bin.printStackTrace();
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


    //Scenen und Exit

    public void SceneWechsel() throws Exception {
        Parent FXMLDING = FXMLLoader.load(getClass().getResource("FXML/Scene2.fxml"));
        Scene scene2 = new Scene(FXMLDING);
        Stage window = main1.getS1();
        window.setScene(scene2);
        window.show();

        punkteRechts = 0;
        punkteLinks = 0;

        try {
            mediaPlayer.stop();
        } catch (Exception ex) {
        }
    }

    public void SceneWechsel2() throws Exception {
        Parent FXMLDING2 = FXMLLoader.load(getClass().getResource("FXML/HauptGame.fxml"));
        scene3 = new Scene(FXMLDING2);
        Stage window = main1.getS1();
        window.setScene(scene3);
        window.show();

        punkteRechts = 0;
        punkteLinks = 0;

        scene3.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.W)
                setW(true);
            if (event.getCode() == KeyCode.S)
                setS(true);
            if (event.getCode() == KeyCode.O)
                setA(true);
            if (event.getCode() == KeyCode.L)
                setD(true);
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


    public void EndSceneSwitch(int end) throws Exception {
        Parent endFXML = null;
        if (end == 1) {
            endFXML = FXMLLoader.load(getClass().getResource("FXML/EndSceneLose.fxml"));
        }
        if (end == 0) {
            endFXML = FXMLLoader.load(getClass().getResource("FXML/EndSceneWin.fxml"));
        }

        Scene endscene = new Scene(endFXML);
        Stage endStage = main1.getS1();
        endStage.setScene(endscene);

        endStage.show();
    }

    public void EndSceneSwitch2P(int end) throws Exception {
        Parent endFXML = null;
        if (end == 1) { //Rechts
            endFXML = FXMLLoader.load(getClass().getResource("FXML/EndSceneRechts.fxml"));
        }
        if (end == 0) { //Links
            endFXML = FXMLLoader.load(getClass().getResource("FXML/EndSceneLinks.fxml"));
        }

        Scene endscene = new Scene(endFXML);
        Stage endStage = main1.getS1();
        endStage.setScene(endscene);

        endStage.show();
    }

    public void Exit() {
        System.exit(0);
    }

    //BackGround

    public void BackgroundMovementLoad() {
        Pane p1 = paneaBackground;
        int xWert2 = 150;
        int yWert2 = 0;

        //Erzeugung von
        for (int x = 0; x < 18; x++) {
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
                arr2[x][y].setText("");
                arr2[x][y].setFont(new Font(10));
                String felder = "Zeile " + (x + 1) + " Spalte " + (y + 1) + " (Index[" + x + "][" + y + "]) Feld " + i;
                arr2[x][y].setId(felder);
                arrayListLabel.add(arr2[x][y]);
                arr2[x][y].setVisible(false);
                p1.getChildren().add(arr2[x][y]);
                yWert2 += 40;
            }
            yWert2 = 0;
            xWert2 += 40;
        }
    }

    public void BackGroundMovementRandomeLabel() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    //Aus dem Array werden einzelne Labels genommen und sichtbar gesetzt
                    arr2[getRandomNumberInRange(0, 3)][getRandomNumberInRange(0, 17)].setVisible(true);
                });
            }
        }, 0, 1000);

        Timer timer2 = new Timer();
        timer2.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    //Aus dem Array werden einzelne Labels genommen und unsichtbar gesetzt
                    arr2[getRandomNumberInRange(0, 3)][getRandomNumberInRange(0, 17)].setVisible(false);
                });
            }
        }, 0, 100);
    }


    public void BackGroundMovementMuster(int form) {
        Timer delay = new Timer();
        Timer delay2 = new Timer();
        switch (form) {
            case 1:
                delay.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            arr2[xZähler][yZähler].setVisible(setVisible);
                            if (yZähler == 16) {
                                xZähler++;
                                yZähler = 0;
                            } else {
                                yZähler++;
                            }
                            if (xZähler == 4) {
                                xZähler = 0;
                                yZähler = 0;
                                if (setVisible) {
                                    setVisible = false;
                                } else {
                                    setVisible = true;
                                }
                            }
                        });
                    }
                }, 0, 250);
                break;

            case 2:
                delay.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            for (int j = 0; j < 12; j++) {
                                arr2[xZähler][yZähler].setVisible(setVisible);
                                switch (xZähler) {
                                    case 1:
                                        arr2[xZähler][yZähler].setStyle("-fx-background-color: #c40012;");
                                        break;
                                    case 2:
                                        arr2[xZähler][yZähler].setStyle("-fx-background-color: #c40077;");
                                        break;
                                    case 3:
                                        arr2[xZähler][yZähler].setStyle("-fx-background-color: #0019c4;");
                                        break;
                                    case 4:
                                        arr2[xZähler][yZähler].setStyle("-fx-background-color: #53c419;");
                                        break;
                                    case 5:
                                        arr2[xZähler][yZähler].setStyle("-fx-background-color: #00c4c1;");
                                        break;
                                    case 6:
                                        arr2[xZähler][yZähler].setStyle("-fx-background-color: #fff100;");
                                        break;
                                    case 7:
                                        arr2[xZähler][yZähler].setStyle("-fx-background-color: #00360b;");
                                        break;
                                    case 8:
                                        arr2[xZähler][yZähler].setStyle("-fx-background-color: #ff00ed;");
                                        break;
                                    case 9:
                                        arr2[xZähler][yZähler].setStyle("-fx-background-color: #ff5700;");
                                        break;
                                    case 10:
                                        arr2[xZähler][yZähler].setStyle("-fx-background-color: #8c0275;");
                                        break;
                                    case 11:
                                        arr2[xZähler][yZähler].setStyle("-fx-background-color: #412875;");
                                        break;
                                    case 12:
                                        arr2[xZähler][yZähler].setStyle("-fx-background-color: #023b4b;");
                                        break;
                                    case 13:
                                        arr2[xZähler][yZähler].setStyle("-fx-background-color: #ff866c;");
                                        break;
                                    case 14:
                                        arr2[xZähler][yZähler].setStyle("-fx-background-color: #4c0608;");
                                        break;
                                    case 15:
                                        arr2[xZähler][yZähler].setStyle("-fx-background-color: #002a2b;");
                                        break;
                                    case 16:
                                        arr2[xZähler][yZähler].setStyle("-fx-background-color: #385500;");
                                        break;
                                    case 17:
                                        arr2[xZähler][yZähler].setStyle("-fx-background-color: #041e3b;");
                                        break;
                                    case 18:
                                        arr2[xZähler][yZähler].setStyle("-fx-background-color: #fff100;");
                                        break;

                                }
                                yZähler++;
                            }
                            xZähler++;
                            if (yZähler == 15) {
                                yZähler = 3;
                            }
                            if (xZähler == 17) {
                                xZähler = 0;
                                if (setVisible) {
                                    setVisible = false;
                                } else {
                                    setVisible = true;
                                }
                            }

                        });
                    }
                }, 0, 250);

                break;
        }
    }

    //Get Set

    public static int getPunkteRechts() {
        return punkteRechts;
    }

    public static void setPunkteRechts(int punkteRechts) {
        Controller.punkteRechts = punkteRechts;
    }

    public static int getPunkteLinks() {
        return punkteLinks;
    }

    public static void setPunkteLinks(int punkteLinks) {
        Controller.punkteLinks = punkteLinks;
    }

    private boolean isW() {
        return w;
    }

    private void setW(boolean w) {
        this.w = w;
    }

    private boolean isS() {
        return s;
    }

    private void setS(boolean s) {
        this.s = s;
    }

    private boolean isWallBugFixMK1() {
        return WallBugFixMK1;
    }

    private void setWallBugFixMK1(boolean wallBugFixMK1) {
        WallBugFixMK1 = wallBugFixMK1;
    }

    private void setTastaturbewegung(boolean tastaturbewegung) {
        Tastaturbewegung = tastaturbewegung;
    }

    private boolean isA() {
        return a;
    }

    private void setA(boolean a) {
        this.a = a;
    }

    private boolean isD() {
        return d;
    }

    private void setD(boolean d) {
        this.d = d;
    }
}