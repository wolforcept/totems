package classes.picture.GUI;

import classes.main.Data;
import classes.picture.GUI.Button;

public class GraphicsButton extends Button {

	public GraphicsButton(Data data, double x, double y) {
		super(data, x, y);
		setImages("buttons/graphics");
	}

	@Override
	public void pressed() {
		getData().changeGraphicsQuality();
	}

	@Override
	public String getStringToDrawWhenMouseOver() {
		return "Graphical Quality";
	}

}
