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

    private static Session session;
    private MessageParser messageparser = new MessageParser(this);
    private static iPersistance Persistance;   

    @Override
    public void injectPersistance(iPersistance PersistanceLayer) {
        Persistance = PersistanceLayer;
    }

    @Override
    public void initializeSession(String ID) {
        if (ID.startsWith("A")) {
            session = new AdminSession(ID);
        } else if (ID.startsWith("C")) {
            session = new CustomerSession(ID);
        }
    }
    
    public String getAccountBalance(String ID){
        return Persistance.getAccountBalance(ID);
    }
    
    /**
     * 
     * @param ID
     * @return 
     */ 
    @Override
    public String getCustomerInfo(String ID) {
        return Persistance.getCustomerInfo(ID); //Do a query to get the info that cooreponds to the given id
    }

    @Override
    public String login(String ID, String password) {
        String test = Persistance.login(ID, password);
        return test;
    }

    ;

    @Override
    public String messageParser(String message) {
        return messageparser.fromProtocol(message); //Parse the message from the client
    }

    @Override
    public String sessionGetID() {
        return session.getID(); //Get the id of the current user
   }

}

