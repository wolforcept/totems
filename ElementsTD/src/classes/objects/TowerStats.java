package classes.objects;

import java.util.HashMap;
import java.util.LinkedList;

import classes.objects.Aura.AuraType;

public class TowerStats {

	private double range, reload, maxReload, projectileSpeed, damage,
			burnDamage, slowAmmount, bounceRange;
	private int burnDuration, slowDuration, bounceNumber, charge, maxCharge,
			chargesAvaliable, chargeIncrement;
	private int gear, exp, redo;
	private HashMap<AuraType, LinkedList<Aura>> buffs;

	public TowerStats() {
		charge = 0;
		maxCharge = 100;
		chargesAvaliable = 0;
		chargeIncrement = 1;
		gear = 1;
		exp = 0;

		buffs = new HashMap<Aura.AuraType, LinkedList<Aura>>();
	}

	public void decrementReload() {
		if (getReload() > 0)
			setReload(getReload() - 1);
		else
			setReload(0);
	}

	public double getReload() {
		return reload;
	}

	public double getMaxReload() {
		return maxReload;
	}

	public double getBurnDamage() {
		return burnDamage;
	}

	public double getBounceRange() {
		return bounceRange;
	}

	public TowerStats setRange(double range) {
		this.range = range;
		return this;
	}

	public TowerStats setReload(double reload) {
		this.reload = reload;
		return this;
	}

	public TowerStats setMaxReload(double maxReload) {
		this.maxReload = maxReload;
		return this;
	}

	public TowerStats setProjectileSpeed(double projectileSpeed) {
		this.projectileSpeed = projectileSpeed;
		return this;
	}

	public TowerStats setDamage(double damage) {
		this.damage = damage;
		return this;
	}

	public TowerStats setBurnDamage(double burnDamage) {
		this.burnDamage = burnDamage;
		return this;
	}

	public TowerStats setBurnDuration(int burnDuration) {
		this.burnDuration = burnDuration;
		return this;
	}

	public TowerStats setSlowAmmount(double slowAmmount) {
		this.slowAmmount = slowAmmount;
		return this;
	}

	public TowerStats setSlowDuration(int slowDuration) {
		this.slowDuration = slowDuration;
		return this;
	}

	public TowerStats setBounceRange(double bounceRange) {
		this.bounceRange = bounceRange;
		return this;
	}

	public TowerStats setBounceNumber(int bounceNumber) {
		this.bounceNumber = bounceNumber;
		return this;
	}

	public void incrementCharge() {
		this.charge += chargeIncrement;
		if (charge >= maxCharge) {
			chargesAvaliable = maxCharge;
			charge = 0;
		}
	}

	public boolean chargesAvaliable(int i) {
		return chargesAvaliable >= i - getGear();
	}

	public boolean chargesAvaliable() {
		return chargesAvaliable > 0;
	}

	public void resetCharge() {
		charge = 0;
	}

	public void useTokens(int i) {
		if (i - getGear() > chargesAvaliable)
			throw new RuntimeException("Not Enough Charges ("
					+ chargesAvaliable + ")");
		chargesAvaliable -= i - getGear();
	}

	public void nextGear() {
		if (gear > 2)
			throw new RuntimeException("Invalid Gear: " + (gear + 1));
		gear++;
	}

	public int getGear() {
		return gear;
	}

	public int getExp() {
		return exp;
	}

	public void addExp(int i) {
		exp += i;
	}

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
		return ret * getGear();
	}

	public int getBounceNumber() {
		return bounceNumber;
	}

	public int getBurnDuration() {
		return burnDuration;
	}

	public void clearBuffs() {
		buffs.clear();
	}

	public void putBuff(AuraType type, LinkedList<Aura> auras) {
		buffs.put(type, auras);
	}

	public LinkedList<Aura> getBuff(AuraType type) {
		return buffs.get(type);
	}

	public int getChargesAvaliable() {
		return chargesAvaliable;
	}

	public int getMaxCharge() {
		return maxCharge;
	}

	public boolean hasCharge() {
		return charge > 0;
	}

	public int getCharge() {
		return charge;
	}

	public TowerStats setRedo(int redo) {
		this.redo = redo;
		return this;
	}

}
