package ro.teamnet.zerotohero.oop.graphicshape.runapp;
import ro.teamnet.zerotohero.oop.graphicshape.*;
import ro.teamnet.zerotohero.oop.graphicshape.canvas.*;
import ro.teamnet.zerotohero.oop.graphicshape.exception.MyException;

/**
 * Created by Diana Maria on 30.06.2016.
 */
public class RunApp {
    public static void testThrow() throws MyException {
        throw new MyException();
    }

    public static void main(String[] args) {
        Circles printCircle = new Circles();
        System.out.println("The default circle area is : " + printCircle.getAreaPub());

        Canvas canvas = new Canvas();
        canvas.getArea();
        Shape newShape = new Circle(10);
        System.out.println(newShape.area());

        ShapeBehaviour shapeBehaviour = new Square(10);
        System.out.println(shapeBehaviour.area());

        Object p1 = new Point(10, 20);
        Object p2 = new Point(50, 100);
        Object p3 = new Point(10, 20);

        System.out.println("p1 equals p2 is " + p1.equals(p2));
        System.out.println("p1 equals p3 is " + p1.equals(p3));

        try {
            testThrow();
        } catch (MyException e) {
           Exception nestedException = new Exception("Test", e);


            try {
                throw nestedException;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
