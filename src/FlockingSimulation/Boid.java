package FlockingSimulation;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Boid implements Runnable
{
    public Point position;
    public int width, height, perception;
    double xVelocity, yVelocity, x, y;
    public Model model;
    public Random rand = new Random();
    
    public Boid(int xV, int yV, Model m, int w, int h)
    {
        model = m;
        this.perception = 50;
        this.width = w;
        this.height = h;
        this.position = new Point(rand.nextInt(w), rand.nextInt(h));
        this.x = xV;
        this.y = yV;
    }

    private void updateVelocity() 
    {
        // updates the velocity(direction) by looking at the surrounding boids
        align();
        moveBoid();
    }
    
    private void align()
    {
        int totalX = 0;
        int totalY = 0;
        int total = 0;
        Vector v;
        for(Boid b : model.boids)
        {
            double distance = this.position.distance(b.position);
            if(b != this && distance < perception)
            {
                total++;
                totalX += b.xVelocity;
                totalY += b.yVelocity;
            }
        }
        
        // steering force (desired - velocity)
        if(total > 0)
        {
            if(x > 0){
                x = (totalX / total) - Math.abs(x);
            }
            else{
                x = (totalX / total) + x;
            }
            
            if(y > 0)
            {
                y = (totalY / total) - Math.abs(y);
            }
            else
            {
                y = (totalY / total) + y;
            }
        }
//        
//        if(x == 0){
//            x = 1;
//        }
//        if(y == 0){
//            y = 1;
//        }
    }
    
    private void moveBoid() 
    {
        position.x += x;
        position.y += y;

        if(position.x > width + 10){
            position.x = -10;
        }

        if(position.y > height + 10){
            position.y = -10;
        }

        if(position.x < -10){
            position.x = width + 10;
        }

        if(position.y < -10){
            position.y = height + 10;
        }
    }
    
    @Override
    public void run() 
    {
        try {
            // initial sleep so all the threads have a chance to start
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Boid.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(true)
        {
            try {
                updateVelocity();
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Boid.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
