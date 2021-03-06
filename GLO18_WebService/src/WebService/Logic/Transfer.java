/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Logic;

import WebService.Acquaintance.IPersistence;
import java.time.LocalDateTime;

/**
 *
 * @author Peter
 */
public class Transfer {

    private IPersistence persistence;
    private final String fromAccount;
    private final String toAccount;
    private final int amount;
    private final String category;
    private final String text;
    private final LocalDateTime date = LocalDateTime.now();
    private final String customerID;

    public Transfer(String fromAccount, String amount, String toAccount, String text, IPersistence persistence, String customerID, String category) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = Integer.parseInt(amount);
        this.category = category;
        this.text = text;
        this.persistence = persistence;
        this.customerID = customerID;
    }

    /**
     * Check if everything is in order with the transfer through a serious of
     * checks
     *
     * @return An error or valid
     */
    public String validate() {
        //Check if user owns account
        if (!isAccount(fromAccount)) {
            return "Error; user does not own account.";
        } //Check if there is enough money in account
        else if (amount > Integer.parseInt(persistence.getAccountBalance(fromAccount))) {
            return "Error; insufficient funds.";
        } //Check if the receiver exists
        else if (!persistence.doesAccountExist(toAccount)) {
            return "Error; recipient not found.";
        }
        return "valid";
    }

    public boolean isAccount(String accountNo) {
        String[] accountNos = persistence.getAccountNos(customerID);
        boolean check = false;
        for (int i = 0; i < accountNos.length; i++) {
            check = accountNo.equals(accountNos[i]);
            if (check) {
                break;
            }
        }
        return check;
    }

    /**
     * Figure out the new balances and update both accounts
     *
     * @return An error or complete
     */
    public synchronized String completeTransfer() {
        int fromBalance = Integer.parseInt(persistence.getAccountBalance(fromAccount));
        int toBalance = Integer.parseInt(persistence.getAccountBalance(toAccount));

        //Calculate new balances
        fromBalance -= amount;
        toBalance += amount;

        //Update the balances
        try {
            persistence.updateAccountBalance(fromAccount, fromBalance);
            persistence.updateAccountBalance(toAccount, toBalance);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return "Error; transaction couldn't be completed.";
        }
        saveTransfer();
        return "complete";
    }

    /**
     * Save the transaction. If it fails print out the input for review later
     */
    private void saveTransfer() {
        if (persistence.saveTransfer(fromAccount, toAccount, amount, category, text, date).equals("false")) {
            System.out.println("Error; Transaction from " + fromAccount
                    + " to " + toAccount + " could not be saved. Date: "
                    + date + " amount: " + amount + " Category: " + category + " message: " + text);
        }
    }

}
