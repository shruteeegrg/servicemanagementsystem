package SMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StaffRegister extends Application {

	public static void main (String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		String backgroundImage = "url('file:///Users/shrutigurungschool/eclipse-workspace/Assessment/image/remote-team.png')";
	    VBox vbox = new VBox();
	    vbox.setStyle("-fx-background-image: " + backgroundImage + "; " +
	            "-fx-background-position: right center; " +
	            "-fx-background-repeat: no-repeat; " +
	            "-fx-padding: 50px;");
	    
		Label lblStaffType = new Label("Staff Type: ");
		lblStaffType.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
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
		
		Label lblLoginUsername = new Label("Login Username: ");
		lblLoginUsername.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblLoginPassword = new Label("Login Password : ");
		lblLoginPassword.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblRegister = new Label("Register:");
		lblRegister.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		Label lblLogout = new Label("Logout: ");
		lblLogout.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");
		
		TextField txtStaffType = new TextField();
		txtStaffType.setText("Staff Admin");
		txtStaffType.setEditable(false);
		
		TextField txtFirstName = new TextField();
		TextField txtLastName = new TextField();
		TextField txtRole = new TextField();
		TextField txtPhone = new TextField();
		TextField txtEmail = new TextField();
		DatePicker datePickerEmployed = new DatePicker();
		TextField txtSalary = new TextField();
		
		TextField txtLoginUsername = new TextField();
		PasswordField txtLoginPassword = new PasswordField();
		
		Image registerIcon = new Image(getClass().getResourceAsStream("/icons/icons8-register-48-3.png"));
        ImageView registerView = new ImageView(registerIcon);
        Button btnRegister = new Button("Register",registerView );
        btnRegister.setStyle("-fx-background-color: white; -fx-text-fill: #18193F;");
        registerView.setFitWidth(31); 
        registerView.setFitHeight(31);
		
		
		btnRegister.setOnAction((event) -> {
		    String firstName = txtFirstName.getText();
		    String lastName = txtLastName.getText();
		    String role = txtRole.getText();
		    String phone = txtPhone.getText();
		    String email = txtEmail.getText();
		    String employed = datePickerEmployed.getValue().toString();
		    String salaryText = txtSalary.getText();
		    String username = txtLoginUsername.getText();
		    String password = txtLoginPassword.getText();

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
		        return;
		    }

		    // Create Staff object
		    Staff staff = new Staff(0, firstName, lastName, role, phone, email, employed, Double.parseDouble(salaryText), username, password);

		    // Save staff record
		    boolean result = saveStaffRecord(staff);
		    if (result) {
		        showAlert("Record Saved Successfully");
		        txtFirstName.clear();
			    txtLastName.clear();
			    txtRole.clear();
			    txtPhone.clear();
			    txtEmail.clear();
			    datePickerEmployed.setValue(null);
			    txtSalary.clear();
			    txtLoginUsername.clear();
			    txtLoginPassword.clear();
		    } else {
		        showAlert("Failed to save record");
		    }
		});

		Image logoutIcon = new Image(getClass().getResourceAsStream("/icons/icons8-logout-64.png"));
        ImageView logoutView = new ImageView(logoutIcon);
        Button btnLogout = new Button("Logout", logoutView);
        btnLogout.setStyle("-fx-background-color: white; -fx-text-fill: #18193F;");
        logoutView.setFitWidth(31); 
        logoutView.setFitHeight(31);
        btnLogout.setOnAction(e -> {
        	openRegister(primaryStage);
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
        Label loginHeader = new Label("STAFF ADMIN REGISTRATION", new ImageView("icons/icons8-service-48.png"));
	    loginHeader.setStyle("-fx-text-fill: white; -fx-font-size: 30px; -fx-font-weight: bold;  -fx-font-family: 'Helvetica';"); // Style the header label

	    pane.add(loginHeader, 0, 0, 2, 1); // Add the header label spanning two columns

		pane.add(lblStaffType, 0, 1);
		pane.add(txtStaffType, 1, 1);
		pane.add(lblFirstName, 0, 2);
		pane.add(txtFirstName, 1, 2);
		pane.add(lblLastName, 0, 3);
		pane.add(txtLastName, 1, 3);
		pane.add(lblRole, 0, 4);
		pane.add(txtRole, 1, 4);		
		pane.add(lblPhone, 0, 5);
		pane.add(txtPhone, 1, 5);
		pane.add(lblEmail, 0, 6);
		pane.add(txtEmail, 1, 6);
		pane.add(lblEmployed, 0, 7);
		pane.add(datePickerEmployed, 1, 7);
		pane.add(lblSalary, 0, 8);
		pane.add(txtSalary, 1, 8);
		pane.add(lblLoginUsername, 0, 9);
		pane.add(txtLoginUsername, 1, 9);
		pane.add(lblLoginPassword, 0, 10);
		pane.add(txtLoginPassword, 1, 10);
		
		pane.add(lblRegister, 0, 12);
		pane.add(btnRegister, 1, 12);
		
		pane.add(lblLogout, 0, 13);
		pane.add(btnLogout, 1, 13);
		
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
	// Method to show alert dialog
				private void showAlert(String message){
				    Alert alert = new Alert(Alert.AlertType.ERROR);
				    alert.setTitle("Error");
				    alert.setHeaderText(message);
				    alert.showAndWait();
				}

			

	
	private boolean saveStaffRecord(Staff staff) {
        boolean result = false;
        String DRIVER = "com.mysql.cj.jdbc.Driver";
        String HOST = "localhost";
        int PORT = 3306;
        String DATABASE ="SMS";
		String DBUSER="root";
		String DBPASS="Mynameisshrutigurung12c!";
		String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
        String sql = "INSERT INTO Staff (firstName, lastName, role, phone, employedSince, salary, user_name, pass_word) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
       
        
        try (
        	
        	Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);
            PreparedStatement pstat = conn.prepareStatement(sql)) {
            
            pstat.setString(1, staff.getFirstName());
            pstat.setString(2, staff.getLastName());
            pstat.setString(3, staff.getRole());
            pstat.setString(4, staff.getPhone());
            pstat.setString(5, staff.getEmployedSince());
            pstat.setDouble(6, staff.getSalary());
            pstat.setString(7, staff.getUser_name());
            pstat.setString(8, staff.getPass_word());
           pstat.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return result;
    }
	
	private void openRegister(Stage primaryStage) {

	    Register registerPage = new Register(); // Create an instance of the InsertPerson page
	    try {
	        registerPage.start(new Stage()); // Start the InsertPerson page
	        primaryStage.close(); // Close the login window
	    } catch (Exception e) {
	    	Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login");
            alert.setHeaderText(null);
            alert.setContentText("Error opeing Registration Home Page :" + e.getMessage());
            alert.showAndWait();
	    }
	}


	}