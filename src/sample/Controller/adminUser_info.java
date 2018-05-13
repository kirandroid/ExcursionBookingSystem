package sample.Controller;

import javafx.beans.property.SimpleStringProperty;

public class adminUser_info {
    private SimpleStringProperty ID, First_Name, Last_Name, Email,Cabin, Gender, Joined_Date;

    public adminUser_info(String ID, String First_Name, String Last_Name, String Email, String Cabin, String Gender, String Joined_Date){
        this.ID = new SimpleStringProperty(ID);
        this.First_Name = new SimpleStringProperty(First_Name);
        this.Last_Name = new SimpleStringProperty(Last_Name);
        this.Email = new SimpleStringProperty(Email);
        this.Cabin = new SimpleStringProperty(Cabin);
        this.Gender = new SimpleStringProperty(Gender);
        this.Joined_Date = new SimpleStringProperty(Joined_Date);
    }

    public String getID() {
        return ID.get();
    }

    public void setID(SimpleStringProperty ID) {
        this.ID = ID;
    }

    public String getFirst_Name() {
        return First_Name.get();
    }

    public void setFirst_Name(SimpleStringProperty First_Name) {
        this.First_Name = First_Name;
    }

    public String getLast_Name() {
        return Last_Name.get();
    }

    public void setLast_Name(SimpleStringProperty Last_Name) {
        this.Last_Name = Last_Name;
    }

    public String getEmail() {
        return Email.get();
    }

    public void setEmail(SimpleStringProperty Email) {
        this.Email = Email;
    }

    public String getCabin() {
        return Cabin.get();
    }

    public void setCabin(SimpleStringProperty Cabin) {
        this.Cabin = Cabin;
    }

    public String getGender(){
        return  Gender.get();
    }

    public void setGender(SimpleStringProperty Gender){
        this.Gender = Gender;
    }

    public String getJoined_Date(){
        return Joined_Date.get();
    }

    public void setJoined_Date(SimpleStringProperty Joined_Date){
        this.Joined_Date = Joined_Date;
    }
}
