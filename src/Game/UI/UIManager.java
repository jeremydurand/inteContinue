package Game.UI;

import java.util.HashMap;
import java.util.Map;

public class UIManager {

    public static final int SPEAK_TO = 1;
    public static final int AVOID = 2;
    public static final int FIGHT = 3;

    public static final int SLEEP = 1;
    public static final int TRAVEL = 2;
    //public static final int RECRUIT = 3;
    public static final int RUINS = 4;
    public static final int INFOS = 5;
    
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

    public static Map getChoiceEncounter() {

        Map m = new HashMap<>();
        m.put(SPEAK_TO, "Parler");
        m.put(AVOID, "Eluder");
        m.put(FIGHT, "Attaquer");

        return m;
    }
    
    public static Map getChoiceDailyAction() {
        Map place = new HashMap<>();
        place.put(SLEEP, "Se Reposer");
        place.put(TRAVEL, "Voyager");
        //place.put(RECRUIT, "Ruines");
        place.put(RUINS, "Recruter");
        place.put(INFOS, "Informations");

        return place;
    }
}
