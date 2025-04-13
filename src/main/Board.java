
package main;

import java.awt.Color;
import java.awt.Graphics2D;
/**
 * Lead Author(s):Arturo Medina
 *         References: Morelli, R., & Walde, R. (2016). Java, Java, Java:
 *         Object-Oriented Problem Solving. Retrieved from
 *         https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *         This project was informed by a tutorial from RyiSnow on building a chess game in Java.
 *         **/
public class Board {

	private final int MAX_COL = 8;
	private final int MAX_ROW = 8;
	private static final int SQUARE_SIZE = 100; // each side is 100 pixels
	private static final int HALF_SQUARE_SIZE = SQUARE_SIZE / 2;

	public void draw(Graphics2D boardGraphics) {

		int switchSquareColor = 0;
		// Nested for loop to create the grid seen on a chess board
		for (int row = 0; row < MAX_ROW; row++) {

			for (int col = 0; col < MAX_COL; col++) {
				// Alternate colors that are used by boardGraphics for each square
				if (switchSquareColor == 0) {
					boardGraphics.setColor(new Color(119, 149, 86));
					switchSquareColor = 1; // alternate to other color
				} else {
					boardGraphics.setColor(new Color(235, 236, 208));
					switchSquareColor = 0; // alternate to other color
				}
				boardGraphics.fillRect(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE); // paint board
																										// to
																										// boardGraphics
			}

			// After each row alternate colors
			if (switchSquareColor == 0) {
				switchSquareColor = 1;
			} else {
				switchSquareColor = 0;
			}

		}
	}

	/**
	 * @return the squareSize
	 */
	public static int getSquareSize() {
		return SQUARE_SIZE;
	}

	/**
	 * @return the halfSquareSize
	 */
	public static int getHalfSquareSize() {
		return HALF_SQUARE_SIZE;
	}
}
