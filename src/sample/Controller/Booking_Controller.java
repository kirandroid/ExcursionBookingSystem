package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Booking_Controller implements Initializable {
    private static String Excursion_ID, Port_ID, Excursion_Name, Booked_Seat, Booked_Date;
    private static ObservableList<booking_info> booking;

    @FXML
    private Label Cancel_ID;

    @FXML
    private StackPane test;

    @FXML
    private JFXTextField Update_textfield_Seat;

    @FXML
    private JFXButton cancelBooking_Button;

    @FXML private TableView<booking_info> tableView;

    @FXML private TableColumn<booking_info, String>  ExcursionID_Column;

    @FXML private TableColumn<booking_info, String> PortID_Column;

    @FXML private TableColumn<booking_info,String> ExcursionName_Column;

    @FXML private TableColumn<booking_info, String> SeatNo_Column;

    @FXML private TableColumn<booking_info, String> BookedDate_Column;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sample.Controller.Login_Controller login_controller;
        login_controller = new sample.Controller.Login_Controller();

        ExcursionID_Column.setCellValueFactory(new PropertyValueFactory<booking_info, String>("Excursion_ID"));
        PortID_Column.setCellValueFactory(new PropertyValueFactory<booking_info, String>("Port_ID"));
        ExcursionName_Column.setCellValueFactory(new PropertyValueFactory<booking_info, String>("Excursion_Name"));
        SeatNo_Column.setCellValueFactory(new PropertyValueFactory<booking_info, String>("Booked_Seat"));
        BookedDate_Column.setCellValueFactory(new PropertyValueFactory<booking_info, String>("Booked_Date"));

        tableView.setItems(getBooking());

        try{
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs","root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `booking` WHERE `Booked By` = '"+login_controller.loggedInID+"' AND `Status` = 'Booked'");
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

        tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Cancel_ID.setText(tableView.getSelectionModel().getSelectedItem().getExcursion_ID());
            }
        });
    }

    public void cancelBooking_Button()throws SQLException{
        test.setVisible(true);

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Cancel"));
        content.setBody(new Text("Booking that are cancelled cannot are deleted forever. Are you sure you want to Cancel?"));
        JFXDialog dialog = new JFXDialog(test, content, JFXDialog.DialogTransition.CENTER);
        JFXButton closeButton = new JFXButton("Close");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
                test.setVisible(false);
            }
        });

        JFXButton okayButton = new JFXButton("Okay");
        okayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                String bookedSeatByUser = null;
                sample.Controller.Login_Controller login_controller;
                login_controller = new sample.Controller.Login_Controller();
                try {
                    Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs","root", "");
                    Statement statement = myConn.createStatement();
                    statement.execute("UPDATE `booking` SET `Status`='Cancelled' WHERE `Booked By`='"+login_controller.loggedInID+"' AND `Excursion ID`='"+tableView.getSelectionModel().getSelectedItem().getExcursion_ID()+"'");
                    ResultSet bookedSeatByUser_RS = statement.executeQuery("SELECT `Booked Seat` FROM `booking` WHERE `Booked By`='"+login_controller.loggedInID+"' AND `Excursion ID`='"+tableView.getSelectionModel().getSelectedItem().getExcursion_ID()+"'");
                    while (bookedSeatByUser_RS.next()){
                        bookedSeatByUser = bookedSeatByUser_RS.getString("Booked Seat");
                    }
                    statement.execute("UPDATE `excursions` SET `Seat`=`Seat`+'"+Integer.parseInt(bookedSeatByUser)+"' WHERE `ID` = '"+tableView.getSelectionModel().getSelectedItem().getExcursion_ID()+"'");
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
                dialog.close();
                test.setVisible(false);
            }
        });
        content.setActions(closeButton, okayButton);

        dialog.show();

        }

    public void updateBooking_Button()throws SQLException{
        
    }



    public ObservableList<booking_info> getBooking(){
        booking = FXCollections.observableArrayList();
        return booking;
    }

}
