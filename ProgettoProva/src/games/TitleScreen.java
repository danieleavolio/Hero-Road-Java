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

import application.Game;
import application.GameLoop;
import application.controller.MovementController;
import application.view.GraphicPanel;
import utilities.AudioClass;
import utilities.EstrazioneStringhe;
import utilities.GestoreImmagini;

	public class TitleScreen extends JPanel{
		
		private static final long serialVersionUID = 1L;
		private static final int START = 0;
		private static final int OPTION = 1;
		private static final int SHOP = 2;
		private static final int EXIT = 3;
		private static final int NEXT = 13;
		private static final int PREV = 14;
		private static final int ABOUT = 15;
		
		private static final int MAINMUSIC = 0;
		private static final int BUTTONMUSIC = 1;
		
		private static final int MAINLOGO = 0;

		GestoreImmagini caricaImmagini = GestoreImmagini.getInstance();
		AudioClass gestoreAudio = AudioClass.getInstance();
		EstrazioneStringhe stringa = EstrazioneStringhe.getInstance();
		
		private ImageIcon background;
		private JButton start;	
		private JButton shop;
		private JButton option;
		private JButton about;
		private JButton exit;
		private JButton next;
		private JButton prev;
		private JLabel logo;
		private boolean started = false;
		
		
		public TitleScreen() {
		start = new JButton();		
		shop = new JButton();
		option = new JButton();
		about = new JButton();
		exit = new JButton();
		next = new JButton();
		prev = new JButton();
		
		
		background = caricaImmagini.setSfondo(stringa.returnDefaultSfondo());
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	
			start.setIcon(new ImageIcon(caricaImmagini.setButton(START, 292, 52)));
			shop.setIcon(new ImageIcon(caricaImmagini.setButton(SHOP, 292, 52)));
			option.setIcon(new ImageIcon(caricaImmagini.setButton(OPTION, 292, 52)));
			about.setIcon(new ImageIcon(caricaImmagini.setButton(ABOUT, 292, 52)));
			exit.setIcon(new ImageIcon(caricaImmagini.setButton(EXIT, 292, 52)));
			next.setIcon(new ImageIcon(caricaImmagini.setButton(NEXT, 49, 32)));
			prev.setIcon(new ImageIcon(caricaImmagini.setButton(PREV, 49, 32)));
			logo = new JLabel(new ImageIcon(caricaImmagini.setLabel(MAINLOGO, 427, 237)));
			
			Box b = Box.createVerticalBox();
			Box b1 = Box.createHorizontalBox();
			b1.add(prev); b1.add(Box.createHorizontalGlue()); b1.add(next);
		    b.add(Box.createRigidArea(new Dimension(415,0))); //PERFETTAMENTE AL CENTRO
			b.add(logo); b.add(Box.createRigidArea(new Dimension(0,10)));
			b.add(start); b.add(Box.createRigidArea(new Dimension(0,10)));
		    b.add(shop); b.add(Box.createRigidArea(new Dimension(0,10)));
		    b.add(option); b.add(Box.createRigidArea(new Dimension(0,10)));
		    b.add(about); b.add(Box.createRigidArea(new Dimension(0,10)));
		    b.add(exit); add(b1); add(b);
		    addListeners(this);
		    addMouse();
		    eseguiOP();
		}
		 
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
		}

		private void addListeners(JPanel p) {
			
				start.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					gestoreAudio.pressedSound(BUTTONMUSIC);
					Container c = p.getParent();
					c.remove(p);
					
					GraphicPanel gp = new GraphicPanel();
					MovementController mc = new MovementController(gp);
					gp.addKeyListener(mc);
					c.add(gp);
					GameLoop gameLoop = new GameLoop(gp, mc);
					if (started == false) {
						Thread t = new Thread(gameLoop);
						t.start();
					}
					started= true;
					Game.getInstance().running = true;
	                Game.getInstance().resetCondition();
					gp.requestFocus();
					gp.setFocusable(true);
					Game.getInstance().bitesTheDust();
					c.revalidate();
				}
			});
				
				shop.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					gestoreAudio.pressedSound(BUTTONMUSIC);
					Container c = p.getParent();
					c.remove(p);
					c.add(new StorePane());
					c.revalidate();
				}
			});
				
				option.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					gestoreAudio.pressedSound(BUTTONMUSIC);
					Container c = p.getParent();
						c.remove(p);
						c.add(new OptionPane());
						c.revalidate();
				}
			});
				
				about.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						gestoreAudio.pressedSound(BUTTONMUSIC);
						Container c = p.getParent();
							c.remove(p);
							c.add(new AboutPane());
							c.revalidate();
						
					}
				});
			
				exit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					gestoreAudio.pressedSound(BUTTONMUSIC);
					System.exit(0);
				}
			});
			
			prev.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					gestoreAudio.pressedSound(BUTTONMUSIC);
					caricaImmagini.prevSfondo();
					background = caricaImmagini.setSfondo(stringa.returnDefaultSfondo());
					
				}
			});
			
			next.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					gestoreAudio.pressedSound(BUTTONMUSIC);
					caricaImmagini.nextSfondo();
					background = caricaImmagini.setSfondo(stringa.returnDefaultSfondo());
				}
			});
		}
		
		private void addMouse() {
			start.addMouseListener(new buttonListener(start, caricaImmagini.setButton(START, 292, 52), caricaImmagini.setButtonClicked(START, 292, 52)));
			shop.addMouseListener(new buttonListener(shop, caricaImmagini.setButton(SHOP, 292, 52), caricaImmagini.setButtonClicked(SHOP, 292, 52)));
			option.addMouseListener(new buttonListener(option, caricaImmagini.setButton(OPTION, 292, 52), caricaImmagini.setButtonClicked(OPTION, 292, 52)));
			about.addMouseListener(new buttonListener(about, caricaImmagini.setButton(ABOUT, 292, 52), caricaImmagini.setButtonClicked(ABOUT, 292, 52)));
			exit.addMouseListener(new buttonListener(exit, caricaImmagini.setButton(EXIT, 292, 52), caricaImmagini.setButtonClicked(EXIT, 292, 52)));
			prev.addMouseListener(new buttonListener(prev, caricaImmagini.setButton(PREV, 49, 32), caricaImmagini.setButtonClicked(PREV, 49, 32)));
			next.addMouseListener(new buttonListener(next, caricaImmagini.setButton(NEXT, 49, 32), caricaImmagini.setButtonClicked(NEXT, 49, 32)));
		}
		
		public void eseguiOP() {
			gestoreAudio.nuovaOP(3, MAINMUSIC);
			gestoreAudio.nuovaOP(4, MAINMUSIC);
			gestoreAudio.nuovaOP(7, MAINMUSIC);
			gestoreAudio.nuovaOP(8, MAINMUSIC);
		}
	
}
