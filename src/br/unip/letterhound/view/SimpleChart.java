package br.unip.letterhound.view;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class SimpleChart extends JPanel{

    private double[] heights;
    private double width;
    private int maxHeight;
    private int maxWidth;

    protected SimpleChart() {}

    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	int desloc = 0;
	double error = maxWidth - width*heights.length;
	
	if(error > 0){
	    desloc = (int) (error/2); 
	}
	    
	for(int i=0; i<heights.length; i++){
	    g.fillRect((int) (i*width)+desloc, (int) (maxHeight-heights[i]), (int) width, (int) heights[i]);
	}
    }

    public static void drawChart(double[] data, int maxValue, JPanel panel){
	panel.removeAll();
	
	int maxHeight = panel.getHeight()-10;
	int maxWidth= panel.getWidth()-10;
	double scale = (double)maxHeight/(double)maxValue;
	
	SimpleChart c = new SimpleChart();
	Dimension d = new Dimension(maxWidth, maxHeight);
	c.setMaximumSize(d);
	c.setMinimumSize(d);
	c.setPreferredSize(d);
	c.width = maxWidth/(double)data.length;
	c.heights = new double[data.length];
	c.maxHeight = maxHeight;
	c.maxWidth = maxWidth;
	
	for(int i=0;i<data.length;i++){
	    c.heights[i] = data[i]*scale;
	}
	
	panel.add(c);
	panel.validate();
    
    }
}
