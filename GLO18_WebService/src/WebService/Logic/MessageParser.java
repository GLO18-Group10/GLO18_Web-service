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
    public MessageParser(LogicFacade logicfacade){
        logic = logicfacade;
    }    
    
    public void fromProtocol(String message) {
        String code = message.substring(0, 2); //Seperate the OPcode
        String[] Data = message.split(";");
        switch (code) {
            case "00":
                break;
            case "01":
                break;
            case "02":
                System.out.println(logic.getAccountBalance(Data[1]));
                //String s = Encrypter.encrypt(logic.getAccountBalance(Data[1]));
                //return s;
                break;
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
