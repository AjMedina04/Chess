package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Queen;
import piece.Rook;

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
/**
 * Main game panel that manages the chess game state, rendering, piece movement,
 * and player interactions. Implements the game loop and mouse input handling.
 **/
// A GamePanel is-a JPanel and is Runnable
public class GamePanel extends JPanel implements Runnable {
	/*
	 * GamePanel is Runnable because it defines the game‐loop logic, and it has-a
	 * Thread so it can run that logic in its own thread.
	 */

	// A GamePanel has-a WINDOW_WIDTH (window width in pixels)
	private static final int WINDOW_WIDTH = 1100;
	// A GamePanel has-a WINDOW_HEIGHT (window height in pixels)
	private static final int WINDOW_HEIGHT = 800;
	// A GamePanel has-a FPS (frames per second target)
	private static final int FPS = 60;
	// A GamePanel has-a gameThread (thread executing the game loop)
	private Thread gameThread;
	// A GamePanel has-a board (chessboard model and renderer)
	private Board board = new Board();
	// A GamePanel has-a mouseHandler (mouse event handler)
	private Mouse mouseHandler = new Mouse();

	// Piece collections
	// A GamePanel has-a selectedPiece (currently selected chess piece)
	private Piece selectedPiece;
	// Stores the official game state - used for resetting when invalid moves occur
	// A GamePanel has-a boardState
	public static ArrayList<Piece> boardState = new ArrayList<>();
	// Working copy that shows current visual state including drag operations
	// A GamePanel has-a displayPieces
	public static ArrayList<Piece> displayPieces = new ArrayList<>();

	// Player colors
	// A GamePanel has-a WHITE constant (identifier for white pieces)
	public static final int WHITE = 0;
	// A GamePanel has-a BLACK constant (identifier for black pieces)
	public static final int BLACK = 1;
	// A GamePanel has-a currentPlayerTurn (tracks whose turn it is)
	private int currentPlayerTurn = WHITE; // Start game with whites turn first

	// Booleans
	// A GamePanel has-a validMove (flag if current drag is a legal move)
	boolean validMove;
	// A GamePanel has-a validDestination (flag if current drop square is valid)
	boolean validDestination;

	public GamePanel() {
		setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		setBackground(new Color(49, 46, 43));
		addMouseMotionListener(mouseHandler);
		addMouseListener(mouseHandler);

		initializeChessPieces();
		copyPieceState(boardState, displayPieces);

	}

	public void startGameLoop() {
		gameThread = new Thread(this);// instantiate the gameThread
		gameThread.start();// used to call the run method

	}

	// Game loop implementation
	// Maintains consistent 60 FPS for smooth animation
	@Override
	public void run() {

		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		while (gameThread != null) { // keeps the game loop running while the program is running

			// Add time since last frame
			currentTime = System.nanoTime(); // check current nano sec time continuously
			delta += (currentTime - lastTime) / drawInterval; // actual rate of time / by our desired rate of time
			lastTime = currentTime; // update last time during this process

			// When enough time has passed, update and render
			if (delta >= 1) { // when we get our desired frame rate
				update(); // Update game state
				repaint(); // Rendering
				delta--; // Reset delta
			}
		}
	}

	// Default position of our white and black teams
	public void initializeChessPieces() {

		// White Team
		boardState.add(new Pawn(WHITE, 0, 6));
		boardState.add(new Pawn(WHITE, 1, 6));
		boardState.add(new Pawn(WHITE, 2, 6));
		boardState.add(new Pawn(WHITE, 3, 6));
		boardState.add(new Pawn(WHITE, 4, 6));
		boardState.add(new Pawn(WHITE, 5, 6));
		boardState.add(new Pawn(WHITE, 6, 6));
		boardState.add(new Pawn(WHITE, 7, 6));
		boardState.add(new Rook(WHITE, 0, 7));
		boardState.add(new Rook(WHITE, 7, 7));
		boardState.add(new Knight(WHITE, 1, 7));
		boardState.add(new Knight(WHITE, 6, 7));
		boardState.add(new Bishop(WHITE, 2, 7));
		boardState.add(new Bishop(WHITE, 5, 7));
		boardState.add(new Queen(WHITE, 3, 7));
		boardState.add(new King(WHITE, 4, 7));

		// Black Team
		boardState.add(new Pawn(BLACK, 0, 1));
		boardState.add(new Pawn(BLACK, 1, 1));
		boardState.add(new Pawn(BLACK, 2, 1));
		boardState.add(new Pawn(BLACK, 3, 1));
		boardState.add(new Pawn(BLACK, 4, 1));
		boardState.add(new Pawn(BLACK, 5, 1));
		boardState.add(new Pawn(BLACK, 6, 1));
		boardState.add(new Pawn(BLACK, 7, 1));
		boardState.add(new Rook(BLACK, 0, 0));
		boardState.add(new Rook(BLACK, 7, 0));
		boardState.add(new Knight(BLACK, 1, 0));
		boardState.add(new Knight(BLACK, 6, 0));
		boardState.add(new Bishop(BLACK, 2, 0));
		boardState.add(new Bishop(BLACK, 5, 0));
		boardState.add(new Queen(BLACK, 3, 0));
		boardState.add(new King(BLACK, 4, 0));

	}

	public void copyPieceState(ArrayList<Piece> source, ArrayList<Piece> target) {
		// Clears list to make room for copy
		target.clear();
		for (int i = 0; i < source.size(); i++) {// Deep copy
			target.add(source.get(i));
		}
	}

	public void handlePieceDragging() {
		// Reset validation flags
		validMove = false;
		validDestination = false;

		// Update display state to match current game state
		copyPieceState(boardState, displayPieces);

		// Update the dragged piece's position to follow the mouse
		selectedPiece.setPixelX(mouseHandler.getPixelX() - Board.getHalfSquareSize());
		selectedPiece.setPixelY(mouseHandler.getPixelY() - Board.getHalfSquareSize());
		selectedPiece.setBoardCol(selectedPiece.convertXToCol(selectedPiece.getPixelX()));
		selectedPiece.setBoardRow(selectedPiece.convertYToRow(selectedPiece.getPixelY()));

		// Check if current mouse position represents a valid move
		if (selectedPiece.isLegalMove(selectedPiece.getBoardCol(), selectedPiece.getBoardRow())) {
			validMove = true;

			// Handle potential piece capture
			if (selectedPiece.getCollidingPiece() != null) {
				// Temporarily remove the piece for visual feedback
				displayPieces.remove(selectedPiece.getCollidingPiece().getIndex());
			}
			validDestination = true;
		}
	}

	/**
	 * Main game update method - called every frame. Handles mouse interactions,
	 * piece selection, dragging, and move validation.
	 */
	public void update() {

		// If mouse button is pressed
		if (mouseHandler.isPressed()) {
			// If you are not selecting a piece
			if (selectedPiece == null) {
				// Check list of displayPieces
				for (Piece piece : displayPieces) {
					// If the mouse is on an ally piece, pick it up as the activePiece
					if (piece.getPieceColor() == currentPlayerTurn
							&& piece.getBoardCol() == mouseHandler.getPixelX() / Board.getSquareSize()
							&& piece.getBoardRow() == mouseHandler.getPixelY() / Board.getSquareSize()) {
						selectedPiece = piece;
					}
				}
			} else {
				// If player is holding a piece
				handlePieceDragging();
			}
		}

		// If mouse button is released
		if (mouseHandler.isPressed() == false) {
			// If no piece is selected
			if (selectedPiece != null) {
				// If destination is valid
				if (validDestination) {

					// Pawn move handling: update, en passant capture, and promotion
					if (selectedPiece instanceof Pawn) {
						Pawn pawn = (Pawn) selectedPiece;
						int origCol = pawn.getPreviousCol();
						int origRow = pawn.getPreviousRow();
						pawn.updatePosition();
						pawn.enPassant(displayPieces, origCol, origRow);
						if (pawn.isEnPassantEligible())
							clearEnPassantEligibilityExcept(pawn);
						else
							clearEnPassantEligibilityForAll();
						if (pawn.shouldPromote()) {
							pawn.promote(displayPieces);
						}
					} else {
						selectedPiece.updatePosition();
						clearEnPassantEligibilityForAll();
					}

					// Update the piece list in case a piece has been captured and removed during
					// the simulation, including promotion
					copyPieceState(displayPieces, boardState);

					// Switch the turn to the other player after a valid move
					currentPlayerTurn = (currentPlayerTurn == WHITE) ? BLACK : WHITE;

				} else {

					copyPieceState(boardState, displayPieces);
					selectedPiece.resetPosition();

				}
				selectedPiece = null;
			}
		}

	}

	/** Clears en passant eligibility on all pawns except the provided pawn. */
	public void clearEnPassantEligibilityExcept(Pawn pawn) {
		for (Piece other : displayPieces) {
			if (other instanceof Pawn && other != pawn)
				((Pawn) other).clearEnPassantEligible();
		}
	}

	/** Clears en passant eligibility on all pawns. */
	public void clearEnPassantEligibilityForAll() {
		for (Piece other : displayPieces) {
			if (other instanceof Pawn)
				((Pawn) other).clearEnPassantEligible();
		}
	}

	// needed to implement graphics
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		Graphics2D chessGraphics = (Graphics2D) graphics;

		// BOARD
		board.draw(chessGraphics);

		// PIECES
		for (Piece piece : displayPieces) {
			piece.draw(chessGraphics);
		}

		if (selectedPiece != null) {
			if (validMove) {
				// Only draw the translucent highlight when the mouse is being pressed
				if (mouseHandler.isPressed()) {
					// Calculate which square the mouse is over
					int boardCol = mouseHandler.getPixelX() / Board.getSquareSize();
					int boardRow = mouseHandler.getPixelY() / Board.getSquareSize();

					// Draw translucent highlight for the square under the mouse
					chessGraphics.setColor(Color.WHITE);
					chessGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
					chessGraphics.fillRect(boardCol * Board.getSquareSize(), boardRow * Board.getSquareSize(),
							Board.getSquareSize(), Board.getSquareSize());
					chessGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				}
			}

			// Draw the piece being moved
			selectedPiece.draw(chessGraphics);

		}
	}

}
