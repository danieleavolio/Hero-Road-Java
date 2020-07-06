package application.model;

import java.util.ArrayList;

import application.Game;

public class Terrain {
	
	private ArrayList<Block> terreno;
	public Terrain() {
		terreno = new ArrayList<Block>();
		initializeTerrain();
		System.err.println("Terrain Initialized");
	}
	
	public void initializeTerrain() {
		for (int i = 0; i < 1200; i++) {
			Block blocco = new Block (i, Game.ALTEZZA_TERRENO + Game.GRANDEZZA_PG - 1); // -1 per farli stare sul terreno
			terreno.add(blocco);
		}
	}
	
	/*public void flyingTerrain() {
		for (int i = 500; i < 800; i++) {
			Block blocco = new Block (i, 350);
			terreno.add(blocco);
		}
	}*/
	
	public ArrayList<Block> getTerreno(){
		return terreno;
	}
	
}
