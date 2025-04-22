package piece;

/**
 * Lead Author(s):Arturo Medina References: Morelli, R., & Walde, R. (2016).
 * Java, Java, Java: Object-Oriented Problem Solving. Retrieved from
 * https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * This project was informed by a tutorial from RyiSnow on building a chess game
 * in Java
 **/
public class Rook extends Piece {

	public Rook(int color, int col, int row) {
		super(color, col, row);
		// Set different images for white and black
		if (isWhite(color)) {
			setPieceImage(getPieceImage("/piece/w-rook"));
		} else {
			setPieceImage(getPieceImage("/piece/b-rook"));
		}
	}


@Override
public boolean isLegalMove(int targetColumn, int targetRow) {
    // 1) Check that the path is a clear straight line
    if (!isStraightPathClear(targetColumn, targetRow)) {
        return false;
    }
    // 2) And that the destination is empty or holds an opponent
    return isDestinationValid(targetColumn, targetRow);
}
}