import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


public class ResourceLoader {
	public static Image loadImage(String path){
		Image image = null;
		try {
			InputStream input = ResourceLoader.class.getResourceAsStream(path);
			image = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}