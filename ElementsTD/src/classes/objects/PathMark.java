package classes.objects;

import classes.main.Data;

public class PathMark extends DrawableObject {

	public PathMark(Data data, double x, double y) {
		super(data, 64 * Math.floor(x / 64) + 32, 64 * Math.floor(y / 64) + 32);
		setImages("pathmark");
	}

}
