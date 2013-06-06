/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class User {
    public String UserName;
    public String Password;
    public int Score;
    public String LastVisit;
    
    public User(String userName, String password, int score, Date lastVisit){
        UserName = userName;
        Password = password;
        Score = score;
        if(lastVisit != null)
            LastVisit = lastVisit.toString();
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

    public String getLastVisit() {
        return LastVisit;
    }

    public void setLastVisit(String LastVisit) {
        this.LastVisit = LastVisit;
    }
    
}
