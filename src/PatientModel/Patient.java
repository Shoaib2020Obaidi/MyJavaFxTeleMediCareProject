package PatientModel;

import com.mysql.cj.conf.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class Patient {

    // when you add records, it displays the records on the table view
    private StringProperty fullNameProperty;
    private StringProperty ageProperty;
    private StringProperty diseaseProperty;
    private StringProperty currentDateProperty;
    private StringProperty currentTimeProperty;
    private StringProperty addressProperty;
    private StringProperty mobileNoProperty;
    private StringProperty bloodTypeProperty;
    private StringProperty languageProperty;
    private StringProperty choosenDrProperty;
    
    public Patient() {
        this.fullNameProperty = new SimpleStringProperty();
        this.ageProperty = new SimpleStringProperty();
        this.diseaseProperty = new SimpleStringProperty();
        this.currentDateProperty = new SimpleStringProperty();
        this.currentTimeProperty = new SimpleStringProperty();
        this.addressProperty = new SimpleStringProperty();
        this.mobileNoProperty = new SimpleStringProperty();
        this.bloodTypeProperty = new SimpleStringProperty();
        this.languageProperty = new SimpleStringProperty();
        this.choosenDrProperty = new SimpleStringProperty();
    }

    // For Patient Full Name
    public String getPatFullName() {
        return fullNameProperty.get();
    }

    public void setPatFullName(String fullName) {
        this.fullNameProperty.set(fullName);
    }

    public StringProperty getPatientFullName() {
        return fullNameProperty;
    }

    // For Patient Age
    public String getPatAge() {
        return ageProperty.get();
    }

    public void setPatAge(String age) {
        this.ageProperty.set(age);
    }

    public StringProperty getPatientAge() {
        return ageProperty;
    }

    // For Patient Disease
    public String getPatDisease() {
        return diseaseProperty.get();
    }

    public void setPatDisease(String disease) {
        this.diseaseProperty.set(disease);
    }

    public StringProperty getPatientDisease() {
        return diseaseProperty;
    }

    // For Patient Joining Date
    public String getPatCurrentDate() {
        return currentDateProperty.get();
    }

    public void setPatCurrentDate(String currentDate) {
        this.currentDateProperty.set(currentDate);
    }

    public StringProperty getPatientCurrentDate() {
        return currentDateProperty;
    }

    // For Patient Joining Time
    public String getPatCurrentTime() {
        return currentTimeProperty.get();
    }

    public void setPatientCurrentTime(String currentTime) {
        this.currentTimeProperty.set(currentTime);
    }

    public StringProperty getPatientCurrentTime() {
        return currentTimeProperty;
    }

    // For Patient Address
    public String getPatAddress() {
        return addressProperty.get();
    }

    public void setPatAddress(String address) {
        this.addressProperty.set(address);
    }

    public StringProperty getPatientAddress() {
        return addressProperty;
    }

    // For Patient Mobile No
    public String getPatMobileNo() {
        return mobileNoProperty.get();
    }

    public void setPatMobileNo(String mobileNo) {
        this.mobileNoProperty.set(mobileNo);
    }

    public StringProperty getPatientMobileNo() {
        return mobileNoProperty;
    }

    // For Patient Blood Type
    public String getPatBloodType() {
        return bloodTypeProperty.get();
    }

    public void setPatBloodType(String bloodType) {
        this.bloodTypeProperty.set(bloodType);
    }

    public StringProperty getPatientBloodType() {
        return bloodTypeProperty;
    }

    // For Patient Language
    public String getPatLangauge() {
        return languageProperty.get();
    }

    public void setPatLanguage(String language) {
        this.languageProperty.set(language);
    }

    public StringProperty getPatientLanguage() {
        return languageProperty;
    }
    
    // For Patient to Choose his/her prefered Doctor
    public String getpreferedDoctor() {
        return choosenDrProperty.get();
    }

    public void setpreferedDoctor(String choosenDr) {
        this.choosenDrProperty.set(choosenDr);
    }

    public StringProperty getpreferedDoctorString() {
        return choosenDrProperty;
    }
}
