package FlockingSimulation;

import java.awt.Point;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Boid implements Runnable
{
    public int width, height, perception;
    public Point position;
    public Vector velocity;
    public Model model;
    public Random rand = new Random();
    
    public Boid(double x, double y, Model m, int w, int h)
    {
        model = m;
        this.perception = 50;
        this.width = w;
        this.height = h;
        this.position = new Point(rand.nextInt(w), rand.nextInt(h));
        this.velocity = new Vector(x, y);
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
        
        for(Boid b : model.boids)
        {
            double distance = this.position.distance(b.position.x, b.position.y);
            if(b != this)
            {
                if(distance < perception)
                {
                    total++;
                    totalX += b.velocity.x;
                    totalY += b.velocity.y;
                }
            }
        
        // steering force (desired - velocity)
        if(total > 0)
        {
            if(velocity.x > 0){
                velocity.x = (totalX / total) - Math.abs(velocity.x);
            }
            else{
                velocity.x = (totalX / total) + velocity.x;
            }
            
            if(velocity.y > 0)
            {
                velocity.y = (totalY / total) - Math.abs(velocity.y);
            }
            else
            {
                velocity.y = (totalY / total) + velocity.y;
            }
        }
    }
    
    private void moveBoid() 
    {
        position.x += velocity.x;
        position.y += velocity.y;

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
