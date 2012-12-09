package classes.objects;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;

import classes.main.Data;
import classes.objects.enemies.EnemyParent;
import classes.picture.GUI.Animation;

public abstract class DrawableObject {

	private long id;
	private double x, y;

	private Animation animation;
	private int imageNumber;
	private double imageIndex;
	private String imageName;
	private Dimension size;

	private boolean removed;
	private double angle;
	private Data data;
	private float alpha;

	public DrawableObject(Data data, double x, double y) {
		this.data = data;
		removed = false;
		this.x = x;
		this.y = y;
		angle = 0;
		imageIndex = 0;
		alpha = 1f;

		if (this instanceof Tower) {
			setId(data.getNewId());
		} else if (this instanceof EnemyParent) {
			setId(data.getNewId());
		} else {
			setId(0);
		}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getX1() {
		return x - size.width / 2;
	}

	public double getY1() {
		return y - size.height / 2;
	}

	public void addX(double xx) {
		x += xx;
	}

	public void addY(double yy) {
		y += yy;
	}

	public Animation getAnimation() {
		return animation;
	}

	public long getId() {
		return id;
	}

	public int getWidth() {
		return size.width;
	}

	public int getHeight() {
		return size.height;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void remove() {
		removed = true;
	}

	public Point getIntSpot() {
		return new Point((int) x, (int) y);
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void setImages(String name) {
		animation = Data.getAnimation(name);
		imageName = name;
		imageNumber = animation.getLen();
		imageIndex = (int) (Math.random() * imageNumber);
		this.size = new Dimension(//
				animation.getWidth(), animation.getHeight());
	}

	public Data getData() {
		return data;
	}

	public Image getCurrentImage() {
		if (imageIndex > imageNumber) {
			System.out.println("ImageIndex(" + imageIndex + ") > ImageNumber("
					+ imageNumber + ") on class: " + getClass());
		}
		if (animation == null) {
			System.out.println("ANIMATION=NULL ERROR\nThread:"
					+ Thread.currentThread() + "\nClass:" + getClass());
		}
		return animation.getImage((int) imageIndex);
	}

	public void nextImage() {
		imageIndex += animation.getSpeed();
		if (imageIndex >= imageNumber) {
			imageIndex = 0;
		}
	}

	public int getImageIndex() {
		return (int) imageIndex;
	}

	public int getImageNumber() {
		return imageNumber;
	}

	public String getImageName() {
		return imageName;
	}

	public boolean isAt(int xx, int yy) {
		if (xx > getX1() && xx < (x + size.width / 2) && yy > getY1()
				&& yy < (y + size.height / 2)) {
			return true;
		}
		return false;
	}

	public void setId(long aId) {
		id = aId;
	}

	public void setAlpha(float a) {
		alpha = a;
	}

	public float getAlpha() {
		return alpha;
	}

}
