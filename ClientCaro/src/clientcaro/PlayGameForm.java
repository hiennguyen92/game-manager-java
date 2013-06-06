
package clientcaro;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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
    private Socket chatDirectSocket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private String User;
    private String Type;
    private Square[][] arrSquare;
    private final int Rows = 20, Cols = 20;
    private  boolean Winner=false,EnemyWinner=false;
    private int time = 60;
    private Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            lblTime.setText(time + "");
            --time;


            if (EnemyWinner == true) {
                JOptionPane.showMessageDialog(null, "Đối Thủ Thắng", "Ket Qua", JOptionPane.PLAIN_MESSAGE);
                setEnableButton(false);
                timer.stop();
            }

            
            
            if (time == 0) {
                time = 60;
            }
        }
    });
    

    public PlayGameForm() {

        initComponents();
        
        jPGame.setLayout(new GridLayout(Rows, Cols));
        arrSquare = new Square[Rows][Cols];
        btnSend.addActionListener(this);
        chatField.addActionListener(this);
        
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++) {
                final int a = i, b = j;
                arrSquare[a][b] = new Square(false);//o ban co
                arrSquare[a][b].setBackground(Color.ORANGE);
                arrSquare[a][b].addActionListener(this);
                arrSquare[a][b].setValue(-1);
                arrSquare[a][b].setRow(i);
                arrSquare[a][b].setCol(j);
                jPGame.add(arrSquare[a][b]);
            }
        }  
    }
    

    
    
    public void setType(String type){
        this.Type = type;
    }

    public void setID(String user) {
        this.User = user;
    }

    public void setSocket(Socket s) {
        this.chatDirectSocket = s;
    }

    public void setDetailConnection(String addr, int port) {
        try {
            chatDirectSocket = new Socket(addr, port);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        try {
            out = new PrintWriter(chatDirectSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(chatDirectSocket.getInputStream()));
            this.sendMessage(User);
            setTitle(in.readLine() + " - " + "Caro");
            Thread read = new Thread(new RemoteReader());
            read.start();
            setVisible(true);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private class RemoteReader implements Runnable {

        @Override
        public void run() {
            String receive;
            try {
                while ((receive = in.readLine()) != null) {
                    if (receive.startsWith("Disconnect")) {
                        //disconnect
                    }
                    if(receive.startsWith("Caro:")){
                        String[] str = receive.substring(5).split(":");
                        //String type = str[0];
                        int i = Integer.parseInt(str[0]);
                        int j = Integer.parseInt(str[1]);
                        String type = str[2];
                        timer.start();
                        if(type.equals("x")){
                            arrSquare[i][j].setIcon(new ImageIcon(System.getProperty("user.dir")+"\\x.png"));
                            arrSquare[i][j].setValue(1);
                            EnemyWinner = StaticCheckWinner.CheckWin(arrSquare, 1, i, j);
                            
                        }
                        else{
                            arrSquare[i][j].setIcon(new ImageIcon(System.getProperty("user.dir")+"\\o.png"));
                            arrSquare[i][j].setValue(0);
                            EnemyWinner = StaticCheckWinner.CheckWin(arrSquare, 0, i, j);
                        }
                        
                        for (int l = 0; l < Rows; l++) {
                            for (int k = 0; k < Cols; k++) {
                                arrSquare[l][k].setEnabled(true);
                            }
                        }
                    }else {
                        setVisible(true);
                        messageArea.append(receive + "\n");
                    }
                }
 
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    private void exit() {
        try {
            chatDirectSocket.close();
        } catch (Exception e) {
            System.err.println("Lỗi ở exit GUI Private");
        }
        System.exit(0);
    }

    public void sendMessage(String s) {
        out.println(s);
        out.flush();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == chatField) {
            String chat = chatField.getText();
            messageArea.append(User + "::" + chat + "\n");
            chatField.setText("");
            out.println(User + "::" + chat);
            out.flush();
        }
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++) {
                if(e.getSource() == arrSquare[i][j]){
                    
                    if(this.Type.equals("x")){
                        arrSquare[i][j].setIcon(new ImageIcon(System.getProperty("user.dir")+"\\x.png"));
                        arrSquare[i][j].setValue(1);
                        Winner = StaticCheckWinner.CheckWin(arrSquare, 1, i, j);
                        setEnableButton(false);
                    }
                    else{
                        arrSquare[i][j].setIcon(new ImageIcon(System.getProperty("user.dir")+"\\o.png"));
                        arrSquare[i][j].setValue(0);
                        Winner = StaticCheckWinner.CheckWin(arrSquare, 0, i, j);
                        setEnableButton(false);
                    }
                    out.println("Caro:" + i + ":" + j +":"+this.Type);
                    out.flush();
                    
                    if (Winner == true) {
                        JOptionPane.showMessageDialog(null, "Bạn Đã Chiến Thắng", "Ket Qua", JOptionPane.PLAIN_MESSAGE);
                    }
                    timer.stop();
                    time = 60;
                }
                
            } 
        }
        

        
    }
    
    
    public void setEnableButton(boolean b) {
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++) {
                arrSquare[i][j].setEnabled(b);
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
                                .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                            .addComponent(jScrollPane1))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(lblTime)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTime)
                        .addGap(249, 249, 249)
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlayGameForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PlayGameForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JTextField chatField;
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
