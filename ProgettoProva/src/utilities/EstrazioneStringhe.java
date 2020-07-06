package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class EstrazioneStringhe {
	
	private static EstrazioneStringhe instance = null;
	
	public static final int HEALT = 0;
	public static final int ATTACK = 1;
	public static final int SECONDCHANCE = 3;
	
	private String inventarioPath;
	private static final String RESOURCES = "/res/";
	private String risorse = "Descrizioni";
	private String buff = "Buff";
	
	
	private EstrazioneStringhe() {
		caricaScorte();
	}
	
	public File caricaPercorso(String filename) {
		File f = null;
		try {
			String path = RESOURCES + "/" + "file" + "/";
			URL url = getClass().getResource(path);
			Path p = Paths.get(url.toURI());
			f = new File(p.toFile(), filename);
		} catch (URISyntaxException e) {
			JOptionPane.showMessageDialog(null, "UNEXPECTED ERROR", "FILE ERROR", JOptionPane.ERROR_MESSAGE);
		}
	
		return f;
	}

	public ArrayList<String> caricaScorte() { //RESTITUISCE UNA ARRAY DI STRINGHE CHE SI ANDRANNO A METTERE POI NELLA LABEL
		ArrayList<String> s = new ArrayList<String>();
		try {
			File f = caricaPercorso(risorse);
			BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			
			while(r.ready()) {
				String string = r.readLine();
				s.add(string);
			}
			r.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "UNEXPECTED ERROR", "FILE ERROR", JOptionPane.ERROR_MESSAGE);

		}
		return s;
	}
	
	public ArrayList<Integer> caricaRoba(String filename) {
		ArrayList<Integer> s = new ArrayList<Integer>();
		try {
			File f = caricaPercorso(filename); //Passo la roba che devo passarmi
			BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			
			while(r.ready()) {
				s.add(Integer.parseInt(r.readLine()));
			}
			r.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "UNEXPECTED ERROR", "FILE ERROR", JOptionPane.ERROR_MESSAGE);

		}
		return s;
	}
	
	//FUNZIONE PER SOMMARE I SOLDI GUADAGNATI A QUELLI OTTENUTI
	public void aggiungiSoldi(int soldiGuadagnati) {
		
		ArrayList<Integer> buffPG = caricaRoba(buff);
		
		FileWriter output = null;
		try {
			//File f = new File(inventarioPath, buff);
			File f = caricaPercorso(buff);
			output = new FileWriter(f);
			BufferedWriter w = new BufferedWriter(output);
			
			for(int i = 0; i < buffPG.size(); i++) {
				if(i == buffPG.size()-1) {
					int soldiAttuali = buffPG.get(buffPG.size()-1);
					int somma = soldiGuadagnati + soldiAttuali;
					String s = Integer.toString(somma);
					w.append(s);
					w.newLine();
				}
				
				else {
					String string = Integer.toString(buffPG.get(i));
					w.append(string);
					w.newLine();
				}
			}
			
			w.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "UNEXPECTED ERROR", "FILE ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
	}
			
	
	public boolean riscriviFile(int tipo, int costo) {//A SECONDA DEL DATO CHE VIENE PASSATO, MODIFICA IL FILE
		//A SECONDA DI QUELLO CHE MODIFICO NELL'ARRAY, ANDRO' A RISCRIVERE
		ArrayList<Integer> buffPG = caricaRoba(buff);
		ArrayList<String> scorte = caricaScorte();
		
		
		int numBuff, soldiRimanenti;
		String str = scorte.get(tipo);
		int numOggetti = Integer.parseInt(str.substring(str.length()-1));
		
		//3 - 2 (DOVE 3 E' IL NUM MAX E 2 E' QUELLO NELLO SHOP, VUOL DIRE CHE HAI 1 SOLO POTENZIAMENTO)
		
		//MODIFICO LE CONDIZIONI
		if(tipo == 3) {
			int soldiAttuali = buffPG.get(buffPG.size()-1);
			
			if(soldiAttuali - costo >= 0 && numOggetti != 0) {
				numOggetti--;
				soldiRimanenti = soldiAttuali - costo;
				numBuff = 1 - numOggetti;
			}

			else
				return false;
		}
		
		else {
			int soldiAttuali = buffPG.get(buffPG.size()-1);
			
			if(soldiAttuali - costo >= 0 && numOggetti != 0) {
				numOggetti--;
				soldiRimanenti = soldiAttuali - costo;
				numBuff = 3 - numOggetti;
			}
			
			else
				return false;
		}
		
		
		scriviBuff(inventarioPath, buff, buffPG, tipo, numBuff, soldiRimanenti);
		ScriviInventario(inventarioPath, risorse, scorte, numOggetti, tipo);
		
		
		return true;
	}
	
	public static EstrazioneStringhe getInstance() {
		if (instance == null)
			instance = new EstrazioneStringhe();
		return instance;
	}
	
	public int returnPrezzo(int tipo) {
		ArrayList<Integer> a = caricaRoba("Prezzi"); 
		return a.get(tipo);
	}
	
	public int returnDefaultSfondo() {
		ArrayList<Integer> a = caricaRoba("DefaultSfondo"); 
		return a.get(0);
	}
	
	public void scriviSfondo(int index) {
		FileWriter output = null;
		try {
			File f = caricaPercorso("DefaultSfondo");
			output = new FileWriter(f);
			BufferedWriter w = new BufferedWriter(output);
			
			String s = Integer.toString(index);
			w.append(s);
			w.newLine();
			
			w.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "UNEXPECTED ERROR", "FILE ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	

	private void scriviBuff(String path, String filename, ArrayList<Integer> array, int tipo, int numBuff, int soldiRimanenti) {
		FileWriter output = null;
		try {
			File f = caricaPercorso(filename);
			output = new FileWriter(f);
			BufferedWriter w = new BufferedWriter(output);

			for (int i = 0; i < array.size(); i++) {
				if (i == tipo) {
					String s = Integer.toString(numBuff);
					w.append(s);
					w.newLine();
				}

				else if (i == array.size() - 1) { //soldi
					String s = Integer.toString(soldiRimanenti);
					w.append(s);
					w.newLine();
				}

				else {
					String s = Integer.toString(array.get(i));
					w.append(s);
					w.newLine();
				}

			}

			w.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "UNEXPECTED ERROR", "FILE ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	private void ScriviInventario(String path, String filename, ArrayList<String> array, int numOggetti, int tipo) {
		FileWriter output = null;
		try {
			File f = caricaPercorso(filename);
			output = new FileWriter(f);
			BufferedWriter w = new BufferedWriter(output);
			
			for(int i = 0; i < array.size(); i++) {
				String string = array.get(i);
				if(i == tipo) {
					w.append(string.substring(0, string.length()-1) + numOggetti);
					w.newLine();
				}
				
				else {
					w.append(string);
					w.newLine();
				}
			}

			w.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "UNEXPECTED ERROR", "FILE ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}
