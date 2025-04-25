package main;
import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("2D Rpg");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack(); // size the window to fit the preferred size of the panel
		window.setLocationRelativeTo(null);

		window.setVisible(true);
//		gamePanel.setupGame();
		gamePanel.startGameThread(); // start the game thread
	}
}
