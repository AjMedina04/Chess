package piece;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Board;
import main.GamePanel;

/**
 * Lead Author(s):Arturo Medina
 *         References: Morelli, R., & Walde, R. (2016). Java, Java, Java:
 *         Object-Oriented Problem Solving. Retrieved from
 *         https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *         This project was informed by a tutorial from RyiSnow on building a chess game in Java
 *         **/
/**
 * Abstract base class representing a chess piece. Handles common piece
 * functionality including movement validation, position tracking, collision
 * detection, and rendering.
 */
public abstract class Piece {

	private int pixelX, pixelY;
	private int boardCol, boardRow, previousCol, previousRow;
	private int pieceColor;
	private BufferedImage pieceImage;
	private Piece collidingPiece;
	private boolean moved;

	public Piece(int pieceColor, int boardCol, int boardRow) {

		this.pieceColor = pieceColor;
		this.setBoardCol(boardCol);
		this.setBoardRow(boardRow);
		setPixelX(convertColToX(boardCol));
		setPixelY(convertRowToY(boardRow));
		previousCol = boardCol;
		previousRow = boardRow;
	}

	// Returns image from resources
	public BufferedImage getPieceImage(String imagePath) {
		BufferedImage pieceImage = null;
		try {
			pieceImage = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
		} catch (IOException e) { // catch if a unexpected request comes in
			e.printStackTrace();
		}
		return pieceImage;
	}

	// Method to set each pieces image
	public void setPieceImage(BufferedImage pieceImage) {
		this.pieceImage = pieceImage;
	}

	// Method to draw each piece
	public void draw(Graphics2D pieceGraphics) {
		pieceGraphics.drawImage(pieceImage, getPixelX(), getPixelY(), Board.getSquareSize(), Board.getSquareSize(),
				null);
	}

	public int getIndex() {
		for (int index = 0; index < GamePanel.displayPieces.size(); index++) {
			if (GamePanel.displayPieces.get(index) == this) {
				return index;
			}
		}
		return 0;
	}

	public void updatePosition() {
		pixelX = convertColToX(boardCol);
		pixelY = convertRowToY(boardRow);
		previousCol = convertXToCol(pixelX);
		previousRow = convertYToRow(pixelY);
		moved = true;

	}

	public void resetPosition() {
		boardCol = getPreviousCol();
		boardRow = getPreviousRow();
		pixelX = convertColToX(boardCol);
		pixelY = convertRowToY(boardRow);

	}

	public abstract boolean isLegalMove(int targetCol, int targetRow);

	public boolean isWithinBoard(int targetCol, int targetRow) {
		if (targetCol >= 0 && targetCol <= 7 && targetRow >= 0 && targetRow <= 7) {
			return true;
		}
		return false;
	}

	public Piece getCollidingPiece(int targetCol, int targetRow) {
		for (Piece piece : GamePanel.displayPieces) {
			if (piece.boardCol == targetCol && piece.boardRow == targetRow && piece != this) {
				return piece;
			}
		}
		return null;
	}

	public boolean isDestinationValid(int targetCol, int targetRow) {

		collidingPiece = getCollidingPiece(targetCol, targetRow);

		// Empty square - can move freely
		if (getCollidingPiece() == null) { // Vacant square
			return true;
		}
		// Square has a piece - can only move if capturing an opponent's piece
		else {
			if (getCollidingPiece().pieceColor != this.pieceColor) { // Occupied square
				return true; // Can capture opponent's piece
			} else { //
				collidingPiece = null; // Can't capture own piece
			}
		}
		return false;
	}

	/**
	 * Returns true if this piece currently occupies the given board square.
	 */
	public boolean isSameSquare(int targetCol, int targetRow) {
		return boardCol == targetCol && boardRow == targetRow;
	}

	/**
	 * Returns true if moving from this piece’s current square to (targetColumn,
	 * targetRow) is strictly horizontal or vertical, non‑zero, on the board, and
	 * has no pieces blocking the path.
	 */
	public boolean isStraightPathClear(int targetColumn, int targetRow) {
		// 1) Must stay on board
		if (!isWithinBoard(targetColumn, targetRow)) {
			return false;
		}

		// 2) Must actually change square
		if (getPreviousCol() == targetColumn && getPreviousRow() == targetRow) {
			return false;
		}

		// 3) Compute how far we’re moving in each axis
		int deltaColumn = targetColumn - getPreviousCol();
		int deltaRow = targetRow - getPreviousRow();

		// 4) Must move purely horizontal or purely vertical
		if (deltaColumn != 0 && deltaRow != 0) {
			return false;
		}

		// 5) Determine step direction for each axis: -1, 0, or +1
		int stepInColumn;
		if (deltaColumn > 0) {
			stepInColumn = 1;
		} else if (deltaColumn < 0) {
			stepInColumn = -1;
		} else {
			stepInColumn = 0;
		}

		int stepInRow;
		if (deltaRow > 0) {
			stepInRow = 1;
		} else if (deltaRow < 0) {
			stepInRow = -1;
		} else {
			stepInRow = 0;
		}

		// 6) Walk the squares between start and target
		int currentColumn = getPreviousCol() + stepInColumn;
		int currentRow = getPreviousRow() + stepInRow;
		while (currentColumn != targetColumn || currentRow != targetRow) {
			for (Piece piece : GamePanel.boardState) {
				if (piece.isSameSquare(currentColumn, currentRow)) {
					return false; // path is blocked
				}
			}
			currentColumn += stepInColumn;
			currentRow += stepInRow;
		}

		return true;
	}

	/**
	 * Returns true if moving from this piece’s current square to (targetColumn,
	 * targetRow) along a diagonal is 1) on the board, 2) non‑zero, 3) a true
	 * diagonal (equal column and row distance), and 4) has no pieces blocking the
	 * path.
	 */
	public boolean isDiagonalPathClear(int targetColumn, int targetRow) {
		// 1) Must stay on board
		if (!isWithinBoard(targetColumn, targetRow)) {
			return false;
		}

		// 2) Must actually change square
		int startColumn = getPreviousCol();
		int startRow = getPreviousRow();
		if (startColumn == targetColumn && startRow == targetRow) {
			return false;
		}

		// 3) Must move equal steps in both axes (true diagonal)
		int deltaColumn = targetColumn - startColumn;
		int deltaRow = targetRow - startRow;
		if (Math.abs(deltaColumn) != Math.abs(deltaRow)) {
			return false;
		}

		// 4) Determine step direction for each axis: +1 or -1
		int stepInColumn;
		if (deltaColumn > 0) {
			stepInColumn = 1;
		} else {
			stepInColumn = -1;
		}

		int stepInRow;
		if (deltaRow > 0) {
			stepInRow = 1;
		} else {
			stepInRow = -1;
		}

		// 5) Walk each square between start and target (exclusive)
		int currentColumn = startColumn + stepInColumn;
		int currentRow = startRow + stepInRow;
		while (currentColumn != targetColumn && currentRow != targetRow) {
			for (Piece piece : GamePanel.boardState) {
				if (piece.isSameSquare(currentColumn, currentRow)) {
					return false; // path is blocked
				}
			}
			currentColumn += stepInColumn;
			currentRow += stepInRow;
		}

		return true;
	}

	// ====== POSITION CONVERSION METHODS ======
	// These methods convert between board coordinates (boardCol/boardRow) and pixel
	// coordinates

	public int convertColToX(int boardCol) {
		return boardCol * Board.getSquareSize(); // boardCol * 100pixels gives us x coordinates

	}

	public int convertRowToY(int boardRow) {
		return boardRow * Board.getSquareSize(); // boardRow * 100pixels gives us y coordinates
	}

	public int convertXToCol(int pixelX) {
		return ((pixelX + Board.getHalfSquareSize()) / Board.getSquareSize());
	}

	public int convertYToRow(int pixelY) {
		return ((pixelY + Board.getHalfSquareSize()) / Board.getSquareSize());
	}

	// ====== GETTER AND SETTER METHODS ======
	// These methods get/set various variables

	/**
	 * @return the boardCol
	 */
	public int getBoardCol() {
		return boardCol;
	}

	/**
	 * @return the previousCol
	 */
	public int getPreviousCol() {
		return previousCol;
	}

	/**
	 * @param boardCol the boardCol to set
	 */
	public void setBoardCol(int boardCol) {
		this.boardCol = boardCol;
	}

	/**
	 * @return the boardRow
	 */
	public int getBoardRow() {
		return boardRow;
	}

	/**
	 * @return the previousRow
	 */
	public int getPreviousRow() {
		return previousRow;
	}

	/**
	 * @param boardRow the boardRow to set
	 */
	public void setBoardRow(int boardRow) {
		this.boardRow = boardRow;
	}

	/**
	 * @return the pixelX
	 */
	public int getPixelX() {
		return pixelX;
	}

	/**
	 * @param pixelX the pixelX to set
	 */
	public void setPixelX(int pixelX) {
		this.pixelX = pixelX;
	}

	/**
	 * @return the pixelY
	 */
	public int getPixelY() {
		return pixelY;
	}

	/**
	 * @param pixelY the pixelY to set
	 */
	public void setPixelY(int pixelY) {
		this.pixelY = pixelY;
	}

	/**
	 * @return the pieceColor
	 */
	public int getPieceColor() {
		return pieceColor;
	}

	/**
	 * @return the collidingPiece
	 */
	public Piece getCollidingPiece() {
		return collidingPiece;
	}

	/**
	 * @param collidingPiece the collidingPiece to set
	 */
	public void setCollidingPiece(Piece collidingPiece) {
		this.collidingPiece = collidingPiece;
	}

	/**
	 * @return the moved
	 */
	public boolean hasMoved() {
		return moved;
	}

	// color is int and WHITE is int value and must use == comparison
	public boolean isWhite(int color) {
		if (GamePanel.WHITE == color) {
			return true;
		}
		return false;
	}

}