package Graficos;

public class Sprite {
	// Sprite model
	
	private final int lateralSize;
	private int x, y;
	public int[] pixels;
	private final SpriteSheet sheet;
	
	// Sprite Collection
	public static Sprite asfalt = new Sprite(32, 0, 0, SpriteSheet.desierto);
	////////////////////
	
	public Sprite(final int lateralSize, final int column, final int row, SpriteSheet spriteSheet) {
		this.lateralSize = lateralSize;
		this.sheet = spriteSheet;
		
		// Give pixels capacity, 64*64, 32*32, 16*16
		this.pixels = new int[this.lateralSize * this.lateralSize];
		
		// Give the Sprite starting pixel coordinates
		this.x = column * lateralSize;
		this.y = row * lateralSize;
		
		// Extract Sprite from sheet
		for(int y = 0; y<this.lateralSize; y++) {
			for (int x = 0; x<this.lateralSize; x++) {
				// Iterating each column in sprite row limits
				// Example: from 0,0 to 0, 63, from 1, 0 to 1, 63 
				// but on a single dimension array.
				this.pixels[x + y * this.lateralSize] = 
						spriteSheet.pixels[(x + this.x) + (y + this.y) * this.sheet.getWIDTH()];
				/*
				* 
				* it's a strange unidimensional array system for replace a bidimentional one.
				* 
				*/
			}
		}
	}
}
