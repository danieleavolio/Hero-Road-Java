
package application;

import javax.swing.JOptionPane;

import application.controller.MovementController;
import application.model.Enemy;
import application.view.GraphicPanel;
import utilities.AnimationHandler;
import utilities.EstrazioneStringhe;

public class GameLoop implements Runnable {

	private GraphicPanel gp;
	private MovementController mc;
	private Game game = Game.getInstance();
	EstrazioneStringhe eS = EstrazioneStringhe.getInstance();
	// SALVARE HIGHSCORE
	int end = 0;

	public GameLoop(GraphicPanel gp, MovementController mc) {
		this.gp = gp;
		this.mc = mc;
	}

	@Override
	public void run() {
		double target = 60.0; // tickPerSecond
		double nsPerTick = 1000000000.0 / target; // nanosecondi per tick
		double unprocessed = 0.0;
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis(); // FPS & TPS CALCULATION
		int FPS = 0, TPS = 0;
		boolean canRender = false;
		while (game.running) {		// quando il gioco gira, gira  il thread, sennò si interrompe
			long now = System.nanoTime();
			AnimationHandler.getInstance().updatePlayer();
			AnimationHandler.getInstance().updateEnemy(Enemy.FROG);
			AnimationHandler.getInstance().updateEnemy(Enemy.BAT);
			AnimationHandler.getInstance().updateEnemy(Enemy.GHOST);
			AnimationHandler.getInstance().updateEnemy(Enemy.SCHELETRO);
			AnimationHandler.getInstance().updateEnemy(Enemy.BOSS);
			
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			// STAMPA FPS
			if (!game.pause && game.getCharacter().isAlive() && !game.winConditionCheck()) {
				if (!mc.comandi().isEmpty())
					mc.movements();
				game.update();
			}
			gp.update();

			/*if (game.pause) // PAUSA DEL GIOCO
				gp.drawPause();*/

			if (!game.getCharacter().isAlive()) {
				game.checkScore();
				game.setHighsScore();
				// Prendi il numero di monete che hai e sommalo a quello che hai nel file. Poi
				// salva il file con il nuovo numero di monete
				if (end == 0) {
					eS.aggiungiSoldi(game.getCoins());
					end = 1;
				}
			}

			if (game.winConditionCheck()) {
				game.checkScore();
				game.setHighsScore();
				// Prendi il numero di monete che hai e sommalo a quello che hai nel file. Poi
				// salva il file con il nuovo numero di monete
				if (end == 0) {
					eS.aggiungiSoldi(game.getCoins());
					end = 1;
				}
			}

			// STAMPA FPSs
			if (unprocessed >= 1) {
				unprocessed--;
				TPS++;
				canRender = true;
			} else
				canRender = false;

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "UNEXPECTED ERROR", "THREAD ERROR", JOptionPane.ERROR_MESSAGE);
				break;
			}
			

			if (canRender)
				FPS++;
			if (System.currentTimeMillis() - 1000 > timer) {
				timer += 1000;
				System.out.println("FPS: " + FPS + " | TPS: " + TPS);
				FPS = 0;
				TPS = 0;
			}
		}
		Thread.currentThread().interrupt();
	}
}
