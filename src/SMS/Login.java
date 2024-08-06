package SMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Login extends Application {
	
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
	    
	    // Set the background color of the HBox
	    Label lblUserType = new Label("SELECT YOUR USER TYPE: ");
	    lblUserType.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");

	    RadioButton rb1 = new RadioButton("Staff Admin");
	    RadioButton rb2 = new RadioButton("Customer");
	    rb1.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
	    rb2.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

	    ToggleGroup group1 = new ToggleGroup();
	    rb1.setToggleGroup(group1);
	    rb2.setToggleGroup(group1);

	    Label lblLoginID = new Label("USERNAME : ");
	    lblLoginID.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");
	    Label lblLoginPassword = new Label("PASSWORD : ");
	    lblLoginPassword.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");

	    TextField txtLoginID = new TextField();
	    PasswordField txtLoginPassword = new PasswordField();

	    Image loginIcon = new Image(getClass().getResourceAsStream("/icons/icons8-login-48-3.png"));
        ImageView loginView = new ImageView(loginIcon);
	    Button btnLogin = new Button( "Login", loginView);
	    loginView.setFitWidth(30); 
	    loginView.setFitHeight(30);
       
      
	    btnLogin.setStyle("-fx-background-color: white; -fx-text-fill: #18193F;");
	    btnLogin.setOnAction((event) -> {
	        String loginName = txtLoginID.getText();
	        String loginPass = txtLoginPassword.getText();
	        boolean isStaffSelected = rb1.isSelected(); // Check if staff radio button is selected
	        Object user = loginUser(loginName, loginPass, isStaffSelected); // Pass the selected radio button state
	        if (user != null) {
	            System.out.println("Valid user");
	            if (isStaffSelected) {
	            	Alert alert = new Alert(AlertType.INFORMATION);
		            alert.setTitle("Login");
		            alert.setHeaderText(null);
		            alert.setContentText("You have sucessfully logged into the system.");
		            alert.showAndWait();
	                openStaffHomePage(primaryStage, (Staff) user);
	            } else {
	            	Alert alert = new Alert(AlertType.INFORMATION);
		            alert.setTitle("Login");
		            alert.setHeaderText(null);
		            alert.setContentText("You have sucessfully logged into the system.");
		            alert.showAndWait();
	                openCustomerHomePage(primaryStage, (Customer) user);
	            }
	        } else {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Login");
	            alert.setHeaderText(null);
	            alert.setContentText("Invalid user");
	            alert.showAndWait();
	            txtLoginID.setText("");
	            txtLoginPassword.setText("");
	            
	        }
	    });

	    Hyperlink lblRegister = new Hyperlink("Have not registered yet? Register Now ");
	    lblRegister.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
	    lblRegister.setOnAction(event -> {
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
	              "-fx-min-height: 500px;");    // Longer height

	
	    Label loginHeader = new Label("LOG IN", new ImageView("icons/icons8-service-48.png"));
	    loginHeader.setStyle("-fx-text-fill: white; -fx-font-size: 30px; -fx-font-weight: bold;"); // Style the header label

	    pane.add(loginHeader, 0, 0, 2, 1); // Add the header label spanning two columns

	    pane.add(lblUserType, 0, 1); // Adjust the row position of the user type label
	    pane.add(rb1, 1, 2); // Adjust the row position of the radio buttons
	    pane.add(rb2, 1, 3); // Adjust the row position of the radio buttons
	    pane.add(lblLoginID, 0, 4); // Adjust the row position of the login ID label
	    pane.add(txtLoginID, 1, 4); // Adjust the row position of the text field for login ID
	    pane.add(lblLoginPassword, 0, 5); // Adjust the row position of the login password label
	    pane.add(txtLoginPassword, 1, 5); // Adjust the row position of the text field for login password
	    pane.add(btnLogin, 1, 6); // Adjust the row position of the close button
	    pane.add(lblRegister, 0, 7); // Adjust the row position of the registration label
	    

	    vbox.getChildren().add(pane);
	    vbox.setAlignment(Pos.CENTER);

	    Scene scene = new Scene(vbox, 600, 400);
	    primaryStage.setScene(scene);
	    Image icon1 = new Image("file:///Users/shrutigurungschool/eclipse-workspace/Assessment/src/icons/icons8-service-48.png");
	    primaryStage.getIcons().add(icon1);
	    primaryStage.setTitle("Service Management System");
	    primaryStage.setWidth(1200);
		primaryStage.setHeight(900);
	    primaryStage.setResizable(false);
	    primaryStage.show();
	}

	public Object loginUser(String user, String password, boolean isStaffSelected) {
	    //pid, fullName, address, gender, ageGroup, reading, playing, other, login_id, pass_word
	    Object loggedInUser = null;
	    
	    String DRIVER = "com.mysql.cj.jdbc.Driver";
	    String HOST = "localhost";
	    int PORT = 3306;
	    String DATABASE = "SMS";
	    String DBUSER = "root";
	    String DBPASS = "Mynameisshrutigurung12c!";
	    String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
	    String sql = "";

	    if (isStaffSelected) {
	        sql = "SELECT * FROM Staff WHERE user_name=? AND pass_word=?";
	    } else {
	        sql = "SELECT * FROM Customer WHERE user_name=? AND pass_word=?";
	    }
	    System.out.println(sql);
	    try {
	        Class.forName(DRIVER); //loading driver
	        Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
	        PreparedStatement pstat = conn.prepareStatement(sql);
	        pstat.setString(1, user);
	        pstat.setString(2, password);
	        ResultSet rs = pstat.executeQuery();//Insert Record
	        while (rs.next()) {
	            if (isStaffSelected) {
	                loggedInUser = new Staff(rs.getInt("staff_id"),
	                       	rs.getString("firstName"),
	                        rs.getString("lastName"),
	                        rs.getString("role"),
	                        rs.getString("phone"),
	                        rs.getString("email"),
	                        rs.getString("employedSince"),
	                        rs.getDouble("salary"),
	                        rs.getString("user_name"),
	                        rs.getString("pass_word")
	                );
	            } else {
	                loggedInUser = new Customer(rs.getInt("customer_id"),
	                        rs.getString("firstName"),
	                        rs.getString("lastName"),
	                        null, // CustomerAddress class
	                        rs.getString("phone"),
	                        rs.getString("email"),
	                        rs.getString("user_name"),
	                        rs.getString("pass_word")
	                );
	            }
	        }
	        pstat.close();
	        conn.close();
	    } catch (Exception ex) {
	    	Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login");
            alert.setHeaderText(null);
            alert.setContentText("Error :" + ex.getMessage());
            alert.showAndWait();
            
	
	    }
	    return loggedInUser;
	}

	public void openStaffHomePage(Stage primaryStage, Staff staff) {
	    StaffHomePage staffHomePage = new StaffHomePage(staff); // Create an instance of the InsertPerson page
	    try {
	        staffHomePage.start(new Stage()); // Start the InsertPerson page
	        primaryStage.close(); // Close the login window
	    } catch (Exception e) {
	    	
	    	Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login");
            alert.setHeaderText(null);
            alert.setContentText("Error opeing Staff Home Page :" + e.getMessage());
            alert.showAndWait();
	    }
	}

	public void openCustomerHomePage(Stage primaryStage, Customer customer) {

	    CustomerHomePage customerHomePage = new CustomerHomePage(customer); // Create an instance of the InsertPerson page
	    try {
	        customerHomePage.start(new Stage()); // Start the InsertPerson page
	        primaryStage.close(); // Close the login window
	    } catch (Exception e) {
	    	Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login");
            alert.setHeaderText(null);
            alert.setContentText("Error opeing Customer Home Page :" + e.getMessage());
            alert.showAndWait();
	    }
	}

	public void openRegister(Stage primaryStage) {

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

