/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcaro;

import Data.DataType.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author ADMIN
 */
public class ManageForm extends javax.swing.JFrame {

    Vector<String> listUsers; //danh sách hiển thị người đang onl
    Vector<String> listTours; //danh sách hiển thị danh sách các giải đấu
    boolean isRun = true; //xác định thread chạy hay ngưng
    static Map<String, Object> listClientCaro; //thông tin caro nhận đc
    static Map<String, Object> listClientStatus; //trạng thái sẵn sàng chơi game
    static char offset = ' ';
    static Object mPauseLock = new Object();

    /**
     * Creates new form ManageForm
     */
    public ManageForm() {
        initComponents();
        ShowAvatar();
        this.setTitle(Client.cUser.UserName);
        listUsers = new Vector<>();
        listTours = new Vector<>();
        listClientCaro = new HashMap<>();
        listClientStatus = new HashMap<>();
        Thread listener = new Thread(listen);
        listener.start();

    }

    private void ShowAvatar() {
        Icon ii = new ImageIcon(getClass().getResource("images.jpg"));
        jLabelImage.setIcon(ii);
    }
    Runnable listen = new Runnable() {
        @Override
        public void run() {
            while (isRun) {
                try {
                    offset = (char) Client.GetObj();
                    System.out.print(offset);
                    switch (offset) {
                        //có 1 người chơi thoát
                        case '0':
                            listUsers.remove(Client.GetMsg());
                            lUserOnline.setListData(listUsers);
                            break;
                        //nhận danh sách người onl
                        case '1':
                            listUsers.add(Client.GetMsg());
                            lUserOnline.setListData(listUsers);
                            break;
                        //nhận danh sách các giải đấu
                        case '2':
                            listTours = new Vector<>((List<String>) Client.GetObj());
                            lTournament.setListData(listTours);
                            break;
                        //nhận được lời mời
                        case '3':
                            String userName = Client.GetMsg();
                            int result = JOptionPane.showConfirmDialog(ManageForm.this, userName + " invited you for a game",
                                    "Invite", JOptionPane.YES_NO_OPTION);
                            Answer answer = new Answer(userName, result);
                            Client.SendObj('2');
                            Client.SendObj(answer);
                            //trả lời yes thì hiện form chơi game
                            if (result == JOptionPane.YES_OPTION) {
                                listClientCaro.put(userName, new Caro(null, null, 'E', 0, 0));
                                listClientStatus.put(userName, "NULL");
                                new PlayGameForm(userName, false).setVisible(true);
                                ManageForm.this.dispose();
                            }
                            break;
                        //nhận kết quả lời mời
                        case '4':
                            answer = (Answer) Client.GetObj();
                            if (answer.Answer == JOptionPane.YES_OPTION) {
                                listClientCaro.put(answer.UserName, new Caro(null, null, 'E', 0, 0));
                                listClientStatus.put(answer.UserName, "NULL");
                                new PlayGameForm(answer.UserName, true).setVisible(true);
                                //ManageForm.this.dispose();
                            } else {
                                JOptionPane.showMessageDialog(ManageForm.this, answer.UserName + " rejected your invitation",
                                        "Rejected", JOptionPane.WARNING_MESSAGE);
                            }
                            break;
                        //nhận lời chat
                        case '5':
                            taChatRoom.append(Client.GetMsg() + "\n");
                            break;
                        //nhận kết quả tham gia giải đấu
                        case '6':
                            String msg = Client.GetMsg();
                            if (msg.equals("score")) {
                                JOptionPane.showMessageDialog(ManageForm.this, " your score's too low",
                                        "Rejected", JOptionPane.WARNING_MESSAGE);
                            } else if (msg.equals("success")) {
                                new WaitingForm().setVisible(true);
                                //isRun = false;
                                //ManageForm.this.dispose();
                            }
                            break;
                        //thông tin caro
                        case 'X':
                            Caro caro = (Caro) Client.GetObj();
                            listClientCaro.put(caro.NameEnemy, caro);
                            System.out.print(caro.UserName + "," + caro.NameEnemy + "," + caro.i + caro.j);
                            break;
                        //Sẵn Sàng Đánh Caro
                        case 'K':
                            msg = Client.GetMsg();
                            listClientStatus.put(msg, "OK");
                            break;
                        default:
                            WaitingForm.resume();
                            synchronized (mPauseLock) {
                                mPauseLock.wait();
                            }
                            break;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ManageForm.this, "Can't connect to server",
                            "Can't connect", JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
            }
        }
    };

    public static void resume() {
        synchronized (mPauseLock) {
            mPauseLock.notifyAll();
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

        btnInvite = new javax.swing.JButton();
        btnJoinTour = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lUserOnline = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lTournament = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        taChat = new javax.swing.JTextArea();
        btnSend = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taChatRoom = new javax.swing.JTextArea();
        jLabelImage = new javax.swing.JLabel();
        btnBrowseImage = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btnInvite.setForeground(new java.awt.Color(0, 204, 255));
        btnInvite.setText("Invite");
        btnInvite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInviteActionPerformed(evt);
            }
        });

        btnJoinTour.setForeground(new java.awt.Color(0, 204, 255));
        btnJoinTour.setText("<html>Join <br>Tournament<html>");
        btnJoinTour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJoinTourActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Users Online"));

        lUserOnline.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(lUserOnline);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tournament"));

        lTournament.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(lTournament);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        taChat.setColumns(20);
        taChat.setRows(5);
        jScrollPane4.setViewportView(taChat);

        btnSend.setForeground(new java.awt.Color(0, 204, 255));
        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Chat Room"));

        taChatRoom.setEditable(false);
        taChatRoom.setColumns(20);
        taChatRoom.setRows(5);
        jScrollPane3.setViewportView(taChatRoom);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabelImage.setText("jLabel1");

        btnBrowseImage.setForeground(new java.awt.Color(0, 204, 255));
        btnBrowseImage.setText("Choose Avatar");
        btnBrowseImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseImageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnSend)
                        .addGap(81, 201, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnInvite)
                            .addComponent(btnJoinTour, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBrowseImage, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(btnInvite)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnJoinTour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBrowseImage)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInviteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInviteActionPerformed
        // TODO add your handling code here:
        try {
            String userName = (String) lUserOnline.getSelectedValue();
            Client.SendObj('1');
            Client.SendMsg(userName);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(ManageForm.this, "Can't connect to server",
                    "Can't connect", JOptionPane.WARNING_MESSAGE);;
        }
    }//GEN-LAST:event_btnInviteActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            // TODO add your handling code here:
            //thông báo tắt client
            Client.SendObj('0');
        } catch (IOException ex) {
            Logger.getLogger(ManageForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        try {
            // TODO add your handling code here:
            Client.SendObj('3');
            Client.SendMsg(taChat.getText());
            taChat.setText("");
        } catch (IOException ex) {
            Logger.getLogger(ManageForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSendActionPerformed

    private void btnBrowseImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseImageActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & GIF Images", "jpg", "gif");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String temp = chooser.getSelectedFile().toString();
            Icon ii = new ImageIcon(temp);
            jLabelImage.setIcon(ii);
        }

    }//GEN-LAST:event_btnBrowseImageActionPerformed

    private void btnJoinTourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJoinTourActionPerformed
        try {
            // TODO add your handling code here:
            String tourName = (String) lTournament.getSelectedValue();
            Client.SendObj('4');
            Client.SendMsg(tourName);
        } catch (IOException ex) {
            Logger.getLogger(ManageForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnJoinTourActionPerformed

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
            java.util.logging.Logger.getLogger(ManageForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowseImage;
    private javax.swing.JButton btnInvite;
    private javax.swing.JButton btnJoinTour;
    private javax.swing.JButton btnSend;
    private javax.swing.JLabel jLabelImage;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JList lTournament;
    private javax.swing.JList lUserOnline;
    private javax.swing.JTextArea taChat;
    private javax.swing.JTextArea taChatRoom;
    // End of variables declaration//GEN-END:variables
}
