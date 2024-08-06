package SMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppointmentBook extends Application {

	private Customer customer;
	private Service service;
	private Staff staff;
	private Quote quote;
	private Worker worker;
	
	// Default constructor
	public AppointmentBook() {
		
	 }
	
	//Constructor accepting instances of classes
    public AppointmentBook(Customer customer, Service service, Staff staff, Quote quote, Worker worker) {
        this.customer = customer;
        this.service = service;
        this.staff = staff;
        this.quote = quote;
        this.worker = worker;
    }
    
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {		
        Label lblWelcome = new Label("Welcome, " + staff.getFirstName() + "!");
        lblWelcome.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
        Label lblStaffId = new Label("Your Staff ID: " + staff.getStaff_id());
        lblStaffId.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
        //Setting Background Image
        String backgroundImage = "url('file:///Users/shrutigurungschool/eclipse-workspace/Assessment/image/remote-team.png')";
	    VBox vbox = new VBox();
	    vbox.setStyle("-fx-background-image: " + backgroundImage + "; " +
	            "-fx-background-position: right center; " +
	            "-fx-background-repeat: no-repeat; " +
	            "-fx-padding: 50px;");
	    
	    
		Label lblCustomer_id = new Label ("Customer ID:");
		lblCustomer_id.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblService_id = new Label ("Service ID: ");
		lblService_id.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblServiceName = new Label ("Service Name:");
		lblServiceName.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblQuote_id = new Label("Quote ID:");
		lblQuote_id.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblStaffWorker_id = new Label("Assigned Staff Worker ID");
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
		
		TextField txtCustomer_id = new TextField();
		txtCustomer_id.setText(String.valueOf(customer.getCustomer_id()));
		txtCustomer_id.setEditable(false);
		
		TextField txtService_id = new TextField();
		txtService_id.setText(String.valueOf(service.getService_id()));
		txtService_id.setEditable(false);
		
		TextField txtServiceName = new TextField();
		txtServiceName.setText(service.getServiceName());
		txtServiceName.setEditable(false);
		
		TextField txtQuote_id = new TextField();
		txtQuote_id.setText(String.valueOf(quote.getQuote_id()));
		txtQuote_id.setEditable(false);
		
		TextField txtStaffWorker_id = new TextField();
		
		TextField txtDate = new TextField();
		txtDate.setText(quote.getDate());
		
		TextField txtTime = new TextField();
		txtTime.setText(quote.getTime());
		
		TextField txtDescription = new TextField();
		TextField txtTotalCost = new TextField();
		
		ComboBox<String> cmbStatus = new ComboBox<>();
		cmbStatus.getItems().addAll("Scheduled", "Completed", "Canceled");
		cmbStatus.setPromptText("Scheduled");
		cmbStatus.setEditable(false);
		
		//Setting the value of the cmbStatus as Scheduled even if staff tries to change it into another value
		cmbStatus.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		    if (!newValue.equals("Scheduled")) {
		        cmbStatus.getSelectionModel().select("Scheduled");
		    }
		});
		cmbStatus.setValue("Scheduled");
		
			Button btnBookAppointment = new Button("Confirm Appointment");
			btnBookAppointment.setOnAction((event) -> {
			//Reading values from UI
			int appointment_id = 0;
			int customer_id = Integer.parseInt(txtCustomer_id.getText());
			int service_id = Integer.parseInt(txtService_id.getText());
			int quote_id = Integer.parseInt(txtQuote_id.getText());
			int  worker_id = Integer.parseInt(txtStaffWorker_id.getText());
			String dateOfAppointment = txtDate.getText();
			String timeOfAppointment = txtTime.getText();
			String description = txtDescription.getText();
			Double totalCost = Double.parseDouble(txtTotalCost.getText());
			String status = cmbStatus.getSelectionModel().getSelectedItem().toString();
			
			//Creating objects of classes and passing the required arguments and null for not required arguments
			Customer customer = new Customer(customer_id, null, null, null, null, null, null,null);
			Service service = new Service(service_id, null, null, null, null, null, null);
			Quote quote=new Quote(quote_id, customer, service, null,null,null,null, null);
			Worker worker = new Worker(worker_id, null,null, null, null, null, null, 0.0, null);
			
			Appointment appointment = new Appointment(appointment_id,customer, service, quote, worker, dateOfAppointment,timeOfAppointment,description, totalCost, status, 0);
			
			boolean res = saveRecord(appointment); //call method
			if(res==true) {
				System.out.println("Record Saved");
				Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
				   alert1.setTitle("INFORMATION");
				   alert1.setHeaderText("Appointment Successfully Booked");
				   alert1.showAndWait();
				primaryStage.close();
				openQuoteStaff(primaryStage);
			}
			else {
				System.out.println("Error: to save record");
				Alert alert1 = new Alert(Alert.AlertType.ERROR);
				   alert1.setTitle("ERROR");
				   alert1.setHeaderText("Error: to book appointment");
				   alert1.showAndWait();
				
			}
		
		});	
		
		Button btnBack = new Button("Back to Requested Quote");
		btnBack.setOnAction(e -> {
			openQuoteStaff(primaryStage);
		});
		
		Button btnHome = new Button("Home");
		btnHome.setOnAction(e -> {
			openStaffHomePage(primaryStage, staff);
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
        
        Label loginHeader = new Label("Book Appointment");
	    loginHeader.setStyle("-fx-text-fill: white; -fx-font-size: 30px; -fx-font-weight: bold;  -fx-font-family: 'Helvetica';"); // Style the header label

	    pane.add(loginHeader, 0, 0, 2, 1); // Add the header label spanning two columns

	    pane.add(lblWelcome, 0, 1);
	    pane.add(lblStaffId, 1, 1);
		
		pane.add(lblCustomer_id, 0, 2);
		pane.add(txtCustomer_id, 1, 2);
		pane.add(lblService_id, 0, 3);
		pane.add(txtService_id, 1, 3);
		pane.add(lblServiceName, 0, 4);
		pane.add(txtServiceName, 1, 4);
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
		pane.add(cmbStatus, 1, 11);
		pane.add(btnBookAppointment, 0, 12);
		pane.add(btnBack, 1, 12);
		pane.add(btnHome, 0, 13);
		
		vbox.getChildren().add(pane);
	    vbox.setAlignment(Pos.CENTER);

	    Scene scene = new Scene(vbox, 600, 400);
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("Book Appointment");
	    Image icon1 = new Image("file:///Users/shrutigurungschool/eclipse-workspace/Assessment/src/icons/icons8-service-48.png");
	    primaryStage.getIcons().add(icon1);
	    primaryStage.setWidth(1200);
		primaryStage.setHeight(900);
	    primaryStage.setResizable(false);
	    primaryStage.show();
	}
	
	public boolean saveRecord(Appointment appointment) {
		//pid, fullName, address, gender, ageGroup, reading, playing, other, login_id, pass_word
		boolean result = false;
		String DRIVER ="com.mysql.cj.jdbc.Driver";
		String HOST ="localhost";
		int PORT=3306;
		String DATABASE ="SMS";
		String DBUSER="root";
		String DBPASS="Mynameisshrutigurung12c!";
		String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
		String sql="INSERT INTO Appointment (customer_id, service_id, quote_id, worker_id, dateOfAppointment, timeOfAppointment, description, totalCost, status, rescheduleCount) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
			pstat.setInt(10, appointment.getRescheduleCount());
			pstat.executeUpdate();//Insert Record
			pstat.close();
			conn.close();
			result=true;
		}
		catch(Exception ex) {
			System.out.println("Error : "+ex.getMessage());
		}
		return result;
	}

	//Method to open QuoteStaff page 
	public void openQuoteStaff(Stage primaryStage) {
	    QuoteStaff quotePage = new QuoteStaff(staff, worker);
	    try {
	    	quotePage.start(new Stage()); 
	        primaryStage.close(); 
	    } catch (Exception e) {
	        System.out.println("Error opening QuoteStaff page: " + e.getMessage());
	    }
	    
    } 
	
	//Method to open Staff Home Page
	public void openStaffHomePage(Stage primaryStage, Staff staff) {
	    StaffHomePage staffHomePage = new StaffHomePage(staff);
	    try {
	    	staffHomePage.start(new Stage()); 
	        primaryStage.close(); 
	    } catch (Exception e) {
	        System.out.println("Error opening Staff Home Page: " + e.getMessage());
	    }
	}
}
