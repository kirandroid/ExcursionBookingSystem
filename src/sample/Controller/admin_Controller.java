package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Main;

import java.io.IOException;

public class admin_Controller {
    @FXML
    private AnchorPane adminPane, adminPane_TablePane;

    @FXML
    private Label adminPane_Greeting;

    @FXML
    private void Minimize_App(MouseEvent event){
        Main.stage.setIconified(true);
    }
    @FXML
    private void Close_App(MouseEvent event){
        System.exit(0);
    }

    @FXML
    private void LogOut(MouseEvent event) throws IOException {
        sample.Controller.welcome_controller welcomeController;
        welcomeController = new sample.Controller.welcome_controller();
        welcomeController.isLoggedIn =false;
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/welcome.fxml"));
        adminPane.getChildren().setAll(pane);
    }

    @FXML
    void adminPane_UserClicked(ActionEvent event) throws IOException{
        adminPane_Greeting.setVisible(false);
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/adminUser.fxml"));
        adminPane_TablePane.getChildren().setAll(pane);
    }

    @FXML
    void adminPane_BookingClicked(ActionEvent event) throws IOException{
        adminPane_Greeting.setVisible(false);
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/adminBooking.fxml"));
        adminPane_TablePane.getChildren().setAll(pane);
    }
}
