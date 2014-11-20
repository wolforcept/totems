package classes.objects.projectiles;

import java.util.LinkedList;

import classes.main.Auxi;
import classes.main.Data;
import classes.main.Elemento;
import classes.objects.Tower;
import classes.objects.enemies.Enemy;
import classes.picture.splashes.SplashParticle;

public class ProjectileMine extends ProjectileParent implements
		ProjectileNeedSplashes {

	private double tarx, tary;
	private boolean showSplash;
	private boolean planted;

	public ProjectileMine(Data data, double x, double y, Tower father,
			double tarx, double tary, int duration, double speed,
			double damage, Elemento elemento, boolean showSplash) {
		super(data, x, y, father, damage, speed, elemento);
		this.tarx = tarx;
		this.tary = tary;
		this.showSplash = showSplash;
	}

	@Override
	public void step() {

		if (planted) {

			LinkedList<Enemy> tempList = getData().getEnemyListClone();
			for (Enemy e : tempList) {

				if (Auxi.collides(this, e)) {
					collide(e);
				}

			}

		} else if (Auxi.point_distance(getX(), getY(), tarx, tary) <= getSpeed()) {

			planted = true;

			// PARTICLES:
			switch (getElement()) {
			case BLAZE:
				for (int i = 0; i < 10; i++) {
					getData().addDrawableObject(
							new SplashParticle(getData(), getX(), getY(),
									"particles/fire", Math.random() * Math.PI,
									Math.random() * 4 - 2,
									Math.random() * 4 - 2, 20, 0.1));
				}
				break;
			default:
				break;
			}

		} else {
			moveToTar();
		}
	}

	private void moveToTar() {

		double dir = Auxi.point_direction(getX(), getY(), tarx, tary);

		double xx = getX() + Math.cos(dir) * getSpeed();
		double yy = getY() - Math.sin(dir) * getSpeed();

		setX(xx);
		setY(yy);

	}

	private void collide(Enemy tar) {
		tar.hurt(getFather(), getDamage(), getElement(), showSplash,
				getSlowDuration(), getSlowAmmount(), getBurnDuration(),
				getBurnDamage());

		remove();
	}

	@Override
	public void destroy() {
		remove();
	}

}