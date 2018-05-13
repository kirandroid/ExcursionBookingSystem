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

public class adminUser_Controller implements Initializable {
    private static String ID, First_Name, Last_Name, Email, Cabin, Gender, Joined_Date;
    private static ObservableList<adminUser_info> userInfo;

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
    }

    public ObservableList<adminUser_info> getUsers(){
        userInfo = FXCollections.observableArrayList();
        return userInfo;
    }
}
