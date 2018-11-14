/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Logic;

import WebService.Acquaintance.iLogic;
import java.time.LocalDateTime;
import WebService.Acquaintance.iPersistence;

/**
 *
 * @author Jeppe Enevold
 */
public class LogicFacade implements iLogic {

    private static Session session;
    private MessageParser messageparser = new MessageParser(this);
    private static iPersistence persistence;

    @Override
    public void injectPersistance(iPersistence PersistanceLayer) {
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

    public String login(String ID, String password) {
        String test = persistence.login(ID, password);
        return test;
    }

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
    public String getCustomerInfo(String ID) {
        return persistence.getCustomerInfo(ID); //Do a query to get the info that cooreponds to the given id
    }
    
    public String storeCustomerInfo(String ID, String name, String phoneNo, String address, String email){
        return persistence.storeCustomerInfo(ID, name, phoneNo, address, email);
    }

    public String getAccountBalance(String ID) {
        return persistence.getAccountBalance(ID);
    }

    public String sessionGetID() {
        return session.getID(); //Get the id of the current user
    }

    public String createCustomer(String ID, String name, String birthday, String phonenumber, String address, String email, String password) {
        return persistence.createCustomer(ID, name, birthday, phonenumber, address, email, password);
    }

    public boolean doesAccountExist(String accountID) {
        return persistence.doesAccountExist(accountID);
    }

    public void updateAccountBalance(String accountID, int amount) {
        persistence.updateAccountBalance(accountID, amount);
    }

    public String saveTransfer(String fromAccount, String toAccount, int amount, String text, LocalDateTime date) {
        return persistence.saveTransfer(fromAccount, toAccount, amount, text, date);
    }

    public String getAccountNos(String customerID) {
        return persistence.getAccountNos(customerID);
    }
    
    public void openAccount(String ID){
        persistence.openAccount(ID);
    }
    
    public void closeAccount(String ID){
        persistence.closeAccount(ID);
    }
}
