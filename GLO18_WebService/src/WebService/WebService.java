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
    }
    
}
