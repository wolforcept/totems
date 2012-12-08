package classes.objects.enemies;

import java.awt.Point;
import java.util.LinkedList;

public class Path {

	public static final int LEVEL1 = 1;
	private LinkedList<Point> path;
	private Point start;

	public Path(int type) {
		path = new LinkedList<Point>();

		switch (type) {
		case LEVEL1:
			setStart(-1, 4);
			addPoint(3, 4);
			addPoint(3, 1);
			addPoint(12, 1);
			addPoint(12, 8);
			addPoint(4, 8);
			addPoint(4, 5);
			addPoint(16, 5);
			break;

		default:
			break;
		}

	}

	private void setStart(int i, int j) {
		start = new Point(32 + i * 64, 64 + j * 64+32);
	}

	private void addPoint(int i, int j) {
		path.add(new Point(32 + i * 64, 64 + j * 64+32));
	}

	public Point getStart() {
		return start;
	}

	public Point nextPoint() {
		if (!path.isEmpty()) {
			return path.pop();
		} else {
			return null;
		}
	}

}
