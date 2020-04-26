package projectmine;

import DBConnection.DBHandler;
import DoctorsModel.Doctors;
import DoctorsModel.DoctorsDAO;
import PatientModel.Patient;
import PatientModel.PatientDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class UsersMainPage_AfterLoginController {
    public static String myName;
    
    // TextFileds
    @FXML
    public TextField fullName;
    @FXML
    private TextField disease;
    @FXML
    private TextField age;
    @FXML
    private TextField address;
    @FXML
    private TextField time;
    @FXML
    private TextField mobileNo;
    @FXML
    private TextField emailAddress;
    
    // Buttons
    @FXML
    private Button addRecordBtn;
    @FXML
    private Button clearRecord;
    @FXML
    private Button back;

    // Progress - Loader
    @FXML
    private ImageView progress;

    @FXML
    private ChoiceBox<String> bloodTypeChoiceBox; // TextField
    @FXML
    private DatePicker date; // TextField
    @FXML
    private ChoiceBox<String> languageChoiceBox; // TextField

    @FXML
    private ComboBox<String> chooseDrCombo; // Database - For Doctors' Names

    public static ObservableList<Patient> data_table;

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Doctors, String> nameCol;
    @FXML
    private TableColumn<Doctors, String> spacialityCol;
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
    private TableView tableViewMine;
    @FXML
    private TableColumn<Patient, String> myNameCol1;
    @FXML
    private TableColumn<Patient, String> myDiseaseCol;
    @FXML
    private TableColumn<Patient, String> myDateCol;
    @FXML
    private TableColumn<Patient, String> myTimeCol;
    @FXML
    private TableColumn<Patient, String> myDrCol;

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

        // here at the last part, you choose the StringProperty - Doctors
        nameCol.setCellValueFactory(cellData -> cellData.getValue().getFullNameString());
        spacialityCol.setCellValueFactory(cellData -> cellData.getValue().getSpecialityString());
        CliinicLocationCol.setCellValueFactory(cellData -> cellData.getValue().getClinicLocationString());
        daysAvailableCol.setCellValueFactory(cellData -> cellData.getValue().getDaysAvailableString());
        timeAvailableCol.setCellValueFactory(cellData -> cellData.getValue().getTimeAvailableString());
        mobileNoCol.setCellValueFactory(cellData -> cellData.getValue().getMobileNoString());
        emailAddCol.setCellValueFactory(cellData -> cellData.getValue().getEmailAddressString());
        
        ObservableList<Doctors> doctorList = DoctorsDAO.getAllRecords();
        populateTable(doctorList);

        // For Patient - My Appointment Info
        myNameCol1.setCellValueFactory(cellData -> cellData.getValue().getPatientFullName());
        myDiseaseCol.setCellValueFactory(cellData -> cellData.getValue().getPatientDisease());
        myDateCol.setCellValueFactory(cellData -> cellData.getValue().getPatientCurrentDate());
        myTimeCol.setCellValueFactory(cellData -> cellData.getValue().getPatientCurrentTime());
        myDrCol.setCellValueFactory(cellData -> cellData.getValue().getpreferedDoctorString());
        ObservableList<Patient> patientLst = PatientDAO.getOneRecord();
        populateTablePatient(patientLst);
        
        tableView.refresh();
        fillComboBox();
    }

    // populate Doctor Table
    private void populateTable(ObservableList<Doctors> doctorsList) {
        tableView.setItems(doctorsList);
    }

    // populate Patient Table
    private void populateTablePatient(ObservableList<Patient> patientLst) {
        tableViewMine.setItems(patientLst);
        tableViewMine.refresh();
    }
    
    @FXML
    private void makeAppoint(ActionEvent event) throws Exception {
        progress.setVisible(true);

        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(4));
        pt.setOnFinished(ev -> {
            try {
                PatientDAO.makeAppointment(fullName.getText(), Integer.parseInt(age.getText()), disease.getText(), date.getEditor().getText(), time.getText(), mobileNo.getText(), bloodTypeChoiceBox.getValue(), languageChoiceBox.getValue(), address.getText(), chooseDrCombo.getValue());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Record Cannot be Inserted " + ex);
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "The Appointment Set Successfully...");
            progress.setVisible(false);
        });
        pt.play();

        myName = fullName.getText();
    }

    public static String passMyName() {
        return myName;
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
        emailAddress.clear();
    }

    @FXML
    private void back(ActionEvent event) {
        back.getScene().getWindow().hide();
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
}
