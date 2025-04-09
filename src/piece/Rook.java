package piece;

import main.GamePanel;

public class Rook extends Piece {

	public Rook(int color, int col, int row) {
		super(color, col, row);
		// Set different images for white and black
		if (color == GamePanel.getWhite()) {
			setImage(getImage("/piece/w-rook"));
		} else {
			setImage(getImage("/piece/b-rook"));
		}
	}

}
