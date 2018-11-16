/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Logic;

import WebService.Acquaintance.ILogic;

/**
 *
 * @author Nick
 */
public class Session {
    String ID;
    ILogic logic;

    public Session(String ID, ILogic logic) {
        this.ID = ID;
        this.logic = logic;
    }
    
    public String getID(){
        return ID;
    }
}
