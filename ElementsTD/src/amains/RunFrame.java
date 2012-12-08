package amains;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import classes.main.Control;
import classes.main.Data;

public class RunFrame {

	JFrame frame;

	public static void main(String[] args) {
		new RunFrame();
	}

	public RunFrame() {
		try {

			// FRAME
			frame = new JFrame("ElementsTD");
			frame.setSize(Data.WINDOW_SIZE);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setLayout(new BorderLayout());
			frame.setResizable(false);
			// Auxi.setInvisibleCursor(frame);

			Control control = new Control();

			// control.getPanel().setSize(Data.WINDOW_SIZE);
			// Auxi.setInvisibleCursor(control.getPanel());
			control.getPanel().setVisible(true);
			frame.add(control.getPanel());
			// frame.pack();
			frame.setVisible(true);
			control.getPanel().resetImage();

			control.start();

		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane
					.showMessageDialog(null, "IOException.\n" + e.toString());
		}
	}
}
