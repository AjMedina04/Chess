package piece;

import main.GamePanel;

public class King extends Piece {

	public King(int color, int col, int row) {
		super(color, col, row);
		// Set different images for white and black
		if (color == GamePanel.getWhite()) {
			setImage(getImage("/piece/w-king"));
		} else {
			setImage(getImage("/piece/b-king"));
		}
	}

}
