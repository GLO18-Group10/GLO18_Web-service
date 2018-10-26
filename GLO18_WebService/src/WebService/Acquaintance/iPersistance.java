/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Acquaintance;

/**
 *
 * @author Jeppe Enevold
 */
public interface iPersistance {
    public String getAccountBalance(String ID);
    String getCustomerInfo(String id);
    String login(String ID, String password);
    String createCustomer(String ID, String name, String birthday, String phonenumber, String address, String email, String password);
}
