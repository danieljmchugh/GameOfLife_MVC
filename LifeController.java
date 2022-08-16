import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class LifeController implements Runnable { // implements Runnable 
	GameModel lifeModel;
	GameView lifeView;
	Thread gameThread;

	public LifeController(GameModel model, GameView view) {
		lifeModel = model;

		// for (int i = 0; i < 5; i++) {
		// 	try {
		// 		// System.out.print("\033[H\033[2J");  
		//     	// System.out.flush();
		// 	    model.printBoard();
		// 	    model.updateGameModel();
		// 	    Thread.sleep(750);
		// 	} catch(InterruptedException ex) {
		// 	    Thread.currentThread().interrupt();
		// 	}
		// }
		
		// System.exit(0);

		/* Uncomment below after GameModel refactor is done...*/

		lifeView = view;
		
		/* Add listener to Run and Exit */

		lifeView.getRunMenuItem().addActionListener(new LifeControllerActionListener(this));
		lifeView.getStopMenuItem().addActionListener(new LifeControllerActionListener(this));
		lifeView.getExitMenuItem().addActionListener(new LifeControllerActionListener(this));
	}

	public void start() {
		if (gameThread == null) {
			gameThread = new Thread(this);
			lifeModel.initGameModel(lifeView.startingCells);
			gameThread.start();
		}
	}

	public synchronized void stop() {
		lifeModel.isStopped = true;
	}

	@Override
	public synchronized void run() {
		while (true) {
			lifeModel.updateGameModel();
        	lifeView.printGame(lifeModel.gameGrid);
			
			try {
				Thread.sleep(500);
			} catch (Exception e) {}
    	}
	}

	private class LifeControllerActionListener implements ActionListener {
		LifeController lifeController;

		public LifeControllerActionListener(LifeController controller) {
			lifeController = controller;
		}

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == lifeView.getRunMenuItem()) {
                start();
            }
            else if (e.getSource() == lifeView.getStopMenuItem()) {
                stop();	
            }
            else if (e.getSource() == lifeView.getExitMenuItem()) {
                System.exit(0);
            }
        }
	}
}
