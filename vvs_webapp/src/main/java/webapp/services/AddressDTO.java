package webapp.services;

public class AddressDTO {
	/**
	 * Address internal identification (unique, primary key, sequential)
	 * Generated by the database engine.
	 */
	public final int id;

	/**
	 * Customer's VAT number
	 */
	public final int customerVat;

	/**
	 * The address fill with address, door, postal code and locality 
	 */
	public final String address;

	public AddressDTO(int id, int customerVat, String address) {
		this.id = id;
		this.customerVat = customerVat;
		this.address = address;
	}
	
	public String toString(){
		return address;
	}
}
