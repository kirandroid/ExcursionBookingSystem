package sample;

import java.sql.*;
import java.util.ArrayList;

public class server {
    public static String UserID, UserFName, UserLName, UserEmail, UserCabin, UserGender;

    public String[] SearchResult_AutoComplete(){
        ArrayList<String> results = new ArrayList<String>();
        try {
//            Connection myConn = DriverManager.getConnection("jdbc:mysql://db4free.net:3307/ebs_ebs", "ebs_ebs", "rootroot"); connect to online database
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT `Name` FROM `excursions`");
            while (rs.next()){
                results.add(rs.getString(1));
//                String Name = rs.getString("Name");
//                System.out.println('"'+Name+'"'+',');
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        String[] words = {"STINGRAY CITY SANDBAR & BEACH BREAK", "TURTLES & SeTINGRAYS LAND & SEA ADVENTURE", "STINGRAY CITY & BARRIER REEF TWO STOP SNORKEL", "CAYMAN CULTURAL EXPRESS"};
        String[] dbresult = new String[results.size()];
        dbresult = results.toArray(dbresult);
        return dbresult;
    }

    public void Registration_Info(){
        sample.Controller.Login_Controller login_controller;
        login_controller = new sample.Controller.Login_Controller();
        try{
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs","root", "");
            Statement statement = myConn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT `ID`,`First Name`,`Last Name`,`Email`,`Cabin Number`,`Gender` FROM `registration` WHERE `Email` ='"+login_controller.loggedUsername+"'");
            while (rs.next()){
                UserID = rs.getString("ID");
                UserFName = rs.getString("First Name");
                UserLName = rs.getString("Last Name");
                UserEmail = rs.getString("Email");
                UserCabin = rs.getString("Cabin Number");
                UserGender = rs.getString("Gender");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    }

