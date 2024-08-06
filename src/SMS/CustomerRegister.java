package SMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class CustomerRegister extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	String backgroundImage = "url('file:///Users/shrutigurungschool/eclipse-workspace/Assessment/image/remote-team.png')";
	    VBox vbox = new VBox();
	    vbox.setStyle("-fx-background-image: " + backgroundImage + "; " +
	            "-fx-background-position: right center; " +
	            "-fx-background-repeat: no-repeat; " +
	            "-fx-padding: 50px;");
	    
        Label lblFirstName = new Label("First Name : ");
        lblFirstName.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
        Label lblLastName = new Label("Last Name : ");
        lblLastName.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
        Label lblAddress = new Label("Address : ");
        lblAddress.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
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
        
        Label lblRePassword = new Label("Re - Password: ");
        lblRePassword.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
       
        Label lblLogin = new Label("Already have an account? ");
        lblLogin.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
        
		Label lblNewRegister = new Label("Or Newly registered user? ");
		lblNewRegister.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");

        TextField txtFirstName = new TextField();
        TextField txtLastName = new TextField();
        TextField txtAddressStreet = new TextField();
        TextField txtAddressCity = new TextField();
        TextField txtAddressZipCode = new TextField();
        TextField txtPhone = new TextField();
        TextField txtEmail = new TextField();
        TextField txtLoginUsername = new TextField();

        PasswordField txtLoginPassword = new PasswordField();
        PasswordField txtRePassword = new PasswordField();
        
        Image registerIcon = new Image(getClass().getResourceAsStream("/icons/icons8-register-48-3.png"));
        ImageView registerView = new ImageView(registerIcon);
        Button btnRegister = new Button("Register",registerView );
        registerView.setFitWidth(31); 
        registerView.setFitHeight(31);
        btnRegister.setStyle("-fx-background-color: white; -fx-text-fill: #18193F;");
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
            String password = "";
		    
            if (txtLoginPassword.getText().equals(txtRePassword.getText())) {
                password = txtLoginPassword.getText();
                
             // Validate phone number
    		    if (!phone.matches("\\d{10}")) {
    		        showAlert("Phone Number must be 10 digits");
    		        txtPhone.clear();
    		        return;
    		    }

    		    // Validate email format
    		    if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
    		        showAlert("Please enter a valid email");
    		        txtEmail.clear();
    		        return;
    		    }

    		    // Validate password length
    		    if (password.length() < 8) {
    		        showAlert("Password must be at least 8 characters long");
    		        txtLoginPassword.clear();
    		        txtRePassword.clear();
    		        return;
    		    }
    		    
                CustomerAddress address = new CustomerAddress(address_id, addressStreet, addressCity, addressZipCode);
                Customer customer = new Customer(customer_id,firstName, lastName, address, phone, email, loginName, password);

                boolean res = saveRecord(customer);
                if (res) {
                    System.out.println("Record Saved");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("INFORMATION");
                    alert.setHeaderText("Registered Succesfully! ");
                    alert.setContentText("Now procced through LOGIN to access your account.");
                    alert.showAndWait();
                    openLoginPage(primaryStage);
                    
                } else {
                    System.out.println("Error: Failed to save record");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error: Failed to register the customer");
                    alert.setContentText("Please try again later");
                    alert.showAndWait();
                }
            } else {
                System.out.println("Re- password must match with the intial password.");
                txtLoginPassword.setText("");
                txtRePassword.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The Password is invalid");
                alert.setContentText("Please try again later");
                alert.showAndWait();
                return;
            }
            
            
        }); 
        Button btnLogin = new Button ("Login Here");
        btnLogin.setStyle("-fx-background-color: white ; -fx-text-fill:  #18193F;");
		btnLogin.setOnAction((event) -> {
			openLoginPage(primaryStage);
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
      
      Label loginHeader = new Label("REGISTRATION");
	    loginHeader.setStyle("-fx-text-fill: white; -fx-font-size: 30px; -fx-font-weight: bold;  -fx-font-family: 'Helvetica';"); // Style the header label

	    pane.add(loginHeader, 0, 0, 2, 1); // Add the header label spanning two columns

      
        pane.add(lblFirstName, 0, 1);
        pane.add(txtFirstName, 1, 1);
        pane.add(lblLastName, 0, 2);
        pane.add(txtLastName, 1, 2);
        pane.add(lblStreet, 0, 3);
        pane.add(txtAddressStreet, 1, 3);
        pane.add(lblCity, 0, 4);
        pane.add(txtAddressCity, 1, 4);
        pane.add(lblZipCode, 0, 5);
        pane.add(txtAddressZipCode, 1, 5);
        pane.add(lblPhone, 0, 6);
        pane.add(txtPhone, 1, 6);
        pane.add(lblEmail, 0, 7);
        pane.add(txtEmail, 1, 7);
        pane.add(lblLoginUsername, 0, 8);
        pane.add(txtLoginUsername, 1, 8);
        pane.add(lblLoginPassword, 0, 9);
        pane.add(txtLoginPassword, 1, 9);
        pane.add(lblRePassword, 0, 10);
        pane.add(txtRePassword, 1, 10);
        pane.add(btnRegister, 1, 11);
  
        pane.add(lblLogin, 1, 12);
		pane.add(btnLogin, 2, 12);
		pane.add(lblNewRegister, 1, 13);

        vbox.getChildren().add(pane);
	    vbox.setAlignment(Pos.CENTER);

	    Scene scene = new Scene(vbox, 600, 400);
	    primaryStage.setScene(scene);
	    Image icon1 = new Image("file:///Users/shrutigurungschool/eclipse-workspace/Assessment/src/icons/icons8-service-48.png");
		primaryStage.getIcons().add(icon1);
        primaryStage.setTitle("Customer Registration");
        primaryStage.setWidth(1200);
		primaryStage.setHeight(900);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
 // Method to show alert dialog
	private void showAlert(String message){
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText(message);
	    alert.showAndWait();
	}
	
    public void openLoginPage(Stage primaryStage) {
	    Login loginPage = new Login(); // Create an instance of the InsertPerson page
	    try {
	    	loginPage.start(new Stage()); // Start the Login page
	        primaryStage.close(); // Close the registration window
	    } catch (Exception e) {
	        System.out.println("Error opening Login page: " + e.getMessage());
	    }
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
    
    

}
