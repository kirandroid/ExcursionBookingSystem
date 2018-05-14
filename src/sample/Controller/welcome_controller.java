package sample.Controller;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class welcome_controller implements Initializable {
    //SELECT `ID` FROM `excursions` ORDER BY RAND() LIMIT 2

    private String id = null, port = null, name = null;
    private String Seat = null, Total_Seat = null, check_excursion_ID;
    public static Boolean isLoggedIn= false;
    @FXML
    private Label ExcursionName_BasicSearch, ExcursionID_BasicSearch, PortID_BasicSearch, RemainingSeat, sliderNo, Recommended_Place1, Recommended_Place2;

    @FXML
    private JFXSlider Booking_NoOfSeat;

    @FXML
    private StackPane welcome_StackPane;

    @FXML
    private Pane Places_Pane1, Places_Pane2;

    @FXML
    public AnchorPane home_snackbarPane, homepane, Search_Main_Pane, Pane_BasicSearchResult, home_recommended_place, Error_doLogin, contain_place2, contain_place1;

    @FXML
    private Label Home_LogIn_button, loggedIn_username;

    @FXML
    public TextField auto_search;

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
        Search_Main_Pane.getChildren().setAll(pane);
    }

    @FXML
    public void Login_Register(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/Login.fxml"));
        homepane.getChildren().setAll(pane);
    }


    @FXML
    public void SearchResult_Basic(MouseEvent event) throws IOException{
        home_recommended_place.setVisible(false);
        Pane_BasicSearchResult.setVisible(true);

//THis is not needed but incase for future reference.
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("../FXML/Search_Result.fxml"));
//        AnchorPane pane1 = loader.load();
//        home_search_result.getChildren().setAll(pane1);

        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT `ID`,`PORT_ID`, `Seat` FROM `excursions` WHERE `Name`= '"+auto_search.getText()+"'");
            while (rs.next()){
                id = rs.getString("ID");
                port = rs.getString("PORT_ID");
                Seat = rs.getString("Seat");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        ExcursionName_BasicSearch.setText(auto_search.getText());
        ExcursionID_BasicSearch.setText(id);
        PortID_BasicSearch.setText(port);
        RemainingSeat.setText(Seat);

    }

    @FXML
    void sliderclicked(MouseEvent event) {
        Double noofseat = Booking_NoOfSeat.getValue();
        sliderNo.setText(""+noofseat.intValue());
    }
    @FXML
    public void BookBtn_BasicSearch(MouseEvent event){
        Double noofseat = Booking_NoOfSeat.getValue();
        sample.Controller.welcome_controller welcomeController;
        welcomeController = new sample.Controller.welcome_controller();

        sample.Controller.Login_Controller login_controller;
        login_controller = new sample.Controller.Login_Controller();

        if (welcomeController.isLoggedIn== false){
            Error_doLogin.setVisible(true);
        }
        else if(welcomeController.isLoggedIn == true){
            try {
                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
                Statement statement = myConn.createStatement();
                ResultSet rs = statement.executeQuery("SELECT `Seat` FROM `excursions` WHERE `ID` ='"+id+"'");
                while (rs.next()) {
                    Total_Seat = rs.getString("Seat");
                }
                ResultSet rs2 = statement.executeQuery("SELECT `Excursion ID` FROM `booking` WHERE `Booked By` = '"+login_controller.loggedInID+"'");
                while (rs2.next()){
                    check_excursion_ID = rs2.getString("Excursion ID");
                }
                if (Integer.parseInt(Total_Seat)-noofseat.intValue() < 0){
                    System.out.println("Sorry all the seat of the Excursion is booked. Do you want to be in the waiting list?");
                }
                else if (id.equals(check_excursion_ID)){
                    System.out.println("Sorry you have already booked this Excursion!");
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
                                String sql = "INSERT INTO `booking`(`Excursion Name`, `Excursion ID`, `Port ID`, `Booked Seat`, `Booked By`, `Status`, `Booked Date`)"+"values(?,?,?,?,?,?,current_timestamp)";
                                PreparedStatement mySt = myConn.prepareStatement(sql);
                                Statement stmt = myConn.createStatement();
                                String updateSeat = "UPDATE `excursions` SET `Seat`=`Seat`-'"+noofseat.intValue()+"' WHERE `ID` = '"+id+"'";
                                stmt.executeUpdate(updateSeat);
                                mySt.setString(1, auto_search.getText());
                                mySt.setString(2, id);
                                mySt.setString(3, port);
                                mySt.setString(4, String.valueOf(noofseat.intValue()));
                                mySt.setString(5, login_controller.loggedInID);
                                mySt.setString(6, "Booked");
                                mySt.executeUpdate();
                                mySt.close();
                                System.out.println("Data sucessfully Inserted");
                                RemainingSeat.setText("");
                                RemainingSeat.setText(Total_Seat);
                                JFXSnackbar snackbar = new JFXSnackbar(home_snackbarPane);
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

            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void BackTo_Profile(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../FXML/profile.fxml"));
        homepane.getChildren().setAll(pane);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources){
//        try {
//            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
//            Statement statement = myConn.createStatement();
//            ResultSet rs = statement.executeQuery("SELECT `ID` FROM `excursions` ORDER BY RAND() LIMIT 2 ");
//            while (rs.next()) {
//                Recommended_Place_id1 = rs.getString("ID");
//
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }

        JFXRippler rippler1 = new JFXRippler(Places_Pane1);
        JFXRippler rippler2 = new JFXRippler(Places_Pane2);
        contain_place2.getChildren().add(rippler2);
        contain_place1.getChildren().add(rippler1);

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
