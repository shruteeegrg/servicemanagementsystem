package SMS;

public class Appointment {

	private int appointment_id;
	private Customer customer;
	private Service service;
	private Quote quote;
	private Worker worker;
	private String dateOfAppointment;
	private String timeOfAppointment;
	private String description;
	private double totalCost;
	private String status;
	private int rescheduleCount;
	
	
	public Appointment() {
		
	}
	public Appointment(int appointment_id, Customer customer, Service service, Quote quote, Worker worker,
			String dateOfAppointment, String timeOfAppointment, String description, double totalCost, String status,
			int rescheduleCount) {
		
		this.appointment_id = appointment_id;
		this.customer = customer;
		this.service = service;
		this.quote = quote;
		this.worker = worker;
		this.dateOfAppointment = dateOfAppointment;
		this.timeOfAppointment = timeOfAppointment;
		this.description = description;
		this.totalCost = totalCost;
		this.status = status;
		this.rescheduleCount = rescheduleCount;
	}
	public int getAppointment_id() {
		return appointment_id;
	}
	public void setAppointment_id(int appointment_id) {
		this.appointment_id = appointment_id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	public Quote getQuote() {
		return quote;
	}
	public void setQuote(Quote quote) {
		this.quote = quote;
	}
	public Worker getWorker() {
		return worker;
	}
	public void setWorker(Worker worker) {
		this.worker = worker;
	}
	public String getDateOfAppointment() {
		return dateOfAppointment;
	}
	public void setDateOfAppointment(String dateOfAppointment) {
		this.dateOfAppointment = dateOfAppointment;
	}
	public String getTimeOfAppointment() {
		return timeOfAppointment;
	}
	public void setTimeOfAppointment(String timeOfAppointment) {
		this.timeOfAppointment = timeOfAppointment;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getRescheduleCount() {
		return rescheduleCount;
	}
	public void setRescheduleCount(int rescheduleCount) {
		this.rescheduleCount = rescheduleCount;
	}
	@Override
	public String toString() {
		return "Appointment [appointment_id=" + appointment_id + ", customer=" + customer + ", service=" + service
				+ ", quote=" + quote + ", worker=" + worker + ", dateOfAppointment=" + dateOfAppointment
				+ ", timeOfAppointment=" + timeOfAppointment + ", description=" + description + ", totalCost="
				+ totalCost + ", status=" + status + ", rescheduleCount=" + rescheduleCount + "]";
	}
	
}