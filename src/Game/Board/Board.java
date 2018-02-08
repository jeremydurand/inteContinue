package Game.Board;

import java.awt.*;
import javax.swing.*;

import Game.GameManager;

import java.awt.event.*;

/**
 * Classe qui dessine le BoardPanel de jeu. Le mécanisme de gestion des
 * hexagones se trouve dans le fichier HexagonalUtils.java
 * 
 * @author benoi
 *
 */
public class Board extends JFrame {

	private int screenWidth;
	private int screenHeight;
	private ImageIcon icon = new ImageIcon(getClass().getResource("/images/avatar_frame.png"));
	private ImageIcon enemyIcon = new ImageIcon(getClass().getResource("/images/versus.png"));
	private JLabel combatLabel;
	private final String COMBAT = "Combat : ";
	private JLabel enduranceLabel;
	private final String ENDURANCE = "Endurance : ";
	private JLabel richesseLabel;
	private final String RICHESSE = "Richesse : ";
	private JButton btnLancer;
	private final String LANCER = "LANCER LES DES";
	private final String OPPOSANT = "Opposant : ";
	private JLabel princeLabel;
	private final String JOUR = "Jour : ";
	private JLabel jourLabel;
	private JFrame frame = this;

	private BoardPanel panel;
	private JLabel enemyLabel;
	private JLabel enemyCombatLabel;
	private JLabel enemyEnduranceLabel;
	private JLabel enemyRichesseLabel;
	private JLabel enemy;

	public BoardPanel getPanel() {
		return panel;
	}

	public void setPanel(BoardPanel panel) {
		this.panel = panel;
	}

	/**
	 * Constructeur de la classe board initialisation des variables et création du
	 * plateau de jeu
	 */
	public Board() {
		createAndShowGUI();
	}

	/**
	 * Création de l'interface
	 */
	private void createAndShowGUI() {

		this.setTitle("Barbarian Prince");
		// paramétrage de la fenetre
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight() - taskBarSize;
		this.setBounds(100, 100, screenWidth, screenHeight);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setIconImage(icon.getImage());
		// frame.setUndecorated(true);

		JPanel barreInfos = new JPanel();
		this.getContentPane().add(barreInfos, BorderLayout.NORTH);

		princeLabel = new JLabel();
		princeLabel.setIcon(icon);
		barreInfos.add(princeLabel);

		combatLabel = new JLabel(COMBAT);
		barreInfos.add(combatLabel);

		enduranceLabel = new JLabel(ENDURANCE);
		barreInfos.add(enduranceLabel);

		richesseLabel = new JLabel(RICHESSE);
		barreInfos.add(richesseLabel);

		btnLancer = new JButton(LANCER);
		barreInfos.add(btnLancer);

		enemyLabel = new JLabel();
		enemyLabel.setIcon(enemyIcon);
		barreInfos.add(enemyLabel);

		enemyCombatLabel = new JLabel(COMBAT);
		barreInfos.add(enemyCombatLabel);

		enemyEnduranceLabel = new JLabel(ENDURANCE);
		barreInfos.add(enemyEnduranceLabel);

		enemyRichesseLabel = new JLabel(RICHESSE);
		barreInfos.add(enemyRichesseLabel);

		enemy = new JLabel(OPPOSANT);
		barreInfos.add(enemy);
		
		btnLancer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new DiceAnimation(frame, GameManager.getInstance().isTwoDice(), icon,
						GameManager.getInstance().getDiceLimit());
			}
		});

		JPanel panel_1 = new JPanel();
		this.getContentPane().add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("");
		panel_1.add(lblNewLabel, BorderLayout.NORTH);

		jourLabel = new JLabel(JOUR + GameManager.getInstance().getDay());
		panel_1.add(jourLabel, BorderLayout.CENTER);

		JLabel lblNewLabel_2 = new JLabel("");
		panel_1.add(lblNewLabel_2, BorderLayout.SOUTH);

		// le panel c'est le BoardPanel de jeu (carte + grille + personnage)
		panel = new BoardPanel(this);
		JScrollPane scroll = new JScrollPane(panel);

		// déplacement avec les fleches de haut en bas
		JScrollBar vertical = scroll.getVerticalScrollBar();
		vertical.setUnitIncrement(16);
		InputMap imV = vertical.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		imV.put(KeyStroke.getKeyStroke("DOWN"), "positiveUnitIncrement");
		imV.put(KeyStroke.getKeyStroke("UP"), "negativeUnitIncrement");

		// déplacement avec les fleches de gauche a droite
		JScrollBar horizontal = scroll.getHorizontalScrollBar();
		horizontal.setUnitIncrement(16);
		InputMap imH = horizontal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		imH.put(KeyStroke.getKeyStroke("RIGHT"), "positiveUnitIncrement");
		imH.put(KeyStroke.getKeyStroke("LEFT"), "negativeUnitIncrement");

		// le mouse drag pour se déplacer sur la carte
		MouseAdapter ma = new MouseAdapter() {

			private Point origin;

			@Override
			public void mousePressed(MouseEvent e) {
				origin = new Point(e.getPoint());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (origin != null) {
					JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, panel);
					if (viewPort != null) {
						int deltaX = origin.x - e.getX();
						int deltaY = origin.y - e.getY();

						Rectangle view = viewPort.getViewRect();
						view.x += deltaX;
						view.y += deltaY;

						panel.scrollRectToVisible(view);
					}
				}
			}

		};

		panel.addMouseListener(ma);
		panel.addMouseMotionListener(ma);

		// un listener pour réagir en cas de changement de la taille de la fenetre
		this.addComponentListener(new ComponentListener() {
			@Override
			public void componentHidden(ComponentEvent arg0) {
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				panel.repaint();
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
			}
		});

		this.getContentPane().add(scroll, BorderLayout.CENTER);
		this.setVisible(true);
		GameManager.getInstance().setPanel(panel);
		GameManager.getInstance().setBoard(this);
		GameManager.getInstance().init();
		JOptionPane.showMessageDialog(this, "Bonjour, pour démarrer la partie veuillez lancer les dés",
				"Bienvenue sur Barbarian Prince", JOptionPane.INFORMATION_MESSAGE, icon);
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public JLabel getCombatLabel() {
		return combatLabel;
	}

	public void setCombatLabel(JLabel combatLabel) {
		this.combatLabel = combatLabel;
	}

	public void setAttackLabel(int attack) {
		this.combatLabel.setText(COMBAT + attack);
	}

	public JLabel getEnduranceLabel() {
		return enduranceLabel;
	}

	public void setEnduranceLabel(JLabel enduranceLabel) {
		this.enduranceLabel = enduranceLabel;
	}

	public void setLifeLabel(int life) {
		this.enduranceLabel.setText(ENDURANCE + life);
	}

	public JLabel getRichesseLabel() {
		return richesseLabel;
	}

	public void setRichesseLabel(JLabel richesseLabel) {
		this.richesseLabel = richesseLabel;
	}

	public void setGoldLabel(int richesse) {
		this.richesseLabel.setText(RICHESSE + richesse);
	}

	public JLabel getPrinceLabel() {
		return princeLabel;
	}

	public void setPrinceLabel(JLabel princeLabel) {
		this.princeLabel = princeLabel;
	}

	public JLabel getJourLabel() {
		return jourLabel;
	}

	public void setJourLabel(JLabel jourLabel) {
		this.jourLabel = jourLabel;
	}

	public void setDayText(int day) {
		this.jourLabel.setText(JOUR + day);
	}

	public JButton getBtnLancer() {
		return btnLancer;
	}

	public void setBtnLancer(JButton btnLancer) {
		this.btnLancer = btnLancer;
	}

	public JLabel getEnemyLabel() {
		return enemyLabel;
	}

	public void setEnemyLabel(JLabel enemyLabel) {
		this.enemyLabel = enemyLabel;
	}

	public JLabel getEnemyCombatLabel() {
		return enemyCombatLabel;
	}

	public void setEnemyCombatLabel(JLabel enemyCombatLabel) {
		this.enemyCombatLabel = enemyCombatLabel;
	}

	public JLabel getEnemyEnduranceLabel() {
		return enemyEnduranceLabel;
	}

	public void setEnemyEnduranceLabel(JLabel enemyEnduranceLabel) {
		this.enemyEnduranceLabel = enemyEnduranceLabel;
	}

	public JLabel getEnemyRichesseLabel() {
		return enemyRichesseLabel;
	}

	public void setEnemyLifeText(int life) {
		enemyEnduranceLabel.setText(ENDURANCE + life);
	}

	public void setEnemyAttackText(int attack) {
		enemyCombatLabel.setText(COMBAT + attack);
	}

	public void setEnemyGoldText(int gold) {
		enemyRichesseLabel.setText(RICHESSE + gold);
	}

	public void setEnemyName(String name) {
		enemy.setText(OPPOSANT + name);
	}
	public void resetOpponent() {
		enemyEnduranceLabel.setText(ENDURANCE);
		enemyCombatLabel.setText(COMBAT);
		enemyRichesseLabel.setText(RICHESSE );
		enemy.setText(OPPOSANT);
	}
	
	public void setEnemyRichesseLabel(JLabel enemyRichesseLabel) {
		this.enemyRichesseLabel = enemyRichesseLabel;
	}

}