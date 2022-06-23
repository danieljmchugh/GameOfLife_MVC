import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LifeController implements Runnable {
	GameState lifeModel;
	GameGui lifeView;
	Thread runner;

	public LifeController(GameState model, GameGui view) {
		lifeModel = model;
		lifeView = view;

		/* Add listener to Run and Exit */
		lifeView.getRunMenuItem().addActionListener(new LifeControllerActionListener(this));
		lifeView.getExitMenuItem().addActionListener(new LifeControllerActionListener(this));
	}

	
	public void start() {
		if (runner == null) {
			runner = new Thread(this);
			runner.start();
		}
	}

	public void run() {
		lifeModel.initGameState(lifeView.startingCells);
		
		/* This is done in a new thread, this is so prevent the main thread being monopolised and can therefore
		   execute the repaint() request done in printGame()
		*/
     	while (true) {
			lifeModel.updateGameState();
            lifeView.printGame(lifeModel.liveCells);
            
            try {
            	Thread.sleep(500);
            }
            catch (Exception e) {}
        }		
	}


	class LifeControllerActionListener implements ActionListener {
		LifeController lifeController;

		public LifeControllerActionListener(LifeController controller) {
			lifeController = controller;
		}

		@Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == lifeView.getRunMenuItem()) {
                start();
            }

            else if (e.getSource() == lifeView.getExitMenuItem()) {
                System.exit(0);
            }
        }
	}	
}

