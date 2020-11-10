package resLoader;

import java.io.InputStream;

/**
 * Creates an {@code InputStream} object that serves to import an asset from the
 * project resources
 * 
 * 
 * @author Samuel Brenner
 *
 */

public class ResourceLoader {

	public static InputStream load(String path) {

		InputStream input = ResourceLoader.class.getResourceAsStream(path);

		if (input == null) {
			input = ResourceLoader.class.getResourceAsStream("/" + path);
		}

		return input;
	}

}
