
public class Booking {
	private int bookingID;
	private Hotel hotel;
	private ModeOfTransport travelMode;
	private Rentals rentals;
	
	public Booking(int ID) {
		bookingID = ID;
	}
	
	void addToRentals(String rental) {
		rentals.addRental(rental);
	}
	
	void removeRentals(String rental) {
		rentals.removeRentals(rental);
	}
	
	void clearRentals() {
		rentals.removeAll();
	}
	
	void setHotelStar(int star) {
		hotel.setStar(star);
	}
	
	void setRange(int range) {
		hotel.setRange(range);
	}
	
	void setNum(int num) {
		hotel.setNum(num);
	}
	
	void addToTransports(String transport) {
		travelMode.addTransport(transport);
	}
	
	void showTransports() {
		travelMode.printTransports();
	}
	void removeTransports(String transport) {
		travelMode.removeTransport(transport);
	}
}
