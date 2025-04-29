package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Lead Author(s):Arturo Medina
 * 
 * References: Morelli, R., & Walde, R. (2016). Java, Java, Java:
 * Object-Oriented Problem Solving. Retrieved from
 * https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * 
 * RyiSnow. (2023, December 4). How to Code Chess in Java [Video]. YouTube.
 * https://www.youtube.com/watch?v=jzCxywhTAUI&t=4612s
 * 
 **/
public class Mouse extends MouseAdapter {

	private int pixelX, pixelY;
	private boolean pressed;

	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		pixelX = e.getX();
		pixelY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		pixelX = e.getX();
		pixelY = e.getY();
	}

	/**
	 * @return the pixelX
	 */
	public int getPixelX() {
		return pixelX;
	}

	/**
	 * @return the pixelY
	 */
	public int getPixelY() {
		return pixelY;
	}

	/**
	 * @return the pressed
	 */
	public boolean isPressed() {
		return pressed;
	}

}
