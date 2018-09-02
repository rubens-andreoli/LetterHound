package aps.letterhound.view;

import aps.letterhound.core.Batcher;
import aps.letterhound.core.Descriptors;
import aps.letterhound.core.Filters;
import aps.letterhound.core.IO;
import static aps.letterhound.core.IO.SUPPORTED_IMG_EXTENSIONS;
import aps.letterhound.core.NeuralNetwork;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;
import org.opencv.core.CvType;
import org.opencv.core.Mat;


public class LetterHound extends javax.swing.JFrame {

    //<editor-fold defaultstate="collapsed" desc="Static">
    public static Mat img2Mat(BufferedImage in) {
	byte[] pixels = ((DataBufferByte) in.getRaster().getDataBuffer()).getData();
	Mat out = new Mat(in.getHeight(), in.getWidth(), CvType.CV_8UC1);
	out.put(0, 0, pixels);
	return out;
    }
    //</editor-fold>
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fchFolder = new javax.swing.JFileChooser();
        fchNetFile = new javax.swing.JFileChooser();
        fchImgFile = new javax.swing.JFileChooser();
        pnlDraw = new javax.swing.JPanel();
        pnlDrawPanel = new DrawPanel();
        btnProcess = new javax.swing.JButton();
        txfResult = new javax.swing.JTextField();
        btnClear = new javax.swing.JButton();
        pnlHorProjection = new javax.swing.JPanel();
        pnlVerProjection = new javax.swing.JPanel();
        pnlDiaProjection = new javax.swing.JPanel();
        pnlSecCount = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        mnbMenu = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        mniProcess = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        mncTrainFolder = new javax.swing.JCheckBoxMenuItem();
        mncValFolder = new javax.swing.JCheckBoxMenuItem();
        sepNeural1 = new javax.swing.JPopupMenu.Separator();
        mniTrain = new javax.swing.JMenuItem();
        mniTestImg = new javax.swing.JMenuItem();
        sepNeural2 = new javax.swing.JPopupMenu.Separator();
        mniSave = new javax.swing.JMenuItem();
        mniLoad = new javax.swing.JMenuItem();
        mnuAbout = new javax.swing.JMenu();

        fchFolder.setApproveButtonToolTipText("");
        fchFolder.setCurrentDirectory(new File(System.getProperty("user.home")));
        fchFolder.setDialogTitle("Abrir");
        fchFolder.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        fchNetFile.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        fchNetFile.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fchNetFile.setDialogTitle("Salvar");
        fchNetFile.setFileFilter(new FileNameExtensionFilter("LetterHound Encog File (*.eg)","eg"));

        fchImgFile.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fchImgFile.setDialogTitle("Abrir");
        fchImgFile.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                for (String extension : SUPPORTED_IMG_EXTENSIONS) {
                    if (file.isDirectory() || file.getName().toLowerCase().endsWith(extension)) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public String getDescription() {
                return "Imagens (*.jpg; *.jpeg; *.png; *.bmp)";
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LetterHound");
        setResizable(false);

        pnlDraw.setBackground(new java.awt.Color(255, 255, 255));
        pnlDraw.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlDraw.setMaximumSize(new java.awt.Dimension(225, 225));
        pnlDraw.setMinimumSize(new java.awt.Dimension(225, 225));

        javax.swing.GroupLayout pnlDrawPanelLayout = new javax.swing.GroupLayout(pnlDrawPanel);
        pnlDrawPanel.setLayout(pnlDrawPanelLayout);
        pnlDrawPanelLayout.setHorizontalGroup(
            pnlDrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 199, Short.MAX_VALUE)
        );
        pnlDrawPanelLayout.setVerticalGroup(
            pnlDrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 206, Short.MAX_VALUE)
        );

        pnlDraw.add(pnlDrawPanel);

        btnProcess.setMnemonic('P');
        btnProcess.setText("Processar");
        btnProcess.setEnabled(false);
        btnProcess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcessActionPerformed(evt);
            }
        });

        txfResult.setEditable(false);
        txfResult.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnClear.setMnemonic('L');
        btnClear.setText("Limpar");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        pnlHorProjection.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlHorProjection.setToolTipText("Projeção Horizontal");
        pnlHorProjection.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 3));

        pnlVerProjection.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlVerProjection.setToolTipText("Projeção Vertical");

        pnlDiaProjection.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlDiaProjection.setToolTipText("Projeção Diagonal");
        pnlDiaProjection.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 3));

        pnlSecCount.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlSecCount.setToolTipText("Setores");
        pnlSecCount.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 3));

        jMenu3.setMnemonic('E');
        jMenu3.setText("Editor");

        jMenuItem5.setMnemonic('L');
        jMenuItem5.setText("Limpar");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        mniProcess.setMnemonic('P');
        mniProcess.setText("Processar");
        mniProcess.setEnabled(false);
        mniProcess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniProcessActionPerformed(evt);
            }
        });
        jMenu3.add(mniProcess);
        jMenu3.add(jSeparator2);

        jMenuItem7.setMnemonic('S');
        jMenuItem7.setText("Sair");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        mnbMenu.add(jMenu3);

        jMenu1.setMnemonic('N');
        jMenu1.setText("Neural Network");

        mncTrainFolder.setMnemonic('T');
        mncTrainFolder.setText("Arquivos de Treinamento");
        mncTrainFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mncTrainFolderActionPerformed(evt);
            }
        });
        jMenu1.add(mncTrainFolder);

        mncValFolder.setMnemonic('V');
        mncValFolder.setText("Arquivos de Validação");
        mncValFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mncValFolderActionPerformed(evt);
            }
        });
        jMenu1.add(mncValFolder);
        jMenu1.add(sepNeural1);

        mniTrain.setText("Treinar");
        mniTrain.setEnabled(false);
        mniTrain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTrainActionPerformed(evt);
            }
        });
        jMenu1.add(mniTrain);

        mniTestImg.setText("Test Image");
        mniTestImg.setEnabled(false);
        mniTestImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTestImgActionPerformed(evt);
            }
        });
        jMenu1.add(mniTestImg);
        jMenu1.add(sepNeural2);

        mniSave.setText("Salvar");
        mniSave.setEnabled(false);
        mniSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSaveActionPerformed(evt);
            }
        });
        jMenu1.add(mniSave);

        mniLoad.setText("Load");
        mniLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniLoadActionPerformed(evt);
            }
        });
        jMenu1.add(mniLoad);

        mnbMenu.add(jMenu1);

        mnuAbout.setMnemonic('B');
        mnuAbout.setText("Sobre");
        mnuAbout.addMenuKeyListener(new javax.swing.event.MenuKeyListener() {
            public void menuKeyPressed(javax.swing.event.MenuKeyEvent evt) {
                mnuAboutMenuKeyPressed(evt);
            }
            public void menuKeyReleased(javax.swing.event.MenuKeyEvent evt) {
            }
            public void menuKeyTyped(javax.swing.event.MenuKeyEvent evt) {
            }
        });
        mnuAbout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnuAboutMouseClicked(evt);
            }
        });
        mnbMenu.add(mnuAbout);

        setJMenuBar(mnbMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlDraw, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pnlHorProjection, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(pnlVerProjection, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pnlDiaProjection, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnClear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnProcess, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txfResult, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(pnlSecCount, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlSecCount, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlHorProjection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlVerProjection, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlDiaProjection, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnProcess, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txfResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(pnlDraw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private File trainFolder;
    private File valFolder;
    private NeuralNetwork nNet;
   
    public LetterHound() {
	initComponents();
    }
    
    private void btnProcessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcessActionPerformed
	if (nNet != null) {
	    Mat draw = LetterHound.img2Mat((((DrawPanel) pnlDrawPanel).getImage()));
	    if(!draw.empty()){
		testMat(draw);
	    }
	}
    }//GEN-LAST:event_btnProcessActionPerformed

    private void testMat(Mat mat){
	Descriptors d = Descriptors.analyzeAll((mat=Filters.applyAll(mat)));
	SimpleChart.drawChart(d.getHorizontalProjection(), 64, pnlHorProjection);
	SimpleChart.drawChart(d.getVerticalProjection(), 64, pnlVerProjection);
	SimpleChart.drawChart(d.getDiagonalProjection(), 100, pnlDiaProjection);
	SimpleChart.drawChart(d.getSectorsCount(), 4096, pnlSecCount);
	String result = Character.toString(nNet.testResult(d));
	txfResult.setText(result); 
//	File file = new File(System.getProperty("user.dir")+"/"+result+".jpg");//TESTING
//	try {
//	    IO.saveMatToImg(mat, file);
//	} catch (IOException ex) {
//	    System.out.println("ERRO");
//	}
    }
    
    private void mncTrainFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mncTrainFolderActionPerformed
        int result = fchFolder.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            trainFolder = fchFolder.getSelectedFile();
            mncTrainFolder.setSelected(true);
	    mniTrain.setEnabled(true);
        }else{
	    trainFolder = null;
	    mncTrainFolder.setSelected(false);
	    mniTrain.setEnabled(false);
	}
    }//GEN-LAST:event_mncTrainFolderActionPerformed

    private void mncValFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mncValFolderActionPerformed
        int result = fchFolder.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION && fchFolder.getSelectedFile().exists()) {
            valFolder = fchFolder.getSelectedFile();
            mncValFolder.setSelected(true);
        }else{
	    valFolder = null;
	    mncValFolder.setSelected(false);
	}
    }//GEN-LAST:event_mncValFolderActionPerformed

    private void mniTrainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTrainActionPerformed
	SwingWorker worker = new SwingWorker() {
	    @Override
	    protected Boolean doInBackground(){
		try {
		    Batcher bt = Batcher.start(trainFolder.getAbsolutePath(), false);
		    BarDialog barT = new BarDialog(bt, "Processando Imagens de Treinamento...");
		    barT.setVisible(true);
		    barT.start();

		    Batcher bv;
		    nNet = new NeuralNetwork();
		    if (valFolder != null) {
			bv = Batcher.start(valFolder.getAbsolutePath(), false);
			BarDialog barV = new BarDialog(bv, "Processando Imagens de Validação...");
			barV.setVisible(true);
			barV.start();
			nNet.train(bt.getDataSet(), bv.getDataSet());
		    } else {
			nNet.train(bt.getDataSet(), null);
		    }
		    
		    BarDialog barN = new BarDialog(nNet, "Treinando Rede...");
		    barN.setVisible(true);
		    barN.start();
		    
		    mniSave.setEnabled(true);
		    mniProcess.setEnabled(true);
		    btnProcess.setEnabled(true);
		    mniTestImg.setEnabled(true);
		} catch (IOException ex) {
		    JOptionPane.showMessageDialog(
			    null,
			    "ERRO: " + ex.getMessage(),
			    "LetterHound",
			    JOptionPane.ERROR_MESSAGE
		    );
		}
		return true;
	    }
	};
	worker.execute();
    }//GEN-LAST:event_mniTrainActionPerformed

    private void mniSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSaveActionPerformed
	fchNetFile.setName("Salvar");
	int result = fchNetFile.showSaveDialog(this);
	if (result == JFileChooser.APPROVE_OPTION && !fchNetFile.getSelectedFile().exists()) {
            File file = fchNetFile.getSelectedFile();
	    if(!file.getName().endsWith("eg")){
		file = new File(file.getAbsolutePath()+".eg");
	    }
            EncogDirectoryPersistence.saveObject(file, nNet.getNet());
	    JOptionPane.showMessageDialog(
		    this,
		    "O arquivo "+file.getName()+" foi salvo com sucesso.",
		    "LetterHound",
		    JOptionPane.PLAIN_MESSAGE
	    );
        }else if(result == JFileChooser.APPROVE_OPTION && fchNetFile.getSelectedFile().exists()){
	    JOptionPane.showMessageDialog(
		    this,
		    "O arquivo "+fchNetFile.getSelectedFile().getName()+" já existe!",
		    "LetterHound",
		    JOptionPane.ERROR_MESSAGE
	    );
	}	
    }//GEN-LAST:event_mniSaveActionPerformed

    private void mnuAboutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuAboutMouseClicked
        new AboutDialog(this).setVisible(true);
    }//GEN-LAST:event_mnuAboutMouseClicked

    private void mnuAboutMenuKeyPressed(javax.swing.event.MenuKeyEvent evt) {//GEN-FIRST:event_mnuAboutMenuKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
	    System.out.println("a");
	    mnuAboutMouseClicked(null);
	}
    }//GEN-LAST:event_mnuAboutMenuKeyPressed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        pnlDiaProjection.removeAll();
	pnlHorProjection.removeAll();
	pnlVerProjection.removeAll();
	pnlSecCount.removeAll();
	txfResult.setText("");
	((DrawPanel)pnlDrawPanel).clear();
	pnlDiaProjection.repaint();
	pnlHorProjection.repaint();
	pnlSecCount.repaint();
	pnlVerProjection.repaint();
    }//GEN-LAST:event_btnClearActionPerformed

    private void mniLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniLoadActionPerformed
	fchNetFile.setName("Abrir");
	int result = fchNetFile.showOpenDialog(this);
	if (result == JFileChooser.APPROVE_OPTION && fchNetFile.getSelectedFile().exists()) {
            File file = fchNetFile.getSelectedFile();

            BasicNetwork network = (BasicNetwork)EncogDirectoryPersistence.loadObject(file);
	    nNet = new NeuralNetwork(network);
	    
	    JOptionPane.showMessageDialog(
		    this,
		    "A rede "+file.getName()+" foi carregada com sucesso.",
		    "LetterHound",
		    JOptionPane.PLAIN_MESSAGE
	    );
	    btnProcess.setEnabled(true);
	    mniSave.setEnabled(true);
	    mniTestImg.setEnabled(true);
        }else if(result == JFileChooser.APPROVE_OPTION && !fchNetFile.getSelectedFile().exists()){
	    JOptionPane.showMessageDialog(
		    this,
		    "O arquivo "+fchNetFile.getSelectedFile().getName()+" não existe!",
		    "LetterHound",
		    JOptionPane.ERROR_MESSAGE
	    );
	}	
    }//GEN-LAST:event_mniLoadActionPerformed

    private void mniProcessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniProcessActionPerformed
        btnProcessActionPerformed(null);
    }//GEN-LAST:event_mniProcessActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        btnClearActionPerformed(null);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_jMenuItem7ActionPerformed
    
    private void mniTestImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTestImgActionPerformed

	int result = fchImgFile.showOpenDialog(this);
	if (result == JFileChooser.APPROVE_OPTION) {
            if(fchImgFile.getSelectedFile().exists()){
		File file = fchImgFile.getSelectedFile();
		btnClearActionPerformed(null);
		testMat(IO.loadImagtoGrayMat(file));
	    }else{
		JOptionPane.showMessageDialog(
		    this,
		    "O arquivo "+fchImgFile.getSelectedFile().getName()+" não existe!",
		    "LetterHound",
		    JOptionPane.ERROR_MESSAGE
		);
	    }
        }	
    }//GEN-LAST:event_mniTestImgActionPerformed

  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnProcess;
    private javax.swing.JFileChooser fchFolder;
    private javax.swing.JFileChooser fchImgFile;
    private javax.swing.JFileChooser fchNetFile;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuBar mnbMenu;
    private javax.swing.JCheckBoxMenuItem mncTrainFolder;
    private javax.swing.JCheckBoxMenuItem mncValFolder;
    private javax.swing.JMenuItem mniLoad;
    private javax.swing.JMenuItem mniProcess;
    private javax.swing.JMenuItem mniSave;
    private javax.swing.JMenuItem mniTestImg;
    private javax.swing.JMenuItem mniTrain;
    private javax.swing.JMenu mnuAbout;
    private javax.swing.JPanel pnlDiaProjection;
    private javax.swing.JPanel pnlDraw;
    private javax.swing.JPanel pnlDrawPanel;
    private javax.swing.JPanel pnlHorProjection;
    private javax.swing.JPanel pnlSecCount;
    private javax.swing.JPanel pnlVerProjection;
    private javax.swing.JPopupMenu.Separator sepNeural1;
    private javax.swing.JPopupMenu.Separator sepNeural2;
    private javax.swing.JTextField txfResult;
    // End of variables declaration//GEN-END:variables


    
}
