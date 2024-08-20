package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

import javafx.event.ActionEvent;

public class CheckoutController {

    @FXML
    private TextField cardfield;

    @FXML
    private TextField cvvfield;

    @FXML
    private DatePicker exdatefield;

    @FXML
    private Button paybtn;

    @FXML
    void PayandCheck(ActionEvent event) throws IOException {
    	String cardNumber = cardfield.getText();
        String cvv = cvvfield.getText();
        LocalDate expirationDate = exdatefield.getValue();

        if (cardNumber.isEmpty() || cvv.isEmpty() || expirationDate == null) {
            showAlert("Invalid Input", "Please fill in all fields.");
            return;
        }

        if (cardNumber.length() != 14 || !cardNumber.matches("\\d+")) {
            showAlert("Invalid Input", "Card number must be 14 digits.");
            cardfield.clear();
            return;
        }

        if (cvv.length() != 3 || !cvv.matches("\\d+")) {
            showAlert("Invalid Input", "CVV must be 3 digits.");
            cvvfield.clear();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Payment Completed");
        alert.setHeaderText(null);
        alert.setContentText("Payment is done and ticket booked successfully!");
        alert.showAndWait();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = loader.load();

        HomeController controller = loader.getController();
        // Set any necessary data or configurations in the controller

        Stage stage = (Stage) paybtn.getScene().getWindow(); // Assuming loginButton is the button used to trigger login
        stage.setScene(new Scene(root));
        stage.setTitle("Search Busses");
        stage.show();
        
        
                           // Change status of booking 
       
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
