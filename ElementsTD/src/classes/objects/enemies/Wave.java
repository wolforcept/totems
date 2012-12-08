package classes.objects.enemies;

import java.util.LinkedList;

import classes.main.Data;

public class Wave {

	private LinkedList<EnemyParent> list;
	private int timer;
	private int delay;

	private static final String[] enemies = { "WEEKO", "SPYKE", "GHOST" };

	enum WaveType {
		//				WEEKO	SPYKE	GHOST
		WEEKO_BASIC (	5, 		0,		0), //
		WEEKO_MEDIUM (	8, 		2,		0), //
		WEEKO_HARD (	12, 	2,		0), //
		GHOST_BASIC(	1,		1,		1), //
;
		
		int[] enemies;

		WaveType(int a1, int a2, int a3) {
			int[] array = {a1,a2,a3};
			enemies = array;
		}

	};

	public Wave(Data data, int pathType) {

		WaveType waveType = WaveType.GHOST_BASIC;

		list = new LinkedList<EnemyParent>();
		timer = delay = 80;
		for (int i = 0; i < enemies.length; i++) {

			EnemyType enemyType = EnemyType.valueOf(enemies[i]);
			for (int j = 0; j < waveType.enemies[i]; j++) {
				list.add(new EnemyParent(data, data.getCurrentEnemyHealth(),
						new Path(pathType), data.getCurrentReward(), enemyType.xp,
						enemyType));
			}
		}
	}

	public EnemyParent pop() {
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

	public LinkedList<EnemyParent> getList() {
		return list;
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

}
