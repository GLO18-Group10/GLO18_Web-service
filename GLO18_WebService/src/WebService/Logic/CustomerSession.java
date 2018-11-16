/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Logic;

import WebService.Acquaintance.ILogic;

/**
 *
 * @author Nick
 */
public class CustomerSession extends Session {

    private String[] accountNos = new String[10];
    private int noOfAccounts = 0;

    public CustomerSession(String ID, ILogic logic) {
        super(ID, logic);

    }

    public boolean isAccount(String accountNo) {
        boolean check = false;
        for (int i = 0; i < noOfAccounts; i++) {
            check = accountNo.equals(accountNos[i]);
        }
        return check;
    }

    public String[] getAccountNos() {
        if (noOfAccounts == 0) {
            String[] accounts = logic.getAccountNos(getID()).split(";");
            if (!accounts[0].equals("")) {
                noOfAccounts = accounts.length;
                for (int i = 0; i < accounts.length; i++) {
                    accountNos[i] = accounts[i];
                }
            }
        }
        if (noOfAccounts == 0) {
            String[] i = {"Error; no accounts found"};
            return i;
        } else {
            return accountNos;
        }
    }
}
