
package games;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class buttonListener implements MouseListener {
	JButton bottone;
	Image clicked;
	Image notClicked;

	public buttonListener(JButton bottone, Image clicked, Image notClicked) {
		this.bottone = bottone;
		this.clicked = clicked;
		this.notClicked = notClicked;
		setta();
	}

	private void setta() {
		bottone.setBorder(null);
		bottone.setContentAreaFilled(false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		bottone.setIcon(new ImageIcon(notClicked));
		setta();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		bottone.setIcon(new ImageIcon(clicked));
		setta();

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
