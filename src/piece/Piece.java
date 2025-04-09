package piece;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Board;

public abstract class Piece {

	private BufferedImage image;
	private int x, y;
	private int col, row, preCol, preRow;
	private int color;

	public Piece(int color, int col, int row) {

		this.color = color;
		this.col = col;
		this.row = row;
		x = getX(col);
		y = getY(row);
		preCol = col;
		preRow = row;

	}

	// returns piece image requested from our resources
	public BufferedImage getImage(String imagePath) {
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
		} catch (IOException e) { // catch if a unexpected request comes in
			e.printStackTrace();
		}
		return image;
	}

	// Get piece position
	public int getX(int col) {
		return col * Board.getSquareSize();

	}

	public int getY(int row) {
		return row * Board.getSquareSize();
	}

	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
