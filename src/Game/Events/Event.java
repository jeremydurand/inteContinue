package Game.Events;

/**
 * Interface implémentée par chaque événement ("event" dans le code)
 */
public interface Event {

    // Fonction permettant de déclencher l'événement.
    void trigger(Game.Character character, int choiceEncounter);
}
