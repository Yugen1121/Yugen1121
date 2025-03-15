
public class Exhibit {
	private String name;
	private int roomNumber;
	private int capacity;
	private String description;
	private int currentVisitor;
	
	public Exhibit(String name, int roomNumber, int capacity, String description, int currentVisitor) {
		this.name = name;
		this.roomNumber = roomNumber;
		this.capacity = capacity;
		this.description = description;
		this.currentVisitor = currentVisitor;
	}
	
	public int getRoomNumber() {
		return this.roomNumber;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public int getCurrentVisitors() {
		return this.currentVisitor;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setVisitors(int visitors) {
		this.currentVisitor = visitors;
	}
	
	public void setRoomNumbwe(int num) {
		this.roomNumber = num;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	
}
