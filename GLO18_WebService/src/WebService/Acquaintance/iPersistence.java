/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Acquaintance;

import java.time.LocalDateTime;

/**
 *
 * @author Jeppe Enevold
 */
public interface iPersistence {
    public String getAccountBalance(String ID);
    String getCustomerInfo(String id);
    String login(String ID, String password);
    String createCustomer(String ID, String name, String birthday, String phonenumber, String address, String email, String password);
    boolean doesAccountExist(String accountID);
    void updateAccountBalance(String accountID, int amount);
    String saveTransfer(String fromAccount, String toAccount, int amount, String text, LocalDateTime date);
    String getAccountNos(String customerID);
    void openAccount(String ID);
    void closeAccount(String ID);
}
