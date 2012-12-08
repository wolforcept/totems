package classes.picture.GUI.towerGUI;

import classes.main.Data;
import classes.objects.Tower.TargetType;
import classes.picture.GUI.Button;

public class FocusButton extends Button {

	private boolean unavaliable;

	public FocusButton(Data data, double x, double y, boolean aUnavaliable) {
		super(data, x, y);

		unavaliable = aUnavaliable;

		if (unavaliable) {
			setImages("buttons/focusgray");
		} else {
			setFocusImage(getData().getSelectedTower().getFocus());
		}
	}

	@Override
	public void pressed() {
		if (!unavaliable) {
			getData().getSelectedTower().cycleFocus();
			setFocusImage(getData().getSelectedTower().getFocus());
		}
	}

	private void setFocusImage(TargetType focus) {
		switch (focus) {
		case HEALTHY:
			setImages("buttons/focushealthy");
			break;
		case UNHEALTHY:
			setImages("buttons/focusunhealthy");
			break;
		case FAST:
			setImages("buttons/focusfast");
			break;
		case SLOW:
			setImages("buttons/focusslow");
			break;
		case RANDOM:
			setImages("buttons/focusrandom");
			break;
		case BURN:
		default:
			System.err.println("FOCUSBUTTON.SETFOCUSIMAGE ERROR");
			break;
		}
	}

	@Override
	public String getStringToDrawWhenMouseOver() {
		return "Sell this Totem";
	}

}
