package games;

import java.awt.Image;
import java.util.ArrayList;

import utilities.AnimationHandler;

public class PlayerAnimation {
	//CLASSE CHE AL SUO INTERNO HA GLI ENUM PUBBLICI DELLE SUE AZIONI
	
	private ArrayList<Image> images;
	AnimationHandler handler = AnimationHandler.getInstance();

	private double index;
	Image currentImage;
	public int tipo;
	
	public static final int IDLE = 0;
	public static final int RUN = 1;
	public static final int ATTACK = 2;
	public static final int JUMP = 3;
	
	public PlayerAnimation(ArrayList<Image> images, int tipo) {
		index = 0;
		this.images = images;
		currentImage = null;
		if(images.size() > 0)
			currentImage = images.get(0);
		this.tipo = tipo;
	}
	
	public boolean update() {
		switch (tipo) {
		case IDLE: //CORRETTO
			index+=0.10;
			break;
			
		case RUN:
			index+=0.20; //0.20
			break;
			
		case ATTACK:
			index+=0.50; //PERFETTO
			break;
			
		case JUMP: //CORRETTO
			index+=0.20; //0.20
			break;
		}
		
		if( index >= images.size()) {
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
