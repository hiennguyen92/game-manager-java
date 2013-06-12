/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class User {
    public String UserName;
    public String Password;
    public int Score;
    public Timestamp LastVisit;

    public Timestamp getLastlogin() {
        return LastVisit;
    }

    public void setLastlogin(Timestamp lastlogin) {
        this.LastVisit = lastlogin;
    }
    
    public User(String userName, String password, int score, Timestamp lastVisit){
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
