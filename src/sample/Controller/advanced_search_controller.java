package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class advanced_search_controller implements Initializable {

    private String id = null, port = null, name = null;

    @FXML
    public ComboBox PortID_box_ID, ExcursionID_box_ID, Excursionname_box_ID;

    ObservableList<String> PortID_box_List = FXCollections.observableArrayList(PortID_comboresult());

    public void ExcursionName_Clicked(){
        ObservableList<String> ExcursionName_box_List = FXCollections.observableArrayList(ExcursionName_comboresult());
        Excursionname_box_ID.setItems(ExcursionName_box_List);
    }

    public void ExcursionID_Clicked(){
        ObservableList<String> ExcursionID_box_List = FXCollections.observableArrayList(ExcursionID_comboresult());
        ExcursionID_box_ID.setItems(ExcursionID_box_List);
    }

    public String[] PortID_comboresult(){
        ArrayList<String> results = new ArrayList<String>();
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT DISTINCT `PORT_ID` FROM `excursions`");
            while (rs.next()){
                results.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] dbresult = new String[results.size()];
        dbresult = results.toArray(dbresult);
        return dbresult;
    }

    public String[] ExcursionName_comboresult(){
        ArrayList<String> results = new ArrayList<String>();
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT `NAME` FROM `excursions` WHERE `PORT_ID` ='"+PortID_box_ID.getValue()+"'");
            while (rs.next()){
                results.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] dbresult = new String[results.size()];
        dbresult = results.toArray(dbresult);
        return dbresult;
    }

    public String[] ExcursionID_comboresult(){
        ArrayList<String> results = new ArrayList<String>();
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT `ID` FROM `excursions` WHERE `NAME` ='"+Excursionname_box_ID.getValue()+"'");
            while (rs.next()){
                results.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] dbresult = new String[results.size()];
        dbresult = results.toArray(dbresult);
        return dbresult;
    }

    @FXML
    public void SearchResult_Advanced(MouseEvent event) throws IOException{
        sample.Controller.welcome_controller welcomeController;
        welcomeController = new sample.Controller.welcome_controller();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML/Search_Result.fxml"));
        AnchorPane pane2 = loader.load();
        welcomeController.home_search_pane.getChildren().setAll(pane2);
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT `NAME`,`PORT_ID` FROM `excursions` WHERE `ID`= '"+ExcursionID_box_ID.getValue()+"'");
            while (rs.next()){
                name = rs.getString("NAME");
                port = rs.getString("PORT_ID");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        //sending the data to search result class
        SearchResult_Controller searchResult_controller = loader.getController();
        searchResult_controller.setlabels(name,ExcursionID_box_ID.getValue().toString(),port);

    }

    @FXML
    public void search_basic_change(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/welcome.fxml"));
        Main.stage.getScene().setRoot(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PortID_box_ID.setItems(PortID_box_List);
    }
}
