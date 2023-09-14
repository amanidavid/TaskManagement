/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;


/**
 *
 * @author AMANI
 */
public abstract class taskRemainder {
     protected int id;
//    protected Date startDate;

    protected taskRemainder(int id) {
        this.id = id;
//        this.startDate = startDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   

    public abstract boolean add();
    public abstract boolean update();
    public abstract boolean delete();
    
}
