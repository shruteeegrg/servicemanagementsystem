package SMS;

public class Service {
	private	int service_id;
	private String serviceName;
	private String serviceCategory;
	private String serviceDescription;
	private String estimatedDuration;
	private Double charge;
private String serviceImage;

public Service(int service_id, String serviceName, String serviceCategory, String serviceDescription, String estimatedDuration,
		Double charge, String serviceImage) {

	this.service_id = service_id;
	this.serviceName = serviceName;
	this.serviceCategory = serviceCategory;
	this.serviceDescription = serviceDescription;
	this.estimatedDuration = estimatedDuration;
	this.charge = charge;
	this.serviceImage = serviceImage;
}

public String getServiceImage() {
	return serviceImage;
}

public void setServiceImage(String serviceImage) {
	this.serviceImage = serviceImage;
}

public int getService_id() {
	return service_id;
}

public void setService_id(int service_id) {
	this.service_id = service_id;
}

public String getServiceName() {
	return serviceName;
}
public void setServiceName(String serviceName) {
	this.serviceName = serviceName;
}
public String getServiceCategory() {
	return serviceCategory;
}
public void setServiceCategory(String serviceCategory) {
	this.serviceCategory = serviceCategory;
}
public String getServiceDescription() {
	return serviceDescription;
}
public void setServiceDescription(String serviceDescription) {
	this.serviceDescription = serviceDescription;
}
public String getEstimatedDuration() {
	return estimatedDuration;
}
public void setEstimatedDuration(String estimatedDuration) {
	this.estimatedDuration = estimatedDuration;
}
public Double getCharge() {
	return charge;
}
public void setCharge(Double charge) {
	this.charge = charge;
}

public Service() {
	
}

@Override
public String toString() {
	return "Service [service_id=" + service_id + ", serviceName=" + serviceName + ", serviceCategory=" + serviceCategory
			+ ", serviceDescription=" + serviceDescription + ", estimatedDuration=" + estimatedDuration + ", charge="
			+ charge + ", serviceImage=" + serviceImage + "]";
}





}
