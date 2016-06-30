package ro.teamnet.zerotohero.oop.graphicshape;

/**
 * Created by Diana Maria on 30.06.2016.
 */
public class Circles {

    public double getAreaPub(){
        Circle testCircle = new Circle();
        return testCircle.area();
    }

    public void getAreaDef(){
        Circle testCircle = new Circle();
        testCircle.fillColour();
        testCircle.fillColour(2);
        testCircle.fillColour(2.3f);
    }
}
