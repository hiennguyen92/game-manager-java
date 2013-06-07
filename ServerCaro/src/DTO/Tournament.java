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
    
}
