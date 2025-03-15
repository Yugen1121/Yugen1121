import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.HashMap;

public class Main {
	
	private static int ID = 0;
	private static int bookingID = 0;
	private static HashMap<Integer, Customer> Customers = new HashMap<Integer, Customer>();
	private static Scanner scanner = new Scanner(System.in);
	
	
	public static void main(String[] args) {
		int Option = -1;
		while (true) {
			Option = -1;
			System.out.println("Menue:");
			System.out.println("1. Create account.");
			System.out.println("2. Log_in.");
			System.out.println("3. exit.");
			
			Option = checkOption();
			
			if (Option == 1) {
				
				createAccount();
				
			}
			else if(Option == 2) {
				
				logIN();
				
			}
			else if(Option == 3 && Option == -1) {
				
				break;
				
			}
			else {
				System.out.println("Invalid option.");
			}
			
		}
		
		System.out.println("Thank you.");
	}
	
	
	static int checkOption() {
		try {
			int Option = scanner.nextInt();
			return Option;
		}catch(InputMismatchException e) {
			return -1;
		}
	}
	
	
	static void createAccount() {
		
		System.out.println("Enter your name: ");
		String name = scanner.next();
		System.out.println("Enter your contact detail: ");
		String detail = scanner.next();
		ID++;
		Customers.put(ID, new Customer(name, ID, detail));

		System.out.println("Option 1");
	}
	
	
	
	static void logIN() {
		
		for (int i = 0; i < 3; i++) {
			System.out.println("Enter your ID: ");
			int id = checkOption();
			System.out.println("Enter you name: ");
			String name = scanner.next();
			System.out.println("Enter your contact detail");
			String contact = scanner.next();
			Customer customer = Customers.get(id);
			if (customer != null) {
				if (customer.getName().equals(name) || customer.getContactDetail() == contact) {
					System.out.println("Optionnnnn");
					break;
				}
			}
			else {
				System.out.println("Contact not found");
			}
		}
		
	}
	
	
	static void Menue2(){
		int option = -1;
		while (true) {
			option = -1;
			System.out.println("1. View profile details.");
			System.out.println("2. Book");
			System.out.println("3. edit booking");
			System.out.println("4. exit");
			
			option = checkOption();
			
			if (option == 1) {
				
			}
			else if(option == 2) {
				
			}
			else if(option == 3) {
				
			}
			else if (option == 4 && option == -1) {
				break;
			}
			else {
				System.out.println("Invalid option.");
			}
		}
	}
	
	
	
}
