package classes.picture.GUI.towerGUI;

import classes.main.Data;
import classes.main.Elemento;
import classes.picture.GUI.Button;

public class TowerButton extends Button {

	private Elemento element;

	public TowerButton(Data data, double x, double y, Elemento element) {
		super(data, x, y);
		setImages("buttons/" + element.toString().toLowerCase());
		this.element = element;
	}

	public String getStringToDrawWhenMouseOver() {
		return element.toString();
	}

	public void pressed() {
		System.out.println(element + " selected.");
		getData().setSelectedElement(element);
	}

	public Elemento getElement() {
		return element;
	}

}
