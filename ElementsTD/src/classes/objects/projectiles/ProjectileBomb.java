package classes.objects.projectiles;

import java.util.Collections;
import java.util.LinkedList;

import classes.main.Auxi;
import classes.main.Data;
import classes.main.Elemento;
import classes.objects.Tower;
import classes.objects.enemies.Enemy;
import classes.picture.splashes.SplashParticle;

public class ProjectileBomb extends ProjectileParent {

	private Enemy tar;
	private boolean showSplash;

	public ProjectileBomb(Data data, double x, double y, Tower father,
			Enemy tar, double speed, double damage, Elemento elemento,
			boolean showSplash) {
		super(data, x, y, father, damage, speed, elemento);
		this.tar = tar;
		this.showSplash = showSplash;
	}

	@Override
	public void step() {

		if (tar == null) {
			remove();
		} else if (Auxi.point_distance(getX(), getY(), tar.getX(), tar.getY()) <= getSpeed()) {

			tar.hurt(getFather(), getDamage(), getElement(), showSplash);

			if (isSlower()) {
				tar.reduceSpeed(getSlowDuration(), getSlowAmmount());
			}
			if (isBurner()) {
				tar.setOnFire(getBurnDuration(), getBurnDamage());
			}

			// PARTICLES:
			switch (getElement()) {
			case BLAZE:
				for (int i = 0; i < 10; i++) {
					getData().addDrawableObject(
							new SplashParticle(getData(), getX(), getY(),
									"projectiles/fire",
									Math.random() * Math.PI,
									Math.random() * 4 - 2,
									Math.random() * 4 - 2, 20, 0.1));
				}
				break;
			default:
				break;
			}

			incrementBounces();
			if (getBounces() > getMaxBounces()) {
				destroy();
			} else {
				LinkedList<Enemy> list = getData().getEnemyListClone();

				list.remove(tar);

				Collections.shuffle(list);

				boolean hasNewTar = false;
				for (Enemy e : list) {
					// if (tar.getId() != e.getId()) {
					if (getBounceRange() > Auxi.point_distance(getX(), getY(),
							e.getX(), e.getY())) {
						tar = e;
						hasNewTar = true;
					}
					// }
				}

				if (!hasNewTar) {
					tar = null;
				}
			}
		} else {
			if (tar.isRemoved()) {
				remove();
			} else {
				setDirection(Auxi.point_direction(getX(), getY(), tar.getX(),
						tar.getY()));
				setAngle(getDirection());
				addX(getSpeed() * Math.cos(getDirection()));
				addY(-getSpeed() * Math.sin(getDirection()));
			}
		}
	}

	@Override
	public void destroy() {
		remove();
	}

	public Enemy getTar() {
		return tar;
	}

}