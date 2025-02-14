import java.lang.Math;



public class Week2{
    public static void main(String[] args){
        circle x = new circle("red");
        x.draw();
        x.calculateArea(2);
    }
}

class shapes{
    private String color;

    public shapes(String color){
        this.color = color;
    }

    public void area(){
        System.out.println("Calculating the area of the shape");
    }

    public void draw(){
        System.out.println("Drawing the shape with the color "+this.color);
    }
}

class circle extends shapes{

    public circle(String color){
        super(color);
    }

    public void calculateArea(int radius){
        super.area();
        System.out.println("The are of the circle with radius "+radius+" is " + 3.14*radius*radius);
    }
    
}
