package Berührung;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    private static Stage s1;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));

        primaryStage.setTitle("SuperTollesSpiel2");
//        primaryStage.setMaxHeight(500);
//        primaryStage.setMaxWidth(750);
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(750);
        primaryStage.setScene(new Scene(root, 750, 500));
        primaryStage.requestFocus();
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreen(true);
        primaryStage.show();
        s1 = primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getS1() {
        return s1;
    }
}
