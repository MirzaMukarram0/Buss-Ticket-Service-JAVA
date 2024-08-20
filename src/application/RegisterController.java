package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class RegisterController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField locationField;

    private List<Traveller> travellers;

    public void setTravellers(List<Traveller> travellers) {
        if (travellers == null) {
            this.travellers = new ArrayList<>();
        } else {
            this.travellers = travellers;
        }
    }
    
    private Connection connect() throws Exception {
        String url = "jdbc:mysql://localhost:3306/TravelWithUs";
        String username = "root"; 
        String password = "Mukarram@69"; 
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }
    
    private void insertTravellerIntoDatabase(Traveller traveller) {
        String sql = "INSERT INTO Traveller (Name, Email, Password) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, traveller.getName());
            pstmt.setString(2, traveller.getEmail());
            pstmt.setString(3, traveller.getPassword());
            
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegister() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String location = locationField.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || location.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill in all fields.");
            return;
        }

        if (findTravellerByEmail(email) != null) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Email is already registered.");
            return;
        }

        Traveller newTraveller = new Traveller(name, email, password);
        travellers.add(newTraveller);
        insertTravellerIntoDatabase(newTraveller);
        showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Welcome, " + newTraveller.getName() + "!");
        
        goToLogin();
    }

    @FXML
    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();
            loginController.setTravellers(travellers);
            Stage stage = (Stage) nameField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    


    private Traveller findTravellerByEmail(String email) {
        for (Traveller traveller : travellers) {
            if (traveller.getEmail().equals(email)) {
                return traveller;
            }
        }
        return null;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
