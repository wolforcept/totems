package classes.objects.projectiles;

import java.util.LinkedList;

import classes.main.Auxi;
import classes.main.Data;
import classes.main.Elemento;
import classes.objects.Tower;
import classes.objects.enemies.EnemyParent;

public class ProjectileLink extends ProjectileParent {

	private double speed, dir, distance;
	private boolean showSplash;

	public ProjectileLink(Data data, double x, double y, Tower father,
			EnemyParent tar, double speed, double damage, Elemento elemento,
			boolean showSplash, double aDistance) {
		super(data, x, y, father, damage, elemento);
		this.speed = speed;
		this.showSplash = showSplash;
		dir = Auxi.point_direction(x, y, tar.getX(), tar.getY());
		distance = aDistance;
	}

	@Override
	public void step() {

		LinkedList<EnemyParent> tempList = getData().getEnemyListClone();
		for (EnemyParent enemy : tempList) {
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
