/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Link;

/**
 *
 * @author Peterzxcvbnm
 */
public class MessageParser {

    public void fromProtocol(String message) {
        String code = message.substring(0, 1); //Seperate the OPcode
        switch (code) {
            case "00": 
            case "01":
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
    }
}
