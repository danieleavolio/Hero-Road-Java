package application.model;

import java.util.ArrayList;

import application.Game;
import utilities.AudioClass;

public class EnemiesManager implements Runnable {

	private ArrayList<Enemy> enemies;
	AudioClass gestoreAudio = AudioClass.getInstance();
	
	private static final int ENEMYDEATH = 5;

	public EnemiesManager() {
		enemies = new ArrayList<Enemy>();
		EntityHandler.addThreadManager(this);
	}

	public void addEnemy(Enemy e) {
		enemies.add(e);
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	private void removeEnemy(Enemy e) {
		Game.getInstance().scoreAdd(e.getPoints());
		gestoreAudio.pressedSound(ENEMYDEATH);
		enemies.remove(e);
	}

	@Override
	public void run() {
		while (true) {
			checkState();
		}
	}

	public void checkState() {
		for (int i = 0; i < enemies.size(); i++) {
			if (!enemies.get(i).getIsAlive()) {
				if(enemies.get(i).getType() == Enemy.BOSS)
					Game.getInstance().bossKilled();
				
				else
					Game.getInstance().killedCounterPlus();
					
				Game.getInstance().coinAdd(enemies.get(i).getPrize());
				removeEnemy(enemies.get(i));
			}
		}
	}
}
