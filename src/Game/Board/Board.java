package Game.Board;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.awt.Point;


/**
 * Classe qui dessine le plateau de jeu. Le mécanisme de gestion des hexagones
 * se trouve dans le fichier HexagonalUtils.java.
 * 
 * Ecrit par benoit jaouen 11/01/2018
 * @author benoi
 *
 */
public class Board {

	// constants and global variables
	final static Color COLOURCELL = Color.ORANGE;
	final static Color COLOURGRID = Color.BLACK;
	final static Color COLOURRIGHT = Color.GREEN;
	final static Color COLOURORWRONG = Color.RED;
	final static int WIDTH_SIZE = 20; // board size x
	final static int HEIGH_SIZE = 23; // board size y
	final static int HEXSIZE = 70; // hex size in pixels
	static int BORDERX = 0;
	final static int BORDERY = 0;

	int[][] board = new int[WIDTH_SIZE][HEIGH_SIZE];
	private JFrame frame;
	private int frameWidth;
	private DrawingPanel panel;
	private int screenWidth;
	private int screenHeight;
	private ImageIcon avatar = new ImageIcon(getClass().getResource("/images/avatar_dark.png"));
	private ImageIcon icon = new ImageIcon(getClass().getResource("/images/avatar_frame.png"));
	private ImageIcon map = new ImageIcon(getClass().getResource("/images/map_bg.jpg"));
	private int avatarX = 0;// par défaut le personnage apparait au coordonnées x0 y0
	private int avatarY = 0;
	private int mapWidth = map.getIconWidth();
	private int mapHeight = map.getIconHeight();

	private Board() {
		initGame();
		createAndShowGUI();

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Board();
			}
		});
	}

	void initGame() {
		HexagonalUtils.setXYasVertex(true); // RECOMMENDED: leave this as FALSE.
		HexagonalUtils.setHeight(HEXSIZE);// Either setHeight or setSize must be run to initialize the hex
		for (int i = 0; i < WIDTH_SIZE; i++) {
			for (int j = 0; j < HEIGH_SIZE; j++) {
				board[i][j] = -1;
			}
		}
	}

	private void createAndShowGUI() {

		frame = new JFrame("Barbarian Prince");

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
		frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// frame.setUndecorated(true);

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

		// le panel c'est le plateau de jeu (carte + grille + personnage)
		panel = new DrawingPanel();
		Dimension mapSize = new Dimension(mapWidth, mapHeight);
		panel.setBorder(BorderFactory.createLineBorder(Color.red));
		panel.setPreferredSize(mapSize);

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

		frame.getContentPane().add(scroll);

		frame.setVisible(true);
	}

	class DrawingPanel extends JPanel {

		public DrawingPanel() {
			MyMouseListener ml = new MyMouseListener();
			addMouseListener(ml);
			addMouseMotionListener(ml);
		}

		public void paintComponent(Graphics g) {
			frameWidth = frame.getWidth();
			// la grille sera décallée en fonction de la taille de l'écran pour bien
			// s'ajuster avec la carte
			if (frameWidth > mapWidth) {
				BORDERX = (frameWidth - mapWidth) / 2;
				HexagonalUtils.setBorders(BORDERX, BORDERY);
			} else {
				BORDERX = 20;
				HexagonalUtils.setBorders(BORDERX, BORDERY);
			}

			Graphics2D g2 = (Graphics2D) g;

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// les hexagones
																									// apparaissent plus
																									// nettes
			super.paintComponent(g2);

			// ajout de la texture de la map en fond
			if (map != null) {
				Insets insets = getInsets();

				int width = getWidth() - 1 - (insets.left + insets.right);
				int height = getHeight() - 1 - (insets.top + insets.bottom);

				int x = (width - map.getImage().getWidth(this)) / 2;
				int y = (height - map.getImage().getHeight(this)) / 2;

				g2.drawImage(map.getImage(), x, y, null);
			}

			repaint();
			for (int i = 0; i < WIDTH_SIZE; i++) {
				for (int j = 0; j < HEIGH_SIZE; j++) {
					if(board[i][j] != 0) {						
						board[i][j] = -1;
					}
				}
			}
			neighbour(avatarX, avatarY, 1);
			// dessin des hexagones
			for (int i = 0; i < WIDTH_SIZE; i++) {
				for (int j = 0; j < HEIGH_SIZE; j++) {
					// l'avatar n'apparaitra qu'a la position qu'on lui a fixé
					if (i == avatarX && avatarY == j) {
						HexagonalUtils.drawHex(i, j, g2, avatar);
					} else {
						HexagonalUtils.drawHex(i, j, g2, null);
					}
					HexagonalUtils.fillHex(i, j, board[i][j], g2);
				}
			}
		}

		class MyMouseListener extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {

				Point p = new Point(HexagonalUtils.pxtoHex(e.getX(), e.getY()));
				if (p.x < 0 || p.y < 0 || p.x >= WIDTH_SIZE || p.y >= HEIGH_SIZE)
					return;

				// les coordonnées de l'avatar sont celles a l'origine du clic de la souris
				if (board[p.x][p.y] == 1) {
					avatarX = p.x;
					avatarY = p.y;
				}

				/*
				 * JViewport viewPort = (JViewport)
				 * SwingUtilities.getAncestorOfClass(JViewport.class, panel); if (viewPort !=
				 * null) {
				 * 
				 * Rectangle view = viewPort.getViewRect(); view.x = p.x+e.getX(); view.y =
				 * p.y+e.getY();
				 * 
				 * panel.scrollRectToVisible(view); }
				 */
				// on redessine le plateau avec la nouvelle position de l'avatar
				repaint();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// update(e);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				update(e);
			}

			private void update(MouseEvent e) {
				Point p = new Point(HexagonalUtils.pxtoHex(e.getX(), e.getY()));
				if (p.x < 0 || p.y < 0 || p.x >= WIDTH_SIZE || p.y >= HEIGH_SIZE)
					return;
				if(board[p.x ][p.y] != 1) {
					board[p.x][p.y] = 0;
				}
				repaint();
				// board[p.x][p.y] = 1;
				// neighbour(p.x, p.y, 1);
				// repaint();
			}

		}

		private void neighbour(int x, int y, int n) {

			for (int i = 1; i <= n; i++) {
				if (x % 2 == 0) {
					if (x - i >= 0 && y - i >= 0) {
						board[x - i][y - i] = 1;
					}
					if (y - i >= 0) {
						board[x][y - i] =1;
					}
					if (x + i < WIDTH_SIZE && y - i >= 0) {
						board[x + i][y - i] = 1;
					}
					if (x + i < WIDTH_SIZE) {
						board[x + i][y] = 1;
					}
					if (y + i < HEIGH_SIZE) {
						board[x][y + i] = 1;
					}
					if (x - i >= 0) {
						board[x - i][y] = 1;
					}
				} else {
					if (x - i >= 0) {
						board[x - i][y] = 1;
					}
					if (y - i >= 0) {
						board[x][y - i] = 1;
					}
					if (x + i < WIDTH_SIZE) {
						board[x + i][y] = 1;
					}
					if (x + i < WIDTH_SIZE && y + 1 < HEIGH_SIZE) {
						board[x + i][y + 1] = 1;
					}
					if (y + i < HEIGH_SIZE) {
						board[x][y + i] = 1;
					}
					if (x - i >= 0 && y + i < HEIGH_SIZE) {
						board[x - i][y + i] = 1;
					}
				}
			}
		}
	}
}