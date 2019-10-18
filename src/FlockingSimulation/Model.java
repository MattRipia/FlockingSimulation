package FlockingSimulation;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

class Model 
{
    public ArrayList<Boid> boids = new ArrayList();
    int width, height;
    Random rand = new Random();
    JPanel mainPanel;
    
    float cohesionValue = 1.3f;
    float alignmentValue = 2.5f;
    float seperationValue = 1.5f;
    
    public Model(int w, int h, JPanel mainPanel)
    {
        width = w;
        height = h;
        this.mainPanel = mainPanel;
        
        for(int i = 0 ; i < 500; i++)
        {
            int xV = rand.nextInt(7) - 3;
            int yV = rand.nextInt(7) - 3;
            
            while(xV == 0 && yV == 0){
                xV = rand.nextInt(7) - 3;
                yV = rand.nextInt(7) - 3;
            }
            
            Boid b = new Boid(xV, yV, this, w, h);
            addBoid(b);

        }
        
        for(Boid b : boids)
        {
            Thread t = new Thread(b);
            t.start();
        }
    }
    
    public synchronized ArrayList<Boid> getBoids(){
        return boids;
    }
    
    public synchronized void addBoid(Boid b){
        boids.add(b);
    }
}
