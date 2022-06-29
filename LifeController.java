import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class LifeController implements Runnable {
	GameModel lifeModel;
	GameView lifeView;
	Thread gameThread;
	
	@Override
	public void run() {
		/* 
			NB!!!

			This approach does NOT pause the process and takes up LOTS system resources.
			Look into using wait()/notify() next for better implementation
		*/

		while (true) {
			if (!lifeModel.isStopped) {
				lifeModel.updateGameModel();
	        	lifeView.printGame(lifeModel.liveCells);
			}

			try {
				Thread.sleep(500);
			} catch (Exception e) {}
    	}
	}	

	public LifeController(GameModel model, GameView view) {
		lifeModel = model;
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
		
		} else {
			lifeModel.isStopped = false;
		}
	}

	public void stop() {
		lifeModel.isStopped = true;
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

// public void runLife() {
// 	lifeModel.initGameModel(lifeView.startingCells);
// 	System.out.println("runLife(): " + Thread.currentThread().getName());
// 	/* This is done in a new thread to prevent the main thread being monopolised. Allowing the requests
// 	   to repaint() to execute in printGame()
// 	*/
// 	new SwingWorker() {
// 		@Override
// 		protected Object doInBackground() {
// 			System.out.println("SwingWorker(): " + Thread.currentThread().getName());
// 	     	while (true) {
// 				try {
// 	            	Thread.sleep(500);
// 	            } catch (Exception e) {}

// 				lifeModel.updateGameModel();
// 				lifeView.printGame(lifeModel.liveCells);
// 	        }
// 		}
// 	}.execute();
// }
