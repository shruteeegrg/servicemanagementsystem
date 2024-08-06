package SMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WorkerCRUD extends Application {

	
	public WorkerCRUD() {
	    // Default constructor
	}
	
	private Staff staff;


    public WorkerCRUD(Staff staff) {
        this.staff = staff;
    }
    
	public static void main (String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub	    
		
		
		Label lblWelcome = new Label("Welcome, " + staff.getFirstName() + "!");
	    Label lblStaffId = new Label("Your Staff ID: " + staff.getStaff_id());
	    
		String backgroundImage = "url('file:///Users/shrutigurungschool/eclipse-workspace/Assessment/image/remote-team.png')";
	    VBox vbox = new VBox();
	    vbox.setStyle("-fx-background-image: " + backgroundImage + "; " +
	            "-fx-background-position: right center; " +
	            "-fx-background-repeat: no-repeat; " +
	            "-fx-padding: 50px;");
	   
	    Label lblWorker_id = new Label("Staff Worker ID");
	    lblWorker_id.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
	   
		Label lblFirstName = new Label("First Name : ");
		lblFirstName.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblLastName = new Label("Last Name : ");
		lblLastName.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblRole = new Label("Role : ");
		lblRole.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblPhone = new Label("Phone Number : ");
		lblPhone.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblEmail = new Label("Email Address : ");
		lblEmail.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblEmployed = new Label("Employed Since : ");
		lblEmployed.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblSalary = new Label("Salary : ");
		lblSalary.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblStaffAdmin = new Label("Assigned by: ");
		lblStaffAdmin.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");

		
		TextField txtWorker_id = new TextField();
		
		
		TextField txtFirstName = new TextField();
		TextField txtLastName = new TextField();
		TextField txtRole = new TextField();
		TextField txtPhone = new TextField();
		TextField txtEmail = new TextField();
		DatePicker datePickerEmployed = new DatePicker();
		TextField txtSalary = new TextField();
		TextField txtStaffAdmin = new TextField();
		txtStaffAdmin.setText(String.valueOf(staff.getStaff_id()));
		txtStaffAdmin.setEditable(false);
		
		Button btnRegister = new Button("Register");
		
		
		btnRegister.setOnAction((event)->{
			int worker_id = 0; 
            String firstName = txtFirstName.getText();
            String lastName =txtLastName.getText(); 
            String role = txtRole.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String employed = datePickerEmployed.getValue().toString();
            Double salary = Double.parseDouble(txtSalary.getText());
            int staff_id = Integer.parseInt(txtStaffAdmin.getText());
            
            	Staff staff = new Staff(staff_id, null, null, null, null, null, null, 0.0, null, null);
                Worker worker = new Worker(worker_id, firstName, lastName, role, phone, email, employed, salary, staff);
                boolean result = saveStaffRecord(worker);
                if (result) {
                    System.out.println("Record Saved");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("INFORMATION");
                    alert.setHeaderText("Staff Worker registered successfully!");
                    alert.showAndWait();
                    txtFirstName.setText("");
                    txtLastName.setText("");
                    txtRole.setText("");
                    txtPhone.setText("");
                    txtEmail.setText("");
                    datePickerEmployed.setValue(null);
                    txtSalary.setText("");
           
               
                } else {
                    System.out.println("Error: Failed to save record");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("An error occurred while saving the staff worker record");
                    alert.setContentText("Please try again later");
                    alert.showAndWait();
                }
    
		});
		
		
		Button btnClose = new Button("Close");
        btnClose.setOnAction((event) -> {
            primaryStage.close(); // close the window
        });
        
        Button btnSearch = new Button ("Search Staff Worker");
        btnSearch.setOnAction((event) -> {
        	int worker_id = Integer.parseInt(txtWorker_id.getText());
			Worker worker= searchRecord(worker_id);						
			if(worker!=null) {
				txtFirstName.setText(worker.getFirstName());
				txtLastName.setText(worker.getLastName());
				txtRole.setText(worker.getRole());
				txtPhone.setText(worker.getPhone());
				txtEmail.setText(worker.getEmail());
				datePickerEmployed.setValue(LocalDate.parse(worker.getEmployedSince()));
				txtSalary.setText(String.valueOf(worker.getSalary()));
				txtStaffAdmin.setText(String.valueOf(worker.getStaff().getStaff_id()));
			
				Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
 				alert1.setTitle("INFORMATION");
 				alert1.setHeaderText("Worker found successfully");
 				alert1.showAndWait();
 				
				System.out.println("Worker found succesfully");
			}
		
			else {
				System.out.println("Worker not found.");
				Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Staff Worker not found");
                alert.setContentText("Please try again later");
                alert.showAndWait();
            }
		});
        
        Button btnUpdate = new Button("Update");
        btnUpdate.setOnAction((event) -> {
        	int worker_id = Integer.parseInt(txtWorker_id.getText());
        	String firstName = txtFirstName.getText();
            String lastName =txtLastName.getText(); 
            String role = txtRole.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String employedSince = datePickerEmployed.getValue().toString();
            Double salary = Double.parseDouble(txtSalary.getText());
            int staff_id = Integer.parseInt(txtStaffAdmin.getText());
            
        	Staff staff = new Staff(staff_id, null, null, null, null, null, null, 0.0, null, null);
            Worker worker = new Worker(worker_id, firstName, lastName, role, phone, email, employedSince, salary, staff);

          
            boolean res = updateRecord(worker);
            if (res) {
                System.out.println("Record Updated");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION");
                alert.setHeaderText("Staff Worker Information updated successfully!");
                alert.showAndWait();
                
                // Clear the text fields after successful update
                txtWorker_id.setText("");
                txtFirstName.setText("");
                txtLastName.setText("");
                txtRole.setText("");
                txtPhone.setText("");
                txtEmail.setText("");
                datePickerEmployed.setValue(null);
                txtSalary.setText("");
            } else {
                System.out.println("Error: Failed to update record");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error: Failed to update record");
                alert.setContentText("Please try again later");
                alert.showAndWait();
            }
        });

        
        Button btnDelete = new Button ("Delete Worker's Account");
        btnDelete.setOnAction((event) -> {
        	int worker_id = Integer.parseInt(txtWorker_id.getText());									
			boolean res = deleteRecord(worker_id); //call method
			if(res==true) {
				System.out.println("Record Deleted");
				 Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                alert.setTitle("INFORMATION");
	                alert.setHeaderText("Staff Worker deleted successfully!");
	                alert.showAndWait();
				txtWorker_id.setText("");
                txtFirstName.setText("");
                txtLastName.setText("");
                txtRole.setText("");
                txtPhone.setText("");
                txtEmail.setText("");
                datePickerEmployed.setValue(null);
                txtSalary.setText("");
			}
			else {
				System.out.println("Error: to delete record");
				Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error: Failed to delete record");
                alert.setContentText("Please try again later");
                alert.showAndWait();
			}
		});
        
        Button btnDisplay = new Button ("Display All Staff Workers");
        btnDisplay.setOnAction(event -> {
            // Fetch all records from the database
            ArrayList<Worker> workers = allRecords();
         
            // Create a new stage for displaying records
            Stage displayStage = new Stage();
            displayStage.setTitle("All Staff Workers Records");

            // Create TableView to display records
            TableView<Worker> displayTable = new TableView<>();
            displayTable.setPrefWidth(600);
            displayTable.setPrefHeight(400);

            // Define columns
            TableColumn<Worker, Integer> col1 = new TableColumn<>("Staff Worker ID");
            col1.setCellValueFactory(new PropertyValueFactory<>("worker_id"));

            TableColumn<Worker, String> col2 = new TableColumn<>("First Name");
            col2.setCellValueFactory(new PropertyValueFactory<>("firstName"));

            TableColumn<Worker, String> col3 = new TableColumn<>("Last Name");
            col3.setCellValueFactory(new PropertyValueFactory<>("lastName"));

            TableColumn<Worker, String> col4 = new TableColumn<>("Role");
            col4.setCellValueFactory(new PropertyValueFactory<>("role"));
            
            TableColumn<Worker, String> col5 = new TableColumn<>("Phone Number");
            col5.setCellValueFactory(new PropertyValueFactory<>("phone"));

            TableColumn<Worker, String> col6 = new TableColumn<>("Email Address");
            col6.setCellValueFactory(new PropertyValueFactory<>("email"));
            
            TableColumn<Worker, String> col7 = new TableColumn<>("Employed Since");
            col7.setCellValueFactory(new PropertyValueFactory<>("employedSince"));
            
            TableColumn<Worker, Double> col8 = new TableColumn<>("Salary");
            col8.setCellValueFactory(new PropertyValueFactory<>("salary"));
          

            // Add columns to the table
            displayTable.getColumns().add(col1);
            displayTable.getColumns().add(col2);
            displayTable.getColumns().add(col3);
            displayTable.getColumns().add(col4);
            displayTable.getColumns().add(col5);
            displayTable.getColumns().add(col6);
            displayTable.getColumns().add(col7);
            displayTable.getColumns().add(col8);
            
            
            // Add data to the table
            displayTable.getItems().addAll(workers);
            Button btnClose1 = new Button("Close");
		    btnClose1.setOnAction((e)->{
				displayStage.close(); //close the window
		    });
            // Create a VBox to hold the TableView and additional buttons
            VBox vBox = new VBox(new Label("All Staff Worker Records"), displayTable, btnClose1);
            vBox.setSpacing(10);
            vBox.setAlignment(Pos.CENTER);

            // Create a scene and set it to the stage
            Scene displayScene = new Scene(vBox);
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
        Label loginHeader = new Label("STAFF WORKER");
	    loginHeader.setStyle("-fx-text-fill: white; -fx-font-size: 30px; -fx-font-weight: bold;  -fx-font-family: 'Helvetica';"); // Style the header label

	    pane.add(loginHeader, 0, 0, 2, 1); // Add the header label spanning two columns

	    pane.add(lblWelcome, 0, 1);
	    pane.add(lblStaffId, 1, 1);
	    
	    pane.add(lblWorker_id, 0, 2);
	    pane.add(txtWorker_id, 1, 2);
	  
		pane.add(lblFirstName, 0, 4);
		pane.add(txtFirstName, 1, 4);
		pane.add(lblLastName, 0, 5);
		pane.add(txtLastName, 1, 5);
		pane.add(lblRole, 0, 6);
		pane.add(txtRole, 1, 6);		
		pane.add(lblPhone, 0, 7);
		pane.add(txtPhone, 1, 7);
		pane.add(lblEmail, 0, 8);
		pane.add(txtEmail, 1, 8);
		pane.add(lblEmployed, 0, 9);
		pane.add(datePickerEmployed, 1, 9);
		pane.add(lblSalary, 0, 10);
		pane.add(txtSalary, 1, 10);
		pane.add(lblStaffAdmin, 0, 11);
		pane.add(txtStaffAdmin, 1, 11);
		
		pane.add(btnRegister, 1, 12);
		pane.add(btnSearch, 2, 12);
		pane.add(btnUpdate, 3, 12);
		pane.add(btnDelete, 4, 12);
		pane.add(btnDisplay, 5, 12);
		pane.add(btnHome, 2, 13);
		pane.add(btnClose, 3, 13);
		
		vbox.getChildren().add(pane);
	    vbox.setAlignment(Pos.CENTER);

	    Scene scene = new Scene(vbox, 600, 400);
	    primaryStage.setScene(scene);
	    Image icon1 = new Image("file:///Users/shrutigurungschool/eclipse-workspace/Assessment/src/icons/icons8-service-48.png");
		primaryStage.getIcons().add(icon1);
	    primaryStage.setTitle("Service Management System ");
	    primaryStage.setWidth(1200);
		primaryStage.setHeight(900);
	    primaryStage.setResizable(false);
	    primaryStage.show();

	}
	
	public boolean saveStaffRecord(Worker worker) {
        boolean result = false;
        String DRIVER = "com.mysql.cj.jdbc.Driver";
        String HOST = "localhost";
        int PORT = 3306;
        String DATABASE ="SMS";
		String DBUSER="root";
		String DBPASS="Mynameisshrutigurung12c!";
		String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
        String sql = "INSERT INTO Worker (firstName, lastName, role, phone, email, employedSince, salary, staff_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


        try
        	(Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);
            PreparedStatement pstat = conn.prepareStatement(sql))
            {
            pstat.setString(1, worker.getFirstName());
            pstat.setString(2, worker.getLastName());
            pstat.setString(3, worker.getRole());
            pstat.setString(4, worker.getPhone());
            pstat.setString(5, worker.getEmail());
            pstat.setString(6, worker.getEmployedSince());
            pstat.setDouble(7, worker.getSalary());
            pstat.setInt(8, worker.getStaff().getStaff_id());

            int affectedRows = pstat.executeUpdate();
            result = affectedRows > 0;
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return result;
    }
	public Worker searchRecord(int worker_id) {
		
        Worker worker = null;
       Staff staff = null;
        String DRIVER ="com.mysql.cj.jdbc.Driver";
        String HOST ="localhost";
        int PORT=3306;
        String DATABASE ="SMS";
        String DBUSER="root";
        String DBPASS="Mynameisshrutigurung12c!";
        String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
        String sql="SELECT * from Worker where worker_id = ?";
     
        try {
            Class.forName(DRIVER); //loading driver
            Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
            PreparedStatement pstat = conn.prepareStatement(sql);
            
            pstat.setInt(1, worker_id);    
            ResultSet rs = pstat.executeQuery();
           if (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String role = rs.getString("role");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String employedSince =rs.getString("employedSince");
                double  salary = rs.getDouble("salary");
                int staff_id = rs.getInt("staff_id");
                
            	 staff = new Staff(staff_id, null, null, null, null, null, null, 0.0, null, null);
                 worker = new Worker(worker_id, firstName, lastName, role, phone, email, employedSince, salary, staff);
                
           }
            pstat.close();
            conn.close();            
        }
        catch(Exception ex) {
            System.out.println("Error : "+ex.getMessage());
        }
        return worker;
    }

    public boolean updateRecord(Worker worker) {
        boolean result = false;
        String DRIVER = "com.mysql.cj.jdbc.Driver";
        String HOST = "localhost";
        int PORT = 3306;
        String DATABASE = "SMS";
        String DBUSER = "root";
        String DBPASS = "Mynameisshrutigurung12c!";
        String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
        String sql = "UPDATE Worker SET firstName=?, lastName=?, role=?, phone=?, email=?, employedSince=?, salary=?, staff_id=?  WHERE worker_id=?";
        try {
            Class.forName(DRIVER); //loading driver
            Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
            PreparedStatement pstat = conn.prepareStatement(sql);
           
            pstat.setString(1, worker.getFirstName());
            pstat.setString(2, worker.getLastName());
            pstat.setString(3, worker.getRole());
            pstat.setString(4, worker.getPhone());
            pstat.setString(5, worker.getEmail());
            pstat.setString(6, worker.getEmployedSince());
            pstat.setDouble(7, worker.getSalary());
            pstat.setInt(8, worker.getStaff().getStaff_id());
            pstat.setInt(9, worker.getWorker_id());
            pstat.executeUpdate();
            pstat.close();         
            conn.close();
            result = true;
        } catch (Exception ex) {
            System.out.println("Error : " + ex.getMessage());
        }
        return result;
    }



	public boolean deleteRecord(int worker_id) {
		boolean result = false;
		String DRIVER ="com.mysql.cj.jdbc.Driver";
		String HOST ="localhost";
		int PORT=3306;
		String DATABASE ="SMS";
		String DBUSER="root";
		String DBPASS="Mynameisshrutigurung12c!";
		String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
		String sql="DELETE FROM Worker  WHERE worker_id=?";
		try {
			Class.forName(DRIVER); //loading driver
			Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
			PreparedStatement pstat = conn.prepareStatement(sql);			
			pstat.setInt(1, worker_id);
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
	
	
	
	public ArrayList<Worker> allRecords() {
		
		Worker worker = null;
		Staff staff = null;
		
        String DRIVER = "com.mysql.cj.jdbc.Driver";
        String HOST = "localhost";
        int PORT = 3306;
        String DATABASE = "SMS";
        String DBUSER = "root";
        String DBPASS = "Mynameisshrutigurung12c!";
        String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
        String sql = " SELECT * FROM Worker;";
        ArrayList<Worker> workers = new ArrayList<>();
    
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);
            PreparedStatement pstat = conn.prepareStatement(sql);
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                int worker_id = rs.getInt("worker_id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String role = rs.getString("role");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String employedSince = rs.getString("employedSince");
                Double salary = rs.getDouble("salary");
                int staff_id = rs.getInt("staff_id");
                
           	 	staff = new Staff(staff_id, null, null, null, null, null, null, 0.0, null, null);
                worker = new Worker(worker_id, firstName, lastName, role, phone, email, employedSince, salary, staff);
                workers.add(worker);
            }
            rs.close();
            pstat.close();
            conn.close();
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Error loading JDBC driver: " + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return workers;
    }
	public void openStaffHomePage(Stage primaryStage, Staff staff) {
	    StaffHomePage staffHomePage = new StaffHomePage(staff); 
	    try {
	    	staffHomePage.start(new Stage()); 
	        primaryStage.close(); 
	    } catch (Exception e) {
	        System.out.println("Error opening Staff Home page: " + e.getMessage());
	        Alert alert = new Alert(Alert.AlertType.ERROR);
		    alert.setTitle("Error");
		    alert.setHeaderText("Error opening Staff Home page");
		    alert.showAndWait();
	    }
	}
	

	}