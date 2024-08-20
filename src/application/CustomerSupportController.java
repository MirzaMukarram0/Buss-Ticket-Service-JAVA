package application;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class CustomerSupportController {

    @FXML
    private TextArea customerInquiryTextArea;

    @FXML
    private TextArea representativeResponseTextArea;

    @FXML
    private ListView<String> documentationListView;

    @FXML
    private TextField documentationTextField;
    @FXML
    private  Button HomeButton;
    private ObservableList<String> documentationList;

    @FXML
    public void initialize() {
        documentationList = FXCollections.observableArrayList();
        documentationListView.setItems(documentationList);
    }

    @FXML
    private void handleAcknowledge() {
        String inquiry = customerInquiryTextArea.getText();
        if (inquiry.isEmpty()) {
            showAlert(AlertType.WARNING, "No Inquiry", "Please enter a customer inquiry.");
        } else {
            showAlert(AlertType.INFORMATION, "Acknowledged", "Customer inquiry acknowledged.");
        }
    }

    @FXML
    private void handleResolve() {
        String response = representativeResponseTextArea.getText();
        if (response.isEmpty()) {
            showAlert(AlertType.WARNING, "No Response", "Please enter a response.");
        } else {
            showAlert(AlertType.INFORMATION, "Resolved", "Issue resolved and response sent to customer.");
        }
    }

    @FXML
    private void handleFollowUp() {
        showAlert(AlertType.INFORMATION, "Follow Up", "Follow-up timeline provided to customer.");
    }

    @FXML
    private void handleAddDocumentation() {
        String documentation = documentationTextField.getText();
        if (documentation.isEmpty()) {
            showAlert(AlertType.WARNING, "No Documentation", "Please enter documentation.");
        } else {
            documentationList.add(documentation);
            documentationTextField.clear();
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

	@FXML public void GotoHome(ActionEvent event) throws IOException {
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
