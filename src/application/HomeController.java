package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HomeController  implements Initializable{

    @FXML
    private TableView<BussDetails> busTable;

    @FXML
    private TableColumn<BussDetails, String> Busscolumn;

    @FXML
    private TableColumn<BussDetails, String> fromcolumn;

    @FXML
    private TableColumn<BussDetails, String> tocolumn;

    @FXML
    private TableColumn<BussDetails, Double> pricecolumn;

    @FXML
    private TableColumn<BussDetails, String> datecolumn;

    @FXML
    private TableColumn<BussDetails, Integer> seatsAvailableColumn;

    @FXML
    private Button bookBus;
    
    @FXML
    private Button FeedButton;
    
    @FXML
    private Button SupportButton;
    
    @FXML
    private Button SearchButton;
    
    @FXML
    private Button TripButton;
    
    public ObservableList<BussDetails> list=FXCollections.observableArrayList();
    
	public List<BussDetails> Busses=new ArrayList<>();
	
	private int travellerid;

	 
    @FXML
    void bookBus(ActionEvent event) throws IOException {
    	 BussDetails selectedBuss = busTable.getSelectionModel().getSelectedItem();
        TicketBooking book=new TicketBooking(selectedBuss );
        if (selectedBuss != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("payment.fxml"));
            Parent root = loader.load();

            PaymentController bookingController = loader.getController();
            bookingController.setSelectedBuss(book);
            bookingController.busspay=selectedBuss;
            Stage stage = new Stage();
            stage.setTitle("Booking Confirmation");
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a bus from the table.");
            alert.showAndWait();
        }
    }
    private Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/TravelWithUs";
        String user = "root";
        String password = "Mukarram@69";
        return DriverManager.getConnection(url, user, password);
    }
    @FXML
    private void populateTable() {
        String sql = "SELECT * FROM BussDetails";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String bussNo = rs.getString("BussNo");
                String source = rs.getString("Source");
                String destination = rs.getString("Destination");
                double price = rs.getDouble("Price");
                LocalDate date = rs.getDate("Date").toLocalDate();
                int capacity = rs.getInt("Capacity");

                BussDetails bussDetails = new BussDetails(bussNo, source, destination, price, date, capacity);
                list.add(bussDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Busscolumn.setCellValueFactory(new PropertyValueFactory<>("BussNo"));
        fromcolumn.setCellValueFactory(new PropertyValueFactory<>("source"));
        tocolumn.setCellValueFactory(new PropertyValueFactory<>("Destination"));
        datecolumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        pricecolumn.setCellValueFactory(new PropertyValueFactory<>("Price"));

        busTable.setItems(list);
    }
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 populateTable();
		
	}
	@FXML public void Searchbus(ActionEvent event)  throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("index.fxml"));
        Parent root = loader.load();

        Controller controller = loader.getController();
        controller.refreshtable();
        // Set any necessary data or configurations in the controller

        Stage stage = (Stage) SearchButton.getScene().getWindow(); // Assuming loginButton is the button used to trigger login
        stage.setScene(new Scene(root));
        stage.setTitle("Search Busses");
        stage.show();
	}
	public int getTravellerid() {
		return travellerid;
	}
	public void setTravellerid(int travellerid) {
		this.travellerid = travellerid;
	}
	
	@FXML 
	public void Feedbacks(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("feedback.fxml"));
        Parent root = loader.load();

        FeedbackController controller = loader.getController();
       controller.setID(travellerid);
        // Set any necessary data or configurations in the controller

        Stage stage = (Stage) SearchButton.getScene().getWindow(); // Assuming loginButton is the button used to trigger login
        stage.setScene(new Scene(root));
        stage.setTitle("Feedback Busses");
        stage.show();
	
	}
	@FXML public void Support(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("support.fxml"));
        Parent root = loader.load();

        CustomerSupportController controller = loader.getController();

        Stage stage = (Stage) SearchButton.getScene().getWindow(); // Assuming loginButton is the button used to trigger login
        stage.setScene(new Scene(root));
        stage.setTitle("Customer Support");
        stage.show();
	}
	@FXML public void tripdetail(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("trip.fxml"));
        Parent root = loader.load();

        TripController controller = loader.getController();

        Stage stage = (Stage) SearchButton.getScene().getWindow(); // Assuming loginButton is the button used to trigger login
        stage.setScene(new Scene(root));
        stage.setTitle("Customer Support");
        stage.show();
		
		
	}
	

}
