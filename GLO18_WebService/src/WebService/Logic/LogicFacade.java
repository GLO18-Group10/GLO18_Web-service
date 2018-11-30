/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Logic;

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
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return "Error; messageParser";
    }
}
