package piece;

/**
 * Lead Author(s):Arturo Medina References: Morelli, R., & Walde, R. (2016).
 * Java, Java, Java: Object-Oriented Problem Solving. Retrieved from
 * https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * This project was informed by a tutorial from RyiSnow on building a chess game
 * in Java
 **/
public class Queen extends Piece {

	public Queen(int color, int col, int row) {
		super(color, col, row);
		// Set different images for white and black
		if (isWhite(color)) {
			setPieceImage(getPieceImage("/piece/w-queen"));
		} else {
			setPieceImage(getPieceImage("/piece/b-queen"));
		}
	}

	@Override
	public boolean isLegalMove(int targetColumn, int targetRow) {
		return (isStraightPathClear(targetColumn, targetRow) || isDiagonalPathClear(targetColumn, targetRow))
				&& isDestinationValid(targetColumn, targetRow);
	}
}
