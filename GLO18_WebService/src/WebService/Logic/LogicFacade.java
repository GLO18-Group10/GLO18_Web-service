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

    private MessageParser messageparser;
    private static IPersistence persistence;

    @Override
    public void injectPersistance(IPersistence PersistanceLayer) {
        persistence = PersistanceLayer;
        messageparser = new MessageParser(persistence);
    }
    
    @Override
    public String messageParser(String message) {
        try {
            return messageparser.fromProtocol(message); //Parse the message from the client
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return "error";
    }

//    @Override
//    public String login(String ID, String password) {
//        String test = persistence.login(ID, password);
//        return test;
//    }

//    @Override
//    public String logout() {
//        return "true";
//    }

//    /**
//     *
//     * @param ID
//     * @return CostumerInfo
//     */
//    @Override
//    public String getCustomerInfo(String ID) {
//        return persistence.getCustomerInfo(ID); //Do a query to get the info that cooreponds to the given id
//    }

//    @Override
//    public String storeCustomerInfo(String ID, String name, String phoneNo, String address, String email) {
//        return persistence.storeCustomerInfo(ID, name, phoneNo, address, email);
//    }

//    @Override
//    public String getAccountBalance(String ID) {
//        return persistence.getAccountBalance(ID);
//    }

//    @Override
//    public String createCustomer(String ID, String name, String birthday, String phonenumber, String address, String email, String password) {
//        return persistence.createCustomer(ID, name, birthday, phonenumber, address, email, password);
//    }

//    @Override
//    public boolean doesAccountExist(String accountID) {
//        return persistence.doesAccountExist(accountID);
//    }

//    @Override
//    public void updateAccountBalance(String accountID, int amount) {
//        persistence.updateAccountBalance(accountID, amount);
//    }

//    @Override
//    public String saveTransfer(String fromAccount, String toAccount, int amount, String text, LocalDateTime date) {
//        return persistence.saveTransfer(fromAccount, toAccount, amount, text, date);
//    }

//    @Override
//    public String[] getAccountNos(String customerID) {
//        int noOfAccounts = 0;
//        String[] accountNos = new String[10];
//
//        if (noOfAccounts == 0) {
//            String[] accounts = persistence.getAccountNos(customerID).split(";");
//            if (!accounts[0].equals("")) {
//                noOfAccounts = accounts.length;
//                for (int i = 0; i < accounts.length; i++) {
//                    accountNos[i] = accounts[i];
//                }
//            }
//        }
//        if (noOfAccounts == 0) {
//            String[] i = {"Error; no accounts found"};
//            return i;
//        } else {
//            return accountNos;
//        }
//    }

//    @Override
//    public String getTransactionHistory(String accountID) {
//        return persistence.getTransactionHistory(accountID);
//    }

//    @Override
//    public String getCustomerIDs() {
//        return persistence.getCustomerIDs();
//    }

//    @Override
//    public void openAccount(String ID) {
//        persistence.openAccount(ID);
//    }
//
//    @Override
//    public void closeAccount(String ID) {
//        persistence.closeAccount(ID);
//    }

}
