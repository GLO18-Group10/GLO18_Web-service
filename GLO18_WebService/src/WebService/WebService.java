package WebService;

import WebService.Acquaintance.iPersistance;
import WebService.Acquaintance.iLogic;
import WebService.Acquaintance.iLink;

import WebService.Logic.LogicFacade;
import WebService.Persistance.PersistanceFacade;
import WebService.Link.LinkFacade;

/**
 *
 * @author Robin
 */
public class WebService {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        iPersistance Persistance = new PersistanceFacade();
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
        // </editor-fold>
        
        Link.startConnection();
    }
    
}
