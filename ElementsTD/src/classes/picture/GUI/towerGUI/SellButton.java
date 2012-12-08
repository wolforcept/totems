package classes.picture.GUI.towerGUI;

import classes.main.Data;
import classes.picture.GUI.Button;

public class SellButton extends Button {

	public SellButton(Data data, double x, double y) {
		super(data, x, y);
		setImages("buttons/sell");
	}

	@Override
	public void pressed() {
		if (getData().getSelectedTower() != null) {
			getData().getSelectedTower().sell();
		}
		getData().removeCurrentBox();
	}

	@Override
	public String getStringToDrawWhenMouseOver() {
		return "Sell this Totem";
	}

}
