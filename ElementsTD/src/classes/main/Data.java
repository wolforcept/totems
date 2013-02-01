package classes.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;

import classes.objects.DrawableObject;
import classes.objects.PathMark;
import classes.objects.Tower;
import classes.objects.enemies.Enemy;
import classes.objects.enemies.EnemyType;
import classes.objects.enemies.Path;
import classes.objects.enemies.Wave;
import classes.objects.enemies.Wave.WaveType;
import classes.objects.projectiles.ProjectileParent;
import classes.picture.GUI.Animation;
import classes.picture.GUI.towerGUI.TowerBox;
import classes.picture.splashes.Splash;
import classes.picture.splashes.SplashText;

public class Data {

	private static final double STARTING_HEALTH = 15,
			STARTING_HEALTH_GAIN = 0.4, HEALTH_GAIN_INCREMENT = 0.002;
	private static final int STARTING_REWARD = 1, REWARD_INCREMENT = 2;
	private static HashMap<String, Animation> imageMap;
	public static final float SELLING_REFUND = .75f;
	public static final long SERIAL_VERSION = 100055L;
	public static final Dimension WINDOW_SIZE = new Dimension(1024, 768);
	public static final int FREQUENCY = 60, STARTING_SHARDS = 200000;
	public static final double AURA_RADIUS = 60;
	public static Font font;

	// COLORS
	public static final Color //
			COLOR_BACKGROUND = new Color(0x07031C), // dark blue
			COLOR_HIT_NORMAL = new Color(0xF7941D), //
			COLOR_WHITE = Color.WHITE, //
			COLOR_RED = Color.RED, //
			COLOR_SELL = Color.LIGHT_GRAY, //
			COLOR_RANGE = new Color(96, 198, 214, 200), //
			// COLOR_LINK = new Color(255, 255, 0, 200), //
			COLOR_RELOAD_BAR = new Color(196, 255, 255, 180), //
			COLOR_CHARGING_BAR = new Color(196, 0, 0, 180), //
			COLOR_CHARGED_BAR = new Color(0, 196, 0, 180), //
			COLOR_GRID = new Color(96, 198, 214, 100), //
			COLOR_GRAY = new Color(128, 128, 128), //
			COLOR_SELECTED_TOWER_BUTTON = new Color(200, 200, 0), //
			COLOR_GRAY_OUT_BUTTONS = new Color(0.5f, 0.5f, 0.5f, 0.5f), //
			COLOR_TOOLTIP_TEXT = new Color(96, 198, 214, 200),//
			COLOR_ENEMY_FILE = new Color(96, 198, 214, 150), //
			COLOR_ENEMY_FILE_TEXT = new Color(196, 255, 255, 180) //
			;

	public void loadImages() {
		System.out.println("Started Loading Images");
		imageMap = new HashMap<String, Animation>();

		// towers tier 1
		getTowerDrawableImage(50, 0.75, "life", true);
		getTowerDrawableImage(50, 0.75, "wind", true); // A
		getTowerDrawableImage(100, 0.75, "fire", true); // F
		getTowerDrawableImage(35, 0.50, "water", true); // W
		getTowerDrawableImage(80, 0.75, "earth", true); // E

		// towers tier 2
		getTowerDrawableImage(50, 0.75, "breeze", true); // AA
		getTowerDrawableImage(50, 0.75, "blaze", true); // FF
		getTowerDrawableImage(200, 0.75, "zenithtide", true); // WW
		getTowerDrawableImage(50, 0.75, "gravity", true); // EE
		getTowerDrawableImage(50, 0.75, "magma", true);
		getTowerDrawableImage(50, 0.75, "dust", true);
		getTowerDrawableImage(50, 0.75, "steam", true);
		getTowerDrawableImage(110, 0.75, "thunder", true);
		getTowerDrawableImage(50, 0.75, "cloud", true);
		getTowerDrawableImage(50, 0.75, "dust", true);
		getTowerDrawableImage(1, 0.75, "mud", true);

		// tier 2.5

		getTowerDrawableImage(50, 0.75, "sandstorm", true); // AAE
		getTowerDrawableImage(50, 0.75, "rain", true); // AAW
		getTowerDrawableImage(50, 0.75, "firestorm", true); // AAF

		getTowerDrawableImage(50, 0.75, "thunderstorm", true); // FFA
		getTowerDrawableImage(50, 0.75, "vulcano", true); // FFE
		getTowerDrawableImage(50, 0.75, "plasma", true); // FFW

		getTowerDrawableImage(50, 0.75, "cyclone", true); // EEA
		getTowerDrawableImage(50, 0.75, "tephra", true); // EEF
		getTowerDrawableImage(50, 0.75, "quicksand", true); // EEW

		getTowerDrawableImage(22, 0.75, "blizzard", true); // WWA
		getTowerDrawableImage(50, 0.75, "tropicalequinox", true); // WWF
		getTowerDrawableImage(50, 0.75, "slurry", true); // WWE

		// towers tier 3
		getTowerDrawableImage(1, 0.75, "endstorm", true);
		getTowerDrawableImage(50, 0.75, "drought", true); // AEF
		getTowerDrawableImage(50, 0.75, "charge", true); //
		getTowerDrawableImage(50, 0.75, "biomass", true);

		getTowerDrawableImage(50, 0.75, "inferno", true);
		getTowerDrawableImage(1, 0.75, "gem", true);
		getTowerDrawableImage(100, 0.75, "tornado", true);
		getTowerDrawableImage(1, 0.75, "torrent", true);

		// BUTTONS
		getDrawableImage(1, 0.75, "buttons/life", true);
		getDrawableImage(1, 0.75, "buttons/wind", true);
		getDrawableImage(1, 0.75, "buttons/fire", true);
		getDrawableImage(1, 0.75, "buttons/water", true);
		getDrawableImage(1, 0.75, "buttons/earth", true);
		getDrawableImage(1, 0.75, "buttons/next_wave", true);
		getDrawableImage(1, 0.75, "buttons/box", true);
		getDrawableImage(1, 0.75, "buttons/sell", true);
		getDrawableImage(1, 0.75, "buttons/locktrue", true);
		getDrawableImage(1, 0.75, "buttons/lockfalse", true);
		getDrawableImage(1, 0.75, "buttons/lockgray", true);
		getDrawableImage(1, 0.75, "buttons/focushealthy", true);
		getDrawableImage(1, 0.75, "buttons/focusunhealthy", true);
		getDrawableImage(1, 0.75, "buttons/focusfast", true);
		getDrawableImage(1, 0.75, "buttons/focusslow", true);
		getDrawableImage(1, 0.75, "buttons/focusrandom", true);
		getDrawableImage(1, 0.75, "buttons/focusgray", true);
		getDrawableImage(1, 0.75, "buttons/autotrue", true);
		getDrawableImage(1, 0.75, "buttons/autofalse", true);

		// Enemies
		getDrawableImage(28, 0.75, "enemies/weeko", true);
		getDrawableImage(60, 0.75, "enemies/spyke", true);
		getDrawableImage(1, 0.75, "enemies/ghost", true);
		getDrawableImage(50, 0.75, "enemies/tank", true);

		// Projectiles
		getProjectileDrawableImage(1, 0.75, "earth", true);
		getProjectileDrawableImage(4, 0.75, "water", false);
		getProjectileDrawableImage(8, 0.75, "wind", false);
		getProjectileDrawableImage(1, 0.75, "fire", true);

		getProjectileDrawableImage(4, 0.75, "biomass", true);
		getProjectileDrawableImage(1, 0.75, "blaze", true);
		getProjectileDrawableImage(1, 0.75, "blizzard", true);
		getProjectileDrawableImage(4, 0.75, "breeze", true);
		getProjectileDrawableImage(1, 0.75, "charge", true);
		getProjectileDrawableImage(1, 0.75, "cloud", true);
		getProjectileDrawableImage(1, 0.75, "cyclone", true);
		getProjectileDrawableImage(1, 0.75, "drought", true);
		getProjectileDrawableImage(1, 0.75, "dust", true);
		getProjectileDrawableImage(1, 0.75, "endstorm", true);
		getProjectileDrawableImage(1, 0.75, "gem", true);
		getProjectileDrawableImage(1, 0.75, "gravity", true);
		getProjectileDrawableImage(1, 0.75, "inferno", true);
		getProjectileDrawableImage(1, 0.75, "magma", true);
		getProjectileDrawableImage(1, 0.75, "mud", true);
		getProjectileDrawableImage(1, 0.75, "plasma", true);
		getProjectileDrawableImage(1, 0.75, "quicksand", true);
		getProjectileDrawableImage(6, 0, "rain", true);
		getProjectileDrawableImage(1, 0.75, "sandstorm", true);
		getProjectileDrawableImage(1, 0.75, "slurry", true);
		getProjectileDrawableImage(1, 0.75, "steam", true);
		getProjectileDrawableImage(1, 0.75, "tephra", true);
		getProjectileDrawableImage(1, 0.75, "thunder", true);
		getProjectileDrawableImage(1, 0.75, "thunderstorm", true);
		getProjectileDrawableImage(1, 0.75, "tornado", true);
		getProjectileDrawableImage(4, 0.75, "torrent", true);
		getProjectileDrawableImage(1, 0.75, "tropicalequinox", true);
		getProjectileDrawableImage(1, 0.75, "vulcano", true);
		getProjectileDrawableImage(4, 0.75, "zenithtide", true);
		getProjectileDrawableImage(1, 0.75, "firestorm", true);

		// PARTICLES
		getDrawableImage(2, 0.1, "particles/fire", true);

		// OTHER
		getDrawableImage(1, 0.75, "enemies/pathmaker", true);
		getDrawableImage(1, 0.75, "pathmark", true);
		getDrawableImage(61, 0.25, "shield", true);

		getDrawableImage(1, 0.75, "shard", true);
		getDrawableImage(10, 0.75, "green_circle", true);

		System.out.println("Finished Loading Images");

		System.out.println("Setting Image Map");
		for (String s : imageMap.keySet()) {
			getAnimation(s);
		}
		System.out.println("Image Map Set");

		System.out.println("Getting Fonts");
		try {
			InputStream a = getClass().getClassLoader().getResourceAsStream(
					"resources/db_boxer.ttf");
			font = Font.createFont(Font.PLAIN, a);

		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Load Complete");

	}

	private void getProjectileDrawableImage(int imgNumber, double imgSpeed,
			String name, boolean isRotatable) {
		getDrawableImage(imgNumber, imgSpeed, "projectiles/" + name,
				isRotatable);
	}

	public static Animation getAnimation(String s) {
		if (imageMap.containsKey(s)) {
			return imageMap.get(s);
		} else {
			System.err.println("Animation <" + s
					+ "> not found int imageHashMap");
			System.exit(0);
			return null;
		}
	}

	private void getTowerDrawableImage(int length, double speed, String a,
			boolean isRotatable) {
		try {
			Elemento e = Elemento.valueOf(a.toUpperCase());
			System.out.println("Getting " + a + " cost=" + e.getCost()
					+ " dps="
					+ (Math.floor(e.damage * 1000 / e.maxReload) / 10));
			imageMap.put(
					a,
					new Animation(ImageIO.read(getClass().getResource(
							"/resources/totems/" + a + ".png")), speed, length,
							isRotatable));
		} catch (IOException e) {
			System.err.println("Image File Missing");
		}
	}

	private void getDrawableImage(int length, double speed, String a,
			boolean isRotatable) {
		try {
			System.out.println("Getting " + a);
			imageMap.put(
					a,
					new Animation(ImageIO.read(getClass().getResource(
							"/resources/" + a + ".png")), speed, length,
							isRotatable));
		} catch (IOException e) {
			System.err.println("Image File Missing");
		}
	}

	private LinkedList<Tower> towerList;
	private LinkedList<Enemy> enemyList;
	private LinkedList<ProjectileParent> projectileList;

	private LinkedList<DrawableObject> staticList;
	private LinkedList<Splash> splashList;
	private LinkedList<PathMark> pathMarkList;

	private ReentrantLock towerLock, enemyLock, projectileLock, pathMarkLock,
			splashLock, staticLock;

	private Elemento selectedElement;

	private Point mouse;
	private boolean drawOnMouse, creatingPath, autoWave;
	private String stringToDraw;
	private double currentEnemyHealth, currentEnemyHealthGain;
	private int currentReward;

	private int waveNumber;
	private Wave currentWave;
	private Wave nextWave;

	private int shards;
	private int pathType;
	private Enemy pathMaker;
	private long currentId;
	private long selectedTowerId;
	private TowerBox currentBox;

	public Data(int p) {

		pathType = p;

		towerList = new LinkedList<Tower>();
		enemyList = new LinkedList<Enemy>();
		projectileList = new LinkedList<ProjectileParent>();

		staticList = new LinkedList<DrawableObject>();
		pathMarkList = new LinkedList<PathMark>();
		splashList = new LinkedList<Splash>();

		towerLock = new ReentrantLock();
		enemyLock = new ReentrantLock();
		projectileLock = new ReentrantLock();
		pathMarkLock = new ReentrantLock();
		splashLock = new ReentrantLock();
		staticLock = new ReentrantLock();

		selectedElement = Elemento.LIFE;
		mouse = new Point(0, 0);
		stringToDraw = "";
		drawOnMouse = false;
		currentEnemyHealth = STARTING_HEALTH;
		currentEnemyHealthGain = STARTING_HEALTH_GAIN;
		currentReward = STARTING_REWARD;
		shards = STARTING_SHARDS;

		waveNumber = 0;
		autoWave = false;

		currentId = 0;

		creatingPath = true;

		loadImages();

		setNextWave();

	}

	public void addDrawableObject(DrawableObject o) {
		if (o instanceof Tower) {
			towerLock.lock();
			towerList.add((Tower) o);
			towerLock.unlock();
		} else if (o instanceof Enemy) {
			enemyLock.lock();
			enemyList.add((Enemy) o);
			enemyLock.unlock();
		} else if (o instanceof ProjectileParent) {
			projectileLock.lock();
			projectileList.add((ProjectileParent) o);
			projectileLock.unlock();
		} else if (o instanceof PathMark) {
			pathMarkLock.lock();
			pathMarkList.add((PathMark) o);
			pathMarkLock.unlock();
		} else if (o instanceof Splash) {
			splashLock.lock();
			splashList.add((Splash) o);
			splashLock.unlock();
		} else {
			staticLock.lock();
			staticList.add(o);
			staticLock.unlock();
		}
	}

	public Elemento getSelectedElement() {
		return selectedElement;
	}

	public boolean getDrawOnMouse() {
		return drawOnMouse;
	}

	public Point getMouse() {
		return mouse;
	}

	public void setMouse(int x, int y) {
		mouse.x = x;
		mouse.y = y;
	}

	public void setDrawOnMouse(boolean b) {
		drawOnMouse = b;
	}

	public String getStringToDraw() {
		return stringToDraw;
	}

	public void setStringToDraw(String stringToDrawWhenMouseOver) {
		stringToDraw = stringToDrawWhenMouseOver;
	}

	public void setSelectedElement(Elemento element) {
		selectedElement = element;
	}

	public double getCurrentEnemyHealth() {
		return currentEnemyHealth;
	}

	public int getShards() {
		return shards;
	}

	public void addShards(int n) {
		shards += n;
	}

	public int getCurrentReward() {
		return currentReward;
	}

	public void incrementReward() {
		currentReward += REWARD_INCREMENT;
	}

	public Wave getCurrentWave() {
		return currentWave;
	}

	public void setCurrentWave(Wave wave) {
		this.currentWave = wave;
	}

	public void setNextWave(Wave wave) {
		this.nextWave = wave;
	}

	public int getPathType() {
		return pathType;
	}

	public void createPathMaker(Path path) {
		pathMaker = new Enemy(this, 1, path, 0, 0, EnemyType.PATHMAKER);
		addDrawableObject(pathMaker);
	}

	/*
	 * START OF LIST GETTERS
	 */

	public LinkedList<DrawableObject> getStaticListClone() {
		try {
			staticLock.lock();
			return staticList;
		} finally {
			staticLock.unlock();
		}

	}

	public LinkedList<Enemy> getEnemyListClone() {
		try {
			enemyLock.lock();
			return new LinkedList<Enemy>(enemyList);
		} finally {
			enemyLock.unlock();
		}
	}

	public LinkedList<Tower> getTowerListClone() {
		try {
			towerLock.lock();
			return new LinkedList<Tower>(towerList);
		} finally {
			towerLock.unlock();
		}
	}

	public LinkedList<ProjectileParent> getProjectileListClone() {
		try {
			projectileLock.lock();
			return new LinkedList<ProjectileParent>(projectileList);
		} finally {
			projectileLock.unlock();
		}
	}

	public LinkedList<PathMark> getPathMarkListClone() {
		try {
			pathMarkLock.lock();
			return new LinkedList<PathMark>(pathMarkList);
		} finally {
			pathMarkLock.unlock();
		}
	}

	public LinkedList<Splash> getSplashListClone() {
		try {
			splashLock.lock();
			return new LinkedList<Splash>(splashList);
		} finally {
			splashLock.unlock();
		}
	}

	/*
	 * END OF LIST GETTERS
	 */

	public int getWaveNumber() {
		return waveNumber;
	}

	public void incrementWaveNumber() {
		waveNumber++;
		currentEnemyHealth = Math.floor(currentEnemyHealth + currentEnemyHealth
				* currentEnemyHealthGain);
		currentEnemyHealthGain -= HEALTH_GAIN_INCREMENT;
	}

	public long getNewId() {
		currentId++;
		return currentId;
	}

	public void setSelectedTower(long id) {
		selectedTowerId = id;
	}

	public Tower getSelectedTower() {

		for (Tower t : getTowerListClone()) {
			if (t.getId() == selectedTowerId)
				return t;
		}
		return null;
	}

	public void removeCurrentBox() {
		if (currentBox != null) {
			currentBox.clear();
			currentBox = null;
		}
	}

	public void setCurrentBox(TowerBox newBox) {
		currentBox = newBox;
	}

	public TowerBox getCurrentBox() {
		return currentBox;
	}

	public void updateBuffs() {
		LinkedList<Tower> tempList = getTowerListClone();
		for (Tower t : towerList) {
			if (!t.isRemoved()) {
				t.updateBuffs(tempList);
			}
		}
	}

	public void setCreatingPath(boolean creatingPath) {
		this.creatingPath = creatingPath;
	}

	public boolean isCreatingPath() {
		return creatingPath;
	}

	public void toggleAutoWaveLock() {
		if (autoWave) {
			autoWave = false;
		} else {
			autoWave = true;
		}
	}

	public void showMessage(String text, boolean downwards, Color color) {
		addDrawableObject(new SplashText(this, getMouse().x, getMouse().y,
				text, downwards, color));
	}

	public void removeAll() {
		boolean needToUpdateBuffs = false;
		towerLock.lock();
		for (Iterator<Tower> iterator = towerList.iterator(); //
		iterator.hasNext();) {
			Tower t = iterator.next();
			if (t.isRemoved()) {
				iterator.remove();
				needToUpdateBuffs = true;
			}
		}
		if (needToUpdateBuffs) {
			updateBuffs();
		}
		towerLock.unlock();

		projectileLock.lock();
		for (Iterator<ProjectileParent> iterator = projectileList.iterator(); //
		iterator.hasNext();) {
			ProjectileParent p = iterator.next();
			if (p.isRemoved()) {
				iterator.remove();
			}
		}
		projectileLock.unlock();

		splashLock.lock();
		for (Iterator<Splash> iterator = splashList.iterator(); //
		iterator.hasNext();) {
			Splash s = iterator.next();
			if (s.isRemoved()) {
				iterator.remove();
			}
		}
		splashLock.unlock();

		staticLock.lock();
		for (Iterator<DrawableObject> iterator = staticList.iterator(); //
		iterator.hasNext();) {
			DrawableObject o = iterator.next();
			if (o.isRemoved()) {
				iterator.remove();
			}
		}
		staticLock.unlock();

		enemyLock.lock();
		for (Iterator<Enemy> iterator = enemyList.iterator(); //
		iterator.hasNext();) {
			Enemy e = iterator.next();
			if (e.isRemoved()) {
				iterator.remove();
			}
		}
		enemyLock.unlock();
	}

	public void sendWaveIfAuto() {
		if (isAutoWave()
				&& (getCurrentWave() == null || getCurrentWave().isEmpty())) {
			boolean send = true;
			for (Enemy e : getEnemyListClone()) {
				if (!e.isRemoved()) {
					send = false;
				}
			}
			if (send)
				sendNextWave();
		}
	}

	private void setNextWave() {
		int wt = waveNumber % WaveType.values().length;
		setNextWave(new Wave(this, pathType, WaveType.values()[wt],
				currentEnemyHealth));
	}
	
	public Wave getNextWave() {
		return nextWave;
	}
	
	public void sendNextWave() {
		incrementWaveNumber();
		incrementReward();
		currentWave = nextWave;
		setNextWave();
	}

	public boolean isAutoWave() {
		return autoWave;
	}

}
