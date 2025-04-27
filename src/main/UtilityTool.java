package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class UtilityTool {
	public static final Random ra=new Random();
	public BufferedImage scaleImage(BufferedImage original,int width,int height) {
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0, 0, width, height, null);
		g2.dispose();
		return scaledImage;
	}
	public static int randomNumber(int min, int max) {
	
		if (min > max) {
		        throw new IllegalArgumentException("ERROR: El valor mínimo no puede ser mayor que el máximo.");
		    }
		return ra.nextInt((max - min) + 1) + min;
	}
}
