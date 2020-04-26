
package projectmine;

import DBConnection.DBHandler;
import DoctorsModel.Doctors;
import DoctorsModel.DoctorsDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class SignUpDoctorByDoctorController implements Initializable {

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
    private TextField daysAvailable;
    @FXML
    private TextField clinicLocation;
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

    private DBHandler handler;
    private Connection connection;
    private PreparedStatement pst;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progress.setVisible(false);
        handler = new DBHandler();
    }    

    @FXML
    private void signUp(ActionEvent event) {
        progress.setVisible(true);
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(2));
        pt.setOnFinished(ev -> {
            try {
                DoctorsDAO.insertDoctor(fullName.getText(), speciality.getText(), ID.getText(), clinicLocation.getText(), daysAvailable.getText(), timeAvailable.getText(), mobileNumber.getText(), emailAddress.getText(), userName.getText(), password.getText());
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Record Cannot be Inserted " + e);
                e.printStackTrace();

            } catch (Exception ex) {
                Logger.getLogger(SignUpDoctorByAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "The Record Inserted Successfully");
            progress.setVisible(false);
            
            backBtn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorLogin.fxml"));
        try {
            Parent root = (Parent)loader.load();
            Stage stage = new Stage();
            stage.setTitle("Main Database");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Can't Load the Page!");
        }
        
        });
        pt.play();
    }

    @FXML
    private void back(ActionEvent event) {
        backBtn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorLogin.fxml"));
        try {
            Parent root = (Parent)loader.load();
            Stage stage = new Stage();
            stage.setTitle("Main Database");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Can't Load the Page!");
        }
    }

    @FXML
    private void clear(ActionEvent event) {
        fullName.clear();
        speciality.clear();
        ID.clear();
        clinicLocation.clear();
        daysAvailable.clear();
        timeAvailable.clear();
        mobileNumber.clear();
        userName.clear();
        password.clear();
    }
}
