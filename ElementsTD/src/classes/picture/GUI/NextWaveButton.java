package classes.picture.GUI;

import classes.main.Data;

public class NextWaveButton extends Button {

	public NextWaveButton(Data data, int x, int y) {
		super(data, x, y);
		setImages("buttons/next_wave");
	}

	@Override
	public void pressed() {
		if (getData().getCurrentWave() == null && !getData().isCreatingPath()) {
			getData().sendNextWave();
		}

	}

	@Override
	public String getStringToDrawWhenMouseOver() {
		return "Press to send the Next Wave";
	}
}
