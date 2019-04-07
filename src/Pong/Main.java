package Pong;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
    private static Stage s1;
    private static Parent root;
    private Scene sc;
    private static Controller c1;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("FXML/Scene2.fxml"));
        primaryStage.setTitle("Pong");
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(750);
        sc = new Scene(root, 1000, 700);
        primaryStage.setScene(sc);
        primaryStage.requestFocus();
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.setFullScreenExitHint("");
        primaryStage.getIcons().add(new Image(getClass().getResource("/Bilder/pongicon.png").toString()));
        primaryStage.setResizable(false);
        primaryStage.show();
        s1 = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    public static Stage getS1() {
        return s1;
    }
    public Parent getRoot() {
        return root;
    }
    public Scene getSc() {
        return sc;
    }

}
