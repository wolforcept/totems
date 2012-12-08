package classes.picture.GUI.towerGUI;

import classes.main.Data;
import classes.objects.DrawableObject;
import classes.objects.Tower;
import classes.picture.GUI.Button;

public class TowerBox extends Button {

	private SellButton sellButton,sell2Button;
	private Tower tower;
	private LockButton lockButton;
	private FocusButton focusButton;

	@SuppressWarnings("incomplete-switch")
	public TowerBox(Data data, double x, double y, Tower aTower) {
		super(data, x, y);

		setImages("buttons/box");
		tower = aTower;

		setX(getX() + getWidth() / 2);
		setY(getY() + getHeight() / 2);

		if ((getX() + getWidth()) > Data.WINDOW_SIZE.width) {
			addX(-getWidth());
		}

		if ((getY() + getHeight()) > Data.WINDOW_SIZE.height) {
			addY(-getHeight());
		}

		sellButton = new SellButton(data, getX1() + 35, getY1() + 20);
		data.addDrawableObject(sellButton);

		boolean lockUnavaliable = false;
		boolean focusUnavaliable = false;

		switch (tower.getElement()) {
		case LIFE:
		case MUD:
		case EARTH:
			lockUnavaliable = true;
			focusUnavaliable = true;
			break;
		case TORRENT:
		case WATER:
			lockUnavaliable = true;
			break;
		case FIRE:
		case BLAZE:
			focusUnavaliable = true;
			break;
		}

		lockButton = new LockButton(data, getX1() + 92, getY1() + 20,
				lockUnavaliable);
		data.addDrawableObject(lockButton);

		focusButton = new FocusButton(data, getX1() + 35, getY1() + 50,
				focusUnavaliable);
		data.addDrawableObject(focusButton);
		
		sell2Button = new SellButton(data, getX1() + 92, getY1() + 50);
		data.addDrawableObject(sell2Button);

	}

	public void clear() {
		sellButton.remove();
		sell2Button.remove();
		lockButton.remove();
		focusButton.remove();
		remove();
	}

	@Override
	public void pressed() {}

	@Override
	public String getStringToDrawWhenMouseOver() {
		return "";
	}

	public DrawableObject getTower() {
		return tower;
	}

	public SellButton getSellButton() {
		return sellButton;
	}

	public LockButton getLockButton() {
		return lockButton;
	}

}
