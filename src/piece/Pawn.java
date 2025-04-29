package piece;

/**
 * Lead Author(s):Arturo Medina References: Morelli, R., & Walde, R. (2016).
 * Java, Java, Java: Object-Oriented Problem Solving. Retrieved from
 * https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * This project was informed by a tutorial from RyiSnow on building a chess game
 * in Java
 **/
/**
 * The Pawn class represents a pawn chess piece and contains logic for
 * determining if a pawn's move is legal according to chess rules.
 */
public class Pawn extends Piece {

	/**
	 * Constructor for the Pawn piece. Sets the color, initial position, and
	 * appropriate image for the pawn.
	 * 
	 * @param color The color of the pawn
	 * @param col   The starting column of the pawn
	 * @param row   The starting row of the pawn
	 */
	public Pawn(int color, int col, int row) {
		super(color, col, row);
		// Set different images for white and black
		if (isWhite(color)) {
			setPieceImage(getPieceImage("/piece/w-pawn"));
		} else {
			setPieceImage(getPieceImage("/piece/b-pawn"));
		}
	}

	/**
	 * Determines if a move to (targetColumn, targetRow) is legal for this pawn.
	 * Checks for standard pawn moves: single forward, double forward from start,
	 * and diagonal capture.
	 * 
	 * @param targetColumn The column to move to
	 * @param targetRow    The row to move to
	 * @return true if the move is legal, false otherwise
	 */
	@Override
	public boolean isLegalMove(int targetColumn, int targetRow) {
		// Must stay on board
		if (!isWithinBoard(targetColumn, targetRow)) {
			return false;
		}
		int direction = isWhite(getPieceColor()) ? -1 : 1;
		int rowDifference = targetRow - getPreviousRow();
		int colDifference = targetColumn - getPreviousCol();

		// Single forward move: must be empty and one square ahead
		if (colDifference == 0 && rowDifference == direction) {
			return isDestinationValid(targetColumn, targetRow, false);
		}
		// Double forward move: only if pawn has not moved yet, both squares must be empty
		if (colDifference == 0 && rowDifference == 2 * direction && !hasMoved()) {
			int intermediateRow = getPreviousRow() + direction;
			return getCollidingPiece(targetColumn, intermediateRow) == null
					&& isDestinationValid(targetColumn, targetRow, false);
		}
		// Diagonal capture: must be one square diagonally forward, and capture opponent
		if (Math.abs(colDifference) == 1 && rowDifference == direction) {
			return isDestinationValid(targetColumn, targetRow, true);
		}
		// Illegal move: return false
		return false;
	}

	public boolean isDestinationValid(int targetCol, int targetRow, boolean isDiagonal) {
		setCollidingPiece(getCollidingPiece(targetCol, targetRow));
		if (isDiagonal) {
			// Diagonal move: must capture opponent
			return getCollidingPiece() != null && getCollidingPiece().getPieceColor() != getPieceColor();
		} else {
			// Forward move: must be empty
			return getCollidingPiece() == null;
		}
	}
}