/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Persistance;

import WebService.Acquaintance.iPersistance;
import java.time.LocalDateTime;

/**
 *
 * @author Jeppe Enevold
 */
public class PersistanceFacade implements iPersistance {

    DBManager dbmanager = new DBManager();

    @Override
    public String getAccountBalance(String accountID) {
        return dbmanager.getAccountBalance(accountID);
    }

    @Override
    public String login(String ID, String password) {

        return dbmanager.login(ID, password);
    }

    /**
     * Method to get customer info that corresponds to the given id
     *
     * @param id The id of the current user
     * @return The customer information
     */
    @Override
    public String getCustomerInfo(String id) {
        return dbmanager.getCustomerInfo(id);
    }

    @Override
    public String createCustomer(String ID, String name, String birthday, String phonenumber, String address, String email, String password) {
        return dbmanager.createCustomer(ID, name, birthday, phonenumber, address, email, password);
    }

    @Override
    public boolean doesAccountExist(String accountID) {
        return dbmanager.doesAccountExist(accountID);
    }

    @Override
    public void updateAccountBalance(String accountID, int amount) {
        dbmanager.updateAccountBalance(accountID, amount);
    }

    @Override
    public String saveTransfer(String fromAccount, String toAccount, int amount, String text, LocalDateTime date) {
        return dbmanager.saveTransfer(fromAccount, toAccount, amount, text, date);
    }

}
