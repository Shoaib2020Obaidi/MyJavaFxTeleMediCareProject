package projectmine;

import DoctorsModel.DoctorsDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import javafx.scene.control.DatePicker;


public class SignupDoctor_ByDoctorController implements Initializable {
    public static boolean signal = false;
    
    public static String fullN;
    public static String speci;
    public static String drID;
    public static String clinicL;
    public static String daysAv;
    public static String timeAv;
    public static String moNo;
    public static String email;
    public static String doctorUserNameStr;
    public static String pass;

    @FXML
    private TextField timeAvailable;
    @FXML
    private Button signUpReg;
    @FXML
    private ImageView progress;
    @FXML
    private Button backBtn;
    @FXML
    private PasswordField password;
    @FXML
    private TextField fullName;
    @FXML
    private TextField speciality;
    @FXML
    private TextField clinicLocation;
    @FXML
    private TextField daysAvailableChoiceBox;
    @FXML
    private TextField userName;
    @FXML
    private TextField ID;
    @FXML
    private TextField mobileNumber;
    @FXML
    private TextField emailAddress;
    
    @FXML
    private Button clear;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progress.setVisible(false);
//        daysAvailableChoiceBox.getItems().add("Sunday");
//        daysAvailableChoiceBox.getItems().add("Monday");
//        daysAvailableChoiceBox.getItems().add("Tuesday");
//        daysAvailableChoiceBox.getItems().add("Wednesday");
//        daysAvailableChoiceBox.getItems().add("Thursday");
//        daysAvailableChoiceBox.getItems().add("Friday");
//        daysAvailableChoiceBox.getItems().add("Saturday");
    }

    @FXML
    private void signUp(ActionEvent event) {
        progress.setVisible(true);
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(4));
        pt.setOnFinished(ev -> {
            JOptionPane.showMessageDialog(null, "Your info have been added Successfully. Wait for the Admin's Confirmation to Sign in");
            progress.setVisible(false);
        });
        pt.play();

        // signal admin that the register button is pressed
        signal = true;

        // when register button is pressed, these values from text fields will be taken and stored into these variables
        fullN = fullName.getText();
        speci = speciality.getText();
        drID = ID.getText();
        clinicL = clinicLocation.getText();
        daysAv = daysAvailableChoiceBox.getText();
        timeAv = timeAvailable.getText();
        moNo = mobileNumber.getText();
        email = emailAddress.getText();
        doctorUserNameStr = userName.getText(); // beased on this value, reject of doctor by admin is gonna take place
        pass = password.getText();
    }

    // check the signal button, if it was true, signal admin that the doctor registration is done, else do nothing
    public int checkSignal() {
        if (signal == true) {
            return 1;
        } else {
            return 0;
        }
    }

    // taking the stored values from signUp method and returning the values to registerDrAfterAdminApproval method
    public String fullName() {
        return fullN;
    }

    public String speciality() {
        return speci;
    }

    public String doctorID() {
        return drID;
    }

    public String clinicLocation() {
        return clinicL;
    }

    public String daysAvailable() {
        return daysAv;
    }

    public String timesAvailable() {
        return timeAv;
    }

    public String mobileNo() {
        return moNo;
    }

    public String emailAdd() {
        return email;
    }

    // based on this return value rejection of Doctor is gonna take palce
    public String sendDrUN() {
        return doctorUserNameStr;
    }

    public String password() {
        return pass;
    }

    public void registerDrAfterApproval() {
        // if you don't make the signal false again, after approval when you login to admin page, it keeps on asking you for approval again and again
        signal = false;
        // taking the returned values from methods and storing them into variables
        String fn = fullName();
        String spe = speciality();
        String dID = doctorID();
        String cL = clinicLocation();
        String dA = daysAvailable();
        String tA = timesAvailable();
        String mN = mobileNo();
        String eA = emailAdd();
        String un = sendDrUN();
        String pss = password();
        try {
            DoctorsDAO.insertDoctor(fn, spe, dID, cL, dA, tA, mN, eA, un, pss);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Record Cannot be Inserted " + e);
            e.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Error Occured in Database While inserting Records " + ex);
        }
    }

    public void rejectDoctor() {
        signal = false;
    }

    @FXML
    private void back(ActionEvent event) {
        backBtn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorLogin.fxml"));
        try {
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Main Database");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Can't Load the Page!");
        }
    }

    @FXML
    private void clear() {
        fullName.clear();
        speciality.clear();
        ID.clear();
        clinicLocation.clear();
        daysAvailableChoiceBox.clear();
        timeAvailable.clear();
        mobileNumber.clear();
        emailAddress.clear();
        userName.clear();
        password.clear();
    }
}
