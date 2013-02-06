package classes.objects.projectiles;

import java.util.LinkedList;

import classes.main.Auxi;
import classes.main.Data;
import classes.main.Elemento;
import classes.objects.Tower;
import classes.objects.enemies.Enemy;

public class ProjectileLink extends ProjectileParent {

	private double dir, distance;
	private boolean showSplash;

	public ProjectileLink(Data data, double x, double y, Tower father,
			Enemy tar, double speed, double damage, Elemento elemento,
			boolean showSplash, double aDistance) {
		super(data, x, y, father, damage, speed, elemento);
		this.showSplash = showSplash;
		dir = Auxi.point_direction(x, y, tar.getX(), tar.getY());
		distance = aDistance;
	}

	@Override
	public void step() {

		LinkedList<Enemy> tempList = getData().getEnemyListClone();
		for (Enemy enemy : tempList) {
			if (Auxi.collides(this, enemy)) {
				enemy.hurt(getFather(), getDamage(), getElement(), showSplash);
			}
		}
		if (distance <= 0) {
			remove();
		} else {
			distance--;
			setDirection(dir);
			setAngle(getDirection());
			addX(getSpeed() * Math.cos(getDirection()));
			addY(-getSpeed() * Math.sin(getDirection()));
		}
	}

	@Override
	public void destroy() {
		remove();
	}

}
