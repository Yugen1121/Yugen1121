

public class Main{
    public static void main(String[] args){
        Cars x = new Cars("Toyota", 1990, "Hilux", "XXX111", 20000);
        x.getBrand();
        return;
    }
}
class Cars{
    private String brand;
    private int year;
    private String model;
    private String carNum;
    private int miles;

    public Cars(String brand, int year, String model, String carNum, int miles){
        this.brand = brand;
        this.year = year;
        this.model = model;
        this.carNum = carNum;
        this.miles = miles;
    }

    public void setBrand(String brand){
        this.brand = brand;
        return;
    }

    public String getBrand(){
        System.out.println(this.brand);
        return this.brand;
    }

    public void setYear(int year){
        this.year = year;
        return;
    }

    public void setModel(String model){
        this.model = model;
        return;
    }
 
    public void setCarNum(String carNum){
        this.carNum = carNum;
        return;
    }

    public void setMiles(int Miles){
        this.miles = Miles;
        return;
    }

}