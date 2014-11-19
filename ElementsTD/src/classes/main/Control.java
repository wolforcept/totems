package classes.main;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;

import classes.objects.DrawableObject;
import classes.objects.PathMark;
import classes.objects.Tower;
import classes.objects.enemies.Enemy;
import classes.objects.enemies.EnemyType;
import classes.objects.enemies.Path;
import classes.objects.projectiles.ProjectileParent;
import classes.picture.GUI.AutoWaveButton;
import classes.picture.GUI.Button;
import classes.picture.GUI.GraphicsButton;
import classes.picture.GUI.MyPanel;
import classes.picture.GUI.NextWaveButton;
import classes.picture.GUI.towerGUI.TowerBox;
import classes.picture.GUI.towerGUI.TowerButton;
import classes.picture.splashes.Splash;
import classes.picture.splashes.SplashAnimation;
import classes.picture.splashes.SplashText;

public class Control extends Thread {

	private MyPanel panel;
	private Data data;
	private boolean leftMouseReleased;
	private boolean rightMouseReleased;

	public Control() throws IOException {

		data = new Data(Path.LEVEL1);
		rightMouseReleased = false;

		System.out.println("Setting up buttons.");
		int spot = 1;
		createTowerButton(spot * 32, 32, "life");
		spot++;
		createTowerButton(spot * 32, 32, "wind");
		spot++;
		createTowerButton(spot * 32, 32, "fire");
		spot++;
		createTowerButton(spot * 32, 32, "water");
		spot++;
		createTowerButton(spot * 32, 32, "earth");
		spot++;

		data.addDrawableObject(new NextWaveButton(data, 688, 22));
		data.addDrawableObject(new AutoWaveButton(data, 688, 22 + 32));

		data.addDrawableObject(new GraphicsButton(data, 788, 22 + 32));

		data.createPathMaker(new Path(data.getPathType()));

		System.out.println("Buttons set up.");

		// Panel
		panel = new MyPanel(data);

		panel.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {

				int notches = e.getWheelRotation();
				int id = data.getSelectedElement().getId();
				int max = 5;

				if (Math.signum(notches) > 0) {
					id++;
					if (id == max) {
						id = 0;
					}
				} else if (Math.signum(notches) < 0) {
					id--;
					if (id == -1) {
						id = max - 1;
					}
				}

				data.setSelectedElement(Elemento.values()[id]);

			}
		});

		panel.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				switch (e.getButton()) {

				case MouseEvent.BUTTON1:
					leftMouseReleased = true;
					break;
				case MouseEvent.BUTTON3:
					rightMouseReleased = true;
					break;
				}
			}
		});

		panel.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				data.getMouse().x = e.getX();
				data.getMouse().y = e.getY();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				data.getMouse().x = e.getX();
				data.getMouse().y = e.getY();
			}
		});

	}

	@Override
	public void run() {

		try {
			while (true) {

				/*
				 * REMOVE STUFF
				 */
				data.removeAll();

				data.setDrawOnMouse(false);
				if (data.getCurrentBox() != null
						&& data.getSelectedTower() == null) {
					data.removeCurrentBox();
				}
				/*
				 * LEFT MOUSE PRESSED
				 */

				if (leftMouseReleased) {
					leftMouseReleased = false;
					boolean pressedButton = false;
					for (DrawableObject b : data.getStaticListClone()) {
						if (b instanceof Button) {
							if (mouseIsOnTop(data, b)) {
								((Button) b).pressed();
								pressedButton = true;
							}
						}
					}

					if (!pressedButton) {
						boolean somethingSelected = false;
						if (data.getCurrentBox() == null
								|| (data.getCurrentBox() != null && !mouseIsOnTop(
										data, data.getCurrentBox()))) {
							for (Tower t : data.getTowerListClone()) {
								if (mouseIsOnTop(data, t)) {

									data.setSelectedTower(t.getId());
									System.out.println(t.getElement().name
											+ " was selected.");
									somethingSelected = true;
								}
							}
							if (somethingSelected) {
								if (data.getCurrentBox() == null
										|| data.getCurrentBox().getTower()
												.getId() != data
												.getSelectedTower().getId()) {

									data.removeCurrentBox();
									TowerBox newBox = new TowerBox(data, data
											.getSelectedTower().getX(), data
											.getSelectedTower().getY(),
											data.getSelectedTower());
									data.setCurrentBox(newBox);
								}
							} else {
								if (data.getCurrentBox() != null
										&& !data.getCurrentBox().isAt(
												data.getMouse().x,
												data.getMouse().y)) {
									data.setSelectedTower(-1);
									data.removeCurrentBox();
									System.out.println("nothing selected.");
								}
							}
						}
					}
				}

				/*
				 * END OF LEFT MOUSE PRESSED
				 */

				/*
				 * RIGHT MOUSE PRESSED
				 */
				RIGHT_MOUSE: if (rightMouseReleased) {
					rightMouseReleased = false;

					boolean //
					isCreatingPath = data.isCreatingPath(), //
					boxExists = data.getCurrentBox() != null, //
					isMouseInsideBox = Auxi.collides(data.getMouse(),
							data.getCurrentBox());

					Elemento selectedElement = data.getSelectedElement();

					// GET TOWER AT MOUSE
					Tower towerAtMouse = null;
					CYCLE: for (Tower t : data.getTowerListClone()) {
						if (t.isAt(data.getMouse().x, data.getMouse().y)) {
							towerAtMouse = t;
							break CYCLE;
						}
					}

					/*
					 * CREATE TOWER
					 */

					if (isCreatingPath) {
						data.showMessage("Wait for path to be finished!",
								false, Data.COLOR_GRAY);
						break RIGHT_MOUSE;
					}
					if (isMouseInsideBox) {
						break RIGHT_MOUSE;
					} else {
						if (boxExists) {
							data.removeCurrentBox();
							break RIGHT_MOUSE;
						}
					}

					if (towerAtMouse == null) {

						if (selectedElement == Elemento.LIFE
								&& Elemento.LIFE.getCost() <= data.getShards()) {

							Tower toAdd = new Tower(
									data,//
									32
											* (int) Math.round(data.getMouse().x / 32)
											+ (data.getSelectedElement()
													.getSize() / 2), //
									32
											* (int) Math.round(data.getMouse().y / 32)
											+ (data.getSelectedElement()
													.getSize() / 2), //
									data.getSelectedElement());

							boolean positionFree = true;
							for (Tower t : data.getTowerListClone()) {
								if (Auxi.collides(t, toAdd)) {
									positionFree = false;
								}
							}
							for (PathMark p : data.getPathMarkListClone()) {
								if (Auxi.collides(p, toAdd)) {
									positionFree = false;
								}
							}

							if (positionFree) {
								data.addDrawableObject(toAdd);
								data.addDrawableObject(new SplashAnimation(
										data, toAdd.getX() - 32,
										toAdd.getY() - 32, "green_circle"));
							}

							System.out.println(selectedElement + "created.");
						}
						break RIGHT_MOUSE;
					}

					/*
					 * UPGRADE TOWER
					 */

					if (towerAtMouse.getGear() == 3
							&& data.getSelectedElement().equals(Elemento.LIFE)) {
						data.showMessage("Totem Gear Already at Maximum",
								false, Data.COLOR_RED);
					} else {
						towerAtMouse.upgrade(data.getSelectedElement());
					}
				}
				/*
				 * END OF RIGHT MOUSE PRESSED
				 */

				// CREATE ENEMIES
				if (data.getCurrentWave() != null) {
					data.getCurrentWave().decrementTimer();
					if (data.getCurrentWave().getTimer() <= 0) {
						Enemy toAdd = data.getCurrentWave().pop();
						if (toAdd != null) {
							data.addDrawableObject(toAdd);
							data.getCurrentWave().resetTimer();
						} else {
							System.out.println("Wave ended");
							data.setCurrentWave(null);
						}
					}
				}

				// RUN Statics
				for (DrawableObject o : data.getStaticListClone()) {
					// STATIC STEP
					o.nextImage();
					if (mouseIsOnTop(data, o)) {
						if (o instanceof Button) {
							data.setDrawOnMouse(true);
							data.setStringToDraw(((Button) o)
									.getStringToDrawWhenMouseOver());
						}
					}
				}

				// RUN TOWERS
				for (Tower t : data.getTowerListClone()) {
					t.step();
				}

				// RUN ENEMIES
				for (Enemy e : data.getEnemyListClone()) {
					// ENEMY STEP
					e.step();
					if (mouseIsOnTop(data, e)) {
						data.setDrawOnMouse(true);
						data.setStringToDraw(e.getClass().getSimpleName());
					}
					if (e.getType().equals(EnemyType.PATHMAKER)) {
						boolean collisionFree = true;
						for (PathMark a : data.getPathMarkListClone()) {
							if (Auxi.collides(e, a)) {
								collisionFree = false;
							}
						}
						if (collisionFree) {
							data.addDrawableObject(new PathMark(data, e.getX(),
									e.getY()));
						}
					}
				}

				// RUN Projectiles
				for (ProjectileParent p : data.getProjectileListClone()) {
					p.nextImage();
					p.parentStep();
					p.step();
				}

				// RUN splashes
				for (Splash s : data.getSplashListClone()) {
					s.step();
				}

				panel.repaint();
				sleep(1000 / Data.FREQUENCY);
			}
		} catch (InterruptedException e) {
			System.err.println("Main Thread Interrupted");
		}
	}

	public static boolean mouseIsOnTop(Data aData, DrawableObject o) {
		return aData.getMouse().x > o.getX1() //
				&& aData.getMouse().x < (o.getX1() + o.getWidth()) //
				&& aData.getMouse().y > o.getY1()//
				&& aData.getMouse().y < (o.getY1() + o.getHeight());

	}

	private void createTowerButton(int x, int y, String element)
			throws IOException {
		data.addDrawableObject(new TowerButton(data, x, y, Elemento
				.valueOf(element.toUpperCase())));
	}

	public MyPanel getPanel() {
		return panel;
	}

}
