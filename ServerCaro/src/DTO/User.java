/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author ADMIN
 */
public class User implements Serializable{
    public String UserName;
    public String Password;
    public int Score;
    public Date LastVisit;

    public Date getLastlogin() {
        return LastVisit;
    }

    public void setLastlogin(Date lastlogin) {
        this.LastVisit = lastlogin;
    }
    
    public User(String userName, String password, int score){
        UserName = userName;
        Password = password;
        Score = score;
        java.util.Date now = new java.util.Date();
        Date date = new Date(now.getTime());
        LastVisit = date;
    }
    
    public User(String userName, String password, int score, Date lastVisit){
        UserName = userName;
        Password = password;
        Score = score;
        LastVisit = lastVisit;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }

    
    
}
