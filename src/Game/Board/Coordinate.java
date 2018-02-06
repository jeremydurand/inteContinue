package Game.Board;


/**
 * Coordinate est une représentation en 3 dimensions d'une coordonnée.
 *
 * L'objet est utilisé afin de définir la position des tuiles entre elles sur le plateau,
 * et plus particulièrement pour calculer les déplacement.
 *
 * todo: update references (@see)
 * @see Board
 *
 * @author robin
 * @version 0.1
 */
public class Coordinate {

    /**
     * Valeur de l'abscisse.
     */
    private int x;

    /**
     * Valeur de l'ordonnée.
     */
    private int y;

    /**
     * Valeur de la hauteur.
     */
    private int z;

    /**
     * Construit un objet Coordinate avec les valeurs x, y et z égales à 0.
     */
    public Coordinate() {
        this(0, 0, 0);
    }

    /**
     * Construit un objet Coordinate avec les valeurs x, y et z spécifiées
     *
     * @param x int
     * @param y int
     * @param z int
     */
    public Coordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * transforme une coordonnée de type offset en une coordonnée de type cube
     *
     * @param rowIndex valeur de l'abscisse
     * @param columnIndex valeur de l'ordonnée
     *
     * @return Coordinate
     */
    public static Coordinate fromOffset(int rowIndex, int columnIndex) {
        Coordinate coordinate = new Coordinate();

        coordinate.setX(columnIndex - (rowIndex - (rowIndex&1)) / 2);
        coordinate.setZ(rowIndex);
        coordinate.setY((coordinate.getX() + coordinate.getZ()) * (-1));

        return coordinate;
    }

    /**
     * Retourne la valeur de x.
     *
     * @return int
     */
    public int getX() {
        return x;
    }

    /**
     * Définit la valeur de x.
     *
     * @param x int
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Retourne la valeur de y.
     *
     * @return int
     */
    public int getY() {
        return y;
    }

    /**
     * Définit la valeur de y.
     *
     * @param y int
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Retourne la valeur de z.
     *
     * @return int
     */
    public int getZ() {
        return z;
    }

    /**
     * Définit la valeur de z.
     *
     * @param z int
     */
    public void setZ(int z) {
        this.z = z;
    }
}
