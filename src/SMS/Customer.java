package SMS;

public class Customer {
	private int customer_id;
	private String firstName;
	private String lastName;
	private CustomerAddress address;
	private String phone;
	private String email;
	private String user_name;
	private String pass_word;


public Customer() {
	
}
public Customer(int customer_id, String firstName, String lastName, CustomerAddress address, String phone, String email,
		String user_name, String pass_word) {
	
	this.customer_id = customer_id;
	this.firstName = firstName;
	this.lastName = lastName;
	this.address = address;
	this.phone = phone;
	this.email = email;
	this.user_name = user_name;
	this.pass_word = pass_word;
}
public int getCustomer_id() {
	return customer_id;
}
public void setCustomer_id(int customer_id) {
	this.customer_id = customer_id;
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
public CustomerAddress getAddress() {
	return address;
}
public void setAddress(CustomerAddress address) {
	this.address = address;
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
	return "Customer [customer_id=" + customer_id + ", firstName=" + firstName + ", lastName=" + lastName + ", address="
			+ address + ", phone=" + phone + ", email=" + email + ", user_name=" + user_name + ", pass_word="
			+ pass_word + "]";
}



}
