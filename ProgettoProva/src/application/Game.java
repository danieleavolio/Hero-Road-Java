package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import application.model.Block;
import application.model.EnemiesManager;
import application.model.Enemy;
import application.model.EntityHandler;
import application.model.MyCharacter;
import application.model.Terrain;
import utilities.EstrazioneStringhe;

public class Game extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final int RIGHT = 0;
	public static final int LEFT = 1;
	public static final int JUMP = 2;
	public static final int SPAWN_SINISTA = -300;
	public static final int SPAWN_DESTRA = 1500;
	public static final int ALTEZZA_TERRENO = 305;
	public static final int GRANDEZZA_PG = 111;
	public static final int ALTEZZA_BOSS = 300;
	public static int gravity = 1;
	public boolean pause; // CONDIZIONE PER METTERE IN PAUSA IL GIOCO
	public boolean running; // CONDIZIONE PER FAR GIRARE IL THREAD
	
	EstrazioneStringhe stringa = EstrazioneStringhe.getInstance();
	
	private Terrain terrain; // IL TERRENO DI GIOCO
	private MyCharacter character; // IL PERSONAGGIO
	private EnemiesManager em; // IL GESTORE DELLA VITA DEI NEMICI E DELLA LORO PRESENZA
	private int enemiesKilled = 0; // NEMICI UCCISI
	private int hordeNumber = 0; // ORDE DI NEMICI
	private int objective = 10; // OBIETTIVO DI NEMICI PER SPAWN BOSS
	private int SCORE = 0; // PUNTEGGIO DELLA PARTITA
	private int HIGHSCORE = 0; // PUNTEGGIO MIGLIORE
	private int coinsGained = 0; // MONETE IN QUESTA PARTITA
	private int nBossKilled = 0; // NUMERO DI BOSS UCCISI
	public int winCondition = 2; // NUMERO DI BOSS DA UCCIDERE
	private static Game game = null;
	// SALVARE LO SCORE
	private String fileName = "SaveData"; // CARICARE HIGHSCORE

	private Game() {
		createCharacter(); // CREA PG
		em = new EnemiesManager(); // CREA GESTORE NEMICI
		terrain = new Terrain(); // CREA TERRENO
		loadHighScore(); // CARICA PUNTEGGIO MIGLIORE
	}

	private void loadHighScore() {
		try {
			File f = stringa.caricaPercorso(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			while (reader.ready()) {
				HIGHSCORE = Integer.parseInt(reader.readLine());
			}
			reader.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "UNEXPECTED ERROR", "FILE NOT FOUND", JOptionPane.ERROR_MESSAGE);

		}
	}

	public void setHighsScore() {
		FileWriter output = null;
		try {
			File f = stringa.caricaPercorso(fileName);
			output = new FileWriter(f);
			BufferedWriter writer = new BufferedWriter(output);
			writer.write("" + HIGHSCORE);
			writer.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "UNEXPECTED ERROR", "FILE NOT FOUND", JOptionPane.ERROR_MESSAGE);
		}
	}

	public String getHighScore() {
		return Integer.toString(HIGHSCORE);
	}

	public MyCharacter getCharacter() {
		return character;
	}

	public EnemiesManager getEnemiesManager() {
		return em;
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public static Game getInstance() {
		if (game == null) {
			game = new Game();
		}
		return game;
	}

	public void enemiesFollow() { // FUNZIONE CHE PERMETTE AI NEMICI DI SEGUIRE IL PERSONAGGIO IN BASE ALLA
									// POSIZIONE
		for (Iterator<Enemy> enemies = game.getEnemiesManager().getEnemies().iterator(); enemies.hasNext();) {
			Enemy e = enemies.next();
			e.followPlayer(getCharacter().getX());
		}
	}

	public void gravityHit() { // FUNZIONE CHE FA FUNZIONARE LA GRAVITA' SUL PERSONAGGIO
		int y = character.getY();
		y += gravity * 5;
		character.setY(y);
	}

	public int getScore() {
		return SCORE;
	}

	public void scoreAdd(int points) { // VIENE CHIAMATA ALLA MORTE DEI NEMICI PER AUMENTARE IL PUNTEGGIO
		SCORE += points;
	}

	public void coinAdd(int coins) { // VIENE CHIAMATA ALLA MORTE DEI NEMICI PER AUMENTARE IL NUMERO DI MONETE
		coinsGained += coins;
	}

	public int getCoins() { // monete guadagnate in questa partita
		return coinsGained;
	}

	public void resetCoins() {
		coinsGained = 0;
	}

	public synchronized void createCharacter() {
		character = new MyCharacter();
		EntityHandler.addThreadC(character); // GESTISCE IL THREAD DEL PERSONAGGIO
	}

	public synchronized void createEnemy() { // CREA IL NEMICO RANDOMICAMENTE TRA TIPO E POSIZIONE DESTRA O SINISTRA
												// DELLO SCHERMO
		Random nemico = new Random();
		Enemy enemy = new Enemy(nemico.nextInt(4));
		Random r = new Random();
		Random posto = new Random();
		if (posto.nextInt(2) == 0)
			enemy.setX(r.nextInt(50) + SPAWN_DESTRA);
		else
			enemy.setX(r.nextInt(50) + SPAWN_SINISTA);
		em.addEnemy(enemy);
		EntityHandler.addThreadE(enemy); // GESTISCE IL THREAD DEL NEMICO
	}

	public synchronized void createBoss() {
		Enemy enemy = new Enemy(Enemy.BOSS);
		Random r = new Random();
		Random posto = new Random();
		if (posto.nextInt(2) == 0)
			enemy.setX(r.nextInt(50) + SPAWN_DESTRA);
		else
			enemy.setX(r.nextInt(50) + SPAWN_SINISTA);
		em.addEnemy(enemy);
		EntityHandler.addThreadE(enemy); // GESTISCE IL THREAD DEL NEMICO
	}

	public void move(int movement) { // GESTIONE MOVIMENTO
		switch (movement) {
		case RIGHT:
			int x = character.getX();
			x += character.getSpeed();
			character.setX(x);
			break;

		case LEFT:
			int x1 = character.getX();
			x1 -= character.getSpeed();
			character.setX(x1);
			break;
		case JUMP:
			if (!getCharacter().isJumping() && getCharacter().isCanJump()) {
				calculateHeight(getCharacter().getY());
				getCharacter().setIsJumping(true);
				getCharacter().setCanJump(false);
			}
			break;
		default:
			break;
		}
	}

	public void calculateHeight(int oldY) {
		int maxY = getCharacter().getMaxY();
		maxY = oldY - 220; // Calcola il salto massimo che può fare il personaggio in base a dove ha
							// saltato
		getCharacter().setMaxY(maxY);
	}

	private void falling() { // CADERE DOPO TOT
		if (getCharacter().getY() < getCharacter().getMaxY()) {
			getCharacter().setIsJumping(false);
		}
		if (!getCharacter().isJumping()) // se non stai saltando allora la gravità ti affligge
			gravityHit();
	}

	public void killedCounterPlus() { // AUMENTA NUMERO NEMICI UCCISI
		enemiesKilled++;
	}

	public int getEnemiesKilled() {
		return enemiesKilled;
	}

	public int getObjective() { // OBIETTIVO PER SPAWN BOSS
		return objective;
	}

	private void jumping() { // SALTARE GRADUALMENTE DI
		if (getCharacter().isJumping() == true) {
			getCharacter().setCanJump(false); // QUANDO SALTO NON POSSO SALTARE
			int y = getCharacter().getY();
			y -= gravity * 5;
			getCharacter().setY(y);
		}
	}

	private void terrainCollision() { // COLLISIONE COL TERRENO
		for (Block b : getTerrain().getTerreno()) {
			int y;

			if (getCharacter().getBottomBounds().intersects(b.getTopBounds())) {
				y = b.getY() - getCharacter().getSizeY();
				getCharacter().setY(y);
				getCharacter().setCanJump(true);
			}

		}

	}

	private void checkUnderTerrain() { // PER EVITARE PROBLEMI DI TERRENO
		if (getCharacter().getY() > getTerrain().getTerreno().get(0).getY() - character.getSizeY()) {
			getCharacter().setY(ALTEZZA_TERRENO);
		}

	}

	private void bordersLimit() { // BORDI SCHERMO
		if (game.getCharacter().getX() > 1200 - character.getSizeX() - 2)
			game.getCharacter().setX(1200 - character.getSizeX() - 2);
		if (game.getCharacter().getX() < -20) // I BORDI DELL'IMMAGINE HANNO DEI PIXEL VUOTI
			game.getCharacter().setX(-20); // I BORDI DELL'IMMAGINE HANNO DEI PIXEL VUOTI
	}

	private void collisionCheck() { // COLLISIONI

		// SCORRIMENTO DEI NEMICI E CONTROLLO DELLE COLLOSIONI
		if (!getEnemiesManager().getEnemies().isEmpty()) {
			for (Enemy e : getEnemiesManager().getEnemies()) {

				if (getCharacter().getHitBox().intersects(e.getHitBox()) && e.getIsAlive()) {
					if (!getCharacter().isInvincible()) {
						getCharacter().healtHit(e.getAttack());

						int x = getCharacter().getX();
						x += getCharacter().getX() - e.getHitBox().getX();
						getCharacter().setX(x);
					}
				}

				// CONTROLLO SE STO ATTACCANDO
				if (getCharacter().isAttacking() == true) {
					if (getCharacter().getColpo().intersects(e.getHitBox())) {
						int x = e.getX();
						x -= getCharacter().getHitBox().getX() - e.getHitBox().getX();
						e.setX(x);
						if (e.getEnemy().getHealt() > 0) {
							e.getEnemy().healtHit(character.getAttack());
						}
					}
					getCharacter().setCanAttack(false);
				}
			}
		}
	}

	/*private void enemyCollision() {
		if (!getEnemiesManager().getEnemies().isEmpty()) {
			for (int i = 0; i < getEnemiesManager().getEnemies().size(); i++) {
				Enemy checking = getEnemiesManager().getEnemies().get(i);
				for (Enemy e : getEnemiesManager().getEnemies()) {
					if (e != checking && e.getHitBox() != null && checking.getHitBox() != null) {
						if (e.getHitBox().intersects(checking.getLeftBounds())) {
							int position = checking.getX() - e.getSizeX() - 2;
							e.setX(position);
						}
						if (e.getHitBox().intersects(checking.getRightBounds())) {
							int position = checking.getX() - e.getSizeX() - 2;
							e.setX(position);

						}
					}
				}
			}
		}
	}*/

	private void enemiesCheck() { // CONTROLLO VITA
		getEnemiesManager().checkState();
	}

	private synchronized void enemiesGeneration() { // GENERAZIONE SPAWN
		while (getEnemiesManager().getEnemies().size() < 1 && enemiesKilled < objective && hordeNumber < 2) { 
			
			createEnemy();
		}
		if (enemiesKilled == objective) {
			hordeNumber++;
			enemiesKilled = 0;
		}

		if (hordeNumber == 1) {
			createBoss();
			hordeNumber = 0;
		}
	}

	public void checkScore() { // CONTROLLO PUNTEGGIO
		if (SCORE > HIGHSCORE)
			HIGHSCORE = SCORE;
	}

	public void bitesTheDust() { // THIS IS KILLER QUEEN'S THIRD BOMB, BITED THE DUST
		character.resetCharacter();
		em.getEnemies().clear();
		enemiesKilled = 0; // NEMICI UCCISI
		hordeNumber = 0; // ORDE DI NEMICI
		SCORE = 0; // PUNTEGGIO DELLA PARTITA
		coinsGained = 0; // MONETE IN QUESTA PARTITA
		nBossKilled = 0; // NUMERO DI BOSS UCCISI
	}

	public void update() {
		if (!winConditionCheck()) {
			falling();
			jumping();
			collisionCheck();
			enemiesCheck();
			terrainCollision();
			enemiesGeneration();
			checkUnderTerrain();
			bordersLimit();
			enemiesFollow();
		}
	}

	public int getnBossKilled() {
		return nBossKilled;
	}

	public void bossKilled() {
		nBossKilled++;
	}

	public boolean winConditionCheck() { // VINCI
		if (winCondition == nBossKilled)
			return true;

		return false;
	}

	public int getWinCondition() {
		return winCondition;
	}

	public void resetCondition() {
		running = true;
		nBossKilled = 0;
	}
}
