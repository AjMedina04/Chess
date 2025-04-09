package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		JFrame window = new JFrame("Chess");// create new JFrame to be a viewing window for the user
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// needed to close the window properly
		window.setResizable(false);// user cannot resize window to simplify coding

		// add gamePanel to the window
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack();// window adjusts size to gamePanel size

		window.setLocationRelativeTo(null);// make the window position more convenient for the user
		window.setVisible(true);
		
		gamePanel.launchGame();
	}

}
