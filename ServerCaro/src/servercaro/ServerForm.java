/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servercaro;

import DAO.UserDAO;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class ServerForm extends javax.swing.JFrame {

    String strNotify = "";
    Thread getConnect;
    private boolean isRun = false;

    /**
     * Creates new form ServerForm
     */
    public ServerForm() {
        centreWindow(this);
        initComponents();

        try {
            Server.sSocket = new ServerSocket(1234);
        } catch (IOException ex) {
            Logger.getLogger(ServerForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 3.4);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 3.3);
        frame.setLocation(x, y);
    }
    Runnable getConnection = new Runnable() {
        @Override
        public void run() {
            while (isRun) {
                try {
                    Socket client = Server.sSocket.accept();
                    strNotify += "Receive a client\n";
                    taStatus.setText(strNotify);
                    //lấy thông tin người đăng nhập
                    DataInputStream in = new DataInputStream(client.getInputStream());
                    String userName = in.readUTF();
                    in = new DataInputStream(client.getInputStream());
                    String password = in.readUTF();
                    DataOutputStream out = new DataOutputStream(client.getOutputStream());
                    int idx = Server.getIndex(userName, password);
                    if (idx != -1) {
                        Client exist = Server.getClient(userName);
                        if (exist == null) {
                            Server.cSockets.add(new Client(client, Server.allUsers.get(idx)));
                            Server.cSockets.get(Server.cSockets.size() - 1).start();
                            out.writeUTF("success");
                            for (int i = 0; i < Server.cSockets.size() - 1; i++) {
                                //gửi thông tin người mới đăng nhập vào các người đã đăng nhập
                                Server.cSockets.get(i).SendObj(1);
                                Server.cSockets.get(i).SendMsg(userName);
                                //gửi thông tin những người đã đăng nhấp đến người mới đăng nhập
                                Server.cSockets.get(Server.cSockets.size() - 1).SendObj(1);
                                Server.cSockets.get(Server.cSockets.size() - 1).SendMsg(Server.cSockets.get(i).getUserName());
                            }

            
                            Server.cSockets.get(Server.cSockets.size()-1).SendObj(-5);
                            Server.cSockets.get(Server.cSockets.size()-1).SendMsg(Server.cSockets.get(Server.cSockets.size() - 1).cUser.UserName+":"+Server.cSockets.get(Server.cSockets.size() - 1).cUser.Score);
                            List<String> msg = Server.getAllToursStatus();
                            Server.cSockets.get(Server.cSockets.size() - 1).SendObj(2);
                            Server.cSockets.get(Server.cSockets.size() - 1).SendObj(msg);

                        } else {
                            out.writeUTF("fail");
                        }
                    } else {
                        out.writeUTF("fail");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ServerForm.class.getName()).log(Level.SEVERE, null, ex);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        taStatus = new javax.swing.JTextArea();
        btnStart = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnTour = new javax.swing.JButton();
        btnUserInfo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Welcome You to Caro Game");
        setBackground(new java.awt.Color(204, 255, 204));

        taStatus.setEditable(false);
        taStatus.setColumns(20);
        taStatus.setRows(5);
        jScrollPane1.setViewportView(taStatus);

        btnStart.setForeground(new java.awt.Color(0, 204, 255));
        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        btnStop.setForeground(new java.awt.Color(0, 204, 255));
        btnStop.setText("Stop");
        btnStop.setEnabled(false);
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });

        btnExit.setForeground(new java.awt.Color(255, 0, 51));
        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnTour.setForeground(new java.awt.Color(0, 204, 255));
        btnTour.setText("Tournament");
        btnTour.setEnabled(false);
        btnTour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTourActionPerformed(evt);
            }
        });

        btnUserInfo.setForeground(new java.awt.Color(0, 204, 255));
        btnUserInfo.setText("User's Info");
        btnUserInfo.setEnabled(false);
        btnUserInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 190, Short.MAX_VALUE)
                        .addComponent(btnUserInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnStart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTour)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStop)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExit)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStart)
                    .addComponent(btnStop)
                    .addComponent(btnExit)
                    .addComponent(btnTour)
                    .addComponent(btnUserInfo))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        // TODO add your handling code here:
        Server.allUsers = UserDAO.GetList();
        if (Server.allUsers == null) {
            strNotify += "Can't connect to database\n";
            taStatus.setText(strNotify);
        } else {
            strNotify += "Connected to database\n";
            strNotify += "Number of users: " + Server.allUsers.size() + "\n";
            strNotify += "Started listening connection\n";
            taStatus.setText(strNotify);
            isRun = true;
            getConnect = new Thread(getConnection);
            getConnect.start();
            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
            btnTour.setEnabled(true);
            btnUserInfo.setEnabled(true);
        }
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        // TODO add your handling code here:
        isRun = false;
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
        btnTour.setEnabled(false);
        btnUserInfo.setEnabled(false);
    }//GEN-LAST:event_btnStopActionPerformed

    private void btnTourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTourActionPerformed
        ManageTourForm a = new ManageTourForm();
        a.setVisible(true);
    }//GEN-LAST:event_btnTourActionPerformed

    private void btnUserInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserInfoActionPerformed
        UserInfo a = new UserInfo();
        a.setVisible(true);
    }//GEN-LAST:event_btnUserInfoActionPerformed

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
            java.util.logging.Logger.getLogger(ServerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStop;
    private javax.swing.JButton btnTour;
    private javax.swing.JButton btnUserInfo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea taStatus;
    // End of variables declaration//GEN-END:variables
}
