package sample.Controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSnackbar;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;
import sample.Main;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register_Controller implements Initializable{
    private String gendertxt;
    @FXML
    private TextField Register_Email, Register_FirstName, Register_LastName, Register_CabinNo;

    @FXML
    private PasswordField Register_Password;


    @FXML
    private Button Register_Button;

    @FXML
    private JFXCheckBox termsCondition;

    @FXML
    private Label Error_Email, Error_Name, Error_Empty, Error_Password, Error_EmailExist;

    @FXML
    private JFXRadioButton checkBoxFemale, checkBoxUnspecified, checkBoxMale;

    @FXML
    private AnchorPane Register_Pane;

    public static boolean validateString(String txt){
        String regx = "^[a-zA-Z ]+$";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();
    }

    @FXML
    void termsCondition_Pressed(ActionEvent event) {
        if (termsCondition.isSelected()){
            Register_Button.setDisable(false);
        }
        else {
            Register_Button.setDisable(true);
        }
    }

    @FXML
    void Register_FirstName_Typed(KeyEvent event) {
        if (!validateString(Register_FirstName.getText())){
            Error_Email.setVisible(false);
            Error_Password.setVisible(false);
            Error_Name.setVisible(true);
        }
        else {
            Error_Email.setVisible(false);
            Error_Password.setVisible(false);
            Error_Name.setVisible(false);
        }
    }

    @FXML
    void Register_LastName_Typed(KeyEvent event) {
        if (!validateString(Register_LastName.getText())){
            Error_Email.setVisible(false);
            Error_Password.setVisible(false);
            Error_Name.setVisible(true);
        }
        else {
            Error_Email.setVisible(false);
            Error_Password.setVisible(false);
            Error_Name.setVisible(false);
        }
    }

    @FXML
    void Register_Email_Typed(KeyEvent event) {
        EmailValidator validator = EmailValidator.getInstance();
        if (!validator.isValid(Register_Email.getText())){
            Error_Email.setVisible(true);
            Error_Password.setVisible(false);
            Error_Name.setVisible(false);
        }
        else {
            Error_Email.setVisible(false);
            Error_Password.setVisible(false);
            Error_Name.setVisible(false);
        }
    }

    @FXML
    void Register_Password_Typed(KeyEvent event) {
        if (Register_Password.getText().length()<8){
            Error_Email.setVisible(false);
            Error_Password.setVisible(true);
            Error_Name.setVisible(false);
        }
        else {
            Error_Email.setVisible(false);
            Error_Password.setVisible(false);
            Error_Name.setVisible(false);
        }
    }

    public void genderSelected(ActionEvent event) {
        if (checkBoxMale.isSelected()) {
            gendertxt = checkBoxMale.getText();
        } else if (checkBoxFemale.isSelected()) {
            gendertxt = checkBoxFemale.getText();
        } else {
            gendertxt = checkBoxUnspecified.getText();
            }
        }
    public void Register_Login_Text_Clicked(){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(Register_Password.getText().getBytes("UTF-8"), 0, Register_Password.getText().length());
            String encriptedPassword = DatatypeConverter.printHexBinary(messageDigest.digest());

            EmailValidator validator = EmailValidator.getInstance();

            if(Register_FirstName.getText().isEmpty() || Register_LastName.getText().isEmpty() || Register_Email.getText().isEmpty() || Register_Password.getText().isEmpty() || Register_CabinNo.getText().isEmpty()){
                Error_Empty.setVisible(true);
                Error_Email.setVisible(false);
                Error_Password.setVisible(false);
                Error_Name.setVisible(false);
                Error_EmailExist.setVisible(false);
            }
            else if (!validator.isValid(Register_Email.getText())){
                Error_Email.setVisible(true);
                Error_Empty.setVisible(false);
                Error_Password.setVisible(false);
                Error_Name.setVisible(false);
                Error_EmailExist.setVisible(false);
            }
            else if (!validateString(Register_FirstName.getText()) || !validateString(Register_LastName.getText())){
                Error_Email.setVisible(false);
                Error_Empty.setVisible(false);
                Error_Password.setVisible(false);
                Error_Name.setVisible(true);
                Error_EmailExist.setVisible(false);
            }
            else if (Register_Password.getText().length()<8){
                Error_Password.setVisible(true);
                Error_Email.setVisible(false);
                Error_Empty.setVisible(false);
                Error_Name.setVisible(false);
                Error_EmailExist.setVisible(false);
            }
            else{
                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
                String sql = "INSERT INTO `registration`(`First Name`, `Last Name`, `Email`, `Password`, `Cabin Number`, `Gender`,`Joined Date`,`Role`)"+"values(?,?,?,?,?,?,current_timestamp,'User')";
                PreparedStatement mySt = myConn.prepareStatement(sql);
                mySt.setString(1, Register_FirstName.getText());
                mySt.setString(2, Register_LastName.getText());
                mySt.setString(3, Register_Email.getText());
                mySt.setString(4, encriptedPassword);
                mySt.setString(5, Register_CabinNo.getText());
                mySt.setString(6, gendertxt);
                mySt.executeUpdate();
                mySt.close();
                JFXSnackbar snackbar = new JFXSnackbar(Register_Pane);
                snackbar.show("Data sucessfully Inserted", 3000);
                Error_Email.setVisible(false);
                Error_Name.setVisible(false);
                Error_Empty.setVisible(false);
                Register_FirstName.clear();
                Register_LastName.clear();
                Register_Email.clear();
                Register_Password.clear();
                Register_CabinNo.clear();

            }

        }
        catch (MySQLIntegrityConstraintViolationException dup){
            Error_Password.setVisible(false);
            Error_Email.setVisible(false);
            Error_Empty.setVisible(false);
            Error_Name.setVisible(false);
            Error_EmailExist.setVisible(true);
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gendertxt = checkBoxMale.getText();
    }
}
