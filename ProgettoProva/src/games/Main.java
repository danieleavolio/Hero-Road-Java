package games;

import java.io.IOException;
import javax.swing.JFrame;

import utilities.AnimationHandler;
import utilities.ImagesLoader;

public class Main {
	public static void main(String[] args) throws IOException {
		JFrame f = new JFrame("Prova");
		f.setSize(1200, 600);
		f.setUndecorated(true);
		f.setLocationRelativeTo(null);
		ImagesLoader.getInstance().loadImages();
		AnimationHandler.getInstance().loadImages();
		TitleScreen t = new TitleScreen();
		f.add(t);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

