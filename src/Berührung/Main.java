package Berührung;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


public class Main extends Application {
    private static Stage s1;
    private static Parent root;
    private Scene sc;
    private static Controller c1;
    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));

        primaryStage.setTitle("SuperTollesSpiel2");
//        primaryStage.setMaxHeight(500);
//        primaryStage.setMaxWidth(750);
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(750);
        sc = new Scene(root, 1000, 700);
        primaryStage.setScene(sc);
        primaryStage.requestFocus();
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.setFullScreenExitHint("");
        //primaryStage.setFullScreen(true);
        primaryStage.show();


//        sc.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.W)
//                c1.setW(true);
//            if (event.getCode() == KeyCode.S)
//                c1.setS(true);
//
//
//        });
//
//        sc.setOnKeyReleased(event -> {
//            if (event.getCode() == KeyCode.W)
//                c1.setW(false);
//            if (event.getCode() == KeyCode.S)
//                c1.setW(false);
//
//        });

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
