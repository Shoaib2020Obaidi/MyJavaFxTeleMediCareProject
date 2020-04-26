package projectmine;

import com.mysql.cj.conf.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class SignUpForPatientModel {
    private SimpleStringProperty fullName;
    private SimpleStringProperty NID;
    private SimpleStringProperty age;
    private SimpleStringProperty disease;
    private SimpleStringProperty joiningDate;
    private SimpleStringProperty joiningTime;
    private SimpleStringProperty address;
    private SimpleStringProperty mobileNo;
    private SimpleStringProperty bloodType;
    private SimpleStringProperty roomNo;
    private SimpleStringProperty dischargingDate;
    private SimpleStringProperty dischargingTime;
    
    public SignUpForPatientModel(){
        
        this.fullName = new SimpleStringProperty();
        this.NID = new SimpleStringProperty();
        this.age = new SimpleStringProperty();
        this.age = new SimpleStringProperty();
        this.disease = new SimpleStringProperty();
        this.joiningDate = new SimpleStringProperty();
        this.joiningTime = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.mobileNo = new SimpleStringProperty();
        this.bloodType = new SimpleStringProperty();
        this.roomNo = new SimpleStringProperty();
        this.dischargingDate = new SimpleStringProperty();
        this.dischargingTime = new SimpleStringProperty();
        
    }
    
    public String getFullName(){
        return fullName.get();
    }
    public void setFullName(String fullName){
        this.fullName.set(fullName);
    }
    
}
