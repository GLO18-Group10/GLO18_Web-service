/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Logic;

/**
 *
 * @author Nick
 */
public class CustomerSession extends Session{
    private String[] accountNos = new String[10];
    private int noOfAccounts = 0;
    
    public CustomerSession(String ID) {
        super(ID);
    }
    
    public boolean isAccount(String accountNo){
        boolean check = false;
        for(int i = 0; i < noOfAccounts; i++){
            check = accountNo.equals(accountNos[i]);
        }
        return check;
    }
}
