package resLoader;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * Handles image importing and resizing based on an image file from the project
 * resources and a new desired width and height
 * 
 * @author Samuel Brenner
 *
 */

public class ImageHandler {

	public static BufferedImage loadAndResize(String file, int width, int height) {

		BufferedImage img = null;

		try {
			img = ImageIO.read(ResourceLoader.load(file));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();

		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return resized;
	}

}
