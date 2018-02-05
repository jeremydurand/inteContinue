package Game;

import junit.framework.TestCase;

public class GameManagerTest extends TestCase {

    /**
     * Vérifie que le GameManager n'est instanciée qu'une fois
     */
    public void testGetInstance() {
        GameManager manager1 = GameManager.getInstance();
        GameManager manager2 = GameManager.getInstance();

        assertSame(manager1, manager2);
    }
}
