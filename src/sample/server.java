package sample;

import java.sql.*;
import java.util.ArrayList;

public class server {

    public String[] search_result(){
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
//        String[] words = {"STINGRAY CITY SANDBAR & BEACH BREAK", "TURTLES & STINGRAYS LAND & SEA ADVENTURE", "STINGRAY CITY & BARRIER REEF TWO STOP SNORKEL", "CAYMAN CULTURAL EXPRESS"};
        String[] dbresult = new String[results.size()];
        dbresult = results.toArray(dbresult);
        return dbresult;
    }


}
