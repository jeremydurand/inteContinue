package Game.Board;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import Game.GameManager;

/**
 * Cette classe g�re l'animation du lanc� de d�s
 * 
 */
public class DiceAnimation {
	private int timeElapsed;
	private int timeRandom;
	private Timer timer;
	private int delay;
	private int random1 = 0;
	private int random2 = 0;

	private javax.swing.JLabel dice1Label;
	private javax.swing.JLabel dice2Label;
	private JLabel numberLabel;
	private JButton okay;
	private boolean twoDices;
	private int limit;

	/**
	 * Constructeur de la classe DiceAnimation
	 * 
	 * @param frame La fentre du jeu par dessus laquelle la boite de dialogue des d�s s'affichera
	 * @param twoDices Bool�en indiquant si il y a un d�s (false) ou deux (true)
	 * @param icon L'icon du jeu
	 * @param manager Le GameManager
	 */
	public DiceAnimation(JFrame frame, boolean twoDices, Icon icon, int limit) {
		this.twoDices = twoDices;
		this.limit = limit;
		
		dice1Label = new javax.swing.JLabel();
		dice2Label = new javax.swing.JLabel();

		JPanel jPanel = new JPanel();

		jPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		dice1Label = new JLabel();
		jPanel.add(dice1Label);
		dice1Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dice/dice" + 1 + ".jpg")));

		if (twoDices) {
			dice2Label = new JLabel();
			jPanel.add(dice2Label);
			dice2Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dice/dice" + 1 + ".jpg")));
		}

		numberLabel = new JLabel("                             	");
		jPanel.add(numberLabel);

		okay = new JButton("Ok");
		okay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane pane = getOptionPane((JComponent) e.getSource());
				pane.setValue(okay);
			}
		});
		okay.setEnabled(false);

		roll();
		//d�sactiver le bouton quitter
		JOptionPane.showOptionDialog(frame, jPanel, "Jet de d�s ...", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, icon, new Object[] { okay }, okay);
		GameManager.getInstance().setThrown(false);
		GameManager.getInstance().setDiceResult((random1 + random2));
		GameManager.getInstance().doStep();
		//GameManager.getInstance().getBoard().getBtnLancer().setEnabled(false);
	}

	/**
	 * Cette m�thode r�cup�re l'objet JOptionPane repr�sentant la boite de dialogue, il est utilis� par le bouton OK pour quitter la fenetre
	 * 
	 * @param parent Le compsant graphique parent, dans notre contexte c'est le JPanel
	 * @return pane : la boite de dialogue
	 */
	protected JOptionPane getOptionPane(JComponent parent) {
		JOptionPane pane = null;
		if (!(parent instanceof JOptionPane)) {
			pane = getOptionPane((JComponent) parent.getParent());
		} else {
			pane = (JOptionPane) parent;
		}
		return pane;
	}

	/**
	 * Cette m�thode ex�cute l'animation des d�s
	 * ici on a fait le choix de faire durer l'animation 3 secondes et un affichage al�atoire de chaque d�s tout les 0.1 secondes
	 * Soit 30 affichages al�atoires pour notre animation
	 */
	private void roll() {
		delay = 100; // milliseconds
		timeRandom = 3000;// milliseconds
		timeElapsed = 0;
		ActionListener taskPerformer = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				random1 = new Random().nextInt(limit) + 1;
				dice1Label.setIcon(
						new javax.swing.ImageIcon(getClass().getResource("/images/dice/dice" + random1 + ".jpg")));
				if (twoDices) {
					random2 = new Random().nextInt(limit) + 1;
					dice2Label.setIcon(
							new javax.swing.ImageIcon(getClass().getResource("/images/dice/dice" + random2 + ".jpg")));
				}
				increment();

			}
		};
		timer = new Timer(delay, taskPerformer);
		timer.start();
	}

	/**
	 * Cette m�thode est un increment ex�cut� � chaque passage dans le timer pour y mettre fin au bout des 3 secondes
	 */
	private void increment() {
		timeElapsed += delay;
		if (timeElapsed >= timeRandom) {
			timer.stop();
			numberLabel.setText("Nombre tir� : " + (random1 + random2));
			okay.setEnabled(true);
		}
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	
}
