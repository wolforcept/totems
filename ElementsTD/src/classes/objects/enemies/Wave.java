package classes.objects.enemies;

import java.util.LinkedList;

import classes.main.Data;

public class Wave {

	private LinkedList<Enemy> list;
	private int timer;
	private int delay;
	private WaveType waveType;
	private double enemyHealth;

	public static final String[] ENEMIE_NAMES = { "WEEKO", "SPYKE", "GHOST" };

	public enum WaveType {
		// WEEKO SPYKE GHOST
		BLA(0, 0, 10), //
		WEEKO_BASIC(5, 0, 0), //
		WEEKO_MEDIUM(8, 2, 0), //
		WEEKO_HARD(12, 2, 0), //
		GHOST_BASIC(1, 1, 1), //
		;

		public int[] enemies;

		WaveType(int a1, int a2, int a3) {
			int[] array = { a1, a2, a3 };
			enemies = array;
		}

		public int[] getEnemies() {
			return enemies;
		}

	};

	public Wave(Data data, int pathType, WaveType t, double hp) {

		waveType = t;
		enemyHealth = hp;

		list = new LinkedList<Enemy>();
		timer = delay = 80;
		for (int i = 0; i < ENEMIE_NAMES.length; i++) {

			EnemyType enemyType = EnemyType.valueOf(ENEMIE_NAMES[i]);
			for (int j = 0; j < waveType.enemies[i]; j++) {
				list.add(new Enemy(data, data.getCurrentEnemyHealth(),
						new Path(pathType), data.getCurrentReward(),
						enemyType.xp, enemyType));
			}
		}
	}

	public WaveType getWaveType() {
		return waveType;
	}

	public Enemy pop() {
		if (list.isEmpty()) {
			System.gc();
			return null;
		}
		return list.pop();
	}

	public int getTimer() {
		return timer;
	}

	public void resetTimer() {
		timer = delay;
	}

	public void decrementTimer() {
		timer -= 1;
	}

	public LinkedList<Enemy> getList() {
		return list;
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public double getEnemyHealth() {
		return enemyHealth;
	}

}
