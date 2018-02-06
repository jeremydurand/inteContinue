package Game;

import Game.Board.Board;
import Game.Board.BoardPanel;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Classe gérant l'exécution des tours.
 *
 * C'est un singleton instancié au lancement du jeu et qui contrôle le
 * déroulement de la partie jusqu'à sa fin, tour par tour.
 */
public class GameManager {

	private int day = 1;
	private Board board;
	private BoardPanel panel;
	private int step = 0;

	/**
	 * Singleton
	 */
	private static GameManager manager;

	/**
	 * Instancie si ce n'est pas déjà fait le GameManager et retourne l'instance.
	 * On ne gère pas l'aspect concurrent car seul le thread principal accède à
	 * cette fonction.
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
		new Board();
		// throw new NotImplementedException();
	}

	/**
	 * Cette m�thode incr�mente le num�ro du jour et met � jour l'affichage du jour sur l'ihm
	 */
	public void dayIncrement() {
		day++;
		board.setDayText(day);
	}

	/**
	 * Retourne le num�ro du jour
	 * 
	 * @return le num�ro du jour
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Applique le num�ro du jour
	 * @param day le num�ro du jour � appliquer
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * Cette m�thode va permettre de choisir le bon �v�nement � ex�cuter
	 * @param des le num�ro de l'�v�nement
	 */
	public void doStep(int des) {
		switch (step) {
		case 0:
			step0(des);
			break;
		case 1:
			step1();
		default:
			break;
		}

	}

	/**
	 * L'�tape 1 : le placement du joueur sur la carte
	 * @param des
	 */
	private void step0(int des) {
		switch (des) {
		case 1:
			panel.moveAvatar(0, 0);
			break;

		case 2:
			panel.moveAvatar(6, 0);
			break;

		case 3:
			panel.moveAvatar(7, 0);
			break;

		case 4:
			panel.moveAvatar(12, 0);
			break;

		case 5:
			panel.moveAvatar(14, 0);
			break;

		case 6:
			panel.moveAvatar(16, 0);
			break;

		default:
			break;
		}
		step++;
	}

	/**
	 * Cette m�thode fait bouger le personnage sur une case al�atoire se trouvant autour de lui
	 */
	private void step1() {
		panel.moveToNeighbour();
	}
	
	/**
	 * Retourne le plateau de jeu
	 * 
	 * @returnle plateau de jeu
	 */
	public BoardPanel getPanel() {
		return panel;
	}

	/**
	 * D�finit le plateau de jeu
	 * @param panel le plateau de jeu � d�finir
	 */
	public void setPanel(BoardPanel panel) {
		this.panel = panel;
	}

	/**
	 * Retourne la fenetre principale du jeu
	 * 
	 * @return la fenetre principale
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * D�finit la fenetre principale
	 * 
	 * @param board la fenetre principale a definir
	 */
	public void setBoard(Board board) {
		this.board = board;
	}
	
	
	
}
