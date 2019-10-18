package FlockingSimulation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Boid implements Runnable
{
    public int width, height, perception;
    public Vector position;
    public Vector velocity;
    public Model model;
    public Random rand = new Random();
    public float maxForce;
    public int maxSpeed;
    public int seperationValue;
    public int cohesionValue;
    
    public Boid(double x, double y, Model m, int w, int h)
    {
        model = m;
        this.width = w;
        this.height = h;
        this.position = new Vector(rand.nextInt(w), rand.nextInt(h));
        this.velocity = new Vector(x, y);
        this.maxForce = 0.05f;
        this.maxSpeed = 3;
        this.perception = 40;
        this.seperationValue = 80;
        this.cohesionValue = 15;
    }

    private void updateVelocity() 
    {
        // updates the velocity(direction) by looking at the surrounding boids
        Vector ali = align();
        Vector sep = seperation();
        Vector coh = cohesion();
        
        // scale these values up
        ali.mult(model.alignmentValue);
        sep.mult(model.seperationValue);
        coh.mult(model.cohesionValue);
        
        // get the total acceleration needed by adding all these values
        Vector acc = new Vector(0,0);
        acc.add(ali);
        acc.add(sep);
        acc.add(coh);
        
        // apply this acceleration to the boid
        moveBoid(acc);
        
        // check the boid is still within the window, or else relocate it
        checkEdges();
    }
    
    
    private Vector seperation()
    {
        Vector acceleration = new Vector(0,0);
        int total = 0;
        
        for(Boid b : model.getBoids())
        {
            double distance = Vector.dist(this.position, b.position);
            if(b != this)
            {
                if(distance < seperationValue && distance > 0)
                {
                    total++;
                    
                    // get the difference in position
                    Vector differnce = Vector.sub(this.position, b.position);
                    
                    // normlaize the difference
                    differnce.normalize();
                    
                    // divide the difference by the distance
                    differnce.div(distance);
                    
                    // add the difference to the steering force
                    acceleration.add(differnce);
                }
            }
        }
        
        // if more than one boid was found, divide the steering force by this many
        if(total > 0){
            acceleration.div(total);
        }
        
        // if the magnitude is greater than 0, or else return a new vector (0,0)
        if (acceleration.mag() > 0) 
        {
            // normlaize the magnitude (set it to 1, but keep the position the same
            acceleration.normalize();
            
            // multiply the vector by the maxSpeed
            acceleration.mult(maxSpeed);
            
            // subtract our own velocity
            acceleration.sub(velocity);
            
            
             // limit the result depending if mag > maxforce
            acceleration.limit(maxForce);
            return acceleration;
        }

        return new Vector(0,0);
    }
    
    private Vector align()
    {
        Vector acceleration = new Vector(0,0);
        int total = 0;
        
        for(Boid b : model.getBoids())
        {
            double distance = Vector.dist(this.position, b.position);
            if(b != this)
            {
                if(distance < perception && distance > 0)
                {
                    total++;
                    
                    // add b's velocity to the acceleration vector
                    acceleration.add(b.velocity);
                }
            }
        }
        
        // steering force (desired - own velocity)
        if(total > 0)
        {
           // average out the position of nearby nodes
           acceleration.div(total);
           
            // normlaize the magnitude (set it to 1, but keep the position the same
           acceleration.normalize();
           
           // multiply the vector by the maxSpeed
           acceleration.mult(maxSpeed);
           
           // subtract our own velocity
           acceleration.sub(velocity);
           
           // limit the result depending if mag > maxforce
           acceleration.limit(maxForce);
        }
        
        //System.out.println("acceleration: " + acceleration.x + " " + acceleration.y);
        return acceleration;
    }

    private Vector cohesion() 
    {
        Vector pos = new Vector(0,0);
        int total = 0;
        
        for(Boid b : model.getBoids())
        {
            double distance = Vector.dist(this.position, b.position);
            if(b != this)
            {
                if(distance < cohesionValue && distance > 0)
                {
                    // if the current boid is close to others, add their distance to a new vector
                    pos.add(b.position);
                    total++;
                }
            }
        }
        
        // if more than one boid was found, divide the steering force by this many and find the steering force
        if(total > 0)
        {
            // average out the position of nearby nodes
            pos.div(total);
            
            // subtract our own position from the average
            Vector acceleration = Vector.sub(pos, this.position);
            
            // normlaize the magnitude (set it to 1, but keep the position the same
            acceleration.normalize();
            
            // multiply the vector by the maxSpeed
            acceleration.mult(maxSpeed);
            
            // subtract our own velocity
            acceleration.sub(velocity);
            
            // limit the result depending if mag > maxforce
            acceleration.limit(maxForce);
            return acceleration;
        }
        
        // else return an empty vector -> no change in acceleration
        return new Vector(0,0);
    }
    
    private void moveBoid(Vector acceleration) 
    {
        // add the acceleration to the current velocity
        velocity.add(acceleration);
        
        // make sure the velocity is less than max speed
        velocity.limit(maxSpeed);
        
        // apply the velocity to the position -> making the boid move
        position.add(velocity);
    }

    private void checkEdges() 
    {
        if(position.x > width + 10){
            position.x = 0;
        }
        else if(position.x <= 0){
            position.x = width + 9;
        }

        if(position.y > height + 10){
            position.y = 0;
        } 
        else if(position.y <= 0){
            position.y = height + 9;
        }
    }
    
    @Override
    public void run() 
    {
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
