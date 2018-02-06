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
		setBoard(new Board(getInstance()));
		// throw new NotImplementedException();
	}

	public void dayIncrement() {
		day++;
		board.setDayText(day);
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void doStep(int des) {
		switch (step) {
		case 0:
			step0(des);
			break;

		default:
			break;
		}

	}

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
	}

	public BoardPanel getPanel() {
		return panel;
	}

	public void setPanel(BoardPanel panel) {
		this.panel = panel;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
	
	
}
