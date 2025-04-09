
package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import piece.Piece;

public class GamePanel extends JPanel implements Runnable {
	private static final int WINDOW_WIDTH = 1100;
	private static final int WINDOW_HEIGHT = 800;
	private final int FPS = 60;
	Thread gameThread;
	Board board = new Board();

	// PIECES
	private static ArrayList<Piece> pieces = new ArrayList<>();

	// COLOR
	private static final int WHITE = 0;
	public static final int BLACK = 1;
	int currentColor = getWhite();

	public GamePanel() {
		setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		setBackground(new Color(49, 46, 43));

	}

	public void launchGame() {
		gameThread = new Thread(this);// instantiate the gameThread
		gameThread.start();// used to call the run method

	}

	@Override
	public void run() {

		// GAME LOOP
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		// keeps the game loop running while the program is running
		while (gameThread != null) {

			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}

	public void update() {

	}

	// needed to implement graphics
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		Graphics2D boardGraphics = (Graphics2D) graphics;
		board.draw(boardGraphics);
	}

	/**
	 * @return the white
	 */
	public static int getWhite() {
		return WHITE;
	}

}
