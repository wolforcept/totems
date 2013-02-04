package classes.picture.GUI;

import classes.main.Data;
import classes.picture.GUI.Button;

public class FancyGraphicsButton extends Button {

	public FancyGraphicsButton(Data data, double x, double y) {
		super(data, x, y);
		toggleImage(getData().isFancyGraphics());
	}

	@Override
	public void pressed() {
		getData().toggleFancyGraphics();
		toggleImage(getData().isFancyGraphics());
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
