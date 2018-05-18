package sample.Controller;

import com.jfoenix.controls.JFXSlider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SearchResult_Controller {

    @FXML
    private Label ExcursionName_BasicSearch, ExcursionID_BasicSearch, PortID_BasicSearch;

    private String SelectedExcursionName = null, SelectedExcursionID = null, SelectedPortID = null;


    @FXML
    private JFXSlider Booking_NoOfSeat;
    @FXML
    private Label sliderNo;

    @FXML
    void sliderclicked(MouseEvent event) {
        Double noofseat = Booking_NoOfSeat.getValue();
        sliderNo.setText(""+noofseat.intValue());
    }
    @FXML
    private void BookBtn_BasicSearch(MouseEvent event){
        Double noofseat = Booking_NoOfSeat.getValue();
        sample.Controller.welcome_controller welcomeController;
        welcomeController = new sample.Controller.welcome_controller();

        sample.Controller.Login_Controller login_controller;
        login_controller = new sample.Controller.Login_Controller();

        if (welcomeController.isLoggedIn== false){
            System.out.println("User is not logged in");
        }
        else if(welcomeController.isLoggedIn == true){
            try {
                if (noofseat.intValue() > 32){
                    System.out.println("Error! No of seat should not be less than 32");
                }
                else {
                    Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
                    String sql = "INSERT INTO `booking`(`Excursion Name`, `Excursion ID`, `Port ID`, `Booked Seat`, `Booked By`, `Booked Date`)"+"values(?,?,?,?,?,current_timestamp)";
                    PreparedStatement mySt = myConn.prepareStatement(sql);
                    mySt.setString(1, SelectedExcursionName);
                    mySt.setString(2, SelectedExcursionID);
                    mySt.setString(3, SelectedPortID);
                    mySt.setString(4, String.valueOf(noofseat.intValue()));
                    mySt.setString(5, login_controller.loggedInID);
                    mySt.executeUpdate();
                    mySt.close();
                    System.out.println("Data sucessfully Inserted");
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void setlabels(String ExcursionName_get, String ExcursionID_get, String PortID_get){
        SelectedExcursionName = ExcursionName_get;
        SelectedExcursionID = ExcursionID_get;
        SelectedPortID = PortID_get;

        ExcursionName_BasicSearch.setText(ExcursionName_get);
        ExcursionID_BasicSearch.setText(ExcursionID_get);
        PortID_BasicSearch.setText(PortID_get);
    }
}
