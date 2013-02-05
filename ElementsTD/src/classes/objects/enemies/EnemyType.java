package classes.objects.enemies;

public enum EnemyType {
	PATHMAKER (1, 10,0, 0, 0, 0, 0),
	//		HEALTH		SPEED	XP		WIND	FIRE	WATER	EARTH
	WEEKO (	0.8,		0.8,	2,		0,		0,		10,		0), //
	SPYKE (	1.0,		0.5,	5,		20,		0,		0,		0), //
	GHOST (	0.7,		1,		3,		0,		0,		-100,	100), //
	;

	public final double health, speed, wind, fire, water, earth;
	public final int xp;

	EnemyType(double health, double speed, int xp, double wind, double fire,
			double water, double earth) {
		this.health = health;
		this.speed = speed;
		this.xp = xp;

		this.wind = wind;
		this.fire = fire;
		this.water = water;
		this.earth = earth;
	}

}
