package projectmine;

import DBConnection.DBHandler;
import DoctorsModel.Doctors;
import DoctorsModel.DoctorsDAO;
import PatientModel.Patient;
import PatientModel.PatientDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class PatientsControlledByAdminController {

    @FXML
    private ImageView progress;
    @FXML
    private TextField fullName;
    @FXML
    private TextField disease;
    @FXML
    private TextField time;
    @FXML
    private TextField age;
    @FXML
    private TextField mobileNo;
    @FXML
    private Button addRecordBtn;
    @FXML
    private Button clearRecord;
    @FXML
    private DatePicker date;
    @FXML
    private ChoiceBox<String> bloodTypeChoiceBox;
    @FXML
    private ChoiceBox<String> languageChoiceBox;
    @FXML
    private Button back;
    @FXML
    private ComboBox<String> chooseDrCombo;
    @FXML
    private TextField address;
    @FXML
    private TableView tableView; // for patients

    @FXML
    private TableColumn<Patient, String> nameCol;
    @FXML
    private TableColumn<Patient, String> ageCol;
    @FXML
    private TableColumn<Patient, String> diseaseCol;
    @FXML
    private TableColumn<Patient, String> currentDateCol;
    @FXML
    private TableColumn<Patient, String> currentTimeCol;
    @FXML
    private TableColumn<Patient, String> mobileNoCol;
    @FXML
    private TableColumn<Patient, String> bloodTypeCol;
    @FXML
    private TableColumn<Patient, String> languageCol;
    @FXML
    private TableColumn<Patient, String> addressCol;
    @FXML
    private TableColumn<Patient, String> choosenDrCol; 
    
    @FXML
    private Button deleteBtn;
    @FXML
    private Button updateBtn;

    // columns for Doctors
    @FXML
    private TableView doctorTableView;
    @FXML
    private TableColumn<Doctors, String> drNameCol;
    @FXML
    private TableColumn<Doctors, String> drSpecialityCol;
    @FXML
    private TableColumn<Doctors, String> drNIDCol;
    @FXML
    private TableColumn<Doctors, String> drClinicLocCol;
    @FXML
    private TableColumn<Doctors, String> drDaysAvailCol;
    @FXML
    private TableColumn<Doctors, String> drTimesAvailCol;
    @FXML
    private TableColumn<Doctors, String> drMobileNoCol;
    @FXML
    private TableColumn<Doctors, String> drEmailAddCol;
    @FXML
    private TableColumn<Doctors, String> drUserNameCol;
    @FXML
    private TableColumn<Doctors, String> drPasswordCol;
    @FXML
    private Button deleteDrBtn;

    private DBHandler handler;
    private Connection connection;
    private PreparedStatement pst;

    public void initialize() throws Exception {
        handler = new DBHandler();
        progress.setVisible(false);

        bloodTypeChoiceBox.getItems().add("A");
        bloodTypeChoiceBox.getItems().add("B");
        bloodTypeChoiceBox.getItems().add("AB");
        bloodTypeChoiceBox.getItems().add("O");

        languageChoiceBox.getItems().add("English");
        languageChoiceBox.getItems().add("Arabic");
        languageChoiceBox.getItems().add("Russian");
        languageChoiceBox.getItems().add("French");

        // for Patients 
        nameCol.setCellValueFactory(cellData -> cellData.getValue().getPatientFullName());
        ageCol.setCellValueFactory(cellData -> cellData.getValue().getPatientAge());
        diseaseCol.setCellValueFactory(cellData -> cellData.getValue().getPatientDisease());
        currentDateCol.setCellValueFactory(cellData -> cellData.getValue().getPatientCurrentDate());
        currentTimeCol.setCellValueFactory(cellData -> cellData.getValue().getPatientCurrentTime());
        mobileNoCol.setCellValueFactory(cellData -> cellData.getValue().getPatientMobileNo());
        bloodTypeCol.setCellValueFactory(cellData -> cellData.getValue().getPatientBloodType());
        languageCol.setCellValueFactory(cellData -> cellData.getValue().getPatientLanguage());
        addressCol.setCellValueFactory(cellData -> cellData.getValue().getPatientAddress());
        choosenDrCol.setCellValueFactory(cellData -> cellData.getValue().getpreferedDoctorString());

        ObservableList<Patient> patientList = PatientDAO.getAllRecords();
        populateTable(patientList);

        // for Doctors
        drNameCol.setCellValueFactory(cellData -> cellData.getValue().getFullNameString());
        drSpecialityCol.setCellValueFactory(cellData -> cellData.getValue().getDoctorIDString());
        drNIDCol.setCellValueFactory(cellData -> cellData.getValue().getDoctorIDString());
        drClinicLocCol.setCellValueFactory(cellData -> cellData.getValue().getClinicLocationString());
        drDaysAvailCol.setCellValueFactory(cellData -> cellData.getValue().getDaysAvailableString());
        drTimesAvailCol.setCellValueFactory(cellData -> cellData.getValue().getTimeAvailableString());
        drMobileNoCol.setCellValueFactory(cellData -> cellData.getValue().getMobileNoString());
        drEmailAddCol.setCellValueFactory(cellData -> cellData.getValue().getEmailAddressString());
        drUserNameCol.setCellValueFactory(cellData -> cellData.getValue().getUserNameString());
        drPasswordCol.setCellValueFactory(cellData -> cellData.getValue().getPasswordString());

        ObservableList<Doctors> doctorsList = DoctorsDAO.getAllRecords();
        populateDoctorTable(doctorsList);

        fillComboBox();
    }

    // For Patients
    private void populateTable(ObservableList<Patient> patientList) {
        tableView.setItems(patientList);
    }

    // For Doctors
    private void populateDoctorTable(ObservableList<Doctors> doctorsList) {
        doctorTableView.setItems(doctorsList);
    }

    @FXML
    private void makeAppoint(ActionEvent event) {
        progress.setVisible(true);

        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(1));
        pt.setOnFinished(ev -> {
            try {
                PatientDAO.makeAppointment(fullName.getText(), Integer.parseInt(age.getText()), disease.getText(), date.getEditor().getText(), time.getText(), mobileNo.getText(), bloodTypeChoiceBox.getValue(), languageChoiceBox.getValue(), address.getText(), chooseDrCombo.getValue());
                ObservableList<Patient> patientList = PatientDAO.getAllRecords();
                populateTable(patientList);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Record Cannot be Inserted " + ex);
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "The Appointment Set Successfully...");
            progress.setVisible(false);
        });
        pt.play();
    }

    @FXML
    private void crearRecord(ActionEvent event) {
        fullName.clear();
        disease.clear();
        age.clear();
        address.clear();
        time.clear();
        mobileNo.clear();
        date.getEditor().clear();
        bloodTypeChoiceBox.getSelectionModel().clearSelection();
        languageChoiceBox.getSelectionModel().clearSelection();
        chooseDrCombo.getSelectionModel().clearSelection();
    }

    @FXML
    private void back(ActionEvent event) {
        back.getScene().getWindow().hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AfterLoggedIn.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Registration");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("Can't Load the Page");
        }
    }

    // Delete Patient
    @FXML
    private void delete(ActionEvent event) throws Exception {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Patient tvDt = (Patient) tableView.getSelectionModel().getSelectedItem();
            PatientDAO.deletePatient(tvDt.getPatFullName());
            tableView.getItems().remove(selectedIndex);

            JOptionPane.showMessageDialog(null, "The Record Has Been Deleted");
            ObservableList<Patient> patientList = PatientDAO.getAllRecords();
            populateTable(patientList);
        }
    }

    // Update Patient
    @FXML
    private void update(ActionEvent event) throws Exception {
        try {
            PatientDAO.updatePatient(fullName.getText(), Integer.parseInt(age.getText()), disease.getText(), date.getEditor().getText(), time.getText(), mobileNo.getText(), bloodTypeChoiceBox.getValue(), languageChoiceBox.getValue(), address.getText(), chooseDrCombo.getValue());
            JOptionPane.showMessageDialog(null, "The Record Has Been Updated");
            ObservableList<Patient> patientList = PatientDAO.getAllRecords();
            populateTable(patientList);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error while Updating Record" + e);
            e.printStackTrace();
        }
    }

    // Delete Doctor
    @FXML
    private void deleteDr(ActionEvent event) throws Exception {
        int selectedIndex = doctorTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Doctors tvDt = (Doctors) doctorTableView.getSelectionModel().getSelectedItem();
            DoctorsDAO.deleteDoctor(tvDt.getFullName());
            doctorTableView.getItems().remove(selectedIndex);

            JOptionPane.showMessageDialog(null, "The Record Has Been Deleted");
            ObservableList<Doctors> doctorsList = DoctorsDAO.getAllRecords();
            populateDoctorTable(doctorsList);
        }
    }

    public void fillComboBox() throws SQLException {
        connection = handler.getConnection();
        String sql = "SELECT * FROM doctor";
        try {
            pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                chooseDrCombo.getItems().addAll(rs.getString("fullName"));
            }
        } catch (Exception e) {

        }
    }


    @FXML
    private void onMouseClick(MouseEvent event) {
        Patient txtField = (Patient) tableView.getSelectionModel().getSelectedItem();
        fullName.setText(txtField.getPatFullName());
        disease.setText(txtField.getPatDisease());
        time.setText(txtField.getPatCurrentTime());
        age.setText(txtField.getPatAge());
        mobileNo.setText(txtField.getPatMobileNo());
        address.setText(txtField.getPatAddress());
//        bloodTypeChoiceBox.setText(txtField.getPatFullName());
//        languageChoiceBox.setText(txtField.getPatFullName());
    }
}
