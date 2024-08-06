package SMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class AppointmentReschedule2 extends Application {
	
	private Customer customer;

	
	public AppointmentReschedule2() {
	    // Default constructor
	}

    public AppointmentReschedule2(Customer customer) {
        this.customer = customer;
      
    }    
	public static void main(String[] args) {
		launch(args);
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
        ObservableList<Appointment> appointments = allRecords();

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
        for (Appointment appointment : appointments) {
            // Create a service representation
            GridPane appointmentRepresentation = appointmentRepresentation(appointment, primaryStage);

            // Add the service representation to the GridPane
            gridPane.add(appointmentRepresentation, columnIndex, rowIndex); 

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
        primaryStage.setTitle("Reschedule Your Booked Appointment");
		Image icon1 = new Image("file:///Users/shrutigurungschool/eclipse-workspace/Assessment/src/icons/icons8-service-48.png");
		primaryStage.getIcons().add(icon1);
        primaryStage.setWidth(1200);
        primaryStage.setHeight(900);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
   

	public ObservableList<Appointment> allRecords() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
		String DRIVER ="com.mysql.cj.jdbc.Driver";
		String HOST ="localhost";
		int PORT=3306;
		String DATABASE ="SMS";
		String DBUSER="root";
		String DBPASS="Mynameisshrutigurung12c!";
		String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
		String sql = "SELECT a.appointment_id, c.customer_id, s.service_id, s.serviceName, q.quote_id, w.worker_id, a.dateOfAppointment, a.timeOfAppointment, a.description, a.totalCost, a.status, a.rescheduleCount  " +
	             "FROM Appointment a, Customer c, Quote q, Service s, Worker w " +
	             "WHERE a.customer_id = c.customer_id AND a.service_id = s.service_id AND a.quote_id = q.quote_id AND a.worker_id = w.worker_id  "
	             + " AND c.customer_id = ?";

		try {
			Class.forName(DRIVER); //loading driver
			Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
			PreparedStatement pstat = conn.prepareStatement(sql);	
			pstat.setInt(1, customer.getCustomer_id());
			ResultSet rs = pstat.executeQuery();
			while(rs.next()) {
				
				int appointment_id = rs.getInt("appointment_id");
				int customer_id = rs.getInt("customer_id");
	        	int service_id = rs.getInt("service_id");
	        	String serviceName = rs.getString("serviceName");
	        	int quote_id = rs.getInt("quote_id");
	        	int worker_id = rs.getInt("worker_id");
	            String dateOfAppointment = rs.getString("dateOfAppointment");
	            String timeOfAppointment = rs.getString("timeOfAppointment");
	            String description=rs.getString("description");
	            Double totalCost= rs.getDouble("totalCost");
	            String status=rs.getString("status");
	            int rescheduleCount = rs.getInt("rescheduleCount");
				
				Customer customer = new Customer(customer_id, null, null, null, null, null, null, null);
				Service service = new Service(service_id, serviceName, null, null, null, null, null);
				Quote quote=new Quote(quote_id, customer, service, null,null,null,null, null);
				Worker worker = new Worker(worker_id, null, null, null, null, null, null, 0.0, null);
				
				Appointment appointment = new Appointment(appointment_id,customer, service, quote, worker, dateOfAppointment, timeOfAppointment, description,totalCost,status, rescheduleCount);
				appointments.add(appointment);
			}
			rs.close();
			pstat.close();
			conn.close();			
		}
		catch(Exception ex) {
			System.out.println("Error : "+ex.getMessage());
		}
		return appointments;
	}
	
	public boolean updateRecord(Appointment appointment) {
	    boolean result = false;
	    String DRIVER ="com.mysql.cj.jdbc.Driver";
	    String HOST ="localhost";
	    int PORT=3306;
	    String DATABASE ="SMS";
	    String DBUSER="root";
	    String DBPASS="Mynameisshrutigurung12c!";
	    String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
	    String sql="UPDATE Appointment SET dateOfAppointment=?, rescheduleCount = rescheduleCount + 1 WHERE appointment_id=?";
	    try {
	        Class.forName(DRIVER); //loading driver
	        Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
	        PreparedStatement pstat = conn.prepareStatement(sql);      
	        pstat.setString(1, appointment.getDateOfAppointment());
	        //pstat.setString(2, appointment.getTimeOfAppointment());
	        pstat.setInt(2, appointment.getAppointment_id()); // set the appointment_id parameter
	        pstat.executeUpdate();//Update Record
	        pstat.close();
	        conn.close();
	       
	        result=true;
	    }
	    catch(Exception ex) {
	        System.out.println("Error : "+ex.getMessage());
	    }
	    return result;
	}
	
	private GridPane appointmentRepresentation(Appointment appointment, Stage primaryStage) {
       
        Label lblCustomerName = new Label ("Customer Name:" + customer.getFirstName() + customer.getLastName());
        lblCustomerName.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		Label lblServiceName = new Label ("Service ID: "+ appointment.getService().getServiceName());
		lblServiceName.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		Label lblQuote_id = new Label("Quote ID:" + appointment.getQuote().getQuote_id());
		lblQuote_id.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		Label lblStaffWorker_id = new Label("Assigned Staff Worker ID: " + appointment.getWorker().getWorker_id());
		lblStaffWorker_id.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		Label lblDate = new Label ("Date Of Appointment: ");
		lblDate.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		DatePicker txtDate = new DatePicker();
		String dateString = appointment.getDateOfAppointment(); // Assuming getDateOfAppointment() returns a String
		LocalDate date = LocalDate.parse(dateString); // Convert the String to LocalDate
		txtDate.setValue(date); // Set the value of the DatePicker
		
		Label lblTime = new Label("Time Of Appointment:");
		lblTime.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		TextField txtTime = new TextField();
		txtTime.setText(appointment.getTimeOfAppointment());
		
		Label lbldescription = new Label("Description: " + appointment.getDescription());
		lbldescription.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		Label lblTotalCost = new Label ("Total Cost:" + appointment.getTotalCost());
		lblTotalCost.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		Label lblStatus = new Label ("Status: " + appointment.getStatus());
		lblStatus.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");

        // Create a "Request Quote" button
        Button btnUpdate = new Button("Reschedule");
        btnUpdate.setOnAction(event -> {
             
        	if ("Scheduled".equals(appointment.getStatus())) 
		       {
		    	   if (appointment.getRescheduleCount() < 5 ) 
		    	   {
		    		   boolean res = updateRecord(appointment);
		    		   if (res) 
		    		   {
		    			   System.out.println("Record Updated");
		    			   Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    				   alert.setTitle("INFORMATION");
	    				   alert.setHeaderText("Record Updated");
	    				   alert.showAndWait();
		    			   appointment.setRescheduleCount(appointment.getRescheduleCount() + 1);
		    			   
		    			   System.out.println("You have reschduled " + appointment.getRescheduleCount() + " time/s. Now, you can reschule the appointment " + (5 - appointment.getRescheduleCount()) + " time/s.");
		    			   Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
	    				   alert2.setTitle("INFORMATION");
	    				   alert2.setHeaderText("You have reschduled " + appointment.getRescheduleCount() + " time/s. Now, you can reschule the appointment " + (5 - appointment.getRescheduleCount()) + " time/s.");
	    				   alert2.showAndWait();
				   
		    			   if (appointment.getRescheduleCount() == 5) {	
		    				   btnUpdate.setDisable(true);	  
		    				   System.out.println("Reschedule limitation met.");
		    				   Alert alert1 = new Alert(Alert.AlertType.WARNING);
		    				   alert1.setTitle("INFORMATION");
		    				   alert1.setHeaderText("Reschedule limitation met");
		    				   alert1.showAndWait();
		    				                                 
		    			   } 
		    		   } 
		    		   else 
		    		   {
		    			   System.out.println("Error: Failed to update record");	
		    			   Alert alert1 = new Alert(Alert.AlertType.ERROR);
		    			   alert1.setTitle("Error");
		    			   alert1.setHeaderText("Error: Failed to update record");
		    			   alert1.showAndWait();
		    			   btnUpdate.setDisable(true);                                  
		    		   }               
		            	                                       
		    	   }    
		    	   
		    	   else {
		    		   System.out.println("Maxiumum reschedule limit met");
		    		   btnUpdate.setDisable(true);	
		    		   Alert alert1 = new Alert(Alert.AlertType.WARNING);
		    		   alert1.setTitle("INFORMATION");
		    		   alert1.setHeaderText("Reschedule limitation met");
		    		   alert1.showAndWait();
		    	   }
		     } 
		       else {    	                                   
		    	   btnUpdate.setDisable(true);
		    	 	Alert alert = new Alert(Alert.AlertType.ERROR);
				    alert.setTitle("Error");
				    alert.setHeaderText("Appointment cannot be rescheduled as the appointment has been completed or canceld.");
				    alert.showAndWait();
				    System.out.println("Appointment cannot be rescheduled as the appointment has been completed or canceld. ");                         
			
		       				}
        	
        });
		    

        // Create a GridPane to hold the labels, image, and button
        GridPane appointmentRepresentation = new GridPane();
        appointmentRepresentation.setHgap(10); // Horizontal gap between nodes
        appointmentRepresentation.setVgap(5); // Vertical gap between nodes
        appointmentRepresentation.setPadding(new Insets(20)); // Add some padding around the GridPane
        appointmentRepresentation.setStyle("-fx-font-size: 16px; " +
                "-fx-background-color: rgba(120, 163, 212, 1); " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-padding: 20px; " +
                "-fx-background-radius: 10px; " +  // Corner radii
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);" + // Lighter drop shadow effect
                "-fx-alignment: center; " +  // Center alignment
                "-fx-min-width: 300px; " +    // Smaller width
                "-fx-min-height: 500px;");

        // Add components to the GridPane
        appointmentRepresentation.addRow(1, lblServiceName);
        appointmentRepresentation.addRow(2, lblCustomerName);
        appointmentRepresentation.addRow(3, lblQuote_id);
        appointmentRepresentation.addRow(4, lblStaffWorker_id);
        appointmentRepresentation.addRow(5, lblDate);
        appointmentRepresentation.addRow(6, txtDate);
        appointmentRepresentation.addRow(7, lblTime);
        appointmentRepresentation.addRow(8, txtTime);
        appointmentRepresentation.addRow(9, lbldescription);
        appointmentRepresentation.addRow(10, lblTotalCost);
        appointmentRepresentation.addRow(11, lblStatus);
        appointmentRepresentation.addRow(12, btnUpdate);
          
        
        
        
        //Set the background color and corner radius to create rounded corners
        appointmentRepresentation.setBackground(new Background(new BackgroundFill(Color.rgb(192,192,192,50.0/255.0), new CornerRadii(10), Insets.EMPTY)));
        //serviceRepresentation.setBackground(new Background(new BackgroundFill(Color.web("#d0e3ff"), new CornerRadii(10), Insets.EMPTY)));
        return appointmentRepresentation;
    }
}
