package Berührung;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class Scene2Controller {
    @FXML
    Button Button4;
    private Main main1;

    public void SceneWechsel2() throws Exception {

        Parent FXMLDING2 = FXMLLoader.load(getClass().getResource("Berührung.fxml"));
        Scene scene3 = new Scene(FXMLDING2);
        Stage window = main1.getS1();
        window.setScene(scene3);
        window.show();

    }
}
