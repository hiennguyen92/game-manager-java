/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ADMIN
 */
public class Tournament {
    public int code; 
    public String name; //tên giải đấu
    public int nPlayer; //số người chơi
    public int prize; //phần thưởng
    public int mPoint; //điểm thấp nhất dc tham gia
    public boolean isStart; //trạng thái đã bắt đầu hay chưa
    public List<User> users;
    public Map<String,Integer> level;

    public Tournament() {
        users = new ArrayList<>();
        level = new HashMap<>();
        isStart = false;
    }
    
    //tất cả tên người dùng trong giải đấu
    public List<String> getNames(){
        List<String> names = new ArrayList<>();
        for(int i = 0; i < users.size(); i++)
            names.add(users.get(i).UserName);
        return names;
    }
    
    //người dùng có tồn tại trong giải đấu ko
    public boolean isExistUser(String name){
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).UserName.equals(name))
                return true;
        }
        return false;
    }
    
    //lấy 1 người chơi có tên name
    public User getUser(String name){
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).UserName.equals(name))
                return users.get(i);
        }
        return null;
    }
}
