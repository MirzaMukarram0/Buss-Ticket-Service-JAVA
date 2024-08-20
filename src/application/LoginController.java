package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import java.sql.ResultSet;
import java.util.List;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passwordField;

    private List<Traveller> travellers;
    
    public int travellerid;

    public void setTravellers(List<Traveller> travellers) {
        this.travellers = travellers;
    }
    
    private Connection connect() throws Exception {
        String url = "jdbc:mysql://localhost:3306/TravelWithUs";
        String username = "root"; 
        String password = "Mukarram@69"; 
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }

    @FXML
    private void handleLogin() throws IOException {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter your email and password.");
            return;
        }

        Traveller traveller = findTravellerByEmail(email);

        if (traveller != null && traveller.getPassword().equals(password)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + traveller.getName() + "!");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Parent root = loader.load();

            HomeController controller = loader.getController();
            controller.setTravellerid(travellerid);
            // Set any necessary data or configurations in the controller

            Stage stage = (Stage) loginButton.getScene().getWindow(); // Assuming loginButton is the button used to trigger login
            stage.setScene(new Scene(root));
            stage.setTitle("Welcome to Your Dashboard");
            stage.show();

            // Navigate to the main application page or dashboard
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect email or password.");
        }
    }

    @FXML
    private void goToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
            Parent root = loader.load();
            RegisterController registerController = loader.getController();
            registerController.setTravellers(travellers);
            Stage stage = (Stage) emailField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Traveller findTravellerByEmail(String email) {
        Traveller traveller = null;
        String sql = "SELECT * FROM Traveller WHERE Email = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                	travellerid=rs.getInt("TravellerID");
                    traveller = new Traveller(rs.getString("Name"), rs.getString("Email"), rs.getString("Password"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return traveller;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
