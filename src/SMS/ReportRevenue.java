package SMS;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ReportRevenue extends Application {
   
    private TableView<Appointment> tableView;
    private Label lblTotalRevenue;
    private Appointment appointment;
    private Service service;
    private Staff staff;
    
    private DatePicker dpStartDate;
    private DatePicker dpEndDate;
    private ComboBox<String> cmbServiceName;
    
    public ReportRevenue(Appointment appointment, Service service, Staff staff) {
    	this.appointment= appointment;
    	this.service = service;
    	this.staff = staff;
    }
    
    public ReportRevenue() {
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
   
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Generate Revenue Report");
       
	    	    
        String backgroundImage = "url('file:///Users/shrutigurungschool/eclipse-workspace/Assessment/image/remote-team.png')";
	    VBox vbox = new VBox();
	    vbox.setStyle("-fx-background-image: " + backgroundImage + "; " +
	            "-fx-background-position: right center; " +
	            "-fx-background-repeat: no-repeat; " +
	            "-fx-padding: 50px;");
	    
        
        Label lblServiceName = new Label("Service Name:");
        lblServiceName.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        cmbServiceName = new ComboBox<>();
        selectServiceName();
        
        Label lblStartDate = new Label("Start Date:");
        lblStartDate.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
        dpStartDate = new DatePicker();
       
        Label lblEndDate = new Label("End Date:");
        lblEndDate.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
        dpEndDate = new DatePicker();
        
        Button btnGenerateReport = new Button("Generate Report");
        btnGenerateReport.setOnAction(e -> generateReport());
        
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<Appointment, Integer> appointmentIdCol = new TableColumn<>("Appointment ID");
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
       
        
        TableColumn<Appointment, Double> totalCostCol = new TableColumn<>("Total Cost");
        totalCostCol.setCellValueFactory(new PropertyValueFactory<>("totalCost"));

        tableView.getColumns().addAll(appointmentIdCol, totalCostCol);
        
        lblTotalRevenue = new Label("Total Revenue: ");
        lblTotalRevenue.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica'; -fx-font-size: 16px");
        
        Button btnHome = new Button("Home");
        btnHome.setOnAction(e -> {
        	openStaffHomePage(primaryStage, staff);
        });
        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.setStyle("-fx-font-size: 16px; " +
	              "-fx-background-color: rgba(120, 163, 212, 0.8); " +
	              "-fx-text-fill: #FFFFFF; " +
	              "-fx-padding: 20px; " +
	              "-fx-background-radius: 10px; " +  // Corner radii
	              "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);" + // Lighter drop shadow effect
	              "-fx-alignment: center; " +  // Center alignment
	              "-fx-min-width: 300px; " +    // Smaller width
	              "-fx-min-height: 500px;");
        
        Label loginHeader = new Label("Report Revenue");
	    loginHeader.setStyle("-fx-text-fill: white; -fx-font-size: 30px; -fx-font-weight: bold;  -fx-font-family: 'Helvetica';"); // Style the header label

	    gridPane.add(loginHeader, 0, 0, 2, 1); // Add the header label spanning two columns
	 
        
        gridPane.add(lblServiceName, 0, 2);
        gridPane.add(cmbServiceName, 1, 2);
        gridPane.add(lblStartDate, 0, 3);
        gridPane.add(dpStartDate, 1, 3);
        gridPane.add(lblEndDate, 0, 4);
        gridPane.add(dpEndDate, 1, 4);
        gridPane.add(btnGenerateReport, 0, 5, 2, 1);
        gridPane.add(tableView, 0, 6, 2, 1);
        gridPane.add(lblTotalRevenue, 0, 7, 2, 1);
        gridPane.add(btnHome, 1, 8);
        
        
        vbox.getChildren().add(gridPane);
	    vbox.setAlignment(Pos.CENTER);

	    Scene scene = new Scene(vbox, 600, 400);
	    primaryStage.setScene(scene);
	    Image icon1 = new Image("file:///Users/shrutigurungschool/eclipse-workspace/Assessment/src/icons/icons8-service-48.png");
		primaryStage.getIcons().add(icon1);
		primaryStage.setTitle("Report Revenue");
	    primaryStage.setWidth(1200);
		primaryStage.setHeight(900);
	    primaryStage.setResizable(false);
	    primaryStage.show();
	   
    }

    
    
    private void generateReport() {
    	
    	String serviceName = cmbServiceName.getValue();
    	 if (serviceName == null || serviceName.isEmpty()) {
    	        showAlert("Please select a service.");
    	        return;
    	    }
    	String startDate = dpStartDate.getValue().toString();
    	String endDate = dpEndDate.getValue().toString();
        String URL= "jdbc:mysql://localhost:3306/sms";
        String USER = "root";
        String PASS = "Mynameisshrutigurung12c!";
        String query = "SELECT a.appointment_id, a.totalCost "
    		    + "FROM appointment a, Service s "
    		    + "WHERE a.service_id = s.service_id AND a.status='Completed'"
    		    + "AND s.serviceName = ? AND dateOfAppointment BETWEEN ? AND ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, serviceName);
            statement.setDate(2, Date.valueOf(startDate));
            statement.setDate(3, Date.valueOf(endDate));

            ResultSet resultSet = statement.executeQuery();
            tableView.getItems().clear();
            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("appointment_id");
                double totalCost = resultSet.getDouble("totalCost");
                tableView.getItems().add(new Appointment(appointmentId, null, service, null, null, null, null, null, totalCost, null, 0));
            }

            double totalRevenue = calculateTotalRevenue();
            lblTotalRevenue.setText("Total Revenue: Rs." + totalRevenue);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error generating report: " + e.getMessage());
        }
    }

    public void selectServiceName() {
        ObservableList<String> serviceNames = FXCollections.observableArrayList();
        
        String URL = "jdbc:mysql://localhost:3306/sms";
        String USER = "root";
        String PASS = "Mynameisshrutigurung12c!";
        String query = "SELECT serviceName FROM Service";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
            
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Iterate through the result set and add service names to the ObservableList
            while (resultSet.next()) {
                String serviceName = resultSet.getString("serviceName");
                serviceNames.add(serviceName);
            }
            
            cmbServiceName.setItems(serviceNames); // Set the items to the ComboBox
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error retrieving service names: " + e.getMessage());
        }
    }

    private double calculateTotalRevenue() {
        double totalRevenue = 0;
        for (Appointment appointment : tableView.getItems()) {
            totalRevenue += appointment.getTotalCost();
        }
        return totalRevenue;
    }

  
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void openStaffHomePage(Stage primaryStage, Staff staff) {
	    StaffHomePage staffHomePage = new StaffHomePage(staff); // Create an instance of the Staff Home page
	    try {
	    	staffHomePage.start(new Stage()); // Start the Staff Home page
	        primaryStage.close(); 
	    } catch (Exception e) {
	        System.out.println("Error opening Staff Home Page: " + e.getMessage());
	    }
	}
}
