package SMS;

public class CustomerAddress {
	private int address_id;
	private String addressStreet;
	private String addressCity;
	private String addressZipCode;
	
public CustomerAddress(int address_id, String addressStreet, String addressCity, String addressZipCode) {

	this.address_id = address_id;
	this.addressStreet = addressStreet;
	this.addressCity = addressCity;
	this.addressZipCode = addressZipCode;
}
public int getAddress_id() {
	return address_id;
}
public void setAddress_id(int address_id) {
	this.address_id = address_id;
}
public String getAddressStreet() {
	return addressStreet;
}
public void setAddressStreet(String addressStreet) {
	this.addressStreet = addressStreet;
}
public String getAddressCity() {
	return addressCity;
}
public void setAddressCity(String addressCity) {
	this.addressCity = addressCity;
}
public String getAddressZipCode() {
	return addressZipCode;
}
@Override
public String toString() {
	return "CustomerAddress [address_id=" + address_id + ", addressStreet=" + addressStreet + ", addressCity="
			+ addressCity + ", addressZipCode=" + addressZipCode + "]";
}
public void setAddressZipCode(String addressZipCode) {
	this.addressZipCode = addressZipCode;
}


}
