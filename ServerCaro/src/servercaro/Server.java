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

    //tất cả người dùng trong database
    public static List<User> allUsers = new ArrayList<>();
    //tất cả Client đã đăng nhập
    public static List<Client> cSockets = new ArrayList<>();
    //tất cả Tour đã tổ chức
    public static List<Tournament> tournaments = new ArrayList<>();
    //socket đại diện server
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
    public static Client getClient(String name) {
        for (int i = 0; i < cSockets.size(); i++) {
            if (cSockets.get(i).cUser.UserName.equals(name)) {
                return cSockets.get(i);
            }
        }
        return null;
    }

    //lấy thông tin user của 1 user trong database
    public static User getUser(String name) {
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).UserName.equals(name)) {
                return allUsers.get(i);
            }
        }
        return null;
    }

    //lấy thông tin 1 tour
    public static Tournament getTour(String name) {
        for (int i = 0; i < tournaments.size(); i++) {
            if (tournaments.get(i).name.equals(name)) {
                return tournaments.get(i);
            }
        }
        return null;
    }

    //lấy tất cả tên tour
    public static List<String> getAllToursStatus() {
        List<String> statuses = new ArrayList<>();
        for (int i = 0; i < tournaments.size(); i++) {
            if (!tournaments.get(i).isStart) {
                Tournament tour = tournaments.get(i);
                String msg = "";
                msg += tour.name + " (";
                msg += tour.users.size() + "\\";
                msg += tour.nPlayer + ")";
                statuses.add(msg);
            }
        }
        return statuses;
    }

    //lấy 1 tour có user tên nào đó
    public static Tournament getTourHasUser(User user) {
        for (int i = 0; i < tournaments.size(); i++) {
            if (tournaments.get(i).isExistUser(user.UserName)) {
                return tournaments.get(i);
            }
        }
        return null;
    }
}
