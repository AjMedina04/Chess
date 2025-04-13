package piece;

import main.GamePanel;
/**
 * Lead Author(s):Arturo Medina
 *         References: Morelli, R., & Walde, R. (2016). Java, Java, Java:
 *         Object-Oriented Problem Solving. Retrieved from
 *         https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *         This project was informed by a tutorial from RyiSnow on building a chess game in Java
 *         **/
public class Pawn extends Piece {

	public Pawn(int color, int col, int row) {
		super(color, col, row);
		// Set different images for white and black
		if (color == GamePanel.getWhite()) {
			setPieceImage(getPieceImage("/piece/w-pawn"));
		} else {
			setPieceImage(getPieceImage("/piece/b-pawn"));
		}
	}

}
