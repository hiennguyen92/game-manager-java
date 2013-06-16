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
import javax.swing.JOptionPane;

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
    public   void SendMsg(String msg) throws IOException {
        DataOutputStream out = new DataOutputStream(cSocket.getOutputStream());
        out.writeUTF(msg);
    }

    //nhận thông tin kiểu string
    public  String GetMsg() throws IOException {
        DataInputStream in = new DataInputStream(cSocket.getInputStream());
        return in.readUTF();
    }

    //gửi thông tin kiểu object (mọi kiểu)
    public  void SendObj(Object obj) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(cSocket.getOutputStream());
        out.writeObject(obj);
    }

    //nhận thông tin kiểu object
    public  Object GetObj() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(cSocket.getInputStream());
        return in.readObject();
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                int offset = (int) GetObj();
                switch (offset) {
                    //nhận thông báo tắt
                    case 0:
                        for (int i = 0; i < Server.cSockets.size(); i++) {
                            if (!Server.cSockets.get(i).cUser.UserName.equals(cUser.UserName)) {
                                Server.cSockets.get(i).SendObj(0);
                                Server.cSockets.get(i).SendMsg(cUser.UserName);
                            }
                        }
                        Server.cSockets.remove(this);
                        this.isRunning = false;
                        break;
                    //nhận lời mời
                    case 1:
                        String userName = GetMsg();
                        Client client = Server.getClient(userName);
                        //gửi lời mời tới người đó
                        client.SendObj(3);
                        client.SendMsg(cUser.UserName);
                        break;
                    //nhận trả lời lời mời
                    case 2:
                        Answer answer = (Answer) GetObj();
                        client = Server.getClient(answer.UserName);
                        //gửi trả lời tới người đó
                        client.SendObj(4);
                        answer.UserName = cUser.UserName;
                        client.SendObj(answer);
                        break;
                    //nhận cập nhật khung chat
                    case 3:
                        String msg = GetMsg();
                        for (int i = 0; i < Server.cSockets.size(); i++) {
                            client = Server.cSockets.get(i);
                            client.SendObj(5);
                            client.SendMsg(cUser.UserName + ": " + msg);
                        }
                        break;
                    // nhận yêu cầu tham gia tour
                    case 4:
                        msg = GetMsg();
                        String tourName = "";
                        for (int i = msg.length() - 1; i >= 0; i--) {
                            if (msg.charAt(i) == '(') {
                                tourName = msg.substring(0, i - 1);
                                break;
                            }
                        }
                        Tournament tour = Server.getTour(tourName);
                        SendObj(6);
                        //full người chơi
                        if (tour.users.size() == tour.nPlayer) {
                            SendMsg("full:null");
                        }
                        //điểm chưa đạt yêu cầu
                        else if (tour.mPoint > cUser.Score) {
                            SendMsg("score:null");
                        } 
                        else {
                            tour.users.add(cUser);
                            SendMsg("success:"+tour.name);
                            tour.TourScore.put(cUser.UserName, 0);
                            tour.nameUser.add(cUser.UserName);
                            for (int i = 0; i < tour.users.size(); i++) {
                                client = Server.getClient(tour.users.get(i).UserName);
                                client.SendObj(7);
                                client.SendObj(tour.getNames());
                            }
                            for (int i = 0; i < Server.cSockets.size(); i++) {
                                Server.cSockets.get(i).SendObj(2);
                                Server.cSockets.get(i).SendObj(Server.getAllToursStatus());
                            }
                        }
                        break;
                    //nhận yêu cầu thoát tour
                    case 5:
                        tour = Server.getTourHasUser(cUser);
                        tour.users.remove(cUser);
                        for (int i = 0; i < Server.cSockets.size(); i++) {
                            //cập nhật lại danh sách tham gia cho các client đã tham gia
                            if (tour.users.contains(Server.cSockets.get(i).cUser)) {
                                Server.cSockets.get(i).SendObj(7);
                                Server.cSockets.get(i).SendObj(tour.getNames());
                            }
                            //cập nhật lại trạng thái Tournament
                            Server.cSockets.get(i).SendObj(2);
                            Server.cSockets.get(i).SendObj(Server.getAllToursStatus());
                        }
                        break;
                        //đánh caro
                    case -1:
                        Caro caro = (Caro) GetObj();
                        client = Server.getClient(caro.NameEnemy);
                        String temp = caro.UserName;
                        caro.UserName = caro.NameEnemy;
                        caro.NameEnemy = temp;
                        client.SendObj(-1);
                        client.SendObj(caro);
                        break;
                        //sẵn sàng
                    case -2:
                        String[] sss = GetMsg().split(":");
                        client = Server.getClient(sss[1]);
                        client.SendObj(-2);
                        client.SendMsg(sss[0]);
                        break;
                        //chatPrivate
                    case 200:
                        ChatPrivate chat = (ChatPrivate)GetObj();
                        client = Server.getClient(chat.NameEnemy);
                        temp = chat.UserName;
                        chat.UserName = chat.NameEnemy;
                        chat.NameEnemy = temp;
                        client.SendObj(200);
                        client.SendObj(chat);
                        break;
                    //kết quả đánh bình thường
                    case -3:
                        CaroResult KQTemp = (CaroResult) GetObj();
                        System.out.println(KQTemp.UserName + "--" + KQTemp.NameEnemy + "--" + KQTemp.Result);
                        Client clientWin = Server.getClient(KQTemp.UserName);
                        //Client clientLose = Server.getClient(KQ.NameEnemy);
                        clientWin.cUser.setScore(clientWin.cUser.getScore() + 1);
                        break;
                    //Kết quả giải đấu
                    case -4:
                        CaroResult KQ = (CaroResult)GetObj();
                        Tournament Tour = Server.getTour(KQ.NameTour);
                        int score = Tour.TourScore.get(KQ.UserName) + 1;
                        Tour.TourScore.put(KQ.UserName, score);
                        Tour.users.add(cUser);
                        for (int i = 0; i < Tour.users.size(); i++) {
                            client = Server.getClient(Tour.users.get(i).UserName);
                            client.SendObj(7);
                            client.SendObj(Tour.getNames());
                        }
                        //người thắng cuối cùng
                        if(Tour.users.size() == 1 && Tour.nPlayer == 1){
                            cUser.Score += Tour.prize;
                            SendObj(10);
                            SendMsg(String.valueOf(Tour.prize));
                            SendMsg(String.valueOf(Tour.TourScore.get(cUser.UserName)));
                        }
                        //các cặp khác xong hết thì bắt đầu chia cặp đánh tiếp
                        else if(Tour.users.size() == Tour.nPlayer){
                            Tour.nPlayer = Tour.nPlayer / 2;
                            for (int i = 0; i < Tour.users.size(); i += 2) {
                                client = Server.getClient(Tour.users.get(i).UserName);
                                client.SendObj(8);
                                answer = new Answer(Tour.users.get(i+1).UserName, JOptionPane.YES_OPTION, Tour.name, false);
                                client.SendObj(answer);
                                client = Server.getClient(Tour.users.get(i + 1).UserName);
                                client.SendObj(8);
                                answer = new Answer(Tour.users.get(i).UserName, JOptionPane.YES_OPTION, Tour.name, true);
                                client.SendObj(answer);                               
                            }
                            Tour.users.clear();
                        }
                        System.out.println(KQ.UserName+"--"+KQ.NameEnemy+"--"+KQ.NameTour+"--"+KQ.Result+"--->"+Tour.TourScore.get(KQ.UserName));
                        break;
                }
            } catch (Exception ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
