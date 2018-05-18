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
import java.sql.*;
import java.util.ResourceBundle;

public class profile_controller implements Initializable {
    public static String UserID, UserFName, UserLName, UserEmail, UserCabin, UserGender;

    @FXML
    private AnchorPane profilepane, Booking_Pane;

    @FXML
    public Label Profile_DisplayName;

    @FXML
    private void Minimize_App(MouseEvent event) {
        Main.stage.setIconified(true);
    }

    @FXML
    private void Close_App(MouseEvent event) {
        System.exit(0);
    }

    public void Registration_Info() {
        sample.Controller.Login_Controller login_controller;
        login_controller = new sample.Controller.Login_Controller();
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/group-8", "root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT `ID`,`First Name`,`Last Name`,`Email`,`Cabin Number`,`Gender` FROM `registration` WHERE `Email` ='" + login_controller.loggedUsername + "'");
            while (rs.next()) {
                UserID = rs.getString("ID");
                UserFName = rs.getString("First Name");
                UserLName = rs.getString("Last Name");
                UserEmail = rs.getString("Email");
                UserCabin = rs.getString("Cabin Number");
                UserGender = rs.getString("Gender");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        welcomeController.isLoggedIn = false;
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/welcome.fxml"));
        profilepane.getChildren().setAll(pane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Registration_Info();
        Profile_DisplayName.setText("Hello, " + UserFName + "!");
    }
}
