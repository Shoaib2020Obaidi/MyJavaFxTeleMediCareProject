package DoctorsModel;

import com.mysql.cj.conf.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class Doctors {

    private StringProperty fullNameProperty;
    private StringProperty specialityProperty;
    private SimpleStringProperty doctorIDProperty;
    private StringProperty clinicLocationProperty;
    private StringProperty daysAvailableProperty;
    private SimpleStringProperty timeAvailableProperty;
    private StringProperty mobileNoProperty;
    private StringProperty emailAddressProperty;
    private StringProperty userNameProperty;
    private StringProperty passwordProperty;
    
    public Doctors() {
        this.fullNameProperty = new SimpleStringProperty();
        this.specialityProperty = new SimpleStringProperty();
        this.doctorIDProperty = new SimpleStringProperty();
        this.clinicLocationProperty = new SimpleStringProperty();
        this.daysAvailableProperty = new SimpleStringProperty();
        this.timeAvailableProperty = new SimpleStringProperty();
        this.mobileNoProperty = new SimpleStringProperty();
        this.emailAddressProperty = new SimpleStringProperty();
        this.userNameProperty = new SimpleStringProperty();
        this.passwordProperty = new SimpleStringProperty();
    }
    
    public String getFullName() {
        return fullNameProperty.get();
    }
    public void setFullName(String fullName) {
        this.fullNameProperty.set(fullName);
    }
    public StringProperty getFullNameString() {
        return fullNameProperty;
    }
 
    public String getSpeciality() {
        return specialityProperty.get();
    }
    public void setSpeciality(String speciality) {
        this.specialityProperty.set(speciality);
    }
    public StringProperty getSpecialityString() {
        return specialityProperty;
    }

    public String getDoctorID() {
        return doctorIDProperty.get();
    }
    public void setDoctorID(String doctorID) {
        this.doctorIDProperty.set(doctorID);
    }
    public StringProperty getDoctorIDString() {
        return doctorIDProperty;
    }

    public String getClinicLocation() {
        return clinicLocationProperty.get();
    }
    public void setClinicLocation(String clinicLocation) {
        this.clinicLocationProperty.set(clinicLocation);
    }
    public StringProperty getClinicLocationString() {
        return clinicLocationProperty;
    }
    
    public String getDaysAvailable() {
        return daysAvailableProperty.get();
    }
    public void setDaysAvailable(String daysAvailable) {
        this.daysAvailableProperty.set(daysAvailable);
    }
    public StringProperty getDaysAvailableString() {
        return daysAvailableProperty;
    }
    
    public String getTimeAvailable() {
        return timeAvailableProperty.get();
    }
    public void setTimeAvailable(String timeAvailable) {
        this.timeAvailableProperty.set(timeAvailable);
    }
    public StringProperty getTimeAvailableString() {
        return timeAvailableProperty;
    }
    
    public String getMobileNo() {
        return mobileNoProperty.get();
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNoProperty.set(mobileNo);
    }
    public StringProperty getMobileNoString() {
        return mobileNoProperty;
    }
    
    // for Email Address
    public String getEmailAddress() {
        return emailAddressProperty.get();
    }
    public void setEmailAddress(String email) {
        this.emailAddressProperty.set(email);
    }
    public StringProperty getEmailAddressString() {
        return emailAddressProperty;
    }
    
    // for User Name
    public String getUserName() {
        return userNameProperty.get();
    }
    public void setUserName(String userName) {
        this.userNameProperty.set(userName);
    }
    public StringProperty getUserNameString() {
        return userNameProperty;
    }
    
    public String getPassword() {
        return passwordProperty.get();
    }
    public void setPassword(String password) {
        this.passwordProperty.set(password);
    }
    public StringProperty getPasswordString() {
        return passwordProperty;
    }
}
