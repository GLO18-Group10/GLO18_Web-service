/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Acquaintance;

/**
 *
 * @author Jeppe Enevold
 */
public interface iLogic {
    public void injectPersistance(iPersistance PersistanceLayer);
    String login(String ID, String message);
    String messageParser(String message);
    void initializeSession(String ID, String identifier);
    
}
