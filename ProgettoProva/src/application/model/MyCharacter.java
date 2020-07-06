package application.model;

import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import application.Game;
import utilities.EstrazioneStringhe;

public class MyCharacter implements Runnable {

	EstrazioneStringhe eS = EstrazioneStringhe.getInstance();
	
	
	
	public static final int DIR_RIGHT = 1;
	public static final int DIR_LEFT = -1;
	private int healt;
	private int attack;
	private int x;
	private int y;
	private int speed;
	private int maxY;
	private boolean isJumping;
	private boolean canJump;
	private boolean isAlive;
	private boolean isAttacking;
	private boolean canAttack = true;
	private boolean isFalling = false;
	private boolean isInvincible = false;
	private int fatigue = 0;			// A 100 SUBISCI 1 DANNO DA FATICA
	private int sizeX;
	private int sizeY;
	private int direction = 1;
	private boolean secondChance;

	public MyCharacter() {
		buffLoad();
		x = 600;
		y = Game.ALTEZZA_TERRENO - 80;
		maxY = 450;
		sizeX = 150;
		sizeY = 111;
		speed = 5;
		isJumping = false;
		isAttacking = false;
		isAlive = true;
		
		canJump = true;
	}
	
	
	public void buffLoad() {
		healt = 5 + eS.caricaRoba("Buff").get(EstrazioneStringhe.HEALT);
		attack = 1 + eS.caricaRoba("Buff").get(EstrazioneStringhe.ATTACK);
		if (eS.caricaRoba("Buff").get(EstrazioneStringhe.SECONDCHANCE) == 1)
			secondChance = true;
		else
			secondChance = false;
	}

	public int getAttack() {
		return attack;
	}

	public int getHealt() {
		return healt;
	}
	
	public int getFatigue() {
		return fatigue;
	}
	
	public void plusFatigue() {
		fatigue++;
	}
	public void resetFatigue() {
		fatigue = 0;
	}
	public void rest() {
		if (isAttacking == false && fatigue > 0)
			fatigue--;
	}

	public int getMaxY() {
		return maxY;
	}

	public boolean isJumping() {
		return isJumping;
	}

	public boolean isCanJump() {
		return canJump;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public boolean isAttacking() {
		return isAttacking;
	}

	public boolean isCanAttack() {
		return canAttack;
	}

	public boolean isFalling() {
		return isFalling;
	}

	public boolean isInvincible() {
		return isInvincible;
	}

	public int getDirection() {
		return direction;
	}

	public int getDelay() {
		return delay;
	}

	public void resetCharacter() {
		buffLoad();
		x = 600;
		y = Game.ALTEZZA_TERRENO - 80;
        maxY = 450;
    	sizeX = 150;
		sizeY = 111;
        speed = 6;
        isJumping = false;
        isAttacking = false;
        isAlive = true;
        canJump = true;
	}

	private Rectangle hitBox = new Rectangle();
	private Rectangle colpo = new Rectangle();

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	public void setCanAttack(boolean var) {
		canAttack = var;
	}

	public void setIsAttacking(boolean var) {
		isAttacking = var;
	}

	public void setIsJumping(boolean var) {
		isJumping = var;
	}

	public void setCanJump(boolean var) {
		canJump = var;
	}

	private int delay = 1000; // 1 second

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

	@Override
	public void run() {
		while (true) {
			checkLife();
			canAttackGestion();
			hitBox = new Rectangle(x + 25 , y + 15, sizeX - 35, sizeY - 15); // L'HITBOX VIENE S
			
			if (direction == DIR_LEFT)
                colpo = new Rectangle(x + (sizeX/6 * direction), y, sizeX / 2, sizeY); // HITBOX ATTACCO
            else
                colpo = new Rectangle(x + (sizeX/2+40 * direction), y, sizeX / 2, sizeY); // HITBOX ATTACCO

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "UNEXPECTED ERROR", "THREAD ERROR", JOptionPane.ERROR_MESSAGE);
			}
			rest();
		}

	}

	public void checkLife() {
		if (healt <= 0 && secondChance == false)
			isAlive = false;
		if (healt <= 0 && secondChance == true) {
			healt = 5 + eS.caricaRoba("Buff").get(EstrazioneStringhe.HEALT);
			secondChance = false;
		}

	}

	public void healtHit(int danno) {
		healt -= danno;
		isInvincible = true;
		invincibleHandle();
	}

	public void setDirection(int direction) {		
		this.direction = direction;
	}

	public void jump(int jumpHeight) {
		if (!isJumping && canJump == true) {
			y -= jumpHeight;
			isJumping = true;
		}
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public Rectangle getColpo() {
		return colpo;
	}

	/*
	 * FUNZIONI PER PRENDERE TUTTI I BORDI DEL PERSONAGGIO
	 */

	public Rectangle getLeftBounds() {
		return new Rectangle(x, y, 6, sizeY);
	}

	public Rectangle getRightBounds() {
		return new Rectangle(x + (sizeX - 6), y, 6, sizeY);
	}
	
	public Rectangle getTopBounds() {
        return new Rectangle(x, y, sizeX, 6);
    }
	
	public Rectangle getBottomBounds() {
        return new Rectangle(x, y + (sizeY - 6), sizeX, 6);
    }

	private void canAttackGestion() {
		if (!canAttack) {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					canAttack = true;
					timer.cancel();
				}
			}, 500);
		}
	}

	private void invincibleHandle() {
		if (isInvincible) {
			final Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					isInvincible = false;
					timer.cancel();
					// dopo 3000 millisecondi sono dinuovo mortale
				}
			}, 3000);
		}
	}
}
