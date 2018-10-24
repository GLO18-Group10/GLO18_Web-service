/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Logic;

import WebService.Acquaintance.iLogic;
import WebService.Acquaintance.iPersistance;

/**
 *
 * @author Jeppe Enevold
 */
public class LogicFacade implements iLogic {
    private static iPersistance Persistance;
    
    @Override
    public void injectPersistance(iPersistance PersistanceLayer){
        Persistance = PersistanceLayer;
    }
    
    public String getAccountBalance(String ID){
        return Persistance.getAccountBalance(ID);
    }
    
}
