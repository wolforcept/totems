package classes.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import classes.objects.DrawableObject;

/**
 * Esta classe contem métodos auxiliares com cálculos
 * 
 * @author Miguel
 * 
 */

public class Auxi {

	public class Duo<T1, T2> {
		T1 o1;
		T2 o2;

		public Duo(T1 o1, T2 o2) {
			this.o1 = o1;
			this.o2 = o2;
		}
	}

	public class Trio<T1, T2, T3> {

		T1 o1;
		T2 o2;
		T3 o3;

		public Trio(T1 obj1, T2 obj2, T3 obj3) {
			o1 = obj1;
			o2 = obj2;
			o3 = obj3;
		}
	}

	/**
	 * Calcular a distancia entre dois pontos
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static double point_distance(double x1, double y1, double x2,
			double y2) {
		return (int) Math.hypot(Math.abs(x1 - x2), Math.abs(y1 - y2));
	}

	public static boolean collides(Point p, DrawableObject o) {
		if (o == null || o == null)
			return false;
		Rectangle rect = new Rectangle(
		//
				(int) (o.getX1()), (int) (o.getY1()),//
				(int) (o.getWidth()), (int) (o.getHeight()));
		return rect.contains(p);
	}

	public static boolean collides(DrawableObject o1, DrawableObject o2) {
		Rectangle rect1 = new Rectangle(
		//
				(int) (o1.getX1()), (int) (o1.getY1()),//
				(int) (o1.getWidth()), (int) (o1.getHeight()));

		Rectangle rect2 = new Rectangle(
		//
				(int) (o2.getX1()), (int) (o2.getY1()),//
				(int) (o2.getWidth()), (int) (o2.getHeight()));

		return rect1.intersects(rect2);
	}

	/**
	 * Calcular se um objecto colide com um determinado rectangulo
	 * 
	 * @param o1
	 * @param rect2
	 * @return
	 */
	// public static boolean collides(GameObject o1, Rectangle rect2) {
	// Rectangle rect1 = new Rectangle((int) Math.round(o1.getX())
	// - (int) Math.round(o1.getRadius() / 2), (int) Math.round(o1
	// .getY()) - (int) Math.round(o1.getRadius() / 2),
	// (int) Math.round(o1.getRadius()), (int) Math.round(o1
	// .getRadius()));
	//
	// return rect1.intersects(rect2);
	// }

	/**
	 * Calcular a direcçao de um primeiro ponto para um segundo
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static double point_direction(double x1, double y1, double x2,
			double y2) {
		return Math.atan2((y1 - y2), (x2 - x1));
	}

	/**
	 * Verificar se uma posição em campo se encontra livre. Para isso necessita
	 * de receber um mapa como argumento
	 * 
	 * @param x
	 * @param y
	 * @param props
	 * @return
	 */
	// public static boolean point_free(double x, double y, RoomProperties
	// props) {
	// for (Troop aTroop : props.getTroopList()) {
	// if (point_distance(aTroop.getX(), aTroop.getY(), x, y) < aTroop
	// .getRadius()) {
	// return false;
	// }
	// }
	// return true;
	// }

	/**
	 * Usado para Melhorar o texto, nomeadamente transformar o texto em
	 * Maiúsculas de um enumerado em algo mais apresentavel
	 * 
	 * @param string
	 * @return
	 */
	public static String beautifulText(String string) {
		String capitalLetter = "" + string.charAt(0);
		string = string.replaceAll("_", " ");
		string = string.toLowerCase();
		char[] a = string.toCharArray();
		for (int i = 0; i < a.length - 1; i++) {
			if (a[i] == ' ') {
				if (a[i + 1] >= 'a' && a[i + 1] <= 'z') {
					a[i + 1] = (char) ((int) a[i + 1] - (int) 'a' - (int) 'A');
				}
			}
		}

		string = new String(a);
		return capitalLetter.toUpperCase() + string.substring(1);
	}

	public static Color get_inverted_color(Color color) {
		int r, g, b;
		r = 255 - color.getRed();
		g = 255 - color.getGreen();
		b = 255 - color.getBlue();
		return new Color(r, g, b);
	}

	public static void setInvisibleCursor(Component c) {
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16,
				BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the Component.
		c.setCursor(blankCursor);

	}

	public static <T> void removeDuplicates(Collection<T> l, Comparator<T> c) {

		HashSet<T> s = new HashSet<T>();
		s.addAll(l);	
		l.clear();
		l.addAll(s);
	}
}
