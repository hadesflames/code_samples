package hades.rs3.tools.dtm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class DTMBuilderEditor extends JFrame{

	private JPanel contentPane;
	private JPanel imagePanel;
	private JScrollPane scroller;
	private BufferedImage img;
	private DTM first;
	private DTM potential;
	private ArrayList<DTM> list;
	private JButton btnAdd;
	
	/**
	 * Create the frame.
	 */
	public DTMBuilderEditor(BufferedImage image, final int scale){
		this.img = image;
		list = new ArrayList<DTM>();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(575, 285);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnExport = new JButton("Export");
		btnExport.setBounds(12, 212, 97, 25);
		btnExport.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(first == null || list == null || list.size() == 0)
					return;
				int firstX = first.p.x / scale;
				int firstY = first.p.y / scale;
				String out = "DTMPoint point = new DTMPoint(new Color(" + first.c.getRed() + ", " + first.c.getGreen() + ", " + first.c.getBlue() + "), new Tolerance(10, 10, 10));\n";
				DTM d = list.get(0);
				out += "DTMSubPoint points[] = {new DTMSubPoint(new ColourPoint(new Point(" + ((d.p.x / scale) - firstX) + ", " + ((d.p.y / scale) - firstY) + "), new Color(" + d.c.getRed() + ", " + d.c.getGreen() + ", " + d.c.getBlue() + ")), new Tolerance(10, 10, 10), 1)";
				for(int i = 1; i<list.size(); i++){
					d = list.get(i);
					out += ", new DTMSubPoint(new ColourPoint(new Point(" + ((d.p.x / scale) - firstX) + ", " + ((d.p.y / scale) - firstY) + "), new Color(" + d.c.getRed() + ", " + d.c.getGreen() + ", " + d.c.getBlue() + ")), new Tolerance(10, 10, 10), 1)";
				}
				out += "};";
				JFrame temp = new JFrame();
				temp.setSize(500, 275);
				temp.setLocationRelativeTo(null);
				temp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				temp.setResizable(false);
				temp.add(new JScrollPane(new JTextArea(out)));
				temp.setVisible(true);
			}
		});
		contentPane.add(btnExport);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(460, 212, 97, 25);
		btnClear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				potential = first = null;
				list.clear();
				imagePanel.repaint();
			}
		});
		contentPane.add(btnClear);
		
		imagePanel = new ImagePane();
		imagePanel.setPreferredSize(new Dimension(img.getWidth(null), img.getHeight(null)));
		imagePanel.addMouseListener(new MouseActions());
		scroller = new JScrollPane(imagePanel);
		scroller.setBounds(12, 13, 545, 186);
		contentPane.add(scroller);
		
		btnAdd = new JButton("Add");
		btnAdd.setBounds(351, 212, 97, 25);
		btnAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(potential == null)
					return;
				if(first == null)
					first = potential;
				else if(list != null)
					list.add(potential);
				potential = null;
				imagePanel.repaint();
			}
		});
		contentPane.add(btnAdd);
	}
	
	private class ImagePane extends JPanel{
		private ImagePane(){
			super();
		}
		
		public void paint(Graphics g){
			if(img != null)
				g.drawImage(img, 0, 0, null);
			if(first != null){
				g.setColor(new Color(0, 0, 255));
				g.drawOval(first.p.x - 5, first.p.y - 5, 10, 10);
				g.drawLine(first.p.x, first.p.y, first.p.x, first.p.y);
				if(list != null && list.size() > 0){
					g.setColor(new Color(0, 255, 0));
					for(DTM d : list){
						g.drawOval(d.p.x - 5, d.p.y - 5, 10, 10);
						g.drawLine(d.p.x, d.p.y, d.p.x, d.p.y);
						g.drawLine(first.p.x, first.p.y, d.p.x, d.p.y);
					}
				}
			}
			if(potential != null){
				g.setColor(new Color(255, 0, 0));
				g.drawOval(potential.p.x - 5, potential.p.y - 5, 10, 10);
				g.drawLine(potential.p.x, potential.p.y, potential.p.x, potential.p.y);
				if(first != null)
					g.drawLine(first.p.x, first.p.y, potential.p.x, potential.p.y);
			}
		}
	}
	
	private class MouseActions extends MouseAdapter{
		public void mouseClicked(MouseEvent e){
			if(list == null)
				return;
			Point p = new Point(e.getX(), e.getY());
			potential = new DTM(p, new Color(img.getRGB(p.x, p.y)));
			imagePanel.repaint();
		}
	}
	
	private class DTM{
		public Point p;
		public Color c;
		
		private DTM(Point p, Color c){
			this.p = p;
			this.c = c;
		}
	}
}