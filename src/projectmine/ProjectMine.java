
package projectmine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProjectMine extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ChooseWhoRU.fxml")); // Real One
//        Parent root = FXMLLoader.load(getClass().getResource("DoctorMainPage.fxml")); // For Practice
        Scene scene = new Scene(root);
        
        // css
        String css = ProjectMine.class.getResource("Styles_1.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
