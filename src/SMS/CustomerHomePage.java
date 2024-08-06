package SMS;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerHomePage extends Application {

    private Customer customer;

    public CustomerHomePage(Customer customer) {
        this.customer = customer; // TO access the data of the object
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	VBox vbox = new VBox();
        String backgroundImage = "url('file:///Users/shrutigurungschool/eclipse-workspace/Assessment/image/task-2.png')";
	    vbox.setStyle("-fx-background-image: " + backgroundImage + "; " +
	            "-fx-background-position: right center; " +
	            "-fx-background-repeat: no-repeat; " +
	            "-fx-padding: 50px;");

        GridPane pane = new GridPane();
       
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20));
        pane.setVgap(10);
        pane.setHgap(10);
        
        GridPane pane1 = new GridPane();
        pane1.setStyle("-fx-font-size: 16px;"
        		+"-fx-background-color: rgba(120, 163, 212, 0.8); "
        		+ "-fx-text-fill: #FFFFFF; " +
                    "-fx-background-radius: 10px; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");
            
        pane1.setPadding(new Insets(15)); // Set margin around pane1

        Image logoImage = new Image(getClass().getResourceAsStream("/icons/icons8-service-48.png"));
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(50);
        logoImageView.setFitHeight(50);// Set the width of the logo image
        logoImageView.setPreserveRatio(true);

        Label lblWelcome = new Label("Welcome, " + customer.getFirstName() + "!", logoImageView);
        lblWelcome.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: white;");
        Label lblCustomerId = new Label("Your Customer ID: " + customer.getCustomer_id());
        lblCustomerId.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: white;");
      
        Button btnService = new Button();
        addIconAndText(btnService, "/icons/icons8-multitasking-64.png", "View Services and Request for Quote");
        btnService.setOnAction(event -> {
        	openViewService(primaryStage, customer);
        });
        beautifyButton(btnService);
        
     
        Button btnViewRequestedQuote = new Button("");
        addIconAndText(btnViewRequestedQuote, "/icons/icons8-history-50.png", "View Requested Quote");
        btnViewRequestedQuote.setOnAction((event) ->{
        	openQuoteView(primaryStage, customer);
        });
        beautifyButton(btnViewRequestedQuote);
        
        Button btnManageAccount = new Button("");
        addIconAndText(btnManageAccount, "/icons/icons8-edit-account-50.png", "Edit Account");
        btnManageAccount.setOnAction((event) ->{
        	openCustomerUpdate(primaryStage, customer);
        });
        beautifyButton(btnManageAccount);
      
        Button btnAppointment = new Button("");
        addIconAndText(btnAppointment, "/icons/icons8-appointment-scheduling-50.png", "Reschedule Appointment");
        btnAppointment.setOnAction( e-> {
        	openAppointmentReschedule(primaryStage, customer);
        });
        beautifyButton(btnAppointment);
        
     
        Button btnServiceRequestHistory = new Button ("");
        addIconAndText(btnServiceRequestHistory, "/icons/icons8-history-50-2.png", "Service Request History");
        btnServiceRequestHistory.setOnAction(e -> {
        	openServiceRequestHistory(primaryStage, customer);
        });
        
        beautifyButton(btnServiceRequestHistory);

        Button btnLogout = new Button("");
        addIconAndText(btnLogout, "/icons/icons8-logout-48.png", "Logout");
        btnLogout.setOnAction(e -> {
        	Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
			alert1.setTitle("INFORMATION");
			alert1.setHeaderText("Logging out from the system.");
			alert1.showAndWait();
        	openLoginPage(primaryStage);
        });
        beautifyButton(btnLogout);
        
        pane1.add(lblWelcome, 0, 0, 2, 1);
        pane1.add(lblCustomerId, 0, 1, 4, 1); 
        
        GridPane.setConstraints(btnService, 1,1);
        GridPane.setConstraints(btnAppointment, 2, 1);
       
  
        GridPane.setConstraints(btnViewRequestedQuote, 1, 2);
        GridPane.setConstraints(btnServiceRequestHistory, 2, 2);
        
        GridPane.setConstraints(btnManageAccount, 1, 3);
        GridPane.setConstraints(btnLogout, 2, 3);
        
       
        
        pane.getChildren().addAll(btnService,btnAppointment, btnViewRequestedQuote,btnServiceRequestHistory, btnManageAccount, btnLogout);

        Region spacer = new Region(); // Create a spacer
        spacer.setPrefHeight(15);
        vbox.getChildren().addAll(pane1, spacer, pane);
        vbox.setAlignment(Pos.CENTER);

	    Scene scene = new Scene(vbox, 600, 400);
	   
        primaryStage.setScene(scene);
        Image icon1 = new Image("file:///Users/shrutigurungschool/eclipse-workspace/Assessment/src/icons/icons8-service-48.png");
		primaryStage.getIcons().add(icon1);
        primaryStage.setTitle("Customer Home Page");
        primaryStage.setWidth(1200);
        primaryStage.setHeight(900);
        primaryStage.setResizable(false);
        primaryStage.show();

    }
    
    private void addIconAndText(Button button, String iconPath, String buttonText) {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(iconPath)));
        icon.setFitWidth(50); 
        icon.setFitHeight(50);
        Label label = new Label(buttonText);
        label.setStyle( "-fx-text-fill: white; ");
        VBox vbox = new VBox(5); // Spacing between icon and text
        vbox.getChildren().addAll(icon, label);
        vbox.setAlignment(Pos.CENTER);

        button.setGraphic(vbox);
    }
    private void beautifyButton(Button button) {
    	button.setStyle("-fx-background-color: rgba(120, 163, 212, 0.95); " +
                "-fx-background-radius: 20px; " + // Set background radius to match border radius
                "-fx-border-color: #BDBDBD; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 20px; " +
                "-fx-padding: 15px 25px; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 16px; " +
                "-fx-min-width: 300px; " +
                "-fx-min-height: 100px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");
    }
    
    public void openAppointmentReschedule(Stage primaryStage, Customer customer) {
    	AppointmentReschedule2 appointmentReschedule = new AppointmentReschedule2(customer);
        try {
        	appointmentReschedule.start(new Stage()); 
            primaryStage.close(); 
        } catch (Exception e) {
            System.out.println("Error opening Appointment Reschedule page: " + e.getMessage());
        }
    }
    
    public void openViewService(Stage primaryStage, Customer customer) {
        ViewService servicePage = new ViewService(customer);
        try {
            servicePage.start(new Stage()); 
            primaryStage.close(); 
        } catch (Exception e) {
            System.out.println("Error opening ViewService page: " + e.getMessage());
        }
    }
    
    public void openQuoteView(Stage primaryStage, Customer customer) {
        QuoteView quotePage = new QuoteView(customer);
        try {
        	quotePage.start(new Stage()); 
            primaryStage.close(); 
        } catch (Exception e) {
            System.out.println("Error opening Quote View page: " + e.getMessage());
        }
    }
    
    
    public void openCustomerUpdate(Stage primaryStage, Customer customer) {
        CustomerUpdate customerPage = new CustomerUpdate(customer);
        try {
        	customerPage.start(new Stage()); 
            primaryStage.close(); 
        } catch (Exception e) {
            System.out.println("Error opening Customer Profile page: " + e.getMessage());
        }
    }
    
    public void openServiceRequestHistory(Stage primaryStage, Customer customer) {
        ServiceRequestHistory historyPage = new ServiceRequestHistory(customer);
        try {
        	historyPage.start(new Stage()); 
            primaryStage.close(); 
        } catch (Exception e) {
            System.out.println("Error opening Service Request History page: " + e.getMessage());
        }
    }
    
    public void openLoginPage(Stage primaryStage) {
	    Login loginPage = new Login(); 
	    try {
	    	loginPage.start(new Stage());
	        primaryStage.close(); 
	    } catch (Exception e) {
	        System.out.println("Error opening Login page: " + e.getMessage());
	    }
}
    
}
