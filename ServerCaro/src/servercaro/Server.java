/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servercaro;

import DTO.Tournament;
import DTO.User;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class Server {

    public static List<User> allUsers = new ArrayList<>();
    public static List<Client> cSockets = new ArrayList<>();
    public static List<Tournament> tournaments = new ArrayList<>();
    public static ServerSocket sSocket;

    //lấy index của 1 người trong database
    public static int getIndex(String userName, String password) {
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).UserName.equals(userName)
                    && allUsers.get(i).Password.equals(password)) {
                return i;
            }
        }
        return -1;
    }
    
    //lấy thông tin client của 1 người đang tham gia chơi
    public static Client getClient(String name){
        for(int i = 0; i < cSockets.size(); i++){
            if(cSockets.get(i).cUser.UserName.equals(name))
                return cSockets.get(i);
        }
        return null;
    }
    
    //lấy thông tin user của 1 user trong database
    public static User getUser(String name){
        for(int i = 0; i < allUsers.size(); i++){
            if(allUsers.get(i).UserName.equals(name))
                return allUsers.get(i);
        }
        return null;
    }
}
