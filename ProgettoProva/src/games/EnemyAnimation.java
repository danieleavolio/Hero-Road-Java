package games;

import java.awt.Image;
import java.util.ArrayList;

import utilities.AnimationHandler;

public class EnemyAnimation {

	// CLASSE CHE AL SUO INTERNO HA GLI ENUM PUBBLICI DELLE SUE AZIONI

	private ArrayList<Image> images;
	AnimationHandler handler = AnimationHandler.getInstance();
	public static final int RANA = 0;
	public static final int BAT = 1;
	public static final int FANTASMA = 2;
	public static final int SCHELETRO = 3;
	public static final int BOSS = 4;

	private double index;
	Image currentImage;
	public int tipo;
	int nemico;

	public static final int RUN = 0;

	public EnemyAnimation(ArrayList<Image> images, int tipo, int nemico) {
		index = 0;
		this.images = images;
		this.nemico = nemico;
		currentImage = null;
		if (images.size() > 0)
			currentImage = images.get(0);
		this.tipo = tipo;
	}

	public boolean update() {

		switch (nemico) {
		case RANA:
			index += 0.05;
			break;

		case BAT:
			index += 0.20;
			break;

		case FANTASMA:
			index += 0.20;
			break;

		case SCHELETRO:
			index += 0.10;
			break;

		case BOSS:
			index += 0.10;
			break;
		}

		if (index >= images.size()) {
			index = 0;
			return false;
		}

		currentImage = images.get((int) index);
		return true;

	}

	public Image getCurrentImage() {
		return currentImage;
	}
}
