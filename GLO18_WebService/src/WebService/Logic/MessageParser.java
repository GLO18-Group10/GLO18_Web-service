/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Logic;

/**
 *
 * @author Peterzxcvbnm
 */
public class MessageParser {

    LogicFacade logic;

    public MessageParser(LogicFacade logic) {
        this.logic = logic;
    }

    /**
    * Method to parse the message from the client and act upon it
    @param message: The message from the protocol
    @return The string which is the response to the client
    */
    public String fromProtocol(String message) {
        String code = message.substring(0, 2); //Seperate the OPcode

        String data[] = message.split(";"); //Seperate the parameters

        switch (code) {
            case "00":
               
                String ID = data[1];
                String password = data[2];
                String test = logic.login(ID, password);
                if (test.equalsIgnoreCase("True")) {
                    logic.initializeSession(ID, test);
                }
                
                
                return test;
            case "01":
                return logic.getCustomerInfo("C1908957623");//logic.sessionGetID());
            case "02":
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
