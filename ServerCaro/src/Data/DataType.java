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
        public String NameTour;
        
        public Answer(String name, int answer,String nameTour){
            UserName = name;
            Answer = answer;
            NameTour = nameTour;
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
<<<<<<< HEAD
    public static class CaroResult implements Serializable {

        public String UserName;
        public String NameEnemy;
        public String NameTour;
        public String Result;

        public CaroResult(String name, String enemy, String nameTour, String result) {
            this.UserName = name;
            this.NameEnemy = enemy;
            this.NameTour = nameTour;
            this.Result = result;
        }
    };
    
=======
>>>>>>> 686f4745fa49a397f57ae49f0b0658181a472d33
}
