/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcaro;

import Data.DataType;
import Data.DataType.Caro;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class WaitingForm extends javax.swing.JFrame {

    public String TourName;
    public boolean isRun = true;

    /**
     * Creates new form WaitingForm
     */
    public WaitingForm() {
        initComponents();
        this.setTitle(Client.cUser.UserName);
        Thread listener = new Thread(listen);
        listener.start();
    }

    public WaitingForm(String tourName) {
        initComponents();
        this.setTitle(Client.cUser.UserName);
        TourName = tourName;
        lbTourName.setText(tourName);
        Thread listener = new Thread(listen);
        listener.start();
    }
    Runnable listen = new Runnable() {
        @Override
        public void run() {
            while (isRun) {
                try {
                    int offset = ManageForm.offset;
                    switch (offset) {
                        //nhận danh sách người chơi
                        case 7:
                            int nRow = 0;
                            DefaultTableModel model = (DefaultTableModel) tbUsers.getModel();
                            model.setRowCount(0);
                            for (int i = 0; i < ManageForm.userNames.size(); i += 2) {
                                model.addRow(new Object[]{null, null, null});
                                tbUsers.setValueAt(nRow + 1, nRow, 0);
                                tbUsers.setValueAt(ManageForm.userNames.get(i), nRow, 1);
                                if (i + 1 < ManageForm.userNames.size()) {
                                    tbUsers.setValueAt(ManageForm.userNames.get(i + 1), nRow, 2);
                                }
                                nRow++;
                            }
                            break;
                        //nhận báo hiệu bắt đầu giải đấu và đấu với 1 người chỉ định
                        case 8:                            
                            WaitingForm.this.setVisible(false);
                            isRun = false;
                            break;
                        //nhận báo hiệu xóa giải đấu
                        case 9:
                            isRun = false;
                            ManageForm.resume();
                            WaitingForm.this.setVisible(false);
                            break;
                        //nhận báo hiệu thằng giải đấu
                        case 10:
                            String msg = Client.GetMsg();
                            new WinForm(TourName, msg).setVisible(true);
                            
                            WaitingForm.this.setVisible(false);
                            ManageForm.resume();
                            isRun = false;
                            break;
                    }

                } catch (Exception ex) {
//                    Logger.getLogger(WaitingForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbTitle = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbUsers = new javax.swing.JTable();
        lbTourName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        lbTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbTitle.setText("Please wait until enough people");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Users participated:");

        tbUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cặp đấu", "Player 1", "Player 2"
            }
        ));
        jScrollPane1.setViewportView(tbUsers);
        tbUsers.getColumnModel().getColumn(0).setResizable(false);
        tbUsers.getColumnModel().getColumn(1).setResizable(false);
        tbUsers.getColumnModel().getColumn(2).setResizable(false);

        lbTourName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbTourName.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lbTitle)
                                .addGap(66, 66, 66))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(lbTourName)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTourName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            // TODO add your handling code here:
            //gửi báo hiệu thoát giải đấu
            Client.SendObj(5);
        } catch (IOException ex) {
            Logger.getLogger(WaitingForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(WaitingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WaitingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WaitingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WaitingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WaitingForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbTourName;
    private javax.swing.JTable tbUsers;
    // End of variables declaration//GEN-END:variables
}
