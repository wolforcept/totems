package classes.picture.GUI;

import classes.main.Data;
import classes.objects.DrawableObject;

public abstract class Button extends DrawableObject {

	public Button(Data data, double x, double y) {
		super(data, x, y);
	}

	public abstract void pressed();

	public abstract String getStringToDrawWhenMouseOver();

}
