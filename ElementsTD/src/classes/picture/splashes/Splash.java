package classes.picture.splashes;

import classes.main.Data;
import classes.objects.DrawableObject;

public abstract class Splash extends DrawableObject {

	public Splash(Data data, double x, double y) {
		super(data, x, y);
	}

	public abstract void step();

}
