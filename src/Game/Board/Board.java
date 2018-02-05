package Game.Board;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.awt.Point;

/**
 * Classe qui dessine le BoardPanel de jeu. 
 *
 * Le mécanisme de gestion des hexagones se trouve dans le fichier HexagonalUtils.java
 * 
 * @author benoit
 */
public class Board {

	private int screenWidth;
	private int screenHeight;
	private ImageIcon icon = new ImageIcon(getClass().getResource("/images/avatar_frame.png"));

	/**
	 * Constructeur de la classe board initialisation des variables et création du plateau de jeu
	 */
	public Board() {
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

		JFrame frame = new JFrame("Barbarian Prince");

		// paramétrage de la fenetre
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight() - taskBarSize;
		frame.setBounds(100, 100, screenWidth, screenHeight);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setIconImage(icon.getImage());
		// frame.setUndecorated(true);

		// le panel c'est le BoardPanel de jeu (carte + grille + personnage)
		BoardPanel panel = new BoardPanel(frame);

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
		frame.addComponentListener(new ComponentListener() {
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
		
		frame.getContentPane().add(scroll);
		frame.setVisible(true);
	}

}
