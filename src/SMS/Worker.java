package SMS;

public class Worker {

	private int worker_id;
	private String firstName;
	private String lastName;
	private String role;
	private String phone;
	private String email;
	private String employedSince;
	private double salary;
	private Staff staff;
	
	public int getWorker_id() {
		return worker_id;
	}
	public void setWorker_id(int worker_id) {
		this.worker_id = worker_id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmployedSince() {
		return employedSince;
	}
	public void setEmployedSince(String employedSince) {
		this.employedSince = employedSince;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	@Override
	public String toString() {
		return "Worker [worker_id=" + worker_id + ", firstName=" + firstName + ", lastName=" + lastName + ", role="
				+ role + ", phone=" + phone + ", email=" + email + ", employedSince=" + employedSince + ", salary="
				+ salary + ", staff=" + staff + "]";
	}
	
	public Worker() {
		
	}
	public Worker(int worker_id, String firstName, String lastName, String role, String phone, String email,
			String employedSince, double salary, Staff staff) {
		
		this.worker_id = worker_id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.phone = phone;
		this.email = email;
		this.employedSince = employedSince;
		this.salary = salary;
		this.staff = staff;
	}
	
	
	
	

}
