package ro.teamnet.zerotohero.oop.graphicshape;

/**
 * Created by Diana Maria on 30.06.2016.
 */
public class Point {
    public int xPos;
    public int yPos;

    public Point(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public boolean equals(Object other) {
        if(other == null)
            return false;
        if(other instanceof Point) {
            Point anotherPoint = (Point) other;
            if( (xPos == anotherPoint.xPos) && (yPos == anotherPoint.yPos) )
                return true;
        }
        return false;
    }


}
