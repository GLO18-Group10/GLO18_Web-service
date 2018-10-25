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
     * Method to parse the message from the client and send it to serverHandler
     *
     * @param message: The message from the protocol
     * @return The string which is the response to the client
     */
    public String fromProtocol(String message) {
        String code = message.substring(0, 2); //Seperate the OPcode
        String[] data = message.split(";"); //Seperate the parameters
        return logic.serverHandler(data);
    }
}
