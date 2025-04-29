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
public class Bishop extends Piece {

	public Bishop(int color, int col, int row) {
		super(color, col, row);
		// Set different images for white and black
		if (isWhite(color)) {
			setPieceImage(getPieceImage("/piece/w-bishop"));
		} else {
			setPieceImage(getPieceImage("/piece/b-bishop"));
		}

	}

	@Override
	public boolean isLegalMove(int targetColumn, int targetRow) {
		return isDiagonalPathClear(targetColumn, targetRow) && isDestinationValid(targetColumn, targetRow);
	}
}