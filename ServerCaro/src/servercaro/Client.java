/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servercaro;

import DTO.Tournament;
import DTO.User;
import Data.DataType.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class Client extends Thread {

    public Socket cSocket;
    public User cUser;
    public boolean isRunning = true;

    public Client(Socket socket, User user) {
        cSocket = socket;
        cUser = user;
    }

    public String getUserName() {
        return cUser.UserName;
    }

    //gửi thông tin kiểu string
    public void SendMsg(String msg) throws IOException {
        DataOutputStream out = new DataOutputStream(cSocket.getOutputStream());
        out.writeUTF(msg);
    }

    //nhận thông tin kiểu string
    public String GetMsg() throws IOException {
        DataInputStream in = new DataInputStream(cSocket.getInputStream());
        return in.readUTF();
    }

    //gửi thông tin kiểu object (mọi kiểu)
    public void SendObj(Object obj) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(cSocket.getOutputStream());
        out.writeObject(obj);
    }

    //nhận thông tin kiểu object
    public Object GetObj() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(cSocket.getInputStream());
        return in.readObject();
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                char offset = (char) GetObj();
                switch (offset) {
                    //nhận thông báo tắt
                    case '0':
                        for (int i = 0; i < Server.cSockets.size(); i++) {
                            if (!Server.cSockets.get(i).cUser.UserName.equals(cUser.UserName)) {
                                Server.cSockets.get(i).SendObj('0');
                                Server.cSockets.get(i).SendMsg(cUser.UserName);
                            }
                        }
                        Server.cSockets.remove(this);
                        this.isRunning = false;
                        break;
                    //nhận lời mời
                    case '1':
                        String userName = GetMsg();
                        Client user = Server.getClient(userName);
                        //gửi lời mời tới người đó
                        user.SendObj('3');
                        user.SendMsg(cUser.UserName);
                        break;
                    //nhận trả lời lời mời
                    case '2':
                        Answer answer = (Answer) GetObj();
                        user = Server.getClient(answer.UserName);
                        //gửi trả lời tới người đó
                        user.SendObj('4');
                        answer.UserName = cUser.UserName;
                        user.SendObj(answer);
                        break;
                    //nhận cập nhật khung chat
                    case '3':
                        String msg = GetMsg();
                        for (int i = 0; i < Server.cSockets.size(); i++) {
                            user = Server.cSockets.get(i);
                            user.SendObj('5');
                            user.SendMsg(cUser.UserName + ": " + msg);
                        }
                        break;
                    // nhận yêu cầu tham gia tour
                    case '4':
                        msg = GetMsg();
                        String tourName = "";
                        for (int i = msg.length() - 1; i >= 0; i--) {
                            if (msg.charAt(i) == '(') {
                                tourName = msg.substring(0, i - 1);
                                break;
                            }
                        }
                        Tournament tour = Server.getTour(tourName);
                        SendObj('6');
                        if (tour.users.size() == tour.nPlayer) {
                            SendMsg("full");
                        } else if (tour.mPoint > cUser.Score) {
                            SendMsg("score");
                        } else {
                            tour.users.add(cUser);
                            SendMsg("success");
                            for (int i = 0; i < tour.users.size(); i++) {
                                user = Server.getClient(tour.users.get(i).UserName);
                                user.SendObj('7');
                                user.SendObj(tour.getNames());
                            }
                            for (int i = 0; i < Server.cSockets.size(); i++) {
                                Server.cSockets.get(i).SendObj('2');
                                Server.cSockets.get(i).SendObj(Server.getAllToursStatus());
                            }
                        }
                        break;
                    //nhận yêu cầu thoát tour
                    case '5':
                        tour = Server.getTourHasUser(cUser);
                        tour.users.remove(cUser);
                        for (int i = 0; i < Server.cSockets.size(); i++) {
                            Server.cSockets.get(i).SendObj('2');
                            Server.cSockets.get(i).SendObj(Server.getAllToursStatus());
                        }
                        break;
                    case 'X':
                        Caro caro = (Caro) GetObj();
                        user = Server.getClient(caro.NameEnemy);
                        String temp = caro.UserName;
                        caro.UserName = caro.NameEnemy;
                        caro.NameEnemy = temp;
                        user.SendObj('X');
                        user.SendObj(caro);
                        System.out.print(caro.UserName + "/" + caro.NameEnemy + "/" + caro.i + "/" + caro.j);
                        break;
                    case 'K':
                        String[] sss = GetMsg().split(":");
                        user = Server.getClient(sss[1]);

                        user.SendObj('K');
                        user.SendMsg(sss[0]);
                        break;
//                    case 'O':
//                        caro = (Caro)GetObj();
//                        user = Server.getClient(caro.NameEnemy);
//                        temp = caro.UserName;
//                        caro.UserName = caro.NameEnemy;
//                        caro.NameEnemy = temp;
//                        user.SendObj('O');
//                        user.SendObj(caro);
//                        System.out.print(caro.UserName+"/"+caro.NameEnemy+"/"+caro.i+"/"+caro.j);
//                        break;
                }
            } catch (Exception ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
