package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;

public class Login_Register_Controller{
    @FXML
    private TextField Login_Username;

    @FXML
    private PasswordField Login_Password;

    public void login_registerButton(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Register.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Register");
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void Close_App(MouseEvent event){
        System.exit(0);
    }

    public void LoginButtonClicked(){
        try{
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration", "root", "");
            String sql = "SELECT * from user_reg where Email=? and Password = ?";
            PreparedStatement mySt = myConn.prepareStatement(sql);
            mySt.setString(1, Login_Username.getText());
            mySt.setString(2, Login_Password.getText());
            ResultSet rs = mySt.executeQuery();
            if(rs.next()){
                System.out.println("Sucessfully logged in!");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Profile");
                stage.setScene(new Scene(root1));
                stage.show();
                //set text to main controller
//                profile_controller profile_controller = fxmlLoader.getController();
//                profile_controller.settext(Login_Username.getText());
            }
            else
                System.out.println("Incorrect Details");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
