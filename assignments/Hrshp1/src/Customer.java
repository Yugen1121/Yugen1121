


public class Customer {
	
	private String name;
	private int customerID;
	private String contactDetail;
	private String billingInformation;
	
	public Customer(String name, int customerID, String contactDetail) {
		this.name = name;
		this.customerID = customerID;
		this.contactDetail = contactDetail;
	}
	
	String getName() {
		return this.name;
	}
	
	int getCustomerID() {
		return this.customerID;
	}
	
	String getContactDetail() {
		return this.contactDetail;
	}
	
	String getBillingInformation() {
		return this.billingInformation;
	}
	
	void setName(String name) {
		this.name = name;
	}
	
	void setContactDetail(String detail){
		this.contactDetail = detail;
	}
	
	void setBillingInformation(String billingInformation) {
		this.billingInformation = billingInformation;
	}
	void printDetails() {
		System.out.println("ID: " + getCustomerID());
		System.out.println("Name: " + getName());
		System.out.println("Contact: " + getContactDetail());

			
	}
}
