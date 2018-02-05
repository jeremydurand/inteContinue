package Game.UI;

import java.util.HashMap;
import java.util.Map;

public class UIManager {

    private static final int SPEAK_TO = 1;
    private static final int AVOID = 2;
    private static final int FIGHT = 3;
    /**
     * Singleton
     */
    private static UIManager manager;

    /**
     * Instancie si ce n'est pas déjà fait le UIManager et retourne l'instance.
     *
     * @return instance du UIManager
     */
    public static UIManager getInstance() {

        if (manager == null) {
            manager = new UIManager();
        }

        return manager;
    }

    public static Integer getChoiceEncounter() {

        Map m = new HashMap<>();
        m.put(SPEAK_TO, "Parler");
        m.put(AVOID, "Eluder");
        m.put(FIGHT, "Attaquer");

        return SPEAK_TO;
    }
}
