package games;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utilities.AudioClass;
import utilities.DefaultFont;
import utilities.EstrazioneStringhe;
import utilities.GestoreImmagini;

public class StorePane extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int BACK = 4;
	private static final int VITA = 9; // MAX 3
	private static final int ATTACCO = 10; // MAX 3
	private static final int NUOVISFONDI = 11; // MAX 3
	private static final int SECONDCHANCE = 12; // MAX 1

	private static final int MAINMUSIC = 0;
	private static final int BUTTONMUSIC = 1;
	private static final int BUYMUSIC = 2;
	private static final int SHOPMUSIC = 4;

	private static final int SFONDO = 2;
	private static final int STORE = 8;
	private static final int LABELVITA = 9;
	private static final int LABELATTACCO = 10;
	private static final int LABELSFONDI = 11;
	private static final int LABELSECONDCHANCE = 12;
	private static final int LABELSOLDI = 13;

	private static final int DESCRIZIONEVITA = 0;
	private static final int DESCRIZIONEATTACCO = 1;
	private static final int DESCRIZIONESFONDI = 2;
	private static final int DESCRIZIONESECONDCHANCE = 3;

	GestoreImmagini caricaImmagini = GestoreImmagini.getInstance();
	AudioClass gestoreAudio = AudioClass.getInstance();
	EstrazioneStringhe stringa = EstrazioneStringhe.getInstance();
	DefaultFont formattaTesto = DefaultFont.getIstance();

	private JButton back;
	private JLabel storeLabel;
	private JLabel soldiLabel;

	private JButton vita;
	private JLabel descrizioneVita;
	private JLabel vitaLabel;

	private JButton attacco;
	private JLabel descrizioneAttacco;
	private JLabel attaccoLabel;

	private JButton nuoviSfondi;
	private JLabel descrizioneSfondi;
	private JLabel nuoviSfondiLabel;

	private JButton secondaChance;
	private JLabel descrizioneSecondChance;
	private JLabel secondaChanceLabel;

	public StorePane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Box b = Box.createHorizontalBox();
		Box all = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createHorizontalBox();
		Box b4 = Box.createHorizontalBox();
		back = new JButton();

		vita = new JButton();
		descrizioneVita = new JLabel();

		attacco = new JButton();
		descrizioneAttacco = new JLabel();

		nuoviSfondi = new JButton();
		descrizioneSfondi = new JLabel();

		secondaChance = new JButton();
		descrizioneSecondChance = new JLabel();

		soldiLabel = new JLabel();
		drawLabel();

		back.setIcon(new ImageIcon(caricaImmagini.setButton(BACK, 100, 67)));

		vita.setIcon(new ImageIcon(caricaImmagini.setButton(VITA, 160, 70)));
		attacco.setIcon(new ImageIcon(caricaImmagini.setButton(ATTACCO, 160, 70)));
		nuoviSfondi.setIcon(new ImageIcon(caricaImmagini.setButton(NUOVISFONDI, 160, 70)));
		secondaChance.setIcon(new ImageIcon(caricaImmagini.setButton(SECONDCHANCE, 160, 70)));

		storeLabel = new JLabel(new ImageIcon(caricaImmagini.setLabel(STORE, 350, 70)));
		vitaLabel = new JLabel(new ImageIcon(caricaImmagini.setLabel(LABELVITA, 80, 70)));
		attaccoLabel = new JLabel(new ImageIcon(caricaImmagini.setLabel(LABELATTACCO, 80, 70)));
		nuoviSfondiLabel = new JLabel(new ImageIcon(caricaImmagini.setLabel(LABELSFONDI, 80, 70)));
		secondaChanceLabel = new JLabel(new ImageIcon(caricaImmagini.setLabel(LABELSECONDCHANCE, 80, 70)));

		b.add(back); b.add(Box.createHorizontalGlue()); b.add(storeLabel); b.add(Box.createHorizontalGlue());b.add(soldiLabel);
		b1.add(vitaLabel); b1.add(Box.createHorizontalGlue()); b1.add(descrizioneVita); b1.add(Box.createHorizontalGlue()); b1.add(vita);
		b2.add(attaccoLabel); b2.add(Box.createHorizontalGlue()); b2.add(descrizioneAttacco); b2.add(Box.createHorizontalGlue()); b2.add(attacco);
		b3.add(nuoviSfondiLabel); b3.add(Box.createHorizontalGlue()); b3.add(descrizioneSfondi); b3.add(Box.createHorizontalGlue()); b3.add(nuoviSfondi);
		b4.add(secondaChanceLabel); b4.add(Box.createHorizontalGlue()); b4.add(descrizioneSecondChance); b4.add(Box.createHorizontalGlue()); b4.add(secondaChance);
		all.add(Box.createRigidArea(new Dimension(0, 40))); all.add(b1);
		all.add(Box.createRigidArea(new Dimension(0, 60)));all.add(b2);
		all.add(Box.createRigidArea(new Dimension(0, 60))); all.add(b3);
		all.add(Box.createRigidArea(new Dimension(0, 60))); all.add(b4);
		all.add(Box.createRigidArea(new Dimension(0, 60)));
		gestoreAudio.nuovaOP(MAINMUSIC, SHOPMUSIC);

		addListeners(this);
		add(b);
		add(all);
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

		vita.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (stringa.riscriviFile(DESCRIZIONEVITA, stringa.returnPrezzo(DESCRIZIONEVITA))) {
					gestoreAudio.pressedSound(BUYMUSIC);
					drawLabel();
					repaint();
				}

			}
		});

		attacco.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (stringa.riscriviFile(DESCRIZIONEATTACCO, stringa.returnPrezzo(DESCRIZIONEATTACCO))) {
					gestoreAudio.pressedSound(BUYMUSIC);
					drawLabel();
					repaint();
				}
			}
		});

		nuoviSfondi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (stringa.riscriviFile(DESCRIZIONESFONDI, stringa.returnPrezzo(DESCRIZIONESFONDI))) {
					gestoreAudio.pressedSound(BUYMUSIC);
					drawLabel();
					repaint();
				}
			}
		});

		secondaChance.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (stringa.riscriviFile(DESCRIZIONESECONDCHANCE, stringa.returnPrezzo(DESCRIZIONESECONDCHANCE))) {
					gestoreAudio.pressedSound(BUYMUSIC);
					drawLabel();
					repaint();

				}
			}
		});
	}

	public void drawLabel() {
		ArrayList<String> s = stringa.caricaScorte();
		ArrayList<Integer> t = stringa.caricaRoba("Buff");

		formattaTesto.impostaFont(descrizioneVita, s.get(DESCRIZIONEVITA));
		formattaTesto.impostaFont(descrizioneAttacco, s.get(DESCRIZIONEATTACCO));
		formattaTesto.impostaFont(descrizioneSfondi, s.get(DESCRIZIONESFONDI));
		formattaTesto.impostaFont(descrizioneSecondChance, s.get(DESCRIZIONESECONDCHANCE));
		formattaTesto.impostaImamagineFont(soldiLabel, t.get(4), caricaImmagini.setLabel(LABELSOLDI, 30, 30));
	}

	private void MouseListeners() {

		back.addMouseListener(new buttonListener(back, caricaImmagini.setButton(BACK, 100, 67),
				caricaImmagini.setButtonClicked(BACK, 100, 67)));

		vita.addMouseListener(new buttonListener(vita, caricaImmagini.setButton(VITA, 160, 70),
				caricaImmagini.setButtonClicked(VITA, 160, 70)));

		attacco.addMouseListener(new buttonListener(attacco, caricaImmagini.setButton(ATTACCO, 160, 70),
				caricaImmagini.setButtonClicked(ATTACCO, 160, 70)));

		nuoviSfondi.addMouseListener(new buttonListener(nuoviSfondi, caricaImmagini.setButton(NUOVISFONDI, 160, 70),
				caricaImmagini.setButtonClicked(NUOVISFONDI, 160, 70)));

		secondaChance
				.addMouseListener(new buttonListener(secondaChance, caricaImmagini.setButton(SECONDCHANCE, 160, 70),
						caricaImmagini.setButtonClicked(SECONDCHANCE, 160, 70)));
	}

}
