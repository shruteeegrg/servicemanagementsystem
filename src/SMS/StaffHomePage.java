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

public class StaffHomePage extends Application {

    private Staff staff;
    private Worker worker;

    public StaffHomePage(Staff staff) {
        this.staff = staff;
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
        GridPane pane1 = new GridPane();

        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20));
        pane.setVgap(10);
        pane.setHgap(10);
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

       Label lblWelcome = new Label("Welcome, " + staff.getFirstName() + "!", logoImageView);
       lblWelcome.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: white;");
       Label lblStaffId = new Label("Your Staff ID: " + staff.getStaff_id());
       lblStaffId.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: white;");
     
       

        
      
        Button btnService = new Button("");
        addIconAndText(btnService, "/icons/icons8-multitasking-64.png", "Manage Services");
        btnService.setOnAction((event) -> {
        	openServicePage(primaryStage);
        });
        beautifyButton(btnService);
        
        Button btnWorker = new Button("");
        addIconAndText(btnWorker, "/icons/icons8-staff-48.png", "Manage Staff Workers");
        beautifyButton(btnWorker);
        btnWorker.setOnAction( e -> {
        	openWorkerPage(primaryStage);
        });

        Button btnRequestQuote = new Button("");
        addIconAndText(btnRequestQuote, "/icons/icons8-quote-50.png", "Review Quotes -> Book Appointment");
        beautifyButton(btnRequestQuote);
        btnRequestQuote.setOnAction(event -> {
        	openQuoteStaff(primaryStage);
        });
        
        Button btnManageAccount = new Button("");
        addIconAndText(btnManageAccount, "/icons/icons8-customer-support-50.png", "Manage Customer's Account");
        beautifyButton(btnManageAccount);
        btnManageAccount.setOnAction(event ->{
        	openCustomerCRUD(primaryStage);
        });
        
        
        Button btnAppointment = new Button("");
        addIconAndText(btnAppointment, "/icons/icons8-appointment-scheduling-50.png", "Customer's Appointment Schedule");
        beautifyButton(btnAppointment);
        btnAppointment.setOnAction(event -> {
        	openAppointment(primaryStage);
        });
        
        Button btnReport = new Button("");
        addIconAndText(btnReport, "/icons/icons8-revenue-68.png", "Generate Service Revenue");
        Button btnReport1 = new Button("");
        addIconAndText(btnReport1, "/icons/icons8-staff-64.png", "Worker Workload Distribution");
        beautifyButton(btnReport);
        btnReport.setOnAction(event -> {
        	openReportRevenue(primaryStage);
        });
        beautifyButton(btnReport1);
        btnReport1.setOnAction(e -> {
     	   openReportWorkload(primaryStage);
        });
        
        Button btnLogout = new Button("");
        addIconAndText(btnLogout, "/icons/icons8-logout-48.png", "Logout");

        
        beautifyButton(btnLogout);
        btnLogout.setOnAction(e -> {
        	Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
			alert1.setTitle("INFORMATION");
			alert1.setHeaderText("Logging out from the system.");
			alert1.showAndWait();
        	openLoginPage(primaryStage);
        });
       

        pane1.add(lblWelcome, 0, 0, 2, 1);
        pane1.add(lblStaffId, 0, 1, 3, 1); 

        GridPane.setConstraints(btnService, 1, 1);
        GridPane.setConstraints(btnWorker, 2, 1);
        
        GridPane.setConstraints(btnAppointment, 2, 2);
        GridPane.setConstraints(btnRequestQuote, 1, 2);

        
        GridPane.setConstraints(btnReport, 1, 3);
        GridPane.setConstraints(btnReport1, 2, 3);
        

        GridPane.setConstraints(btnManageAccount, 1, 4);
        GridPane.setConstraints(btnLogout,2, 4);
        
        
        pane.getChildren().addAll(btnService, btnWorker, btnAppointment,btnRequestQuote, btnReport, btnReport1,btnManageAccount, btnLogout);

        Region spacer = new Region(); // Create a spacer
        spacer.setPrefHeight(15);
        vbox.getChildren().addAll(pane1, spacer, pane);
        vbox.setAlignment(Pos.CENTER);

	    Scene scene = new Scene(vbox, 600, 400);
	   
        primaryStage.setScene(scene);
        Image icon1 = new Image("file:///Users/shrutigurungschool/eclipse-workspace/Assessment/src/icons/icons8-service-48.png");
		primaryStage.getIcons().add(icon1);
        primaryStage.setTitle("Staff Home Page");
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


    public void openServicePage(Stage primaryStage) {
        ServiceCRUD servicePage = new ServiceCRUD(staff);
        try {
            servicePage.start(new Stage());
            primaryStage.close();
        } catch (Exception e) {
            System.out.println("Error opening Service page: " + e.getMessage());
        }

    }

    public void openCustomerCRUD(Stage primaryStage) {
        CustomerCRUD customerPage = new CustomerCRUD(staff); // Create an instance of the InsertPerson page
        try {
            customerPage.start(new Stage());
            primaryStage.close();
        } catch (Exception e) {
            System.out.println("Error opening Customer CRUD page: " + e.getMessage());
        }

    }

    public void openQuoteStaff(Stage primaryStage) {
        QuoteStaff quotePage = new QuoteStaff(staff, worker); // Create an instance of the InsertPerson page
        try {
            quotePage.start(new Stage()); // Start the Login page
            primaryStage.close(); // Close the registration window
        } catch (Exception e) {
            System.out.println("Error opening Quote Staff page: " + e.getMessage());
        }

    }

    public void openAppointment(Stage primaryStage) {
        AppointmentCRUD appointmentPage = new AppointmentCRUD(staff); // Create an instance of the InsertPerson page
        try {
            appointmentPage.start(new Stage());
            primaryStage.close();
        } catch (Exception e) {
            System.out.println("Error opening Appointment page: " + e.getMessage());
        }

    }

    public void openReportWorkload(Stage primaryStage) {
        ReportWorkload reportPage1 = new ReportWorkload(staff);
        try {
            reportPage1.start(new Stage());
            primaryStage.close();
        } catch (Exception e) {
            System.out.println("Error opening Report Revenue page: " + e.getMessage());
        }

    }

    public void openReportRevenue(Stage primaryStage) {
        ReportRevenue reportPage = new ReportRevenue(null, null,staff);
        try {
            reportPage.start(new Stage());
            primaryStage.close();
        } catch (Exception e) {
            System.out.println("Error opening Report Workload page: " + e.getMessage());
        }

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

    public void openWorkerPage(Stage primaryStage) {
        WorkerCRUD workerPage = new WorkerCRUD(staff); // Create an instance of the InsertPerson page
        try {
            workerPage.start(new Stage()); // Start the Login page
            primaryStage.close(); // Close the registration window
        } catch (Exception e) {
            System.out.println("Error opening Login page: " + e.getMessage());
        }
    }

}
