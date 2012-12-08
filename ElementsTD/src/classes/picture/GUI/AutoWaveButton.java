package classes.picture.GUI;

import classes.main.Data;
import classes.picture.GUI.Button;

public class AutoWaveButton extends Button {

	public AutoWaveButton(Data data, double x, double y) {
		super(data, x, y);
		toggleImage(getData().isAutoWave());
	}

	@Override
	public void pressed() {
		getData().toggleAutoWaveLock();
		toggleImage(getData().isAutoWave());
	}

	@Override
	public String getStringToDrawWhenMouseOver() {
		return "After all enemies are gone, send in the next wave";
	}

	private void toggleImage(boolean isAutoWave) {
		if (isAutoWave) {
			setImages("buttons/autotrue");
			getData().sendWaveIfAuto();
		} else {
			setImages("buttons/autofalse");
		}
	}

}
