package application.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Iterator;
import javax.swing.JPanel;
import application.Game;
import application.model.Enemy;
import application.model.MyCharacter;
import games.EnemyAnimation;
import games.PlayerAnimation;
import utilities.AnimationHandler;
import utilities.AudioClass;
import utilities.DefaultFont;
import utilities.GestoreImmagini;

public class GraphicPanel extends JPanel {


	private static final long serialVersionUID = 1L;
	
	private static final int BACKGROUND = 4;
	private static final int BACKGROUNDPAUSE = 5;
	private static final int SCONFITTA = 14;
	private static final int VITTORIA = 15;
	
	private static final int MAINMUSIC = 0;
	private static final int HERO = 3;
	private static final int MUSICSCONFITTA = 7;
	private static final int MUSICVITTORIA = 8;
	
	AnimationHandler handler = AnimationHandler.getInstance();
	AudioClass gestoreAudio = AudioClass.getInstance();
	DefaultFont formattaTesto = DefaultFont.getIstance();

	GestoreImmagini caricaImmagini = GestoreImmagini.getInstance();

	Game game = Game.getInstance();

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawSfondo(g);
		drawCharacter(g);
		//drawAttack(g);
		drawHealt(g);
		drawState(g);
		drawPause(g);
	}

	public GraphicPanel() {
		this.setFocusable(true);
		gestoreAudio.nuovaOP(MAINMUSIC, HERO);
		
	}
	
	public void drawHud(Graphics g) {
		g.setFont(formattaTesto.returnFont(30));
		g.setColor(Color.RED);
		g.drawString("Killed: " + game.getEnemiesKilled(), 10, 590); //10 X, QUINDI A SX MENTRE 590 QUINDI SOTTO
		
		g.setColor(Color.WHITE);
		g.drawString("Score: " + game.getScore(), 950, 20);
		
		g.setColor(Color.YELLOW);
		g.drawString("Coins earned: " + game.getCoins(), 950, 590);
		
		g.setColor(Color.BLACK);
		g.drawString("Best score: ", 525, 20);
		g.setFont(formattaTesto.returnFont(50));
		g.setColor(Color.WHITE);
		g.drawString(game.getHighScore(), 560, 60);
		
		
		
		
	}
	
	public void drawHealt(Graphics g) {
		int distanza = 10;
		for (int i = 0; i < game.getCharacter().getHealt(); i++) {
			g.drawImage(caricaImmagini.setLabel(16, 32, 32), distanza, 10, this);
			distanza += 30; //DISTANZA OTTIMA
		}
	}
	
	public void drawLifeEnemy(Graphics g, Enemy e) {
		int distanza = 0;
		for (int i = 0; i < e.getHealt(); i++) {
			g.drawImage(caricaImmagini.setLabel(16, 16, 16), e.getX() + distanza, e.getY() - 40, null);
			distanza += 15;
		}
	}

	public void drawFatigue(Graphics g) {
		g.setColor(Color.green);
		int distanza = 0;
		for (int i = game.getCharacter().getFatigue(); i >= 0; i -= 10) {
			g.fillRect(game.getCharacter().getX() + distanza, game.getCharacter().getY() - 10, game.getCharacter().getSizeX() / 10, game.getCharacter().getSizeY() / 10);
			distanza += game.getCharacter().getSizeX() / 10;
		}
	}
	
	

	public void drawSfondo(Graphics g) {
		g.drawImage(caricaImmagini.setSfondo(BACKGROUND).getImage(), 0, 0, getWidth(), getHeight(), this);
	}

	

	/*public void drawAttack(Graphics g) { //HITBOX ATTACCO
		if (game.getCharacter().isAttacking() == true) {
			Graphics2D g2 = (Graphics2D) g;
			g.setColor(Color.MAGENTA);
			if (game.getCharacter().getDirection() == MyCharacter.DIR_LEFT)
				g2.draw(game.getCharacter().getColpo());
			else
				g2.draw(game.getCharacter().getColpo());
				
		}
	}*/

	public void drawCharacter(Graphics g) {
		//Graphics2D g2 = (Graphics2D) g;
		AnimationHandler handler = AnimationHandler.getInstance();
		PlayerAnimation animation = handler.getCurrentAnimazionePlayer();
		Image img = animation.getCurrentImage();
		if (game.getCharacter().getDirection() == MyCharacter.DIR_RIGHT) {
			g.drawImage(img, game.getCharacter().getX(), game.getCharacter().getY(), game.getCharacter().getSizeX(), game.getCharacter().getSizeY(), null);
			/*g2.setColor(Color.BLACK);
			g2.draw(game.getCharacter().getHitBox());*/
		}

		else {
			g.drawImage(img, game.getCharacter().getX() + game.getCharacter().getSizeX(), game.getCharacter().getY(), -game.getCharacter().getSizeX(), game.getCharacter().getSizeY(), null);
			/*g2.setColor(Color.BLACK);
			g2.draw(game.getCharacter().getHitBox());*/
		}

	}

	

	public void drawEnemies(Graphics g) {
		//Graphics2D g2 = (Graphics2D) g;
		
		for (Iterator<Enemy> enemies = game.getEnemiesManager().getEnemies().iterator(); enemies.hasNext();) {
			Enemy e = enemies.next();
			if (e.getIsAlive()) {
				if (e.getType() == Enemy.FROG) {
					drawLifeEnemy(g, e);
					EnemyAnimation animation = handler.getCurrentAnimazioneEnemy(Enemy.FROG);
					Image img = animation.getCurrentImage();
					if (e.getDirection() == Enemy.SX) {
						g.drawImage(img, e.getX(), e.getY(), e.getSizeX(), e.getSizeY(), null); // DESTRA
						/*g2.setColor(Color.BLACK);
						g2.draw(e.getHitBox());*/
					}

					else {
						g.drawImage(img, e.getX() + e.getSizeX(), e.getY(), -e.getSizeX(), e.getSizeY(), null); // SINISTRA
						/*g2.setColor(Color.BLACK);
						g2.draw(e.getHitBox());*/
					}
				}

				if (e.getType() == Enemy.BAT) {
					drawLifeEnemy(g, e);
					EnemyAnimation animation = handler.getCurrentAnimazioneEnemy(Enemy.BAT);
					Image img = animation.getCurrentImage();
					if (e.getDirection() == Enemy.SX) {
						g.drawImage(img, e.getX(), e.getY(), e.getSizeX(), e.getSizeY(), null); // DESTRA
						/*g2.setColor(Color.BLACK);
						g2.draw(e.getHitBox());*/
					}
						

					else {
						g.drawImage(img, e.getX() + e.getSizeX(), e.getY(), -e.getSizeX(), e.getSizeY(), null); // SINISTRA	
						/*g2.setColor(Color.BLACK);
						g2.draw(e.getHitBox());*/
					}
																											// (FUNZIONA)
				}
				
				if (e.getType() == Enemy.GHOST) {
					drawLifeEnemy(g, e);
					EnemyAnimation animation = handler.getCurrentAnimazioneEnemy(Enemy.GHOST);
					Image img = animation.getCurrentImage();
					drawLifeEnemy(g, e);
					if (e.getDirection() == Enemy.SX) {
						g.drawImage(img, e.getX(), e.getY(), e.getSizeX(), e.getSizeY(), null); // DESTRA
						/*g2.setColor(Color.BLACK);
						g2.draw(e.getHitBox());*/
					}
						

					else {
						g.drawImage(img, e.getX() + e.getSizeX(), e.getY(), -e.getSizeX(), e.getSizeY(), null); // SINISTRA	
						/*g2.setColor(Color.BLACK);
						g2.draw(e.getHitBox());*/;
					}
																											// (FUNZIONA)
				}
				
				if (e.getType() == Enemy.SCHELETRO) {
					drawLifeEnemy(g, e);
					EnemyAnimation animation = handler.getCurrentAnimazioneEnemy(Enemy.SCHELETRO);
					Image img = animation.getCurrentImage();
					if (e.getDirection() == Enemy.SX) {
						g.drawImage(img, e.getX(), e.getY(), e.getSizeX(), e.getSizeY(), null); // DESTRA
						/*g2.setColor(Color.BLACK);
						g2.draw(e.getHitBox());*/
					}
						

					else {
						g.drawImage(img, e.getX() + e.getSizeX(), e.getY(), -e.getSizeX(), e.getSizeY(), null); // SINISTRA	
						/*g2.setColor(Color.BLACK);
						g2.draw(e.getHitBox());*/
					}																		
				}
				
				if (e.getType() == Enemy.BOSS) {
					drawLifeEnemy(g, e);
					EnemyAnimation animation = handler.getCurrentAnimazioneEnemy(Enemy.BOSS);
					drawLifeEnemy(g, e);
					Image img = animation.getCurrentImage();
					if (e.getDirection() == Enemy.SX) {
						g.drawImage(img, e.getX() + e.getSizeX(), e.getY(), -e.getSizeX(), e.getSizeY(), null); // SINISTRA	
						/*g2.setColor(Color.BLACK);
						g2.draw(e.getHitBox());*/
					}
						

					else {
						g.drawImage(img, e.getX(), e.getY(), e.getSizeX(), e.getSizeY(), null); // DESTRA
						/*g2.setColor(Color.BLACK);
						g2.draw(e.getHitBox());*/
					}
				}
			}
		}
	}

	public void drawPause(Graphics g) {

		if (game.pause == true && game.getCharacter().isAlive()) {
			g.drawImage(caricaImmagini.setSfondo(BACKGROUNDPAUSE).getImage(), 0, 0, getWidth(), getHeight(), this);
		}
	}


	public void drawState(Graphics g) { // FATTO
		if (game.getCharacter().isAlive() && !game.winConditionCheck()) { // VIVO, QUINDI NON FARE NULLA
			drawFatigue(g);
			drawHud(g);
			drawEnemies(g);
		}
		if (!game.getCharacter().isAlive()) { // MORTO, QUINDI MOSTRA IMMAGINE MORTE
			g.drawImage(caricaImmagini.setLabel(SCONFITTA, this.getWidth(), this.getHeight()), 0, 0, null);
			gestoreAudio.nuovaOP(HERO, MUSICSCONFITTA);
		}
		if (game.winConditionCheck()) { // VINTO, QUINDI MOSTRA IMMAGINE VITTORIA
			g.drawImage(caricaImmagini.setLabel(VITTORIA, this.getWidth(), this.getHeight()), 0, 0, null);
			gestoreAudio.nuovaOP(HERO, MUSICVITTORIA);
		}
	}

	public void update() {
		repaint();

	}
}
