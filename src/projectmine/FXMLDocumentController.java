package projectmine;

import java.io.IOException;
import java.net.URL;
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
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField userName;
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
    private Button delete;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progress.setVisible(false);
        userName.setStyle("-fx-text-inner-color: #800517");
        password.setStyle("-fx-text-inner-color: #800517");
    }

    @FXML
    private void login(ActionEvent event) throws IOException {
        progress.setVisible(true);
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(2));

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
        } else if (userName.getText().equals("admin") && password.getText().equals("123") || userName.getText().equals("Admin") && password.getText().equals("123")) {
//            pt.setOnFinished(eve -> {
                login.getScene().getWindow().hide();
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AfterLoggedIn.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Administrator");
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Can't Load the Page "+e);
                }
//            });
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
    }

    private void openBorderLayout(ActionEvent event) {
        delete.getScene().getWindow().hide(); // to hide the window while opening another
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChooseWhoRU.fxml"));
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
    private void back(ActionEvent event) {
        back.getScene().getWindow().hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChooseWhoRU.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Registration");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("Can't Load the Page");
        }
    }
}
