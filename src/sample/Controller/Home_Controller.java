package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Home_Controller implements Initializable {
    @FXML
    private AnchorPane rootpane;

    @FXML
    private TextField auto_search;

    @FXML
    private void Close_App(MouseEvent event) {
        System.exit(0);
    }

//    @FXML
//    void Login_Register(MouseEvent event) {
//        try {
////            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/Login.fxml"));
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/Login.fxml"));
//            Parent root1 = (Parent) fxmlLoader.load();
//            Stage stage = new Stage();
//            stage.initStyle(StageStyle.UNDECORATED);
//            stage.setTitle("Register");
//            stage.setScene(new Scene(root1));
//            stage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    @FXML
    public void Login_Register(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/Login.fxml"));
        rootpane.getChildren().setAll(pane);
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT `Name` FROM `excursions`");
            String Name = null;
            ArrayList<String> results = new ArrayList<String>();
            while (rs.next()){
                results.add(rs.getString(1));
//                Name = rs.getString("Name");
            }
            TextFields.bindAutoCompletion(auto_search, results);
            System.out.println(results);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] words = {"STINGRAY CITY SANDBAR & BEACH BREAK", "TURTLES & STINGRAYS LAND & SEA ADVENTURE", "CAYMAN CULTURAL EXPRESS"};


    }
}