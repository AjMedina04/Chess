package piece;

import main.GamePanel;

public class Queen extends Piece {

	public Queen(int color, int col, int row) {
		super(color, col, row);
		// Set different images for white and black
		if (color == GamePanel.getWhite()) {
			setImage(getImage("/piece/w-queen"));
		} else {
			setImage(getImage("/piece/b-queen"));
		}
	}

}
