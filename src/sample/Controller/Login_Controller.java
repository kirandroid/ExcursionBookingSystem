package sample.Controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Main;

import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.*;

public class Login_Controller {
    public static String loggedInFirstName, loggedInID, loggedUsername;

    @FXML
    private void Minimize_App(MouseEvent event){
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
    private void Close_App(MouseEvent event){
        System.exit(0);
    }

    public void LoginButtonClicked(){
        try{
            loggedUsername = Login_Username.getText();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(Login_Password.getText().getBytes("UTF-8"), 0, Login_Password.getText().length());
            String encriptedPassword = DatatypeConverter.printHexBinary(messageDigest.digest());

            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
            String sql = "SELECT * from registration where Email=? and Password = ? and Role = ?";
            PreparedStatement mySt = myConn.prepareStatement(sql);
            mySt.setString(1, Login_Username.getText());
            mySt.setString(2, encriptedPassword);
            mySt.setString(3, "User");
            ResultSet rs = mySt.executeQuery();

            PreparedStatement myStAdmin = myConn.prepareStatement(sql);
            myStAdmin.setString(1, Login_Username.getText());
            myStAdmin.setString(2, encriptedPassword);
            myStAdmin.setString(3, "Admin");
            ResultSet rsAd = myStAdmin.executeQuery();

            if(rs.next()){
                System.out.println("Sucessfully logged in!");
                AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/profile.fxml"));
                login_rootpane.getChildren().setAll(pane);
                sample.Controller.welcome_controller welcomeController;
                welcomeController = new sample.Controller.welcome_controller();
                welcomeController.isLoggedIn =true;
                Statement statement = myConn.createStatement();
                ResultSet firstnameset = statement.executeQuery("SELECT `First Name`, `ID` FROM `registration` WHERE `Email` ='"+Login_Username.getText()+"'");
                while (firstnameset.next()) {
                    loggedInFirstName = firstnameset.getString("First Name");
                    loggedInID = firstnameset.getString("ID");
                }
            }
            else if (rsAd.next()){
                AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/Admin_Panel.fxml"));
                login_rootpane.getChildren().setAll(pane);
            }
            else
                ErrorLogin_Label.setVisible(true);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void change_to_register() throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/Register.fxml"));
        Login_pane.getChildren().setAll(pane);

    }

    @FXML
    private void BackTo_Home(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/welcome.fxml"));
        Main.stage.getScene().setRoot(root);
    }

}
