package WebService;

import WebService.Acquaintance.iLogic;
import WebService.Acquaintance.iLink;

import WebService.Logic.LogicFacade;
import WebService.Persistance.PersistenceFacade;
import WebService.Link.LinkFacade;
import WebService.Acquaintance.iPersistence;

/**
 *
 * @author Robin
 */
public class WebService {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        iPersistence Persistance = new PersistenceFacade();
        iLogic Logic = new LogicFacade();
        iLink Link = new LinkFacade();

        Logic.injectPersistance(Persistance);
        Link.injectLogic(Logic);

        // <editor-fold desc="TestCode">
        System.out.println(Logic.messageParser("00;C1234567;kode"));
        System.out.println(Logic.messageParser("01;"));
        System.out.println(Logic.messageParser("02;7331"));
        System.out.println(Logic.messageParser("08"));
        System.out.println(Logic.messageParser("05;12345678;1;1234;hej"));
      //  System.out.println(Logic.messageParser("09;Cd;0"));
        // </editor-fold>

        Link.startConnection();
    }

}
