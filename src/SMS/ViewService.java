package SMS;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewService extends Application {

	private Customer customer;
	
	public ViewService() {
	    // Default constructor
	}

    public ViewService(Customer customer) {
        this.customer = customer;
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    
    	GridPane pane1 = new GridPane();
    	
    	pane1.setPrefWidth(1200);
        pane1.setStyle("-fx-font-size: 16px;"
        		+"-fx-background-color: rgba(120, 163, 212, 0.8); "
        		+ "-fx-text-fill: #FFFFFF; " +
                    "-fx-background-radius: 10px; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);"
                    + "-fx-padding:20px 20px 20px 20px");
            
        pane1.setPadding(new Insets(15)); // Set margin around pane1
        Label lblWelcome = new Label("Welcome, " + customer.getFirstName() + "!");
        lblWelcome.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: white;");
        Label lblCustomerId = new Label("Your Customer ID: " + customer.getCustomer_id());
        lblCustomerId.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: white;");

        Button backButton = new Button("Home");
        backButton.setOnAction(event -> {
            CustomerHomePage customerHomePage = new CustomerHomePage(customer);
            try {
                customerHomePage.start(primaryStage);
            } catch (Exception e) {
                System.out.println("Error opening CustomerHomePage: " + e.getMessage());
            }
        });
        
        // Fetch all records from the database
        ObservableList<Service> services = allRecords();

        // Create a GridPane to hold the elements
        GridPane gridPane = new GridPane();
       // gridPane.setPrefHeight(800);
        gridPane.setPrefWidth(1100);
        gridPane.setHgap(50); // Horizontal gap between nodes
        gridPane.setVgap(50); // Vertical gap between nodes
        gridPane.setPadding(new Insets(10)); // Add some padding around the GridPane

        // Add the "Welcome" and "Customer ID" labels to the first row
        pane1.add(lblWelcome, 0, 0, 2, 1);
        pane1.add(lblCustomerId, 0, 1, 3, 1); 
        
        GridPane rootPane = new GridPane();
        rootPane.setPadding(new Insets(10)); // Add some padding around the rootPane
        rootPane.setHgap(10); // Horizontal gap between nodes
        rootPane.setVgap(10); // Vertical gap between nodes

        // Add pane1 to the rootPane
        rootPane.add(pane1, 0, 0);
        
        // Counter for alternating between columns
        int columnIndex = 0;
        int rowIndex = 1; // Start adding service representations from the second row

        // Iterate over the services and create a representation for each one
        for (Service service : services) {
            // Create a service representation
            GridPane serviceRepresentation = createServiceRepresentation(service, primaryStage);

            // Add the service representation to the GridPane
            gridPane.add(serviceRepresentation, columnIndex, rowIndex); 

            // Alternate between three columns
            columnIndex = (columnIndex + 1) % 3; // Switch between 0, 1, and 2 for three columns
            if (columnIndex == 0) {
                rowIndex++; // Move to the next row after filling all three columns
            }
        }

        gridPane.add(backButton, 0, rowIndex, 3, 1); // span across all three columns
        // Create a ScrollPane to contain the GridPane
        ScrollPane scrollPane = new ScrollPane(gridPane);
        rootPane.add(scrollPane, 0, 1);

     // Add the home button below the ScrollPane
     rootPane.add(backButton, 0, 2);
        // Create a scene and set it to the ScrollPane
        Scene scene = new Scene(rootPane, 880, 400);
        primaryStage.setScene(scene);
        
        // Set the title of the stage
        primaryStage.setTitle("Service Management System");
        Image icon1 = new Image("file:///Users/shrutigurungschool/eclipse-workspace/Assessment/src/icons/icons8-service-48.png");
		primaryStage.getIcons().add(icon1);
        primaryStage.setWidth(1200);
        primaryStage.setHeight(900);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
   
    public static void main(String[] args) {
        launch(args);
    }

    public ObservableList<Service> allRecords() {
        ObservableList<Service> services = FXCollections.observableArrayList();
        String DRIVER = "com.mysql.cj.jdbc.Driver";
        String HOST = "localhost";
        int PORT = 3306;
        String DATABASE = "SMS";
        String DBUSER = "root";
        String DBPASS = "Mynameisshrutigurung12c!";
        String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
        String sql = "SELECT * FROM Service";
        try {
            Class.forName(DRIVER); // Load the MySQL JDBC driver
            Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS); // Establish connection to the database
            PreparedStatement pstat = conn.prepareStatement(sql);
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                int service_id = rs.getInt("service_id");
                String serviceName = rs.getString("serviceName");
                String serviceCategory = rs.getString("serviceCategory");
                String serviceDescription = rs.getString("serviceDescription");
                String estimatedDuration = rs.getString("estimatedDuration");
                Double serviceCharge = rs.getDouble("charge");
                String imagePath = rs.getString("serviceImage"); // Fetch image path from the database

                // Create a Service object and add it to the list
                Service service = new Service(service_id, serviceName, serviceCategory, serviceDescription, estimatedDuration, serviceCharge, imagePath);
                services.add(service);
            }
            // Close the ResultSet, PreparedStatement, and Connection
            rs.close();
            pstat.close();
            conn.close();
        } catch (Exception ex) {
            System.out.println("Error : " + ex.getMessage());
        }
        return services;
    }

    private GridPane createServiceRepresentation(Service service, Stage primaryStage) {
        // Create labels to display service information
        Label nameLabel = new Label("Service Name: " + service.getServiceName());
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        Label categoryLabel = new Label("Category: " + service.getServiceCategory());
        categoryLabel.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        Label descriptionLabel = new Label("Description: " + service.getServiceDescription());
        descriptionLabel.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        Label durationLabel = new Label("Duration: " + service.getEstimatedDuration());
        durationLabel.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        Label chargeLabel = new Label("Charge: Rs. " + service.getCharge());
        chargeLabel.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        String imagePath = service.getServiceImage();

        // Create an ImageView for the service image
        ImageView imageView = new ImageView();
        try {
            // Load the image from the image folder in the project
            Image image = new Image("file:///Users/shrutigurungschool/eclipse-workspace/Assessment/image/" + imagePath);
            imageView.setImage(image);
            imageView.setFitWidth(250);
            imageView.setFitHeight(150);// Adjust the width and height of the image view as needed
            imageView.setPreserveRatio(false); // Preserve the aspect ratio of the image
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            // Display a placeholder image or handle the error as needed
        }

        // Create a "Request Quote" button
        Button requestQuoteButton = new Button("Request Quote");
        requestQuoteButton.setOnAction(event -> {
            // Handle the request quote action here
            System.out.println("Quote requested for service: " + service.getServiceName());
            openQuoteCRUD(primaryStage, customer, service); // Pass the Customer object
        });

        // Create a GridPane to hold the labels, image, and button
        GridPane serviceRepresentation = new GridPane();
        serviceRepresentation.setHgap(10); // Horizontal gap between nodes
        serviceRepresentation.setVgap(5); // Vertical gap between nodes
        serviceRepresentation.setPadding(new Insets(20)); // Add some padding around the GridPane
        serviceRepresentation.setStyle("-fx-font-size: 16px; " +
                "-fx-background-color: rgba(120, 163, 212, 0.8); " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-padding: 20px; " +
                "-fx-background-radius: 10px; " +  // Corner radii
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);" + // Lighter drop shadow effect
                "-fx-alignment: center; " +  // Center alignment
                "-fx-min-width: 300px; " +    // Smaller width
                "-fx-min-height: 500px;");

        // Add components to the GridPane
        serviceRepresentation.addRow(1, imageView);
        serviceRepresentation.addRow(2, nameLabel);
        serviceRepresentation.addRow(3, categoryLabel);
        serviceRepresentation.addRow(4, descriptionLabel);
        serviceRepresentation.addRow(5, durationLabel);
        serviceRepresentation.addRow(6, chargeLabel);
        serviceRepresentation.addRow(7, requestQuoteButton);
        
        
        //Set the background color and corner radius to create rounded corners
        serviceRepresentation.setBackground(new Background(new BackgroundFill(Color.rgb(192,192,192,50.0/255.0), new CornerRadii(10), Insets.EMPTY)));
        //serviceRepresentation.setBackground(new Background(new BackgroundFill(Color.web("#d0e3ff"), new CornerRadii(10), Insets.EMPTY)));
        return serviceRepresentation;
    }

    
    public void openQuoteCRUD(Stage primaryStage, Customer customer, Service service) {
        QuoteSave quotePage = new QuoteSave(customer, service); // Pass the Customer object to QuoteCRUD constructor
        try {
            quotePage.start(new Stage()); 
            primaryStage.close(); 
        } catch (Exception e) {
            System.out.println("Error opening Quote page: " + e.getMessage());
        }
    }

}
