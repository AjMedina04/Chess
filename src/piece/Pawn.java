package piece;

import java.util.List;

/**
 * Lead Author(s):Arturo Medina 
 * 
 * References: Morelli, R., & Walde, R. (2016).
 * Java, Java, Java: Object-Oriented Problem Solving. Retrieved from
 * https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * 
 * RyiSnow. (2023, December 4). How to Code Chess in Java [Video]. YouTube.
 * https://www.youtube.com/watch?v=jzCxywhTAUI&t=4612s
 * 
 **/
/**
 * The Pawn class represents a pawn chess piece and contains logic for
 * determining if a pawn's move is legal according to chess rules.
 */
// Pawn is-a Piece, inherits common piece behavior, position, movement,
// rendering
public class Pawn extends Piece {

	// A Pawn has-a enPassantEligible (flag indicating vulnerability to en passant
	// capture, true for one turn after a two-square advance)
	private boolean enPassantEligible = false;

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
		// Double forward move: only if pawn has not moved yet, both squares must be
		// empty
		if (colDifference == 0 && rowDifference == 2 * direction && !hasMoved()) {
			enPassantEligible = true;
			int intermediateRow = getPreviousRow() + direction;
			return getCollidingPiece(targetColumn, intermediateRow) == null
					&& isDestinationValid(targetColumn, targetRow, false);
		}
		// Diagonal capture: must be one square diagonally forward, and capture opponent
		if (Math.abs(colDifference) == 1 && rowDifference == direction) {
			// Normal diagonal capture
			if (isDestinationValid(targetColumn, targetRow, true)) {
				return true;
			}
			// En passant capture
			if (getCollidingPiece(targetColumn, getPreviousRow()) instanceof Pawn
					&& ((Pawn) getCollidingPiece(targetColumn, getPreviousRow())).isEnPassantEligible()) {
				return true;
			}

		}
		// Illegal move: return false
		return false;
	}

	/**
	 * Determines if the destination square at (targetCol, targetRow) is valid for
	 * the pawn, based on the type of move (forward or diagonal).
	 *
	 * For forward moves (isDiagonal == false), the destination must be empty. For
	 * diagonal moves (isDiagonal == true), the destination must contain an
	 * opponent's piece.
	 *
	 * @param targetCol  The target column to move to
	 * @param targetRow  The target row to move to
	 * @param isDiagonal True if the move is diagonal (capture), false if forward
	 * @return true if the destination is valid for the pawn's move type, false
	 *         otherwise
	 */
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

	public boolean shouldPromote() {
		int promotionRow = isWhite(getPieceColor()) ? 0 : 7;
		return getBoardRow() == promotionRow;
	}

	/**
	 * Returns true if this pawn is vulnerable to en passant capture.
	 */
	public boolean isEnPassantEligible() {
		return enPassantEligible;
	}

	/**
	 * Clears en passant vulnerability. Called after opponent's turn if no capture
	 * occurred.
	 */
	public void clearEnPassantEligible() {
		enPassantEligible = false;
	}

	/**
	 * Performs en passant capture: if this pawn moved diagonally by one and an
	 * adjacent pawn is eligible, removes it.
	 * 
	 * @param displayPieces the list of pieces to update
	 * @param origCol       original column before the move
	 * @param origRow       original row before the move
	 */
	public void enPassant(List<Piece> displayPieces, int origCol, int origRow) {
		// En passant: diagonal move by one onto empty square
		if (Math.abs(getBoardCol() - origCol) == 1 && Math.abs(getBoardRow() - origRow) == 1) {
			Piece adjacent = getCollidingPiece(getBoardCol(), origRow);
			if (adjacent instanceof Pawn && ((Pawn) adjacent).isEnPassantEligible()) {
				displayPieces.remove(adjacent);
			}
		}
	}

	/**
	 * Promotes this pawn by replacing it with a queen in the provided lists.
	 */
	public void promote(List<Piece> displayPieces) {
		// Replace this pawn with a queen in the display list; boardState will be synced
		// later
		displayPieces.remove(this);
		Piece queen = new Queen(getPieceColor(), getBoardCol(), getBoardRow());
		displayPieces.add(queen);
	}
}