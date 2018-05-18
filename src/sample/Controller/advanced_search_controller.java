package sample.Controller;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class advanced_search_controller implements Initializable {

    private String Seat = null, Total_Seat = null, check_excursion_ID, Text_Recommended_Place1= null, Text_Recommended_Place2 = null;
    private String id = null, port = null, name = null;

    @FXML
    private Label ExcursionName_BasicSearch, ExcursionID_BasicSearch, PortID_BasicSearch, sliderNo, RemainingSeat, Recommended_Place1, Recommended_Place2;

    @FXML
    private JFXSlider Booking_NoOfSeat;

    @FXML
    private StackPane welcome_StackPane;

    @FXML
    private AnchorPane Search_Main_Pane, Pane_ADVSearchResult, home_recommended_place, contain_place2, contain_place1, Error_doLogin;

    @FXML
    private Pane Places_Pane1, Places_Pane2;

    @FXML
    public ComboBox PortID_box_ID, ExcursionID_box_ID, Excursionname_box_ID;

    public void Places_Pane1_Clicked(){
        home_recommended_place.setVisible(false);
        Pane_ADVSearchResult.setVisible(true);

        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT `Name`,`PORT_ID`, `Seat` FROM `excursions` WHERE `ID`= '"+Text_Recommended_Place1+"'");
            while (rs.next()){
                name = rs.getString("Name");
                port = rs.getString("PORT_ID");
                Seat = rs.getString("Seat");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        ExcursionName_BasicSearch.setText(name);
        ExcursionID_BasicSearch.setText(Text_Recommended_Place1);
        PortID_BasicSearch.setText(port);
        RemainingSeat.setText(Seat);
    }

    public void Places_Pane2_Clicked(){
        home_recommended_place.setVisible(false);
        Pane_ADVSearchResult.setVisible(true);

        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT `Name`,`PORT_ID`, `Seat` FROM `excursions` WHERE `ID`= '"+Text_Recommended_Place2+"'");
            while (rs.next()){
                name = rs.getString("Name");
                port = rs.getString("PORT_ID");
                Seat = rs.getString("Seat");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        ExcursionName_BasicSearch.setText(name);
        ExcursionID_BasicSearch.setText(Text_Recommended_Place2);
        PortID_BasicSearch.setText(port);
        RemainingSeat.setText(Seat);
    }


    ObservableList<String> PortID_box_List = FXCollections.observableArrayList(PortID_comboresult());


    public void ExcursionName_Clicked() {
        ObservableList<String> ExcursionName_box_List = FXCollections.observableArrayList(ExcursionName_comboresult());
        Excursionname_box_ID.setItems(ExcursionName_box_List);
    }

    public void ExcursionID_Clicked() {
        ObservableList<String> ExcursionID_box_List = FXCollections.observableArrayList(ExcursionID_comboresult());
        ExcursionID_box_ID.setItems(ExcursionID_box_List);
    }

    public String[] PortID_comboresult() {
        ArrayList<String> results = new ArrayList<String>();
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT DISTINCT `PORT_ID` FROM `excursions`");
            while (rs.next()) {
                results.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] dbresult = new String[results.size()];
        dbresult = results.toArray(dbresult);
        return dbresult;
    }

    public String[] ExcursionName_comboresult() {
        ArrayList<String> results = new ArrayList<String>();
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT `NAME` FROM `excursions` WHERE `PORT_ID` ='" + PortID_box_ID.getValue() + "'");
            while (rs.next()) {
                results.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] dbresult = new String[results.size()];
        dbresult = results.toArray(dbresult);
        return dbresult;
    }

    public String[] ExcursionID_comboresult() {
        ArrayList<String> results = new ArrayList<String>();
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT `ID` FROM `excursions` WHERE `NAME` ='" + Excursionname_box_ID.getValue() + "'");
            while (rs.next()) {
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
    public void search_basic_change(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/welcome.fxml"));
        Main.stage.getScene().setRoot(root);
    }

    @FXML
    public void SearchResult_Basic(MouseEvent event) throws SQLException {
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
        Statement statement = myConn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT `Seat`, `PORT_ID`, `Name`, `ID` FROM `excursions` WHERE `ID` ='" + ExcursionID_box_ID.getValue() + "'");
        while (rs.next()) {
            Seat = rs.getString("Seat");
            port = rs.getString("PORT_ID");
            name = rs.getString("Name");
            id = rs.getString("ID");
        }
        home_recommended_place.setVisible(false);
        Pane_ADVSearchResult.setVisible(true);

        ExcursionName_BasicSearch.setText(name);
        ExcursionID_BasicSearch.setText(id);
        PortID_BasicSearch.setText(port);
        RemainingSeat.setText(Seat);

    }

    @FXML
    void sliderclicked(MouseEvent event) {
        Double noofseat = Booking_NoOfSeat.getValue();
        sliderNo.setText("" + noofseat.intValue());
    }

    @FXML
    private void BookBtn_BasicSearch(MouseEvent event) {
        Double noofseat = Booking_NoOfSeat.getValue();

        sample.Controller.welcome_controller welcomeController;
        welcomeController = new sample.Controller.welcome_controller();

        sample.Controller.Login_Controller login_controller;
        login_controller = new sample.Controller.Login_Controller();

        if (!(welcomeController.isLoggedIn == true)) {
            Error_doLogin.setVisible(true);
        } else if (welcomeController.isLoggedIn == true) {
            try {
                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
                Statement statement = myConn.createStatement();
                ResultSet rs = statement.executeQuery("SELECT `Seat` FROM `excursions` WHERE `ID` ='" + ExcursionID_box_ID.getValue() + "'");
                while (rs.next()) {
                    Total_Seat = rs.getString("Seat");
                }
                ResultSet rs2 = statement.executeQuery("SELECT  `Excursion ID` FROM `booking` WHERE `Booked By` = '"+login_controller.loggedInID+"'");
                while (rs2.next()){
                    check_excursion_ID = rs2.getString("Excursion ID");
                }
                if (ExcursionID_box_ID.getValue().toString().equals(check_excursion_ID)){
                    welcome_StackPane.setVisible(true);
                    JFXDialogLayout content = new JFXDialogLayout();
                    content.setHeading(new Text("Error"));
                    content.setBody(new Text("Sorry you have already booked this tour!"));
                    JFXDialog dialog = new JFXDialog(welcome_StackPane, content, JFXDialog.DialogTransition.CENTER);
                    JFXButton closeButton = new JFXButton("Close");
                    closeButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            dialog.close();
                            welcome_StackPane.setVisible(false);
                        }
                    });
                    content.setActions(closeButton);
                    dialog.show();

                }
                else if (Integer.parseInt(Total_Seat) <= 0) {

                    welcome_StackPane.setVisible(true);
                    JFXDialogLayout content = new JFXDialogLayout();
                    content.setHeading(new Text("Booking"));
                    content.setBody(new Text("Sorry all the seat of the Excursion is booked. Do you want to be in the waiting list?"));
                    JFXDialog dialog = new JFXDialog(welcome_StackPane, content, JFXDialog.DialogTransition.CENTER);
                    JFXButton closeButton = new JFXButton("Close");
                    closeButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            dialog.close();
                            welcome_StackPane.setVisible(false);
                        }
                    });
                    JFXButton okButton = new JFXButton("Okay");
                    okButton.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                String sql = "INSERT INTO `booking`(`Excursion Name`, `Excursion ID`, `Port ID`, `Booked Seat`, `Booked By`, `Status`, `Booked Date`)"+"values(?,?,?,?,?,?,current_timestamp)";
                                PreparedStatement mySt = myConn.prepareStatement(sql);
                                Statement stmt = myConn.createStatement();
                                mySt.setString(1, Excursionname_box_ID.getValue().toString());
                                mySt.setString(2, ExcursionID_box_ID.getValue().toString());
                                mySt.setString(3, PortID_box_ID.getValue().toString());
                                mySt.setString(4, String.valueOf(noofseat.intValue()));
                                mySt.setString(5, login_controller.loggedInID);
                                mySt.setString(6, "Waiting");
                                mySt.executeUpdate();
                                mySt.close();
                                RemainingSeat.setText("");
                                RemainingSeat.setText(Total_Seat);
                                JFXSnackbar snackbar = new JFXSnackbar(Search_Main_Pane);
                                snackbar.show("Data Inserted to Waiting List", 2000);
                            }
                            catch (SQLException e){
                                e.printStackTrace();
                            }
                            dialog.close();
                            welcome_StackPane.setVisible(false);
                        }
                    });
                    content.setActions(closeButton, okButton);
                    dialog.show();
                }
                else if (noofseat.intValue()> Integer.parseInt(Total_Seat)){
                    int seatSub = Integer.parseInt(Total_Seat)-noofseat.intValue();
                    welcome_StackPane.setVisible(true);
                    JFXDialogLayout content = new JFXDialogLayout();
                    content.setHeading(new Text("Booking"));
                    content.setBody(new Text("Sorry only "+Total_Seat+" seats are available. Do you want to keep rest "+ Math.abs(seatSub)+" seats in waiting list?"));
                    JFXDialog dialog = new JFXDialog(welcome_StackPane, content, JFXDialog.DialogTransition.CENTER);
                    JFXButton closeButton = new JFXButton("Close");
                    closeButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            dialog.close();
                            welcome_StackPane.setVisible(false);
                        }
                    });
                    JFXButton okButton = new JFXButton("Okay");
                    okButton.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                String sql = "INSERT INTO `booking`(`Excursion Name`, `Excursion ID`, `Port ID`, `Booked Seat`, `Booked By`, `Status`, `Booked Date`)"+"values(?,?,?,?,?,?,current_timestamp)";
                                String sqlWait = "INSERT INTO `booking`(`Excursion Name`, `Excursion ID`, `Port ID`, `Booked Seat`, `Booked By`, `Status`, `Booked Date`)"+"values(?,?,?,?,?,?,current_timestamp)";
                                PreparedStatement mySt = myConn.prepareStatement(sql);
                                PreparedStatement myStWait = myConn.prepareStatement(sqlWait);
                                Statement stmt = myConn.createStatement();
                                String updateSeat = "UPDATE `excursions` SET `Seat`=`Seat`-'"+Total_Seat+"' WHERE `ID` = '"+id+"'";
                                stmt.executeUpdate(updateSeat);
                                mySt.setString(1, Excursionname_box_ID.getValue().toString());
                                mySt.setString(2, ExcursionID_box_ID.getValue().toString());
                                mySt.setString(3, PortID_box_ID.getValue().toString());
                                mySt.setString(4, String.valueOf(Total_Seat));
                                mySt.setString(5, login_controller.loggedInID);
                                mySt.setString(6, "Booked");
                                mySt.executeUpdate();
                                mySt.close();
                                myStWait.setString(1, Excursionname_box_ID.getValue().toString());
                                myStWait.setString(2, ExcursionID_box_ID.getValue().toString());
                                myStWait.setString(3, PortID_box_ID.getValue().toString());
                                myStWait.setString(4, String.valueOf(Math.abs(seatSub)));
                                myStWait.setString(5, login_controller.loggedInID);
                                myStWait.setString(6, "Waiting");
                                myStWait.executeUpdate();
                                myStWait.close();
                                RemainingSeat.setText("");
                                RemainingSeat.setText(Total_Seat);
                                JFXSnackbar snackbar = new JFXSnackbar(Search_Main_Pane);
                                snackbar.show("Data sucessfully Inserted", 2000);
                            }
                            catch (SQLException e){
                                e.printStackTrace();
                            }
                            dialog.close();
                            welcome_StackPane.setVisible(false);
                        }
                    });
                    content.setActions(closeButton, okButton);
                    dialog.show();
                }

                else {
                    welcome_StackPane.setVisible(true);
                    JFXDialogLayout content = new JFXDialogLayout();
                    content.setHeading(new Text("Booking"));
                    content.setBody(new Text("Do you want to Confirm Bookings?"));
                    JFXDialog dialog = new JFXDialog(welcome_StackPane, content, JFXDialog.DialogTransition.CENTER);
                    JFXButton closeButton = new JFXButton("Close");
                    closeButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            dialog.close();
                            welcome_StackPane.setVisible(false);
                        }
                    });
                    JFXButton okButton = new JFXButton("Okay");
                    okButton.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                String sql = "INSERT INTO `booking`(`Excursion Name`, `Excursion ID`, `Port ID`, `Booked Seat`, `Booked By`, `Status`, `Booked Date`)" + "values(?,?,?,?,?,?,current_timestamp)";
                                Statement stmt = myConn.createStatement();
                                String updateSeat = "UPDATE `excursions` SET `Seat`=`Seat`-'" + noofseat.intValue() + "' WHERE `ID` = '" + ExcursionID_box_ID.getValue() + "'";
                                stmt.executeUpdate(updateSeat);
                                PreparedStatement mySt = myConn.prepareStatement(sql);
                                mySt.setString(1, Excursionname_box_ID.getValue().toString());
                                mySt.setString(2, ExcursionID_box_ID.getValue().toString());
                                mySt.setString(3, PortID_box_ID.getValue().toString());
                                mySt.setString(4, String.valueOf(noofseat.intValue()));
                                mySt.setString(5, login_controller.loggedInID);
                                mySt.setString(6, "Booked");
                                mySt.executeUpdate();
                                mySt.close();
                                RemainingSeat.setText("");
                                RemainingSeat.setText(Total_Seat);
                                JFXSnackbar snackbar = new JFXSnackbar(Search_Main_Pane);
                                snackbar.show("Data sucessfully Inserted", 2000);
                            }
                            catch (SQLException e){
                                e.printStackTrace();
                            }
                            dialog.close();
                            welcome_StackPane.setVisible(false);
                        }
                    });
                    content.setActions(closeButton, okButton);
                    dialog.show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
            Statement statement = myConn.createStatement();
            Statement statement1 = myConn.createStatement();
            Statement st_randName = myConn.createStatement();
            ResultSet booking_ExID_BoSeat_rs = statement.executeQuery("SELECT `Excursion ID`, `Booked Seat`, `Booked By` FROM `booking` WHERE `Status` = \"Waiting\" ");
            ResultSet randomExcursion_RS = statement1.executeQuery("SELECT `ID` FROM `excursions` ORDER BY RAND() LIMIT 2");
            while (randomExcursion_RS.next()){
                Text_Recommended_Place1 = randomExcursion_RS.getString("ID");

                ResultSet randomExcursionName_RS = st_randName.executeQuery("SELECT `Name` FROM `excursions` WHERE `ID` = '"+Text_Recommended_Place1+"'");
                while (randomExcursionName_RS.next()){
                    Recommended_Place1.setText(randomExcursionName_RS.getString("Name"));
                }

                while (randomExcursion_RS.next()){
                    Text_Recommended_Place2 = randomExcursion_RS.getString("ID");

                    ResultSet randomExcursionName_RS1 = st_randName.executeQuery("SELECT `Name` FROM `excursions` WHERE `ID` = '"+Text_Recommended_Place2+"'");
                    while (randomExcursionName_RS1.next()){
                        Recommended_Place2.setText(randomExcursionName_RS1.getString("Name"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


        JFXRippler rippler1 = new JFXRippler(Places_Pane1);
        JFXRippler rippler2 = new JFXRippler(Places_Pane2);
        contain_place2.getChildren().add(rippler2);
        contain_place1.getChildren().add(rippler1);
        PortID_box_ID.setItems(PortID_box_List);
    }
}
