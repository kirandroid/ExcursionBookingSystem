package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
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

public class adminUser_Controller implements Initializable {
    private static String ID, First_Name, Last_Name, Email, Cabin, Gender, Joined_Date;
    private static ObservableList<adminUser_info> userInfo;

    @FXML
    private Label Cancel_ID;

    @FXML
    private StackPane userBooking_StackPane;

    @FXML
    private TableView<adminUser_info> adminUserTable;

    @FXML private TableColumn<adminUser_info, String> userID_Column;

    @FXML private TableColumn<adminUser_info, String> fName_Column;

    @FXML private TableColumn<adminUser_info, String> lName_Column;

    @FXML private TableColumn<adminUser_info,String> email_Column;

    @FXML private TableColumn<adminUser_info, String> cabin_Column;

    @FXML private TableColumn<adminUser_info, String> gender_Column;

    @FXML private TableColumn<adminUser_info, String> joinedDate_Column;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userID_Column.setCellValueFactory(new PropertyValueFactory<adminUser_info, String>("ID"));
        fName_Column.setCellValueFactory(new PropertyValueFactory<adminUser_info, String>("First_Name"));
        lName_Column.setCellValueFactory(new PropertyValueFactory<adminUser_info, String>("Last_Name"));
        email_Column.setCellValueFactory(new PropertyValueFactory<adminUser_info, String>("Email"));
        cabin_Column.setCellValueFactory(new PropertyValueFactory<adminUser_info, String>("Cabin"));
        gender_Column.setCellValueFactory(new PropertyValueFactory<adminUser_info, String>("Gender"));
        joinedDate_Column.setCellValueFactory(new PropertyValueFactory<adminUser_info, String>("Joined_Date"));

        adminUserTable.setItems(getUsers());

        try{
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs","root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `registration` WHERE `Role`=\"User\" ");
            while (rs.next()){
                ID = rs.getString("ID");
                First_Name = rs.getString("First Name");
                Last_Name = rs.getString("Last Name");
                Email = rs.getString("Email");
                Cabin = rs.getString("Cabin Number");
                Gender = rs.getString("Gender");
                Joined_Date = rs.getString("Joined Date");
                userInfo.add(new adminUser_info(ID,First_Name,Last_Name,Email, Cabin, Gender, Joined_Date));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        adminUserTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Cancel_ID.setText(adminUserTable.getSelectionModel().getSelectedItem().getID());
            }
        });
    }

    public void cancelBooking_Button() throws SQLException {
        if (adminUserTable.getSelectionModel().getSelectedItem() == null) {
            System.out.println("Select a table");
        } else {
            userBooking_StackPane.setVisible(true);

            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Cancel"));
            content.setBody(new Text("Delete User?. All the bookings by the user will be lost!"));
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
                    String exID = null, bookedSeat = null;
                    try {
                        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
                        Statement statement = myConn.createStatement();
                        Statement upSt = myConn.createStatement();

                        ResultSet exID_BookedSeat_RS = statement.executeQuery("SELECT `Excursion ID`, `Booked Seat` FROM `booking` WHERE `Booked By` = '" + adminUserTable.getSelectionModel().getSelectedItem().getID() + "' AND `Status` = 'Booked'");
                        while (exID_BookedSeat_RS.next()) {
                            exID = exID_BookedSeat_RS.getString("Excursion ID");
                            bookedSeat = exID_BookedSeat_RS.getString("Booked Seat");
                            upSt.execute("UPDATE `excursions` SET `Seat`=`Seat`+'" + Integer.parseInt(bookedSeat) + "' WHERE `ID` = '" + exID+ "'");
                            upSt.execute("UPDATE `booking` SET `Status`='Cancelled' WHERE `Booked By`='" + adminUserTable.getSelectionModel().getSelectedItem().getID() + "' AND `Excursion ID`='" + exID + "'AND `Status` = 'Booked'");
                        }

                        statement.execute("DELETE FROM `registration` WHERE `ID` = '"+adminUserTable.getSelectionModel().getSelectedItem().getID()+"'");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    dialog.close();
                    userBooking_StackPane.setVisible(false);
                }
            });
            content.setActions(closeButton, okayButton);

            dialog.show();

        }

    }

    public ObservableList<adminUser_info> getUsers(){
        userInfo = FXCollections.observableArrayList();
        return userInfo;
    }
}
