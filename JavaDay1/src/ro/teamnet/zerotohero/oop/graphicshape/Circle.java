package ro.teamnet.zerotohero.oop.graphicshape;

/**
 * Created by Diana Maria on 30.06.2016.
 */
public class Circle extends Shape{
    private int xPos;
    private int yPos;
    private int radius;

    public Circle(){
        this.xPos = 2;
        this.yPos = 4;
        this.radius = 2;
    }

    public Circle(int radius){
        this.radius = radius;
    }

    public Circle(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Circle(int xPos, int yPos, int radius){
        this.xPos = xPos;
        this.yPos = yPos;
        this.radius = radius;
    }

    public double area(){
        return this.radius * this.radius * Math.PI;
    }

    public void fillColour(){
        System.out.println(super.color);
    }
    public void fillColour(int color) {
        super.setColor(color);
        System.out.println("The circle color is now 2");
    }
    public void fillColour(float saturation){
        super.setSaturation(saturation);
    }

    @Override
    public String toString() {
        return "center = (" + xPos + ","
                    + yPos +
                ") and radius=" + radius;
    }
}
