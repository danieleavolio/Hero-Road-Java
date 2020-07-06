package utilities;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class GestoreImmagini {

	private static GestoreImmagini instance = null;
	private static final String RESOURCES = "/res/";
	EstrazioneStringhe stringa = EstrazioneStringhe.getInstance();
	private HashMap<Integer, Image> button;
	private HashMap<Integer, Image> buttonClicked;
	private HashMap<Integer, Image> label;
	private HashMap<Integer, ImageIcon> sfondo;
	
	public static final int VOLUMEON = 7;
	public static final int VOLUMEOFF = 8;
	
	private static final int ZERO = 3;
	private static final int VENTICINQUE = 4;
	private static final int CINQUANTA = 5;
	private static final int SETTANTACINQUE = 6;
	private static final int CENTO = 7;
	
	private GestoreImmagini() {
		button = getResources("button");
		buttonClicked = getResources("buttonClicked");
		label = getResources("label");
		sfondo = getResourcesGif("sfondo");
	}
	
	public Image setButton(int nomeFile, int w, int h) {
		return button.get(nomeFile).getScaledInstance(w, h, Image.SCALE_SMOOTH);
	}
	
	public Image setButtonClicked(int nomeFile, int w, int h) {
		return buttonClicked.get(nomeFile).getScaledInstance(w, h, Image.SCALE_SMOOTH);
	}
	
	public Image setLabel(int nomeFile, int w, int h) {
		return label.get(nomeFile).getScaledInstance(w, h, Image.SCALE_SMOOTH);
	}
	
	public ImageIcon setSfondo(int nomeFile) {
		return sfondo.get(nomeFile);
	}
	
	private HashMap<Integer, Image> getResources(String name) {
		HashMap<Integer, Image> temp = new HashMap<Integer, Image>();
		
		try {
			String path = RESOURCES + name + "/";
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
			
			int index = 0;
			for (File img : listOfResources) {
				temp.put(index++, (ImageIO.read(getClass().getResourceAsStream( path  + img.getName()))));
			}															
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "UNEXPECTED ERROR", "IMAGE ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		return temp;
	}
	
	private HashMap<Integer, ImageIcon> getResourcesGif(String name) {
		HashMap<Integer, ImageIcon> temp = new HashMap<Integer, ImageIcon>();
		
		try {
			String path = RESOURCES + name + "/";
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
			
			int index = 0;
			for (File img : listOfResources) {
				URL percorso = this.getClass().getResource( path  + img.getName());
				temp.put(index++, new ImageIcon(percorso));
			}															
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "UNEXPECTED ERROR", "IMAGE ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		return temp;
	}
	
	public static GestoreImmagini getInstance() {
		if (instance == null)
			instance = new GestoreImmagini();
		return instance;
	}

	public Image cambiaVolume(double volume) {
		int temp = (int) (volume*100);
		Image i = null;
		switch (temp) {
		case 0:
			i = setLabel(ZERO,200,135);
			break;
		case 25:
			i = setLabel(VENTICINQUE,200,135);
			break;
		case 50:
			i = setLabel(CINQUANTA,200,135);
			break;
		case 75:
			i = setLabel(SETTANTACINQUE,200,135);
			break;
		case 100:
			i = setLabel(CENTO,200,135);
			break;
		}
		return i;
	}
	
	public Image buttonVolume(double volume){
		int temp = (int) (volume*100);
		
		if(temp != 0)
			return setButton(VOLUMEON,400,270);
		else
			return setButton(VOLUMEOFF,400,270);
	}
	
	public void nextSfondo() {
		//MODIFICO QUELLO CHE DEVE PRENDERE
		int temp = stringa.returnDefaultSfondo();
		int limite = stringa.caricaRoba("Buff").get(2);
		temp++;
		if( temp > limite)
			temp = 0;
		
		//AD OGNI MOFICA CHIAMA IL METODO PER SCRIVERE
		stringa.scriviSfondo(temp);
	}
	
	public void prevSfondo() {
		int temp = stringa.returnDefaultSfondo();
		int limite = stringa.caricaRoba("Buff").get(2);
		temp--;
		if( temp < 0)
			temp = limite;
		stringa.scriviSfondo(temp);
	}
}
