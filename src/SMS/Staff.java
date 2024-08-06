package SMS;

public class Staff {
	private int staff_id;
	private String firstName;
	private String lastName;
	private String role;
	private String phone;
	private String email;
	private String employedSince;
	private double salary;
	private String user_name;
	private String pass_word;

public Staff() {
	
}

public Staff(int staff_id, String firstName, String lastName, String role, String phone, String email,
		String employedSince, double salary, String user_name, String pass_word) {
	
	this.staff_id = staff_id;
	this.firstName = firstName;
	this.lastName = lastName;
	this.role = role;
	this.phone = phone;
	this.email = email;
	this.employedSince = employedSince;
	this.salary = salary;
	this.user_name = user_name;
	this.pass_word = pass_word;
}

public int getStaff_id() {
	return staff_id;
}

public void setStaff_id(int staff_id) {
	this.staff_id = staff_id;
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
public String getUser_name() {
	return user_name;
}
public void setUser_name(String user_name) {
	this.user_name = user_name;
}
public String getPass_word() {
	return pass_word;
}
public void setPass_word(String pass_word) {
	this.pass_word = pass_word;
}

@Override
public String toString() {
	return "Staff [staff_id=" + staff_id + ", firstName=" + firstName + ", lastName="
			+ lastName + ", role=" + role + ", phone=" + phone + ", email=" + email + ", employedSince=" + employedSince
			+ ", salary=" + salary + ", user_name=" + user_name + ", pass_word=" + pass_word + "]";
}



}
