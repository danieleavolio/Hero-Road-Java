package application.model;

import java.awt.Rectangle;
import java.util.Random;

import javax.swing.JOptionPane;

import application.Game;
import games.EnemyAnimation;
import utilities.AnimationHandler;

public class Enemy implements Runnable {

	public static final int FROG = 0;
	public static final int BAT = 1; // DA CAMBIARE
	public static final int GHOST = 2;
	public static final int SCHELETRO = 3;
	public static final int BOSS = 4;
	public static final int DX = 1;
	public static final int SX = -1;
	AnimationHandler handler = AnimationHandler.getInstance();

	private int x;
	private int y;
	private int speed;
	private int sizeX;
	private int sizeY;
	private int healt;
	private int attack;
	private boolean isAlive = true;
	private int direction = 1;
	private int type;
	private int points;
	private int prize;
	private Rectangle hitBox;

	public Enemy(int TIPO) {
		switch (TIPO) {
		case FROG: //LENTO, POCO ATTACCO, POCA VITA
			y = Game.ALTEZZA_TERRENO + 30;
			sizeX = 80;
			sizeY = 80;
			isAlive = true;
			attack = 1;
			speed = 1;
			healt = 1;
			hitBox = new Rectangle(x, y, sizeX, sizeY);
			type = TIPO;
			points = 10;
			Random r = new Random();
			prize = r.nextInt(6) + 2;
			break;
		case BAT: //PIU' VELOCE, POCO ATTACCO, VITA BASSA
			y = Game.ALTEZZA_TERRENO; //PIU AUMENTO + VA SOTTO
			sizeX = 80;
			sizeY = 120;
			isAlive = true;
			attack = 1;
			speed = 3;
			healt = 1;
			hitBox = new Rectangle(x, y, sizeX, sizeY);
			type = TIPO;
			points = 10;
			Random r1 = new Random();
			prize = r1.nextInt(6) + 2;
			break;
		case GHOST://VELOCITA MEDIA, ATTACCO MEDIO, VITA MEDIA
			y = Game.ALTEZZA_TERRENO + 30;
			sizeX = 80;
			sizeY = 80;
			isAlive = true;
			attack = 2;
			speed = 2;
			healt = 3;
			hitBox = new Rectangle(x, y, sizeX, sizeY);
			type = TIPO;
			points = 10;
			Random r2 = new Random();
			prize = r2.nextInt(6) + 2;
			break;
		case SCHELETRO://MOLTO VELOCE, MOLTO FORTE, MA FRAGILE
			y = Game.ALTEZZA_TERRENO + 22;
			sizeX = 80;
			sizeY = 80;
			isAlive = true;
			attack = 3;
			speed = 3;
			healt = 2;
			hitBox = new Rectangle(x, y, sizeX, sizeY);
			type = TIPO;
			points = 10;
			Random r3 = new Random();
			prize = r3.nextInt(6) + 2;
			break;
		case BOSS:
			y = Game.ALTEZZA_BOSS;
			sizeX = 128;
			sizeY = 128;
			isAlive = true;
			speed = 2;
			healt = 10;
			attack = 3;
			hitBox = new Rectangle(x, y, sizeX, sizeY);
			type = TIPO;
			points = 100;
			prize = 50;
			break;
		}
	}

	public int getAttack() {
		return attack;
	}

	public int getPrize() {
		return prize;
	}

	public int getHealt() {
		return healt;
	}

	public int getPoints() {
		return points;
	}

	public boolean getIsAlive() {
		return isAlive;
	}

	public int getType() {
		return type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSpeed() {
		return speed;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public Rectangle getLeftBounds() { // CAPIRE SE SI STA TOCCANDO CON QUALCUN ALTRO A SX
		return new Rectangle(x, y, 6, sizeY);
	}

	public Rectangle getRightBounds() { // CAPIRE SE SI STA TOCCANDO CON QUALCUN ALTRO A DX
		return new Rectangle(x + (sizeX - 6), y, 6, sizeY);
	}

	public int getDirection() {
		return direction;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setSpeedy(int speedy) {
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public void setHealt(int healt) {
		this.healt = healt;
	}

	public void healtHit(int danno) {
		healt -= danno;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setHitBox(Rectangle hitBox) {
		this.hitBox = hitBox;
	}

	@Override
	public void run() {
		while (true) {
			switch (type) {
			case FROG:
			

				hitBox = new Rectangle(x, y, sizeX, sizeY);
				handler.setAnimazioneEnemy(EnemyAnimation.RUN, Enemy.FROG);

				break;
				
			case BAT:

				hitBox = new Rectangle(x, y - 20, sizeX, sizeY - 20);
				handler.setAnimazioneEnemy(EnemyAnimation.RUN, Enemy.BAT);
				
				break;
				
			case GHOST:

				hitBox = new Rectangle(x, y, sizeX, sizeY);
				handler.setAnimazioneEnemy(EnemyAnimation.RUN, Enemy.GHOST);
				
				break;
				
			case SCHELETRO:

				hitBox = new Rectangle(x, y, sizeX, sizeY);
				handler.setAnimazioneEnemy(EnemyAnimation.RUN, Enemy.SCHELETRO);
				
				break;
				
			case BOSS:
				
				hitBox = new Rectangle(x, y + 40, sizeX , sizeY - 40); 
				//X = LARGHEZZA, Y = ALTEZZA
				//PER AGGIUSTARE LA HITBOX IN ALTEZZA, AUMENTARE Y E DIMINUIRE SIZE Y
				//PER AGGIUSTARE LA HITBOX IN LARGHEZZA, AUMENTARE X E DIMINUIRE SIZE X
				handler.setAnimazioneEnemy(EnemyAnimation.RUN, Enemy.BOSS);

				break;
			}

			checkLife();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "UNEXPECTED ERROR", "THREAD ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void checkLife() {
		if (healt <= 0) {
			isAlive = false;
		}
			
	}

	public void followPlayer(int X) {
		if (isAlive) {
			if (getX() - Game.getInstance().getCharacter().getX() < 0)
				direction = -1;
			else
				direction = 1;
			x -= speed * direction;
		}
	}
	public Rectangle getHitBox() {
		return hitBox;
	}

	public Enemy getEnemy() {
		return this;
	}
}
