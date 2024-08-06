package SMS;

public class Quote {
	private int quote_id;
	private Customer customer;
	private Service service;
	private String date;
	private String time;
	private String requirement;
	private Double budget;
	private String status;


public Quote() {
	
}
public Quote(int quote_id, Customer customer, Service service, String date, String time, String requirement,
		Double budget, String status) {

	this.quote_id = quote_id;
	this.customer = customer;
	this.service = service;
	this.date = date;
	this.time = time;
	this.requirement = requirement;
	this.budget = budget;
	this.status = status;
}
public int getQuote_id() {
	return quote_id;
}
public void setQuote_id(int quote_id) {
	this.quote_id = quote_id;
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
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
public String getRequirement() {
	return requirement;
}
public void setRequirement(String requirement) {
	this.requirement = requirement;
}
public Double getBudget() {
	return budget;
}
public void setBudget(Double budget) {
	this.budget = budget;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
@Override
public String toString() {
	return "Quote [quote_id=" + quote_id + ", customer=" + customer + ", service=" + service + ", date=" + date
			+ ", time=" + time + ", requirement=" + requirement + ", budget=" + budget + ", status=" + status + "]";
}


}
