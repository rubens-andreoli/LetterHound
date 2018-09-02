/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps.letterhound.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import org.scribble.Scribble;

/**
 *
 * @author Amanda
 */
public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {

    private static final int SIZE = 200;
    private static final BasicStroke LINESTYLE = new BasicStroke(
	    15, 
	    BasicStroke.CAP_ROUND, 
	    BasicStroke.JOIN_ROUND
    );
    
    private ArrayList scribbles;
    private BufferedImage image;
    
    private Scribble currentScribble;

    public DrawPanel() {
	setSize(SIZE,SIZE);
	setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	image = newImage();
	scribbles = new ArrayList();
	addMouseListener(this);
	addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) image.getGraphics();
	g2.fillRect(0,0,image.getWidth()+10,image.getHeight()+10);
	g2.setColor(Color.BLACK);
	g2.setStroke(LINESTYLE);
	int numScribbles = scribbles.size();
	for (int i = 0; i < numScribbles; i++) {
	    Scribble s = (Scribble) scribbles.get(i);
	    g2.draw(s);
	}
	g.drawImage(image, 0, 0, this);
    }
    
    protected BufferedImage newImage(){
	return new BufferedImage(
		this.getWidth()+10, 
		this.getHeight()+10, 
		BufferedImage.TYPE_BYTE_GRAY
	);
    }
    
    public void clear(){
	scribbles.clear();
	image = newImage();
	this.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
	currentScribble = new Scribble();
	scribbles.add(currentScribble);
	currentScribble.moveto(e.getX(), e.getY());
    }

    /**
     * This method and mouseMoved() below are from the MouseMotionListener interface. If we're in drawing
     * mode, this method adds a new point to the current scribble and requests a redraw
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
	currentScribble.lineto(e.getX(), e.getY());
	repaint();
    }
    
    public BufferedImage getImage() {
	return image;
    }

    public @Override void mouseReleased(MouseEvent e) {}
    public @Override void mouseClicked(MouseEvent e) {}
    public @Override void mouseEntered(MouseEvent e) {}
    public @Override void mouseExited(MouseEvent e) {}
    public @Override void mouseMoved(MouseEvent e) {}
}
