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
	private int gridRows;
    private int gridCols;
    public List<Cell> startingCells = new ArrayList<Cell>();

	private JPanel gameGrid;
	private JMenuBar menuBar;
	private JMenu lifeMenu;
	private JMenuItem runMenuItem;
	private JMenuItem stopMenuItem;
	private JMenuItem exitMenuItem;
	
	public GameView(int rows, int cols) {
		this.gridRows = rows;
		this.gridCols = cols;
		
		this.menuBar = new JMenuBar();
		this.lifeMenu = new JMenu("Life");
		this.runMenuItem = new JMenuItem("Start");
		this.stopMenuItem = new JMenuItem("Stop");
		this.exitMenuItem = new JMenuItem("Exit");

		this.lifeMenu.add(runMenuItem);
		this.lifeMenu.add(stopMenuItem);
		this.lifeMenu.add(exitMenuItem);
		this.menuBar.add(lifeMenu);

		this.gameGrid = createInteractiveGameGrid();
		this.add(gameGrid);

		this.setTitle("Life");
		this.setJMenuBar(menuBar);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		this.pack();
		this.setVisible(true);
	}

	/* Initial Game Grid presented to the played to SET INITIAL living cells */
	private JPanel createInteractiveGameGrid() {
		JPanel gameGrid = new JPanel();
		gameGrid.setLayout(new GridLayout(gridRows, gridCols, 0, 0));
		
		for (int row = 0; row < gridRows; row++) {
			for (int col = 0; col < gridCols; col++) {
				CellPanel cellPanel = new CellPanel(row, col, true);
				gameGrid.add(cellPanel);
			}
		}
		return gameGrid;
	}

	/* Subsequent Game Grid presented to the played to PRINT living cells */
	private JPanel createGameGrid(List<Cell> livingCells) {
		JPanel gameGrid = new JPanel();
		gameGrid.setLayout(new GridLayout(gridRows, gridCols, 0, 0));

		for (int row = 0; row < gridRows; row++) {
			for (int col = 0; col < gridCols; col++) {
				CellPanel cellPanel = new CellPanel(row, col, false);
				
				/* Default value as white, but change to black if it matches a living cell */
				for (Cell c : livingCells) {
					if ((c.getRow() == row) && (c.getCol() == col)) {
						cellPanel.setBackgroundColor(Color.BLACK);		
					}
				}
				gameGrid.add(cellPanel);
			}
		}
		return gameGrid;
	}

	public void printGame(List<Cell> livingCells) {
		this.remove(gameGrid);
		
		this.gameGrid = createGameGrid(livingCells);
		this.add(gameGrid);

		this.revalidate();
		this.repaint();
	}

	public JMenuItem getRunMenuItem() { return runMenuItem; }
	public JMenuItem getStopMenuItem() { return stopMenuItem; }
	public JMenuItem getExitMenuItem() { return exitMenuItem; }

	private class CellPanel extends JPanel implements MouseListener {
		static final int PIXEL_SIZE = 20;
		int row;
		int col;
		Color backgroundColor;

		CellPanel(int row, int col, boolean lisntener) {
			this.backgroundColor = Color.WHITE;
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			this.setPreferredSize(new Dimension(PIXEL_SIZE, PIXEL_SIZE));
			if (lisntener) {
				this.addMouseListener(this);
			}
			this.row = row;
			this.col = col;
		}

		void setBackgroundColor(Color color) {
			this.backgroundColor = color;
		}

		@Override
		protected void paintComponent(Graphics g) {
			g.setColor(backgroundColor);
			g.fillRect(0,0, getWidth(), getHeight());
		}

		@Override
		public void mousePressed(MouseEvent event) {
			if(event.getButton() == MouseEvent.BUTTON1) {
				this.setBackgroundColor(Color.BLACK);
				startingCells.add(new Cell(this.row, this.col));
				this.repaint();
			}
			else if(event.getButton() == MouseEvent.BUTTON3) {
				this.setBackgroundColor(Color.WHITE);

				for (int i = 0; i < startingCells.size(); i++) {
					if((startingCells.get(i).getRow() == this.row) && (startingCells.get(i).getCol() == this.col)) {
						startingCells.remove(i);
					}
				}
				this.repaint();	
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
