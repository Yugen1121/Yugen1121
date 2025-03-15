
public class Hotel {
	private int star = 0;
	private int range = 0;
	private int num = 0;
	public Hotel(int star, int range, int num) {
		this.star = star;
		this.range = range;
		this.num = num;
	}
	
	int getStar() {
		return this.star;
	}
	
	int getNum() {
		return this.num;
	}
	
	int getRange() {
		return this.range;
	}
	
	void setStar(int star) {
		this.star = star;
	}
	
	void setRange(int range) {
		this.range = range;
	}
	
	void  setNum(int num) {
		this.num = num;
	}
}
