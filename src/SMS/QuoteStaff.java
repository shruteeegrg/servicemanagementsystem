package SMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TableCell;




public class QuoteStaff extends Application {
	
	private Staff staff;
	private Worker worker;

    public QuoteStaff(Staff staff, Worker worker) {
        this.staff = staff;
        this.worker = worker;
    }
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		Label lblWelcome = new Label("Welcome, " + staff.getFirstName() + "!");
        Label lblStaffId = new Label("Your Staff ID: " + staff.getStaff_id());
        
		TableView<Quote> table1 = new TableView<>();
		table1.setPrefWidth(1200);
		table1.setPrefHeight(900);
		
		TableColumn<Quote, Integer> col1 = new TableColumn<>("Quote ID: ");
		col1.setCellValueFactory(new PropertyValueFactory<>("quote_id"));
		col1.setMinWidth(75);
		
		TableColumn<Quote, Integer> col2 = new TableColumn<>("Customer ID");
		col2.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCustomer().getCustomer_id()).asObject());
		col2.setMinWidth(150);
		
		TableColumn<Quote, Integer> col3 = new TableColumn<>("Service ID");
		col3.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getService().getService_id()).asObject());
		col3.setMinWidth(150);
		
		TableColumn<Quote, String> col4 = new TableColumn<>("Service Name");
		col4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getService().getServiceName()));
		col4.setMinWidth(150);
		
		TableColumn<Quote, String> col5 = new TableColumn<>("Date");
		col5.setCellValueFactory(new PropertyValueFactory<>("date"));
		col5.setMinWidth(150);
		
		TableColumn<Quote, String> col6 = new TableColumn<>("Time");
		col6.setCellValueFactory(new PropertyValueFactory<>("time"));
		col6.setMinWidth(150);
		
		TableColumn<Quote, String> col7 = new TableColumn<>("Requirement");
		col7.setCellValueFactory(new PropertyValueFactory<>("requirement"));
		col7.setMinWidth(150);
		
		TableColumn<Quote, String> col8 = new TableColumn<>("Budget");
		col8.setCellValueFactory(new PropertyValueFactory<>("budget"));
		col8.setMinWidth(150);
		
		ComboBox<String> cmbStatus = new ComboBox<>();
		cmbStatus.getItems().addAll("Pending", "Accepted", "Rejected");
		
		TableColumn<Quote, String> col9 = new TableColumn<>("Status");
		table1.setEditable(true);
		col9.setCellFactory(ComboBoxTableCell.forTableColumn(cmbStatus.getItems()));
		col9.setCellValueFactory(new PropertyValueFactory<>("status"));

		col9.setOnEditCommit(event -> {
		    Quote quote = event.getRowValue();
		   quote.setStatus(event.getNewValue());
		});
		
		col9.setMinWidth(150);
		
		TableColumn<Quote, Void> col10 = new TableColumn<>("Update");
		col10.setCellFactory(column -> {
		    return new TableCell<Quote, Void>() {
		        private final Button btnUpdate = new Button("Confirm");

		        {
		            btnUpdate.setOnAction((event) -> {
		                Quote quote = getTableView().getItems().get(getIndex());
		                boolean res = updateRecord(quote);
		                if (res) {
		                    System.out.println("Record Updated");
		                   Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
		 				   alert1.setTitle("INFORMATION");
		 				   alert1.setHeaderText("Record Updated Successfully");
		 				   alert1.showAndWait();
		                    // Check if the updated status is "Accepted"
		                    if ("Accepted".equals(quote.getStatus())) {
		                        // Open the appointment CRUD page
		                        openAppointmentCRUD(primaryStage, quote, worker);
		                    }
		                } else {
		                    System.out.println("Error: Failed to update record");
		                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
			 				   alert1.setTitle("ERROR");
			 				   alert1.setHeaderText("Error: Failed to update record");
			 				   alert1.showAndWait();
		                }
		            });
		        }

		        @Override
		        protected void updateItem(Void item, boolean empty) {
		            super.updateItem(item, empty);
		            if (empty) {
		                setGraphic(null);
		            } else {
		                setGraphic(btnUpdate);
		            }
		        }
		    };
		});
		col10.setMinWidth(150);
		
		table1.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10);

		//set data
		ArrayList<Quote> quotes = allRecords();
			
		//set persons to tabl1
		for(Quote quote: quotes) {
		table1.getItems().add(quote);
		}
				
		Button btnClose = new Button("Close");
		btnClose.setOnAction((event) -> {
		primaryStage.close();
		});
        
        Button btnHome = new Button("Home");
		btnHome.setOnAction(event -> {
			openStaffHomePage(primaryStage, staff);
		}) ;
		
		//FlowPane pane = new FlowPane();
        //GridPane pane = new GridPane();
       
			
		VBox vboxpane = new VBox(); 
		vboxpane.getChildren().add(lblWelcome);
		vboxpane.getChildren().add(lblStaffId);
		vboxpane.getChildren().add(table1);
		vboxpane.getChildren().add(btnHome);
		vboxpane.getChildren().add(btnClose);
		Scene scene = new Scene(vboxpane);
		primaryStage.setScene(scene);
		Image icon1 = new Image("file:///Users/shrutigurungschool/eclipse-workspace/Assessment/src/icons/icons8-service-48.png");
		primaryStage.getIcons().add(icon1);
		primaryStage.setTitle("Review Requested Quote");
		primaryStage.setWidth(1200);
		primaryStage.setHeight(900);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
		  

	
	
	public ArrayList allRecords() {
	
		ArrayList<Quote> quotes = new ArrayList<Quote>();
		String DRIVER ="com.mysql.cj.jdbc.Driver";
		String HOST ="localhost";
		int PORT=3306;
		String DATABASE ="SMS";
		String DBUSER="root";
		String DBPASS="Mynameisshrutigurung12c!";
		String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
		String sql="  SELECT q.quote_id, c.customer_id, s.service_id, s.serviceName, q.date, q.time, q.requirement, q.budget, q.status"
				+ " FROM Customer c, Quote q, Service s"
				+ " WHERE q.customer_id = c.customer_id AND q.service_id = s.service_id; ";
				
		try {
			Class.forName(DRIVER); //loading driver
			Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
			PreparedStatement pstat = conn.prepareStatement(sql);			
			ResultSet rs = pstat.executeQuery();
			while(rs.next()) {
				int quote_id = rs.getInt("quote_id");
				int customer_id = rs.getInt("customer_id");
				int service_id = rs.getInt("service_id");
				String serviceName = rs.getString("serviceName");
				String date = rs.getString("date");
				String time = rs.getString("time");
				String requirement=rs.getString("requirement");
				Double budget= rs.getDouble("budget");
				String status=rs.getString("status");
				
				Service service = new Service(service_id, serviceName, null, null, null, 0.0, null);
				Customer customer = new Customer(customer_id, null, null, null, null,null, null, null );
				Quote quote =new Quote(quote_id, customer, service, date, time, requirement, budget, status); 
				quotes.add(quote);
			}
			rs.close();
			pstat.close();
			conn.close();			
		}
		catch(Exception ex) {
			System.out.println("Error : "+ex.getMessage());
		}
		return quotes;
	}
	
	public boolean updateRecord(Quote quote) {
        boolean result = false;
        String DRIVER = "com.mysql.cj.jdbc.Driver";
        String HOST = "localhost";
        int PORT = 3306;
        String DATABASE = "SMS";
        String DBUSER = "root";
        String DBPASS = "Mynameisshrutigurung12c!";
        String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
        String sql = "UPDATE Quote SET status=? where quote_id=?";
        try {
            Class.forName(DRIVER); //loading driver
            Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
            PreparedStatement pstat = conn.prepareStatement(sql);
            
            // Set values for the customer table
            pstat.setString(1, quote.getStatus());
            pstat.setInt(2, quote.getQuote_id());
            pstat.executeUpdate(); 
            pstat.close();
            conn.close();
            result = true;
        } catch (Exception ex) {
            System.out.println("Error : " + ex.getMessage());
        }
        return result;
    }
	
	public void openStaffHomePage(Stage primaryStage, Staff staff) {
	    StaffHomePage staffHomePage = new StaffHomePage(staff); 
	    try {
	    	staffHomePage.start(new Stage()); 
	        primaryStage.close();
	    } catch (Exception e) {
	        System.out.println("Error opening Staff Home page: " + e.getMessage());
	    }
	}
	
	public void openAppointmentCRUD(Stage primaryStage, Quote quote, Worker worker) {
		AppointmentBook appointmentPage = new AppointmentBook(quote.getCustomer(), quote.getService(), staff, quote, worker); // Create an instance of the InsertPerson page
	    try {
	    	appointmentPage.start(new Stage()); 
	        primaryStage.close();
	    } catch (Exception e) {
	        System.out.println("Error opening Book Appointment page: " + e.getMessage());
	    }
	    System.out.println("Quote accepted, leading to appointment page: " + quote.getService().getServiceName());
	}

}
