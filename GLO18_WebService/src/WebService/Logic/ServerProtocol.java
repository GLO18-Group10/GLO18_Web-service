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
public class ServerProtocol {

    ILogic logic;
    Session session;

    public ServerProtocol(ILogic logic) {
        this.logic = logic;
    }

    public String serverHandler(String[] data) {
        switch (data[0]) {
            case "00":
                String ID = data[1];
                String password = data[2];
                String test = logic.login(ID, password);
                if (test.equalsIgnoreCase("True")) {
                    //session = new CustomerSession(ID, logic);
                    session = logic.initializeSession(ID);
                }
                return test;
            case "01":
                return logic.getCustomerInfo(logic.sessionGetID());
            case "02":
                return logic.getAccountBalance(data[1]);
            case "03":
                break;
            case "04":
                break;
            case "05":
                String response05;
                Transfer transfer = new Transfer(data[1], data[2], data[3], data[4], logic);
                response05 = transfer.validate((CustomerSession)session);
                //Send back the error if the transfer could not be completed
                if (!response05.equals("valid")) {
                    return response05;
                } else { //Otherwise complete the transfer
                    return transfer.completeTransfer();
                }
            case "06":
                break;
            case "07":
                String ID1 = data[1];
                String name = data[2];
                String birthday = data[3];
                String phonenumber = data[4];
                String address = data[5];
                String email = data[6];
                String password1 = data[7];
                return logic.createCustomer(ID1, name, birthday, phonenumber, address, email, password1);
            case "08":
                String answer = "";
                String[] accountNos =  ((CustomerSession)session).getAccountNos();
                for (String no : accountNos) {
                    if (no != null) {
                        answer += no;
                    }
                }
                return answer;
            case "09":
                try {
                    if (data[2].equals("1")) {
                        logic.openAccount(data[1]);
                    } else if (data[2].equals("0")) {
                        logic.closeAccount(data[1]);
                    }
                    return "complete";
                } catch (Exception e) {
                    return e.getMessage();
                }
            case "10":
                break;
            case "11":
                break;
            case "12":
                break;
            case "18":
                return logic.logout();
            case "19":
                break;
            default:
                break;
        }
        return "Error";
    }
}
