package FlockingSimulation;

public class Vector 
{
    double x, y;
    
    public Vector(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
 
    void add(Vector v) 
    {
        x += v.x;
        y += v.y;
    }
 
    void sub(Vector v) 
    {
        x -= v.x;
        y -= v.y;
    }
 
    void div(double val) 
    {
        x /= val;
        y /= val;
    }
 
    void mult(double val) 
    {
        x *= val;
        y *= val;
    }
 
    double mag() 
    {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
 
    double dot(Vector v) 
    {
        return x * v.x + y * v.y;
    }
 
    void normalize() {
        double mag = mag();
        if (mag != 0) {
            x /= mag;
            y /= mag;
        }
    }
 
    void limit(double lim) 
    {
        double mag = mag();
        if (mag != 0 && mag > lim) {
            x *= lim / mag;
            y *= lim / mag;
        }
    }
 
    double heading() {
        return Math.atan2(y, x);
    }
 
    static Vector sub(Vector v, Vector v2) {
        return new Vector(v.x - v2.x, v.y - v2.y);
    }
 
    static double dist(Vector v, Vector v2) {
        return Math.sqrt(Math.pow(v.x - v2.x, 2) + Math.pow(v.y - v2.y, 2));
    }
 
    static double angleBetween(Vector v, Vector v2) {
        return Math.acos(v.dot(v2) / (v.mag() * v2.mag()));
    }
        
}
