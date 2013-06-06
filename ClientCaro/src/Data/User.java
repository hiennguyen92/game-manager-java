/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

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
}
