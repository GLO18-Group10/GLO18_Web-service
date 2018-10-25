/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Link;

import WebService.Acquaintance.iLink;
import WebService.Acquaintance.iLogic;


/**
 *
 * @author Jeppe Enevold
 */
public class LinkFacade implements iLink {
    private static iLogic Logic;
    
    @Override
    public void injectLogic(iLogic LogicLayer){
        Logic = LogicLayer;
    }
       
}
