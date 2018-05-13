package sample.Controller;

import javafx.beans.property.SimpleStringProperty;

public class adminWaiting_info {
    private SimpleStringProperty  Booking_ID, Excursion_Name, Excursion_ID, Port_ID, Booked_Seat, Booked_By, Booked_Date;

    public adminWaiting_info(String Booking_ID, String Excursion_Name, String Excursion_ID, String Port_ID, String Booked_Seat, String Booked_By, String Booked_Date){
        this.Booking_ID = new SimpleStringProperty(Booking_ID);
        this.Excursion_Name = new SimpleStringProperty(Excursion_Name);
        this.Excursion_ID = new SimpleStringProperty(Excursion_ID);
        this.Port_ID = new SimpleStringProperty(Port_ID);
        this.Booked_Seat = new SimpleStringProperty(Booked_Seat);
        this.Booked_By = new SimpleStringProperty(Booked_By);
        this.Booked_Date = new SimpleStringProperty(Booked_Date);
    }

    public String getBooking_ID(){
        return Booking_ID.get();
    }

    public void setBooking_ID(SimpleStringProperty Booking_ID){
        this.Booking_ID = Booking_ID;
    }

    public String getBooked_By(){
        return Booked_By.get();
    }

    public void setBooked_By(SimpleStringProperty Booked_By){
        this.Booked_By = Booked_By;
    }

    public String getExcursion_ID() {
        return Excursion_ID.get();
    }

    public void setExcursion_ID(SimpleStringProperty Excursion_ID) {
        this.Excursion_ID = Excursion_ID;
    }

    public String getPort_ID() {
        return Port_ID.get();
    }

    public void setPort_ID(SimpleStringProperty Port_ID) {
        this.Port_ID = Port_ID;
    }

    public String getExcursion_Name() {
        return Excursion_Name.get();
    }

    public void setExcursion_Name(SimpleStringProperty Excursion_Name) {
        this.Excursion_Name = Excursion_Name;
    }

    public String getBooked_Seat() {
        return Booked_Seat.get();
    }

    public void setBooked_Seat(SimpleStringProperty Booked_Seat) {
        this.Booked_Seat = Booked_Seat;
    }

    public String getBooked_Date() {
        return Booked_Date.get();
    }

    public void setBooked_Date(SimpleStringProperty Booked_Date) {
        this.Booked_Date = Booked_Date;
    }
}
