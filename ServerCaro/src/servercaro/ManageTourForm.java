/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servercaro;

import DTO.Tournament;
import DTO.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author hieu
 */
public class ManageTourForm extends javax.swing.JFrame {
    
    boolean isRun = true;
    /**
     * Creates new form ManageTourForm
     */
    public ManageTourForm() {
        initComponents();
        ListAllUser();
        ShowAllUser();
        Thread listener = new Thread(listen);
        listener.start();
    }
    Runnable listen = new Runnable() {
        @Override
        public void run() {
            while (isRun) {
                if(jcomboGiaiDau.getSelectedIndex() != -1)
                    ShowTourInfo();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ManageTourForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    private void ListAllUser() {
        jcomboGiaiDau.removeAll();
        for (int i = 0; i < Server.tournaments.size(); i++) {
            jcomboGiaiDau.addItem(Server.tournaments.get(i).name);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jcomboGiaiDau = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlistUser = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jcomboAddUser = new javax.swing.JComboBox();
        btnAddUser = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnAddTour = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Choose Tournament");

        jlistUser.setEditable(false);
        jlistUser.setColumns(20);
        jlistUser.setRows(5);
        jScrollPane1.setViewportView(jlistUser);

        jLabel2.setText("All User");

        btnAddUser.setText("Add");
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserUserActionPerformed(evt);
            }
        });

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnAddTour.setText("Add");
        btnAddTour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTourActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcomboAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 149, Short.MAX_VALUE)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(138, 138, 138))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcomboGiaiDau, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAddTour)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcomboGiaiDau, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddTour, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcomboAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExit)
                    .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ShowAllUser() {
        jcomboAddUser.removeAll();
        for (int i = 0; i < Server.allUsers.size(); i++) {
            jcomboAddUser.addItem(Server.allUsers.get(i).UserName);
        }
    }

    private void ShowTourInfo() {
        Tournament tour;

        String tengiaidau = jcomboGiaiDau.getSelectedItem().toString();
        String setUser = "List of users in tournament '" + tengiaidau + "' : " + "\n";

        tour = Server.getTour(tengiaidau);

        for (int i = 0; i < tour.users.size(); i++) {
            String username = tour.users.get(i).UserName;
            setUser += (i + 1) + "\t";
            setUser += username;
            setUser += "\n";
        }

        jlistUser.setText(setUser);
    }

    private void btnAddUserUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserUserActionPerformed
        Tournament tour = new Tournament();
        boolean result = false;

        String userName = jcomboAddUser.getSelectedItem().toString();
        String tengiaidau = jcomboGiaiDau.getSelectedItem().toString();

        if (userName != null && tengiaidau != null) {
            tour = Server.getTour(tengiaidau);

            result = tour.isExistUser(userName);
            if (!result) {
                User user = Server.getUser(userName);
                tour.users.add(user);
                JOptionPane.showMessageDialog(null, "Insert Successfully");
            } else {
                JOptionPane.showMessageDialog(null, "The user is already existed");
            }
        }
    }//GEN-LAST:event_btnAddUserUserActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        isRun = false;
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnAddTourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTourActionPerformed
        // TODO add your handling code here:
        new CreateTourForm().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnAddTourActionPerformed

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
            java.util.logging.Logger.getLogger(ManageTourForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageTourForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageTourForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageTourForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new ManageTourForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddTour;
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnExit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox jcomboAddUser;
    private javax.swing.JComboBox jcomboGiaiDau;
    private javax.swing.JTextArea jlistUser;
    // End of variables declaration//GEN-END:variables
}
