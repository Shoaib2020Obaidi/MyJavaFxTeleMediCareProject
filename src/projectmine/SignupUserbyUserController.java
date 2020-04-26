package projectmine;

import UsersModel.Users;
import UsersModel.UsersDAO;
import java.net.URL;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class SignupUserbyUserController implements Initializable {

    @FXML
    private TextField userNameReg;
    @FXML
    private RadioButton male;
    private RadioButton other;
    @FXML
    private RadioButton female;
    @FXML
    private TextField locationReg;
    @FXML
    private Button signUpReg;
    @FXML
    private ImageView progress;
    @FXML
    private Button backBtn;
    @FXML
    private PasswordField passwordReg;
    @FXML
    private Button clearUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progress.setVisible(false);
    }

    @FXML
    private void signUp(ActionEvent event) {
        progress.setVisible(true);
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(3));
        pt.setOnFinished(ev -> {
            try {
                UsersDAO.insertUser(userNameReg.getText(), passwordReg.getText(), male.getText(), locationReg.getText());
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Record Cannot be Inserted " + e);
                e.printStackTrace();
            } catch (Exception ex) {
                Logger.getLogger(SignUpDoctorByAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "New User has been Created.");
            progress.setVisible(false);
        });
        pt.play();
    }
    
    public String getGender() {
        String gender = "";

        if (male.isSelected()) {
            gender = "Male";
        } else if (female.isSelected()) {
            gender = "female";
        } else if (other.isSelected()) {
            gender = "other";
        }
        return gender;
    }
    
    @FXML
    private void back(ActionEvent event) {
        signUpReg.getScene().getWindow().hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserLogin.fxml"));
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
    private void clearUsers(ActionEvent event) {
        userNameReg.clear();
        passwordReg.clear();
    }
}
