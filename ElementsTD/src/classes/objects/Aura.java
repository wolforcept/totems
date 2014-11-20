package classes.objects;

import java.util.HashMap;

import classes.main.Elemento;

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

	public static void setAuras(HashMap<AuraType, Double> auras, Elemento e) {
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
	}

}
