package UsersModel;

import com.mysql.cj.conf.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class Users {
    // when you add records, it displays the records on the table view
    private StringProperty nameProperty;
    private StringProperty passwordProperty;
    private StringProperty genderProperty;
    private StringProperty nationalIDProperty;

    
    public Users() {
        this.nameProperty = new SimpleStringProperty();
        this.passwordProperty = new SimpleStringProperty();
        this.genderProperty = new SimpleStringProperty();
        this.nationalIDProperty = new SimpleStringProperty();
    }

    public String getName() {
        return nameProperty.get();
    }

    public void setFirstName(String name) {
        this.nameProperty.set(name);
    }

    public StringProperty getNameString() {
        return nameProperty;
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

    public String getGender() {
        return genderProperty.get();
    }

    public void setGender(String gender) {
        this.genderProperty.set(gender);
    }

    public StringProperty getGenderString() {
        return genderProperty;
    }
    
    public String getNationalID() {
        return nationalIDProperty.get();
    }

    public String setNationalID() {
        return nationalIDProperty.get();
    }
    
    public void setNationalID(String nationalID) {
        this.nationalIDProperty.set(nationalID);
    }

    public StringProperty getNationalIDString() {
        return nationalIDProperty;
    }
}
