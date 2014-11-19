package classes.objects.projectiles;

import java.util.LinkedList;

import classes.main.Auxi;
import classes.main.Data;
import classes.main.Elemento;
import classes.objects.Tower;
import classes.objects.enemies.Enemy;
import classes.picture.splashes.SplashParticle;

public class ProjectilePierce extends ProjectileParent implements
		ProjectileNeedSplashes {

	private double distance;
	private boolean showSplash;
	private LinkedList<Long> dealtDamage;

	public ProjectilePierce(Data data, double x, double y, Tower father,
			Enemy tar, double speed, double damage, Elemento elemento,
			boolean showSplash, double aDistance, double missDistance,
			double outterDistance) {
		super(data, x, y, father, damage, speed, elemento);
		this.showSplash = showSplash;
		double dir = (Math.random() * missDistance) - (missDistance / 2)
				+ Auxi.point_direction(x, y, tar.getX(), tar.getY());
		distance = aDistance + (Math.random() * aDistance * outterDistance);
		dealtDamage = new LinkedList<Long>();
		setDirection(dir);
		setAngle(getDirection());
	}

	@Override
	public void step() {

		LinkedList<Enemy> tempList = getData().getEnemyListClone();
		for (Enemy enemy : tempList) {
			if (Auxi.collides(this, enemy)) {
				if (!dealtDamage.contains(enemy.getId())) {
					enemy.hurt(getFather(), getDamage(), getElement(),
							showSplash);
					dealtDamage.add(enemy.getId());
				}
				// PARTICLES:
				switch (getElement()) {
				case ZENITHTIDE:
					if (Math.random() > 0.5)
						getData().addDrawableObject(
								new SplashParticle(getData(), getX(), getY(),
										"projectiles/water", Math.random()
												* Math.PI,
										Math.random() * 4 - 2,
										Math.random() * 4 - 2, 5, 0.5));
					break;

				default:
					break;
				}
			}
		}
		if (distance <= 0) {
			destroy();
		} else {
			distance -= getSpeed();

			addX(getSpeed() * Math.cos(getDirection()));
			addY(-getSpeed() * Math.sin(getDirection()));
		}
	}

	@Override
	public void destroy() {
		remove();
	}

}
