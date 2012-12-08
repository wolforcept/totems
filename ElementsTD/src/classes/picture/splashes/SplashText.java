package classes.picture.splashes;

import java.awt.Color;

import classes.main.Data;

public class SplashText extends Splash {

	private String text;
	private double hspeed, vspeed;
	private Color color;
	private boolean downwards;

	public SplashText(Data data, double x, double y, String text,
			boolean downwards, Color c) {
		super(data, x, y);
		this.text = text;
		vspeed = 5;
		hspeed = Math.random() * 4 - 2;
		color = c;
		this.downwards = downwards;
	}

	public String getText() {
		return text;
	}

	public void step() {
		if (downwards)
			addY(vspeed);
		else
			addY(-vspeed);
		addX(hspeed);
		vspeed *= .9;
		if (vspeed <= 0.1) {
			remove();
		}
	}

	public Color getColor() {
		return color;
	}
}
