package FlockingSimulation;

import java.awt.Point;
import java.util.ArrayList;
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
    public float maxForce;
    public int maxSpeed;
    
    public Boid(double x, double y, Model m, int w, int h)
    {
        model = m;
        this.perception = 20;
        this.width = w;
        this.height = h;
        this.position = new Point(rand.nextInt(w), rand.nextInt(h));
        this.velocity = new Vector(x, y);
        this.maxForce = 0.01f;
        this.maxSpeed = 3;
    }

    private void updateVelocity() 
    {
        // updates the velocity(direction) by looking at the surrounding boids
        Vector acceleration = align(model.boids);
        moveBoid(acceleration);
    }
    
    private Vector align(ArrayList<Boid> boids)
    {
        Vector acceleration = new Vector(0,0);
        int total = 0;
        
        for(Boid b : boids)
        {
            double distance = position.distance(b.position);
            if(b != this)
            {
                if(distance < perception)
                {
                    total++;
                    acceleration.x += b.velocity.x;
                    acceleration.y += b.velocity.y;
                }
            }
        }
        
        // steering force (desired - velocity)
        if(total > 0)
        {
            acceleration.x = (acceleration.x / total);
            acceleration.y = (acceleration.y / total);
            acceleration.x -= velocity.x;
            acceleration.y -= velocity.y;
            
            // magnitude?
            //acceleration.setMag(this.maxSpeed);
            
            if(acceleration.x > maxForce){
                acceleration.x = maxForce;
            }
            if(acceleration.y > maxForce){
                acceleration.y = maxForce;
            }
        }
        
        return acceleration;
    }
    
    private void moveBoid(Vector acceleration) 
    {
        
        velocity.x += acceleration.x;
        velocity.y += acceleration.y;
        position.x += velocity.x;
        position.y += velocity.y;
        
        if(velocity.x > maxSpeed){
            velocity.x = maxSpeed;
        }
        
        if(velocity.y > maxSpeed){
            velocity.y = maxSpeed;
        }

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
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Boid.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
