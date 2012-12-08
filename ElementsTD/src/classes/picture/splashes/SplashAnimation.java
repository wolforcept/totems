package classes.picture.splashes;

import classes.main.Data;

public class SplashAnimation extends Splash {

	public SplashAnimation(Data data, double x, double y, String imageName) {
		super(data, x, y);
		setImages(imageName);
	}

	public void step() {
		nextImage();
		if (getImageIndex() >= getImageNumber() - 1) {
			remove();
		}
	}

}
