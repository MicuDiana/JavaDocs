package ro.teamnet.zerotohero.oop.graphicshape;

/**
 * Created by Diana Maria on 30.06.2016.
 */
public class Point3D extends Point {
    private int zPos;
    public Point3D(int xPos, int yPos, int zPos){
        super(xPos,yPos);
        this.zPos = zPos;
    }
}
