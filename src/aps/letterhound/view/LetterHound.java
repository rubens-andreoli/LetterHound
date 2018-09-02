package aps.letterhound.view;

import aps.letterhound.core.Batcher;
import aps.letterhound.core.Descriptors;
import aps.letterhound.core.Filters;
import aps.letterhound.core.IO;
import static aps.letterhound.core.IO.SUPPORTED_IMG_EXTENSIONS;
import aps.letterhound.core.NeuralNetwork;
import java.awt.Component;
import java.awt.Toolkit;
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
    private static final long serialVersionUID = 2L;
    
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
        btnSave = new javax.swing.JButton();
        mnbMenu = new javax.swing.JMenuBar();
        mnuEditor = new javax.swing.JMenu();
        mniClear = new javax.swing.JMenuItem();
        mniProcess = new javax.swing.JMenuItem();
        sepEditor1 = new javax.swing.JPopupMenu.Separator();
        mniExit = new javax.swing.JMenuItem();
        mnuNeural = new javax.swing.JMenu();
        mncTrainFolder = new javax.swing.JCheckBoxMenuItem();
        mncValFolder = new javax.swing.JCheckBoxMenuItem();
        sepNeural1 = new javax.swing.JPopupMenu.Separator();
        mniTrain = new javax.swing.JMenuItem();
        mniTrainD = new javax.swing.JMenuItem();
        sepNeural2 = new javax.swing.JPopupMenu.Separator();
        mniTestImg = new javax.swing.JMenuItem();
        sepNeural3 = new javax.swing.JPopupMenu.Separator();
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
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/letterhound.png")));
        setResizable(false);

        pnlDraw.setBackground(new java.awt.Color(255, 255, 255));
        pnlDraw.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlDraw.setMaximumSize(new java.awt.Dimension(225, 225));
        pnlDraw.setMinimumSize(new java.awt.Dimension(225, 225));

        pnlDrawPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlDrawPanelMouseDragged(evt);
            }
        });

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
        btnProcess.setToolTipText("Processa desenho pela rede neural.");
        btnProcess.setEnabled(false);
        btnProcess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcessActionPerformed(evt);
            }
        });

        txfResult.setEditable(false);
        txfResult.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txfResult.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txfResult.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txfResultKeyTyped(evt);
            }
        });

        btnClear.setMnemonic('L');
        btnClear.setText("Limpar");
        btnClear.setToolTipText("Limpa painel de desenho.");
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

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aps/letterhound/view/img/save.png"))); // NOI18N
        btnSave.setToolTipText("<html>Salva desenhos para pasta de treinamento.<br>Utiliza resposta do processamento como<br>nome do aquivo, corrija-a caso necessário.</html>");
        btnSave.setEnabled(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        mnuEditor.setMnemonic('E');
        mnuEditor.setText("Editor");

        mniClear.setMnemonic('L');
        mniClear.setText("Limpar");
        mniClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniClearActionPerformed(evt);
            }
        });
        mnuEditor.add(mniClear);

        mniProcess.setMnemonic('P');
        mniProcess.setText("Processar");
        mniProcess.setEnabled(false);
        mniProcess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniProcessActionPerformed(evt);
            }
        });
        mnuEditor.add(mniProcess);
        mnuEditor.add(sepEditor1);

        mniExit.setMnemonic('S');
        mniExit.setText("Sair");
        mniExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniExitActionPerformed(evt);
            }
        });
        mnuEditor.add(mniExit);

        mnbMenu.add(mnuEditor);

        mnuNeural.setMnemonic('N');
        mnuNeural.setText("Rede Neural");

        mncTrainFolder.setMnemonic('T');
        mncTrainFolder.setText("Arquivos de Treinamento");
        mncTrainFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mncTrainFolderActionPerformed(evt);
            }
        });
        mnuNeural.add(mncTrainFolder);

        mncValFolder.setMnemonic('V');
        mncValFolder.setText("Arquivos de Validação");
        mncValFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mncValFolderActionPerformed(evt);
            }
        });
        mnuNeural.add(mncValFolder);
        mnuNeural.add(sepNeural1);

        mniTrain.setText("Treinar");
        mniTrain.setEnabled(false);
        mniTrain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTrainActionPerformed(evt);
            }
        });
        mnuNeural.add(mniTrain);

        mniTrainD.setText("Treinar (dump)");
        mniTrainD.setEnabled(false);
        mniTrainD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTrainDActionPerformed(evt);
            }
        });
        mnuNeural.add(mniTrainD);
        mnuNeural.add(sepNeural2);

        mniTestImg.setText("Testar Imagem");
        mniTestImg.setEnabled(false);
        mniTestImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTestImgActionPerformed(evt);
            }
        });
        mnuNeural.add(mniTestImg);
        mnuNeural.add(sepNeural3);

        mniSave.setText("Salvar");
        mniSave.setEnabled(false);
        mniSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSaveActionPerformed(evt);
            }
        });
        mnuNeural.add(mniSave);

        mniLoad.setText("Abrir");
        mniLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniLoadActionPerformed(evt);
            }
        });
        mnuNeural.add(mniLoad);

        mnbMenu.add(mnuNeural);

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txfResult, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txfResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnProcess, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(pnlDraw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private File trainFolder;
    private File valFolder;
    private NeuralNetwork nNet;
    private IO trainIO;
    private IO valIO;
    private Mat curMat;
    private Component mainFrame;
//  private boolean isDraw;
   
    public LetterHound() {
	initComponents();
	this.mainFrame = this;
    }
    
    private void train(boolean isDump){
	if(trainIO == null) return; //shouldn't happen
	SwingWorker worker = new SwingWorker() {
	    @Override
	    protected Boolean doInBackground() {
		try {
		    Batcher bt = new Batcher(trainIO, isDump);
		    bt.start();
		    BarDialog barT = new BarDialog(mainFrame, bt, "Processando Imagens de Treinamento...");
		    barT.start();
		    
		    Batcher bv;
		    nNet = new NeuralNetwork();
		    if (valIO != null) {
			bv = new Batcher(valIO, isDump);
			bv.start();
			BarDialog barV = new BarDialog(mainFrame, bv, "Processando Imagens de Validação...");
			barV.start();
			nNet.train(bt.getDataSet(), bv.getDataSet());
		    } else {
			nNet.train(bt.getDataSet(), null);
		    }
		    
		    BarDialog barN = new BarDialog(mainFrame, nNet, "Treinando Rede...");
		    barN.start();
		    
		    mniSave.setEnabled(true);
//		    mniProcess.setEnabled(true);
//		    btnProcess.setEnabled(true);
		    mniTestImg.setEnabled(true);
		    return true;
		} catch (IOException ex) {
		    JOptionPane.showMessageDialog(
			null,
			"Erro durante o processamento!\n"+ex.getMessage(),
			"LetterHound",
			JOptionPane.ERROR_MESSAGE
		    );
		    return false;
		}
	    }
	};
	worker.execute();
    }
    
    private void processDraw() {
	if (nNet == null) return;  //shouldn't happen
	Mat draw = LetterHound.img2Mat((((DrawPanel) pnlDrawPanel).getImage()));
//	if (isDraw) {
	    testMat(draw);
	    btnSave.setEnabled(true);
	    txfResult.setEditable(true);
//	}
    }
    
    private void testMat(Mat mat){
	Descriptors d = Descriptors.analyzeAll((curMat=Filters.applyAll(mat)));
	SimpleChart.drawChart(d.getHorizontalProjection(), 64, pnlHorProjection);
	SimpleChart.drawChart(d.getVerticalProjection(), 64, pnlVerProjection);
	SimpleChart.drawChart(d.getDiagonalProjection(), 100, pnlDiaProjection);
	SimpleChart.drawChart(d.getSectorsCount(), 1024, pnlSecCount);
	String result = Character.toString(nNet.testResult(d));
	txfResult.setText(result); 
    }
    
    private void clear() {
	pnlDiaProjection.removeAll();
	pnlHorProjection.removeAll();
	pnlVerProjection.removeAll();
	pnlSecCount.removeAll();
	txfResult.setText("");
	btnSave.setEnabled(false);
	txfResult.setEditable(false);
//	isDraw=false;
	btnProcess.setEnabled(false);
	mniProcess.setEnabled(false);
	((DrawPanel)pnlDrawPanel).clear();
	pnlDiaProjection.repaint();
	pnlHorProjection.repaint();
	pnlSecCount.repaint();
	pnlVerProjection.repaint();
    }
    
    private void about(){
	new AboutDialog(this).setVisible(true);
    }
    
    private void btnProcessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcessActionPerformed
	this.processDraw();
    }//GEN-LAST:event_btnProcessActionPerformed
 
    private void mncTrainFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mncTrainFolderActionPerformed
        int result = fchFolder.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            trainFolder = fchFolder.getSelectedFile();
	    try {
		trainIO = IO.getIO(trainFolder);
		mncTrainFolder.setSelected(true);
		mniTrain.setEnabled(true);
		mniTrainD.setEnabled(true);
	    } catch (IOException ex) {
		JOptionPane.showMessageDialog(
			this,
			"Erro ao abrir a pasta!\n"+ex.getMessage(),
			"LetterHound",
			JOptionPane.ERROR_MESSAGE
		);
	    }
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
	    try {
		valIO = IO.getIO(valFolder);
		mncValFolder.setSelected(true);
	    } catch (IOException ex) {
		JOptionPane.showMessageDialog(
			this,
			"Erro ao abrir a pasta!\n"+ex.getMessage(),
			"LetterHound",
			JOptionPane.ERROR_MESSAGE
		);
	    }
        }else{
	    valFolder = null;
	    mncValFolder.setSelected(false);
	}
    }//GEN-LAST:event_mncValFolderActionPerformed

    private void mniTrainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTrainActionPerformed
	this.train(false);
    }//GEN-LAST:event_mniTrainActionPerformed

    private void mniSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSaveActionPerformed
	fchNetFile.setDialogTitle("Salvar");
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
        this.about();
    }//GEN-LAST:event_mnuAboutMouseClicked

    private void mnuAboutMenuKeyPressed(javax.swing.event.MenuKeyEvent evt) {//GEN-FIRST:event_mnuAboutMenuKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
	    this.about();
	}
    }//GEN-LAST:event_mnuAboutMenuKeyPressed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        this.clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void mniProcessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniProcessActionPerformed
       this.processDraw();
    }//GEN-LAST:event_mniProcessActionPerformed

    private void mniClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniClearActionPerformed
        this.clear();
    }//GEN-LAST:event_mniClearActionPerformed

    private void mniExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniExitActionPerformed
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_mniExitActionPerformed
    
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

    private void mniLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniLoadActionPerformed
        fchNetFile.setDialogTitle("Abrir");
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
//          btnProcess.setEnabled(true);
//	    mniProcess.setEnabled(true);
            mniSave.setEnabled(true);
            mniTestImg.setEnabled(true);
            btnSave.setEnabled(false);
        }else if(result == JFileChooser.APPROVE_OPTION && !fchNetFile.getSelectedFile().exists()){
            JOptionPane.showMessageDialog(
                this,
                "O arquivo "+fchNetFile.getSelectedFile().getName()+" não existe!",
                "LetterHound",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_mniLoadActionPerformed

    private void mniTrainDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTrainDActionPerformed
        this.train(true);
    }//GEN-LAST:event_mniTrainDActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(curMat==null) return; //shoudn't happen
	if(trainIO!=null){
	    if(!txfResult.getText().isEmpty()){ 
		try {
		    trainIO.saveMatToImg(curMat, txfResult.getText()/*.toLowerCase()*/.charAt(0));
		    btnSave.setEnabled(false);
		} catch (IOException ex) {
		    JOptionPane.showMessageDialog(
			this,
			"A imagem não pode ser salva!\n"+ex.getMessage(),
			"LetterHound",
			JOptionPane.ERROR_MESSAGE
		    );
		}
	    }else{
		JOptionPane.showMessageDialog(
		    this,
		    "A imagem não pode ser salva sem uma resposta da letra correta.",
		    "LetterHound",
		    JOptionPane.INFORMATION_MESSAGE
		);
	    }
	}else{
	    JOptionPane.showMessageDialog(
                this,
                "Selecione uma pasta de treinamento antes de salvar a imagem.",
                "LetterHound",
                JOptionPane.INFORMATION_MESSAGE
            );
	}
    }//GEN-LAST:event_btnSaveActionPerformed

    private void pnlDrawPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDrawPanelMouseDragged
//        if(!isDraw) isDraw = true;
	if(!btnProcess.isEnabled()/* && !mniProcess.isEnabled()*/ && nNet != null){
	    btnProcess.setEnabled(true);
	    mniProcess.setEnabled(true);
	}
    }//GEN-LAST:event_pnlDrawPanelMouseDragged

    private void txfResultKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txfResultKeyTyped
	if(txfResult.getText().length()>0 || !String.valueOf(evt.getKeyChar()).matches("[a-z]")){
	    evt.consume();
	}/*else{
	    evt.setKeyChar(Character.toLowerCase(evt.getKeyChar()));
	}*/
    }//GEN-LAST:event_txfResultKeyTyped

  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnProcess;
    private javax.swing.JButton btnSave;
    private javax.swing.JFileChooser fchFolder;
    private javax.swing.JFileChooser fchImgFile;
    private javax.swing.JFileChooser fchNetFile;
    private javax.swing.JMenuBar mnbMenu;
    private javax.swing.JCheckBoxMenuItem mncTrainFolder;
    private javax.swing.JCheckBoxMenuItem mncValFolder;
    private javax.swing.JMenuItem mniClear;
    private javax.swing.JMenuItem mniExit;
    private javax.swing.JMenuItem mniLoad;
    private javax.swing.JMenuItem mniProcess;
    private javax.swing.JMenuItem mniSave;
    private javax.swing.JMenuItem mniTestImg;
    private javax.swing.JMenuItem mniTrain;
    private javax.swing.JMenuItem mniTrainD;
    private javax.swing.JMenu mnuAbout;
    private javax.swing.JMenu mnuEditor;
    private javax.swing.JMenu mnuNeural;
    private javax.swing.JPanel pnlDiaProjection;
    private javax.swing.JPanel pnlDraw;
    private javax.swing.JPanel pnlDrawPanel;
    private javax.swing.JPanel pnlHorProjection;
    private javax.swing.JPanel pnlSecCount;
    private javax.swing.JPanel pnlVerProjection;
    private javax.swing.JPopupMenu.Separator sepEditor1;
    private javax.swing.JPopupMenu.Separator sepNeural1;
    private javax.swing.JPopupMenu.Separator sepNeural2;
    private javax.swing.JPopupMenu.Separator sepNeural3;
    private javax.swing.JTextField txfResult;
    // End of variables declaration//GEN-END:variables

}
