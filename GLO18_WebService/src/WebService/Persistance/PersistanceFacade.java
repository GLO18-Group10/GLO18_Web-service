/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Persistance;

import WebService.Acquaintance.iPersistance;

/**
 *
 * @author Jeppe Enevold
 */
public class PersistanceFacade implements iPersistance {
DBManager dbmanager = new DBManager();

    @Override
    public String login(String ID, String password) {
        
        return "true" +";" + "C";
    }

    

    
    
}
