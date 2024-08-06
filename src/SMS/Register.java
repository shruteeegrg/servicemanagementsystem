package SMS;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Register extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
    	String backgroundImage = "url('file:///Users/shrutigurungschool/eclipse-workspace/Assessment/image/remote-team.png')";
	    VBox vbox = new VBox();//Vertical Box
	    vbox.setStyle("-fx-background-image: " + backgroundImage + "; " +
	            "-fx-background-position: right center; " +
	            "-fx-background-repeat: no-repeat; " +
	            "-fx-padding: 50px;");
        //RadioButton
        Label lblUserType = new Label("Welcome! Select your User Type: ");
        lblUserType.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        
        RadioButton rb1 = new RadioButton("Staff Admin");
        rb1.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        
        RadioButton rb2 = new RadioButton("Customer");
        rb2.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        
        RadioButton rb3 = new RadioButton("Head of Administration");
        rb3.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        ToggleGroup group1 = new ToggleGroup();
        rb1.setToggleGroup(group1);
        rb2.setToggleGroup(group1);
        rb3.setToggleGroup(group1);

       //Setting image icon
        Image continueIcon = new Image(getClass().getResourceAsStream("/icons/icons8-continue-48-5.png"));
        ImageView continueView = new ImageView(continueIcon);
        Button btnContinue = new Button("Continue",continueView);
        continueView.setFitWidth(31); 
        continueView.setFitHeight(31);
        btnContinue.setStyle("-fx-background-color: white; -fx-text-fill: #18193F;");
        btnContinue.setOnAction((event) -> {
            if (rb1.isSelected() == true) {
                openStaffRegister(primaryStage);//method calling
                primaryStage.close();
            } else {
                openCustomerRegister(primaryStage);
                primaryStage.close();
            }
        });

        GridPane pane = new GridPane();
        pane.setVgap(5);
        pane.setHgap(5);
        pane.setStyle("-fx-font-size: 16px; " +
	              "-fx-background-color: rgba(120, 163, 212, 0.8); " +
	              "-fx-text-fill: #FFFFFF; " +
	              "-fx-padding: 20px; " +
	              "-fx-background-radius: 10px; " +  // Corner radii
	              "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);" + // Lighter drop shadow effect
	              "-fx-alignment: center; " +  // Center alignment
	              "-fx-min-width: 300px; " +    // Smaller width
	              "-fx-min-height: 500px;");
      
        Label loginHeader = new Label("REGISTRATION", new ImageView("icons/icons8-service-48.png"));
	    loginHeader.setStyle("-fx-text-fill: white; -fx-font-size: 30px; -fx-font-weight: bold;  -fx-font-family: 'Helvetica';"); // Style the header label
	    pane.add(loginHeader, 0, 0, 2, 1);
	    
        pane.setConstraints(lblUserType, 0, 1);
        pane.setConstraints(rb1, 1, 2);
        pane.setConstraints(rb2, 1, 3);
        pane.setConstraints(rb3, 1, 4);
        pane.setConstraints(btnContinue, 0, 5);
        pane.getChildren().addAll(lblUserType, rb1, rb2,rb3,  btnContinue);
        
        btnContinue.setOnAction((event) -> {
            if (rb1.isSelected()) {
            	
            	openLoginPage(primaryStage);
              
            } else if (rb3.isSelected()) {
            	Label lblHOS = new Label("Enter the administration password to access the system: ");
            	lblHOS.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
            	PasswordField txtHOS = new PasswordField();
            	Button btnSubmit = new Button("Continue with password");
            	btnSubmit.setStyle("-fx-background-color: white; -fx-text-fill: #18193F;");
            	pane.setConstraints(lblHOS, 2, 5);
            	pane.setConstraints(txtHOS, 2, 6);
            	pane.setConstraints(btnSubmit, 2,7);
            	pane.getChildren().addAll(lblHOS, txtHOS, btnSubmit);
            	
            	btnSubmit.setOnAction(e -> {
            		String password = txtHOS.getText();
                    if (password.equals("admin_password")) {
                    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText("Login Successfull!");
                        alert.setContentText("You have successfully logged into the system!");
                        alert.showAndWait();
                        openStaffRegister(primaryStage);
                    } else {
                        System.out.println("The Password is invalid");
                        txtHOS.setText("");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("The Password is invalid");
                        alert.setContentText("Please try again later");
                        alert.showAndWait();
                    }
            	}); 
            }
            else if(rb2.isSelected()) {
            	openCustomerRegister(primaryStage);
            }
         
        });

        vbox.getChildren().add(pane);
	    vbox.setAlignment(Pos.CENTER);

	    Scene scene = new Scene(vbox, 600, 400);
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("Service Management System ");
	    primaryStage.setWidth(1200);
		primaryStage.setHeight(900);
	    primaryStage.setResizable(false);
	    primaryStage.show();
    }

    public void openStaffRegister(Stage primaryStage) {
        StaffRegister staffRegisterPage = new StaffRegister(); // Create an instance of the StaffRegister page
        try {
            staffRegisterPage.start(new Stage()); // Opening Staff Registration Window
            primaryStage.close(); // Close the login window
        } catch (Exception e) {
            System.out.println("Error opening Staff Registration page: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred while opening the staff registration page");
            alert.setContentText("Please try again later");
            alert.showAndWait();
        }
    }

    public void openCustomerRegister(Stage primaryStage) {
        CustomerRegister customerRegisterPage = new CustomerRegister(); // Create an instance of the CustomerRegister page
        try {
            customerRegisterPage.start(new Stage()); // Opening Customer Registration Window
            primaryStage.close(); // Close the login window
        } catch (Exception e) {
            System.out.println("Error opening Customer Registration page: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred while opening the customer registration page");
            alert.setContentText("Please try again later");
            alert.showAndWait();
        }
    }
    public void openLoginPage(Stage primaryStage) {
	    Login loginPage = new Login(); // Create an instance of the InsertPerson page
	    try {
	    	loginPage.start(new Stage()); // Start the Login page
	        primaryStage.close(); // Close the registration window
	    } catch (Exception e) {
	        System.out.println("Error opening Login page: " + e.getMessage());
	        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred while opening the Login page");
            alert.setContentText("Please try again later");
            alert.showAndWait();
	    }
	    
	
	}

}