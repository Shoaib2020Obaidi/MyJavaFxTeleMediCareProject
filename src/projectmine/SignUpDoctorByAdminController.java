package projectmine;

import DBConnection.DBHandler;
import DoctorsModel.Doctors;
import DoctorsModel.DoctorsDAO;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

public class SignUpDoctorByAdminController {

    @FXML
    private Button signUpReg;
    @FXML
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
    private ChoiceBox<String> daysAvailable;
    @FXML
    private TextField timeAvailable;
    @FXML
    private TextField mobileNumber;
    @FXML
    private TextField emailAddress;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    @FXML
    public TableView tableView;
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
    private Button updateRecord;
    @FXML
    private Button deleteRecord;
    @FXML
    private Button clear;

    public static ObservableList<Doctors> data_table;

    public void initialize() throws Exception {
        progress.setVisible(false);

        daysAvailable.getItems().add("Sunday");
        daysAvailable.getItems().add("Monday");
        daysAvailable.getItems().add("Tuesday");
        daysAvailable.getItems().add("Wednesday");
        daysAvailable.getItems().add("Thursday");
        daysAvailable.getItems().add("Friday");
        daysAvailable.getItems().add("Saturday");

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

        ObservableList<Doctors> doctorsList = DoctorsDAO.getAllRecords();
        populateTable(doctorsList);

        editableColumns();

    }

    private void editableColumns() throws Exception {

        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setFullName(e.getNewValue());
        });
        spacialityCol.setCellFactory(TextFieldTableCell.forTableColumn());
        spacialityCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setSpeciality(e.getNewValue());
        });
        IDCol.setCellFactory(TextFieldTableCell.forTableColumn());
        IDCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setDoctorID(e.getNewValue());
        });
        CliinicLocationCol.setCellFactory(TextFieldTableCell.forTableColumn());
        CliinicLocationCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setClinicLocation(e.getNewValue());
        });
        daysAvailableCol.setCellFactory(TextFieldTableCell.forTableColumn());
        daysAvailableCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setDaysAvailable(e.getNewValue());
        });
        timeAvailableCol.setCellFactory(TextFieldTableCell.forTableColumn());
        timeAvailableCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setTimeAvailable(e.getNewValue());
        });
        mobileNoCol.setCellFactory(TextFieldTableCell.forTableColumn());
        mobileNoCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setMobileNo(e.getNewValue());
        });
        emailAddCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailAddCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setEmailAddress(e.getNewValue());
        });
        userNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        userNameCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setUserName(e.getNewValue());
        });
        passwordCol.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPassword(e.getNewValue());
        });

        tableView.setEditable(true);
    }

    private void populateTable(ObservableList<Doctors> doctorsList) {
        tableView.setItems(doctorsList);
    }

    @FXML
    // when Register Button is clicked, this method is invoked
    private void signUp(ActionEvent event) throws Exception {
        progress.setVisible(true);
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(3));
        pt.setOnFinished(ev -> {
            try {
                DoctorsDAO.insertDoctor(fullName.getText(), speciality.getText(), ID.getText(), clinicLocation.getText(), daysAvailable.getValue(), timeAvailable.getText(), mobileNumber.getText(), emailAddress.getText(), userName.getText(), password.getText());
                JOptionPane.showMessageDialog(null, "A New Record Has Been Added");
                progress.setVisible(false);

                // the following line of codes are the 1st step for searching 
                ObservableList<Doctors> doctorsList = DoctorsDAO.getAllRecords();
                populateTable(doctorsList);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Record Cannot be Inserted " + e);
                e.printStackTrace();
            } catch (Exception ex) {
                System.out.println("Error Occured in Database");
            }
        });
        pt.play();
    }

    @FXML
    private void back(ActionEvent event) {
        backBtn.getScene().getWindow().hide();
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

    @FXML
    private void updateRecord(ActionEvent event) throws Exception {
        try {
            DoctorsDAO.updateDoctor(fullName.getText(), speciality.getText(), ID.getText(), clinicLocation.getText(), daysAvailable.getValue(), timeAvailable.getText(), mobileNumber.getText(), emailAddress.getText(), userName.getText(), password.getText());
            JOptionPane.showMessageDialog(null, "The Record Has Been Updated");
            ObservableList<Doctors> doctorsList = DoctorsDAO.getAllRecords();
            populateTable(doctorsList);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errro while Updating Record" + e);
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteRecord(ActionEvent event) throws Exception {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Doctors tvDt = (Doctors) tableView.getSelectionModel().getSelectedItem();
            DoctorsDAO.deleteDoctor(tvDt.getFullName());
            tableView.getItems().remove(selectedIndex);

            JOptionPane.showMessageDialog(null, "The Record Has Been Deleted");
            ObservableList<Doctors> doctorsList = DoctorsDAO.getAllRecords();
            populateTable(doctorsList);
        }
    }

    @FXML
    private void clear(ActionEvent event) {
        fullName.clear();
        speciality.clear();
        ID.clear();
        clinicLocation.clear();
        daysAvailable.getSelectionModel().clearSelection();
        timeAvailable.clear();
        mobileNumber.clear();
        userName.clear();
        password.clear();
    }

    @FXML
    private void onMouseClick(MouseEvent event) {
        Doctors tvDt = (Doctors) tableView.getSelectionModel().getSelectedItem();
        fullName.setText(tvDt.getFullName());
        speciality.setText(tvDt.getSpeciality());
        ID.setText(tvDt.getDoctorID());
        clinicLocation.setText(tvDt.getClinicLocation());
//        daysAvailable.setText(tvDt.getDaysAvailable()); // for integer
        timeAvailable.setText(tvDt.getTimeAvailable());       
        mobileNumber.setText(tvDt.getMobileNo());
        emailAddress.setText(tvDt.getEmailAddress());
        userName.setText(tvDt.getUserName());
        password.setText(tvDt.getPassword());
    }
}
