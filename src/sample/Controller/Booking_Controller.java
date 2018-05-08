package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Booking_Controller implements Initializable {
    private static String Excursion_ID, Port_ID, Excursion_Name, Booked_Seat, Booked_Date;
    private static ObservableList<booking_info> booking;

    @FXML private TableView<booking_info> tableView;

    @FXML private TableColumn<booking_info, String>  ExcursionID_Column;

    @FXML private TableColumn<booking_info, String> PortID_Column;

    @FXML private TableColumn<booking_info,String> ExcursionName_Column;

    @FXML private TableColumn<booking_info, String> SeatNo_Column;

    @FXML private TableColumn<booking_info, String> BookedDate_Column;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ExcursionID_Column.setCellValueFactory(new PropertyValueFactory<booking_info, String>("Excursion_ID"));
        PortID_Column.setCellValueFactory(new PropertyValueFactory<booking_info, String>("Port_ID"));
        ExcursionName_Column.setCellValueFactory(new PropertyValueFactory<booking_info, String>("Excursion_Name"));
        SeatNo_Column.setCellValueFactory(new PropertyValueFactory<booking_info, String>("Booked_Seat"));
        BookedDate_Column.setCellValueFactory(new PropertyValueFactory<booking_info, String>("Booked_Date"));

        tableView.setItems(getBooking());

        try{
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs","root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `booking`");
            while (rs.next()){
                Excursion_ID = rs.getString("Excursion ID");
                Port_ID = rs.getString("Port ID");
                Excursion_Name = rs.getString("Excursion Name");
                Booked_Seat = rs.getString("Booked Seat");
                Booked_Date = rs.getString("Booked Date");
                booking.add(new booking_info(Excursion_ID,Port_ID,Excursion_Name,Booked_Seat,Booked_Date));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    public ObservableList<booking_info> getBooking(){
        booking = FXCollections.observableArrayList();
        return booking;
    }

}
