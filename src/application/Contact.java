package application;

import javafx.beans.property.SimpleStringProperty;

public class Contact {
	private SimpleStringProperty first;
	private SimpleStringProperty last;
	private SimpleStringProperty phone;
	private SimpleStringProperty email;
	private SimpleStringProperty street;
	private SimpleStringProperty city;
	private SimpleStringProperty state;
	private SimpleStringProperty zip;
	private SimpleStringProperty country;
	
	public Contact(String first, String last, String phone, String email, 
		           String street, String city, String state, String zip, String country) {
		this.first = new SimpleStringProperty(first);
		this.last = new SimpleStringProperty(last);
		this.phone = new SimpleStringProperty(phone);
		this.email = new SimpleStringProperty(email);
		this.street = new SimpleStringProperty(street);
		this.city = new SimpleStringProperty(city);
		this.state = new SimpleStringProperty(state);
		this.zip = new SimpleStringProperty(zip);
		this.country = new SimpleStringProperty(country);
	}
	
	public void setFirst(String first) {
		this.first = new SimpleStringProperty(first);
	}
	public void setLast(String last) {
		this.last = new SimpleStringProperty(last);
	}
	public void setPhone(String phone) {
		this.phone = new SimpleStringProperty(phone);
	}
	public void setEmail(String email) {
		this.email = new SimpleStringProperty(email);
	}
	public void setStreet(String street) {
		this.street = new SimpleStringProperty(street);
	}
	public void setCity(String city) {
		this.city = new SimpleStringProperty(city);
	}
	public void setState(String state) {
		this.state = new SimpleStringProperty(state);
	}
	public void setZip(String zip) {
		this.zip = new SimpleStringProperty(zip);
	}
	public void setCountry(String country) {
		this.country = new SimpleStringProperty(country);
	}
	
	public String getFirst() {
		return first.get();
	}
	
	public String getLast() {
		return last.get();
	}
	
	public String getPhone() {
		return phone.get();
	}
	
	public String getEmail() {
		return email.get();
	}
	
	public String getStreet() {
		return street.get();
	}
	
	public String getCity() {
		return city.get();
	}
	
	public String getState() {
		return state.get();
	}
	public String getZip() {
		return zip.get();
	}
	public String getCountry() {
		return country.get();
	}
	
}
