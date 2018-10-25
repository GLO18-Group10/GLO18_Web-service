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
    public void initializeSession(String ID, String identifier) {
        if (identifier.equalsIgnoreCase("A")) {
            session = new AdminSession(ID);
        } else if (identifier.equalsIgnoreCase("C")) {
            session = new CustomerSession(ID);
        }
    }

    ;
    
      @Override
    public String getCustomerInfo(String ID) {
        return Persistance.getCustomerInfo(ID);
    }

    public String login(String ID, String password) {

        return Persistance.login(ID, password);
    }

    ;

    @Override
    public String messageParser(String message) {
        return messageparser.fromProtocol(message);
    }

    @Override
    public String sessionGetID() {
        return session.getID();
   }
}
