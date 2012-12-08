package classes.objects.projectiles;

import java.util.LinkedList;

import classes.main.Auxi;
import classes.main.Data;
import classes.main.Elemento;
import classes.objects.Tower;
import classes.objects.enemies.EnemyParent;
import classes.picture.splashes.SplashParticle;

public class ProjectilePierce extends ProjectileParent {

	private double speed, dir, distance;
	private boolean showSplash;
	private LinkedList<Long> dealtDamage;

	public ProjectilePierce(Data data, double x, double y, Tower father,
			EnemyParent tar, double speed, double damage, Elemento elemento,
			boolean showSplash, double aDistance, double missDistance) {
		super(data, x, y, father, damage, elemento);
		this.speed = speed;
		this.showSplash = showSplash;
		dir = (Math.random() * missDistance) - (missDistance / 2)
				+ Auxi.point_direction(x, y, tar.getX(), tar.getY());
		distance = aDistance + (Math.random() * aDistance * 0.5);
		dealtDamage = new LinkedList<Long>();
	}

	@Override
	public void step() {

		LinkedList<EnemyParent> tempList = getData().getEnemyListClone();
		for (EnemyParent enemy : tempList) {
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
										Math.random() * 4 - 2, 5,0.5));
					break;

				default:
					break;
				}
			}
		}
		if (distance <= 0) {
			destroy();
		} else {
			distance--;
			setDirection(dir);
			setAngle(getDirection());
			addX(speed * Math.cos(getDirection()));
			addY(-speed * Math.sin(getDirection()));
		}
	}

	@Override
	public void destroy() {
		remove();
	}

	public double getSpeed() {
		return speed;
	}
}
