package classes.picture.GUI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import javax.swing.JPanel;

import classes.main.Auxi;
import classes.main.Data;
import classes.objects.DrawableObject;
import classes.objects.PathMark;
import classes.objects.Tower;
import classes.objects.enemies.Enemy;
import classes.objects.enemies.EnemyType;
import classes.objects.enemies.Path;
import classes.objects.enemies.Wave;
import classes.objects.projectiles.ProjectileBlast;
import classes.objects.projectiles.ProjectileParent;
import classes.picture.GUI.towerGUI.TowerBox;
import classes.picture.GUI.towerGUI.TowerButton;
import classes.picture.splashes.Splash;
import classes.picture.splashes.SplashAnimation;
import classes.picture.splashes.SplashParticle;
import classes.picture.splashes.SplashText;

public class MyPanel extends JPanel {

	private static final long serialVersionUID = Data.SERIAL_VERSION;
	private static final Image SHARD_IMAGE = Data.getAnimation("shard")
			.getImage(0);

	private Font fontMonospaced, fontMonospacedSmall;

	private Data data;

	private Image bufferImage;
	private Graphics bufferGraphics;
	private double shieldImageNumber;
	private int linkColorInt;

	public void resetImage() {
		// clean up the previous image
		if (bufferGraphics != null) {
			bufferGraphics.dispose();
			bufferGraphics = null;
		}
		if (bufferImage != null) {
			bufferImage.flush();
			bufferImage = null;
		}
		System.gc();
		bufferImage = createImage(getSize().width, getSize().height);
		bufferGraphics = bufferImage.getGraphics();

	}

	public MyPanel(Data aData) {

		fontMonospaced = Data.font.deriveFont(18F);
		fontMonospacedSmall = Data.font.deriveFont(12F);
		shieldImageNumber = 0;
		linkColorInt = 0;
		this.data = aData;
	}

	@Override
	public void paint(Graphics g) {
		update(g);
	}

	@Override
	public void update(Graphics g) {
		if (bufferGraphics != null) {
			bufferGraphics.clearRect(0, 0, getSize().width, getSize().height);
			paintBuffer(bufferGraphics);
			g.drawImage(bufferImage, 0, 0, this);
		}
	}

	public void paintBuffer(Graphics g) {

		g.setColor(Data.COLOR_BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());

		for (int i = 32; i <= 704; i += 32) {
			g.setColor(Data.COLOR_GRID);
			g.drawLine(32, i, 992, i);
		}
		for (int i = 32; i <= 992; i += 32) {
			g.setColor(Data.COLOR_GRID);
			g.drawLine(i, 32, i, 704);
		}

		g.setColor(Data.COLOR_BACKGROUND);
		g.fillRect(0, 0, getWidth(), 64);

		// DRAW PATH
		for (PathMark p : data.getPathMarkListClone()) {
			drawImage(p, g);
		}

		{// DRAW TOWERS
			for (Tower t : data.getTowerListClone()) {
				drawImage(t, g);

				if (t.getMaxReload() > 10) {
					g.setColor(Data.COLOR_RELOAD_BAR);
					g.fillRect((int) t.getX1(), (int) (t.getY1() + t
							.getHeight()),
							(int) (t.getReload() * t.getWidth() / t
									.getMaxReload()), 2);
				}
				if (t.getChargesAvaliable() > 0) {
					g.setColor(Data.COLOR_CHARGED_BAR);
					g.fillRect((int) t.getX1(), (int) (t.getY1() - 2 + t
							.getHeight()),
							(int) (t.getChargesAvaliable() * t.getWidth() / t
									.getMaxCharge()), 2);
				} else if (t.getCharge() > 0) {
					g.setColor(Data.COLOR_CHARGING_BAR);
					g.fillRect((int) t.getX1(), (int) (t.getY1() - 2 + t
							.getHeight()),
							(int) (t.getCharge() * t.getWidth() / t
									.getMaxCharge()), 2);

				}
				if (t.mouseOn()) {
					if (t.getRange() > 0) {
						g.setColor(Data.COLOR_RANGE);
						g.drawOval((int) (t.getX() - t.getRange()),
								(int) (t.getY() - t.getRange()),
								(int) (t.getRange() * 2),
								(int) (t.getRange() * 2));
					}

				}
			}

			// linkColorInt = (linkColorInt > 255 * 2) ? 0 : linkColorInt++;
			linkColorInt = (linkColorInt + 1) % (255 * 2);
			int colorInt = (linkColorInt <= 255) ? linkColorInt
					: 255 - (linkColorInt - 255);

			{// DRAW LINKS
				for (Tower t2 : data.getTowerListClone()) {
					if (t2.mouseOn()) {
						// g.setColor(Data.COLOR_LINK);

						g.setColor(//
						new Color(colorInt, 255, (int) (Math.random() * 255)));
						for (Iterator<Entry<Long, Point>> iterator = t2
								.getLinks().entrySet().iterator(); iterator
								.hasNext();) {
							Point p = iterator.next().getValue();
							// g.setColor(new Color(1f, 0f, 0f, 0.5f));
							g.drawLine(
									(int) (t2.getX() - 2 + 4 * Math.random()),
									(int) (t2.getY() - 2 + 4 * Math.random()),
									(int) (p.x - 2 + 4 * Math.random()),
									(int) (p.y - 2 + 4 * Math.random()));
							// g.setColor(new Color(0f, 1f, 0f, 0.5f));
							g.drawLine(
									(int) (t2.getX() - 2 + 4 * Math.random()),
									(int) (t2.getY() - 2 + 4 * Math.random()),
									(int) (p.x - 2 + 4 * Math.random()),
									(int) (p.y - 2 + 4 * Math.random()));
							// g.setColor(new Color(0f, 0f, 1f, 0.5f));
							g.drawLine(
									(int) (t2.getX() - 2 + 4 * Math.random()),
									(int) (t2.getY() - 2 + 4 * Math.random()),
									(int) (p.x - 2 + 4 * Math.random()),
									(int) (p.y - 2 + 4 * Math.random()));
						}
					}
				}
			}
		}

		LinkedList<ProjectileParent> tempProjectileList = data
				.getProjectileListClone();
		for (ProjectileParent p : tempProjectileList) {
			drawImage(p, g);
		}

		{// DRAW ENEMIES
			LinkedList<Enemy> tempEnemyList = data.getEnemyListClone();

			Collections.sort(tempEnemyList, new Comparator<Enemy>() {
				@Override
				public int compare(Enemy e1, Enemy e2) {
					return (int) (Math.signum(e1.getY() - e2.getY()));
				}
			});
			shieldImageNumber += 0.5;
			if (shieldImageNumber >= 61) {
				shieldImageNumber = 0;
			}
			for (Enemy e : tempEnemyList) {
				drawImage(e, g);
				if (!e.getType().equals(EnemyType.PATHMAKER)) {
					g.setColor(Data.COLOR_GRAY);
					g.fillRect((int) (e.getX1()), (int) (e.getY1() - 2),
							(int) (e.getWidth()), -4);
					g.setColor(new Color(//
							255 - (int) Math.max(0,
									e.getHealth() / e.getMaxHealth() * 255),//
							(int) Math.max(0, e.getHealth() / e.getMaxHealth()
									* 255),//
							0));
					g.drawRect((int) e.getX1(), (int) e.getY1() - 4, Math.max(
							0, (int) (e.getWidth() * e.getHealth() / e
									.getMaxHealth())), 3);

					g.fillRect((int) e.getX1(), (int) e.getY1() - 4, Math.max(
							0, (int) (e.getWidth() * e.getShield() / e
									.getMaxShield())), 3);
				}

				if (e.getType() != EnemyType.PATHMAKER) {
					if (e.getShield() > 0) {
						drawImage("shield", (int) shieldImageNumber,
								e.getX() - 32, e.getY() - 32, g);
					}
				}
			}
		}
		/*
		 * TOWER BOX
		 */

		if (data.getCurrentBox() != null) {
			TowerBox box = data.getCurrentBox();
			drawImage(box.getCurrentImage(), (int) box.getX1(),
					(int) box.getY1(), g);
		}

		{// DRAW ELEMENT BUTTONS
			Point2D yellowPoint = null;
			Dimension yellowDimension = null;
			LinkedList<DrawableObject> tempStaticList = data
					.getStaticListClone();
			for (DrawableObject p : tempStaticList) {
				drawImage(p, g);
				if (p instanceof TowerButton) {
					if (data.getSelectedElement() == ((TowerButton) p)
							.getElement()) {
						yellowPoint = new Point2D.Double((int) p.getX1() - 1,
								(int) p.getY1() - 1);
						yellowDimension = new Dimension(p.getWidth() + 2,
								p.getHeight() + 2);
					}
					if (data.getSelectedElement() != ((TowerButton) p)
							.getElement()) {
						g.setColor(Data.COLOR_GRAY_OUT_BUTTONS);
						g.fillRect((int) p.getX1(), (int) p.getY1(),
								p.getWidth(), p.getHeight());
					}
				}
			}
			if (yellowPoint != null && yellowPoint != null) {
				g.setColor(Data.COLOR_SELECTED_TOWER_BUTTON);
				g.drawRect((int) yellowPoint.getX(), (int) yellowPoint.getY(),
						(int) yellowDimension.getWidth(),
						(int) yellowDimension.getHeight());
			}
		}

		/*
		 * 
		 */

		drawImage(SHARD_IMAGE, 188, 2, g);
		g.setColor(Data.COLOR_TOOLTIP_TEXT);
		g.setFont(fontMonospaced);
		g.drawString("Shards: " + data.getShards(), 234, 38);

		/*
		 * 
		 */

		int infoBarY = getHeight() - 26;
		int infoBarHeight = 18;

		g.setColor(Color.black);
		g.fillRect(0, getHeight() - 30, getWidth(), 18);
		g.setColor(Data.COLOR_TOOLTIP_TEXT);
		g.setFont(fontMonospaced);
		g.drawLine(0, infoBarY, getWidth(), infoBarY);
		g.drawLine(0, infoBarY + infoBarHeight, getWidth(), infoBarY
				+ infoBarHeight);

		if (data.getDrawOnMouse()) {
			g.drawString(data.getStringToDraw(), 32, infoBarY + infoBarHeight
					- 2);
		}

		{// DRAW SPLASHES
			for (Splash s : data.getSplashListClone()) {

				if (s instanceof SplashText) {
					g.setFont(fontMonospacedSmall);
					g.setColor(((SplashText) s).getColor());
					g.drawString(((SplashText) s).getText(), (int) s.getX(),
							(int) s.getY());
				} else if (s instanceof SplashAnimation) {
					drawImage(s.getCurrentImage(), s.getX(), s.getY(), g);
				} else if (s instanceof SplashParticle) {
					drawImage(s.getCurrentImage(), s.getX() - s.getWidth() / 2,
							s.getY() - s.getHeight() / 2, g, 1, 1, 0,
							s.getAlpha(), true);
				}
			}
		}

		g.setColor(Data.COLOR_TOOLTIP_TEXT);
		// LINES OF MOUSE
		bufferGraphics.drawLine(0, data.getMouse().y, getWidth(),
				data.getMouse().y);
		bufferGraphics.drawLine(data.getMouse().x, 0, data.getMouse().x,
				getHeight());

		/*
		 * NEXT WAVE INFO
		 */

		{
			g.setFont(fontMonospaced);
			g.drawString("Wave Nr " + (data.getWaveNumber() + 1) + ":", 430, 22);

			Wave nextwave = data.getNextWave();

			if (nextwave != null) {

				int pos = 450;
				for (int i = 0; i < Wave.ENEMIE_NAMES.length; i++) {

					EnemyType enemyType = EnemyType
							.valueOf(Wave.ENEMIE_NAMES[i]);

					int nr = nextwave.getWaveType().enemies[i];

					if (nr > 0) {
						Animation anim = Data.getAnimation("enemies/"
								+ enemyType.toString().toLowerCase());
						Image a = anim.getImage(0);

						drawImage(a, pos + 20, 48, g, .8, .8, 0, 0.8f, false);

						g.setFont(fontMonospaced);

						g.drawString("" + nr, pos - 20, 48);
						int xx = data.getMouse().x;
						int yy = data.getMouse().y;

						if (yy > 28 && yy < 60) {
							if (xx > pos && xx < pos + 50) {
								drawEnemyFile(g, nextwave.getEnemyHealth(),
										enemyType, pos + 16, 64);
							}
						}
						pos += 64;
					}

				}

			}
		}
		g.setFont(fontMonospacedSmall);
		g.drawString("Enemy List size: " + data.getEnemyListClone().size(),
				850, 10);
		g.drawString("Tower List size: " + data.getTowerListClone().size(),
				850, 20);
		g.drawString("Projectile List size: "
				+ data.getProjectileListClone().size(), 850, 30);
		g.drawString("Splash List size: " + data.getSplashListClone().size(),
				850, 40);
		g.drawString("Statics List size: " + data.getStaticListClone().size(),
				850, 50);

		// HOW TO DRAW A SPIRAL
		// for (double i = 0; i < 100; i+=0.1) {
		// double xx = i * Math.cos(i), yy = i * Math.sin(i);
		// g.drawRect((int)(xx*10)+200, (int)(yy*10)+200, 1, 1);
		// }

	}

	private void drawEnemyFile(Graphics g, double mainHealth, EnemyType e,
			int i, int j) {

		Polygon p = new Polygon();
		int arrowWidth = 5, arrowHeight = 6, boxWidth = 150, boxHeight = 143;

		p.addPoint(i, j);
		p.addPoint(i + arrowWidth, j + arrowHeight);
		p.addPoint(i + boxWidth / 2, j + arrowHeight);
		p.addPoint(i + boxWidth / 2, j + arrowHeight + boxHeight);
		p.addPoint(i + -boxWidth / 2, j + arrowHeight + boxHeight);
		p.addPoint(i + -boxWidth / 2, j + arrowHeight);
		p.addPoint(i + -arrowWidth, j + arrowHeight);

		g.setColor(Data.COLOR_ENEMY_FILE);
		g.fillPolygon(p);

		g.setColor(Data.COLOR_ENEMY_FILE_TEXT);
		g.setFont(fontMonospacedSmall);
		g.drawPolygon(p);

		double health = e.health * mainHealth;

		int xx = i + 5 - boxWidth / 2, yy = j + arrowHeight + 15, nl = 17;
		g.drawString("Name: " + e.toString().toLowerCase(), xx, yy);
		yy += nl;
		g.drawString("Heatlh: " + (((int) (100 * health)) / 100), xx, yy);
		yy += nl;
		g.drawString("Worth: " + (((int) (100 * e.xp)) / 100), xx, yy);
		yy += nl + 5;
		g.drawString("resistances:", xx, yy);
		yy += nl;
		g.drawString(" - wind", xx, yy);
		if (e.wind != 0)
			g.drawString((((int) (100 * e.wind)) / 100) + "%", xx + 70, yy);
		yy += nl;
		g.drawString(" - fire", xx, yy);
		if (e.fire != 0)
			g.drawString((((int) (100 * e.fire)) / 100) + "%", xx + 70, yy);
		yy += nl;
		g.drawString(" - water", xx, yy);
		if (e.water != 0)
			g.drawString((((int) (100 * e.water)) / 100) + "%", xx + 70, yy);
		yy += nl;
		g.drawString(" - earth", xx, yy);
		if (e.earth != 0)
			g.drawString((((int) (100 * e.earth)) / 100) + "%", xx + 70, yy);
		yy += nl;
	}

	private void drawImage(String name, int imageNumber, double x, double y,
			Graphics g) {

		drawImage(Data.getAnimation(name).getImage(imageNumber), x, y, g);
	}

	private void drawImage(Image o, double x, double y, Graphics g,
			double scaleX, double scaleY, double angle, float alpha,
			boolean useX1Y1) {

		BufferedImage b = new BufferedImage(o.getWidth(this), o.getWidth(this),
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D gg = (Graphics2D) b.getGraphics();

		gg.scale(scaleX, scaleY);
		gg.rotate(angle, o.getWidth(this) / 2.0, o.getHeight(this) / 2.0);
		gg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));
		gg.drawImage(o, 0, 0, this);

		if (useX1Y1) {
			g.drawImage(b, (int) x, (int) y, this);
		} else {
			g.drawImage(b, (int) x - (int) (b.getWidth() / 2), (int) y
					- (int) (b.getHeight() / 2), this);
		}
	}

	private void drawImage(Image o, double x, double y, Graphics g) {
		drawImage(o, x, y, g, 1, 1, 0, 1, true);
	}

	private void drawImage(DrawableObject o, Graphics g) {
		if (o instanceof ProjectileBlast) {
			double c = ((ProjectileBlast) o).getCounter();
			double x = (o.getWidth() * c / 2);
			double y = (o.getHeight() * c / 2);
			if (!o.isRemoved())
				drawImage(o.getCurrentImage(), (int) o.getX() - x,
						(int) o.getY() - y, g, c, c, -o.getAngle(),
						o.getAlpha(), true);
		} else {
			if (!o.isRemoved())
				drawImage(o.getCurrentImage(),
						(int) (o.getX() - o.getWidth() / 2),
						(int) (o.getY() - o.getHeight() / 2), g, 1, 1,
						-o.getAngle(), o.getAlpha(), true);
		}
	}
}
