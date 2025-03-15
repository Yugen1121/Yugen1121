import java.util.ArrayList;

public class ModeOfTransport {
	private ArrayList<String> Transports = new ArrayList<String>();
	
	void addTransport(String transport) {
		Transports.add(transport);
	}
	
	ArrayList<String> getTransports(){
		return Transports;
	}
	
	void printTransports() {
		if (!Transports.isEmpty()) {
			System.out.println("No transports.");
			return;
		}
		for (String i: Transports) {
			System.out.println(i + "\n");
		}
	}
	
	void removeTransport(String transport) {
		for (int i = 0; i < Transports.size(); i++) {
			if (Transports.get(i).equals(transport)) {
				Transports.remove(i);
				System.out.println("Removed successfully");
				return;
			}
		}
		System.out.println("Couldn't find the method.");
		return;
		
	}
}