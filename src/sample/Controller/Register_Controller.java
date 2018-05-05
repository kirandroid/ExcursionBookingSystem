package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

public class Register_Controller {

    @FXML
    private void Minimize_App(MouseEvent event){
        Main.stage.setIconified(true);
    }
    @FXML
    private void Close_App(MouseEvent event){
        System.exit(0);
    }

    @FXML
    private void BackTo_Login(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Login.fxml"));
        Main.stage.getScene().setRoot(root);
    }

}
