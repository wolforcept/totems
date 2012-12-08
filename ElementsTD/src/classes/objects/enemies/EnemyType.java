package classes.objects.enemies;

public enum EnemyType {
	PATHMAKER (1, 10,0, 0, 0, 0, 0),
	//		HEALTH		SPEED	XP		WIND	FIRE	WATER	EARTH
	WEEKO (	1.0,		0.8,	2,		0,		0,		0,		0), //
	SPYKE (	0.8,		0.5,	5,		0,		0,		0,		0), //
	GHOST (	0.7,		1,		5,		0,		0,		0,		0), //
	;

	public double health, speed, wind, fire, water, earth;
	public int xp;

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
