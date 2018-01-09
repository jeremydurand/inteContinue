package Game;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Classe gérant l'exécution des tours.
 *
 * C'est un singleton instancié au lancement du jeu et qui contrôle le déroulement de la partie
 * jusqu'à sa fin, tour par tour.
 */
public class GameManager {

    /**
     * Singleton
     */
    private static GameManager manager;

    /**
     * Instancie si ce n'est pas déjà fait le GameManager et retourne l'instance.
     * On ne gère pas l'aspect concurrent car seul le thread principal accède à cette fonction.
     *
     * @return instance du GameManager
     */
    public static GameManager getInstance() {

        if (manager == null) {
            manager = new GameManager();
        }

        return manager;
    }

    /**
     * Fonction contrôlant le déroulement d'un tour.
     */
    public void play() {
        throw new NotImplementedException();
    }
}
