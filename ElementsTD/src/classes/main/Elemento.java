package classes.main;

import classes.objects.Tower.TargetType;

public enum Elemento {
	
//	ENUM			CODE	NAME					RANGE	RELOAD	SPEED	DMG		REDO	TARGET TYPE				TRGT LOCK	SPLASH		SLOW	DUR		BURN	DUR		BNCS	RNG
	LIFE(			"000",	"Totem of Life",		0,		0,		0.0,	0,		0,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
	WIND(			"A00",	"Totem of Wind",		128,	60,		3.0, 	10.0, 	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		3,		100),
	FIRE(			"F00",	"Fire Totem",			128, 	100, 	10.0, 	0.0, 	1,		TargetType.BURN,		false,		false,		0,		0,		0.5,	500,	0,		0),
	WATER(			"W00", 	"Water Totem",			128,	1,		10.0,	0.5,	1,		TargetType.HEALTHY,		true,		false,		0,		0,		0,		0,		0,		0),
	EARTH(			"E00",	"Earth Totem",			128, 	60, 	10.0,	20.0, 	1, 		TargetType.RANDOM,		true,		true,		5,		50,		0,		0,		0,		0),
//	ENUM			CODE	NAME					RANGE	RELOAD	SPEED	DMG		REDO	TARGET TYPE				TRGT LOCK	SPLASH		SLOW	DUR		BURN	DUR		BNCS	RNG
	STEAM(			"FW0",	"Steam Totem",			128,	40,		10.0,	10.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
	THUNDER(		"FA0",	"Thunder Totem",		128,	40,		10.0,	10.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
	MAGMA(			"FE0",	"Magma Totem",			128,	40,		10.0,	10.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
	CLOUD(			"WA0",	"Cloud",				128,	40,		10.0,	10.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
	MUD(			"WE0",	"Mud Field",			0,		0,		0.0,	0.0,	0,		TargetType.FAST,		false,		false,		0,		0,		0,		0,		0,		0),
	DUST(			"EA0",	"Dust Field",			128,	40,		10.0,	10.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
//	ENUM			CODE	NAME					RANGE	RELOAD	SPEED	DMG		REDO	TARGET TYPE				TRGT LOCK	SPLASH		SLOW	DUR		BURN	DUR		BNCS	RNG		
	BREEZE(			"AA0",	"Totem of Breezes",		128,	40,		10.0,	0.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
	SANDSTORM(		"AAE",	"Sandstorm Totem",		128,	40,		10.0,	10.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
	RAIN(			"AAW",	"Rain Totem",			512,	0,		10.0,	10.0,	8,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
	THUNDERSTORM(	"AAF",	"Thunderstorm Totem",	128,	40,		10.0,	10.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
//	ENUM			CODE	NAME					RANGE	RELOAD	SPEED	DMG		REDO	TARGET TYPE				TRGT LOCK	SPLASH		SLOW	DUR		BURN	DUR		BNCS	RNG	
	BLAZE(			"FF0",	"Blaze Totem",			128,	40,		10.0,	10.0,	1,		TargetType.BURN,		false,		true,		0,		0,		1,		50,		1,		100),
	VULCANO(		"FFE",	"Vulcano",				128,	40,		10.0,	10.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
	FIRESTORM(		"FFA",	"Firestorm Totem",		128,	100,	10.0,	0.0,	1,		TargetType.RANDOM,		true,		false,		0,		0,		0.1,	500,	0,		0),
	PLASMA(			"FFW",	"Totem of Plasma",		128,	40,		10.0,	10.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
//	ENUM			CODE	NAME					RANGE	RELOAD	SPEED	DMG		REDO	TARGET TYPE				TRGT LOCK	SPLASH		SLOW	DUR		BURN	DUR		BNCS	RNG	
	GRAVITY(		"EE0",	"Gravity Field",		128,	40,		10.0,	10.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
	CYCLONE(		"EEA",	"Cyclone Totem",		128,	40,		10.0,	10.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
	TEPHRA(			"EEF",	"Tephra Totem",			128,	40,		10.0,	10.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
	QUICKSAND(		"EEW",	"Quicksand Field",		128,	40,		10.0,	10.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
//	ENUM			CODE	NAME					RANGE	RELOAD	SPEED	DMG		REDO	TARGET TYPE				TRGT LOCK	SPLASH		SLOW	DUR		BURN	DUR		BNCS	RNG	
	ZENITHTIDE(		"WW0",	"Zenith Tide Totem",	158,	1,		10.0,	1.8,	1,		TargetType.RANDOM,		true,		false,		0,		0,		0,		0,		0,		0),
	BLIZZARD(		"WWA",	"Blizzard Totem",		128,	40,		10.0,	10.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
	SLURRY(			"WWE",	"Totem of Slurry",		128,	40,		10.0,	10.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
	TROPICALEQUINOX("WWF",	"Tropical Equinox",		128,	500,	5.0,	50.0,	1,		TargetType.RANDOM,		true,		true,		50,		50,		0,		0,		0,		0),
//	ENUM			CODE	NAME					RANGE	RELOAD	SPEED	DMG		REDO	TARGET TYPE				TRGT LOCK	SPLASH		SLOW	DUR		BURN	DUR		BNCS	RNG	
	DROUGHT(		"FAE",	"Drought Field",		128,	40,		10.0,	10.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
	BIOMASS(		"WEA",	"Biomass Totem",		150,	2,		5.0,	10,		1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
	ENDSTORM(		"FWA",	"Endstorm",				128,	10,		10.0,	10.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
	CHARGE(			"EWF",	"Charged Fields",		128,	40,		10.0,	10.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
//	ENUM			CODE	NAME					RANGE	RELOAD	SPEED	DMG		REDO	TARGET TYPE				TRGT LOCK	SPLASH		SLOW	DUR		BURN	DUR		BNCS	RNG	
	INFERNO(		"FFF",	"Inferno Totem",		186,	40,		20.0,	100.0,	4,		TargetType.RANDOM,		true,		true,		0,		0,		5,		50,		1,		100),
	GEM(			"EEE",	"Gem",					0,		0,		0.0,	0.0,	0,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
	TORNADO(		"AAA",	"Tornado Totem",		128,	40,		10.0,	10.0,	1,		TargetType.RANDOM,		true,		true,		0,		0,		0,		0,		0,		0),
	TORRENT(		"WWW",	"Torrent Totem",		256,	0,		1,		0.02,	4,		TargetType.RANDOM,		false,		false,		0,		0,		0,		0,		0,		0);
//	ENUM			CODE	NAME					RANGE	RELOAD	SPEED	DMG		REDO	TARGET TYPE				TRGT LOCK	SPLASH		SLOW	DUR		BURN	DUR		BNCS	RNG	

	private int size;
	public int range, maxReload, slowDuration, burnDuration, bounceNumber;
	public double projectileSpeed, damage, slowAmmount, bounceRange,
			burnDamage;
	public int redo;
	public String name;
	public String elementCode;
	public TargetType targetType;
	public boolean targetLockOn, showSplash;

	private Elemento(String cons, String string, int aRange, int aMaxReload,
			double aProjectileSpeed, double aDamage, int aRedo,
			TargetType aTargetType, boolean aTargetLockOn, boolean aShowSplash,
			double aSlowAmmount, int aSlowDuration, double aBurnDamage,
			int aBurnDuration, int aBounceNumber, double aBounceRange) {
		elementCode = cons;
		if (elementCode.length() != 3) {
			System.err.println("WRONG CONSTRUCTION AT " + this);
			System.exit(0);
		}
		name = string;
		size = 32;
		range = aRange;
		maxReload = aMaxReload;
		projectileSpeed = aProjectileSpeed;
		damage = aDamage;
		redo = aRedo;
		targetType = aTargetType;
		targetLockOn = aTargetLockOn;
		showSplash = aShowSplash;
		slowAmmount = aSlowAmmount;
		slowDuration = aSlowDuration;
		burnDamage = aBurnDamage;
		burnDuration = aBurnDuration;
		bounceNumber = aBounceNumber;
		bounceRange = aBounceRange;
	}

	public int getId() {
		return ordinal();
	}

	public int getSize() {
		return size;
	}

	public int getCost() {
		return costOf(elementCode.charAt(0)) * costOf(elementCode.charAt(1))
				* costOf(elementCode.charAt(2));
	}

	public static Elemento getElement(final String s) {
		if (s.length() != 3) {
			System.err.println("Invalid getElement() Arguments");
			System.exit(0);
		}

		final char[] chars = { s.charAt(0), s.charAt(1), s.charAt(2) };

		final int[][] comb = { { 0, 1, 2 }, { 0, 2, 1 }, { 1, 0, 2 },
				{ 1, 2, 0 }, { 2, 0, 1 }, { 2, 1, 0 } };

		for (int c = 0; c < comb.length; c++) {
			char[] charArray = { chars[comb[c][0]], chars[comb[c][1]],
					chars[comb[c][2]] };
			for (int i = 0; i < values().length; i++) {
				if (values()[i].elementCode.equals(new String(charArray))) {
					return values()[i];
				}
			}
		}
		System.err.println("Invalid getElement() call");
		return null;
	}

	private int costOf(char c) {
		int cost = -1;
		switch (c) {
		case 'A':
			cost = 20;
			break;

		case 'W':
			cost = 10;
			break;

		case 'E':
			cost = 25;
			break;

		case 'F':
			cost = 15;
			break;
		case 'L':
			cost = 5;
			break;

		case '0':
			cost = 1;
			break;

		default:
			break;
		}
		if (cost == -1) {
			System.err.println("INVALID CHAR");
			return 0;
		} else {
			return cost;
		}
	}

	public static char elementToLetter(Elemento e) {
		switch (e) {
		case WIND:
			return 'A';
		case FIRE:
			return 'F';
		case WATER:
			return 'W';
		case EARTH:
			return 'E';
		case LIFE:
		default:
			return '0';
		}

	}

	public static Elemento getUpgradedElement(Elemento element,
			Elemento elementPlus) {
		int i = 0;
		char[] s = new String(element.elementCode).toCharArray();
		while (i < s.length) {
			if (s[i] == '0') {
				s[i] = Elemento.elementToLetter(elementPlus);
				Elemento newElement = Elemento.getElement(new String(s));
				return newElement;
			}
			i++;
		}
		return null;
	}

}
