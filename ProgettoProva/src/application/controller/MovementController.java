package application.controller;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import application.Game;
import application.model.MyCharacter;
import games.PlayerAnimation;
import games.TitleScreen;
import utilities.AnimationHandler;
import utilities.AudioClass;

public class MovementController implements KeyListener {
	private static ArrayList<Integer> comandi = new ArrayList<Integer>();
	AnimationHandler handler = AnimationHandler.getInstance();
	AudioClass gestoreAudio = AudioClass.getInstance();
	
	private static final int ATTACK = 6;
	private static final int HERO = 3;
	private static final int MUSICSCONFITTA = 7;

	private static Integer RIGHT = 0;
	private static Integer LEFT = 1;
	private static Integer UP = 2;
	private static Integer SPACE = 3;
	private boolean canAddAttack = true;
	private JPanel p;

	Game game = Game.getInstance();

	public MovementController(JPanel p) {
		this.p = p;
	}

	@Override
	public void keyPressed(KeyEvent e) {

		/*if (e.getKeyChar() == KeyEvent.VK_ESCAPE) { // chiude il gioco
			System.err.println("Closing the game");
			System.exit(0);
		}*/

		if (!game.pause) // mette pausa
			if (e.getKeyCode() == KeyEvent.VK_P) {
				game.pause = true;
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					JOptionPane.showMessageDialog(null, "UNEXPECTED ERROR", "THREAD ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		if ((!game.getCharacter().isAlive()) && (e.getKeyCode() == KeyEvent.VK_R)) {
			game.resetCoins();
			game.bitesTheDust(); // Resetta la partita per riprovare
			gestoreAudio.nuovaOP(MUSICSCONFITTA, HERO);
		}
		if (!game.getCharacter().isAlive() && e.getKeyCode() == KeyEvent.VK_M) {
			// ritorna al menu principale
			Container c = p.getParent();
			c.remove(p);
			c.add(new TitleScreen());
			c.revalidate();
			Game.getInstance().running = false;
		}
		
		if(game.pause == true && game.getCharacter().isAlive() && e.getKeyCode() == KeyEvent.VK_M) {
			// ritorna al menu principale
			Container c = p.getParent();
			c.remove(p);
			c.add(new TitleScreen());
			c.revalidate();
			Game.getInstance().running = false;
			game.pause = false;
		}

		if (game.getCharacter().isAlive() && game.winConditionCheck()) {
			if (e.getKeyCode() == KeyEvent.VK_M) {
				// ritorna al menu principale
				Container c = p.getParent();
				c.remove(p);
				c.add(new TitleScreen());
				c.revalidate();
				Game.getInstance().running = false;
			}
		}

		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			if (!isInside(RIGHT)) {
				comandi.add(RIGHT);

			}
			break;
		case KeyEvent.VK_LEFT:
			if (!isInside(LEFT)) {
				comandi.add(LEFT);
			}
			break;

		default:;
			break;
		}

		if (e.getKeyCode() == KeyEvent.VK_UP && game.getCharacter().isCanJump() && !game.getCharacter().isAttacking())
			// Se sono a terra, allora posso aggiungere il comando di saltare
			if (!isInside(UP)) {
				comandi.add(UP);
				// CALCOLA ALTEZZA MASSIMA DAL POSTO IN CUI SALTI
				game.calculateHeight(game.getCharacter().getY());

			}

		if (e.getKeyCode() == KeyEvent.VK_SPACE && game.getCharacter().isCanAttack() && canAddAttack
				&& !game.getCharacter().isJumping()) {
			if (!isInside(SPACE)) {
				canAddAttack = false;
				comandi.add(SPACE);
			}

		}

		/*if (e.getKeyCode() == KeyEvent.VK_E)
			game.createEnemy();*/

		if (game.pause) {
			if (e.getKeyCode() == KeyEvent.VK_R) { // Riprende il gioco
				game.pause = false;
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					JOptionPane.showMessageDialog(null, "UNEXPECTED ERROR", "THREAD ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			comandi.remove(RIGHT);
			
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {;
			comandi.remove(LEFT);
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			comandi.remove(UP);
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			comandi.remove(SPACE);
			canAddAttack = true;
			game.getCharacter().setIsAttacking(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public void movements() {
		if (!comandi.isEmpty())
			for (int i = 0; i < comandi.size(); i++) {
				if (comandi.get(i).equals(RIGHT)) {
					game.move(RIGHT);
					game.getCharacter().setDirection(MyCharacter.DIR_RIGHT);
					handler.setAnimazionePlayer(PlayerAnimation.RUN);
				}
				if (comandi.get(i).equals(LEFT)) {
					game.move(LEFT);
					game.getCharacter().setDirection(MyCharacter.DIR_LEFT);
					handler.setAnimazionePlayer(PlayerAnimation.RUN);
				}
				if (comandi.get(i).equals(UP) && game.getCharacter().isCanJump()) {
					// SE SONO A TERRA POSSO SALTARE, ALTRIMENTI NO
					game.move(UP);
					handler.setAnimazionePlayer(PlayerAnimation.JUMP);
				}
				if (comandi.get(i).equals(SPACE)) {
					game.getCharacter().setCanAttack(false);
					game.getCharacter().setIsAttacking(true);
					handler.setAnimazionePlayer(PlayerAnimation.ATTACK);
					gestoreAudio.pressedSound(ATTACK);

					if (game.getCharacter().getFatigue() == 100) {
						game.getCharacter().healtHit(1);
						game.getCharacter().resetFatigue();
					}
					game.getCharacter().plusFatigue();
				}
			}
	}

	public boolean isInside(Integer scelta) {
		for (Integer i : comandi) {
			if (i == scelta)
				return true;
		}
		return false;
	}

	public ArrayList<Integer> comandi() {
		return comandi;
	}

}
