/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Logic;

/**
 *
 * @author Nick
 */
public class Session {
    String ID;

    public Session(String ID) {
        this.ID = ID;
    }
    
    public String getID(){
        return ID;
    }
}
