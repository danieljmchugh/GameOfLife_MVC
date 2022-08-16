import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.util.ArrayList;
import java.util.List;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameView extends javax.swing.JFrame {
	private int gridDim;
    public boolean[] startingCells;
    private boolean[] boardCells;

	private JPanel gameGrid;
	private JMenuBar menuBar;
	private JMenu lifeMenu;
	private JMenuItem runMenuItem;
	private JMenuItem stopMenuItem;
	private JMenuItem exitMenuItem;
	
	public GameView(int dimention) {
		gridDim = dimention;

		startingCells = new boolean[gridDim * gridDim];
		boardCells = new boolean[gridDim * gridDim];

		menuBar = new JMenuBar();
		lifeMenu = new JMenu("Life");
		runMenuItem = new JMenuItem("Start");
		stopMenuItem = new JMenuItem("Stop");
		exitMenuItem = new JMenuItem("Exit");

		lifeMenu.add(runMenuItem);
		lifeMenu.add(stopMenuItem);
		lifeMenu.add(exitMenuItem);
		menuBar.add(lifeMenu);

		gameGrid = createInteractiveGameGrid();
		add(gameGrid);

		setTitle("Life");
		setJMenuBar(menuBar);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		pack();
		setVisible(true);
	}

	/* Initial Game Grid presented to the played to SET initial living cells */
	private JPanel createInteractiveGameGrid() {
		JPanel gameGrid = new JPanel();
		gameGrid.setLayout(new GridLayout(gridDim, gridDim, 0, 0));
		
		for (int i = 0; i < boardCells.length - 1; i++) {
			int row = i / gridDim;
			int col = i % gridDim;
			CellPanel cellPanel = new CellPanel(i, Color.WHITE, true);
			gameGrid.add(cellPanel);
		}
		return gameGrid;
	}
	

	/* Subsequent Game Grid presented to the played to PRINT living cells */
	private JPanel createGameGrid(boolean[] board) {
		JPanel gameGrid = new JPanel();
		gameGrid.setLayout(new GridLayout(gridDim, gridDim, 0, 0));

		for (int i = 0; i < boardCells.length; i++) {
			int row = i / gridDim;
			int col = i % gridDim;
			
			CellPanel cellPanel = (board[i]) ? new CellPanel(i, Color.BLACK, false) : new CellPanel(i, Color.WHITE, false);
			gameGrid.add(cellPanel);
		}
		
		return gameGrid;
	}

	public void printGame(boolean[] board) {
		remove(gameGrid);
		
		gameGrid = createGameGrid(board);
		add(gameGrid);

		revalidate();
		repaint();
	}

	public JMenuItem getRunMenuItem() { return runMenuItem; }
	public JMenuItem getStopMenuItem() { return stopMenuItem; }
	public JMenuItem getExitMenuItem() { return exitMenuItem; }

	private class CellPanel extends JPanel implements MouseListener {
		final int PIXEL_SIZE = 20;
		public int index;
		public Color colour;

		CellPanel(int i, Color colour, boolean lisntener) {
			this.index = i;
			this.colour = colour;
			if (lisntener) {
				addMouseListener(this);
			}

			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			setPreferredSize(new Dimension(PIXEL_SIZE, PIXEL_SIZE));
		}

		public void flipColour() {
			colour = (colour == Color.WHITE) ? Color.BLACK : Color.WHITE; 
		}

		/* TODO: Read up on paintComponent() */
		@Override
		protected void paintComponent(Graphics g) {
			g.setColor(colour);
			g.fillRect(0,0, getWidth(), getHeight());
		}

		@Override
		public void mousePressed(MouseEvent event) {
			if(event.getButton() == MouseEvent.BUTTON1) {
				colour = Color.BLACK;
				startingCells[index] = true;
				repaint();
			}
			else if(event.getButton() == MouseEvent.BUTTON3) {
				colour = Color.WHITE;
				startingCells[index] = false;
				repaint();	
			}
		}
		
		/* 
		 * Gotta have empty implementation of these for MouseListener...
		 * Would use MouseAdapter, but can't inheriting 
		 * from two classes, so... :)
		 */
        public void mouseClicked(MouseEvent e) {}  
    	public void mouseEntered(MouseEvent e) {}  
    	public void mouseExited(MouseEvent e) {}
    	public void mouseReleased(MouseEvent e) {}  
	}
}