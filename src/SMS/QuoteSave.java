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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class QuoteSave extends Application {

	private Customer customer;
	private Service service;
	
	public QuoteSave() {
	    // Default constructor
	}

    public QuoteSave(Customer customer, Service service) {
        this.customer = customer;
        this.service = service;
    }
    
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
        Label lblWelcome = new Label("Welcome, " + customer.getFirstName() + "!");
        lblWelcome.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
        Label lblCustomerId = new Label("Your Customer ID: " + customer.getCustomer_id());
        lblCustomerId.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
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
		
		Label lblDate = new Label ("Date:");
		lblDate.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblTime = new Label("Available Time Range:");
		lblTime.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblrequirment = new Label("Requirement/s:");
		lblrequirment.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblBudget = new Label ("Your Budget:");
		lblBudget.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
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
		
		DatePicker DatePickerdate = new DatePicker();
		TextField txtTime = new TextField();
		
		TextField txtRequirement = new TextField();
		TextField txtBudget = new TextField();
		
		ComboBox<String> cmbStatus = new ComboBox<>();
		cmbStatus.getItems().addAll("Pending", "Accepted", "Rejected");
		cmbStatus.setPromptText("Pending");
		cmbStatus.setEditable(false);

		cmbStatus.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		    if (!newValue.equals("Pending")) {
		        cmbStatus.getSelectionModel().select("Pending");
		    }
		});

		cmbStatus.setValue("Pending");
		
		Button btnRequestQuote = new Button("Send Request Quote");
		btnRequestQuote.setOnAction((event) -> {
			//Reading values from UI
			int quote_id = 0;
			int customer_id = Integer.parseInt(txtCustomer_id.getText());
			int service_id = Integer.parseInt(txtService_id.getText());
			String date= DatePickerdate.getValue().toString();;
			String time = txtTime.getText();
			String requirement = txtRequirement.getText();
			Double budget = Double.parseDouble(txtBudget.getText());
			String status = cmbStatus.getSelectionModel().getSelectedItem().toString();
			
			Customer customer = new Customer(customer_id, null, null, null, null, null, null, null);
			Service service = new Service(service_id, null, null, null, null, null, null);
			Quote quote=new Quote(quote_id, customer, service, date,time,requirement,budget, status);	
			
			boolean res = saveRecord(quote); //call method
			if(res==true) {
				System.out.println("Record Saved");
				Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
				   alert1.setTitle("INFORMATION");
				   alert1.setHeaderText("Quote Request Sent Successfully!");
				   alert1.showAndWait();
				DatePickerdate.setValue(null);
				txtTime.setText("");
				txtRequirement.setText("");
				txtBudget.setText("");
				
			}
			else {
				System.out.println("Error: to save record");
				Alert alert1 = new Alert(Alert.AlertType.ERROR);
				   alert1.setTitle("ERROR");
				   alert1.setHeaderText("Error to sent quote request!");
				   alert1.showAndWait();
			}
		
		});	
		
		Button btnBack = new Button("Back to Services");
		btnBack.setOnAction(e -> {
			openViewService(primaryStage, customer);
		});
		
		Button btnHome = new Button("Home");
		btnHome.setOnAction(e ->{
			openCustomerHomePage(primaryStage, customer);
		});
		
		GridPane pane = new GridPane();
		
		pane.setVgap(10); 
        pane.setHgap(10);
        pane.setStyle("-fx-font-size: 16px; " +
	              "-fx-background-color: rgba(120, 163, 212, 0.8); " +
	              "-fx-text-fill: #FFFFFF; " +
	              "-fx-padding: 20px; " +
	              "-fx-background-radius: 10px; " +  // Corner radii
	              "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);" + // Lighter drop shadow effect
	              "-fx-alignment: center; " +  // Center alignment
	              "-fx-min-width: 300px; " +    // Smaller width
	              "-fx-min-height: 500px;");
        Label loginHeader = new Label("Request Quote");
	    loginHeader.setStyle("-fx-text-fill: white; -fx-font-size: 30px; -fx-font-weight: bold;  -fx-font-family: 'Helvetica';"); // Style the header label

	    pane.add(loginHeader, 0, 0, 2, 1); // Add the header label spanning two columns
   
		pane.add(lblWelcome, 0, 1);
		pane.add(lblCustomerId, 1, 1);
		pane.add(lblCustomer_id, 0, 2);
		pane.add(txtCustomer_id, 1, 2);
		pane.add(lblService_id, 0, 3);
		pane.add(txtService_id, 1, 3);
		pane.add(lblServiceName, 0, 4);
		pane.add(txtServiceName, 1, 4);
		pane.add(lblDate, 0, 5);
		pane.add(DatePickerdate, 1, 5);
		pane.add(lblTime, 0, 6);
		pane.add(txtTime, 1, 6);
		pane.add(lblrequirment, 0, 7);
		pane.add(txtRequirement, 1, 7);
		pane.add(lblBudget, 0, 8);
		pane.add(txtBudget, 1, 8);
		pane.add(lblStatus, 0, 9);
		pane.add(cmbStatus, 1, 9);
		
		pane.add(btnRequestQuote, 0, 10);
		pane.add(btnBack, 0, 11);
		pane.add(btnHome, 1, 11);
		
		vbox.getChildren().add(pane);
	    vbox.setAlignment(Pos.CENTER);

	    Scene scene = new Scene(vbox, 600, 400);
	    primaryStage.setScene(scene);
	    Image icon1 = new Image("file:///Users/shrutigurungschool/eclipse-workspace/Assessment/src/icons/icons8-service-48.png");
		primaryStage.getIcons().add(icon1);
        primaryStage.setTitle("Request Quote");
        primaryStage.setWidth(1200);
		primaryStage.setHeight(900);
        primaryStage.setResizable(false);
        primaryStage.show();
	}
	
	public boolean saveRecord(Quote quote) {
		//pid, fullName, address, gender, ageGroup, reading, playing, other, login_id, pass_word
		boolean result = false;
		String DRIVER ="com.mysql.cj.jdbc.Driver";
		String HOST ="localhost";
		int PORT=3306;
		String DATABASE ="SMS";
		String DBUSER="root";
		String DBPASS="Mynameisshrutigurung12c!";
		String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
		String sql="INSERT INTO Quote (customer_id, service_id, date, time,requirement, budget, status) VALUES(?, ?, ?, ?, ?, ?, ?)";

		try {
			Class.forName(DRIVER); //loading driver
			Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setInt(1, quote.getCustomer().getCustomer_id());
			pstat.setInt(2, quote.getService().getService_id());
			pstat.setString(3, quote.getDate());
			pstat.setString(4, quote.getTime());
			pstat.setString(5,quote.getRequirement());
			pstat.setDouble(6, quote.getBudget());
			pstat.setString(7, quote.getStatus());
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

public void openCustomerHomePage(Stage primaryStage, Customer customer) {
		
	    CustomerHomePage customerHomePage = new CustomerHomePage(customer); 
	    try {
	    	customerHomePage.start(new Stage());
	        primaryStage.close(); // 
	    } catch (Exception e) {
	        System.out.println("Error opening Customer Home page: " + e.getMessage());
	    }
	}

	public void openViewService(Stage primaryStage, Customer customer) 
	{
    ViewService servicePage = new ViewService(customer);
    try {
        	servicePage.start(new Stage()); 
        	primaryStage.close(); 
    	} catch (Exception e) 
    		{
    		System.out.println("Error opening Service View page: " + e.getMessage());
    		}
		}
	
}
