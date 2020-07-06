package utilities;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class AudioClass {

	
	private static AudioClass audioClass = null;
	public HashMap<Integer, Clip> audioPlayer;
	private static final String RESOURCES = "/res/";
	private AudioInputStream a;
	public Clip clip;
	private static double volume = 0.25; // VOLUME GLOBALE 
	private static double lastVolume = 0;

	private AudioClass () {
			audioPlayer = getResources();
			setVol(volume);
	}
	
	private HashMap<Integer,Clip> getResources(){
		HashMap<Integer,Clip> temp = new HashMap<Integer,Clip>();

		try {
			String path = RESOURCES + "audio" + "/";
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
			for (File audio : listOfResources) {
					a = AudioSystem.getAudioInputStream(new File(p + File.separator + audio.getName())); 
					clip = AudioSystem.getClip();
					clip.open(a);
					temp.put(index++, clip);
			}
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException | URISyntaxException e) {
					JOptionPane.showMessageDialog(null, "UNEXPECTED ERROR", "AUDIO ERROR", JOptionPane.ERROR_MESSAGE);
				}
			
		return temp;
	}
	
	public void startReplay(Integer i, boolean loop) {
		if(audioPlayer.get(i).isActive())
			return;
		
		if(audioPlayer.get(i).getFramePosition() == audioPlayer.get(i).getFrameLength())
			audioPlayer.get(i).setFramePosition(0);
		
		audioPlayer.get(i).setFramePosition(0);
				
		audioPlayer.get(i).start();
		
		if(loop)
			audioPlayer.get(i).loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void pressedSound(Integer i) {
		if(audioPlayer.get(i).isActive()) {
			audioPlayer.get(i).stop();
			audioPlayer.get(i).setFramePosition(0);
			audioPlayer.get(i).start();
		
			
		}
			
			audioPlayer.get(i).setFramePosition(0);
			audioPlayer.get(i).start();
		
		
	}
	
	public static AudioClass getInstance () {
		if (audioClass == null)
			audioClass = new AudioClass();
		return audioClass;
	}
	
	public void setVol (double vol) {
		for (Integer i : audioPlayer.keySet()) {
			FloatControl gain = (FloatControl) audioPlayer.get(i).getControl(FloatControl.Type.MASTER_GAIN);
			float dB = (float) (Math.log(vol) / Math.log(10) * 20); //float control è un oggetto per arrivare ad avere l'accesso al volume
			gain.setValue(dB); 	//db è decibel
		}	
	}
	
	public void upVolume() {
		if (volume < 1.0) {
			lastVolume = volume;
			volume+=0.25;
			setVol(volume);
		}
		
		if ( volume == 0) {
			volume = lastVolume;
			setVol(volume);
		}
	}
	
	public void lowerVolume() {
		if (volume > 0) {
			volume-=0.25;
			lastVolume = volume;
			setVol(volume);
		}
		
		if ( volume == 0) {
			volume = lastVolume;
			setVol(volume);
		}
	}
	
	public void muteVolume() {
		if (volume != 0.0) {
			lastVolume = volume;
			volume = 0;
			setVol(volume);
		}
		
		else {
			volume = lastVolume;
			setVol(volume);
		}
	}
	
	public void nuovaOP(Integer vecchia, Integer nuova) {
		audioPlayer.get(vecchia).stop();
		startReplay(nuova, true);
	}

	public double getVolume() {
		return volume;
	}
}
