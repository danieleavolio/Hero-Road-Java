package games;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utilities.AudioClass;
import utilities.GestoreImmagini;

public class OptionPane extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int BACK = 4;

	private static final int VOLUMEDOWN = 5;
	private static final int VOLUMEUP = 6;
	private static final int VOLUMEON = 7;
	private static final int VOLUMEOFF = 8;
	

	private static final int BUTTONMUSIC = 1;

	private static final int OPTION = 1;
	private static final int SFONDO = 2;

	GestoreImmagini caricaImmagini = GestoreImmagini.getInstance();
	AudioClass gestoreAudio = AudioClass.getInstance();
	private JButton back;
	private JButton menoButton;
	private JButton piuButton;
	private JButton volumeOnOff;
	private JLabel optionLabel;
	private JLabel numVolume;

	public OptionPane() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Box b = Box.createHorizontalBox();
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		back = new JButton();
		menoButton = new JButton();
		piuButton = new JButton();
		volumeOnOff = new JButton();

		back.setIcon(new ImageIcon(caricaImmagini.setButton(BACK, 100, 67)));
		piuButton.setIcon(new ImageIcon(caricaImmagini.setButton(VOLUMEUP, 200, 135)));
		menoButton.setIcon(new ImageIcon(caricaImmagini.setButton(VOLUMEDOWN, 200, 135)));
		volumeOnOff.setIcon(new ImageIcon(caricaImmagini.buttonVolume(gestoreAudio.getVolume())));

		optionLabel = new JLabel(new ImageIcon(caricaImmagini.setLabel(OPTION, 350, 70)));
		numVolume = new JLabel(new ImageIcon(caricaImmagini.cambiaVolume(gestoreAudio.getVolume())));

		b.add(back); b.add(Box.createRigidArea(new Dimension(325, 0))); b.add(optionLabel); b.add(Box.createHorizontalGlue());
		b1.add(Box.createVerticalGlue()); b1.add(volumeOnOff);
		b2.add(menoButton); b2.add(numVolume); b2.add(piuButton);
		addListeners(this);
		this.add(b);
		this.add(b1);
		this.add(b2);
		MouseListeners();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(caricaImmagini.setLabel(SFONDO, this.getWidth(), this.getHeight()), 0, 0, null);

	}

	private void addListeners(JPanel p) {
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gestoreAudio.pressedSound(BUTTONMUSIC);

				Container c = p.getParent();
				c.remove(p);
				c.add(new TitleScreen());
				c.revalidate();
			}
		});

		piuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// AUMENTA VOLUME MUSICA SE NON E' GIA' AL 100%
				gestoreAudio.pressedSound(BUTTONMUSIC);
				AudioClass.getInstance().upVolume();
				numVolume.setIcon(new ImageIcon(caricaImmagini.cambiaVolume(gestoreAudio.getVolume())));
				volumeOnOff.setIcon(new ImageIcon(caricaImmagini.buttonVolume(gestoreAudio.getVolume())));
				MouseListeners();
			}
		});

		menoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// DIMINUISCI VOLUME MUSICA SE NON E' GIA' ALLO 0%
				gestoreAudio.pressedSound(BUTTONMUSIC);
				AudioClass.getInstance().lowerVolume();
				numVolume.setIcon(new ImageIcon(caricaImmagini.cambiaVolume(gestoreAudio.getVolume())));
				volumeOnOff.setIcon(new ImageIcon(caricaImmagini.buttonVolume(gestoreAudio.getVolume())));
				MouseListeners();
			}
		});

		volumeOnOff.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// MUTA IL VOLUME

				gestoreAudio.muteVolume();
				gestoreAudio.pressedSound(BUTTONMUSIC);
				volumeOnOff.setIcon(new ImageIcon(caricaImmagini.buttonVolume(gestoreAudio.getVolume())));
				numVolume.setIcon(new ImageIcon(caricaImmagini.cambiaVolume(gestoreAudio.getVolume())));

				volumeOnOff.setIcon(new ImageIcon(caricaImmagini.buttonVolume(gestoreAudio.getVolume())));
				MouseListeners();
				
			}
		});
	}

	private void MouseListeners() {
		
		
		if(gestoreAudio.getVolume() == 0)
			volumeOnOff.addMouseListener(new buttonListener(volumeOnOff, caricaImmagini.setButton(VOLUMEOFF, 400, 270), caricaImmagini.setButtonClicked(VOLUMEOFF, 400, 270)));
		
		else
			volumeOnOff.addMouseListener(new buttonListener(volumeOnOff, caricaImmagini.setButton(VOLUMEON, 400, 270), caricaImmagini.setButtonClicked(VOLUMEON, 400, 270)));
		
		back.addMouseListener(new buttonListener(back, caricaImmagini.setButton(BACK, 100, 67),
				caricaImmagini.setButtonClicked(BACK, 100, 67)));
		piuButton.addMouseListener(new buttonListener(piuButton, caricaImmagini.setButton(VOLUMEUP, 200, 135),
				caricaImmagini.setButtonClicked(VOLUMEUP, 200, 135)));
		menoButton.addMouseListener(new buttonListener(menoButton, caricaImmagini.setButton(VOLUMEDOWN, 200, 135),
				caricaImmagini.setButtonClicked(VOLUMEDOWN, 200, 135)));
	}
	
}
