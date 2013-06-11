/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class Tournament {
    public int code;
    public String name;
    public int nPlayer;
    public int prize;
    public int mPoint;
    public List<User> users;

    public Tournament() {
        users = new ArrayList<>();
    }
    
    public List<String> getNames(){
        List<String> names = new ArrayList<>();
        for(int i = 0; i < users.size(); i++)
            names.add(users.get(i).UserName);
        return names;
    }
    
    public boolean isExistUser(String name){
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).UserName.equals(name))
                return true;
        }
        return false;
    }
}
