/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Connection.DataProvider;
import DTO.User;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class UserDAO {
    public static String sql;
    
    public static List<User> GetList(){
        List<User> users = null;
        try {
            sql = "select * from [USER]";
            DataProvider dataProvider = new DataProvider();
            users = DataProvider.ExecuteQuery(sql);
            return users;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public static boolean Add(User user){
        sql = "insert into [USER] values( N'"
                + user.UserName + "',N'"
                + user.Password + "',"
                + user.Score + ",'"
                + user.LastVisit + "')";
        if(DataProvider.ExecuteNonquery(sql))
            return true;
        return false;
    }
    
   public static boolean Update(User user) {
       sql = "update [USER] set LastVisit = '"
                + user.LastVisit + "', Password = N'"
                + user.Password + "', Score = "
                + user.Score + " where UserName = '"
                + user.UserName + "'";
       if(DataProvider.ExecuteNonquery(sql))
            return true;
        return false;
   }
   
   public static boolean Delete(String userName) {
       sql = "delete from [USER] where UserName = '" + userName + "'";
       if(DataProvider.ExecuteNonquery(sql))
            return true;
        return false;
   }
}
