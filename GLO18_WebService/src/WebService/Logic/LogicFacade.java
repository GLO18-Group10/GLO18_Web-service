/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Logic;

import java.time.LocalDateTime;
import WebService.Acquaintance.ILogic;
import WebService.Acquaintance.IPersistence;

/**
 *
 * @author Jeppe Enevold
 */
public class LogicFacade implements ILogic {

    private static Session session;
    private MessageParser messageparser = new MessageParser(this);
    private static IPersistence persistence;

    @Override
    public void injectPersistance(IPersistence PersistanceLayer) {
        persistence = PersistanceLayer;
    }

    @Override
    public Session initializeSession(String ID) {
        if (ID.startsWith("A")) {
            session = new AdminSession(ID, this);
        } else if (ID.startsWith("C")) {
            session = new CustomerSession(ID, this);
        }
        return session;
    }

    @Override
    public String messageParser(String message) {
        return messageparser.fromProtocol(message); //Parse the message from the client
    }

    @Override
    public String login(String ID, String password) {
        String test = persistence.login(ID, password);
        return test;
    }

    @Override
    public String logout() {
        session = null;

        if (session == null) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     *
     * @param ID
     * @return CostumerInfo
     */
    @Override
    public String getCustomerInfo(String ID) {
        return persistence.getCustomerInfo(ID); //Do a query to get the info that cooreponds to the given id
    }

    @Override
    public String getAccountBalance(String ID) {
        return persistence.getAccountBalance(ID);
    }

    @Override
    public String sessionGetID() {
        return session.getID(); //Get the id of the current user
    }

    @Override
    public String createCustomer(String ID, String name, String birthday, String phonenumber, String address, String email, String password) {
        return persistence.createCustomer(ID, name, birthday, phonenumber, address, email, password);
    }

    @Override
    public boolean doesAccountExist(String accountID) {
        return persistence.doesAccountExist(accountID);
    }

    @Override
    public void updateAccountBalance(String accountID, int amount) {
        persistence.updateAccountBalance(accountID, amount);
    }

    @Override
    public String saveTransfer(String fromAccount, String toAccount, int amount, String text, LocalDateTime date) {
        return persistence.saveTransfer(fromAccount, toAccount, amount, text, date);
    }

    @Override
    public String getAccountNos(String customerID) {
        return persistence.getAccountNos(customerID);
    }
    
    @Override
    public void openAccount(String ID){
        persistence.openAccount(ID);
    }
    
    @Override
    public void closeAccount(String ID){
        persistence.closeAccount(ID);
    }
}
