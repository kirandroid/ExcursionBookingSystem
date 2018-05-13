package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class adminWaiting_Controller implements Initializable {
    private static String Excursion_ID, Port_ID, Excursion_Name, Booked_Seat, Booked_Date, Booking_ID, Booked_By;
    private static ObservableList<adminbooking_info> adminbooking_info;

    @FXML
    private Label Cancel_ID;

    @FXML
    private JFXTextField Update_textfield_Seat;

    @FXML
    private JFXButton cancelBooking_Button;

    @FXML private TableView<adminbooking_info> adminBookingTable;

    @FXML
    private TableColumn<adminbooking_info, String> bookingID_Column;

    @FXML
    private TableColumn<adminbooking_info, String> bookingExName_Column;

    @FXML
    private TableColumn<adminbooking_info, String> bookingExID_Column;

    @FXML
    private TableColumn<adminbooking_info, String> bookingPortID_Column;

    @FXML
    private TableColumn<adminbooking_info, String> bookedSeat_column;

    @FXML
    private TableColumn<adminbooking_info, String> bookedBy_Column;

    @FXML
    private TableColumn<adminbooking_info, String> bookedDate_Column;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookingID_Column.setCellValueFactory(new PropertyValueFactory<adminbooking_info, String>("Booking_ID"));
        bookingExName_Column.setCellValueFactory(new PropertyValueFactory<adminbooking_info, String>("Excursion_Name"));
        bookingExID_Column.setCellValueFactory(new PropertyValueFactory<adminbooking_info, String>("Excursion_ID"));
        bookingPortID_Column.setCellValueFactory(new PropertyValueFactory<adminbooking_info, String>("Port_ID"));
        bookedSeat_column.setCellValueFactory(new PropertyValueFactory<adminbooking_info, String>("Booked_Seat"));
        bookedBy_Column.setCellValueFactory(new PropertyValueFactory<adminbooking_info, String>("Booked_By"));
        bookedDate_Column.setCellValueFactory(new PropertyValueFactory<adminbooking_info, String>("Booked_Date"));
        adminBookingTable.setItems(getadminBooking());

        try{
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs","root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `booking` WHERE `Status` = \"Waiting\"");
            while (rs.next()){
                Excursion_ID = rs.getString("Excursion ID");
                Port_ID = rs.getString("Port ID");
                Excursion_Name = rs.getString("Excursion Name");
                Booked_Seat = rs.getString("Booked Seat");
                Booked_Date = rs.getString("Booked Date");
                Booking_ID = rs.getString("Booking ID");
                Booked_By = rs.getString("Booked By");
                adminbooking_info.add(new adminbooking_info(Booking_ID, Excursion_Name,Excursion_ID,Port_ID,Booked_Seat,Booked_By, Booked_Date));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

//        tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                Cancel_ID.setText(tableView.getSelectionModel().getSelectedItem().getExcursion_ID());
//            }
//        });
    }

//    public void cancelBooking_Button()throws SQLException{
//        String bookedSeatByUser = null;
//        Login_Controller login_controller;
//        login_controller = new Login_Controller();
//
//        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs","root", "");
//        Statement statement = myConn.createStatement();
//        statement.execute("UPDATE `booking` SET `Status`='Cancelled' WHERE `Booked By`='"+login_controller.loggedInID+"' AND `Excursion ID`='"+tableView.getSelectionModel().getSelectedItem().getExcursion_ID()+"'");
//        ResultSet bookedSeatByUser_RS = statement.executeQuery("SELECT `Booked Seat` FROM `booking` WHERE `Booked By`='"+login_controller.loggedInID+"' AND `Excursion ID`='"+tableView.getSelectionModel().getSelectedItem().getExcursion_ID()+"'");
//        while (bookedSeatByUser_RS.next()){
//            bookedSeatByUser = bookedSeatByUser_RS.getString("Booked Seat");
//        }
//        statement.execute("UPDATE `excursions` SET `Seat`=`Seat`+'"+Integer.parseInt(bookedSeatByUser)+"' WHERE `ID` = '"+tableView.getSelectionModel().getSelectedItem().getExcursion_ID()+"'");
//    }
//
//    public void updateBooking_Button()throws SQLException{
//
//    }



    public ObservableList<adminbooking_info> getadminBooking(){
        adminbooking_info = FXCollections.observableArrayList();
        return adminbooking_info;
    }

}
