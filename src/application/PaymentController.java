package application;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PaymentController {
    @FXML
    private Label bussno;

    @FXML
    private Label dest;

    @FXML
    private Button paybtn;

    @FXML
    private Label price;

    @FXML
    private Label src;

    @FXML
    private Label datelabel;

    @FXML
    private TextField seatsfield;

    public BussDetails busspay;

    @FXML
    void Checkout(ActionEvent event) throws IOException {
        String seatInput = seatsfield.getText();
        if (seatInput.isEmpty()) {
            showAlert("Invalid Input", "Seat number cannot be empty.");
            return;
        }

        try {
            int seatNumber = Integer.parseInt(seatInput);
            if (seatNumber < 1 || seatNumber > busspay.getCapacity()) {
                showAlert("Invalid Seat Number", "Seat number must be between 1 and " + busspay.getCapacity() + ".");
                return;
            }

            // Update seat status in the database
            if (bookSeat(busspay, seatNumber)) {
                showAlert("Seat Booked", "Seat is Booked Successfully!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
    	        alert.setTitle("Seat Booked");
    	        alert.setHeaderText(null);
    	        alert.setContentText("Seat is Booked Successfully!");
    	        alert.showAndWait();
    	        FXMLLoader loader = new FXMLLoader(getClass().getResource("PayandCheck.fxml"));
    	         Parent root = loader.load();
    	         CheckoutController CC = loader.getController();
    	         Stage stage = new Stage();
    	         stage.setTitle("Payment Confirmation");
    	         stage.setScene(new Scene(root));
    	         stage.show();
        	}
        	else {
        		 Alert alert = new Alert(Alert.AlertType.ERROR);
        	        alert.setTitle("Seat Already Occupied");
        	        alert.setHeaderText(null);
        	        alert.setContentText("Please select another seat!");
        	        alert.showAndWait();
        	        showAvailableSeats(busspay);
        	}
             

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid seat number.");
        }
    }
    private Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/TravelWithUs";
        String user = "root";
        String password = "Mukarram@69";
        return DriverManager.getConnection(url, user, password);
    }


    private boolean bookSeat(BussDetails buss, int seatNumber) {
        String sql = "UPDATE Seats s JOIN BussDetails b ON s.Buss_ID = b.Buss_ID SET s.Status = TRUE WHERE b.BussNo = ? AND s.Seat_ID = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, buss.getBussNo());
            pstmt.setInt(2, seatNumber);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAvailableSeats(BussDetails selectedBuss) {
        StringBuilder availableSeats = new StringBuilder();
        String sql = "SELECT Seat_ID FROM Seats s JOIN BussDetails b ON s.Buss_ID = b.Buss_ID WHERE b.BussNo = ? AND s.Status = FALSE";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, selectedBuss.getBussNo());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                availableSeats.append(rs.getInt("Seat_ID") + 1).append(" "); // +1 to display seat number starting from 1
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Available Seats");
        alert.setHeaderText(null);
        alert.setContentText("Available Seats: " + availableSeats.toString().trim());
        alert.showAndWait();
    }
    

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setSelectedBuss(TicketBooking book) {
        BussDetails selectedBuss = book.getBookedbuss();
        src.setText(selectedBuss.getSource());
        dest.setText(selectedBuss.getDestination());
        bussno.setText(selectedBuss.getBussNo());
        price.setText(String.format("$%.2f", selectedBuss.getPrice()));
        datelabel.setText(selectedBuss.getDate().toString());
        busspay = selectedBuss;
    }
}
