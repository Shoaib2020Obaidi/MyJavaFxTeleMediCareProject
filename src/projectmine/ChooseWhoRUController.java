
package projectmine;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ChooseWhoRUController implements Initializable {

    @FXML
    private Button adminBtn;
    @FXML
    private Button userBtn;
    @FXML
    private Button doctorBtn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void openAdminLogin(ActionEvent event) {
        adminBtn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
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
    private void openUserLogin(ActionEvent event) {
        userBtn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserLogin.fxml"));
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
    private void openDoctorLogin(ActionEvent event) {
        userBtn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorLogin.fxml"));
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
