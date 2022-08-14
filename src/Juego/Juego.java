package Juego;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import Graficos.Display;
import control.Keyboard;

public class Juego extends Canvas implements Runnable{
	public static final long serialVersionUID = 1;
	
	public static JFrame window;
	public static int ups = 0;
	public static int fps = 0;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String NAME = "JAVA PROGRAM";
	
	private static Thread thread;
	private static volatile boolean running = false;
	
	private static int x = 0;
	private static int y = 0;
	
	private static Keyboard keyboard;
	
	private static Display display;
	private static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private static int[] imagePixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Juego() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		window = new JFrame();
		window.setVisible(true);
		window.setResizable(false);
		window.setLayout(new BorderLayout());
		window.add(this, BorderLayout.CENTER);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		keyboard = new Keyboard();
		addKeyListener(keyboard);
		
		display = new Display(WIDTH, HEIGHT);
	}
	
	@Override
	public void run() {
		System.out.println("| Renderer started |");
		final int NS_PER_SEC = 1000000000;
		final byte UPS = 60;
		final double NS_PER_UPDATE = NS_PER_SEC / UPS;
		
		long firstRef = System.nanoTime();
		long counterRef = System.nanoTime();
		double time;
		double delta = 0;
		
		requestFocus();
		while(running) {
			final long loopStart = System.nanoTime();
			time = loopStart - firstRef;
			firstRef = loopStart;
			
			delta += time / NS_PER_UPDATE;
			while(delta>=1) {
				update();
				draw();
				delta--;
			}

			if(System.nanoTime() - counterRef > NS_PER_SEC) {
				window.setTitle(NAME + " | FPS: " + fps + " | UPS: " + ups);
				ups = 0;
				fps = 0;
				counterRef = System.nanoTime();
			}
		}
	}
	
	private synchronized void start() {
		thread = new Thread(this, "Graficos");
		thread.start();
		running = true;
	}
	
	private synchronized void stop() {
		try {
			if(this.thread != null && this.thread.isAlive()) {
				this.thread.join();
				running = false;
			}
		}
		catch(Exception e) {
			System.err.println(e);
		}
	}
	
	private void update() {
		keyboard.update();
		if(keyboard.up) {
			y++;
		}
		if(keyboard.down) {
			y--;
		}
		if(keyboard.left) {
			x++;
		}
		if(keyboard.right) {
			x--;
		}
		ups++;
	}
	
	private void draw() {
		BufferStrategy strategy = getBufferStrategy();
		if(strategy == null) {
			createBufferStrategy(3);
			return;
		}
		display.clear();
		display.draw(x, y);
		
		System.arraycopy(display.pixels, 0, imagePixels, 0, imagePixels.length);
		
		Graphics g = strategy.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		strategy.show();
		
		// Own Interpretation: imagePixels is a link to the static pixels of the image.
		// Because of that, with just modify imagePixels the changes are shown on 
		// the image draw.
		
		fps++;
	}

	public static void main(String[] args) {
		Juego juego = new Juego();
		juego.start();
	}
}
