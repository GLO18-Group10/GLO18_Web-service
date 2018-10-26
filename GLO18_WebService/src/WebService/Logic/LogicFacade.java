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

    ;
    /**
     * 
     * @param ID
     * @return 
     */
    
      @Override
    public String getCustomerInfo(String ID) {
        return Persistance.getCustomerInfo(ID); //Do a query to get the info that cooreponds to the given id
    }

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

    @Override
    public String createCustomer(String ID, String name, String birthday, String phonenumber, String address, String email, String password) {
        return Persistance.createCustomer(ID, name, birthday, phonenumber, address, email, password);
    }
    
    
    /**
     * Method to choose the next action for the program
     *
     * @param data String array with the OPcode and the parameters
     * @return String with the response to the client
     */
    
    
}
