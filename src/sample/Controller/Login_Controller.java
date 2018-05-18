package sample.Controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Main;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.sql.*;
import java.util.ResourceBundle;

public class Login_Controller implements Initializable {
    public static String loggedInFirstName, loggedInID, loggedUsername;

    @FXML
    private void Minimize_App(MouseEvent event) {
        Main.stage.setIconified(true);
    }

    @FXML
    private JFXTextField Login_Username;

    @FXML
    private Label ErrorLogin_Label;

    @FXML
    private JFXPasswordField Login_Password;

    @FXML
    private AnchorPane Login_pane, login_rootpane;

    @FXML
    private void Close_App(MouseEvent event) {
        System.exit(0);
    }

    //logs in user on button clicked, yeah!
    public void LoginButtonClicked() {
        try {
            loggedUsername = Login_Username.getText();

            //This code changes the password entered by user to a hashed SHA1 text.
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(Login_Password.getText().getBytes("UTF-8"), 0, Login_Password.getText().length());
            String encriptedPassword = DatatypeConverter.printHexBinary(messageDigest.digest());
            //The hashed text will then be compared to the password in the database.

            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/group-8", "root", "");
            String sql = "SELECT * from registration where Email=? and Password = ? and Role = ?";
            PreparedStatement myStUser = myConn.prepareStatement(sql);

            myStUser.setString(1, Login_Username.getText());
            myStUser.setString(2, encriptedPassword);
            myStUser.setString(3, "User");
            ResultSet rs = myStUser.executeQuery();

            PreparedStatement myStAdmin = myConn.prepareStatement(sql);
            myStAdmin.setString(1, Login_Username.getText());
            myStAdmin.setString(2, encriptedPassword);
            myStAdmin.setString(3, "Admin");
            ResultSet rsAd = myStAdmin.executeQuery();

            //Checks for user login, if matched changes the scene to user profile
            if (rs.next()) {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/profile.fxml"));
                login_rootpane.getChildren().setAll(pane);
                sample.Controller.welcome_controller welcomeController;
                welcomeController = new sample.Controller.welcome_controller();
                welcomeController.isLoggedIn = true;
                Statement statement = myConn.createStatement();
                ResultSet firstnameset = statement.executeQuery("SELECT `First Name`, `ID` FROM `registration` WHERE `Email` ='" + Login_Username.getText() + "'");
                while (firstnameset.next()) {
                    loggedInFirstName = firstnameset.getString("First Name");
                    loggedInID = firstnameset.getString("ID");
                }
            }
            //Checks for Admin login, if matched changes the scene to Admin panel
            else if (rsAd.next()) {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/Admin_Panel.fxml"));
                login_rootpane.getChildren().setAll(pane);
            } else
                ErrorLogin_Label.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //function to change the scene to Register scene
    public void goTo_Register() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/Register.fxml"));
        Login_pane.getChildren().setAll(pane);

    }

    //function to go back to the dashboard
    @FXML
    private void backTo_Home(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/welcome.fxml"));
        Main.stage.getScene().setRoot(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Login_Password.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                LoginButtonClicked();
            }
        });
    }
}
