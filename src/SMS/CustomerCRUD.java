package SMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class CustomerCRUD extends Application {
	
	private Staff staff;

    public CustomerCRUD(Staff staff) {
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
	    
    	Label lblCustomer_id = new Label("Customer ID: ");
    	lblCustomer_id.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
    	
        Label lblFirstName = new Label("First Name : ");
        lblFirstName.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
        Label lblLastName = new Label("Last Name : ");
        lblLastName.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
        Label lblStreet = new Label("Address Street : ");
        lblStreet.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
        Label lblCity = new Label("Address City : ");
        lblCity.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
        Label lblZipCode = new Label("Address Zip Code : ");
        lblZipCode.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
        Label lblPhone = new Label("Phone Number : ");
        lblPhone.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
        Label lblEmail = new Label("Email Address : ");
        lblEmail.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
        Label lblLoginUsername = new Label("Login Username: ");
        lblLoginUsername.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
        Label lblLoginPassword = new Label("Login Password : ");
        lblLoginPassword.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
        TextField txtCustomer_id = new TextField();
        TextField txtFirstName = new TextField();
        TextField txtLastName = new TextField();
        TextField txtAddressStreet = new TextField();
        TextField txtAddressCity = new TextField();
        TextField txtAddressZipCode = new TextField();
        TextField txtPhone = new TextField();
        TextField txtEmail = new TextField();
        TextField txtLoginUsername = new TextField();
        TextField txtLoginPassword = new TextField();
      
        Button btnRegister = new Button("Register Customer");
        btnRegister.setOnAction((event) -> {
            // Reading values from UI
        	int customer_id = 0;
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            int address_id= 0;
            String addressStreet = txtAddressStreet.getText();
            String addressCity = txtAddressCity.getText();
            String addressZipCode = txtAddressZipCode.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String loginName = txtLoginUsername.getText();
            String password =  txtLoginPassword.getText();
            
                CustomerAddress address = new CustomerAddress(address_id, addressStreet, addressCity, addressZipCode);

                // Create a Customer object with all details
                Customer customer = new Customer(customer_id,firstName, lastName, address, phone, email, loginName, password);

                boolean res = saveRecord(customer);
                if (res) {
                    System.out.println("Customer registered successfully.");
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
    				alert1.setTitle("INFORMATION");
    				alert1.setHeaderText("Customer registered successfully");
    				alert1.showAndWait();
                } else {
                    System.out.println("Error: Failed to save record");
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
    				alert1.setTitle("INFORMATION");
    				alert1.setHeaderText("Error: Failed to register customer record");
    				alert1.showAndWait();
                }  
            
            
        });

        Button btnClose = new Button("Close");
        btnClose.setOnAction((event) -> {
            primaryStage.close(); // close the window
        });
        
        Button btnSearch = new Button ("Search Customer");
        btnSearch.setOnAction((event) -> {
        	
        	int customer_id = Integer.parseInt(txtCustomer_id.getText());
			Customer customer= searchRecord(customer_id);						
			if(customer!=null) {
				txtFirstName.setText(customer.getFirstName());
				txtLastName.setText(customer.getLastName());
				txtFirstName.setText(customer.getFirstName());
				txtAddressStreet.setText(customer.getAddress().getAddressStreet());
				txtAddressCity.setText(customer.getAddress().getAddressCity());
				txtAddressZipCode.setText(customer.getAddress().getAddressZipCode());
				txtPhone.setText(customer.getPhone());
				txtEmail.setText(customer.getEmail());
				txtLoginUsername.setText(customer.getUser_name());
				txtLoginPassword.setText(customer.getPass_word());
				
				Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
 				alert1.setTitle("INFORMATION");
 				alert1.setHeaderText("Customer found successfully");
 				alert1.showAndWait();
 				
				System.out.println("Customer found succesfully");
			}
		
			else {
				System.out.println("Customer not found.");
				Alert alert1 = new Alert(Alert.AlertType.ERROR);
				alert1.setTitle("INFORMATION");
				alert1.setHeaderText("Customer not found");
				alert1.showAndWait();
            }
		});
        
        Button btnUpdate = new Button("Update");
        btnUpdate.setOnAction((event) -> {
            int customer_id = Integer.parseInt(txtCustomer_id.getText());
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String addressStreet = txtAddressStreet.getText();
            String addressCity = txtAddressCity.getText();
            String addressZipCode = txtAddressZipCode.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String user_name = txtLoginUsername.getText();
            String pass_word = txtLoginPassword.getText();
            
            // Retrieve the address_id from the database
            Customer customerInDB = searchRecord(customer_id);
            int address_id = customerInDB.getAddress().getAddress_id();

            // Create a new CustomerAddress object with the updated information
            CustomerAddress address = new CustomerAddress(address_id, addressStreet, addressCity, addressZipCode);
            
            // Create a new Customer object with the updated information
            
            Customer customer = new Customer(customer_id, firstName, lastName, address, phone, email, user_name, pass_word);
            
            // Update the record in the database
            boolean res = updateRecord(customer, address);
            if (res) {
                System.out.println("Record Updated");
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
				alert1.setTitle("INFORMATION");
				alert1.setHeaderText("Customer Information updated successfully");
				alert1.showAndWait();
				
                // Clear the text fields after successful update
                txtCustomer_id.setText("");
                txtFirstName.setText("");
                txtLastName.setText("");
                txtAddressStreet.setText("");
                txtAddressCity.setText("");
                txtAddressZipCode.setText("");
                txtPhone.setText("");
                txtEmail.setText("");
                txtLoginUsername.setText("");
                txtLoginPassword.setText("");
            } else {
                System.out.println("Error: Failed to update record");
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
				alert1.setTitle("ERROR");
				alert1.setHeaderText("Error: Failed to update Customer Information");
				alert1.showAndWait();
            }
        });

        
        Button btnDelete = new Button ("Delete Customer's Account");
        btnDelete.setOnAction((event) -> {
        	int customer_id = Integer.parseInt(txtCustomer_id.getText());//string->int										
			boolean res = deleteRecord(customer_id); //call method
			if(res==true) {
				System.out.println("Record Deleted");
				Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
				alert1.setTitle("INFORMATION");
				alert1.setHeaderText("Customer Deleted Successfully");
				alert1.showAndWait();
				
				txtCustomer_id.setText("");
		        txtFirstName.setText("");
		        txtLastName.setText("");
		        txtAddressStreet.setText("");
		        txtAddressCity.setText("");
		        txtAddressZipCode.setText("");
		        txtPhone.setText("");
		        txtEmail.setText("");
		        txtLoginUsername.setText("");
		        txtLoginPassword.setText("");
			}
			else {
				System.out.println("Error: to delete record");
				Alert alert1 = new Alert(Alert.AlertType.ERROR);
				alert1.setTitle("ERROR");
				alert1.setHeaderText("Error: to delete record");
				alert1.showAndWait();
			}
		});
        
        Button btnDisplay = new Button ("Display All Customers");
        btnDisplay.setOnAction(event -> {
            // Fetch all records from the database
            ArrayList<Customer> customers = allRecords();
         
            // Create a new stage for displaying records
            Stage displayStage = new Stage();
            displayStage.setTitle("All Customer Records");

            // Create TableView to display records
            TableView<Customer> displayTable = new TableView<>();
            displayTable.setPrefWidth(600);
            displayTable.setPrefHeight(400);

            // Define columns
            TableColumn<Customer, Integer> col1 = new TableColumn<>("Customer ID");
            col1.setCellValueFactory(new PropertyValueFactory<>("customer_id"));

            TableColumn<Customer, String> col2 = new TableColumn<>("First Name");
            col2.setCellValueFactory(new PropertyValueFactory<>("firstName"));

            TableColumn<Customer, String> col3 = new TableColumn<>("Last Name");
            col3.setCellValueFactory(new PropertyValueFactory<>("lastName"));

            TableColumn<Customer, String> col4 = new TableColumn<>("Address Street");
            col4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddressStreet()));
            
            TableColumn<Customer, String> col5 = new TableColumn<>("Address City");
            col5.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddressCity()));
            
            TableColumn<Customer, String> col6 = new TableColumn<>("Address ZipCode");
            col6.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddressZipCode()));
            
            TableColumn<Customer, String> col7 = new TableColumn<>("Phone Number");
            col7.setCellValueFactory(new PropertyValueFactory<>("phone"));

            TableColumn<Customer, String> col8 = new TableColumn<>("Email Address");
            col8.setCellValueFactory(new PropertyValueFactory<>("email"));
            
            TableColumn<Customer, String> col9 = new TableColumn<>("Username");
            col9.setCellValueFactory(new PropertyValueFactory<>("user_name"));
            
            TableColumn<Customer, String> col10 = new TableColumn<>("Password");
            col10.setCellValueFactory(new PropertyValueFactory<>("pass_word"));

            // Add columns to the table
            displayTable.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10);

            // Add data to the table
            displayTable.getItems().addAll(customers);
            
            Button btnClose1 = new Button("Close");
		    btnClose1.setOnAction((e)->{
				displayStage.close(); //close the window
		    });
            // Create a VBox to hold the TableView and additional buttons
            VBox vBox = new VBox(new Label("All Customer Records"), displayTable, btnClose1);
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
        Label loginHeader = new Label("Manage Customer's Account");
	    loginHeader.setStyle("-fx-text-fill: white; -fx-font-size: 30px; -fx-font-weight: bold;  -fx-font-family: 'Helvetica';"); // Style the header label

	    pane.add(loginHeader, 0, 0, 2, 1); // Add the header label spanning two columns
    
        pane.add(lblWelcome, 0, 1);
        pane.add(lblStaffId, 1, 1);
        pane.add(lblCustomer_id, 0, 2);
        pane.add(txtCustomer_id, 1, 2);
        pane.add(lblFirstName, 0, 3);
        pane.add(txtFirstName, 1, 3);
        pane.add(lblLastName, 0, 4);
        pane.add(txtLastName, 1, 4);
        pane.add(lblStreet, 0, 5);
        pane.add(txtAddressStreet, 1, 5);
        pane.add(lblCity, 0, 6);
        pane.add(txtAddressCity, 1, 6);
        pane.add(lblZipCode, 0, 7);
        pane.add(txtAddressZipCode, 1, 7);
        pane.add(lblPhone, 0, 8);
        pane.add(txtPhone, 1, 8);
        pane.add(lblEmail, 0, 9);
        pane.add(txtEmail, 1, 9);
        pane.add(lblLoginUsername, 0, 10);
        pane.add(txtLoginUsername, 1, 10);
        pane.add(lblLoginPassword, 0, 11);
        pane.add(txtLoginPassword, 1, 11);
        pane.add(btnRegister, 1, 12);
        pane.add(btnSearch, 1, 13);
        pane.add(btnUpdate, 2, 13);
		pane.add(btnDelete, 1, 14);
		pane.add(btnDisplay, 2, 14);
		pane.add(btnHome, 0, 15);
		pane.add(btnClose, 1, 15);
		
		vbox.getChildren().add(pane);
	    vbox.setAlignment(Pos.CENTER);

	    Scene scene = new Scene(vbox, 600, 400);
	    primaryStage.setScene(scene);
	    Image icon1 = new Image("file:///Users/shrutigurungschool/eclipse-workspace/Assessment/src/icons/icons8-service-48.png");
		primaryStage.getIcons().add(icon1);
	    primaryStage.setTitle("Manage Customer");
	    primaryStage.setWidth(1200);
		primaryStage.setHeight(900);
	    primaryStage.setResizable(false);
	    primaryStage.show();
    
    }
   
    public boolean saveRecord(Customer customer) {
        boolean result = false;
        String DRIVER = "com.mysql.cj.jdbc.Driver";
        String HOST = "localhost";
        int PORT = 3306;
        String DATABASE = "SMS";
        String DBUSER = "root";
        String DBPASS = "Mynameisshrutigurung12c!";
        String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
        String customerSql = "INSERT INTO Customer (firstName, lastName, address_id, phone, email, user_name, pass_word) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String addressSql = "INSERT INTO CustomerAddress (addressStreet, addressCity, addressZipCode) VALUES (?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement addressPstat = null;
        PreparedStatement customerPstat = null;
        
        try {
            Class.forName(DRIVER); // Loading driver
            conn = DriverManager.getConnection(URL, DBUSER, DBPASS); // Connection with database server
            conn.setAutoCommit(false); // Start transaction
            
            // Insert address into CustomerAddress table
            addressPstat = conn.prepareStatement(addressSql, PreparedStatement.RETURN_GENERATED_KEYS);
            addressPstat.setString(1, customer.getAddress().getAddressStreet());
            addressPstat.setString(2, customer.getAddress().getAddressCity());
            addressPstat.setString(3, customer.getAddress().getAddressZipCode());
            int affectedRows = addressPstat.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating address failed, no rows affected.");
            }
            
            // Retrieve the generated address ID
            int addressId = 0;
            try (ResultSet generatedKeys = addressPstat.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    addressId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating address failed, no ID obtained.");
                }
            }
            
            // Insert customer into Customer table
            customerPstat = conn.prepareStatement(customerSql);
            customerPstat.setString(1, customer.getFirstName());
            customerPstat.setString(2, customer.getLastName());
            customerPstat.setInt(3, addressId); // Use the retrieved address ID
            customerPstat.setString(4, customer.getPhone());
            customerPstat.setString(5, customer.getEmail());
            customerPstat.setString(6, customer.getUser_name());
            customerPstat.setString(7, customer.getPass_word());
            customerPstat.executeUpdate(); // Insert Record
            
            conn.commit(); // Commit transaction
            result = true;
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback transaction in case of exception
                }
            } catch (SQLException e) {
                System.out.println("Rollback error: " + e.getMessage());
            }
        } finally {
            try {
                if (addressPstat != null) {
                    addressPstat.close();
                }
                if (customerPstat != null) {
                    customerPstat.close();
                }
                if (conn != null) {
                    conn.setAutoCommit(true); // Restore auto-commit mode
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return result;
    }
    
    public Customer searchRecord(int customer_id) {
        Customer customer = null;
        CustomerAddress address = null;
        String DRIVER ="com.mysql.cj.jdbc.Driver";
        String HOST ="localhost";
        int PORT=3306;
        String DATABASE ="SMS";
        String DBUSER="root";
        String DBPASS="Mynameisshrutigurung12c!";
        String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
        String sql="SELECT c.*, ca.* FROM Customer c JOIN CustomerAddress ca ON c.address_id = ca.address_id WHERE c.customer_id = ?";
        
        try {
            Class.forName(DRIVER); //loading driver
            Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
            PreparedStatement pstat = conn.prepareStatement(sql);
            
            pstat.setInt(1, customer_id);    
            ResultSet rs = pstat.executeQuery();
           if (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                int address_id = rs.getInt("address_id"); // Retrieve the address_id from the database
                String addressStreet = rs.getString("addressStreet");
                String addressCity = rs.getString("addressCity");
                String addressZipCode = rs.getString("addressZipCode");
                String phone=rs.getString("phone");
                String email= rs.getString("email");
                String loginName=rs.getString("user_name");
                String password= rs.getString("pass_word");

                address = new CustomerAddress(address_id, addressStreet, addressCity, addressZipCode);
                customer = new Customer(customer_id,firstName, lastName, address, phone, email, loginName, password);
           }
            pstat.close();
            conn.close();            
        }
        catch(Exception ex) {
            System.out.println("Error : "+ex.getMessage());
        }
        return customer;
    }

    public boolean updateRecord(Customer customer, CustomerAddress address) {
        boolean result = false;
        String DRIVER = "com.mysql.cj.jdbc.Driver";
        String HOST = "localhost";
        int PORT = 3306;
        String DATABASE = "SMS";
        String DBUSER = "root";
        String DBPASS = "Mynameisshrutigurung12c!";
        String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
        String sql = "UPDATE Customer SET firstName=?, lastName=?, phone=?, email=?, user_name=?, pass_word=? WHERE customer_id=?";
        try {
            Class.forName(DRIVER); //loading driver
            Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
            PreparedStatement pstat = conn.prepareStatement(sql);
            
            // Set values for the customer table
            pstat.setString(1, customer.getFirstName());
            pstat.setString(2, customer.getLastName());
            pstat.setString(3, customer.getPhone());
            pstat.setString(4, customer.getEmail());
            pstat.setString(5, customer.getUser_name());
            pstat.setString(6, customer.getPass_word());
            pstat.setInt(7, customer.getCustomer_id());
            
            pstat.executeUpdate(); // Update customer record

            // Update the address details
            String addressSql = "UPDATE CustomerAddress SET addressStreet=?, addressCity=?, addressZipCode=? WHERE address_id=?";
            PreparedStatement addressPstat = conn.prepareStatement(addressSql);
            addressPstat.setString(1, customer.getAddress().getAddressStreet());
            addressPstat.setString(2, customer.getAddress().getAddressCity());
            addressPstat.setString(3, customer.getAddress().getAddressZipCode());
            addressPstat.setInt(4, customer.getAddress().getAddress_id()); // Update address_id
            addressPstat.executeUpdate(); // Update address record

            pstat.close();
            addressPstat.close();
            conn.close();
            result = true;
        } catch (Exception ex) {
            System.out.println("Error : " + ex.getMessage());
        }
        return result;
    }



	public boolean deleteRecord(int customer_id) {
		boolean result = false;
		String DRIVER ="com.mysql.cj.jdbc.Driver";
		String HOST ="localhost";
		int PORT=3306;
		String DATABASE ="SMS";
		String DBUSER="root";
		String DBPASS="Mynameisshrutigurung12c!";
		String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
		String sql="DELETE FROM Customer  WHERE customer_id=?";
		try {
			Class.forName(DRIVER); //loading driver
			Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
			PreparedStatement pstat = conn.prepareStatement(sql);			
			pstat.setInt(1, customer_id);
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
	
	
	
	public ArrayList<Customer> allRecords() {
		
		Customer customer = null;
        CustomerAddress address = null;
        
        String DRIVER = "com.mysql.cj.jdbc.Driver";
        String HOST = "localhost";
        int PORT = 3306;
        String DATABASE = "SMS";
        String DBUSER = "root";
        String DBPASS = "Mynameisshrutigurung12c!";
        String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
        String sql = " SELECT c.customer_id, c.firstName, c.lastName, ca.addressStreet, ca.addressCity, ca.addressZipCode, c.phone, c.email, c.user_name, c.pass_word FROM Customer c, CustomerAddress ca WHERE c.address_id = ca.address_id" ;
        //String sql = "SELECT c.customer_id, c.firstName, c.lastName, ca.address_id, ca.addressStreet, ca.addressCity, ca.addressZipCode, c.phone, c.email, c.user_name, c.pass_word FROM Customer c JOIN CustomerAddress ca ON c.address_id = ca.address_id";
        ArrayList<Customer> customers = new ArrayList<>();
    
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);
            PreparedStatement pstat = conn.prepareStatement(sql);
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                int customer_id = rs.getInt("customer_id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                int address_id = 0;
                String addressStreet = rs.getString("addressStreet");
                String addressCity = rs.getString("addressCity");
                String addressZipCode = rs.getString("addressZipCode");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String user_name = rs.getString("user_name");
                String pass_word = rs.getString("pass_word");
               
                System.out.println(addressStreet + "," + addressCity + "," +addressZipCode);
                address = new CustomerAddress(address_id, addressStreet, addressCity, addressZipCode);
                customer = new Customer(customer_id, firstName, lastName, address, phone, email, user_name, pass_word);
                customers.add(customer);
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
        return customers;
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
