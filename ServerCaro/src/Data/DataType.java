/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.Serializable;

/**
 *
 * @author ADMIN
 */
public class DataType {
    public static class Answer implements Serializable{
        public String UserName;
        public int Answer;
        
        public Answer(String name, int answer){
            UserName = name;
            Answer = answer;
        }
    };
}
