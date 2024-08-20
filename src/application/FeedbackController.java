package application;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FeedbackController implements Initializable {

    @FXML
    private TextArea feedbackTextArea;

    @FXML
    private TableView<Feedback> feedbackTableView;

    private ObservableList<Feedback> feedbackList;
    
    @FXML
    private Button HomeButton;
    
    private int ID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        feedbackList = FXCollections.observableArrayList();
        
        // Create TableColumn instances
        TableColumn<Feedback, LocalDate> feedbackDateColumn = new TableColumn<>("Feedback Date");
        feedbackDateColumn.setPrefWidth(100);
        feedbackDateColumn.setCellValueFactory(new PropertyValueFactory<>("feedbackDate"));

        TableColumn<Feedback, LocalTime> feedbackTimeColumn = new TableColumn<>("Feedback Time");
        feedbackTimeColumn.setPrefWidth(100);
        feedbackTimeColumn.setCellValueFactory(new PropertyValueFactory<>("feedbackTime"));

        TableColumn<Feedback, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setPrefWidth(300);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Add the TableColumn instances to the TableView
        feedbackTableView.getColumns().addAll(feedbackDateColumn, feedbackTimeColumn, descriptionColumn);
        
        loadFeedbacks();
    }

    @FXML
    private void submitFeedback(ActionEvent event) {
        String description = feedbackTextArea.getText().trim();
        if (description.isEmpty()) {
            showAlert("Invalid Input", "Feedback description cannot be empty.");
            return;
        }

        LocalDate feedbackDate = LocalDate.now();
        LocalTime feedbackTime = LocalTime.now();

        // Assuming you have the traveller's ID
        int travellerId = ID; // Replace 1 with the actual traveller's ID

        Feedback feedback = new Feedback(feedbackDate, feedbackTime, description, travellerId);
        saveFeedbackToDatabase(feedback);
        feedbackList.add(feedback);
        feedbackTextArea.clear();
    }

    private void saveFeedbackToDatabase(Feedback feedback) {
    	String sql = "INSERT INTO Feedback (Feedback_Date, Feedback_Time, Description, TravellerID) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TravelWithUs", "root", "Mukarram@69");
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setDate(1, Date.valueOf(feedback.getFeedbackDate()));
            pstmt.setTime(2, Time.valueOf(feedback.getFeedbackTime()));
            pstmt.setString(3, feedback.getDescription());
            pstmt.setInt(4, ID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadFeedbacks() {
        String sql = "SELECT * FROM Feedback";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TravelWithUs", "root", "Mukarram@69");
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                LocalDate feedbackDate = rs.getDate("Feedback_Date").toLocalDate();
                LocalTime feedbackTime = rs.getTime("Feedback_Time").toLocalTime();
                String description = rs.getString("Description");
                int travellerId = rs.getInt("TravellerID");

                Feedback feedback = new Feedback(feedbackDate, feedbackTime, description, travellerId);
                feedbackList.add(feedback);
            }
            feedbackTableView.setItems(feedbackList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
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
