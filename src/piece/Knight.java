package piece;

import main.GamePanel;

public class Knight extends Piece {

	public Knight(int color, int col, int row) {
		super(color, col, row);
		// Set different images for white and black
		if (color == GamePanel.getWhite()) {
			setImage(getImage("/piece/w-knight"));
		} else {
			setImage(getImage("/piece/b-knight"));
		}
	}

}
