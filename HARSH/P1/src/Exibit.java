
public class Exibit {
	private String name;
	private String description;
	private int roomNumber;
	private int capacity;
	private int currentVisitors;
	
	public Exibit() {
		
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String des) {
		this.description = des;
	}
	
	public void setRoomNumber(int num) {
		this.roomNumber = num;
	}
	
	public void setCurrentVisitiors(int num) {
		this.currentVisitors = num;
	}
	
	public void setCapacity(int num) {
		this.capacity = num;
	}
	
	public void getName() {
		System.out.println(this.name);
	}
	
	public void getDescription(){
		System.out.println(this.description);
	}
	
	public void getRoomNumber() {
		System.out.println(this.roomNumber);
	}
	
	public void getCapacity() {
		System.out.println(this.capacity);
	}
	
	public void getCurrentVisitors() {
		System.out.println(this.currentVisitors);
	}
	
}
