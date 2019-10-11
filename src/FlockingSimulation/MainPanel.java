package FlockingSimulation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;


public class MainPanel extends JPanel implements ActionListener{

    public Timer timer;
    public DrawPanel drawPanel;
    public Model model;
    int width = 1200, height = 900;
    
    public MainPanel()
    {
        super(new BorderLayout());
        super.setPreferredSize(new Dimension(width,height));
        
        model = new Model(width, height);
        drawPanel = new DrawPanel(model);
        
        this.add(drawPanel, BorderLayout.CENTER);
        
        timer = new Timer(10, this);
        timer.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        drawPanel.repaint();
    }
}
