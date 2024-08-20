package application;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Controller implements Initializable{

    @FXML
   
	public List<BussDetails> Busses=new ArrayList<>();

	@FXML
	private Button bookbtn;
    @FXML
    private DatePicker datefield;

    @FXML
    private TableView<BussDetails> displaytable;

    @FXML
    private Button findbtn;

    @FXML
    private TextField fromfield;

    @FXML
    private TextField tofield;
    @FXML
    private TableColumn<BussDetails, Date> datecolumn=new TableColumn<>("Date");

    @FXML
    private TableColumn<BussDetails, String> fromcolumn=new TableColumn<>("Source");
    
    @FXML
    private TableColumn<BussDetails, String> Busscolumn=new TableColumn<>("BussNo");
    
    @FXML
    private TableColumn<BussDetails, String> tocolumn=new TableColumn<>("Destination");
    @FXML
    private TableColumn<BussDetails, Double> pricecolumn=new TableColumn<>("Price");
    
    @FXML
    private Button HomeButton;
    
    public ObservableList<BussDetails> list=FXCollections.observableArrayList();
    
    private Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/TravelWithUs";
        String user = "root";
        String password = "Mukarram@69";
        return DriverManager.getConnection(url, user, password);
    }

   public void refreshtable() {

	   displaytable.setItems(list);
	    
	    }
   
    @FXML
    void findbuss(ActionEvent event) {
        list.clear();
        String From = fromfield.getText();
        String To = tofield.getText();
        LocalDate date = datefield.getValue();
        
        String query = "SELECT * FROM BussDetails WHERE Source = ? AND Destination = ? AND Date = ?";
        
        try (Connection conn = connect();
        	PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, From);
            pstmt.setString(2, To);
            pstmt.setDate(3, Date.valueOf(date));
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                BussDetails buss = new BussDetails(
                        rs.getString("BussNo"),
                        rs.getString("Source"),
                        rs.getString("Destination"),
                        rs.getDouble("Price"),
                        rs.getDate("Date").toLocalDate(),
                        rs.getInt("Capacity")
                );
                list.add(buss);
            }
            
            if (list.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Not Found", "Sorry! Preferred Buss Not Found");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        refreshtable();
        fromfield.clear();
        tofield.clear();
        datefield.setValue(null);
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    
    @FXML
    void BookTicket(ActionEvent event) throws IOException {

     BussDetails selectedBuss = displaytable.getSelectionModel().getSelectedItem();
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
	@FXML 
	public void GoToHome(ActionEvent event) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = loader.load();

        HomeController controller = loader.getController();
        // Set any necessary data or configurations in the controller

        Stage stage = (Stage) HomeButton.getScene().getWindow(); // Assuming loginButton is the button used to trigger login
        stage.setScene(new Scene(root));
        stage.setTitle("Search Busses");
        stage.show();
	}

	@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tocolumn.setMinWidth(150);
        fromcolumn.setMinWidth(150);
        Busscolumn.setMinWidth(150);
        datecolumn.setMinWidth(150);
        pricecolumn.setMinWidth(150);
        
        tocolumn.setCellValueFactory(new PropertyValueFactory<>("Destination"));
        fromcolumn.setCellValueFactory(new PropertyValueFactory<>("source"));
        Busscolumn.setCellValueFactory(new PropertyValueFactory<>("BussNo"));
        datecolumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        pricecolumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        
        displaytable.getColumns().addAll(Busscolumn, tocolumn, fromcolumn, datecolumn, pricecolumn);
    }
	
}
