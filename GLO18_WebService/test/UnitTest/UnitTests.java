/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UnitTest;

import WebService.Acquaintance.ILink;
import WebService.Acquaintance.ILogic;
import WebService.Acquaintance.IPersistence;
import WebService.Link.LinkFacade;
import WebService.Logic.LogicFacade;
import WebService.Persistance.PersistenceFacade;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jeppe Enevold
 */
public class UnitTests {
    
    final private static IPersistence persistance = new PersistenceFacade();
    final private static ILogic logic = new LogicFacade();
    final private static ILink link = new LinkFacade();
    
    public UnitTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logic.injectPersistance(persistance);
        link.injectLogic(logic);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
