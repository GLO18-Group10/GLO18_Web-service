package WebService;

import WebService.Logic.LogicFacade;
import WebService.Persistance.PersistenceFacade;
import WebService.Link.LinkFacade;
import WebService.Acquaintance.ILink;
import WebService.Acquaintance.ILogic;
import WebService.Acquaintance.IPersistence;

/**
 *
 * @author Robin
 */
public class WebService {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IPersistence persistance = new PersistenceFacade();
        ILogic logic = new LogicFacade();
        ILink link = new LinkFacade();

        logic.injectPersistance(persistance);
        link.injectLogic(logic);

        // <editor-fold desc="TestCode">
        System.out.printf("%-40s %s \n", "Testing login: ", logic.messageParser("00;C111234552;password"));
        System.out.printf("%-40s %s \n", "Testing get customer info: ", logic.messageParser("01;C111234552"));
        System.out.printf("%-40s %s \n", "Testing get account balance: ", logic.messageParser("02;123456789;C111234552"));
        System.out.printf("%-40s %s \n", "Testing get account numbers: ", logic.messageParser("08;C111234552"));
        System.out.printf("%-40s %s \n", "Testing make transaction: ", logic.messageParser("05;123456789;1;1234;hej;C111234552;category"));
        System.out.printf("%-40s %s \n", "Testing open customer account: ", logic.messageParser("09;C77778888;1;A1234"));
        System.out.printf("%-40s %s \n", "Testing close customer account: ", logic.messageParser("09;C77778888;0;A1234"));
        System.out.printf("%-40s %s \n", "Testing get customer accounts: ", logic.messageParser("10;A1234"));
        // </editor-fold>


        link.startConnection();
    }

}
