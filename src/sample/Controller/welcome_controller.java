package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class welcome_controller implements Initializable {
    private String id = null, port = null, name = null;
    public static Boolean isLoggedIn= false;

    //---------------For making the screen draggable-------------
    double x, y;

    @FXML
    void windowDragged(MouseEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX()-x);
        stage.setY(event.getScreenY()-y);
        stage.setOpacity(0.7f);
    }

    @FXML
    void windowDraggedDone(MouseEvent event){
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setOpacity(1.0f);
    }

    @FXML
    void windowPressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }
    //---------------For making the screen draggable-------------

    @FXML
    public AnchorPane home_search_pane, home_search_result, homepane, Error_doLogin;

    @FXML
    private Label Home_LogIn_button, loggedIn_username;

    @FXML
    public TextField auto_search;

    @FXML
    private void Close_App(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void Minimize_App(MouseEvent event){
        Main.stage.setIconified(true);
    }


    @FXML
    private void search_pane_change(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/advanced_search.fxml"));
        home_search_pane.getChildren().setAll(pane);
    }

    @FXML
    public void Login_Register(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/Login.fxml"));
        homepane.getChildren().setAll(pane);
    }


    @FXML
    public void SearchResult_Basic(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML/Search_Result.fxml"));
        AnchorPane pane1 = loader.load();
        home_search_result.getChildren().setAll(pane1);
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT `ID`,`PORT_ID` FROM `excursions` WHERE `Name`= '"+auto_search.getText()+"'");
            while (rs.next()){
                id = rs.getString("ID");
                port = rs.getString("PORT_ID");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        //sending the data to search result class
        SearchResult_Controller searchResult_controller = loader.getController();
        searchResult_controller.setlabels(auto_search.getText(),id,port);

    }

    @FXML
    private void BackTo_Profile(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/profile.fxml"));
        homepane.getChildren().setAll(pane);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //getting the array result from server class and binding it with the autocomplete textfield
        sample.server ser;
        ser = new sample.server();
        TextFields.bindAutoCompletion(auto_search, ser.SearchResult_AutoComplete());
        if (isLoggedIn){
            sample.Controller.Login_Controller login_controller;
            login_controller = new sample.Controller.Login_Controller();

            Home_LogIn_button.setVisible(false);
            loggedIn_username.setVisible(true);
            loggedIn_username.setText("Welcome "+login_controller.loggedInFirstName);

        }
        else{
            Home_LogIn_button.setVisible(true);
            loggedIn_username.setVisible(false);
        }
    }
}
