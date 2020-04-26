package projectmine;

import DoctorsModel.Doctors;
import DoctorsModel.DoctorsDAO;
import MailUtility.JavaMailUtility;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;

public class ShowDoctorBeforeApprovalController {

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Doctors, String> nameCol;
    @FXML
    private TableColumn<Doctors, String> spacialityCol;
    @FXML
    private TableColumn<Doctors, String> IDCol;
    @FXML
    private TableColumn<Doctors, String> CliinicLocationCol;
    @FXML
    private TableColumn<Doctors, String> daysAvailableCol;
    @FXML
    private TableColumn<Doctors, String> timeAvailableCol;
    @FXML
    private TableColumn<Doctors, String> mobileNoCol;
    @FXML
    private TableColumn<Doctors, String> emailAddCol;
    @FXML
    private TableColumn<Doctors, String> userNameCol;
    @FXML
    private TableColumn<Doctors, String> passwordCol;
    @FXML
    private Button discardBtn;
    @FXML
    private Button approveBtn;
    @FXML
    private ImageView progress;
    
    // for getting email address of doctor from his/her textField and sending him/her Approval message accordingly
    SignupDoctor_ByDoctorController approveDr = new SignupDoctor_ByDoctorController();
    
    public void initialize() throws Exception {
        progress.setVisible(false);

        nameCol.setCellValueFactory(cellData -> cellData.getValue().getFullNameString());
        spacialityCol.setCellValueFactory(cellData -> cellData.getValue().getSpecialityString());
        IDCol.setCellValueFactory(cellData -> cellData.getValue().getDoctorIDString());
        CliinicLocationCol.setCellValueFactory(cellData -> cellData.getValue().getClinicLocationString()); // if ur id is integer, you need to create method
        daysAvailableCol.setCellValueFactory(cellData -> cellData.getValue().getDaysAvailableString());
        timeAvailableCol.setCellValueFactory(cellData -> cellData.getValue().getTimeAvailableString());
        mobileNoCol.setCellValueFactory(cellData -> cellData.getValue().getMobileNoString());
        emailAddCol.setCellValueFactory(cellData -> cellData.getValue().getEmailAddressString());
        userNameCol.setCellValueFactory(cellData -> cellData.getValue().getUserNameString());
        passwordCol.setCellValueFactory(cellData -> cellData.getValue().getPasswordString());

        ObservableList<Doctors> doctorsList = DoctorsDAO.getLatestRecord();
        populateTable(doctorsList);
    }

    private void populateTable(ObservableList<Doctors> doctorsList) {
        tableView.setItems(doctorsList);
    }

    @FXML
    private void approveRequest(ActionEvent event) {
        progress.setVisible(true);
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(2));
        pt.setOnFinished(ev -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != 0) {
                JOptionPane.showMessageDialog(null, "There is No Record to be Approved");
            } else {
                JOptionPane.showMessageDialog(null, "The Record Approved Successfully and a Confirmation Email was Sent to him/her."); 
                try {
                    String email = approveDr.emailAdd();
                    JavaMailUtility.sendMail(email); // method to send Dr an Approval Message
                } catch (MessagingException ex) {
                    JOptionPane.showMessageDialog(null, "Problem Occured While Sending Email");
                }
            }
            progress.setVisible(false);
            approveBtn.getScene().getWindow().hide();
        });
        pt.play();
    }

    @FXML
    private void discardRequest(ActionEvent event) throws Exception {
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(2));
        pt.setOnFinished(ev -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Doctors tvDt = (Doctors) tableView.getSelectionModel().getSelectedItem();
                try {
                    DoctorsDAO.deleteDoctor(tvDt.getFullName());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Problem Occured While Deleting the Record");
                }
                tableView.getItems().remove(selectedIndex);
                JOptionPane.showMessageDialog(null, "The Record Rejected Successfully");
            }
            discardBtn.getScene().getWindow().hide();
        });
        pt.play();
    }
}
