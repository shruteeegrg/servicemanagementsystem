package SMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javafx.application.Application;
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



public class ServiceCRUD extends Application{

	private Staff staff;

    public ServiceCRUD(Staff staff) {
        this.staff = staff;
    }
    
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Label lblWelcome = new Label("Welcome, " + staff.getFirstName() + "!");
	    Label lblStaffId = new Label("Your Staff ID: " + staff.getStaff_id());
	    
	    String backgroundImage = "url('file:///Users/shrutigurungschool/eclipse-workspace/Assessment/image/remote-team.png')";
	    VBox vbox = new VBox();
	    vbox.setStyle("-fx-background-image: " + backgroundImage + "; " +
	            "-fx-background-position: right center; " +
	            "-fx-background-repeat: no-repeat; " +
	            "-fx-padding: 50px;");
		
		Label lblService_id = new Label("Service_ID");
		lblService_id.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		TextField txtService_id = new TextField();
	    
		Label lblName = new Label("Service Name: ");
		lblName.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblCategory = new Label ("Service Category : ");
		lblCategory.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblDescription  = new Label("Service Description: ");
		lblDescription.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblDuration = new Label ("Estimated Duration: ");
		lblDuration.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label  lblCharge = new Label ("Charge: ");
		lblCharge.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblImagePath = new Label ("Image Path");
		lblImagePath.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		TextField txtName = new TextField();
		ComboBox<String> cmbCategory = new ComboBox<>();
		cmbCategory.getItems().addAll("Repair", "Installation", "Maintenance", "Consultation");
		TextField txtDescription = new TextField();
		TextField txtDuration = new TextField();
		TextField txtCharge = new TextField();
		TextField txtImagePath = new TextField();
	
		Button btnSave = new Button("Save");
		btnSave.setOnAction((event) -> {
			//Reading values from UI
			int service_id = 0;
			String serviceName = txtName.getText();
			String serviceCategory = cmbCategory.getValue().toString();
			String serviceDescription = txtDescription.getText();
			String estimatedDuration = txtDuration.getText();
			Double servicecharge = Double.parseDouble(txtCharge.getText());
			String serviceImage = txtImagePath.getText();
			
			Service service=new Service(service_id, serviceName, serviceCategory, serviceDescription, estimatedDuration, servicecharge, serviceImage);			
			boolean res = saveRecord(service); //call method
			if(res==true) {
				System.out.println("Record Saved");
				 Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
 				alert1.setTitle("INFORMATION");
 				alert1.setHeaderText("Service Added successfully");
 				alert1.showAndWait();
				txtName.setText("");
				cmbCategory.setValue(null);
				txtDescription.setText("");
				txtDuration.setText("");
				txtCharge.setText("");
				txtImagePath.setText("");
			
			}
			else {
				System.out.println("Error: to save record");
				 Alert alert1 = new Alert(Alert.AlertType.ERROR);
	 				alert1.setTitle("ERROR");
	 				alert1.setHeaderText("Error: to save service record");
	 				alert1.showAndWait();
			}
		
				
		
		});	
		
		Button btnSearch=new Button("Search");
		btnSearch.setOnAction((event)->{
			
			int service_id = Integer.parseInt(txtService_id.getText());//String->int			
			Service service= searchRecord(service_id);						
			if(service!=null) {
				txtName.setText(service.getServiceName());			
				String serviceCategory = service.getServiceCategory();
				if(serviceCategory.equals("Repair")) {
					cmbCategory.getSelectionModel().select(0);	
				}
				else if(serviceCategory.equals("Installation")) {
					cmbCategory.getSelectionModel().select(1);	
				}
				else if(serviceCategory.equals("Maintenance")) {
					cmbCategory.getSelectionModel().select(2);	
				}
				else if(serviceCategory.equals("Consultation")) {
					cmbCategory.getSelectionModel().select(3);	
				}
				txtDescription.setText(service.getServiceDescription());
				txtDuration.setText(service.getEstimatedDuration());
				txtCharge.setText(String.valueOf(service.getCharge()));
				txtImagePath.setText(service.getServiceImage());
				
				Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
 				alert1.setTitle("INFORMATION");
 				alert1.setHeaderText("Record found successfully");
 				alert1.showAndWait();
				System.out.println("Record found");
				
			}
			else {
				System.out.println("Record not found");
				Alert alert1 = new Alert(Alert.AlertType.ERROR);
 				alert1.setTitle("ERROR");
 				alert1.setHeaderText("Service record not found");
 				alert1.showAndWait();
			}
		});

		
		Button btnUpdate = new Button("Update");
		btnUpdate.setOnAction((event) -> {
			
			int service_id = Integer.parseInt(txtService_id.getText());
			String serviceName = txtName.getText();
			String serviceCategory = cmbCategory.getValue().toString();
			String serviceDescription = txtDescription.getText();
			String estimatedDuration = txtDuration.getText();
			Double servicecharge = Double.parseDouble(txtCharge.getText());
			String serviceImage = txtImagePath.getText();
			
			Service service=new Service(service_id, serviceName, serviceCategory, serviceDescription, estimatedDuration, servicecharge, serviceImage);	
			boolean res = updateRecord(service); //call method
			if(res==true) {
				System.out.println("Record Updated");
				Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
 				alert1.setTitle("INFORMATION");
 				alert1.setHeaderText("Service Record Updated Successfully!");
 				alert1.showAndWait();
				txtService_id.setText("");
				txtName.setText("");
				cmbCategory.setValue(null);
				txtDescription.setText("");
				txtDuration.setText("");
				txtCharge.setText("");
				txtImagePath.setText("");
			}
			else {
				System.out.println("Error: to update record");
				Alert alert1 = new Alert(Alert.AlertType.ERROR);
 				alert1.setTitle("ERROR");
 				alert1.setHeaderText("Error: to update service record");
 				alert1.showAndWait();
			}
		
				
		});
		
		
		Button btnDelete = new Button("Delete");
		btnDelete.setOnAction(event -> {
			int service_id = Integer.parseInt(txtService_id.getText());//string->int										
			boolean res = deleteRecord(service_id); //call method
			if(res==true) {
				System.out.println("Record Deleted");
				Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
 				alert1.setTitle("INFORMATION");
 				alert1.setHeaderText("Service Record Deleted Successfully!");
 				alert1.showAndWait();
				txtService_id.setText("");
				txtName.setText("");
				cmbCategory.setValue(null);
				txtDescription.setText("");
				txtDuration.setText("");
				txtCharge.setText("");
				txtImagePath.setText("");
			}
			else {
				System.out.println("Error: to delete record");
				Alert alert1 = new Alert(Alert.AlertType.ERROR);
 				alert1.setTitle("ERROR");
 				alert1.setHeaderText("Error: to delete service record");
 				alert1.showAndWait();
				
			}
		});
		
		Button btnDisplay = new Button("Display All Record");
		btnDisplay.setOnAction(event -> {      
		    // Fetch all records from the database
		    ArrayList<Service> services = allRecords();

		    // Create a new stage for displaying records
		    Stage displayStage = new Stage();
		    displayStage.setTitle("All Service Records");

		    // Create TableView to display records
		    TableView<Service> displayTable = new TableView<>();
		    displayTable.setPrefWidth(600);
		    displayTable.setPrefHeight(400);
		    

		    // Define columns
		    TableColumn<Service, Integer> idCol = new TableColumn<>("Service ID");
		    idCol.setCellValueFactory(new PropertyValueFactory<>("service_id"));

		    TableColumn<Service, String> nameCol = new TableColumn<>("Service Name");
		    nameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));

		    TableColumn<Service, String> categoryCol = new TableColumn<>("Category");
		    categoryCol.setCellValueFactory(new PropertyValueFactory<>("serviceCategory"));

		    TableColumn<Service, String> descriptionCol = new TableColumn<>("Description");
		    descriptionCol.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));

		    TableColumn<Service, String> durationCol = new TableColumn<>("Duration");
		    durationCol.setCellValueFactory(new PropertyValueFactory<>("estimatedDuration"));

		    TableColumn<Service, Double> chargeCol = new TableColumn<>("Charge");
		    chargeCol.setCellValueFactory(new PropertyValueFactory<>("charge"));
		    
		    TableColumn<Service, String> imageCol = new TableColumn<>("Image Path");
		    imageCol.setCellValueFactory(new PropertyValueFactory<>("serviceImage"));

		    // Add columns to the table
		    displayTable.getColumns().addAll(idCol, nameCol, categoryCol, descriptionCol, durationCol, chargeCol, imageCol);

		    // Add data to the table
		    displayTable.getItems().addAll(services);
		    	    
		    Button btnClose = new Button("Close");
		    btnClose.setOnAction((e)->{
				displayStage.close(); //close the window
			}); 
		    // Create a scene and set it to the stage
		    Scene displayScene = new Scene(new VBox(new Label("All Service Records"), displayTable, btnClose));
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
        pane.setStyle("-fx-font-size: 16px; " +
	              "-fx-background-color: rgba(120, 163, 212, 0.8); " +
	              "-fx-text-fill: #FFFFFF; " +
	              "-fx-padding: 20px; " +
	              "-fx-background-radius: 10px; " +  // Corner radii
	              "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);" + // Lighter drop shadow effect
	              "-fx-alignment: center; " +  // Center alignment
	              "-fx-min-width: 300px; " +    // Smaller width
	              "-fx-min-height: 500px;");
        
        Label loginHeader = new Label("Manage Services");
	    loginHeader.setStyle("-fx-text-fill: white; -fx-font-size: 30px; -fx-font-weight: bold;  -fx-font-family: 'Helvetica';"); // Style the header label

	    pane.add(loginHeader, 0, 0, 2, 1); // Add the header label spanning two columns

	    pane.add(lblStaffId, 1, 1);
	    
		pane.add(lblService_id, 0, 2);
		pane.add(txtService_id, 1, 2);
		pane.add(lblName, 0, 3);
		pane.add(txtName, 1, 3);
		pane.add(lblCategory, 0, 4);
		pane.add(cmbCategory, 1, 4);
		pane.add(lblDescription, 0, 5);
		pane.add(txtDescription, 1, 5);
		pane.add(lblDuration, 0, 6);
		pane.add(txtDuration, 1, 6);
		pane.add(lblCharge, 0, 7);
		pane.add(txtCharge, 1, 7);
		pane.add(lblImagePath, 0, 8);
		pane.add(txtImagePath, 1, 8);
		pane.add(btnSave, 0, 9);
		pane.add(btnSearch, 1, 9);
		pane.add(btnUpdate, 2, 9);
		pane.add(btnDelete, 0, 10);
		pane.add(btnDisplay, 1, 10);
		pane.add(btnHome, 1,11);
		pane.add(btnClose, 2, 11);
		
		vbox.getChildren().add(pane);
	    vbox.setAlignment(Pos.CENTER);

	    Scene scene = new Scene(vbox, 600, 400);
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("Service Management");
	    Image icon1 = new Image("file:///Users/shrutigurungschool/eclipse-workspace/Assessment/src/icons/icons8-service-48.png");
		primaryStage.getIcons().add(icon1);
	    primaryStage.setWidth(1200);
		primaryStage.setHeight(900);
	    primaryStage.setResizable(false);
	    primaryStage.show();
	}
	
	public boolean saveRecord(Service service) {
		//pid, fullName, address, gender, ageGroup, reading, playing, other, login_id, pass_word
		boolean result = false;
		String DRIVER ="com.mysql.cj.jdbc.Driver";
		String HOST ="localhost";
		int PORT=3306;
		String DATABASE ="SMS";
		String DBUSER="root";
		String DBPASS="Mynameisshrutigurung12c!";
		String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
		String sql="INSERT INTO Service (serviceName, serviceCategory, serviceDescription, estimatedDuration, charge, serviceImage) VALUES(?, ?, ?, ?, ?, ?)";

		try {
			Class.forName(DRIVER); //loading driver
			Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, service.getServiceName());
			pstat.setString(2, service.getServiceCategory());
			pstat.setString(3, service.getServiceDescription());
			pstat.setString(4, service.getEstimatedDuration());
			pstat.setDouble(5, service.getCharge());
			pstat.setString(6, service.getServiceImage());
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
	
	public Service searchRecord(int service_id) {
		//pid, fullName, address, gender, ageGroup, reading, playing, other, login_id, pass_word
		Service service = null;
		String DRIVER ="com.mysql.cj.jdbc.Driver";
		String HOST ="localhost";
		int PORT=3306;
		String DATABASE ="SMS";
		String DBUSER="root";
		String DBPASS="Mynameisshrutigurung12c!";
		String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
		String sql="SELECT * FROM Service WHERE service_id=?";
		try {
			Class.forName(DRIVER); //loading driver
			Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setInt(1, service_id);			
			ResultSet rs = pstat.executeQuery();
			while(rs.next()) {
				String serviceName = rs.getString("serviceName");
				String serviceCategory = rs.getString("serviceCategory");
				String serviceDescription = rs.getString("serviceDescription");
				String estimatedDuration=rs.getString("estimatedDuration");
				Double serviceCharge= rs.getDouble("charge");
				String serviceImage=rs.getString("serviceImage");
				service =new Service(service_id, serviceName, serviceCategory, serviceDescription, estimatedDuration, serviceCharge, serviceImage);
			}
			pstat.close();
			conn.close();			
		}
		catch(Exception ex) {
			System.out.println("Error : "+ex.getMessage());
		}
		return service;
	}
	public boolean updateRecord(Service service) {
	    //pid, fullName, address, gender, ageGroup, reading, playing, other, login_id, pass_word
	    boolean result = false;
	    String DRIVER ="com.mysql.cj.jdbc.Driver";
	    String HOST ="localhost";
	    int PORT=3306;
	    String DATABASE ="SMS";
	    String DBUSER="root";
	    String DBPASS="Mynameisshrutigurung12c!";
	    String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
	    String sql="UPDATE Service SET serviceName=?, serviceCategory=?, serviceDescription=?, estimatedDuration=?, charge=?, serviceImage=? WHERE service_id=?";
	    try {
	        Class.forName(DRIVER); //loading driver
	        Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
	        PreparedStatement pstat = conn.prepareStatement(sql);            
	        pstat.setString(1, service.getServiceName());
	        pstat.setString(2, service.getServiceCategory());
	        pstat.setString(3, service.getServiceDescription());
	        pstat.setString(4, service.getEstimatedDuration());
	        pstat.setDouble(5, service.getCharge());
	        pstat.setString(6, service.getServiceImage());
	        pstat.setInt(7, service.getService_id()); // Assuming getServiceId() returns the service ID
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

	public boolean deleteRecord(int service_id) {
		//pid, fullName, address, gender, ageGroup, reading, playing, other, login_id, pass_word
		boolean result = false;
		String DRIVER ="com.mysql.cj.jdbc.Driver";
		String HOST ="localhost";
		int PORT=3306;
		String DATABASE ="SMS";
		String DBUSER="root";
		String DBPASS="Mynameisshrutigurung12c!";
		String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
		String sql="DELETE FROM Service  WHERE service_id=?";
		try {
			Class.forName(DRIVER); //loading driver
			Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
			PreparedStatement pstat = conn.prepareStatement(sql);			
			pstat.setInt(1, service_id);
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
		//pid, fullName, address, gender, ageGroup, reading, playing, other, login_id, pass_word
		
		ArrayList<Service> services = new ArrayList<Service>();
		String DRIVER ="com.mysql.cj.jdbc.Driver";
		String HOST ="localhost";
		int PORT=3306;
		String DATABASE ="SMS";
		String DBUSER="root";
		String DBPASS="Mynameisshrutigurung12c!";
		String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
		String sql="SELECT * FROM Service";
		try {
			Class.forName(DRIVER); //loading driver
			Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
			PreparedStatement pstat = conn.prepareStatement(sql);			
			ResultSet rs = pstat.executeQuery();
			while(rs.next()) {
				int service_id = rs.getInt("service_id");
				String serviceName = rs.getString("serviceName");
				String serviceCategory = rs.getString("serviceCategory");
				String serviceDescription = rs.getString("serviceDescription");
				String estimatedDuration=rs.getString("estimatedDuration");
				Double serviceCharge= rs.getDouble("charge");
				String imagePath=rs.getString("serviceImage");
				
				Service service =new Service(service_id, serviceName, serviceCategory, serviceDescription, estimatedDuration, serviceCharge, imagePath);
				services.add(service);
			}
			pstat.close();
			conn.close();			
		}
		catch(Exception ex) {
			System.out.println("Error : "+ex.getMessage());
		}
		return services;
	}

	//Method to open Staff Home Page
	public void openStaffHomePage(Stage primaryStage, Staff staff) {
	    StaffHomePage staffHomePage = new StaffHomePage(staff); 
	    try {
	    	staffHomePage.start(new Stage()); 
	        primaryStage.close(); 
	    } catch (Exception e) {
	        System.out.println("Error opening Staff Home  page: " + e.getMessage());
	    }
	}

}
