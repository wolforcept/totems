package classes.picture.GUI.towerGUI;

import classes.main.Data;
import classes.picture.GUI.Button;

public class LockButton extends Button {

	private boolean unavaliable;

	public LockButton(Data data, double x, double y, boolean aUnavaliable) {
		super(data, x, y);
		unavaliable = aUnavaliable;

		if (unavaliable) {
			setImages("buttons/lockgray");
		} else {
			if (getData().getSelectedTower().getLock()) {
				setImages("buttons/locktrue");
			} else {
				setImages("buttons/lockfalse");
			}
		}
	}

	@Override
	public void pressed() {
		if (!unavaliable) {
			getData().getSelectedTower().toggleLock();
			if (getData().getSelectedTower().getLock()) {
				setImages("buttons/locktrue");
			} else {
				setImages("buttons/lockfalse");
			}
		}
	}

	@Override
	public String getStringToDrawWhenMouseOver() {
		return "Sell this Totem";
	}

}
