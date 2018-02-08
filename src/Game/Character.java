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

    private int gold;
    
    private String nom;

    /**
     * Constructeur vide instanciant un Character sans les attributs
     *
     */
    public Character(String nom) {
    	this.nom = nom;
    }
    
    /**
     * Constructeur instanciant un Character avec la vie (life) et l'attaque (attack) spécifiée
     *
     * @param life int
     * @param attack int
     * @param gold int
     */
    public Character(String nom, int life, int attack,int gold) {
    	this.nom = nom;
        this.life = life;
        this.attack = attack;
        this.gold = gold;
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

    /**
     * Retourne la richesse du character
     *
     * @return int
     */
    public int getGold() {
        return gold;
    }

    /**
     * Définit la richesse du character
     *
     * @param gold int
     */
    public void setGold(int gold) {
        this.gold = gold;
    }

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

    
}
