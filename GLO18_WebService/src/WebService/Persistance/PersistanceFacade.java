/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Persistance;

import WebService.Acquaintance.iPersistance;

/**
 *
 * @author Jeppe Enevold
 */
public class PersistanceFacade implements iPersistance {

    DBManager dbmanager = new DBManager();
/**
 * Method to get customer info that corresponds to the given id
 * @param id The id of the current user
 * @return The customer information
 */
    @Override
    public String getCustomerInfo(String id) {
        return dbmanager.getCustomerInfo(id);
    }

    @Override
    public String login(String ID, String password) {

        return "true" + ";" + "C";
    }
}
