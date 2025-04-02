package piece;

import java.awt.image.BufferedImage;

import main.Board;

public class Piece {

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
		
	}
	
	public int getX(int col) {
		return col * Board.getSquareSize();
		
	}
	
	public int getY(int row) {
		return row * Board.getSquareSize();
	}
}

