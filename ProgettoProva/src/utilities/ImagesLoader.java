package utilities;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import games.EnemyAnimation;
import games.PlayerAnimation;

public class ImagesLoader {

	private static final String RESOURCES = "/res/animazioni/";
	private static ImagesLoader instance = null;
	
	private ImagesLoader() {
	}
	
	public void loadImages() {
		//MI CARICO TUTTE LE IMMAGINI
		AnimationHandler handler = AnimationHandler.getInstance();
		handler.addAnimazionePlayer(new PlayerAnimation(getResources("player", "idle"), PlayerAnimation.IDLE));
		handler.addAnimazionePlayer(new PlayerAnimation(getResources("player","run"), PlayerAnimation.RUN));
		handler.addAnimazionePlayer(new PlayerAnimation(getResources("player","attack"), PlayerAnimation.ATTACK));
		handler.addAnimazionePlayer(new PlayerAnimation(getResources("player","jump"), PlayerAnimation.JUMP));
		
		handler.addAnimazioneEnemy(new EnemyAnimation(getResources("enemy1","run"), EnemyAnimation.RUN, EnemyAnimation.RANA), EnemyAnimation.RANA);
		handler.addAnimazioneEnemy(new EnemyAnimation(getResources("enemy2","run"), EnemyAnimation.RUN, EnemyAnimation.BAT), EnemyAnimation.BAT);
		handler.addAnimazioneEnemy(new EnemyAnimation(getResources("enemy3","run"), EnemyAnimation.RUN, EnemyAnimation.FANTASMA), EnemyAnimation.FANTASMA);
		handler.addAnimazioneEnemy(new EnemyAnimation(getResources("enemy4","run"), EnemyAnimation.RUN, EnemyAnimation.SCHELETRO), EnemyAnimation.SCHELETRO);
		handler.addAnimazioneEnemy(new EnemyAnimation(getResources("boss","run"), EnemyAnimation.RUN, EnemyAnimation.BOSS), EnemyAnimation.BOSS);
	}
	
	private ArrayList<Image> getResources(String percorso, String name) {
		ArrayList<Image> temp = new ArrayList<Image>();
		
		try {
			String path = RESOURCES + percorso + "/" + name + "/";
			URL url = getClass().getResource(path);
			Path  p = Paths.get(url.toURI());
			File f = p.toFile();
			ArrayList<File> listOfResources = new ArrayList<File>();
			for (File r : f.listFiles())
				listOfResources.add(r);
			
			Collections.sort(listOfResources, new Comparator<File>() {
				@Override
				public int compare(File o1, File o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			
			for (File img : listOfResources) {
				temp.add(ImageIO.read(getClass().getResourceAsStream( path  + img.getName())));
			}															
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "UNEXPECTED ERROR", "IMAGE ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		return temp;
	}
	
	
	public static ImagesLoader getInstance() {
		if (instance == null)
			instance = new ImagesLoader();
		return instance;
	}
}
