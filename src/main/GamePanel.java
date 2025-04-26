package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import entity.Player;
import manager.AssetManager;
import manager.CollisionManager;
import manager.KeyManager;
import manager.SoundManager;
import objects.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	//SCREEN SETTINGS
	public final int originalTileSize = 16; //16x16 tile
	final int scale = 3; //3x scaling
	public final int tileSize = originalTileSize * scale; //48x48 tile
	public final int maxScreenCol = 16; //16 tiles across
	public final int maxScreenRow = 12; //12 tiles down
	public final int screenWidth = tileSize * maxScreenCol; //768 pixels
	public final int screenHeight = tileSize * maxScreenRow; //576 pixels
	
	//WORLD SETTING
	public final int maxWorldCol=50;
	public final int maxWorldRow=50;
//	public final int worldWidth=tileSize*maxWorldCol;
//	public final int worldHeight=tileSize*maxWorldRow;
	//SYSTEM
	Thread gameLoop;
	KeyManager km=new KeyManager();
	public TileManager tileM=new TileManager(this);
	SoundManager music= new SoundManager();
	SoundManager sE = new SoundManager();
	public UI ui= new UI(this);
	int FPS=60;
	//SETTINGS
	public CollisionManager cManager= new CollisionManager(this);
	//ENTITY AND OBJECT
	public Player player = new Player(this, km);
	public SuperObject obj[]=new SuperObject[10];
	public AssetManager aSetter = new AssetManager(this);
	//GAME STATE

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(km);
		this.setFocusable(true);
	}
	public void setupGame() {
		aSetter.setObject();
//		playMusic(0);
		
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
		//***DEBUG START***
		long drawStart=0;
		if (km.checkDrawTime==true) {
			drawStart=System.nanoTime();	
		}
		
		//*****************
		//DRAW FIRST THE TILE
		tileM.draw(g2);
		
		for(int i = 0 ; i < obj.length;i++) {
			if (obj[i]!=null) {
				obj[i].draw(g2, this);
			}
		}
		//SECOND THE PLAYER
		player.draw(g2);
		//THEN THE UI
		ui.draw(g2);
		//***DEBUG END**
		if (km.checkDrawTime==true) {
			long drawEnd = System.nanoTime();
			long delta = drawStart-drawEnd;
			g2.setColor(Color.WHITE);
			g2.drawString("Delta: "+ delta,10,500);
			System.out.println("Delta:"+delta);	
		}
		//**************
		
		//THEN CLEAR WHEN NOT USING
		g2.dispose();
		
	}
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSE(int i) {
		sE.setFile(i);
		sE.play();
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
