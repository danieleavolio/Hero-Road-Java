package utilities;

import java.util.HashMap;

import games.EnemyAnimation;
import games.PlayerAnimation;

public class AnimationHandler {

	private static AnimationHandler instance = null;

	private HashMap<Integer, PlayerAnimation> animazionePlayer;

	private HashMap<Integer, EnemyAnimation> animazioneEnemy1;
	private HashMap<Integer, EnemyAnimation> animazioneEnemy2;
	private HashMap<Integer, EnemyAnimation> animazioneEnemy3;
	private HashMap<Integer, EnemyAnimation> animazioneEnemy4;

	private HashMap<Integer, EnemyAnimation> animazioneBoss;

	private PlayerAnimation currentAnimationPlayer;

	private EnemyAnimation currentAnimationEnemy1;
	private EnemyAnimation currentAnimationEnemy2;
	private EnemyAnimation currentAnimationEnemy3;
	private EnemyAnimation currentAnimationEnemy4;

	private EnemyAnimation currentAnimationBoss;

	private AnimationHandler() {
		animazionePlayer = new HashMap<Integer, PlayerAnimation>();

		animazioneEnemy1 = new HashMap<Integer, EnemyAnimation>();
		animazioneEnemy2 = new HashMap<Integer, EnemyAnimation>();
		animazioneEnemy3 = new HashMap<Integer, EnemyAnimation>();
		animazioneEnemy4 = new HashMap<Integer, EnemyAnimation>();

		animazioneBoss = new HashMap<Integer, EnemyAnimation>();

		currentAnimationPlayer = null;

		currentAnimationEnemy1 = null;
		currentAnimationEnemy2 = null;
		currentAnimationEnemy3 = null;
		currentAnimationEnemy4 = null;

		currentAnimationBoss = null;

	}

	// PARTE PLAYER
	public void setAnimazionePlayer(Integer tipo) {
		currentAnimationPlayer = animazionePlayer.get(tipo);
		;

	}

	public void addAnimazionePlayer(PlayerAnimation a) {
		animazionePlayer.put(a.tipo, a);
	}

	public PlayerAnimation getCurrentAnimazionePlayer() {
		return currentAnimationPlayer;
	}

	public void updatePlayer() {
		if (currentAnimationPlayer == null)
			return;
		if (!currentAnimationPlayer.update())
			currentAnimationPlayer = animazionePlayer.get(PlayerAnimation.IDLE);
	}

	// PARTE ENEMY
	public void setAnimazioneEnemy(Integer tipo, int tipoNemico) {
		switch (tipoNemico) {
		case 0: // RANA
			currentAnimationEnemy1 = animazioneEnemy1.get(tipo);
			break;
		case 1: // BAT
			currentAnimationEnemy2 = animazioneEnemy2.get(tipo);
			break;
		case 2: // FANTASMA
			currentAnimationEnemy3 = animazioneEnemy3.get(tipo);
			break;

		case 3: // SCHELETRO
			currentAnimationEnemy4 = animazioneEnemy4.get(tipo);
			break;

		case 4: // BOSS
			currentAnimationBoss = animazioneBoss.get(tipo);
			break;

		default:
			break;
		}
	}

	public void addAnimazioneEnemy(EnemyAnimation a, int tipoNemico) {
		switch (tipoNemico) {

		case 0: // RANA
			animazioneEnemy1.put(a.tipo, a);
			break;

		case 1: // BAT
			animazioneEnemy2.put(a.tipo, a);
			break;

		case 2: // FANTASMA
			animazioneEnemy3.put(a.tipo, a);
			break;

		case 3: // SCHELETRO
			animazioneEnemy4.put(a.tipo, a);
			break;

		case 4: // BOSS
			animazioneBoss.put(a.tipo, a);
			break;

		default:
			break;
		}
	}

	public EnemyAnimation getCurrentAnimazioneEnemy(int tipoNemico) {

		if (tipoNemico == 0)
			return currentAnimationEnemy1;

		else if (tipoNemico == 1)
			return currentAnimationEnemy2;

		else if (tipoNemico == 2)
			return currentAnimationEnemy3;

		else if (tipoNemico == 3)
			return currentAnimationEnemy4;

		else
			return currentAnimationBoss;
	}

	public void updateEnemy(int tipoNemico) {
		switch (tipoNemico) {
		case 0: // RANA
			if (currentAnimationEnemy1 == null)
				return;
			if (!currentAnimationEnemy1.update())
				currentAnimationEnemy1 = animazioneEnemy1.get(EnemyAnimation.RUN);
			break;

		case 1: // BAT
			if (currentAnimationEnemy2 == null)
				return;
			if (currentAnimationEnemy2.update())
				currentAnimationEnemy2 = animazioneEnemy2.get(EnemyAnimation.RUN);
			;
			break;

		case 2: // FANTASMA
			if (currentAnimationEnemy3 == null)
				return;
			if (!currentAnimationEnemy3.update())
				currentAnimationEnemy3 = animazioneEnemy3.get(EnemyAnimation.RUN);
			break;

		case 3: // SCHELETRO
			if (currentAnimationEnemy4 == null)
				return;
			if (!currentAnimationEnemy4.update())
				currentAnimationEnemy4 = animazioneEnemy4.get(EnemyAnimation.RUN);
			;
			break;

		case 4: // BOSS
			if (currentAnimationBoss == null)
				return;
			if (!currentAnimationBoss.update())
				currentAnimationBoss = animazioneBoss.get(EnemyAnimation.RUN);
			break;

		default:
			break;
		}
	}

	public void loadImages() {
		currentAnimationPlayer = animazionePlayer.get(PlayerAnimation.IDLE);

		currentAnimationEnemy1 = animazioneEnemy1.get(EnemyAnimation.RUN);
		currentAnimationEnemy2 = animazioneEnemy2.get(EnemyAnimation.RUN);
		currentAnimationEnemy3 = animazioneEnemy3.get(EnemyAnimation.RUN);
		currentAnimationEnemy4 = animazioneEnemy4.get(EnemyAnimation.RUN);
		currentAnimationBoss = animazioneBoss.get(EnemyAnimation.RUN);
	}

	public static AnimationHandler getInstance() {
		if (instance == null)
			instance = new AnimationHandler();
		return instance;
	}

}
