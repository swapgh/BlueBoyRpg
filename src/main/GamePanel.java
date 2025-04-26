package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import entity.Player;
import manager.KeyManager;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	//SCREEN SETTINGS
	final int originalTileSize = 16; //16x16 tile
	final int scale = 3; //3x scaling
	public final int tileSize = originalTileSize * scale; //48x48 tile
	public final int maxScreenCol = 16; //16 tiles across
	public final int maxScreenRow = 12; //12 tiles down
	public final int screenWidth = tileSize * maxScreenCol; //768 pixels
	public final int screenHeight = tileSize * maxScreenRow; //576 pixels
	
	//WORLD SETTING
	//SYSTEM
	Thread gameLoop;
	KeyManager km=new KeyManager();
	TileManager tileM=new TileManager(this);
	int FPS=60;
	//SETTINGS
	//ENTITY AND OBJECT
	Player player = new Player(this, km);
	//GAME STATE

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(km);
		this.setFocusable(true);
	}
	public void startGameThread() {
		gameLoop = new Thread(this);
		gameLoop.start();
	}

	public void update() {
		player.update();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2= (Graphics2D)g;
		tileM.draw(g2);
		player.draw(g2);
		g2.dispose();
	}
	
	//CORE OF THE GAME
	@Override
	public void run() {
        double drawInterval = 1000000000 / FPS; // time per frame in nanoseconds
        double delta = 0; // time difference
        long past = System.nanoTime(); // get the current time in nanoseconds
        long now; // current time in nanoseconds

        while (gameLoop != null) {
            now = System.nanoTime(); // get the current time in nanoseconds
            delta += (now - past) / drawInterval; // calculate the time difference
            past = now; // set the last time to the current time

            if (delta >= 1) { // if the time difference is greater than or equal to 1
                update(); // update the game state
                repaint(); // repaint the screen
                delta--; // decrease the delta by 1
            }
		}
	}
		
}
