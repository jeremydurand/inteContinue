package Game;

/**
 * Character enregistre les paramètre d'un personnage.
 *
 * Cette classe est utilisée pour enregistrer les informations des PNJ et du héros.
 *
 * @author robin
 */
public class Character {

    /**
     * Vie du character
     */
    private int life;

    /**
     * Attaque du character
     */
    private int attack;

    /**
     * Constructeur instanciant un Character avec la vie (life) et l'attaque (attack) spécifiée
     *
     * @param life int
     * @param attack int
     */
    public Character(int life, int attack) {
        this.life = life;
        this.attack = attack;
    }

    /**
     * Retourne la vie du character
     *
     * @return int
     */
    public int getLife() {
        return life;
    }

    /**
     * Définit la vie du character
     *
     * @param life int
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * Retourne l'attaque du character
     *
     * @return int
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Définit l'attaque du character
     *
     * @param attack int
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }
}
