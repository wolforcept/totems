package amains;

import java.io.IOException;

import javax.swing.JApplet;

import classes.main.Control;
import classes.main.Data;

public class RunApplet extends JApplet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() {

		try {

			setSize(Data.WINDOW_SIZE);

			Control control = new Control();

			control.getPanel().setSize(Data.WINDOW_SIZE);
			// Auxi.setInvisibleCursor(control.getPanel());
			control.getPanel().setVisible(true);
			add(control.getPanel());
			control.getPanel().resetImage();

			control.start();

			setVisible(true);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}