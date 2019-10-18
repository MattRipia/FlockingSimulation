package FlockingSimulation;

/* 
    The vector class is a simple data structure which was created by:
    https://rosettacode.org/wiki/Boids/Java

    It contains basic vector math and is useful for the implementation of the algorithm
*/

public class Vector 
{
    double x, y;
    
    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }
 
    void add(Vector v) {
        x += v.x;
        y += v.y;
    }
 
    void sub(Vector v) {
        x -= v.x;
        y -= v.y;
    }
 
    void div(double val) {
        x /= val;
        y /= val;
    }
 
    void mult(double val) {
        x *= val;
        y *= val;
    }
 
    // returns the current magnitude
    double mag() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    // normalizes the x and y values based on the magnatude
    // we are essentially setting the magnitude to 1, but keeping the same direction.
    void normalize() {
        double mag = mag();
        if (mag != 0) {
            x /= mag;
            y /= mag;
        }
    }
 
    // if magnitude is greater than the limit, then we want to scale down
    void limit(double lim) {
        double mag = mag();
        if (mag != 0 && mag > lim) 
        {
            x *= lim / mag;
            y *= lim / mag;
        }
    }
 
    // subtracts one vector from the other
    static Vector sub(Vector v, Vector v2) {
        return new Vector(v.x - v2.x, v.y - v2.y);
    }
 
    // finds the distance between two vectors
    static double dist(Vector v, Vector v2) {
        return Math.sqrt(Math.pow(v.x - v2.x, 2) + Math.pow(v.y - v2.y, 2));
    }
}
