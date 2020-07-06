package games;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import utilities.AudioClass;
import utilities.GestoreImmagini;

public class AboutPane extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int BACK = 4;

	private static final int BUTTONMUSIC = 1;

	private static final int ABOUT = 17;

	GestoreImmagini caricaImmagini = GestoreImmagini.getInstance();
	AudioClass gestoreAudio = AudioClass.getInstance();

	private JButton back;

	public AboutPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		back = new JButton();
		back.setIcon(new ImageIcon(caricaImmagini.setButton(BACK, 100, 67)));
		add(back);
		addListeners(this);
		addMouseListeners();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(caricaImmagini.setLabel(ABOUT, this.getWidth(), this.getHeight()), 0, 0, null);
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
	}

	private void addMouseListeners() {
		back.addMouseListener(new buttonListener(back, caricaImmagini.setButton(BACK, 100, 67),
				caricaImmagini.setButtonClicked(BACK, 100, 67)));
	}

}
