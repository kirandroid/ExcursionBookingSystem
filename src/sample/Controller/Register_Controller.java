package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Main;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Register_Controller {
    @FXML
    private TextField Register_Email, Register_FirstName, Register_LastName, Register_CabinNo;

    @FXML
    private PasswordField Register_Password;


    public void Register_Login_Text_Clicked(){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(Register_Password.getText().getBytes("UTF-8"), 0, Register_Password.getText().length());
            String encriptedPassword = DatatypeConverter.printHexBinary(messageDigest.digest());
            if(Register_FirstName.getText().isEmpty() || Register_LastName.getText().isEmpty() || Register_Email.getText().isEmpty() || Register_Password.getText().isEmpty() || Register_CabinNo.getText().isEmpty()){
                System.out.println("Error Please fill out all the TextFields");
            }
            else{
                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
                String sql = "INSERT INTO `registration`(`First Name`, `Last Name`, `Email`, `Password`, `Cabin Number`,`Joined Date`)"+"values(?,?,?,?,?,current_timestamp)";
                PreparedStatement mySt = myConn.prepareStatement(sql);
                mySt.setString(1, Register_FirstName.getText());
                mySt.setString(2, Register_LastName.getText());
                mySt.setString(3, Register_Email.getText());
                mySt.setString(4, encriptedPassword);
                mySt.setString(5, Register_CabinNo.getText());
                mySt.executeUpdate();
                mySt.close();
                System.out.println("Data sucessfully Inserted");
                Register_FirstName.clear();
                Register_LastName.clear();
                Register_Email.clear();
                Register_Password.clear();
                Register_CabinNo.clear();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

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
