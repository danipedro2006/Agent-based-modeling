package ABM;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DotPanel extends javax.swing.JPanel {
	private static final long serialVersionUID = -2909151487015547533L;

	private static BufferedImage offscreenImage;
	private static Graphics2D offscreen;

	private int dotSize;
	private int width;
	private int height;

	private Color penColor;
	private boolean autoShow;

	public DotPanel(int w, int h, int dotSize) {
		this.dotSize = dotSize;
		width = w * dotSize;
		height = h * dotSize;
		autoShow = false;
	}

	public void setAutoShow(boolean b) {
		autoShow = b;
	}

	public int getHeightInDots() {
		return height / dotSize;
	}

	public int getWidthInDots() {
		return width / dotSize;
	}

	public int getDotSize() {
		return dotSize;
	}

	public void init() {
		width = this.getWidth();
		height = this.getHeight();

		if (width == 0 || height == 0) {
			System.out.println("Error");
			System.exit(-1);
		}
		offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		offscreen = offscreenImage.createGraphics();
	}

	public void clear() {
		clear(Color.BLACK);
	}

	public void clear(Color color) {
		offscreen.setColor(color);
		offscreen.fillRect(0, 0, width, height);
		offscreen.setColor(penColor);
	}

	public void setPenColor(Color penColor) {
		this.penColor = penColor;
		offscreen.setColor(penColor);
	}

	public Dimension getPreferredSize() {
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

	public void paintComponent(Graphics g) {
		g.drawImage(offscreenImage, 0, 0, null);
	}

	public void drawSquare(int x, int y, Color c) {
		setPenColor(c);
		offscreen.fillRect(x * dotSize, y * dotSize, 2 * dotSize, 2 * dotSize);
		if (autoShow) {
			repaintAndSleep(0);
		}

	}

	public void drawCircle(int x, int y, Color c) {
		setPenColor(c);
		offscreen.fillOval(x * dotSize, y * dotSize, 2 * dotSize, 2 * dotSize);
		if (autoShow) {
			repaintAndSleep(0);
		}

	}
}
