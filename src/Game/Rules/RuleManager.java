package Game.Rules;

/**
 * RuleManager s'occupe d'appliquer les règles et mécaniques réutilisables
 *
 * @author robin
 */
public class RuleManager {

    /**
     * instance du singleton
     */
    private static RuleManager instance;

    /**
     * Retourne l'instance du RuleManager
     *
     * Le RuleManager est unique et est donc créé et obtenu par une mécanique de singleton.
     *
     * @return RuleManager
     */
    public static RuleManager getInstance() {
        if (instance == null) {
            instance = new RuleManager();
        }

        return instance;
    }

    /**
     * Exécute une attaque entre deux characters
     *
     * @param attacker Character
     * @param defender Character
     */
    public void attack(Character attacker, Character defender) {
    }
}
