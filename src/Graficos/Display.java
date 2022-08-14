package Graficos;

public final class Display {
	private final int WIDTH, HEIGHT;
	public final int[] pixels;
	
	// Temporal
	private final static int SPRITE_LATERAL_SIZE = 32;
	private final static int SPRITE_MASK = SPRITE_LATERAL_SIZE -1;
	///////////
	
	public Display(final int width, final int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.pixels = new int[WIDTH * HEIGHT];
	}
	
	public void clear() {
		for(int i = 0; i<pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void draw(final int offsetX, final int offsetY) {
		/*
		 * The offsets reffers to the camera distance from the initial point reference
		 * 
		 */
		
		for(int y = 0; y<HEIGHT; y++) {
			int yPos = y + offsetY;
			if(yPos < 0 || yPos >= HEIGHT) {
				continue;
			}
			for(int x = 0; x<WIDTH; x++) {
				int xPos = x + offsetX;
				if(xPos <0 || xPos >= WIDTH) {
					continue;
				}
				// Temporal
				pixels[xPos + yPos * WIDTH] = Sprite.asfalt.pixels[(x & SPRITE_MASK) + (y & SPRITE_MASK) * SPRITE_LATERAL_SIZE];
				// the & function = if x>SPRITE_MASK: x = 0;
				///////////
			}
		}
	}
}
