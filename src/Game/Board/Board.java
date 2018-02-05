package Game.Board;

import java.awt.*;

import javax.swing.*;

import com.sun.xml.internal.ws.api.config.management.policy.ManagedServiceAssertion.NestedParameters;

import oracle.jrockit.jfr.JFR;

import java.awt.event.*;
import java.util.Random;

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
	private JLabel combatLabel;
	private final String COMBAT = "Combat : ";
	private JLabel enduranceLabel;
	private final String ENDURANCE = "Endurance : ";
	private JLabel richesseLabel;
	private final String RICHESSE = "Richesse : ";
	private JButton btnLancer;
	private final String LANCER = "LANCER";
	private JLabel princeLabel;
	private final String JOUR = "Jour : ";
	private JLabel jour;
	private JFrame frame = this;

	/**
	 * Constructeur de la classe board initialisation des variables et création du
	 * plateau de jeu
	 */
	private Board() {
		createAndShowGUI();
	}

	/**
	 * Méthode de lancement, elle est a enlever car c'est le game manager qui
	 * l'exécutera
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Board();
			}
		});
	}

	/**
	 * Création de l'interface
	 */
	private void createAndShowGUI() {

		new JFrame("Barbarian Prince");

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

		// le panel c'est le BoardPanel de jeu (carte + grille + personnage)
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

		btnLancer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new DiceAnimation(frame, false, icon);
			}
		});

		JPanel panel_1 = new JPanel();
		this.getContentPane().add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("");
		panel_1.add(lblNewLabel, BorderLayout.NORTH);

		jour = new JLabel(JOUR);
		panel_1.add(jour, BorderLayout.CENTER);

		JLabel lblNewLabel_2 = new JLabel("");
		panel_1.add(lblNewLabel_2, BorderLayout.SOUTH);

		BoardPanel panel = new BoardPanel(this);

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
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

}