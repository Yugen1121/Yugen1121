import java.util.ArrayList;


public class Rentals {
	private ArrayList<String> Rentals = new ArrayList<String>();
	
	void addRental(String rentals) {
		Rentals.add(rentals);
	}
	
	void addShowRentals() {
		if (!Rentals.isEmpty()) {
			System.out.println("No rentlas");
			return;
		}
		for (String i: Rentals) {
			System.out.println(i);
		}
	}
	
	void removeRentals(String rental) {
		for (int i = 0; i < Rentals.size(); i++) {
			if (Rentals.get(i).equals(rental)) {
				Rentals.remove(i);
				System.out.println("Removed " + rental);
				return;
			}
		}
		
		System.out.println("Could'nt find " + rental);
	}
	
	void removeAll() {
		Rentals.clear();
		System.out.println("Successfully cleared.");
	}
}
