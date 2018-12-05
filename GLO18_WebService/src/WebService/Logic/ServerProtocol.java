/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Logic;

import WebService.Acquaintance.IPersistence;

/**
 *
 * @author Nick
 */
public class ServerProtocol {

    private IPersistence persistence;
    private MailHandler mailHandler;
    private ActionLogger logger;

    public ServerProtocol(IPersistence persistence) {
        this.persistence = persistence;
        mailHandler = new MailHandler();
        this.logger = new ActionLogger(persistence);
    }

    public String serverHandler(String[] data) {
        switch (data[0]) {
            case "00":
                String ID = data[1];
                String password = data[2];
                String response00 = persistence.login(ID, password);
                if (response00.equals("true")) {
                    logger.logAction(ID, "Logged in");
                } else {
                    logger.logAction(ID, "Failed to log in");
                }
                return response00;
            case "01":
                logger.logAction(data[1], "Get customer info");
                return persistence.getCustomerInfo(data[1]);
            case "02":
                logger.logAction(data[2], "Get account balance");
                return persistence.getAccountBalance(data[1]);
            case "03":
                String response03 = persistence.storeCustomerInfo(data[5], data[1], data[2], data[3], data[4]);
                if (response03.equals("true")) {
                    logger.logAction(data[5], "Stored customer info");
                } else {
                    logger.logAction(data[5], "Failed to store customer info");
                }
                return response03;
            case "04":
                break;
            case "05":
                String response05;
                Transfer transfer = new Transfer(data[1], data[2], data[3], data[4], persistence, data[5]);
                response05 = transfer.validate();
                //Send back the error if the transfer could not be completed
                if (!response05.equals("valid")) {
                    logger.logAction(data[5], "Failed transfer to accountnumber: " + data[3] + " from accountnumber: " + data[1] + " amount: " + data[2] + "DKK");
                    return response05;
                } else { //Otherwise complete the transfer
                    logger.logAction(data[5], "Completed transfer to accountnumber: " + data[3] + " from accountnumber: " + data[1] + " amount: " + data[2] + "DKK");
                    return transfer.completeTransfer();
                }
            case "06":
                String ID2 = data[1];
                logger.logAction(data[2], "Get transaction history");
                return persistence.getTransactionHistory(ID2);
            case "07":
                String ID1 = data[1];
                String name = data[2];
                String birthday = data[3];
                String phonenumber = data[4];
                String address = data[5];
                String email = data[6];
                String password1 = data[7];
                String adminID = data[8];

                String response07 = persistence.createCustomer(ID1, name, birthday, phonenumber, address, email, password1);
                if (response07.equalsIgnoreCase("true")) {
                    logger.logAction(adminID, "Created customer with ID: " + ID1);
                } else {
                    logger.logAction(adminID, "failed to create user with ID: " + ID1);
                }
                return response07;
            case "08":
                String answer = "";
                String[] accountNos = persistence.getAccountNos(data[1]);
                for (String no : accountNos) {
                    if (no != null) {
                        answer += no;
                    }
                }
                logger.logAction(data[1], "Get bank accounts");
                return answer;
            case "09":
                try {
                    if (data[2].equals("1")) {
                        persistence.openAccount(data[1]);
                        logger.logAction(data[3], "Opened customer account with ID: " + data[1]);
                    } else if (data[2].equals("0")) {
                        persistence.closeAccount(data[1]);
                        logger.logAction(data[3], "Closed customer account with ID: " + data[1]);
                    }
                    return "complete";
                } catch (Exception e) {
                    return "Error; could not open/close account";
                }
            case "10":
                logger.logAction(data[1], "Get customer IDs");
                //removes all "C" from Customer IDS              
                return persistence.getCustomerIDs().replace("C", "");
            case "11":
                break;
            case "12":
                break;
            case "13":
                if (persistence.login(data[1], data[2]).equals("true")) {
                    try {
                        persistence.updatePassword(data[1], data[3]);
                        logger.logAction(data[1], "Change password succeeded");
                        return "true";
                    } catch (Exception e) {
                        System.out.println("Error; serverHandler; updatePassword");
                        return "Error; Unexpected SQL error";
                    }
                } else {
                    logger.logAction(data[1], "Change password failed");
                    return "Incorrect password";
                }
            case "14":
                try {
                    String response14 = mailHandler.sendMailAutogenerated(data[1], data[2], data[3], data[4]);
                    logger.logAction(data[1], "sent mail to: " + data[2]);
                    return response14;
                } catch (Exception e) {
                    System.out.println("Error; mailHandler; sendAutogeneratedMail");
                    logger.logAction(data[1], "failed to send mail to: " + data[2]);
                    return "error in protocol 14 in web-service";
                }
            case "16":
                ID = data[1];
                password = data[2];
                String response16 = persistence.login(ID, password);
                if (response16.equalsIgnoreCase("true")) {
                    logger.logAction(ID, "Password check: correct");
                } else {
                    logger.logAction(ID, "password check: wrong");
                }
                return response16;
            case "18":
                String ID_logout = data[1];
                if (logger.logAction(ID_logout, "Logged out")) {
                    return "true";
                } else {
                    return "false";
                }
            default:
                break;
        }
        return "Error; Protocol number not found " + data[0];
    }
}
