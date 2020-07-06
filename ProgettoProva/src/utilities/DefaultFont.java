package utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class DefaultFont {

	private static DefaultFont defaultFont = null;
	private static final String RESOURCES = "/res/";
	private Font retroBit;

	private DefaultFont() {
		caricaFont();
	}

	private void caricaFont() {
		try {
			String path = RESOURCES + "font" + "/";
			URL url = getClass().getResource(path);
			Path p = Paths.get(url.toURI());
			retroBit = Font.createFont(Font.TRUETYPE_FONT, new File(p.toFile() + File.separator + "8-Bit Madness.ttf"))
					.deriveFont(33f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(
					Font.createFont(Font.TRUETYPE_FONT, new File(p.toFile() + File.separator + "8-Bit Madness.ttf"))
							.deriveFont(33f));
		} catch (FontFormatException | IOException | URISyntaxException e) {
			JOptionPane.showMessageDialog(null, "UNEXPECTED ERROR", "FONT ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public Font returnFont(float size) {
		return retroBit.deriveFont(size);
	}

	// FARE METODO CHE CAMBIA COLORE E POSSIBILMENTE FONT

	public void impostaFont(JLabel l, String s) {
		l.setFont(retroBit);
		l.setForeground(Color.white);
		l.setText(s);
	}

	public void impostaImamagineFont(JLabel l, Integer s, Image i) {
		l.setFont(retroBit);
		l.setForeground(Color.white);
		l.setIcon(new ImageIcon(i));
		l.setText(Integer.toString(s));
	}

	public static DefaultFont getIstance() {
		if (defaultFont == null)
			defaultFont = new DefaultFont();
		return defaultFont;
	}
}
