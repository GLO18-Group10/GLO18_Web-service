/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Acquaintance;

import WebService.Logic.Session;
import java.time.LocalDateTime;

/**
 *
 * @author Jeppe Enevold
 */
public interface ILogic {

    public void injectPersistance(IPersistence PersistenceLayer);

    public Session initializeSession(String ID);

    public String messageParser(String message);

    public String login(String ID, String password);

    public String logout();
    
    public String getCustomerInfo(String ID);
    
    public String getAccountBalance(String ID);
    
    public String sessionGetID();
    
    public String createCustomer(String ID, String name, String birthday, String phonenumber, String address, String email, String password);
    
    public boolean doesAccountExist(String accountID);
    
    public void updateAccountBalance(String accountID, int amount);
    
    public String saveTransfer(String fromAccount, String toAccount, int amount, String text, LocalDateTime date);
    
    public String getAccountNos(String customerID);
    
    public void openAccount(String ID);
    
    public void closeAccount(String ID);
    
    public String storeCustomerInfo(String ID, String name, String phoneNo, String address, String email);
    
    public String getTransactionHistory(String accountID);
    
    public String getCustomerIDs();
}
