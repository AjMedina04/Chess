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

	}

	public void resetPosition() {
		boardCol = getPreviousCol();
		boardRow = getPreviousRow();
		pixelX = convertColToX(boardCol);
		pixelY = convertRowToY(boardRow);
	}

	public boolean isLegalMove(int targetCol, int targetRow) {
		return true;
	}

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

}