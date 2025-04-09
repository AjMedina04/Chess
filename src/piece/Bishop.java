package piece;

import main.GamePanel;

public class Bishop extends Piece {

	public Bishop(int color, int col, int row) {
		super(color, col, row);
		// Set different images for white and black
		if (color == GamePanel.getWhite()) {
			setImage(getImage("/piece/w-bishop"));
		} else {
			setImage(getImage("/piece/b-bishop"));
		}

	}

}
