/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servercaro;

import DTO.Tournament;
import DTO.User;
import Data.DataType;
import Data.DataType.Answer;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author hieu
 */
public class ManageTourForm extends javax.swing.JFrame {

    boolean isRun = true;
    Tournament Tour = null;

    /**
     * Creates new form ManageTourForm
     */
    public ManageTourForm() {
        centreWindow(this);
        initComponents();
        ListAllUser();
        ShowAllUser();
        Thread listener = new Thread(listen);
        listener.start();
    }

    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 3.4);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 3.3);
        frame.setLocation(x, y);
    }
    Runnable listen = new Runnable() {
        @Override
        public void run() {
            while (isRun) {
                if (jcomboGiaiDau.getSelectedIndex() != -1) {
                    ShowTourInfo();
                }
                if(Tour != null && Tour.nPlayer > 1){
                    if(Tour.users.size()==Tour.nPlayer){
                        btnStart.doClick();
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ManageTourForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    private void ListAllUser() {
        jcomboGiaiDau.removeAll();
        for (int i = 0; i < Server.tournaments.size(); i++) {
            if (!Server.tournaments.get(i).isStart) {
                jcomboGiaiDau.addItem(Server.tournaments.get(i).name);
            }
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
        btnStart = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Choose Tournament");

        jlistUser.setEditable(false);
        jlistUser.setColumns(20);
        jlistUser.setRows(5);
        jScrollPane1.setViewportView(jlistUser);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("All User");

        btnAddUser.setForeground(new java.awt.Color(0, 204, 255));
        btnAddUser.setText("Add");
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserUserActionPerformed(evt);
            }
        });

        btnExit.setForeground(new java.awt.Color(255, 51, 51));
        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnAddTour.setForeground(new java.awt.Color(0, 204, 255));
        btnAddTour.setText("Add");
        btnAddTour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTourActionPerformed(evt);
            }
        });

        btnStart.setForeground(new java.awt.Color(0, 204, 255));
        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        btnDelete.setForeground(new java.awt.Color(0, 204, 255));
        btnDelete.setText("Del");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnEdit.setForeground(new java.awt.Color(0, 204, 255));
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        jButton1.setText("Ghi Xuống");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jcomboGiaiDau, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAddTour)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEdit)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(btnStart))
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcomboGiaiDau, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddTour, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)))
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
        String tengiaidau = jcomboGiaiDau.getSelectedItem().toString();
        String setUser = "List of users in tournament '" + tengiaidau + "' : " + "\n";

        Tour = Server.getTour(tengiaidau);

        for (int i = 0; i < Tour.users.size(); i++) {
            String username = Tour.users.get(i).UserName;
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

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        // TODO add your handling code here:
        if (Tour.users.size() < Tour.nPlayer) {
            JOptionPane.showMessageDialog(this, "not enough people",
                    "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            Tour.nPlayer = Tour.nPlayer / 2;
            jcomboGiaiDau.removeItem(Tour.name);
            for (int i = 0; i < Tour.users.size(); i += 2) {
                try {
                    Client client = Server.getClient(Tour.users.get(i).UserName);
                    client.SendObj(8);
                    Answer answer = new Answer(Tour.users.get(i+1).UserName, JOptionPane.YES_OPTION, Tour.name, false);
                    client.SendObj(answer);
                    client = Server.getClient(Tour.users.get(i + 1).UserName);
                    client.SendObj(8);
                    answer = new Answer(Tour.users.get(i).UserName, JOptionPane.YES_OPTION, Tour.name, true);
                    client.SendObj(answer);
                } catch (IOException ex) {
                    Logger.getLogger(ManageTourForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Tour.users.clear();
        }
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if (jcomboGiaiDau.getSelectedIndex() != -1) {
            for (int i = 0; i < Server.cSockets.size(); i++) {
                try {
                    Server.cSockets.get(i).SendObj(2);
                    Server.cSockets.get(i).SendObj(Server.getAllToursStatus());
                } catch (IOException ex) {
                    Logger.getLogger(ManageTourForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            jcomboGiaiDau.removeItem(Tour.name);
            Server.tournaments.remove(Tour);
        } else {
            JOptionPane.showMessageDialog(this, "You haven't selected a tournament",
                    "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        if (jcomboGiaiDau.getSelectedIndex() != -1) {
            new EditTourForm(Tour).setVisible(true);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "You haven't selected a tournament",
                    "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Tournament tour = Server.getTour((String)jcomboGiaiDau.getSelectedItem());
        for (int i = 0; i < tour.TourScore.size(); i++) {
            System.out.println(tour.TourScore.get(Server.allUsers.get(i).UserName));
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox jcomboAddUser;
    private javax.swing.JComboBox jcomboGiaiDau;
    private javax.swing.JTextArea jlistUser;
    // End of variables declaration//GEN-END:variables
}
