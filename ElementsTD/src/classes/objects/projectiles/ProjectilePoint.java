package classes.objects.projectiles;

import java.awt.Point;
import java.util.LinkedList;

import classes.main.Auxi;
import classes.main.Data;
import classes.main.Elemento;
import classes.objects.Tower;
import classes.objects.enemies.Enemy;
import classes.picture.splashes.SplashParticle;

public class ProjectilePoint extends ProjectileParent {

	private Point tar;

	public ProjectilePoint(Data data, double x, double y, Tower father,
			double damage, Elemento elemento, double speed, Point tar) {
		super(data, x, y, father, damage, speed, elemento);
		this.tar = tar;
	}

	@Override
	public void step() {

		setDirection(Auxi.point_direction(getX(), getY(), tar.x, tar.y));

		addX(Math.cos(getDirection()) * getSpeed());
		addY(-Math.sin(getDirection()) * getSpeed());

		if (Auxi.point_distance(getX(), getY(), tar.x, tar.y) <= getSpeed()) {
			destroy();
		}
	}

	@Override
	public void destroy() {
		LinkedList<Enemy> tempList = getData().getEnemyListClone();
		for (Enemy e : tempList) {
			if (Auxi.collides(this, e)) {
				e.hurt(getFather(), getDamage(), getElement(), true,
						getSlowDuration(), getSlowAmmount(), getBurnDuration(),
						getBurnDamage());
				switch (getElement()) {
				case RAIN:
					for (int i = 0; i < 5; i++) {
						getData().addDrawableObject(
								new SplashParticle(getData(), getX(), getY(),
										"projectiles/water", Math.random()
												* Math.PI * 2, Math.random(),
										Math.random(), 20, 0.1));
					}
					break;

				default:
					break;
				}
			}
		}
		remove();
	}

}
