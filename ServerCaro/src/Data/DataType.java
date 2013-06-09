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
    
    public static class Caro implements Serializable{
      public String UserName;
      public String NameEnemy;
      public int i;
      public int j;
      public char Type;
      
      public Caro(String name,String enemy,char type,int i,int j){
          UserName = name;
          NameEnemy = enemy;
          this.i = i;
          this.j = j;
          this.Type = type;
      }
    };
}
