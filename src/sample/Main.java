package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;//import fxmlloader from javafx
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static Stage stage = null;

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXML/welcome.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        this.stage = stage;
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
