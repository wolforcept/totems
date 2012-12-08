package classes.objects;

public class Aura {

	public enum AuraType {
		DAMAGE, RELOAD_SPEED, PROJECTILE_SPEED, RANGE, SLOW_AMMOUNT, SLOW_DURATION, REDO
	}

	private double ammount;

	public Aura(double aAmmount) {
		ammount = aAmmount;
	}

	public double getAmmount() {
		return ammount;
	}

}
