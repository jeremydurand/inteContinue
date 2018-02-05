package Game.Board;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * Classe JPanel qui contient les hexagones et la carte en fond
 * 
 * @author benoit
 */
class BoardPanel extends JPanel {

	// déclarations des couleurs utilisées pour les tuiles
	final static Color COLOURCELL = Color.ORANGE;
	final static Color COLOURGRID = Color.BLACK;
	final static Color COLOURRIGHT = Color.GREEN;
	final static Color COLOURORWRONG = Color.RED;
	
	// par défaut le personnage apparait au coordonnées x0 y0
	private int avatarX = 0;
	private int avatarY = 0;
	private final ImageIcon AVATAR = new ImageIcon(getClass().getResource("/images/avatar_dark.png"));
	
	//éléments relatifs à la fenetre
	private JFrame frame; 
	private int frameWidth;
	private Cursor cursor;

	//valeurs des bordures la bordure y est fixe mais la bordure x s'adapte à la taille de la fenetre
	private int BORDERX = 0;
	private final static int BORDERY = 0;
	
	//élément relatifs à la carte de fond
	private final ImageIcon MAP = new ImageIcon(getClass().getResource("/images/map_bg.jpg"));
	private final int MAP_HEIGHT = MAP.getIconHeight();
	private final int MAP_WIDTH = MAP.getIconWidth();
	
	//élément relatifs aux hexagones
	private final int WIDTH_SIZE = 20; // board size x
	private final int HEIGH_SIZE = 23; // board size y
	private final int HEXSIZE = 70; // hex size in pixels
	private int neighbourRank = 1;
	int[][] board = new int[WIDTH_SIZE][HEIGH_SIZE];
	
	/**
	 * Contructeur, il spécifie un écouteur de base et de mouvement pour les interactions de la souris sur le BoardPanel
	 */
	public BoardPanel(JFrame frame) {
		
		this.frame = frame;
		
		//initialisation des hexagones
		HexagonalUtils.setXYasVertex(true); // RECOMMENDED: leave this as FALSE.
		HexagonalUtils.setHeight(HEXSIZE);// Either setHeight or setSize must be run to initialize the hex
		for (int i = 0; i < WIDTH_SIZE; i++) {
			for (int j = 0; j < HEIGH_SIZE; j++) {
				board[i][j] = -1;
			}
		}
		
		Dimension mapSize = new Dimension(MAP_WIDTH, MAP_HEIGHT);
		this.setBorder(BorderFactory.createLineBorder(Color.red));
		this.setPreferredSize(mapSize);
		MyMouseListener ml = new MyMouseListener();
		addMouseListener(ml);
		addMouseMotionListener(ml);
	}

	/**
	 * Méthode qui dessine le plateau
	 * 
	 * @param g Graphics élément graphique du plateau
	 */
	public void paintComponent(Graphics g) {
		frameWidth = frame.getWidth();
		frame.setCursor(cursor);
		// la grille sera décallée en fonction de la taille de l'écran pour bien
		// s'ajuster avec la carte
		if (this.getWidth() > MAP_WIDTH) {
			BORDERX = ((this.getWidth() - MAP_WIDTH) / 2)+20;
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
		if (MAP != null) {
			Insets insets = getInsets();

			int width = getWidth() - 1 - (insets.left + insets.right);
			int height = getHeight() - 1 - (insets.top + insets.bottom);

			int x = (width - MAP.getImage().getWidth(this)) / 2;
			int y = (height - MAP.getImage().getHeight(this)) / 2;

			g2.drawImage(MAP.getImage(), x, y, null);
		}

		repaint();
		for (int i = 0; i < WIDTH_SIZE; i++) {
			for (int j = 0; j < HEIGH_SIZE; j++) {
				if (board[i][j] != 0) {
					board[i][j] = -1;
				}
			}
		}
		neighbour(avatarX, avatarY, neighbourRank);
		// dessin des hexagones
		for (int i = 0; i < WIDTH_SIZE; i++) {
			for (int j = 0; j < HEIGH_SIZE; j++) {
				// l'avatar n'apparaitra qu'a la position qu'on lui a fixé
				if (i == avatarX && avatarY == j) {
					HexagonalUtils.drawHex(i, j, g2, AVATAR);
				} else {
					HexagonalUtils.drawHex(i, j, g2, null);
				}
				HexagonalUtils.fillHex(i, j, board[i][j], g2);
			}
		}
	}

	/**
	 * Classe MouseListener qui écoute toutes les interactions faite par la souris sur le BoardPanel (déplacement clique ect..)
	 * 
	 * @author benoit
	 *
	 */
	class MyMouseListener extends MouseAdapter {
		/**
		 * Un clique peut déplacer le personnage si la case est assez proche (1 case sans cheval, 2 cases avec cheval)
		 */
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
			// on redessine le BoardPanel avec la nouvelle position de l'avatar
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

		/**
		 * Mise a jour de l'affichage au niveau de la postion de la souris qui indique si un clique est possible ou non
		 * 
		 * @param e MouseEvent evenement de la souris
		 */
		private void update(MouseEvent e) {
			for (int i = 0; i < WIDTH_SIZE; i++) {
				for (int j = 0; j < HEIGH_SIZE; j++) {
					board[i][j] = -1;
				}
			}
			neighbour(avatarX, avatarY, neighbourRank);
			Point p = new Point(HexagonalUtils.pxtoHex(e.getX(), e.getY()));
			if (p.x < 0 || p.y < 0 || p.x >= WIDTH_SIZE || p.y >= HEIGH_SIZE)
				return;
			if (p.x == avatarX && p.y == avatarY) {
				cursor = new Cursor(Cursor.DEFAULT_CURSOR);
			} else if (board[p.x][p.y] == 1) {
				cursor = new Cursor(Cursor.HAND_CURSOR);
			} else {
				board[p.x][p.y] = 0;
				cursor = new Cursor(Cursor.DEFAULT_CURSOR);
			}
			/*
			 * if (board[p.x][p.y] != 1 && (p.x != avatarX || p.y != avatarY)) {
			 * board[p.x][p.y] = 0; cursor = new Cursor(Cursor.DEFAULT_CURSOR); }else {
			 * cursor = new Cursor(Cursor.HAND_CURSOR); }
			 */
			repaint();
			// board[p.x][p.y] = 1;
			// neighbour(p.x, p.y, 1);
			// repaint();
		}

	}

	/**
	 * Cette méthode trouve les cases voisines autour du personnage
	 * 
	 * TODO : Prend en compte des voisins de rang 2 (avec cheval)
	 * 
	 * @param x position x du personnage
	 * @param y position y du personnage
	 * @param n rang n de voisins a prendre en compte (1 sans cheval 2 avec)
	 */
	private void neighbour(int x, int y, int n) {

		for (int i = 1; i <= n; i++) {
			if (x % 2 == 0) {
				if (x - i >= 0 && y - i >= 0) {
					board[x - i][y - i] = 1;
				}
				if (y - i >= 0) {
					board[x][y - i] = 1;
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
