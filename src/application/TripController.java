package application;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class TripController {

    @FXML
    private TextArea experienceTextArea;

    @FXML
    private ComboBox<String> socialMediaComboBox;
    
    @FXML
    private Button HomeButton;

    @FXML
    public void initialize() {
        socialMediaComboBox.setItems(FXCollections.observableArrayList("Twitter", "Instagram", "Facebook", "LinkedIn"));
    }

    @FXML
    private void handleShare() {
        String experience = experienceTextArea.getText();
        String platform = socialMediaComboBox.getValue();

        if (experience.isEmpty() || platform == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Incomplete Information");
            alert.setHeaderText(null);
            alert.setContentText("Please write your experience and select a social media platform.");
            alert.showAndWait();
        } else {
            // Logic to share the experience on the selected platform
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Share Successful");
            alert.setHeaderText(null);
            alert.setContentText("Your experience has been shared on " + platform + ".");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleCancel() {
        experienceTextArea.clear();
        socialMediaComboBox.setValue(null);
    }

	@FXML public void GoToHome(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = loader.load();
        HomeController controller = loader.getController();
        // Set any necessary data or configurations in the controller
        Stage stage = (Stage) HomeButton.getScene().getWindow(); // Assuming loginButton is the button used to trigger login
        stage.setScene(new Scene(root));
        stage.setTitle("Search Busses");
        stage.show();
	}
}
