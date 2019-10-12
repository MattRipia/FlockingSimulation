package FlockingSimulation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class MainPanel extends JPanel implements ActionListener{

    public Timer timer;
    public DrawPanel drawPanel;
    public JPanel sliderPanel;
    public Model model;
    int width = 1200, height = 900;
    
    public MainPanel()
    {
        super(new BorderLayout());
        super.setPreferredSize(new Dimension(width,height));
        
        model = new Model(width, height, sliderPanel);
        drawPanel = new DrawPanel(model);
        sliderPanel = new JPanel();
        
        JSlider cohesionSlider = new JSlider(0, 300, 130);
        cohesionSlider.setBorder(BorderFactory.createTitledBorder("Cohesion - "));
        cohesionSlider.addChangeListener(new ChangeListener() 
        {
            public void stateChanged(ChangeEvent event) 
            {
              model.cohesionValue = cohesionSlider.getValue() / 100;
            }
          });
        
        JSlider alignmentSlider = new JSlider(0, 300, 250);
        alignmentSlider.setBorder(BorderFactory.createTitledBorder("Alignment - "));
        alignmentSlider.addChangeListener(new ChangeListener() 
        {
            public void stateChanged(ChangeEvent event) 
            {
              model.alignmentValue = alignmentSlider.getValue() / 100;
            }
          });
                
        JSlider seperationSlider = new JSlider(0, 300, 150);
        seperationSlider.setBorder(BorderFactory.createTitledBorder("Seperation - "));
        seperationSlider.addChangeListener(new ChangeListener() 
        {
            public void stateChanged(ChangeEvent event) 
            {
              model.seperationValue = seperationSlider.getValue() / 100;
            }
          });
        
        sliderPanel.add(cohesionSlider);
        sliderPanel.add(alignmentSlider);
        sliderPanel.add(seperationSlider);
        
        this.add(drawPanel, BorderLayout.CENTER);
        this.add(sliderPanel, BorderLayout.SOUTH);
        
        timer = new Timer(10, this);
        timer.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        drawPanel.repaint();
    }
}
