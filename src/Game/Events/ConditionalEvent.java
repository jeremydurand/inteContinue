package Game.Events;

/**
 * Interface définissant les évenements conditionels
 * 
 * @see Event
 * @author robin
 */
public interface ConditionalEvent extends Event {

    // hérité de la classe Event
    void trigger();

    // vérifie si le jet de dés répond aux conditions de l'événeme,t
    boolean matchDiceRoll(int diceRoll);
}
