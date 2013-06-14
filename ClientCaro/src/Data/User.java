/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author ADMIN
 */
public class User implements Serializable{
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
