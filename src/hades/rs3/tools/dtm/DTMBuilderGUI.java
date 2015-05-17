package hades.rs3.tools.dtm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.tribot.api.Screen;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class DTMBuilderGUI extends JFrame{
	private JPanel contentPane;
	private JPanel gameFrame;
	private JScrollPane scroller;
	private BufferedImage img;
	private Image sub;
	private Point mousePos;
	private Point pressed, released;
	private boolean dragging = false;
	
	public DTMBuilderGUI(){
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1150, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		gameFrame = new GameScreen();
		scroller = new JScrollPane(gameFrame);
		scroller.setBounds(12, 13, 1120, 701);
		MouseActions mouse = new MouseActions();
		gameFrame.addMouseListener(mouse);
		gameFrame.addMouseMotionListener(mouse);
		contentPane.add(scroller);
		
		JButton btnCaptureScreen = new JButton("Capture Screen");
		btnCaptureScreen.setBounds(12, 727, 130, 25);
		btnCaptureScreen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				captureScreen();
			}
		});
		contentPane.add(btnCaptureScreen);
	}
	
	private void createBox(int x, int y){
		Point p = getTopLeft(x, y);
		mousePos = new Point(x, y);
		ColorModel cm = img.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = img.copyData(null);
		BufferedImage sub = new BufferedImage(cm, raster, isAlphaPremultiplied, null).getSubimage(p.x, p.y, 7, 7);
		sub.setRGB(3, 3, Color.RED.getRGB());
		this.sub = sub.getScaledInstance(100, 100, Image.SCALE_REPLICATE);
	}
	
	private Point getTopLeft(int x, int y){
		int newX = x >= 3 ? x - 3 : 0;
		int newY = y >= 3 ? y - 3 : 0;
		return new Point(newX, newY);
	}
	
	private Point getDrawPos(){
		return new Point(mousePos.x - 100 >= 0 ? mousePos.x - 100 : mousePos.x + 100, mousePos.y - 100 >= 0 ? mousePos.y - 100 : mousePos.y + 100);
	}
	
	private void captureScreen(){
		Rectangle r = Screen.getViewport();
		if(r == null || r.width == 0 || r.height == 0){
			img = null;
			return;
		}
		img = new BufferedImage(r.width, r.height, BufferedImage.TYPE_INT_RGB);
		for(int i = 0; i<r.width; i++)
			for(int j = 0; j<r.height; j++)
				img.setRGB(i, j, Screen.getColourAt(i, j).getRGB());
		gameFrame.setPreferredSize(new Dimension(r.width, r.height));
		gameFrame.revalidate();
	}
	
	private void openEditor(BufferedImage img){
		int scale = 8;
		DTMBuilderEditor frame = new DTMBuilderEditor(toBufferedImage(img.getScaledInstance(img.getWidth() * scale, img.getHeight() * scale, Image.SCALE_REPLICATE)), scale);
		frame.setVisible(true);
	}
	
	public BufferedImage toBufferedImage(Image img){
	    if(img instanceof BufferedImage)
	    	return (BufferedImage)img;

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	
	private class GameScreen extends JPanel{
		private GameScreen(){
			super();
		}
		
		public void paint(Graphics g){
			if(img != null)
				g.drawImage(img, 0, 0, null);
			if(mousePos != null && sub != null){
				Point p = getDrawPos();
				g.drawImage(sub, p.x, p.y, null);
			}
			if(pressed != null && released != null){
				Rectangle r = new Rectangle(released.x > pressed.x ? pressed.x : released.x, released.y > pressed.y ? pressed.y : released.y, released.x > pressed.x ? released.x - pressed.x : pressed.x - released.x, released.y > pressed.y ? released.y - pressed.y : pressed.y - released.y);
				g.setColor(new Color(255, 0, 0));
				g.drawRect(r.x, r.y, r.width, r.height);
				g.setColor(new Color(1f, 0f, 0f, 0.25f));
				g.fillRect(r.x, r.y, r.width, r.height);
			}
		}
	}
	
	private class MouseActions extends MouseAdapter{
		public void mouseClicked(MouseEvent e){}
		
		public void mousePressed(MouseEvent e){
			pressed = new Point(e.getX(), e.getY());
			released = null;
			dragging = true;
			sub = null;
			gameFrame.repaint();
		}
		
		public void mouseReleased(MouseEvent e){
			released = new Point(e.getX(), e.getY());
			BufferedImage temp = img.getSubimage(released.x > pressed.x ? pressed.x : released.x, released.y > pressed.y ? pressed.y : released.y, released.x > pressed.x ? released.x - pressed.x : pressed.x - released.x, released.y > pressed.y ? released.y - pressed.y : pressed.y - released.y);
			openEditor(temp);
			mouseMoved(e);
			dragging = false;
			gameFrame.repaint();
		}
		
		public void mouseEntered(MouseEvent e){}
		
		public void mouseExited(MouseEvent e){
			sub = null;
		}
		
		public void mouseDragged(MouseEvent e){
			released = new Point(e.getX(), e.getY());
			sub = null;
			gameFrame.repaint();
		}
		
		public void mouseMoved(MouseEvent e){
			int x = e.getX();
			int y = e.getY();
			if(!dragging){
				if(img == null || x > img.getWidth() || x < 0 || y > img.getHeight() || y < 0)
					return;
				createBox(x, y);
				gameFrame.repaint();
			}
		}
	}
}