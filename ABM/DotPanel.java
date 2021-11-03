package ABM;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DotPanel extends JPanel {

	public static final Color BLACK=Color.BLACK;
	public static final Color BLUE=Color.BLUE;
	public static final Color CYAN=Color.CYAN;
	public static final Color DARK_GREY=Color.DARK_GRAY;
	public static final Color GRAY=Color.GRAY;
	public static final Color GREEN=Color.GREEN;
	public static final Color LIGHT_GREY=Color.LIGHT_GRAY;
	public static final Color MAGENTA=Color.MAGENTA;
	public static final Color ORANGE=Color.ORANGE;
	public static final Color PINK=Color.PINK;
	public static final Color RED=Color.RED;
	public static final Color WHITE=Color.WHITE;
	public static final Color YELLOW=Color.YELLOW;
	
	private static BufferedImage offscreenImage;
	private static Graphics offscreen;
	private int dotSize;
	private int width;
	private int height;
	private Color penColor;
	private boolean autoShow;
	long delay;
	
	public DotPanel(int w, int h, int dotSize) {
		this.dotSize=dotSize;
		width=w*dotSize;
		height=h*dotSize;
		autoShow=false;
	}
	
	
	public void setAutoShow(boolean b) {
		autoShow=b;
	}
	
	public int getHeightInDots() {
		return height/dotSize;
	}
	public int getWidthInDots() {
		return width/dotSize;
	}
	
	public int getDotSize() {
		return dotSize;
	}

	public void init() {
		width=this.getWidth();
		height=this.getHeight();
		
		if(width==0 || height==0) {
			System.out.println("Error");
			System.exit(-1);
		}
		offscreenImage=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		offscreen=offscreenImage.createGraphics();
	}
	
	public void clear() {
		clear(BLACK);
	}
	
	public void clear(Color color) {
		offscreen.setColor(color);
		offscreen.fillRect(0, 0, width, height);
		offscreen.setColor(penColor);
	}

	public void setPenColor(Color penColor) {
		this.penColor=penColor;
		offscreen.setColor(penColor);
	}
	
	public Dimension getPreferedSize() {
		return new Dimension(width, height);
	}

	public void repaintAndSleep(int delay) {
		this.repaint();
		
		try {
			Thread.currentThread().sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("Error sleeping");
		}
	}


	public void drawSquare(int x, int y, Color c) {
		setPenColor(c);
		offscreen.fillRect(x*dotSize, y*dotSize, 2*dotSize, 2*dotSize);
		if(autoShow) {
			repaintAndSleep(0);
		}
		
	}


	public void drawCircle(int xPoint, int yPoint, Color color) {
		// TODO Auto-generated method stub
		
	}
	
	
}
