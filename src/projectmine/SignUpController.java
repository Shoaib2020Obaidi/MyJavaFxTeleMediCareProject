package projectmine;

import DBConnection.DBHandler;
import PatientModel.Patient;
import PatientModel.PatientDAO;
import UsersModel.Users;
import UsersModel.UsersDAO;
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

public class SignUpController implements Initializable {
    @FXML
    private TextField userNameReg;
    @FXML
    private TextField passwordReg;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    private RadioButton other;
    @FXML
    private TextField locationReg;
    @FXML
    private Button signUpReg;
    @FXML
    private ImageView progress;

    // Declaration for Database
    private Connection connection;
    private DBHandler handler;
    private PreparedStatement pst;
    @FXML
    private Button backBtn;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Users, String> userNameCosl;
    @FXML
    private TableColumn<Users, String> passwordCols;
    @FXML
    private TableColumn<Users, String> genderCols;
    @FXML
    private TableColumn<Users, String> NIDCols;

    @FXML
    private Button clearUser;
    @FXML
    private Button updateUser;
    @FXML
    private Button deleteUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progress.setVisible(false);
        handler = new DBHandler();

        userNameCosl.setCellValueFactory(cellData -> cellData.getValue().getNameString());
        passwordCols.setCellValueFactory(cellData -> cellData.getValue().getPasswordString());
        genderCols.setCellValueFactory(cellData -> cellData.getValue().getGenderString());
        NIDCols.setCellValueFactory(cellData -> cellData.getValue().getNationalIDString());

        ObservableList<Users> userList = null;
        try {
            userList = UsersDAO.getAllRecords();
        } catch (Exception ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
        populateTable(userList);

        try {
            editableColumns();
        } catch (Exception ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void editableColumns() throws Exception {

        userNameCosl.setCellFactory(TextFieldTableCell.forTableColumn());
        userNameCosl.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setFirstName(e.getNewValue());
        });

        passwordCols.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordCols.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPassword(e.getNewValue());
        });

        genderCols.setCellFactory(TextFieldTableCell.forTableColumn());
        genderCols.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setGender(e.getNewValue());
        });

        NIDCols.setCellFactory(TextFieldTableCell.forTableColumn());
        NIDCols.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setNationalID(e.getNewValue());
        });

        tableView.setEditable(true);
    }

    private void populateTable(ObservableList<Users> userList) {
        tableView.setItems(userList);
    }

    @FXML
    private void signUp(ActionEvent event) {
        progress.setVisible(true);
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(3));
        pt.setOnFinished(ev -> {
            try {
                UsersDAO.insertUser(userNameReg.getText(), passwordReg.getText(), male.getText(), locationReg.getText());
                // the following line of codes are the 1st step for searching 
                ObservableList<Users> doctorList = UsersDAO.getAllRecords();
                populateTable(doctorList);
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
    private void clearUsers(ActionEvent event) {
        userNameReg.clear();
        passwordReg.clear();
        locationReg.clear();
    }

    @FXML
    private void updateUser(ActionEvent event) throws Exception {
        try {
            UsersDAO.updateUser(userNameReg.getText(), passwordReg.getText(), male.getText(), locationReg.getText());
            JOptionPane.showMessageDialog(null, "The Record Has Been Updated");

            // the following line of codes are the 1st step for searching 
            ObservableList<Users> userList = UsersDAO.getAllRecords();
            populateTable(userList);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errro while Updating Record" + e);
            e.printStackTrace();
            throw e;
        }
    }

    @FXML
    private void deleteUser(ActionEvent event) throws Exception {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Users tvDt = (Users) tableView.getSelectionModel().getSelectedItem();
            UsersDAO.deleteUser(tvDt.getName());
            tableView.getItems().remove(selectedIndex);

            JOptionPane.showMessageDialog(null, "The Record Has Been Deleted");

            // the following line of codes are the 1st step for searching 
            ObservableList<Users> userList = UsersDAO.getAllRecords();
            populateTable(userList);

        }
    }

    @FXML
    private void onMouseClick(MouseEvent event) {
        Users txtField = (Users) tableView.getSelectionModel().getSelectedItem();
        userNameReg.setText(txtField.getName());
        passwordReg.setText(txtField.getPassword());
        male.setText(txtField.getGender());

        locationReg.setText(txtField.getNationalID());
    }
}

