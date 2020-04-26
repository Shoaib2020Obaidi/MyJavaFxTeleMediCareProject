package projectmine;

import DBConnection.DBHandler;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class DoctorLoginController implements Initializable {
    public static String drUserName;
    
    @FXML
    public TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;
    @FXML
    private Hyperlink forgotPassword;
    @FXML
    private RadioButton rememberMe;
    @FXML
    private ImageView progress;
    @FXML
    private Button back;
    @FXML
    private Button signup;

    private DBHandler handler;
    private Connection connection;
    private PreparedStatement pst;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progress.setVisible(false);
        handler = new DBHandler();
    }        

    @FXML
    private void login(ActionEvent event) {
        progress.setVisible(true);
        PauseTransition pt = new PauseTransition();
        pt.setDuration(javafx.util.Duration.seconds(1));

        // Retreive Data from Database (freerider schema, doctor table)
        connection = handler.getConnection();
        String query = "SELECT * FROM doctor where userName =? and password =?";

        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, userName.getText());
            pst.setString(2, password.getText());
            ResultSet rs = pst.executeQuery();

            int count = 0;
            while (rs.next()) {
                count = count + 1;
            }
            if (userName.getText().isEmpty() && password.getText().isEmpty()) {
                progress.setVisible(false);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning...");
                alert.setContentText("User Name and Password are Required!");
                alert.setHeaderText(null);
                alert.showAndWait();
            } else if (userName.getText().isEmpty()) {
                progress.setVisible(false);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning...");
                alert.setContentText("User Name is Required!");
                alert.setHeaderText(null);
                alert.showAndWait();
            } else if (password.getText().isEmpty()) {
                progress.setVisible(false);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning...");
                alert.setContentText("Password is Required!");
                alert.setHeaderText(null);
                alert.showAndWait();
            } else if (count == 1) {
                pt.setOnFinished(ev -> {
                    login.getScene().getWindow().hide();
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DoctorMainPage.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Shoaib Shahzad Obaidi Database");
                        stage.setScene(new Scene(root1));
                        stage.show();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Can't Load the Page");
                    }
                });
                pt.play();
            } else {
                progress.setVisible(true);
                pt.setDuration(javafx.util.Duration.seconds(2));
                pt.setOnFinished(ev -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Miss Matched...");
                    alert.setContentText("User Name or Password Miss Matched!");
                    alert.setHeaderText(null);
                    alert.show();
                    progress.setVisible(false);
                });
                pt.play();
            }
        } catch (SQLException el) {
            el.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException el) {
                el.printStackTrace();
            }
        }
        drUserName = userName.getText(); // save user name of Doctor into a variable to pass it after
    }

    public static String passUserName(){
        return drUserName;
    }
    
    public String passDrName(){
        return userName.getText();
    }
    
    @FXML
    private void signup(ActionEvent event) {
        signup.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignupDoctorByDoctor.fxml"));
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
    private void back(ActionEvent event) {
        back.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChooseWhoRU.fxml"));
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
}
