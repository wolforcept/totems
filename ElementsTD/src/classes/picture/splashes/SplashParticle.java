package classes.picture.splashes;

import classes.main.Data;

public class SplashParticle extends Splash {

	private double hspeed, vspeed, health;
	private float alpha;
	private double fade;

	public SplashParticle(Data data, double x, double y, String imageName,
			double imageAngle, double aHspeed, double aVspeed, double aHealth,
			double fade) {
		super(data, x, y);
		setImages(imageName);
		setAngle(imageAngle);
		hspeed = aHspeed;
		vspeed = aVspeed;
		health = aHealth;
		alpha = 1;
		this.fade = 1 - fade;
	}

	public void setTransparency(int transparency) {
		this.alpha = transparency;
	}

	public void step() {
		alpha *= fade;
		nextImage();
		setAlpha(alpha);
		addX(hspeed);
		addY(vspeed);
		health--;
		if (health <= 0) {
			remove();
		}
	}

}
