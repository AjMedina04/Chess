package piece;

/**
 * Lead Author(s):Arturo Medina
 * 
 * References: Morelli, R., & Walde, R. (2016). Java, Java, Java:
 * Object-Oriented Problem Solving. Retrieved from
 * https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * 
 * RyiSnow. (2023, December 4). How to Code Chess in Java [Video]. YouTube.
 * https://www.youtube.com/watch?v=jzCxywhTAUI&t=4612s
 * 
 **/
public class King extends Piece {

	// Constructor for the King piece
	public King(int color, int col, int row) {
		super(color, col, row); // Call the superclass constructor to initialize position and color

		// Set image based on the piece's color
		if (isWhite(color)) {
			setPieceImage(getPieceImage("/piece/w-king")); // White king image
		} else {
			setPieceImage(getPieceImage("/piece/b-king")); // Black king image
		}
	}

	// Determine if the move to the target square is legal for a King
	@Override
	public boolean isLegalMove(int targetCol, int targetRow) {

		// Check if the target square is within board boundaries
		if (isWithinBoard(targetCol, targetRow)) {

			// King can move one square in any direction
			boolean isAdjacentHorizontalOrVertical = Math.abs(targetCol - getPreviousCol())
					+ Math.abs(targetRow - getPreviousRow()) == 1;
			boolean isDiagonalMove = Math.abs(targetCol - getPreviousCol())
					* Math.abs(targetRow - getPreviousRow()) == 1;

			if (isAdjacentHorizontalOrVertical || isDiagonalMove) {

				// Check if the destination square is either empty or occupied by an opponent's
				// piece
				if (isDestinationValid(targetCol, targetRow)) {
					return true;
				}
			}
		}
		return false; // Move is not legal
	}
}
