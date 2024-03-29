package classes.picture.GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Animation {

	private Image[] array;
	private int width, height;
	private int length;
	private double speed;
	private boolean isRotatable;

	public Animation(Image image, double speed, int aLength,
			boolean aIsRotatable) {
		length = aLength;
		isRotatable = aIsRotatable;
		if (length == 1) {
			speed = 0;
			Image images[] = { image };
			array = images;
			width = image.getWidth(null);
			height = image.getHeight(null);
		} else {

			this.speed = speed;
			array = new BufferedImage[length];

			height = image.getHeight(null);
			width = (int) Math.floor(image.getWidth(null) / length);

			for (int i = 0; i < length; i++) {

				array[i] = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_ARGB);
				Graphics g = array[i].getGraphics();
				g.drawImage(image, 0, 0, width, height, width * i, 0, width
						* (i + 1), height, null);

			}
		}
	}

	public boolean isRotatable() {
		return isRotatable;
	}

	public Image getImage(int i) {
		if (i >= array.length) {
			return array[0];// TODO THIS GAVE AN ERROR
		}
		return array[i];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getLen() {
		return length;
	}

	public double getSpeed() {
		return speed;
	}
}
