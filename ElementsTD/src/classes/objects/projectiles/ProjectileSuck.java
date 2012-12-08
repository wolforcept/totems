package classes.objects.projectiles;

import classes.main.Auxi;
import classes.main.Data;
import classes.main.Elemento;
import classes.objects.Tower;
import classes.objects.enemies.EnemyParent;

public class ProjectileSuck extends ProjectileParent {

	private double speed, dir;

	public ProjectileSuck(Data data, EnemyParent tar, Tower father,
			Tower tower, double speed, double damage, Elemento elemento) {
		super(data, tar.getX(), tar.getY(), father, damage, elemento);
		this.speed = speed;
		addX(tar.getWidth() * 0.25 - 0.5 * Math.random() * tar.getWidth());
		addY(tar.getHeight() * 0.25 - 0.5 * Math.random() * tar.getHeight());
		dir = Auxi.point_direction(getX(), getY(), tower.getX(), tower.getY());
		tar.hurt(getFather(), getDamage(), getElement(), false);
	}

	@Override
	public void step() {

		double distance = Auxi.point_distance(getX(), getY(), getFather()
				.getX(), getFather().getY());
		if (distance <= getFather().getWidth() / 3) {
			remove();
		} else {
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
