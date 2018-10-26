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
public class ServerProtocol {
    
    LogicFacade logic;
    
    public ServerProtocol(LogicFacade logic) {
        this.logic = logic;
    }
    
    public String serverHandler(String[] data) {
        switch (data[0]) {
            case "00":
                String ID = data[1];
                String password = data[2];
                String test = logic.login(ID, password);
                if (test.equalsIgnoreCase("True")) {
                    logic.initializeSession(ID);
                }
                return test;
            case "01":
                return logic.getCustomerInfo(logic.sessionGetID());
            case "02":
                return logic.getAccountBalance(data[1]);
            case "03":
            case "04":
            case "05":
            case "06":
            case "07":
            case "08":
            case "09":
            case "10":
            case "11":
            case "12":
            case "18":
            case "19":
            default:
        }
        return "Error";
    }
}
