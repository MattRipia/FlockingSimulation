package FlockingSimulation;

import java.util.ArrayList;
import java.util.Random;

class Model 
{
    public ArrayList<Boid> boids = new ArrayList();
    int width, height;
    Random rand = new Random();
    
    public Model(int w, int h)
    {
        width = w;
        height = h;
        
        for(int i = 0 ; i < 50; i++)
        {
            int xV = rand.nextInt(3) - 2;
            int yV = rand.nextInt(3) - 2;
            
            while(xV == 0 || yV == 0){
                xV = rand.nextInt(3) - 2;
                yV = rand.nextInt(3) - 2;
            }
            
            Boid b = new Boid(xV, yV, this, w, h);
            Thread t = new Thread(b);
            t.start();
            addBoid(b);
        }
    }
    
    public synchronized ArrayList<Boid> getBoids(){
        return boids;
    }
    
    public synchronized void addBoid(Boid b){
        boids.add(b);
    }
}
