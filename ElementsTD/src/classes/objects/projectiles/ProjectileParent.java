package classes.objects.projectiles;

import classes.main.Data;
import classes.main.Elemento;
import classes.objects.DrawableObject;
import classes.objects.Tower;
import classes.picture.splashes.SplashParticle;

public abstract class ProjectileParent extends DrawableObject {

	private double direction, damage;
	Elemento element;
	private Tower father;
	private double slowAmmount, burnDamage, bounceRange;
	int burnDuration, slowDuration;

	private boolean slower, burner, bouncer;
	private int bounces, maxBounces;

	/**
	 * @param x
	 * @param y
	 * @param speed
	 * @param elemento
	 */
	public ProjectileParent(Data data, double x, double y, Tower father,
			double damage, Elemento elemento) {

		super(data, x, y);
		setImages("projectiles/" + elemento.toString().toLowerCase());
		this.father = father;
		this.damage = damage;
		this.element = elemento;
		slower = false;
		burner = false;
		bouncer = false;
	}

	public void parentStep() {
		getData().addDrawableObject(
				new SplashParticle(getData(), getX(), getY(), getImageName(), Math.PI
						* Math.random(), 0, 0, 20, 0.5));
	}

	public abstract void step();

	public abstract void destroy();

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public double getDirection() {
		return direction;
	}

	public double getDamage() {
		return damage;
	}

	public Elemento getElement() {
		return element;
	}

	public Tower getFather() {
		return father;
	}

	public double getSlowAmmount() {
		return slowAmmount;
	}

	public int getSlowDuration() {
		return slowDuration;
	}

	public boolean isSlower() {
		return slower;
	}

	public boolean isBurner() {
		return burner;
	}

	public boolean isBouncer() {
		return bouncer;
	}

	public double getBurnDamage() {
		return burnDamage;
	}

	public int getBurnDuration() {
		return burnDuration;
	}

	public int getBounces() {
		return bounces;
	}

	public int getMaxBounces() {
		return maxBounces;
	}

	public void incrementBounces() {
		bounces++;
	}

	public double getBounceRange() {
		return bounceRange;
	}

	public void setBurn(double aDamage, int aDuration) {
		burner = true;
		burnDamage = aDamage;
		burnDuration = aDuration;
	}

	public void setSlow(double aAmmount, int aDuration) {
		slower = true;
		slowAmmount = aAmmount;
		slowDuration = aDuration;
	}

	public void setBounce(int aBounceNumber, double aBounceRange) {
		bouncer = true;
		bounces = 0;
		maxBounces = aBounceNumber;
		bounceRange = aBounceRange;
	}

}
