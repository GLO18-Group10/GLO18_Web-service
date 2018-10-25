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
    private MessageParser messageparser = new MessageParser(this);
    
    @Override
    public void injectPersistance(iPersistance PersistanceLayer){
        Persistance = PersistanceLayer;
    }
    
    @Override
    public void executeProtocol(String message){
        messageparser.fromProtocol(message);
    }
    
    public String getAccountBalance(String ID){
        return Persistance.getAccountBalance(ID);
    }
    
}
