package Game.Events;

import java.util.ArrayList;
import java.util.List;

/**
 * EventManager gère les mécaniques des événements
 *
 * EventManager permet de déclencher un événement par rapport à certaines conditions,
 * et de ne pas exposer le fonctionnement interne des événements au reste du programme.
 *
 * todo: gérer en interne le choix des events
 *
 * @author robin
 */
public class EventManager {

    /**
     * Liste des événements se déclenchant selon le type de tuile
     *
     * @see Event
     */
    private List<Event> tileEvents;

    /**
     * Liste des événements se déclenchant en fonction d'un jet de dé
     *
     * @see ConditionalEvent
     */
    private List<ConditionalEvent> conditionalEvents;

    /**
     * Constructeur générique
     */
    public EventManager() {
        tileEvents = new ArrayList<>();
        conditionalEvents = new ArrayList<>();
    }

    /**
     * Retourne la liste des tileEvents
     *
     * @return Event
     */
    public List<Event> getTileEvents() {
        return tileEvents;
    }

    /**
     * Retourne la liste des conditionalEvents
     *
     * @return ConditionalEvent
     */
    public List<ConditionalEvent> getConditionalEvents() {
        return conditionalEvents;
    }
}
