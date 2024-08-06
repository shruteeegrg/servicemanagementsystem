package SMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppointmentCRUD extends Application {

	private Staff staff;
	
	public AppointmentCRUD( Staff staff) {
        this.staff = staff;
    }
    
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		
        Label lblStaffId = new Label( staff.getFirstName() + "'s Staff ID: " + staff.getStaff_id());
        
        String backgroundImage = "url('file:///Users/shrutigurungschool/eclipse-workspace/Assessment/image/remote-team.png')";
	    VBox vbox = new VBox();
	    vbox.setStyle("-fx-background-image: " + backgroundImage + "; " +
	            "-fx-background-position: right center; " +
	            "-fx-background-repeat: no-repeat; " +
	            "-fx-padding: 50px;");
        
        Label lblAppointment_id = new Label("Appointment ID");
        lblAppointment_id.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
		Label lblCustomer_id = new Label ("Customer ID:");
		lblCustomer_id.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblService_id = new Label ("Service ID: ");
		lblService_id.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblQuote_id = new Label("Quote ID:");
		lblQuote_id.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblStaffWorker_id = new Label("Assigned Staff ID");
		lblStaffWorker_id.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblDate = new Label ("Date Of Appointment:");
		lblDate.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblTime = new Label("Time Of Appointment:");
		lblTime.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lbldescription = new Label("Description:");
		lbldescription.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblTotalCost = new Label ("Total Cost:");
		lblTotalCost.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblStatus = new Label ("Status");
		lblStatus.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		TextField txtAppointment_id = new TextField();
		TextField txtCustomer_id = new TextField();
		TextField txtService_id = new TextField();	
		TextField txtQuote_id = new TextField();
		TextField txtStaffWorker_id = new TextField();
		TextField txtDate = new TextField();
		TextField txtTime = new TextField();
		TextField txtDescription = new TextField();
		TextField txtTotalCost = new TextField();
		
		ComboBox<String> cmbStatus1 = new ComboBox<>();
		cmbStatus1.getItems().addAll("Scheduled", "Completed", "Canceled");

		Button btnSearch = new Button("Search");
		btnSearch.setOnAction((event) -> {
			//Reading values from UI
			int appointment_id = Integer.parseInt(txtAppointment_id.getText());
			
			Appointment appointment = searchRecord(appointment_id);
			if(appointment != null) {
				
			txtCustomer_id.setText(String.valueOf(appointment.getCustomer().getCustomer_id()));
			txtService_id.setText(String.valueOf(appointment.getService().getService_id()));
			txtQuote_id.setText(String.valueOf(appointment.getQuote().getQuote_id()));
			txtStaffWorker_id.setText(String.valueOf(appointment.getWorker().getWorker_id()));
			txtDate.setText(appointment.getDateOfAppointment());
			txtTime.setText(appointment.getTimeOfAppointment());
			txtDescription.setText(appointment.getDescription());
			txtTotalCost.setText(String.valueOf(appointment.getTotalCost()));
			String status = appointment.getStatus();
			if(status.equals("Scheduled")) {
				cmbStatus1.getSelectionModel().select(0);	
			}
			else if(status.equals("Completed")) {
				cmbStatus1.getSelectionModel().select(1);	
			}
			else if(status.equals("Canceled")) {
				cmbStatus1.getSelectionModel().select(2);	
			}
			
			    System.out.println("Record found successfully!");
			    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
				alert1.setTitle("INFORMATION");
				alert1.setHeaderText("Appointment found successfully!");
				alert1.showAndWait();
			}
		
			else {
				System.out.println("Error: to search record");
				 Alert alert1 = new Alert(Alert.AlertType.ERROR);
					alert1.setTitle("ERROR");
					alert1.setHeaderText("Error: to search appointment");
					alert1.showAndWait();
			}
	
		});
		
		Button btnUpdate = new Button("Update");
		btnUpdate.setOnAction(e -> {
			int appointment_id = Integer.parseInt(txtAppointment_id.getText());
			int customer_id = Integer.parseInt(txtCustomer_id.getText());
			int service_id = Integer.parseInt(txtService_id.getText());
			int quote_id = Integer.parseInt(txtQuote_id.getText());
			int  worker_id= Integer.parseInt(txtStaffWorker_id.getText());
			String dateOfAppointment = txtDate.getText();
			String timeOfAppointment = txtTime.getText();
			String description = txtDescription.getText();
			Double totalCost = Double.parseDouble(txtTotalCost.getText());
			String status = cmbStatus1.getValue().toString();
			int rescheduleCount = 0;
			
			Customer customer = new Customer(customer_id, null, null, null, null, null, null, null);
			Service service = new Service(service_id, null, null, null, null, null, null);
			Quote quote=new Quote(quote_id, customer, service, null,null,null,null, null);
			Worker worker = new Worker(worker_id, null, null, null, null, null, null, 0.0, null);
			
			Appointment appointment = new Appointment(appointment_id,customer, service, quote, worker, dateOfAppointment, timeOfAppointment, description,totalCost,status, rescheduleCount);
			
			boolean res = updateRecord(appointment); //call method
			if(res==true) {
				System.out.println("Record Updated Successfully!");
				Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
				alert1.setTitle("INFORMATION");
				alert1.setHeaderText("Appointment Updated successfully!");
				alert1.showAndWait();
				
				
			}
			else {
				System.out.println("Error: to update record");
				Alert alert1 = new Alert(Alert.AlertType.ERROR);
				alert1.setTitle("ERROR");
				alert1.setHeaderText("Error: to update appointment");
				alert1.showAndWait();
			}
		
		});	

		
		Button btnDelete = new Button("Delete");
		btnDelete.setOnAction(event -> {
			int appointment_id = Integer.parseInt(txtAppointment_id.getText());//string->int										
			boolean res = deleteRecord(appointment_id); //call method
			if(res==true) {
				System.out.println("Appointment Deleted");
				Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
				alert1.setTitle("INFORMATION");
				alert1.setHeaderText("Appointment Deleted successfully!");
				alert1.showAndWait();
				txtAppointment_id.setText(""); //Setting the text field to null / clearing the text field 
				txtCustomer_id.setText("");
				txtService_id.setText("");
				txtQuote_id.setText("");
				txtStaffWorker_id.setText("");
				txtDate.setText("");
				txtTime.setText("");
				txtDescription.setText("");
				txtTotalCost.setText("");
				cmbStatus1.setValue(null);
			}
			else {
				System.out.println("Error: to delete appointment record");
				Alert alert1 = new Alert(Alert.AlertType.ERROR);
				alert1.setTitle("ERROR");
				alert1.setHeaderText("Error: to delete appointment record");
				alert1.showAndWait();
			}
		});
		
		Button btnDisplay = new Button("Display All Record");
		btnDisplay.setOnAction(event -> {      
		    // Retrieving all appointment records from the database
		    ArrayList<Appointment> appointments = allRecords();

		    // Create a new stage/window for displaying records
		    Stage displayStage = new Stage();
		    displayStage.setTitle("All Appointments Booked");

		    // Create TableView to display records
		    TableView<Appointment> displayTable = new TableView<>();
		    displayTable.setPrefWidth(600);
		    displayTable.setPrefHeight(400);
		    
		    //Creating TableColumn that will be displayed in TableView
		    TableColumn<Appointment, Integer> col1 = new TableColumn<>("Appointment ID: ");
			col1.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
			col1.setMinWidth(75);
			
			TableColumn<Appointment, Integer> col2 = new TableColumn<>("Customer ID");
			col2.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCustomer().getCustomer_id()).asObject());
			col2.setMinWidth(150);
			
			TableColumn<Appointment, Integer> col3 = new TableColumn<>("Service ID");
			col3.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getService().getService_id()).asObject());
			col3.setMinWidth(150);
			
			TableColumn<Appointment, String> col4 = new TableColumn<>("Service Name");
			col4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getService().getServiceName()));
			col4.setMinWidth(150);
			
			TableColumn<Appointment, Integer> col5 = new TableColumn<>("Quote ID");
			col5.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuote().getQuote_id()).asObject());
			col5.setMinWidth(150);
			
			TableColumn<Appointment, Integer> col6 = new TableColumn<>("Staff ID");
			col6.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getWorker().getWorker_id()).asObject());
			col6.setMinWidth(150);
			
			TableColumn<Appointment, String> col7 = new TableColumn<>("Date");
			col7.setCellValueFactory(new PropertyValueFactory<>("dateOfAppointment"));
			col7.setMinWidth(150);
			
			TableColumn<Appointment, String> col8 = new TableColumn<>("Time");
			col8.setCellValueFactory(new PropertyValueFactory<>("timeOfAppointment"));
			col8.setMinWidth(150);
			
			TableColumn<Appointment, String> col9 = new TableColumn<>("Description");
			col9.setCellValueFactory(new PropertyValueFactory<>("description"));
			col9.setMinWidth(150);
			
			TableColumn<Appointment, String> col10 = new TableColumn<>("Total Cost");
			col10.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
			col10.setMinWidth(150);
			
			TableColumn<Appointment, String> col11 = new TableColumn<>("Status");
			col11.setCellValueFactory(new PropertyValueFactory<>("status"));
			col11.setMinWidth(150);
			
			
			displayTable.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10, col11);
		    // Add data to the table
		    displayTable.getItems().addAll(appointments);
		    
		    Button btnClose = new Button("Close");
		    btnClose.setOnAction((e)->{
				displayStage.close(); //close the window
			}); 
		    // Create a scene and set it to the stage
		    Scene displayScene = new Scene(new VBox(new Label("All Appointment Booked"), displayTable, btnClose));
		    displayStage.setScene(displayScene);

		    // Show the stage
		    displayStage.setWidth(800);
		    displayStage.setHeight(500);
		    displayStage.show();
		    
		});
		
		Button btnHome = new Button("Home");
		btnHome.setOnAction(event -> {
			openStaffHomePage(primaryStage, staff);
		}) ;
		
		Button btnClose=new Button("Close");
		btnClose.setOnAction((event)->{
			primaryStage.close(); //close the window
		});
		
		GridPane pane = new GridPane();
		
		pane.setVgap(10); 
        pane.setHgap(10);
        pane.setStyle("-fx-font-size: 16px; " + //setting font size
	              "-fx-background-color: rgba(120, 163, 212, 0.8); " + //setting background colour
	              "-fx-text-fill: #FFFFFF; " + //Changing text colour
	              "-fx-padding: 20px; " +
	              "-fx-background-radius: 10px; " +  // Cornering the edge
	              "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);" + // Lighter drop shadow effect
	              "-fx-alignment: center; " +  // Centre alignment
	              "-fx-min-width: 300px; " +    // width
	              "-fx-min-height: 500px;"); //height

        Label loginHeader = new Label("Manage Appointment");
	    loginHeader.setStyle("-fx-text-fill: white; -fx-font-size: 30px; -fx-font-weight: bold;  -fx-font-family: 'Helvetica';"); // Style the header label

	    pane.add(loginHeader, 0, 0, 2, 1); // Add the header label spanning two columns
	    pane.add(lblStaffId, 0, 1);
		
		pane.add(lblAppointment_id, 0, 2);
		pane.add(txtAppointment_id, 1, 2);
		pane.add(lblCustomer_id, 0, 3);
		pane.add(txtCustomer_id, 1, 3);
		pane.add(lblService_id, 0, 4);
		pane.add(txtService_id, 1, 4);
		pane.add(lblQuote_id, 0, 5);
		pane.add(txtQuote_id, 1, 5);
		pane.add(lblStaffWorker_id, 0, 6);
		pane.add(txtStaffWorker_id, 1, 6);
		pane.add(lblDate, 0, 7);
		pane.add(txtDate, 1, 7);
		pane.add(lblTime, 0, 8);
		pane.add(txtTime, 1, 8);
		pane.add(lbldescription, 0, 9);
		pane.add(txtDescription, 1, 9);
		pane.add(lblTotalCost, 0, 10);
		pane.add(txtTotalCost, 1, 10);
		pane.add(lblStatus, 0, 11);
		pane.add(cmbStatus1, 1, 11);
		pane.add(btnSearch, 0, 12);
		pane.add(btnUpdate, 1, 12);
		pane.add(btnDelete, 2, 12);
		pane.add(btnDisplay, 3, 12);
		pane.add(btnHome, 1, 13);
		pane.add(btnClose, 2, 13);
		
		vbox.getChildren().add(pane);
	    vbox.setAlignment(Pos.CENTER);

	    Scene scene = new Scene(vbox, 600, 400);
	    primaryStage.setScene(scene);
	    Image icon1 = new Image("file:///Users/shrutigurungschool/eclipse-workspace/Assessment/src/icons/icons8-service-48.png");
	    primaryStage.getIcons().add(icon1);
	    primaryStage.setTitle("Manage Appointment");
	    primaryStage.setWidth(1200);
		primaryStage.setHeight(900);
	    primaryStage.setResizable(false);
	    primaryStage.show();
	}
	
	 public Appointment searchRecord(int appointment_id) {
	        Appointment appointment = null;
	        String DRIVER ="com.mysql.cj.jdbc.Driver";
	        String HOST ="localhost";
	        int PORT=3306;
	        String DATABASE ="SMS";
	        String DBUSER="root";
	        String DBPASS="Mynameisshrutigurung12c!";
	        String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
	        String sql="SELECT * FROM Appointment WHERE appointment_id = ?";
	        
	        try {
	            Class.forName(DRIVER); //loading driver
	            Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
	            PreparedStatement pstat = conn.prepareStatement(sql);
	            pstat.setInt(1, appointment_id);    
	            ResultSet rs = pstat.executeQuery();
	           while(rs.next()) {
	        	int customer_id = rs.getInt("customer_id");
	        	int service_id = rs.getInt("service_id");
	        	int quote_id = rs.getInt("quote_id");
	        	int worker_id = rs.getInt("worker_id");
	            String dateOfAppointment = rs.getString("dateOfAppointment");
	            String timeOfAppointment = rs.getString("timeOfAppointment");
	            String description=rs.getString("description");
	            Double totalCost= rs.getDouble("totalCost");
	            String status=rs.getString("status");
	            int rescheduleCount = 0;
	            
	            Customer customer = new Customer(customer_id, null, null, null, null, null, null, null);
				Service service = new Service(service_id, null, null, null, null, null, null);
				Quote quote=new Quote(quote_id, customer, service, null,null,null,null, null);
				Worker worker = new Worker(worker_id, null, null, null, null, null, null, 0.0, null);
				
				appointment = new Appointment(appointment_id,customer, service, quote, worker, dateOfAppointment, timeOfAppointment, description,totalCost,status, rescheduleCount);
	           }
	            pstat.close();
	            conn.close();            
	        }
	        catch(Exception ex) {
	            System.out.println("Error : "+ex.getMessage());
	        }
	        return appointment;
	    
		}
	 
	 
	 public boolean updateRecord(Appointment appointment) {
		    //pid, fullName, address, gender, ageGroup, reading, playing, other, login_id, pass_word
		    boolean result = false;
		    String DRIVER ="com.mysql.cj.jdbc.Driver";
		    String HOST ="localhost";
		    int PORT=3306;
		    String DATABASE ="SMS";
		    String DBUSER="root";
		    String DBPASS="Mynameisshrutigurung12c!";
		    String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
		    String sql="UPDATE Appointment SET customer_id=?, service_id=?, quote_id=?, worker_id=?, dateOfAppointment=?, timeOfAppointment =? , description =?, totalCost =? , status=?  WHERE appointment_id=?";
		    try {
		        Class.forName(DRIVER); //loading driver
		        Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
		        PreparedStatement pstat = conn.prepareStatement(sql);            
		        pstat.setInt(1, appointment.getCustomer().getCustomer_id());
		        pstat.setInt(2, appointment.getService().getService_id());
		        pstat.setInt(3, appointment.getQuote().getQuote_id());
		        pstat.setInt(4, appointment.getWorker().getWorker_id());
		        pstat.setString(5, appointment.getDateOfAppointment());
		        pstat.setString(6, appointment.getTimeOfAppointment());
		        pstat.setString(7, appointment.getDescription());
		        pstat.setDouble(8, appointment.getTotalCost());
		        pstat.setString(9, appointment.getStatus());
		        pstat.setInt(10, appointment.getAppointment_id()); // Assuming getServiceId() returns the service ID
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
	 
	 
	 public boolean deleteRecord(int appointment_id) {
			//pid, fullName, address, gender, ageGroup, reading, playing, other, login_id, pass_word
			boolean result = false;
			String DRIVER ="com.mysql.cj.jdbc.Driver";
			String HOST ="localhost";
			int PORT=3306;
			String DATABASE ="SMS";
			String DBUSER="root";
			String DBPASS="Mynameisshrutigurung12c!";
			String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
			String sql="DELETE FROM Appointment  WHERE appointment_id=?";
			try {
				Class.forName(DRIVER); //loading driver
				Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
				PreparedStatement pstat = conn.prepareStatement(sql);			
				pstat.setInt(1, appointment_id);
				pstat.executeUpdate();//Delete Record
				pstat.close();
				conn.close();
				result=true;
			}
			catch(Exception ex) {
				System.out.println("Error : "+ex.getMessage());
			}
			return result;
		}	
		
		public ArrayList allRecords() {
			
			ArrayList<Appointment> appointments = new ArrayList<Appointment>();
			String DRIVER ="com.mysql.cj.jdbc.Driver";
			String HOST ="localhost";
			int PORT=3306;
			String DATABASE ="SMS";
			String DBUSER="root";
			String DBPASS="Mynameisshrutigurung12c!";
			String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
			String sql = "SELECT a.appointment_id, c.customer_id, s.service_id, s.serviceName, q.quote_id, w.worker_id, a.dateOfAppointment, a.timeOfAppointment, a.description, a.totalCost, a.status " +
		             "FROM Appointment a, Customer c, Quote q, Service s, Worker w " +
		             "WHERE a.customer_id = c.customer_id AND a.service_id = s.service_id AND a.quote_id = q.quote_id AND a.worker_id = w.worker_id ;";

			try {
				Class.forName(DRIVER); //loading driver
				Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
				PreparedStatement pstat = conn.prepareStatement(sql);	
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
		            String status = rs.getString("status");
		            int rescheduleCount = 0;
					
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
		
		
		public void openStaffHomePage(Stage primaryStage, Staff staff) {
		    StaffHomePage staffHomePage = new StaffHomePage(staff); // Create an instance/object of the Staff Home page
		    try {
		    	staffHomePage.start(new Stage()); //Opens Staff Home Page
		        primaryStage.close(); //Close the AppointmentCRUD page
		    } catch (Exception e) {
		        System.out.println("Error opening Staff Home page: " + e.getMessage());
		    }
		}
}
