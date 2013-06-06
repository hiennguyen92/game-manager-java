/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcaro;

/**
 *
 * @author Nguyen Hien
 */
public class StaticCheckWinner {
   static boolean CheckWin(Square[][] matrix,int value,int curRow,int curCol){
       //check ngang
       int count_Ngang = 0;
       for (int j = 0; j < matrix[0].length; ++j) {

           if (matrix[curRow][j].getValue()== value) {
               ++count_Ngang;
           } else {
               count_Ngang = 0;
           }
           if (count_Ngang == 5) {
               return true;
           }
       }
       
       //check Doc
       int count_Doc = 0;
       for (int i = 0; i < matrix.length; ++i) {
           if (matrix[i][curCol].getValue() == value) {
               ++count_Doc;
           } else {
               count_Doc = 0;
           }
           if (count_Doc == 5) {
               return true;
           }
       }
       int R_Cheo_Trai,C_Cheo_trai;
       R_Cheo_Trai=curRow;
       C_Cheo_trai = curCol;
       
       //check cheo trai
       int count_Cheo_Trai = 0;
       while (R_Cheo_Trai != 0 && C_Cheo_trai != 0) {
           --R_Cheo_Trai;
           --C_Cheo_trai;

       }
       while (R_Cheo_Trai < matrix.length && C_Cheo_trai < matrix[0].length) {
           if (matrix[R_Cheo_Trai++][C_Cheo_trai++].getValue() == value) {
               ++count_Cheo_Trai;
           } else {
               count_Cheo_Trai = 0;
           }
           if (count_Cheo_Trai == 5) {
               return true;
           }
       }
       
       int R_Cheo_Phai, C_Cheo_Phai;
       R_Cheo_Phai = curRow;
       C_Cheo_Phai = curCol;
       
       //check cheo phai
       int count_cheo_Phai = 0;
       while (C_Cheo_Phai < matrix[0].length - 1 && R_Cheo_Phai != 0) {
           --R_Cheo_Phai;
           ++C_Cheo_Phai;
       }

       while (R_Cheo_Phai < matrix.length && C_Cheo_Phai != -1) {
           if (matrix[R_Cheo_Phai++][C_Cheo_Phai--].getValue() == value) {
               ++count_cheo_Phai;
           } else {
               count_cheo_Phai = 0;
           }
           if (count_cheo_Phai == 5) {
               return true;
           }
       }

       return false;
   }
}
