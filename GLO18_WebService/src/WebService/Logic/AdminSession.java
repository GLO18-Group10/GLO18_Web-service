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
public class AdminSession extends Session{
    //private static AdminSession adminsession;
    public AdminSession(String ID, ILogic logic) {
        super(ID, logic);
        //adminsession = new AdminSession(ID);
    }
    
    
}
