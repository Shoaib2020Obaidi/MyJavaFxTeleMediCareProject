package projectmine;

import DBConnection.DBHandler;
import DoctorsModel.Doctors;
import MailUtility.JavaMailUtility;
import PatientModel.Patient;
import UsersModel.Users;
import UsersModel.UsersDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class AfterLoggedInController {
    // counters for user, doctor and patient counts
    int countUser = 0;
    int countDoctor = 0;
    int countPatient = 0;
    
    @FXML
    private Button backBtn;
    @FXML
    private Button createUserBtn;
    @FXML
    private Button createDoctor;
    @FXML
    private Button addPatientBtn;

    SignupDoctor_ByDoctorController approveDr = new SignupDoctor_ByDoctorController();
    
    private DBHandler handler;
    private Connection connection;
    private PreparedStatement pst;
    
    @FXML
    private Label userCount;
    @FXML
    private Label doctorCount;
    @FXML
    private Label patientCount;

    public void initialize() throws Exception {
        handler = new DBHandler();
        popupConfirmDrMsg();
        
        // to dispaly total number of users exist in the system
        int users = countUsersDB();
        userCount.setText(""+users);
        
        // to dispaly total number of doctors exist in the system
        int doctors = countDoctorsDB();
        doctorCount.setText(""+doctors);
         
        // to dispaly total number of patients exist in the system
        int patients = countPatientsDB();
        patientCount.setText(""+patients);
    }

    // popup confirmage message either to accept or reject the Doctor
    public void popupConfirmDrMsg() throws MessagingException {
        int check = approveDr.checkSignal();
        if (check == 1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText("The System is Having a Doctor Pending for your Approval");
            alert.setContentText("What Do You Want to Do with the Doctor? ");

            ButtonType approve = new ButtonType("Approve");
            ButtonType reject = new ButtonType("Reject");
            ButtonType showDr = new ButtonType("Show Doctor");
            ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(approve, reject, showDr, cancel);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == approve) {
                approveDr.registerDrAfterApproval(); // register
                String email = approveDr.emailAdd();
                System.out.println("The Email Address is: "+email); // get email address of doctor from his textFields and put it in send to email
                JavaMailUtility.sendMail(email); // method to send Dr an Approval Message
                JOptionPane.showMessageDialog(null, "A Confirmation Email Was Sent to the Doctor..."+email);
            } else if (result.get() == reject) {     // do not register (reject) - do nothing means ignor the request
                approveDr.rejectDoctor();
                System.out.println("Doctor is Rejected");
            } else if (result.get() == showDr) {
                approveDr.registerDrAfterApproval(); // register temporarily for digging about Doctor
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowDoctorBeforeApproval.fxml")); // then open another page
                try {
                    Parent root = (Parent) loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Pending User");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("Can't Load the Page!");
                }

            } else {
                System.out.println("Let the Doctor in as well");
            }
        } else {
            System.out.println("No Doctor is Pending for Approval");
        }
    }

    @FXML
    private void back(ActionEvent event) {
        backBtn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
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
    private void createUser(ActionEvent event) {
        createUserBtn.getScene().getWindow().hide(); // to hide the window while opening another
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Registration Page");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can't Load the Page...!");
        }
    }

    @FXML
    private void createDoctor(ActionEvent event) {
        createDoctor.getScene().getWindow().hide(); // to hide the window while opening another
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignUpDoctorByAdmin.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Registration Page");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can't Load the Page");
        }
    }

    @FXML
    private void addPatient(ActionEvent event) {
        addPatientBtn.getScene().getWindow().hide(); // to hide the window while opening another
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PatientsControlledByAdmin.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Patients Page Controlled By Admin");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can't Load the Page...!");
        }
    }
    
    // method to get total number of users from database
    public int countUsersDB() throws SQLException {
        connection = handler.getConnection();
        Statement stm = connection.createStatement();
        ResultSet rs3 = stm.executeQuery("SELECT COUNT(*) As 'count' FROM `project` ");
        while(rs3.next()){
            countUser = rs3.getInt("count");
            break;
        }
        return countUser;
    }
    
    // method to get total number of doctors from database
    public int countDoctorsDB() throws SQLException {
        connection = handler.getConnection();
        Statement stm = connection.createStatement();
        ResultSet rs3 = stm.executeQuery("SELECT COUNT(*) As 'count' FROM `doctor` ");
        while(rs3.next()){
            countDoctor = rs3.getInt("count");
            break;
        }
        return countDoctor;
    }
    
    // method to get total number of patients from database
    public int countPatientsDB() throws SQLException {
        connection = handler.getConnection();
        Statement stm = connection.createStatement();
        ResultSet rs3 = stm.executeQuery("SELECT COUNT(*) As 'count' FROM `patientappointment` ");
        while(rs3.next()){
            countPatient = rs3.getInt("count");
            break;
        }
        return countPatient;
    }
   
    // to send Email to Doctor After his/her Approval
    public static void sendMail(String recepient) throws MessagingException{
        Properties properties = new Properties();
        
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String myAccountEmail = "printf.shoaib@gmail.com";
        String password = "123Shahzad123";
        
        Session session = Session.getInstance(properties, new Authenticator(){
           @Override
           protected PasswordAuthentication getPasswordAuthentication(){
               return new PasswordAuthentication(myAccountEmail, password);
           }
        });
        
        Message message = prepareMessage(session, myAccountEmail, recepient);
        
        Transport.send(message);
        System.out.println("Email Sent Successfully");
    }
    
    private static Message prepareMessage(Session session, String myAccountEmail, String recepient){
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Approval Message From TMS");
            
            String htmlCode = "<h1>Hello Dear Doctor, Welcome to the Tele Medi Care System.</h1>  <h2>We're glad to have your with us.</h3> <h2>Now you can directly CONTACT with us, any time.</h3>";
            message.setContent(htmlCode, "text/html");
            
            return message;
        }catch(Exception e){
            System.out.println("Problem Occured While Sending the Email");
        }
        return null;
    }
}
