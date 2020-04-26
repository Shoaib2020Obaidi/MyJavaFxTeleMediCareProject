
package projectmine;

import DoctorsModel.DoctorsDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class DoctorsControlledByUserController implements Initializable{

    @FXML
    private Button signUpReg;
    
    private ImageView progress;
    @FXML
    private Button backBtn;

    @FXML
    private TextField fullName;
    @FXML
    private TextField speciality;
    @FXML
    private TextField ID;
    @FXML
    private TextField clinicLocation;
    @FXML
    private DatePicker daysAvailable;
    @FXML
    private TextField mobileNumber;
    @FXML
    private TextField emailAddress;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    @FXML
    private Button clear;
    @FXML
    private ChoiceBox<String> timeAvailableChoic;

    public void initialize(URL url, ResourceBundle rb) {
        progress.setVisible(false);
        
        timeAvailableChoic.getItems().add("Sunday");
        timeAvailableChoic.getItems().add("Mon");
        timeAvailableChoic.getItems().add("Tue");
        timeAvailableChoic.getItems().add("Wed");
        timeAvailableChoic.getItems().add("Thu");
        timeAvailableChoic.getItems().add("Fri");
        
    }

    @FXML
    // when Register Button is clicked, this method is invoked
    private void signUp(ActionEvent event) throws Exception {
        progress.setVisible(true);
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(2));
        pt.setOnFinished(ev -> {
            try {
                DoctorsDAO.insertDoctor(fullName.getText(), speciality.getText(), ID.getText(), clinicLocation.getText(), daysAvailable.getEditor().getText(), timeAvailableChoic.getValue(), mobileNumber.getText(), emailAddress.getText(), userName.getText(), password.getText());
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Record Cannot be Inserted " + e);
                e.printStackTrace();

            } catch (Exception ex) {
                Logger.getLogger(SignUpDoctorByAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "The Record Inserted Successfully");
            progress.setVisible(false);
        });
        pt.play();
    }

    @FXML
    private void back(ActionEvent event) {
        backBtn.getScene().getWindow().hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserMainPage.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Registration");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("Can't Load the Page");
        }
    }

    @FXML
    private void clear(ActionEvent event) {
        fullName.clear();
        speciality.clear();
        ID.clear();
        clinicLocation.clear();
        mobileNumber.clear();
        userName.clear();
        password.clear();
    }
}
