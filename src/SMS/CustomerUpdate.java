package SMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerUpdate extends Application {
	
private Customer customer;
	
	public CustomerUpdate() {
	    // Default constructor
	}
	
    public CustomerUpdate(Customer customer) {
        this.customer = customer;
    }

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
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

    	Label lblCustomer_id = new Label("Enter your Customer ID: ");
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
        txtCustomer_id.setText(String.valueOf(customer.getCustomer_id()));
        txtCustomer_id.setEditable(false);
        
        TextField txtFirstName = new TextField();
        TextField txtLastName = new TextField();
        TextField txtAddressStreet = new TextField();
        TextField txtAddressCity = new TextField();
        TextField txtAddressZipCode = new TextField();
        TextField txtPhone = new TextField();
        TextField txtEmail = new TextField();
        TextField txtLoginUsername = new TextField();
        TextField txtLoginPassword = new TextField();
        
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
				alert1.setHeaderText("Your information found succesfully.");
				alert1.showAndWait();
				
				System.out.println("Your information found succesfully");
			}
		
			else {
				System.out.println("Customer not found.");
				Alert alert1 = new Alert(Alert.AlertType.ERROR);
				alert1.setTitle("ERROR");
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
            boolean res = updateRecord(customer);
            if (res) {
                System.out.println("Record Updated");
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
				alert1.setTitle("INFORMATION");
				alert1.setHeaderText("Your information updated successfully.");
				alert1.showAndWait();
				
                // Clear the text fields after successful update
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
                System.out.println("Error: Failed to update your information");
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
				alert1.setTitle("ERROR");
				alert1.setHeaderText("Error: Failed to update your information");
				alert1.setContentText("Please try again");
				alert1.showAndWait();
            }
        });
        
        Button backButton = new Button("Home");
        backButton.setOnAction(event -> {
            CustomerHomePage customerHomePage = new CustomerHomePage(customer);
            try {
                customerHomePage.start(primaryStage);
            } catch (Exception e) {
                System.out.println("Error opening Customer Home Page: " + e.getMessage());
            }
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
        Label loginHeader = new Label("Edit Personal Details");
	    loginHeader.setStyle("-fx-text-fill: white; -fx-font-size: 30px; -fx-font-weight: bold;  -fx-font-family: 'Helvetica';"); // Style the header label

	    pane.add(loginHeader, 0, 0, 2, 1); // Add the header label spanning two columns
   
		pane.add(lblWelcome, 0, 1);
		pane.add(lblCustomerId, 1, 1);
        
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
        pane.add(btnSearch, 1, 14);
        pane.add(btnUpdate, 2, 14);
		pane.add(backButton, 0, 16);
		pane.add(btnClose, 1, 16);
		
		vbox.getChildren().add(pane);
	    vbox.setAlignment(Pos.CENTER);

	    Scene scene = new Scene(vbox, 600, 400);
	    primaryStage.setScene(scene);
	    Image icon1 = new Image("file:///Users/shrutigurungschool/eclipse-workspace/Assessment/src/icons/icons8-service-48.png");
		primaryStage.getIcons().add(icon1);
        primaryStage.setTitle("Edit Personal Details");
        primaryStage.setWidth(1200);
		primaryStage.setHeight(900);
        primaryStage.setResizable(false);
        primaryStage.show();
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

    public boolean updateRecord(Customer customer) {
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

}
