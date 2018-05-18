package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class adminWaiting_Controller implements Initializable {
    private static String Excursion_ID, Port_ID, Excursion_Name, Booked_Seat, Booked_Date, Booking_ID, Booked_By;
    private static ObservableList<adminWaiting_info> adminbooking_info;

    @FXML
    private Label Cancel_ID;

    @FXML
    private StackPane userBooking_StackPane;

    @FXML
    private TableView<adminWaiting_info> adminBookingTable;

    @FXML
    private TableColumn<adminWaiting_info, String> bookingID_Column;

    @FXML
    private TableColumn<adminWaiting_info, String> bookingExName_Column;

    @FXML
    private TableColumn<adminWaiting_info, String> bookingExID_Column;

    @FXML
    private TableColumn<adminWaiting_info, String> bookingPortID_Column;

    @FXML
    private TableColumn<adminWaiting_info, String> bookedSeat_column;

    @FXML
    private TableColumn<adminWaiting_info, String> bookedBy_Column;

    @FXML
    private TableColumn<adminWaiting_info, String> bookedDate_Column;

    //For refreshing the table data when some action is performed
    public void refresh() {
        adminbooking_info.clear();
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/group-8", "root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `booking` WHERE `Status` = \"Waiting\"");
            while (rs.next()) {
                Excursion_ID = rs.getString("Excursion ID");
                Port_ID = rs.getString("Port ID");
                Excursion_Name = rs.getString("Excursion Name");
                Booked_Seat = rs.getString("Booked Seat");
                Booked_Date = rs.getString("Booked Date");
                Booking_ID = rs.getString("Booking ID");
                Booked_By = rs.getString("Booked By");
                adminbooking_info.add(new adminWaiting_info(Booking_ID, Excursion_Name, Excursion_ID, Port_ID, Booked_Seat, Booked_By, Booked_Date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookingID_Column.setCellValueFactory(new PropertyValueFactory<adminWaiting_info, String>("Booking_ID"));
        bookingExName_Column.setCellValueFactory(new PropertyValueFactory<adminWaiting_info, String>("Excursion_Name"));
        bookingExID_Column.setCellValueFactory(new PropertyValueFactory<adminWaiting_info, String>("Excursion_ID"));
        bookingPortID_Column.setCellValueFactory(new PropertyValueFactory<adminWaiting_info, String>("Port_ID"));
        bookedSeat_column.setCellValueFactory(new PropertyValueFactory<adminWaiting_info, String>("Booked_Seat"));
        bookedBy_Column.setCellValueFactory(new PropertyValueFactory<adminWaiting_info, String>("Booked_By"));
        bookedDate_Column.setCellValueFactory(new PropertyValueFactory<adminWaiting_info, String>("Booked_Date"));
        adminBookingTable.setItems(getWaitingBooking());

        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/group-8", "root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `booking` WHERE `Status` = \"Waiting\"");
            while (rs.next()) {
                Excursion_ID = rs.getString("Excursion ID");
                Port_ID = rs.getString("Port ID");
                Excursion_Name = rs.getString("Excursion Name");
                Booked_Seat = rs.getString("Booked Seat");
                Booked_Date = rs.getString("Booked Date");
                Booking_ID = rs.getString("Booking ID");
                Booked_By = rs.getString("Booked By");
                adminbooking_info.add(new adminWaiting_info(Booking_ID, Excursion_Name, Excursion_ID, Port_ID, Booked_Seat, Booked_By, Booked_Date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        adminBookingTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Cancel_ID.setText(adminBookingTable.getSelectionModel().getSelectedItem().getExcursion_ID());
            }
        });
    }

    public void cancelBooking_Button() throws SQLException {
        if (adminBookingTable.getSelectionModel().getSelectedItem() == null) {
            userBooking_StackPane.setVisible(true);
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Cancel"));
            content.setBody(new Text("Select a table"));
            JFXDialog dialog = new JFXDialog(userBooking_StackPane, content, JFXDialog.DialogTransition.CENTER);
            JFXButton closeButton = new JFXButton("Close");
            closeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialog.close();
                    userBooking_StackPane.setVisible(false);
                }
            });
            content.setActions(closeButton);
            dialog.show();
        } else {
            userBooking_StackPane.setVisible(true);

            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Cancel"));
            content.setBody(new Text("Booking that are cancelled are deleted forever. Are you sure you want to Cancel?"));
            JFXDialog dialog = new JFXDialog(userBooking_StackPane, content, JFXDialog.DialogTransition.CENTER);
            JFXButton closeButton = new JFXButton("Close");
            closeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialog.close();
                    userBooking_StackPane.setVisible(false);
                }
            });

            JFXButton okayButton = new JFXButton("Okay");
            okayButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/group-8", "root", "");
                        Statement statement = myConn.createStatement();
                        statement.execute("UPDATE `booking` SET `Status`='Cancelled' WHERE `Booked By`='" + adminBookingTable.getSelectionModel().getSelectedItem().getBooked_By() + "' AND `Excursion ID`='" + adminBookingTable.getSelectionModel().getSelectedItem().getExcursion_ID() + "' AND `Status` = 'Waiting'");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    dialog.close();
                    userBooking_StackPane.setVisible(false);
                    refresh();
                }
            });
            content.setActions(closeButton, okayButton);

            dialog.show();

        }

    }


    public ObservableList<adminWaiting_info> getWaitingBooking() {
        adminbooking_info = FXCollections.observableArrayList();
        return adminbooking_info;
    }

}
