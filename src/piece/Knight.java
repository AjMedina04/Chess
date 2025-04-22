package piece;

/**
 * Lead Author(s):Arturo Medina References: Morelli, R., & Walde, R. (2016).
 * Java, Java, Java: Object-Oriented Problem Solving. Retrieved from
 * https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * This project was informed by a tutorial from RyiSnow on building a chess game
 * in Java
 **/
public class Knight extends Piece {

	public Knight(int color, int col, int row) {
		super(color, col, row);
		// Set different images for white and black
		if (isWhite(color)) {
			setPieceImage(getPieceImage("/piece/w-knight"));
		} else {
			setPieceImage(getPieceImage("/piece/b-knight"));
		}
	}

	@Override
	public boolean isLegalMove(int targetColumn, int targetRow) {
		// 1) Must stay on board
		if (!isWithinBoard(targetColumn, targetRow)) {
			return false;
		}

		// 2) Compute absolute deltas
		int deltaColumn = Math.abs(targetColumn - getPreviousCol());
		int deltaRow = Math.abs(targetRow - getPreviousRow());

		// 3) Must be an L‑shape and destination must be valid
		if (isLShapeMove(deltaColumn, deltaRow) && isDestinationValid(targetColumn, targetRow)) {
			return true;
		}

		return false;
	}

	/**
	 * @return true if (deltaColumn, deltaRow) form a knight’s L‑shape: 2×1 or 1×2
	 */
	private boolean isLShapeMove(int deltaColumn, int deltaRow) {
		return (deltaColumn == 2 && deltaRow == 1) || (deltaColumn == 1 && deltaRow == 2);
	}
}
