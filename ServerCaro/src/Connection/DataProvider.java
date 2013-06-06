/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import DTO.User;
import Xml.XmlReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class DataProvider {

    public static String driverName = null;
    public static String connectionString = null;
    public static String userName = null;
    public static String password = null;

    public static Connection getConnection() {
        try {
            XmlReader reader = new XmlReader("Connection.xml");
            driverName = reader.getString("DriverName");
            connectionString = reader.getString("ConnectionString");
            userName = reader.getString("UserName");
            password = reader.getString("Password");
            Class.forName(driverName);
            Connection connection = DriverManager.getConnection(connectionString, userName, password);
            return connection;
        } catch (Exception ex) {
            Logger.getLogger(DataProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static List<User> ExecuteQuery(String sql) {
        List<User> users = new ArrayList<User>();
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String userName = resultSet.getString("UserName");
                String password = resultSet.getString("Password");
                int score = resultSet.getInt("Score");
                Date lastVisit = resultSet.getDate("LastVisit");

                User user = new User(userName, password, score, lastVisit);

                users.add(user);
            }
            resultSet.close();
            connection.close();

        } catch (Exception ex) {
            Logger.getLogger(DataProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    public static boolean ExecuteNonquery(String sql) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute(sql);
            connection.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
