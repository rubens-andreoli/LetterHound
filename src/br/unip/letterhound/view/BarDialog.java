package br.unip.letterhound.view;

import br.unip.letterhound.core.Progressable;
import java.awt.Component;
import java.awt.event.WindowEvent;

public class BarDialog extends javax.swing.JDialog {

    private final String label;
    private final Progressable progressable;
    private final Component parent;

    public BarDialog(final Component parent, final Progressable progressable, String label) {
	this.progressable = progressable;
	this.label = label;
	this.parent = parent;
	initComponents();
    }
    
    public void start() {
	setVisible(true);
	setLocationRelativeTo(parent);
	bar.setMaximum(progressable.getMaxCount());
	while(bar.getValue() < progressable.getMaxCount()){
//	    synchronized(progressable) {
		bar.setValue(progressable.getCounter());
//	    }
	}

	this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(label);
        setAlwaysOnTop(true);
        setFocusable(false);
        setUndecorated(true);
        setResizable(false);
        setSize(300,30);

        bar.setString(label);
        bar.setStringPainted(true);
        getContentPane().add(bar, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(300, 30));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar bar;
    // End of variables declaration//GEN-END:variables
}
