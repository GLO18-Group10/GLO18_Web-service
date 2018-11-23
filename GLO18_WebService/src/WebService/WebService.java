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
//        System.out.println(logic.messageParser("00;C1234567;kode"));
//        System.out.println(logic.messageParser("01;"));
//        System.out.println(logic.messageParser("02;7331"));
//
//        System.out.println(Logic.messageParser("08"));
//        System.out.println(Logic.messageParser("05;12345678;1;1234;hej"));
//
//        System.out.println(logic.messageParser("08"));
//        System.out.println(logic.messageParser("05;12345678;1;1234;hej"));
//        System.out.println(Logic.messageParser("09;Cd;0"));
//         </editor-fold>

        link.startConnection();
    }

}
