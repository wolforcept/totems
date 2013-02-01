package classes.objects.projectiles;

import java.util.LinkedList;

import classes.main.Auxi;
import classes.main.Data;
import classes.main.Elemento;
import classes.objects.Tower;
import classes.objects.enemies.Enemy;
import classes.picture.splashes.SplashParticle;

public class ProjectileAura extends ProjectileParent {

	double speed = 0.1;
	private double timer, maxTimer;
	private double centrex, centrey;

	public ProjectileAura(Data data, double x, double y, Tower father,
			double damage, Elemento elemento) {
		super(data, x, y, father, damage, elemento);
		maxTimer = father.getRange() * (0.9 + 0.1 * Math.random());
		timer = father.getRange() * 2 / 5;
		centrex = getX();
		centrey = getY();
	}

	@Override
	public void step() {
		timer += speed;

		double ag = Auxi.point_direction(getX1(), getY1(), centrex, centrey);
		double hs = 5*Math.cos(ag);
		double vs = -5*Math.sin(ag);
		getData().addDrawableObject(
				new SplashParticle(getData(), getX(), getY(), "particles/fire",
						Math.PI * 2 * Math.random(), hs, vs, timer/20, 0.1));

		setX(centrex + timer * Math.cos(timer));
		setY(centrey + timer * Math.sin(timer));

		LinkedList<Enemy> tempList = getData().getEnemyListClone();
		for (Enemy e : tempList) {
			if (Auxi.collides(e, this)) {
				e.hurt(getFather(), getDamage(), getElement(), false);
			}
		}
		if (timer >= maxTimer) {
			destroy();
		}
	}

	@Override
	public void destroy() {
		remove();
	}

}
