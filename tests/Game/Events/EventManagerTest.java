package Game.Events;

import Game.GameManager;
import junit.framework.TestCase;

import java.util.List;

/**
 * Tests du GameManager
 */
public class EventManagerTest extends TestCase {

    /**
     * Vérifie que tous les events de tuiles sont accessibles
     */
    public void testGetTileEvents() {
        EventManager manager = GameManager.getInstance().getEventManager();

        List events = manager.getTileEvents();

        assertEquals(events.size(), 0);
    }

    /**
     * Vérifie que tous les events conditionnels sont accessibles
     */
    public void testGetConditionalEvents() {
        EventManager manager = GameManager.getInstance().getEventManager();

        List events = manager.getConditionalEvents();

        assertEquals(events.size(), 0);
    }
}
