package classes.objects.enemies;

import java.awt.Color;
import java.awt.Point;

import classes.main.Auxi;
import classes.main.Data;
import classes.main.Elemento;
import classes.objects.DrawableObject;
import classes.objects.Tower;
import classes.picture.splashes.SplashParticle;
import classes.picture.splashes.SplashText;

public class Enemy extends DrawableObject {

	private Path path;
	private double[] resists = { 0, 0, 0, 0 };
	private Tower lastHit;
	private int xpReward, reward, burningDuration, reducedSpeedDuration;
	// private Elemento burningElement; // TODO
	private double health, maxHealth, burningDamage, speed, reducedSpeed;
	private boolean dead;
	private double shield, maxShield;
	private Point tar;
	private EnemyType type;

	public Enemy(Data data, double health, Path path, int reward,
			int expReward, EnemyType aType) {
		super(data, path.getStart().x, path.getStart().y);

		this.type = aType;
		this.health = maxHealth = health * type.health;
		this.path = path;
		this.reward = reward;
		this.xpReward = type.xp * xpReward;
		lastHit = null;
		burningDuration = 0;
		dead = false;
		speed = type.speed;
		shield = maxShield = 0;
		if (Math.random() < 0.2)
			shield = maxShield = health / 2;
		setImages("enemies/" + type.toString().toLowerCase());
		tar = path.nextPoint();

		if (type.wind > 0) {
			addResistance(Elemento.WIND, type.wind);
		}
		if (type.fire > 0) {
			addResistance(Elemento.FIRE, type.fire);
		}
		if (type.water > 0) {
			addResistance(Elemento.WATER, type.water);
		}
		if (type.earth > 0) {
			addResistance(Elemento.EARTH, type.earth);
		}
	}

	public void addResistance(Elemento e, double resistAmount) {
		resists[e.getId() - 1] = resistAmount;
	}

	public double getHealth() {
		return health;
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public double getResist(int element) {
		return resists[element];
	}

	public Path getPath() {
		return path;
	}

	public void step() {

		if (health <= 0) {
			death();
		}

		if (burningDuration > 0) {
			for (int i = 0; i < 3; i++) {
				getData().addDrawableObject(new SplashParticle(//
						getData(),//
						getX() + 0.8 * getWidth() * (Math.random() - .5),//
						getY() + 0.8 * getWidth() * (Math.random() - .5),//
						"particles/fire",//
						Math.PI * Math.random(),//
						Math.random() - .5, Math.random() - .5, 10, 0.2));//
			}
			hurt(burningDamage);
			burningDuration--;
		}
		if (reducedSpeedDuration > 0) {
			reducedSpeedDuration--;
		}
		moveToNextPointInPath(getSpeed());
		nextImage();

	}

	private void moveToNextPointInPath(double speed) {
		if (tar == null) {
			endPath();
			if (type.equals(EnemyType.PATHMAKER)) {
				getData().setCreatingPath(false);
			}
		} else {
			double dir = Auxi.point_direction(getX(), getY(), tar.x, tar.y);
			if (type.equals(EnemyType.PATHMAKER)) {
				setAngle(dir);
				double xx = getX() - 20 * Math.cos(dir) + Math.random() * 10
						- 5;
				double yy = getY() + 20 * Math.sin(dir) + Math.random() * 10
						- 5;
				getData().addDrawableObject(
						new SplashParticle(getData(), xx, yy, "particles/fire",
								Math.random() * Math.PI * 2,
								Math.random() * 4 - 2, Math.random() * 4 - 2,
								20, 0.1));
			}
			if (Auxi.point_distance(getX(), getY(), tar.x, tar.y) <= speed) {

				setX(tar.x);
				setY(tar.y);
				tar = getPath().nextPoint();

			} else {

				addX(speed * Math.cos(dir));

				addY(-speed * Math.sin(dir));

			}
		}
	}

	public void endPath() {
		// TODO enemy end
		remove();
	}

	private void death() {
		if (dead == false) {
			dead = true;
			for (int i = 0; i < 20; i++) {
				getData().addDrawableObject(
						new SplashParticle(getData(), getX(), getY(),
								"particles/fire", 0, Math.random() * 2 - 1,
								Math.random() * 2 - 1, 25 + Math.random() * 25,
								0.1));
			}
			getData().addShards(reward);
			getData().addDrawableObject(
					new SplashText(getData(), (int) getX(), (int) getY(), "+"
							+ reward + " Shards", true, Color.GREEN));
			if (lastHit != null) {
				lastHit.addExp(xpReward);
			}
			System.out.println("Death of " + getClass().getSimpleName()
					+ " by " + lastHit.getElement() + " for " + reward
					+ " shards");
			remove();
		}
		getData().sendWaveIfAuto();

	}

	private void hurt(double d) {
		if (shield > 0) {
			shield -= d;
			if (shield < 0) {
				health += shield;
			}
		} else {
			health -= d;
		}

		if (health <= 0) {
			death();
		}
	}

	public void hurt(Tower tower, double damage, Elemento type,
			boolean showSplash) {

		lastHit = tower;

		int[] elementQuantity = { 0, 0, 0, 0 };
		int totalElementQuantity = 3;

		for (int i = 0; i <= 2; i++) {
			char a = type.elementCode.charAt(i);
			switch (a) {

			case 'A': // WIND
				elementQuantity[0] += 1;
				break;

			case 'F': // FIRE
				elementQuantity[1] += 1;
				break;

			case 'W': // WATER
				elementQuantity[2] += 1;
				break;

			case 'E': // EARTH
				elementQuantity[3] += 1;
				break;

			case '0':
				totalElementQuantity -= 1;
				break;

			default:
				System.err.print("ELEMENT FAIL AT ");
				System.out.println("Enemy.hurt()");
				System.exit(0);
				break;
			}
		}

		double dmg[] = new double[4];

		for (int i = 0; i <= 3; i++) {
			dmg[i] = elementQuantity[i] * damage / totalElementQuantity;
			dmg[i] -= dmg[i] * resists[i] / 100;
		}

		hurt(dmg[0] + dmg[1] + dmg[2] + dmg[3]);

		if (showSplash) {
			getData().addDrawableObject(
					new SplashText(getData(), getX(), getY(), ""
							+ (int) (dmg[0] + dmg[1] + dmg[2] + dmg[3]), false,
							Data.COLOR_HIT_NORMAL));
		}
	}

	public void setOnFire(int duration, double damage) {
		burningDuration = duration;
		burningDamage = damage;
	}

	/**
	 * 
	 * @param slowDuration
	 * @param ammount
	 *            in %
	 */
	public void reduceSpeed(int slowDuration, double ammount) {
		reducedSpeedDuration = slowDuration;
		reducedSpeed = speed * ammount;
	}

	public double getSpeed() {
		if (reducedSpeedDuration > 0) {
			return speed - reducedSpeed;
		} else {
			return speed;
		}
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getBurnDuration() {
		return burningDuration;
	}

	public double getShield() {
		return shield;
	}

	public double getMaxShield() {
		return maxShield;
	}

	public void setInitialShield(double shield) {
		this.shield = this.maxShield = shield;
	}

	public EnemyType getType() {
		return type;
	}

}
