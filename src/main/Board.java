package main;

import java.awt.Color;
import java.awt.Graphics2D;

public class Board {

	private final int MAX_COL = 8;
	private final int MAX_ROW = 8;
	private static final int SQUARE_SIZE = 100;
	private static final int HALF_SQUARE_SIZE = getSquareSize() / 2;

	public void draw(Graphics2D boardGraphics) {

		int switchSquareColor = 0;
		// Nested for loop to create the grid seen on a chess board
		for (int row = 0; row < MAX_ROW; row++) {

			for (int col = 0; col < MAX_COL; col++) {
				// Alternate colors that are used by boardGraphics for each square
				if (switchSquareColor == 0) {
					boardGraphics.setColor(new Color(119, 149, 86));
					switchSquareColor = 1;
				} else {
					boardGraphics.setColor(new Color(235, 236, 208));
					switchSquareColor = 0;
				}
				// Actually draws the grid with correct colors and size
				boardGraphics.fillRect(col * getSquareSize(), row * getSquareSize(), getSquareSize(), getSquareSize());
			}

			// Needed to alternate colors for the next row
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
}
