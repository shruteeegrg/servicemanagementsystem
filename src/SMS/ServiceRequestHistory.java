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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ServiceRequestHistory extends Application {
	private Customer customer;
	
	public ServiceRequestHistory() {
	    // Default constructor
	}

    public ServiceRequestHistory(Customer customer) {
        this.customer = customer;
        
    }
    
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		if (customer == null) {
            // If customer is null, create a new Customer instance
            customer = new Customer(0, null, null, null, null, null, null, null);
      
		}

        Label lblWelcome = new Label("Welcome, " + customer.getFirstName() + "!");
        Label lblCustomerId = new Label("Your Customer ID: " + customer.getCustomer_id());
        
        TableView<Appointment> table1 = new TableView<>();
		table1.setPrefWidth(1200);
		table1.setPrefHeight(900);
		
		TableColumn<Appointment, Integer> col1 = new TableColumn<>("Appointment ID");
		col1.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
		col1.setMinWidth(100);
		
		TableColumn<Appointment, Integer> col2 = new TableColumn<>("Customer ID");
		col2.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCustomer().getCustomer_id()).asObject());
		col2.setMinWidth(75);
		
		TableColumn<Appointment, Integer> col3 = new TableColumn<>("Service ID");
		col3.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getService().getService_id()).asObject());
		col3.setMinWidth(75);
		
		TableColumn<Appointment, String> col4 = new TableColumn<>("Service Name");
		col4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getService().getServiceName()));
		col4.setMinWidth(150);
		
		TableColumn<Appointment, Integer> col5 = new TableColumn<>("Quote ID");
		col5.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuote().getQuote_id()).asObject());
		col5.setMinWidth(75);
		
		TableColumn<Appointment, Integer> col6 = new TableColumn<>("Staff ID");
		col6.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getWorker().getWorker_id()).asObject());
		col6.setMinWidth(75);
		
		TableColumn<Appointment, String> col7 = new TableColumn<>("Date");	
		col7.setCellValueFactory(new PropertyValueFactory<>("dateOfAppointment"));
		col7.setMinWidth(100);
		
		TableColumn<Appointment, String> col8 = new TableColumn<>("Time");
		col8.setCellValueFactory(new PropertyValueFactory<>("timeOfAppointment"));
		col8.setMinWidth(150);
		
		TableColumn<Appointment, String> col9 = new TableColumn<>("Description");
		col9.setCellValueFactory(new PropertyValueFactory<>("description"));
		col9.setMinWidth(198);
		
		TableColumn<Appointment, String> col10 = new TableColumn<>("Total Cost");
		col10.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
		col10.setMinWidth(75);
		
		TableColumn<Appointment, String> col11 = new TableColumn<>("Status");
		col11.setCellValueFactory(new PropertyValueFactory<>("status"));
		col11.setMinWidth(100);
		

		table1.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10, col11);

		//set data
		ArrayList<Appointment> appointments = allRecords();
			
		//set persons to tabl1
		for(Appointment appointment: appointments) {
		table1.getItems().add(appointment);
		}
				
		Button btnClose = new Button("Close");
		btnClose.setOnAction((event) -> {
		primaryStage.close();
		});
		
        Button backButton = new Button("Home");
        backButton.setOnAction(event -> {
            CustomerHomePage customerHomePage = new CustomerHomePage(customer);
            try {
                customerHomePage.start(primaryStage);
            } catch (Exception e) {
                System.out.println("Error opening CustomerHomePage: " + e.getMessage());
            }
        });
		
       	
		VBox vboxpane = new VBox(); 
		vboxpane.getChildren().add(lblWelcome);
        vboxpane.getChildren().add(lblCustomerId);
		vboxpane.getChildren().add(table1);
		vboxpane.getChildren().add(backButton);
		vboxpane.getChildren().add(btnClose);
		Scene scene = new Scene(vboxpane);
		primaryStage.setScene(scene);
		Image icon1 = new Image("file:///Users/shrutigurungschool/eclipse-workspace/Assessment/src/icons/icons8-service-48.png");
		primaryStage.getIcons().add(icon1);
		primaryStage.setTitle("Service Request History");
		primaryStage.setWidth(1200);
		primaryStage.setHeight(900);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	

	public ArrayList allRecords() {
	
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		String DRIVER ="com.mysql.cj.jdbc.Driver";
		String HOST ="localhost";
		int PORT=3306;
		String DATABASE ="SMS";
		String DBUSER="root";
		String DBPASS="Mynameisshrutigurung12c!";
		String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
		String sql = "SELECT a.appointment_id, c.customer_id, s.service_id, s.serviceName, q.quote_id, w.worker_id, a.dateOfAppointment, a.timeOfAppointment, a.description, a.totalCost, a.status " +
	             "FROM Appointment a, Customer c, Quote q, Service s, Worker w " +
	             "WHERE a.customer_id = c.customer_id AND a.service_id = s.service_id AND a.quote_id = q.quote_id AND a.worker_id = w.worker_id " +
	             "AND c.customer_id = ? AND a.status = 'Completed' OR a.status= 'Canceled';";


		try {
			Class.forName(DRIVER); //loading driver
			Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
			PreparedStatement pstat = conn.prepareStatement(sql);	
			pstat.setInt(1, customer.getCustomer_id());
			ResultSet rs = pstat.executeQuery();
			while(rs.next()) {
				int appointment_id = rs.getInt("appointment_id");
				int customer_id = rs.getInt("customer_id");
	        	int service_id = rs.getInt("service_id");
	        	String serviceName = rs.getString("serviceName");
	        	int quote_id = rs.getInt("quote_id");
	        	int worker_id = rs.getInt("worker_id");
	            String dateOfAppointment = rs.getString("dateOfAppointment");
	            String timeOfAppointment = rs.getString("timeOfAppointment");
	            String description=rs.getString("description");
	            Double totalCost= rs.getDouble("totalCost");
	            String status=rs.getString("status");
	            int rescheduleCount = 0;
				Customer customer = new Customer(customer_id, null, null, null, null, null, null, null);
				Service service = new Service(service_id, serviceName, null, null, null, null, null);
				Quote quote=new Quote(quote_id, customer, service, null,null,null,null, null);
				Worker worker = new Worker(worker_id, null, null, null, null, null, null, 0.0, null);
				
				Appointment appointment = new Appointment(appointment_id,customer, service, quote, worker, dateOfAppointment, timeOfAppointment, description,totalCost,status, rescheduleCount);
				appointments.add(appointment);
			}
			rs.close();
			pstat.close();
			conn.close();			
		}
		catch(Exception ex) {
			System.out.println("Error : "+ex.getMessage());
		}
		return appointments;
	}
	
	 public boolean updateRecord(Appointment appointment) {
		    //pid, fullName, address, gender, ageGroup, reading, playing, other, login_id, pass_word
		    boolean result = false;
		    String DRIVER ="com.mysql.cj.jdbc.Driver";
		    String HOST ="localhost";
		    int PORT=3306;
		    String DATABASE ="SMS";
		    String DBUSER="root";
		    String DBPASS="Mynameisshrutigurung12c!";
		    String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
		    String sql="UPDATE Appointment SET customer_id=?, service_id=?, quote_id=?, staff_id=?, dateOfAppointment=?, timeOfAppointment =? , description =?, totalCost =? , status=?  WHERE appointment_id=?";
		    try {
		        Class.forName(DRIVER); //loading driver
		        Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
		        PreparedStatement pstat = conn.prepareStatement(sql);            
		        pstat.setInt(1, appointment.getCustomer().getCustomer_id());
		        pstat.setInt(2, appointment.getService().getService_id());
		        pstat.setInt(3, appointment.getQuote().getQuote_id());
		        pstat.setInt(4, appointment.getWorker().getWorker_id());
		        pstat.setString(5, appointment.getDateOfAppointment());
		        pstat.setString(6, appointment.getTimeOfAppointment());
		        pstat.setString(7, appointment.getDescription());
		        pstat.setDouble(8, appointment.getTotalCost());
		        pstat.setString(9, appointment.getStatus());
		        pstat.setInt(10, appointment.getAppointment_id()); // Assuming getServiceId() returns the service ID
		        pstat.executeUpdate();//Update Record
		        pstat.close();
		        conn.close();
		        result=true;
		    }
		    catch(Exception ex) {
		        System.out.println("Error : "+ex.getMessage());
		    }
		    return result;
		}
}
