package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class profile_controller implements Initializable {
    @FXML
    private AnchorPane profilepane, Booking_Pane;

    @FXML
    public Label Profile_DisplayName;

    @FXML
    private void Minimize_App(MouseEvent event){
        Main.stage.setIconified(true);
    }
    @FXML
    private void Close_App(MouseEvent event){
        System.exit(0);
    }


    @FXML
    private void BackTo_Home(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/welcome.fxml"));
        profilepane.getChildren().setAll(pane);
    }

    @FXML
    private void BackTo_MyBooking(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/my bookings.fxml"));
        Booking_Pane.getChildren().setAll(pane);
    }

    @FXML
    private void BackTo_waitinglist(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/Waiting List.fxml"));
        Booking_Pane.getChildren().setAll(pane);
    }

    @FXML
    private void LogOut(MouseEvent event) throws IOException {
        sample.Controller.welcome_controller welcomeController;
        welcomeController = new sample.Controller.welcome_controller();
        welcomeController.isLoggedIn =false;
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/welcome.fxml"));
        profilepane.getChildren().setAll(pane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sample.server server;
        server = new sample.server();
        server.Registration_Info();

        Profile_DisplayName.setText("Hello, "+server.UserFName+"!");
    }
}
