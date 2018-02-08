package Game;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javax.swing.JOptionPane;

import Game.Board.Board;
import Game.Board.BoardPanel;
import Game.Board.Dialog;
import Game.Events.implementations.Encounter;
import Game.UI.UIManager;

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
	private boolean princeTurn = false;
	private boolean surpriseAttack = false;
	private int step = 0;
	private Character prince;
	private boolean canMove = false;
	private int diceLimit = 6;
	private boolean twoDice = false;
	private int diceResult = 0;
	private final Character GARDE_ROYALE = new Character("Garde royale", 5, 4, 4);
	private final Character CAPITAIN_ROYALE = new Character("Capitaine de la garde royale", 5, 4, 4);
	private final Character NAIN = new Character("Nain", 5, 6, 12);
	private final Character EPEISTE = new Character("Epeiste", 6, 6, 7);
	private final Character CHASSEUR_DE_PRIME = new Character("Chasseur de prime", 6, 6, 50);
	private final Character MERCENAIRE = new Character("Mercenaire", 5, 5, 4);
	private final Character AMAZONE = new Character("Amazone", 6, 5, 4);
	ArrayList<Character> others = new ArrayList<>();
	private boolean hasThrown = false;
	private Character meet;
	private int response;

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

	public void init() {
		prince = new Character("Prince barbare");
		setPrinceAttack(8);
		setPrinceLife(9);
		setPrinceGold(10);
		initOpponent();
	}

	public void initOpponent() {
		others.add(GARDE_ROYALE);
		others.add(CAPITAIN_ROYALE);
		others.add(NAIN);
		others.add(EPEISTE);
		others.add(CHASSEUR_DE_PRIME);
		others.add(MERCENAIRE);
		others.add(AMAZONE);
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
	 * Cette méthode incrémente le numéro du jour et met à jour l'affichage du jour
	 * sur l'ihm
	 * 
	 * @param day
	 *            le numéro du jour à appliquer
	 */
	public void dayIncrement() {
		this.day++;
		board.setDayText(day);
	}

	/**
	 * Cette méthode va permettre de choisir le bon événement à exécuter
	 * 
	 * @param des
	 *            le numéro de l'événement
	 */
	public void doStep() {
		switch (step) {
		case 0:
			moveFirstSpawn();
			break;
		case 1:
			meet();
			break;
		case 2:
			new Encounter().trigger(meet, response + 1);
			break;
		case 3:
			new Encounter().attack();
			break;
		default:
			break;
		}

	}

	/**
	 * L'étape 1 : le placement du joueur sur la carte
	 * 
	 * @param des
	 */
	private void moveFirstSpawn() {
		switch (getDiceResult()) {
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
		step = 1;
		doStep();
	}

	public void meet() {
		canMove = false;
		initOpponent();
		meet = others.get(new Random().nextInt(others.size()));
		Map map = UIManager.getChoiceEncounter();
		setOpponentAttack(meet.getAttack());
		setOpponentLife(meet.getLife());
		setOpponentGold(meet.getGold());
		setOpponentName(meet.getNom());
		String[] options = new String[] { String.valueOf(map.get(UIManager.SPEAK_TO)),
				String.valueOf(map.get(UIManager.AVOID)), String.valueOf(map.get(UIManager.FIGHT)) };
		response = JOptionPane.showOptionDialog(board, "Vous rencontrez " + meet.getNom(),
				"Jour " + day + " - Rencontre", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
				board.getIcon(), options, options[0]);
		princeTurn = new Random().nextBoolean();
		String name = "";
		if (princeTurn) {
			name = prince.getNom();
		} else {
			name = meet.getNom();
		}
		/*
		 * JOptionPane.showMessageDialog(board, "Vous avez choisi "+ map.get(response+1)
		 * + "\nVeuillez lancer les dés pour le type d'attaque",
		 * String.valueOf(map.get(response+1)), JOptionPane.INFORMATION_MESSAGE,
		 * board.getIcon()); step = 2;
		 */
		board.getBtnLancer().setEnabled(true);
		String combatInfo = "";
		if (GameManager.getInstance().isPrinceTurn()) {
			combatInfo = "\nVeuillez lancer les dés pour votre tour";
		} else {
			combatInfo = "\nVeuillez lancer les dés pour le tour de " + GameManager.getInstance().getMeet().getNom();
		}
		JOptionPane.showMessageDialog(board,
				"Vous avez choisi " + map.get(response + 1) + combatInfo,
				String.valueOf(map.get(response + 1)), JOptionPane.INFORMATION_MESSAGE, board.getIcon());
		/*
		 * final JOptionPane optionPane = new JOptionPane("Vous avez choisi " +
		 * map.get(response + 1) + "\nVeuillez lancer les dés pour le tour de " + name,
		 * JOptionPane.INFORMATION_MESSAGE); Dialog.returnDialog(optionPane,
		 * String.valueOf(map.get(response + 1)));
		 */
		step = 3;
		// Where response == 0 for Yes, 1 for No, 2 for Maybe and -1 or 3 for
		// Escape/Cancel.

	}

	public void end() {
		panel.moveAvatar(-1, -1);
		step = 0;
		String[] options = new String[] { "Rejouer", "Quitter" };
		response = JOptionPane.showOptionDialog(board, "Rejouer ?",
				"Fin de partie", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
				board.getIcon(), options, options[0]);
		if(response == 0) {
			init();
			GameManager.getInstance().getBoard().getBtnLancer().setEnabled(true);
			JOptionPane.showMessageDialog(board, "Lancez les dés pour démarrer la nouvelle partie",
					"Barbarian Prince", JOptionPane.INFORMATION_MESSAGE, board.getIcon());
		}else {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					JOptionPane.showMessageDialog(board,
							"A bientot !",
							"Barbarian Prince", JOptionPane.INFORMATION_MESSAGE, board.getIcon());
				}
			}).start();;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
	}
	/**
	 * Cette méthode fait bouger le personnage sur une case aléatoire se trouvant
	 * autour de lui
	 */
	public void moveToNeighbour() {
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
	 * 
	 * @param panel
	 *            le plateau de jeu à définir
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
	 * @param board
	 *            la fenetre principale a definir
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	public void setPrinceAttack(int attack) {
		this.prince.setAttack(attack);
		board.setAttackLabel(attack);
	}

	public void setPrinceLife(int life) {
		this.prince.setLife(life);
		board.setLifeLabel(life);
	}

	public void setPrinceGold(int gold) {
		this.prince.setGold(gold);
		board.setGoldLabel(gold);
	}

	public void setOpponentAttack(int attack) {
		this.meet.setAttack(attack);
		board.setEnemyAttackText(attack);
	}

	public void setOpponentLife(int life) {
		this.meet.setLife(life);
		board.setEnemyLifeText(life);
	}

	public void setOpponentName(String name) {
		board.setEnemyName(name);
	}

	public void setOpponentGold(int gold) {
		this.meet.setGold(gold);
		board.setEnemyGoldText(gold);
	}

	public void resetOpponentStats() {
		board.resetOpponent();
	}

	public int getDiceLimit() {
		return diceLimit;
	}

	public void setDiceLimit(int diceLimit) {
		this.diceLimit = diceLimit;
	}

	public boolean isTwoDice() {
		return twoDice;
	}

	public void setTwoDice(boolean twoDice) {
		this.twoDice = twoDice;
	}

	public void enableThrow(boolean enable) {
		board.getBtnLancer().setEnabled(enable);
	}

	public boolean isCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public int getDiceResult() {
		return diceResult;
	}

	public void setDiceResult(int diceResult) {
		this.diceResult = diceResult;
	}

	public boolean isThrown() {
		return hasThrown;
	}

	public void setThrown(boolean hasThrown) {
		this.hasThrown = hasThrown;
	}

	public Character getPrince() {
		return prince;
	}

	public void setPrince(Character prince) {
		this.prince = prince;
	}

	public ArrayList<Character> getOthers() {
		return others;
	}

	public void setOthers(ArrayList<Character> others) {
		this.others = others;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public boolean isHasThrown() {
		return hasThrown;
	}

	public void setHasThrown(boolean hasThrown) {
		this.hasThrown = hasThrown;
	}

	public Character getMeet() {
		return meet;
	}

	public void setMeet(Character meet) {
		this.meet = meet;
	}

	public int getResponse() {
		return response;
	}

	public void setResponse(int response) {
		this.response = response;
	}

	public boolean isPrinceTurn() {
		return princeTurn;
	}

	public void setPrinceTurn(boolean princeTurn) {
		this.princeTurn = princeTurn;
	}

	public boolean isSurpriseAttack() {
		return surpriseAttack;
	}

	public void setSurpriseAttack(boolean surpriseAttack) {
		this.surpriseAttack = surpriseAttack;
	}

}
