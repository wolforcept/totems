package classes.objects;

import java.awt.Point;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

import classes.main.Auxi;
import classes.main.Data;
import classes.main.Elemento;
import classes.objects.Aura.AuraType;
import classes.objects.enemies.Enemy;
import classes.objects.enemies.EnemyType;
import classes.objects.projectiles.ProjectileAura;
import classes.objects.projectiles.ProjectileBlast;
import classes.objects.projectiles.ProjectileBomb;
import classes.objects.projectiles.ProjectileMine;
import classes.objects.projectiles.ProjectileParent;
import classes.objects.projectiles.ProjectilePierce;
import classes.objects.projectiles.ProjectilePoint;
import classes.objects.projectiles.ProjectileSimple;
import classes.objects.projectiles.ProjectileSuck;
import classes.picture.splashes.SplashParticle;
import classes.picture.splashes.SplashText;

public class Tower extends DrawableObject {

	private static final int BIOMASS_CHARGEUSE = 4, PLASMA_CHARGEUSE = 5;

	public TowerStats stats;

	private Elemento element;

	private boolean mouseOnMe, targetLockOn, showSplash;

	private Enemy target;
	private TargetType targetType;

	private HashMap<AuraType, Double> auras;
	private HashMap<Long, Point> links;

	public enum TargetType {
		/* AVALIABLE */HEALTHY, UNHEALTHY, FAST, SLOW, RANDOM, //
		/* NOT AVALIABLE */BURN
	}

	public Tower(Data data, int x, int y, Elemento e) {
		super(data, x, y);

		mouseOnMe = false;
		auras = new HashMap<AuraType, Double>();
		links = new HashMap<Long, Point>();
		setStats(e);
		System.out.println("Created tower with id: " + getId());
	}

	private void setStats(Elemento e) {
		element = e;
		targetType = e.targetType;
		targetLockOn = e.targetLockOn;
		showSplash = e.showSplash;

		stats = new TowerStats()//
				.setRange(e.range) //
				.setReload(0) //
				.setMaxReload(e.maxReload) //
				.setProjectileSpeed(e.projectileSpeed) //
				.setDamage(e.damage) //
				.setBurnDamage(e.burnDamage) //
				.setBurnDuration(e.burnDuration) //
				.setSlowAmmount(e.slowAmmount) //
				.setSlowDuration(e.slowDuration) //
				.setBounceNumber(e.bounceNumber) //
				.setBounceRange(e.bounceRange) //
				.setRedo(e.redo);

		Aura.setAuras(auras, e);

		setImages(e.toString().toLowerCase());
		getData().updateBuffs();
		updateBuffs(getData().getTowerListClone());
		new Thread() {
			public void run() {
				System.gc();
			}
		};
	}

	public void step() {

		nextImage();
		stats.decrementReload();
		if (getElement() != Elemento.LIFE) {

			for (int i = 0; i < stats.getRedo(); i++) {
				fire();
			}
			if (classes.main.Control.mouseIsOnTop(getData(), this)) {
				getData().setDrawOnMouse(true);
				setMouseOnMe(true);
				getData().setStringToDraw(getStringToDrawWhenMouseOver());
			} else {
				setMouseOnMe(false);
			}

		}
	}

	private void fire() {
		Enemy firingAt = null;

		if (targetLockOn && getTarget() != null && !getTarget().isRemoved()) {
			if (getElement().equals(Elemento.ENDSTORM)) {
				firingAt = getTarget();
			} else {
				if (stats.getRange() >= Auxi.point_distance(getTarget().getX(),
						getTarget().getY(), getX(), getY())) {
					firingAt = getTarget();
				} else {
					LinkedList<Enemy> list = getData().getEnemyListClone();
					firingAt = pickTarget(list);
				}
			}
		} else {
			LinkedList<Enemy> list = getData().getEnemyListClone();
			firingAt = pickTarget(list);
		}

		if (stats.getReload() <= 0) {

			if (getElement() == Elemento.TROPICALEQUINOX) {

				if (getData().getEnemyListClone().isEmpty()) {
					stats.setReload(0);
				} else {
					LinkedList<PathMark> list = getData()
							.getPathMarkListClone();
					double xx = -100, yy = -1;
					for (PathMark pathMark : list) {

						double oldDistance = Auxi.point_distance(getX(),
								getY(), xx, yy);
						double newDistance = Auxi.point_distance(getX(),
								getY(), pathMark.getX(), pathMark.getY());

						if (xx == -100 || oldDistance > newDistance) {
							boolean collides = false;
							LinkedList<ProjectileParent> plist = getData()
									.getProjectileListClone();
							for (ProjectileParent p : plist) {
								if (Auxi.collides(pathMark, p)) {
									collides = true;
								}
							}
							if (!collides) {
								if (stats.getRange() > Auxi.point_distance(
										pathMark.getX(), pathMark.getY(),
										getX(), getY())
										&& pathMark.getX() > 0
										&& pathMark.getX() < Data.WINDOW_SIZE.width
										&& pathMark.getY() > 0
										&& pathMark.getY() < Data.WINDOW_SIZE.height) {
									xx = pathMark.getX();
									yy = pathMark.getY();
								}
							}
						}
					}

					if (xx != -100) {
						getData().addDrawableObject(//
								new ProjectileMine(getData(), getX(), getY(),
										this, xx, yy, 1000, 3, 50,
										getElement(), showSplash));
						stats.setReload(stats.getMaxReload());
					}
				}
			} else if (firingAt != null) {
				ProjectileParent p = fireAt(firingAt);
				if (p != null) {
					getData().addDrawableObject(p);
				}
				stats.setReload(stats.getMaxReload());
			}
		}
	}

	private Enemy pickTarget(LinkedList<Enemy> list) {
		switch (targetType) {
		case RANDOM:
			Collections.shuffle(list);
			break;
		case FAST:
			Collections.sort(list, new Comparator<Enemy>() {
				@Override
				public int compare(Enemy e1, Enemy e2) {
					return (int) (Math.signum(e2.getSpeed() - e1.getSpeed()));
				}
			});
			break;

		case SLOW:
			Collections.sort(list, new Comparator<Enemy>() {
				@Override
				public int compare(Enemy e1, Enemy e2) {
					return -(int) (Math.signum(e2.getSpeed() - e1.getSpeed()));
				}
			});
			break;

		case HEALTHY:
			Collections.sort(list, new Comparator<Enemy>() {
				@Override
				public int compare(Enemy e1, Enemy e2) {
					return (int) (Math.signum(e2.getHealth() - e1.getHealth()));
				}
			});
			break;
		case UNHEALTHY:
			Collections.sort(list, new Comparator<Enemy>() {
				@Override
				public int compare(Enemy e1, Enemy e2) {
					return (int) (Math.signum(e1.getHealth() - e2.getHealth()));
				}
			});
			break;
		case BURN:
			Collections.sort(list, new Comparator<Enemy>() {
				@Override
				public int compare(Enemy e1, Enemy e2) {
					return (int) (Math.signum(e1.getBurnDuration()
							- e2.getBurnDuration()));
				}
			});
			break;
		}

		for (Enemy e : list) {
			if (!(e.getType().equals(EnemyType.PATHMAKER))) {
				if (getElement().equals(Elemento.ENDSTORM)) {
					return e;
				}
				if (stats.getRange() >= Auxi.point_distance(getX(), getY(),
						e.getX(), e.getY())) {
					return e;
				}
			}
		}
		return null;
	}

	public ProjectileParent fireAt(final Enemy tar) {

		this.target = tar;

		ProjectileParent ret = new ProjectileSimple(getData(), getX(), getY(),
				this, target, stats.getProjectileSpeed(), stats.getDamage(),
				getElement(), showSplash);

		switch (getElement()) {
		case LIFE:
		case MUD:
			ret = null;
			break;

		case EARTH:
		case FIRESTORM:
			ret = new ProjectileBlast(getData(), getX(), getY(), this,
					stats.getDamage(), getElement(), stats.getRange(),
					showSplash);
			break;

		case ZENITHTIDE:
			ret = new ProjectilePierce(getData(), getX(), getY(), this, target,
					stats.getProjectileSpeed(), stats.getDamage(), element,
					false, stats.getRange(), 0.5, 0.5);
			ret.setAlpha((float) (Math.random()));
			break;

		case ENDSTORM:
			ret = new ProjectileAura(getData(), getX(), getY(), this,
					stats.getDamage(), getElement());
			break;

		case BIOMASS:
			if (stats.chargesAvaliable(BIOMASS_CHARGEUSE)) {
				ret = new ProjectilePierce(getData(), getX(), getY(), this,
						target, stats.getProjectileSpeed(),
						stats.getDamage() * 10, element, false,
						stats.getRange(), 0.5, 0);
				stats.useTokens(BIOMASS_CHARGEUSE);
			} else {
				stats.incrementCharge();
				ret = new ProjectileSuck(getData(), target, this, this,
						stats.getProjectileSpeed(), stats.getDamage(), element);
			}
			break;

		case PLASMA:
			if (stats.chargesAvaliable(PLASMA_CHARGEUSE)) {
				ret = new ProjectileBlast(getData(), getX(), getY(), this,
						stats.getDamage(), getElement(), stats.getRange(),
						showSplash, "projectiles/earth");
				stats.useTokens(PLASMA_CHARGEUSE);
			} else {
				stats.incrementCharge();
				ret = new ProjectileSuck(getData(), target, this, this,
						stats.getProjectileSpeed(), stats.getDamage(), element);
			}
			break;

		case RAIN:

			double angle = Math.random() * Math.PI * 2;
			double radius = Math.random() * stats.getRange() + Math.random()
					* stats.getRange() / 2;
			double xxx = Math.cos(angle) * radius;
			double yyy = Math.sin(angle) * radius;

			ret = new ProjectilePoint(getData(), getX() + xxx - 20, getY()
					+ yyy - 40, this, stats.getDamage(), getElement(), 5,
					new Point((int) (getX() + xxx), (int) (getY() + yyy)));
			break;

		case BLAZE:
		case INFERNO:
			ret = new ProjectileBomb(getData(), getX(), getY(), this, tar,
					stats.getProjectileSpeed(), stats.getDamage(),
					getElement(), showSplash);
			break;

		case TORRENT:
			ret = new ProjectilePierce(getData(), getX(), getY(), this, target,
					stats.getProjectileSpeed(), stats.getDamage(), element,
					true, stats.getRange(), 0.5, Math.random() * .5);
			ret.setAlpha((float) Math.random());
			ret.addX(Math.random() * ret.getWidth() - ret.getWidth() / 2);
			ret.addY(Math.random() * ret.getHeight() - ret.getHeight() / 2);
			ret.setSpeed(Math.random() * 5 + 5);
			break;

		case WATER:
			ret.setAlpha((float) Math.random());
			break;
		default:
		}

		if (stats.getBounceNumber() != 0 && stats.getBounceRange() != 0) {
			ret.setBounce(stats.getBounceNumber(), stats.getBounceRange());
		}
		if (stats.getSlowAmmount() != 0 && stats.getSlowDuration() != 0) {
			ret.setSlow(stats.getSlowAmmount() / 100, stats.getSlowDuration());
		}
		if (stats.getBurnDuration() != 0 && stats.getBurnDamage() != 0) {
			ret.setBurn(stats.getBurnDamage(), stats.getBurnDuration());
		}
		return ret;

	}

	public String getStringToDrawWhenMouseOver() {
		String s1 = "    ";
		switch (stats.getGear()) {
		case 2:
			s1 = " II ";
			break;
		case 3:
			s1 = " III";
			break;
		}

		String s2 = "";
		if (Elemento.getUpgradedElement(getElement(), getData()
				.getSelectedElement()) != null) {
			s2 += "  Upgrading this with "
					+ getData().getSelectedElement()
					+ " costs "
					+ Elemento.getUpgradedElement(getElement(),
							getData().getSelectedElement()).getCost();

		}

		return element.name + s1 + "   Dmg:" + stats.getDamage() + "   Range: "
				+ stats.getRange() + "   Exp: " + stats.getExp() + s2;
	};

	public boolean mouseOn() {
		return mouseOnMe;
	}

	public void setMouseOnMe(boolean b) {
		mouseOnMe = b;
	}

	/*
	 * 
	 */

	public Elemento getElement() {
		return element;
	}

	public void upgrade(final Elemento new_ele) {

		if (new_ele == null)
			return;

		System.out.println("Upgrading from " + element + " with " + new_ele);

		if (new_ele == Elemento.LIFE) {
			if (element == Elemento.LIFE) {
				getData().showErrorMessage("LIFE TOTEM CAN NOT BE UPGRADED");
				return;
			}
			stats.nextGear();
			getData().addDrawableObject(
					new SplashText(getData(), getData().getMouse().x, getData()
							.getMouse().y, "Totem Gear Raised to "
							+ stats.getGear(), false, Data.COLOR_WHITE));
			getData().addShards(-Elemento.LIFE.getCost());
			return;

		}

		if (!element.elementCode.contains("0")) {
			getData().showErrorMessage("INVALID ELEMENT UPGRADE");
			return;
		}

		Elemento newElement = Elemento.getUpgradedElement(element, new_ele);

		if (newElement == null) {
			getData().showErrorMessage("INVALID ELEMENT UPGRADE");
			return;
		}

		if (newElement.getCost() > getData().getShards()) {
			getData().showErrorMessage("NOT ENOUGH SHARDS");
			return;
		}

		getData().addShards(-newElement.getCost());
		setStats(newElement);
		getData().addDrawableObject(
				new SplashText(getData(), getData().getMouse().x, getData()
						.getMouse().y, newElement.name + " created", false,
						Data.COLOR_WHITE));

	}

	public Enemy getTarget() {
		return target;
	}

	public void updateBuffs(final LinkedList<Tower> list) {
		stats.clearBuffs();
		links.clear();
		for (int i = 0; i < AuraType.values().length; i++) {
			AuraType type = AuraType.values()[i];
			stats.putBuff(type, new LinkedList<Aura>());
		}
		for (Tower tower : list) {
			if (tower.getId() != getId())
				for (int i = 0; i < AuraType.values().length; i++) {
					AuraType type = AuraType.values()[i];
					if (tower.getAura(type) != null) {
						if (Auxi.point_distance(tower.getX(), tower.getY(),
								getX(), getY()) <= Data.AURA_RADIUS) {
							stats.getBuff(type).add(
									new Aura(tower.getAura(type)));
							links.put(
									tower.getId(),
									new Point((int) tower.getX(), (int) tower
											.getY()));
						}
					}
				}
		}
		System.out.print("Buffs for " + getElement().name + ": ");
		for (int i = 0; i < AuraType.values().length; i++) {
			AuraType type = AuraType.values()[i];
			System.out.print(type + ":");
			if (stats.getBuff(type) != null)
				for (Aura a : stats.getBuff(type)) {
					System.out.print(a.getAmmount() + ", ");
				}
		}
		System.out.println(";");
	}

	private Double getAura(AuraType t) {
		return auras.get(t);
	}

	public void toggleLock() {
		if (targetLockOn) {
			targetLockOn = false;
		} else {
			targetLockOn = true;
		}
	}

	public boolean getLock() {
		return targetLockOn;
	}

	public TargetType getFocus() {
		return targetType;
	}

	public void cycleFocus() {
		if (targetType.ordinal() == 4) {
			targetType = TargetType.values()[0];
		} else {
			targetType = TargetType.values()[targetType.ordinal() + 1];
		}
	}

	public HashMap<Long, Point> getLinks() {
		return links;
	}

	public void sell() {
		if (element == Elemento.LIFE)
			return;
		int refund = (int) (Data.SELLING_REFUND * getElement().getCost());
		if (refund > 0) {
			getData().addShards(refund);
			getData().addDrawableObject(
					new SplashText(getData(), getX(), getY(), "+" + refund
							+ " Shards", true, Data.COLOR_SELL));
			for (int i = 0; i < 10; i++) {
				getData().addDrawableObject(
						new SplashParticle(getData(), getX(), getY(),
								"particles/fire", Math.random() * Math.PI, Math
										.random() * 2 - 1,
								Math.random() * 2 - 1, Math.random() * 20 + 20,
								0.2));
			}
		}
		remove();
	}

}
