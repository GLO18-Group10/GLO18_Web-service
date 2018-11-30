package WebService.Logic;

import WebService.Acquaintance.IPersistence;
import java.time.LocalDateTime;

/**
 *
 * @author Robin
 */
public class ActionLogger {
    
    private IPersistence persistence;

    public ActionLogger(IPersistence persistence) {
        this.persistence = persistence;
    }
    
    public void logAction(String ID, String action){
        persistence.logAction(ID, LocalDateTime.now(), action);
    }
    
}
