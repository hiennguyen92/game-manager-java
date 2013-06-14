
package clientcaro;

import Data.DataType;
import Data.DataType.Caro;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
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
    final int TIME = 20;
    
    private char PlayerType;
    private String nameDoiThu;
    private String NameTour;
    boolean isRun = true;
    boolean isOverTime = false;
    private Square[][] arrSquare;
    private final int Rows = 20, Cols = 20;
    private  boolean Winner=false,EnemyWinner=false;
    private int time = TIME;
    Thread listener;
    private Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            lblTime.setText(""+ time);
            --time;
            if(time<10){
                Toolkit.getDefaultToolkit().beep();
            }
            if (time == 0) {
                lblTime.setText("0 RANDOM");
                time = TIME;
                isOverTime = true;
                timer.stop();
            }
        }
    });
    

    public PlayGameForm(String UserDoiThu,boolean isInviter,String nameTour) {

        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jPGame.setLayout(new GridLayout(Rows, Cols));
        arrSquare = new Square[Rows][Cols];
        btnSend.addActionListener(this);
        chatField.addActionListener(this);
        nameDoiThu = UserDoiThu;
        NameTour = nameTour;
        if("".equals(NameTour))
            setTitle(Client.cUser.UserName+" vs "+UserDoiThu);
        else
            setTitle(NameTour+":"+Client.cUser.UserName+" vs "+UserDoiThu);
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
        jbtnClose.setVisible(false);
        if(isInviter)
            jlblStatus.setText("Waiting...");
        else
            jlblStatus.setText("Click 'OK'");
        listener = new Thread(listen);
        listener.start();
    }

    int result = JOptionPane.NO_OPTION;
    
    Runnable listen = new Runnable(){
        @Override
        public void run() {
            while (isRun) {
                try {
                    jlblScore.setText("Score: "+Client.cUser.Score);
                    if(isOverTime)
                    {
                        while (true) {                            
                            Random rand = new Random();
                            int R = rand.nextInt(Rows);
                            int C = rand.nextInt(Cols);
                            if(arrSquare[R][C].getValue() == '-'){
                                arrSquare[R][C].doClick();
                                isOverTime = false;
                                break;
                            }
                        }
                    }
                    if(ManageForm.listClientStatus.size()>0){
                        String status = (String)ManageForm.listClientStatus.get(nameDoiThu);
                        if(status.equals("OK")){
                            jBtnStart.setEnabled(true);
                            jlblStatus.setText("Click 'Start'");
                            ManageForm.listClientStatus.put(nameDoiThu, "NULL");
                        }
                    }
                    if(ManageForm.listClientCaro.size()>0)
                    {
                        Caro caro = (Caro)ManageForm.listClientCaro.get(nameDoiThu);
                        if(caro.NameEnemy != null){
                            timer.start();
                            jlblStatus.setText("Playing...");
                            if(caro.Type == 'X'){
                                arrSquare[caro.i][caro.j].setIcon(new ImageIcon(System.getProperty("user.dir")+"\\x.png"));
                                arrSquare[caro.i][caro.j].setValue('x');
                                setButtonListener(true);
                                ManageForm.listClientCaro.put(nameDoiThu, new Caro(null, null, 'E', 0, 0));
                                EnemyWinner = StaticCheckWinner.CheckWin(arrSquare, 'x', caro.i, caro.j);
                            }
                            else{
                                arrSquare[caro.i][caro.j].setIcon(new ImageIcon(System.getProperty("user.dir")+"\\o.png"));
                                arrSquare[caro.i][caro.j].setValue('o');
                                setButtonListener(true);
                                ManageForm.listClientCaro.put(nameDoiThu, new Caro(null, null, 'E', 0, 0));
                                EnemyWinner = StaticCheckWinner.CheckWin(arrSquare, 'o', caro.i, caro.j);
                            }
                        } 
                    }
                    Thread.sleep(100); 
                    if (EnemyWinner) {
                        timer.stop();
                        EnemyWinner = false;
                        setButtonListener(false);
                        JOptionPane.showMessageDialog(PlayGameForm.this, "You Lose!!!!", "LOSE", JOptionPane.OK_OPTION);
                        jbtnClose.doClick();
                    }
                    if(Winner){
                        try {
                            JOptionPane.showMessageDialog(null, "You Win!!!", "WIN", JOptionPane.OK_OPTION);
                            if ("".equals(NameTour)) {
                                Client.cUser.Score++;
                                Client.SendObj('R');
                                DataType.CaroResult result = new DataType.CaroResult(Client.cUser.UserName, nameDoiThu,"", "WIN");
                                Client.SendObj(result);
                                timer.stop();
                                Winner = false;
                                setButtonListener(false); 
                            }
                            else{
                                Client.SendObj('T');
                                DataType.CaroResult result = new DataType.CaroResult(Client.cUser.UserName, nameDoiThu, NameTour, "WIN");
                                Client.SendObj(result);
                                timer.stop();
                                Winner = false;
                                setButtonListener(false);
                            }
                            jbtnClose.doClick();
                        } catch (IOException ex) {
                            Logger.getLogger(PlayGameForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(PlayGameForm.this, ex.getMessage(),
                            "Fail", JOptionPane.WARNING_MESSAGE);
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
                    setButtonListener(false);
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
                    time=TIME;
                    timer.stop();
                }
                
            } 
        }  
    }
    
    
    
    public void setButtonListener(boolean isListener) {
        if (isListener) {
            for (int i = 0; i < Rows; i++) {
                for (int j = 0; j < Cols; j++) {
                    if(arrSquare[i][j].getValue() == '-')
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
        jlblStatus = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jlblScore = new javax.swing.JLabel();
        jbtnClose = new javax.swing.JButton();

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

        btnSend.setText("SEND");

        lblTime.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTime.setText("- -");

        jBtnStart.setForeground(new java.awt.Color(0, 204, 255));
        jBtnStart.setText("Start");
        jBtnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnStartActionPerformed(evt);
            }
        });

        jBtnOK.setForeground(new java.awt.Color(0, 204, 255));
        jBtnOK.setText("OK!!!");
        jBtnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnOKActionPerformed(evt);
            }
        });

        jlblStatus.setText("jLabel1");

        jLabel1.setText("Time:");

        jlblScore.setText("jLabel2");

        jbtnClose.setText("Close");
        jbtnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chatField, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlblStatus)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblTime))
                                    .addComponent(jBtnStart))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jBtnOK)
                                    .addComponent(jlblScore))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtnClose)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnClose)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTime)
                            .addComponent(jLabel1)
                            .addComponent(jlblScore))
                        .addGap(87, 87, 87)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnStart)
                            .addComponent(jBtnOK))
                        .addGap(18, 18, 18)
                        .addComponent(jlblStatus)
                        .addGap(107, 107, 107)
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
       jlblStatus.setText("Playing...");
       jBtnStart.setEnabled(false);
       setButtonListener(true);
       timer.start();
    }//GEN-LAST:event_jBtnStartActionPerformed

    private void jBtnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnOKActionPerformed
        // TODO add your handling code here:
        try {
            jlblStatus.setText("Waiting...");
            jBtnOK.setEnabled(false);
            Client.SendObj('K');
            Client.SendMsg(Client.cUser.UserName+":"+nameDoiThu);
        } catch (IOException ex) {
            Logger.getLogger(PlayGameForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnOKActionPerformed

    private void jbtnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCloseActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jbtnCloseActionPerformed

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPGame;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JButton jbtnClose;
    private javax.swing.JLabel jlblScore;
    private javax.swing.JLabel jlblStatus;
    private javax.swing.JLabel lblTime;
    private javax.swing.JTextArea messageArea;
    // End of variables declaration//GEN-END:variables
}
