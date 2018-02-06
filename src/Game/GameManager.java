package Game;

import Game.Board.Board;
import Game.Board.BoardPanel;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Classe gÃ©rant l'exÃ©cution des tours.
 *
 * C'est un singleton instanciÃ© au lancement du jeu et qui contrÃ´le le
 * dÃ©roulement de la partie jusqu'Ã  sa fin, tour par tour.
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
	 * Instancie si ce n'est pas dÃ©jÃ  fait le GameManager et retourne l'instance.
	 * On ne gÃ¨re pas l'aspect concurrent car seul le thread principal accÃ¨de Ã 
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
	 * Fonction contrÃ´lant le dÃ©roulement d'un tour.
	 */
	public void play() {
		new Board();
		// throw new NotImplementedException();
	}

	/**
	 * Cette méthode incrémente le numéro du jour et met à jour l'affichage du jour sur l'ihm
	 */
	public void dayIncrement() {
		day++;
		board.setDayText(day);
	}

	/**
	 * Retourne le numéro du jour
	 * 
	 * @return le numéro du jour
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Applique le numéro du jour
	 * @param day le numéro du jour à appliquer
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * Cette méthode va permettre de choisir le bon événement à exécuter
	 * @param des le numéro de l'événement
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
	 * L'étape 1 : le placement du joueur sur la carte
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
	 * Cette méthode fait bouger le personnage sur une case aléatoire se trouvant autour de lui
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
	 * Définit le plateau de jeu
	 * @param panel le plateau de jeu à définir
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
	 * Définit la fenetre principale
	 * 
	 * @param board la fenetre principale a definir
	 */
	public void setBoard(Board board) {
		this.board = board;
	}
	
	
	
}
