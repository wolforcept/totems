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
import classes.objects.enemies.EnemyParent;
import classes.objects.enemies.EnemyType;
import classes.objects.projectiles.ProjectileAura;
import classes.objects.projectiles.ProjectileBlast;
import classes.objects.projectiles.ProjectileBomb;
import classes.objects.projectiles.ProjectileParent;
import classes.objects.projectiles.ProjectilePierce;
import classes.objects.projectiles.ProjectilePoint;
import classes.objects.projectiles.ProjectileSimple;
import classes.objects.projectiles.ProjectileSuck;
import classes.picture.splashes.SplashParticle;
import classes.picture.splashes.SplashText;

public class Tower extends DrawableObject {

	private double range, reload, maxReload, projectileSpeed, damage,
			burnDamage, slowAmmount, bounceRange;
	private Elemento element;
	private boolean mouseOnMe, targetLockOn, showSplash;
	private int gear, exp, bounceNumber, burnDuration, slowDuration, redo;
	private EnemyParent target;
	private HashMap<AuraType, Double> auras;
	private TargetType targetType;
	private HashMap<AuraType, LinkedList<Aura>> buffs;
	private HashMap<Long, Point> links;
	private int charge, maxCharge, chargesAvaliable;

	public enum TargetType {
		/* AVALIABLE */HEALTHY, UNHEALTHY, FAST, SLOW, RANDOM, //
		/* NOT AVALIABLE */BURN
	}

	public Tower(Data data, int x, int y, Elemento e) {
		super(data, x, y);
		mouseOnMe = false;
		auras = new HashMap<AuraType, Double>();
		links = new HashMap<Long, Point>();
		getSpecs(e);
		gear = 1;
		exp = 0;
		System.out.println("Created tower with id: " + getId());
	}

	private void getSpecs(Elemento e) {
		gear = 1;
		range = e.range;
		maxReload = e.maxReload;
		projectileSpeed = e.projectileSpeed;
		damage = e.damage;
		redo = e.redo;
		targetType = e.targetType;
		targetLockOn = e.targetLockOn;
		element = e;
		reload = 0;

		burnDamage = e.burnDamage;
		burnDuration = e.burnDuration;
		slowAmmount = e.slowAmmount;
		slowDuration = e.slowDuration;
		bounceNumber = e.bounceNumber;
		bounceRange = e.bounceRange;
		showSplash = e.showSplash;
		charge = 0;
		maxCharge = 100;
		chargesAvaliable = 0;

		setImages(e.toString().toLowerCase());

		buffs = new HashMap<Aura.AuraType, LinkedList<Aura>>();

		auras.clear();
		switch (e) {
		case WIND:
			auras.put(AuraType.RANGE, 20.0);
			break;

		case BLAZE:
			auras.put(AuraType.DAMAGE, 10.0);
			break;

		case MUD:
			auras.put(AuraType.SLOW_AMMOUNT, 50.0);
			auras.put(AuraType.SLOW_DURATION, 50.0);
			break;

		case GEM:
			auras.put(AuraType.DAMAGE, 100.0);
			break;
		default:
			break;
		}
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
		decrementReload();
		if (getElement() != Elemento.LIFE) {

			for (int i = 0; i < getRedo(); i++) {
				fire();
			}
			if (classes.main.Control.mouseIsOnTop(getData(), this)) {
				getData().setDrawOnMouse(true);
				showRange(true);
				getData().setStringToDraw(getStringToDrawWhenMouseOver());
			} else {
				showRange(false);
			}

		}
	}

	private void fire() {
		EnemyParent firingAt = null;

		if (targetLockOn && getTarget() != null && !getTarget().isRemoved()) {
			if (getElement().equals(Elemento.ENDSTORM)) {
				firingAt = getTarget();
			} else {
				if (getRange() >= Auxi.point_distance(getTarget().getX(),
						getTarget().getY(), getX(), getY()))
					firingAt = getTarget();
			}
		} else {
			LinkedList<EnemyParent> list = getData().getEnemyListClone();
			firingAt = pickTarget(list);
		}

		if (getReload() <= 0) {
			if (firingAt != null) {
				ProjectileParent p = fireAt(firingAt);
				if (p != null) {
					getData().addDrawableObject(p);
				}
				setReload(getMaxReload());
			}
		}
	}

	private EnemyParent pickTarget(LinkedList<EnemyParent> list) {
		switch (targetType) {
		case RANDOM:
			Collections.shuffle(list);
			break;
		case FAST:
			Collections.sort(list, new Comparator<EnemyParent>() {
				@Override
				public int compare(EnemyParent e1, EnemyParent e2) {
					return (int) (Math.signum(e2.getSpeed() - e1.getSpeed()));
				}
			});
			break;

		case SLOW:
			Collections.sort(list, new Comparator<EnemyParent>() {
				@Override
				public int compare(EnemyParent e1, EnemyParent e2) {
					return -(int) (Math.signum(e2.getSpeed() - e1.getSpeed()));
				}
			});
			break;

		case HEALTHY:
			Collections.sort(list, new Comparator<EnemyParent>() {
				@Override
				public int compare(EnemyParent e1, EnemyParent e2) {
					return (int) (Math.signum(e2.getHealth() - e1.getHealth()));
				}
			});
			break;
		case UNHEALTHY:
			Collections.sort(list, new Comparator<EnemyParent>() {
				@Override
				public int compare(EnemyParent e1, EnemyParent e2) {
					return (int) (Math.signum(e1.getHealth() - e2.getHealth()));
				}
			});
			break;
		case BURN:
			Collections.sort(list, new Comparator<EnemyParent>() {
				@Override
				public int compare(EnemyParent e1, EnemyParent e2) {
					return (int) (Math.signum(e1.getBurnDuration()
							- e2.getBurnDuration()));
				}
			});
			break;
		}

		for (EnemyParent e : list) {
			if (!(e.getType().equals(EnemyType.PATHMAKER))) {
				if(getElement().equals(Elemento.ENDSTORM)){
					return e;
				}
				if (getRange() >= Auxi.point_distance(getX(), getY(), e.getX(),
						e.getY())) {
					return e;
				}
			}
		}
		return null;
	}

	public ProjectileParent fireAt(final EnemyParent tar) {

		this.target = tar;

		ProjectileParent ret = new ProjectileSimple(getData(), getX(), getY(),
				this, target, getProjectileSpeed(), getDamage(), getElement(),
				showSplash);

		switch (getElement()) {
		case LIFE:
		case MUD:
			ret = null;
			break;

		case EARTH:
		case FIRESTORM:
			ret = new ProjectileBlast(getData(), getX(), getY(), this,
					getDamage(), getElement(), getRange(), showSplash);
			break;

		case ZENITHTIDE:
			ret = new ProjectilePierce(getData(), getX(), getY(), this, target,
					getProjectileSpeed(), getDamage(), element, false,
					getRange() / getProjectileSpeed(), 0.5);
			ret.setAlpha((float) (Math.random()));
			break;

		case ENDSTORM:
			ret = new ProjectileAura(getData(), getX(), getY(), this,
					getDamage(), getElement());
			break;

		case BIOMASS:
			if (chargesAvaliable > 0) {
				ret = new ProjectilePierce(getData(), getX(), getY(), this,
						target, getProjectileSpeed(), getDamage() * 10,
						element, false, getRange() / getProjectileSpeed(), 0.5);
				chargesAvaliable -= 4;
			} else {
				if (charge >= maxCharge) {
					chargesAvaliable = maxCharge;
					charge = 0;
				} else {
					charge++;
					ret = new ProjectileSuck(getData(), target, this, this,
							getProjectileSpeed(), getDamage(), element);
				}
			}
			break;

		case PLASMA:
			if (chargesAvaliable > 0) {
				ret = new ProjectileBlast(getData(), getX(), getY(), this,
						getDamage(), getElement(), getRange(), showSplash,
						"projectiles/earth");
				chargesAvaliable -= 20;
			} else {
				if (charge >= maxCharge) {
					chargesAvaliable = maxCharge;
					charge = 0;
				} else {
					charge += 5;
					ret = new ProjectileSuck(getData(), target, this, this,
							getProjectileSpeed(), getDamage(), element);
				}
			}
			break;

		case RAIN:

			double angle = Math.random() * Math.PI * 2;
			double radius = Math.random() * getRange() + Math.random()
					* getRange() / 2;
			double xxx = Math.cos(angle) * radius;
			double yyy = Math.sin(angle) * radius;

			ret = new ProjectilePoint(getData(), getX() + xxx - 20, getY()
					+ yyy - 40, this, getDamage(), getElement(), 5, new Point(
					(int) (getX() + xxx), (int) (getY() + yyy)));
			break;

		case BLAZE:
		case INFERNO:
			ret = new ProjectileBomb(getData(), getX(), getY(), this, tar,
					getProjectileSpeed(), getDamage(), getElement(), showSplash);
			break;

		case WATER:
			ret.setAlpha((float) Math.random());
			break;
		default:
		}

		if (bounceNumber != 0 && bounceRange != 0) {
			ret.setBounce(bounceNumber, bounceRange);
		}
		if (getSlowAmmount() != 0 && getSlowDuration() != 0) {
			ret.setSlow(getSlowAmmount() / 100, getSlowDuration());
		}
		if (burnDuration != 0 && burnDamage != 0) {
			ret.setBurn(burnDamage, burnDuration);
		}
		return ret;

	}

	public String getStringToDrawWhenMouseOver() {
		String s1 = "    ";
		switch (gear) {
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

		return element.name + s1 + "   Dmg:" + getDamage() + "   Range: "
				+ getRange() + "   Exp: " + exp + s2;
	};

	public double getMaxReload() {
		return maxReload;
	}

	public boolean mouseOn() {
		return mouseOnMe;
	}

	public void showRange(boolean b) {
		mouseOnMe = b;
	}

	public void decrementReload() {
		setReload(Math.max(0, getReload() - 1));
	}

	/*
	 * STATS AFFECTED BY BUFFS
	 */

	public double getRange() {
		double ret = range;
		if (buffs.get(AuraType.RANGE) != null)
			for (Aura aura : buffs.get(AuraType.RANGE)) {
				if (ret > 0)
					ret += aura.getAmmount();
			}
		return ret + ret * (.1 * gear - .1);
	}

	public double getProjectileSpeed() {
		double ret = projectileSpeed;
		if (buffs.get(AuraType.PROJECTILE_SPEED) != null)
			for (Aura aura : buffs.get(AuraType.PROJECTILE_SPEED)) {
				ret += aura.getAmmount();
			}
		return ret;
	}

	public double getSlowAmmount() {
		double ret = slowAmmount;
		if (buffs.get(AuraType.SLOW_AMMOUNT) != null)
			for (Aura aura : buffs.get(AuraType.SLOW_AMMOUNT)) {
				if (aura.getAmmount() > ret) {
					ret = aura.getAmmount();
				}
			}
		return ret;
	}

	public int getSlowDuration() {
		int ret = slowDuration;
		if (buffs.get(AuraType.SLOW_DURATION) != null)
			for (Aura aura : buffs.get(AuraType.SLOW_DURATION)) {
				ret += aura.getAmmount();
			}
		return ret;
	}

	public int getRedo() {
		int ret = redo;
		if (buffs.get(AuraType.REDO) != null)
			for (Aura aura : buffs.get(AuraType.REDO)) {
				ret += aura.getAmmount();
			}

		return ret;
	}

	public double getDamage() {
		double ret = damage;
		if (buffs.get(AuraType.DAMAGE) != null)
			for (Aura aura : buffs.get(AuraType.DAMAGE)) {
				ret += damage * aura.getAmmount() / 100;
			}
		return ret * gear;
	}

	/*
	 * 
	 */

	public Elemento getElement() {
		return element;
	}

	public int getExp() {
		return exp;
	}

	public void addExp(int i) {
		exp += i;
	}

	public double getReload() {
		return reload;
	}

	public void setReload(double d) {
		reload = d;
	}

	public void upgrade(final Elemento elementPlus) {

		if (elementPlus != null) {
			if (elementPlus.getCost() > getData().getShards()) {
				getData().showMessage("NOT ENOUGH SHARDS", false,
						Data.COLOR_RED);
			} else {

				final Elemento myElement = element;

				System.out.println("Upgrading from " + myElement + " with "
						+ elementPlus);

				if (elementPlus == Elemento.LIFE) {
					if (myElement != Elemento.LIFE) {
						// if (gear < 3) {
						gear++;
						getData().addDrawableObject(
								new SplashText(getData(),
										getData().getMouse().x, getData()
												.getMouse().y,
										"Totem Gear Raised to " + gear, false,
										Data.COLOR_WHITE));
						getData().addShards(-Elemento.LIFE.getCost());
						// } else {
						// getData().addDrawableObject(
						// new SplashText(getData(), getData().getMouse().x,
						// getData().getMouse().y,
						// "Totem Gear already at Maximum"));
						// }
					} else {
						getData().addDrawableObject(
								new SplashText(getData(),
										getData().getMouse().x, getData()
												.getMouse().y,
										"Life totem has no gear", false,
										Data.COLOR_RED));
					}
				} else {

					boolean validElement = false;
					for (int i = 0; i < myElement.elementCode.length(); i++) {
						if (myElement.elementCode.charAt(i) == '0') {
							validElement = true;
						}
					}
					if (!validElement) {
						getData().addDrawableObject(
								new SplashText(getData(),
										getData().getMouse().x, getData()
												.getMouse().y,
										"Invalid Element Upgrade", false,
										Data.COLOR_RED));
						System.err.println("Invalid Upgrade");
					} else {

						Elemento newElement = Elemento.getUpgradedElement(
								myElement, elementPlus);

						if (newElement == null) {
							getData().addDrawableObject(
									new SplashText(getData(), getData()
											.getMouse().x,
											getData().getMouse().y,
											"Invalid Element Upgrade", false,
											Data.COLOR_RED));
						} else {
							getData().addShards(-newElement.getCost());
							getSpecs(newElement);
							getData().addDrawableObject(
									new SplashText(getData(), getData()
											.getMouse().x,
											getData().getMouse().y,
											newElement.name + " created",
											false, Data.COLOR_WHITE));
						}
					}
				}
			}
		}
	}

	public EnemyParent getTarget() {
		return target;
	}

	public void updateBuffs(final LinkedList<Tower> list) {
		buffs.clear();
		links.clear();
		for (int i = 0; i < AuraType.values().length; i++) {
			AuraType type = AuraType.values()[i];
			buffs.put(type, new LinkedList<Aura>());
		}
		for (Tower tower : list) {
			if (tower.getId() != getId())
				for (int i = 0; i < AuraType.values().length; i++) {
					AuraType type = AuraType.values()[i];
					if (tower.getAura(type) != null) {
						if (Auxi.point_distance(tower.getX(), tower.getY(),
								getX(), getY()) <= Data.AURA_RADIUS) {
							buffs.get(type).add(new Aura(tower.getAura(type)));
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
			if (buffs.get(type) != null)
				for (Aura a : buffs.get(type)) {
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

	public int getCharge() {
		return charge;
	}

	public int getMaxCharge() {
		return maxCharge;
	}

	public int getChargesAvaliable() {
		return chargesAvaliable;
	}

	public HashMap<Long, Point> getLinks() {
		return links;
	}

	public int getGear() {
		return gear;
	}

	public void sell() {
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
