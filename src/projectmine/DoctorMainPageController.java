package projectmine;

import DBUtil.DBUtil;
import PatientModel.Patient;
import PatientModel.PatientDAO;
import static PatientModel.PatientDAO.delete;
import static PatientModel.PatientDAO.getPatientObjects;
import static PatientModel.PatientDAO.str;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import static projectmine.DoctorLoginController.passUserName;

public class DoctorMainPageController {
    public static boolean signal = false; // signal for moving record from available patients to History Records 

    @FXML
    private Button back;
    @FXML
    private Button checkedBtn;
    @FXML
    private Button prescribeBtn;

    // For Available Patients
    @FXML
    public TableView tableView;

    @FXML
    private TableColumn<Patient, String> nameCol;
    @FXML
    private TableColumn<Patient, String> ageCol;
    @FXML
    private TableColumn<Patient, String> diseaseCol;
    @FXML
    private TableColumn<Patient, String> dateCol;
    @FXML
    private TableColumn<Patient, String> timeCol;
    @FXML
    private TableColumn<Patient, String> mobNoCol;
    @FXML
    private TableColumn<Patient, String> bloodTypeCol;
    @FXML
    private TableColumn<Patient, String> languageCol;
    @FXML
    private TableColumn<Patient, String> addressCol;

    // Patients History
    @FXML
    private TableView tableViewHistory;
    @FXML
    private TableColumn<Patient, String> nameHistoryCol;
    @FXML
    private TableColumn<Patient, String> ageHistoryCol;
    @FXML
    private TableColumn<Patient, String> diseaseHistoryCol;
    @FXML
    private TableColumn<Patient, String> dateHistoryCol;
    @FXML
    private TableColumn<Patient, String> timeHistoryCol;
    @FXML
    private TableColumn<Patient, String> mobNoHistoryCol;
    @FXML
    private TableColumn<Patient, String> bloodTypeHistoryCol;
    @FXML
    private TableColumn<Patient, String> languageHistoryCol;
    @FXML
    private TableColumn<Patient, String> addressHistoryCol;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField ageTextField;
    @FXML
    private TextField diseaseTextField;
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField timeTextField;
    @FXML
    private TextField mobileNoTextField;
    @FXML
    private TextField bloodTypeTextField;
    @FXML
    private TextField languageTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField choosenDrTextField;
    @FXML
    private Label doctorNameLabel;

    String dName = passUserName(); // invoking the doctor's name and storing it into a variable to dispaly it at the top his/her Main Page
    public void initialize() throws Exception {
        doctorNameLabel.setText("Welcome "+dName+" to the Tele-Medicare System");
        
        nameTextField.setText("Patient's Name"); // default display
        ageTextField.setVisible(false);
        diseaseTextField.setVisible(false);
        dateTextField.setVisible(false);        
        timeTextField.setVisible(false);        
        mobileNoTextField.setVisible(false);        
        bloodTypeTextField.setVisible(false);   
        languageTextField.setVisible(false);        
        addressTextField.setVisible(false);        
        choosenDrTextField.setVisible(false);        
        
        // for Available Patients
        nameCol.setCellValueFactory(cellData -> cellData.getValue().getPatientFullName());
        ageCol.setCellValueFactory(cellData -> cellData.getValue().getPatientAge());
        diseaseCol.setCellValueFactory(cellData -> cellData.getValue().getPatientDisease());
        dateCol.setCellValueFactory(cellData -> cellData.getValue().getPatientCurrentDate());
        timeCol.setCellValueFactory(cellData -> cellData.getValue().getPatientCurrentTime());
        mobNoCol.setCellValueFactory(cellData -> cellData.getValue().getPatientMobileNo());
        bloodTypeCol.setCellValueFactory(cellData -> cellData.getValue().getPatientBloodType());
        languageCol.setCellValueFactory(cellData -> cellData.getValue().getPatientLanguage());
        addressCol.setCellValueFactory(cellData -> cellData.getValue().getPatientAddress());
        // Displaying the Patients who have appointment with specifi Doctor
        ObservableList<Patient> patientLst = PatientDAO.getDrPatientsRecords();
        populateTablePatient(patientLst);

        // for Patients' History
        nameHistoryCol.setCellValueFactory(cellData -> cellData.getValue().getPatientFullName());
        ageHistoryCol.setCellValueFactory(cellData -> cellData.getValue().getPatientAge());
        diseaseHistoryCol.setCellValueFactory(cellData -> cellData.getValue().getPatientDisease());
        dateHistoryCol.setCellValueFactory(cellData -> cellData.getValue().getPatientCurrentDate());
        timeHistoryCol.setCellValueFactory(cellData -> cellData.getValue().getPatientCurrentTime());
        mobNoHistoryCol.setCellValueFactory(cellData -> cellData.getValue().getPatientMobileNo());
        bloodTypeHistoryCol.setCellValueFactory(cellData -> cellData.getValue().getPatientBloodType());
        languageHistoryCol.setCellValueFactory(cellData -> cellData.getValue().getPatientLanguage());
        addressHistoryCol.setCellValueFactory(cellData -> cellData.getValue().getPatientAddress());
        
        // to populate history table while page is opened
        ObservableList<Patient> patientHistoryLst = PatientDAO.getAllHistoryRecords();
        populateHistoryOfPatients(patientHistoryLst);
    }

    // populate available patients' table
    private void populateTablePatient(ObservableList<Patient> patientLst) {
        tableView.setItems(patientLst);
    }

    // populate patients history 
    private void populateHistoryOfPatients(ObservableList<Patient> patientHistory) {
        tableViewHistory.setItems(patientHistory);
    }

    // delete the available patient from doctors page
    @FXML
    private void checkedPatient() throws Exception {
        signal = true;
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Patient tvDt = (Patient) tableView.getSelectionModel().getSelectedItem();
            PatientDAO.deletePatient(tvDt.getPatFullName());
            tableView.getItems().remove(selectedIndex);
        }
        makeHistory(); // when the records of available patient is deleted, it goes to the history records
    }

    // method to insert into history table and display the records into that
    private void makeHistory() throws Exception {
        PatientDAO.insertIntopatientHistoryTable(nameTextField.getText(), Integer.parseInt(ageTextField.getText()), diseaseTextField.getText(), dateTextField.getText(), timeTextField.getText(), mobileNoTextField.getText(), bloodTypeTextField.getText(), languageTextField.getText(), addressTextField.getText(), choosenDrTextField.getText());
        ObservableList<Patient> patList = PatientDAO.getAllHistoryRecords();
        populateHistTable(patList); // to populate history table while patient is checked by doctor
    }

    // populate history table
    private void populateHistTable(ObservableList<Patient> pList) {
        tableViewHistory.setItems(pList);
    }
    
    // 
    public void showHistoryOfPatients() throws Exception {
        getDrPatientsDeletedRecords();
        ObservableList<Patient> patientHistoryLst = getDrPatientsDeletedRecords();
        populateHistoryOfPatients(patientHistoryLst);
    }

    @FXML
    private void prescribeToPatient(ActionEvent event) throws Exception {

    }

//    public String passDrName() {
//        return nameTextField.getText();
//    }
    // For History Records begins here
    /////////////////////////////////////////////////////////////////////////////
    public ObservableList<Patient> getDrPatientsDeletedRecords() throws Exception {
//        String str = passDrName();
        String name = nameTextField.getText();
        String sql = "SELECT * FROM patientshistory WHERE fullName = '" + name + "' ";
        try {
            ResultSet rsSet = DBUtil.dbExecute(sql);
            // we send this resultSet to another method and from that method we will get each emp object and we pass it to controller
            ObservableList<Patient> patientList = getPatientObjects(rsSet);
            return patientList;
        } catch (SQLException e) {
            System.out.println("Error Occured While Fetching the Records" + e);
            e.printStackTrace();
            throw e;
        }
    }

    // this is the method which we create object for every calss
    public static ObservableList<Patient> getPatientObjects(ResultSet rsSet) throws SQLException {
        try {
            ObservableList<Patient> patientList = FXCollections.observableArrayList();
            while (rsSet.next()) {
                Patient patient = new Patient();

                patient.setPatFullName(rsSet.getString("fullName"));
                patient.setPatAge(rsSet.getString("age"));
                patient.setPatDisease(rsSet.getString("disease"));
                patient.setPatCurrentDate(rsSet.getString("date"));
                patient.setPatientCurrentTime(rsSet.getString("time"));
                patient.setPatMobileNo(rsSet.getString("mobileNo"));
                patient.setPatBloodType(rsSet.getString("bloodType"));
                patient.setPatLanguage(rsSet.getString("language"));
                patient.setPatAddress(rsSet.getString("address"));
                patient.setpreferedDoctor(rsSet.getString("choosenDr"));

                patientList.add(patient);
            }
            return patientList;
        } catch (SQLException e) {
            System.out.println("Error Occured While Fetching Data");
            e.printStackTrace();
            throw e;
        }
    }
    /////////////////////////////////////////////////////////////////////////////

    @FXML
    private void DisplayColTextField(javafx.scene.input.MouseEvent event) {
        Patient txtField = (Patient) tableView.getSelectionModel().getSelectedItem();
        
        nameTextField.setText(txtField.getPatFullName());
        ageTextField.setText(txtField.getPatAge());
        diseaseTextField.setText(txtField.getPatDisease());
        dateTextField.setText(txtField.getPatCurrentDate());
        timeTextField.setText(txtField.getPatCurrentTime());
        mobileNoTextField.setText(txtField.getPatMobileNo());
        bloodTypeTextField.setText(txtField.getPatBloodType());
        languageTextField.setText(txtField.getPatLangauge());
        addressTextField.setText(txtField.getPatAddress());
        
    }

    @FXML
    private void back(ActionEvent event) {
        back.getScene().getWindow().hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DoctorLogin.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Doctor Page");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("Can't Load the Page");
        }
    }
}
