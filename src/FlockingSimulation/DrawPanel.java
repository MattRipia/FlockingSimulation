package FlockingSimulation;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

class DrawPanel extends JPanel
{
    Model model;
    
    public DrawPanel(Model m)
    {
        setBackground(Color.BLACK);
        model = m;
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        // painting
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        for(Boid b : model.getBoids())
        {
            int x = (int)b.position.x;
            int y = (int)b.position.y;
            g.fillOval(x, y, 10, 10);
        }
    }
}
