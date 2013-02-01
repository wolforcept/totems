package classes.objects.projectiles;

import classes.main.Auxi;
import classes.main.Data;
import classes.main.Elemento;
import classes.objects.Tower;
import classes.objects.enemies.Enemy;

public class ProjectileBlast extends ProjectileParent {

	private float counter, maxCount;
	private double radius;
	private boolean showSplash;
	private double growSpeed;

	public ProjectileBlast(Data data, double x, double y, Tower father,
			double damage, Elemento elemento, double aRadius,
			boolean aShowSplash) {
		super(data, x, y, father, damage, elemento);
		counter = 0;
		radius = aRadius;
		maxCount = (float) (radius / (getAnimation().getWidth() / 2));
		showSplash = aShowSplash;
		switch (element) {
		default:
			growSpeed = 1;
			break;
		}
	}

	public ProjectileBlast(Data data, double x, double y, Tower father,
			double damage, Elemento element, double radius, boolean showSplash,
			String a) {
		this(data, x, y, father, damage, element, radius, showSplash);
		setImages(a);
	}

	@Override
	public void step() {
		counter += .01 * growSpeed;
		if (counter >= maxCount) {
			for (Enemy e : getData().getEnemyListClone()) {
				if (radius > Auxi.point_distance(e.getX(), e.getY(), getX(),
						getY())) {
					e.hurt(getFather(), getDamage(), getElement(), showSplash);
					if (isSlower()) {
						e.reduceSpeed(getSlowDuration(), getSlowAmmount());
					}
					if (isBurner()) {
						e.setOnFire(getBurnDuration(), getBurnDamage());
					}
				}
			}
			remove();
		}
	}

	@Override
	public void destroy() {
		remove();
	}

	public double getCounter() {
		return counter;
	}

}
