package aps.letterhound.view;

import java.awt.Frame;

public class AboutDialog extends javax.swing.JDialog {

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JTextArea jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sobre");
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setResizable(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aps/letterhound/view/img/unip.png"))); // NOI18N

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(240, 240, 240));
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("SOFTWARE PROJETADO PARA A DISCIPLINA APS (ATIVIDADES PRATICAS SUPERVISIONADAS)\n\nCURSO: CIENCIA DA COMPUTACAO\nTURMA: CC2P18 / CC2Q18 UNIP CAMPUS VARGAS\nAMANDA________C448JH-5\nGUILHERME_____C59386-9\nRAPHAEL_______C39FCE-7 \nRUBENS________T49128-2\n \nESTE APLICATIVO FOI DESENVOLVIDO EM LINGUAGEM DE PROGRAMACAO ORIENTADA A OBJETO (\"JAVA\")  UTILIZANDO AS BIBLIOTECAS OPENCV E ENCOG.\nO OBJETIVO FOI UTILIZAR CONHECIMENTOS DE PROCESSAMENTO DE IMAGENS E INTELIGENCIA ARTIFICIAL PARA IMPLEMENTAR UMA REDE NEURAL QUE FOSSE CAPAZ DE RECONHECER LETRAS MANUSCRITAS.\nFOI UTILIZADA UMA REDE PERCEPTRON MULTICAMADAS COM CAMADAS DE 259, 50 e 26 NEURONIOS NAS CAMADAS DE ENTRADA, OCULTA E DE SAIDA, RESPECTIVAMENTE. A REDE UTILIZA FUNCOES DE ATIVACAO ELLIOT NAS DUAS ULTIMAS CAMADAS E O ALGORITMO DE PROPAGACAO RPROP.");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private final Frame parent;
    
    public AboutDialog(Frame parent) {
	super(parent);
	this.parent = parent;
	initComponents();
    }

    @Override
    public void setVisible(boolean b) {
	this.setLocationRelativeTo(parent);
	super.setVisible(b);
    }
 
}
