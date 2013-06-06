/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcaro;

import javax.swing.JButton;

/**
 *
 * @author Nguyen Hien
 */
public class Square extends JButton{
    private boolean status=false;
    private boolean X;
    private boolean O;
    private int row,col;
    
    private int value;  //nguoi thang

    
    
    
    public Square(boolean status){
        super();
        this.status = status;
    }
    /**
     * @return the X
     */
    public boolean isX() {
        return X;
    }

    /**
     * @param X the X to set
     */
    public void setX(boolean X) {
        this.X = X;
    }

    /**
     * @return the O
     */
    public boolean isO() {
        return O;
    }

    /**
     * @param O the O to set
     */
    public void setO(boolean O) {
        this.O = O;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * @param col the col to set
     */
    public void setCol(int col) {
        this.col = col;
    }
    
    
}
