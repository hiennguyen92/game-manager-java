/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcaro;

import Data.User;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author ADMIN
 */
public class Client {
    public static Socket cSocket;
    public static User cUser;
    
    public static void Init(int port) throws IOException{
        cSocket = new Socket("localhost", port);
        cUser = new User(null, null, 0, null);
    }
    
    //gửi thông tin kiểu string
    public static void SendMsg(String msg) throws IOException{
        DataOutputStream out = new DataOutputStream(cSocket.getOutputStream());;
        out.writeUTF(msg);
    }
    
    //nhận thông tin kiểu string
    public static String GetMsg() throws IOException{
        DataInputStream in = new DataInputStream(cSocket.getInputStream());
        return in.readUTF();
    }
    
    //gửi thông tin kiểu object (mọi kiểu)
    public static void SendObj(Object obj) throws IOException{
        ObjectOutputStream out = new ObjectOutputStream(cSocket.getOutputStream());;
        out.writeObject(obj);
    }
    
    //nhận thông tin kiểu object
    public static Object GetObj() throws IOException, ClassNotFoundException{
        ObjectInputStream in = new ObjectInputStream(cSocket.getInputStream());
        return in.readObject();
    }
}
