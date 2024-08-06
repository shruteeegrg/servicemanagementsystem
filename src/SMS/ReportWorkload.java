package SMS;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class ReportWorkload extends Application {

    private ComboBox<String> cmbServiceName;
    private TableView<WorkloadEntry> tableView;
    private Staff staff;

    public ReportWorkload(Staff staff) {
    	this.staff = staff;
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Workload Distribution Report");
       
	    
        
        String backgroundImage = "url('file:///Users/shrutigurungschool/eclipse-workspace/Assessment/image/remote-team.png')";
	    VBox vbox = new VBox();
	    vbox.setStyle("-fx-background-image: " + backgroundImage + "; " +
	            "-fx-background-position: right center; " +
	            "-fx-background-repeat: no-repeat; " +
	            "-fx-padding: 50px;");
	    

        cmbServiceName = new ComboBox<>();
        Label lblServiceName = new Label("Service Name:");
        lblServiceName.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        lblServiceName.setLabelFor(cmbServiceName);
        selectServiceName();

        tableView = new TableView<>();
      
        TableColumn<WorkloadEntry, Integer> staffIdCol = new TableColumn<>("Staff Worker ID");
        staffIdCol.setCellValueFactory(new PropertyValueFactory<>("workerId"));
        staffIdCol.setMinWidth(90);

        TableColumn<WorkloadEntry, Integer> completedAppointmentsCol = new TableColumn<>("Completed Appointments");
        completedAppointmentsCol.setCellValueFactory(new PropertyValueFactory<>("completedAppointments"));
        completedAppointmentsCol.setMinWidth(200);

        TableColumn<WorkloadEntry, Double> workloadDistributionCol = new TableColumn<>("Workload Distribution");
        workloadDistributionCol.setCellValueFactory(new PropertyValueFactory<>("workloadDistribution"));
        workloadDistributionCol.setMinWidth(200);
        
        tableView.getColumns().addAll(staffIdCol, completedAppointmentsCol, workloadDistributionCol);

        Button btnGenerateReport = new Button("Generate Report");
        btnGenerateReport.setOnAction(e -> generateReport());
        
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
        
        Label loginHeader = new Label("Workload Report");
	    loginHeader.setStyle("-fx-text-fill: white; -fx-font-size: 30px; -fx-font-weight: bold;  -fx-font-family: 'Helvetica';"); // Style the header label

	    gridPane.add(loginHeader, 0, 0, 2, 1); // Add the header label spanning two columns
        
        gridPane.addRow(2, lblServiceName,cmbServiceName);
        gridPane.addRow(3, new Label(" "), new Label(" ")); // Empty row for spacing
        gridPane.addRow(4, btnGenerateReport);
        gridPane.addRow(5, tableView);
        gridPane.addRow(6, btnHome);

        vbox.getChildren().add(gridPane);
	    vbox.setAlignment(Pos.CENTER);

	    Scene scene = new Scene(vbox, 600, 400);
	    primaryStage.setScene(scene);
	    Image icon1 = new Image("file:///Users/shrutigurungschool/eclipse-workspace/Assessment/src/icons/icons8-service-48.png");
		primaryStage.getIcons().add(icon1);
		primaryStage.setTitle("Workload Report");
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

        String url = "jdbc:mysql://localhost:3306/sms";
        String user = "root";
        String password = "Mynameisshrutigurung12c!";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // Get total staff assigned for the service
            int totalStaffAssigned = getTotalStaffAssigned(connection, serviceName);

            // Get completed appointments by each staff and calculate workload distribution
            ObservableList<WorkloadEntry> workloadEntries = FXCollections.observableArrayList();
            String query = "SELECT worker_id, COUNT(*) AS completed_appointments " +
                    "FROM Appointment " +
                    "WHERE service_id = (SELECT service_id FROM Service WHERE serviceName = ?) " +
                    "AND status = 'Completed' " +
                    "GROUP BY worker_id";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, serviceName);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int staffId = resultSet.getInt("worker_id");
                    int completedAppointments = resultSet.getInt("completed_appointments");
                    double workloadDistribution = (double) completedAppointments / totalStaffAssigned;
                    workloadEntries.add(new WorkloadEntry(staffId, completedAppointments, workloadDistribution));
                }
            }
            tableView.setItems(workloadEntries);

        } catch (SQLException e) {
            showAlert("Error generating report: " + e.getMessage());
        }
    }

    private int getTotalStaffAssigned(Connection conn, String serviceName) throws SQLException {
        String query = "SELECT COUNT(DISTINCT worker_id) AS totalStaff " +
                "FROM Appointment " +
                "WHERE service_id = (SELECT service_id FROM Service WHERE serviceName = ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, serviceName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("totalStaff");
            }
        }
        return 0;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class WorkloadEntry {
        private final int workerId;
        private final int completedAppointments;
        private final double workloadDistribution;

        public WorkloadEntry(int workerId, int completedAppointments, double workloadDistribution) {
            this.workerId = workerId;
            this.completedAppointments = completedAppointments;
            this.workloadDistribution = workloadDistribution;
        }

        public int getWorkerId() {
            return workerId;
        }

        public int getCompletedAppointments() {
            return completedAppointments;
        }

        public double getWorkloadDistribution() {
            return workloadDistribution;
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
    
    public void openStaffHomePage(Stage primaryStage, Staff staff) {
	    StaffHomePage staffHomePage = new StaffHomePage(staff); // Create an instance of the InsertPerson page
	    try {
	    	staffHomePage.start(new Stage()); // Start the InsertPerson page
	        primaryStage.close(); // Close the login window
	    } catch (Exception e) {
	        System.out.println("Error opening Staff Home page: " + e.getMessage());
	    }
	}
}
