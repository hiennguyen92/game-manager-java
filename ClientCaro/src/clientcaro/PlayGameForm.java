
package clientcaro;

import Data.DataType;
import Data.DataType.Caro;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Nguyen Hien
 */
public class PlayGameForm extends javax.swing.JFrame implements ActionListener {

    /**
     * Creates new form CaroGUI
     */
   // private Socket chatDirectSocket = null;
    //private PrintWriter out = null;
    //private BufferedReader in = null;
    //private String User;
    //private String Type;
    private char PlayerType;
    private String nameDoiThu; 
    boolean isRun = true;
    private Square[][] arrSquare;
    private final int Rows = 20, Cols = 20;
    private  boolean Winner=false,EnemyWinner=false;
    private int time = 60;
//    private Timer timer = new Timer(1000, new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            lblTime.setText(time + "");
//            --time;
//            if (EnemyWinner == true) {
//                JOptionPane.showMessageDialog(null, "Ban Da Thu", "Ket Qua", JOptionPane.PLAIN_MESSAGE);
//                timer.stop();
//            }
//            if (time == 0) {
//                time = 60;
//            }
//        }
//    });
    

    public PlayGameForm(String UserDoiThu,boolean isInviter) {

        initComponents();
        
        jPGame.setLayout(new GridLayout(Rows, Cols));
        arrSquare = new Square[Rows][Cols];
        btnSend.addActionListener(this);
        chatField.addActionListener(this);
        nameDoiThu = UserDoiThu;
        setTitle(Client.cUser.UserName+"==>>"+UserDoiThu);
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++) {
                final int a = i, b = j;
                arrSquare[a][b] = new Square(false);//o ban co
                arrSquare[a][b].setBackground(Color.ORANGE);
                arrSquare[a][b].setValue('-');
                arrSquare[a][b].setRow(i);
                arrSquare[a][b].setCol(j);
                jPGame.add(arrSquare[a][b]);
            }
        } 
        jBtnStart.setVisible(isInviter);
        jBtnStart.setEnabled(false);
        jBtnOK.setVisible(!isInviter);
        Thread listener = new Thread(listen);
        listener.start();
    }

    Runnable listen = new Runnable(){
        @Override
        public void run() {
            while (isRun) {
                try {
                    Thread.sleep(100);
                    if(ManageForm.listClientStatus.size()>0){
                        String status = (String)ManageForm.listClientStatus.get(nameDoiThu);
                        if(status.equals("OK")){
                            jBtnStart.setEnabled(true);
                        }
                    }
                    if(ManageForm.listClientCaro.size()>0)
                    {
                        Caro caro = (Caro)ManageForm.listClientCaro.get(nameDoiThu);
                        
                        if(caro.NameEnemy != null){
                            setButtonListener(true);
                            if(caro.Type == 'X'){
                                arrSquare[caro.i][caro.j].setIcon(new ImageIcon(System.getProperty("user.dir")+"\\x.png"));
                                arrSquare[caro.i][caro.j].setValue('x');
                                ManageForm.listClientCaro.put(nameDoiThu, new Caro(null, null, 'E', 0, 0));
                                EnemyWinner = StaticCheckWinner.CheckWin(arrSquare, 'x', caro.i, caro.j);
                            }
                            else{
                                arrSquare[caro.i][caro.j].setIcon(new ImageIcon(System.getProperty("user.dir")+"\\o.png"));
                                arrSquare[caro.i][caro.j].setValue('o');
                                ManageForm.listClientCaro.put(nameDoiThu, new Caro(null, null, 'E', 0, 0));
                                EnemyWinner = StaticCheckWinner.CheckWin(arrSquare, 'o', caro.i, caro.j);
                            }
                        }
                    }     
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(PlayGameForm.this, ex.getMessage(),
                            "Nhan dc roi", JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
            }
        }
        
    };



    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == chatField) {
            String chat = chatField.getText();
        }
        
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++) {
                if(e.getSource() == arrSquare[i][j]){
                    if(PlayerType == 'x'){
                        arrSquare[i][j].setIcon(new ImageIcon(System.getProperty("user.dir")+"\\x.png"));
                        arrSquare[i][j].setValue('x');
                        Winner = StaticCheckWinner.CheckWin(arrSquare, 'x', i, j);
                        try {
                            Client.SendObj('X');
                            DataType.Caro caro = new DataType.Caro(Client.cUser.UserName, nameDoiThu,'X', i, j);
                            Client.SendObj(caro);
                            messageArea.append(Client.cUser.UserName+":"+nameDoiThu+":"+i+","+j);
                        } catch (IOException ex) {
                            Logger.getLogger(PlayGameForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else{
                        arrSquare[i][j].setIcon(new ImageIcon(System.getProperty("user.dir")+"\\o.png"));
                        arrSquare[i][j].setValue('o');
                        Winner = StaticCheckWinner.CheckWin(arrSquare, 'o', i, j);
                        try {
                            Client.SendObj('X');
                            DataType.Caro caro = new DataType.Caro(Client.cUser.UserName, nameDoiThu, 'O', i, j);
                            Client.SendObj(caro);
                            messageArea.append(Client.cUser.UserName + ":" + nameDoiThu + ":" + i + "," + j);
                        } catch (IOException ex) {
                            Logger.getLogger(PlayGameForm.class.getName()).log(Level.SEVERE, null, ex);
                        }             
                    }
                    
                    if (Winner == true) {
                        JOptionPane.showMessageDialog(null, "Bạn Đã Chiến Thắng", "Ket Qua", JOptionPane.PLAIN_MESSAGE);
                    }
                    setButtonListener(false);
                }
                
            } 
        }  
    }
    
    public char Type(){
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++)
                if(arrSquare[i][j].getValue() == 'x')
                    return 'o';
        }
        return 'x';
    }
    
    
    public void setButtonListener(boolean isListener) {
        if (isListener) {
            for (int i = 0; i < Rows; i++) {
                for (int j = 0; j < Cols; j++) {
                    arrSquare[i][j].addActionListener(this);
                }
            }
        }
        else{
            for (int i = 0; i < Rows; i++) {
                for (int j = 0; j < Cols; j++) {
                    arrSquare[i][j].removeActionListener(this);
                }
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageArea = new javax.swing.JTextArea();
        jPGame = new javax.swing.JPanel();
        chatField = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        lblTime = new javax.swing.JLabel();
        jBtnStart = new javax.swing.JButton();
        jBtnOK = new javax.swing.JButton();

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(750, 550));
        setResizable(false);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Message"));

        messageArea.setEditable(false);
        messageArea.setColumns(20);
        messageArea.setLineWrap(true);
        messageArea.setRows(5);
        messageArea.setPreferredSize(new java.awt.Dimension(165, 94));
        jScrollPane1.setViewportView(messageArea);

        jPGame.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPGame.setPreferredSize(new java.awt.Dimension(500, 500));

        javax.swing.GroupLayout jPGameLayout = new javax.swing.GroupLayout(jPGame);
        jPGame.setLayout(jPGameLayout);
        jPGameLayout.setHorizontalGroup(
            jPGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 496, Short.MAX_VALUE)
        );
        jPGameLayout.setVerticalGroup(
            jPGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 496, Short.MAX_VALUE)
        );

        btnSend.setText("Send");

        lblTime.setText("- -");

        jBtnStart.setText("Start");
        jBtnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnStartActionPerformed(evt);
            }
        });

        jBtnOK.setText("OK!!!");
        jBtnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chatField, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(lblTime))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jBtnStart)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnOK)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTime)
                        .addGap(87, 87, 87)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnStart)
                            .addComponent(jBtnOK))
                        .addGap(139, 139, 139)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(chatField, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnStartActionPerformed
        // TODO add your handling code here:
       PlayerType = 'x';
       jBtnStart.setEnabled(false);
       setButtonListener(true);
    }//GEN-LAST:event_jBtnStartActionPerformed

    private void jBtnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnOKActionPerformed
        // TODO add your handling code here:
        try {
            Client.SendObj('K');
            Client.SendMsg(Client.cUser.UserName+":"+nameDoiThu);
        } catch (IOException ex) {
            Logger.getLogger(PlayGameForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        jBtnOK.setEnabled(false);
    }//GEN-LAST:event_jBtnOKActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(PlayGameForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//               // new PlayGameForm().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JTextField chatField;
    private javax.swing.JButton jBtnOK;
    private javax.swing.JButton jBtnStart;
    private javax.swing.JPanel jPGame;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JLabel lblTime;
    private javax.swing.JTextArea messageArea;
    // End of variables declaration//GEN-END:variables
}
