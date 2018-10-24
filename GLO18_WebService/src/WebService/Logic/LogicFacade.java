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
    public static AdminSession adminsession; 
    public static CustomerSession customerSession;
    private MessageParser messageparser = new MessageParser();
    
    public void injectPersistance(iPersistance PersistanceLayer){
        Persistance = PersistanceLayer;
    }
    
    @Override
    public void initializeSession(String ID, String identifier){
        if (identifier.equalsIgnoreCase("A")){
            adminsession = new AdminSession(ID);
        }
        else if (identifier.equalsIgnoreCase("C")) {
            customerSession = new CustomerSession(ID);
        }
        };
    
    public String login(String ID, String password){
        
        return Persistance.login(ID, password);
    };

    @Override
    public String messageParser(String message) {
        return messageparser.fromProtocol(message);
    }
}
