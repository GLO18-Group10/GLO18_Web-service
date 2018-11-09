/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Acquaintance;

import WebService.Logic.Session;

/**
 *
 * @author Jeppe Enevold
 */
public interface iLogic {

    public void injectPersistance(iPersistence PersistenceLayer);

    public Session initializeSession(String ID);

    public String messageParser(String message);
    
    public String logout();
    
}
