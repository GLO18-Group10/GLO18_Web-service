/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Link;

import WebService.Acquaintance.iLink;
import WebService.Acquaintance.iLogic;
import WebService.Logic.LogicFacade;


/**
 *
 * @author Jeppe Enevold
 */
public class LinkFacade implements iLink {
    private static iLogic Logic;
    
    public void injectLogic(iLogic LogicLayer){
        Logic = LogicLayer;
    }

    @Override
    public String messageParser(String message) {
        return Logic.messageParser(message);
    }
}
