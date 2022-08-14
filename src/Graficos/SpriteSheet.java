package Graficos;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class SpriteSheet {
	// SpriteSheet model
	
	public final int[] pixels;
	private final int WIDTH;
	private final int HEIGHT;
	
	// SpriteSheet Collection
	public static SpriteSheet desierto = new SpriteSheet("/textures/Desierto.png", 320, 320);
	/////////////////////////
	
	
	public SpriteSheet(final String path, final int width, final int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
		
		// || Preparing pixels availables
		this.pixels = new int[WIDTH * HEIGHT];
		
		try {
			// || Loading the image
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			
			// || Get the rgb info from the image
			image.getRGB(
					// Start X
					0,
					// Start y
					0,
					// Width
					WIDTH,
					// Height
					HEIGHT,
					// Pixels array,
					pixels,
					// Offset
					0,
					// Scan Size
					WIDTH
			);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	public int[] getPixels() {
		return pixels;
	}


	public int getWIDTH() {
		return WIDTH;
	}


	public int getHEIGHT() {
		return HEIGHT;
	}
}
