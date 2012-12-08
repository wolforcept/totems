package classes.objects.projectiles;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import classes.main.Auxi;
import classes.main.Data;
import classes.main.Elemento;
import classes.objects.Tower;
import classes.objects.enemies.EnemyParent;
import classes.picture.splashes.SplashParticle;

public class ProjectileSimple extends ProjectileParent {

	private double speed;
	private EnemyParent tar;
	private boolean showSplash;

	public ProjectileSimple(Data data, double x, double y, Tower father,
			EnemyParent tar, double speed, double damage, Elemento elemento,
			boolean showSplash) {
		super(data, x, y, father, damage, elemento);
		this.speed = speed;
		this.tar = tar;
		this.showSplash = showSplash;
	}

	@Override
	public void step() {

		if (tar == null) {
			remove();
		} else if (Auxi.point_distance(getX(), getY(), tar.getX(), tar.getY()) <= speed) {

			tar.hurt(getFather(), getDamage(), getElement(), showSplash);

			if (isSlower()) {
				tar.reduceSpeed(getSlowDuration(), getSlowAmmount());
			}
			if (isBurner()) {
				tar.setOnFire(getBurnDuration(), getBurnDamage());
			}

			// PARTICLES:
			switch (getElement()) {
			case WATER:
				getData().addDrawableObject(
						new SplashParticle(getData(), getX(), getY(),
								"projectiles/water", Math.random() * Math.PI,
								Math.random() * 4 - 2, Math.random() * 4 - 2,
								20, 0.1));
				break;
			// case BREEZE:
			// double dirr = Auxi.point_direction(tar.getX(),
			// tar.getY(),getFather().getX(),
			// getFather().getY());
			// tar.addX(20 * Math.cos(Math.toRadians(dirr)));
			// tar.addY(20 * Math.sin(Math.toRadians(dirr)));
			// break;
			default:
				break;
			}

			incrementBounces();
			if (getBounces() > getMaxBounces()) {
				destroy();
			} else {

				LinkedList<EnemyParent> list =	getData().getEnemyListClone();

				Collections.shuffle(list);

				Iterator<EnemyParent> it = list.iterator();
				boolean hasNewTar = false;
				while (it.hasNext() && !hasNewTar) {
					EnemyParent e = it.next();
					if (tar.getId() != e.getId()) {
						if (getBounceRange() > Auxi.point_distance(getX(),
								getY(), e.getX(), e.getY())) {
							tar = e;
							hasNewTar = true;
						}
					}
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
				addX(speed * Math.cos(getDirection()));
				addY(-speed * Math.sin(getDirection()));
			}
		}
	}

	@Override
	public void destroy() {
		remove();
	}

	public EnemyParent getTar() {
		return tar;
	}

	public double getSpeed() {
		return speed;
	}
}
