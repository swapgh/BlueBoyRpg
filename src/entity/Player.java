package entity;



import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
//import java.io.IOException;
//import javax.imageio.ImageIO;
import main.GamePanel;
import manager.KeyManager;


public class Player extends Entity{
	
	GamePanel gp;
	KeyManager km;
	public Player(GamePanel gp,KeyManager km) {
		this.gp=gp;
		this.km=km;
		setDefaultValues();
//		getPlayerImage();
	}
	public void setDefaultValues() {
		x=100;
		y=100;
		speed=5;
//		direction="down";
	}
//	public void getPlayerImage() {
//		try {
//		up1=ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
//		up2=ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
//		down1=ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
//		down2=ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
//		left1=ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
//		left2=ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
//		right1=ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
//		right2=ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
//		} catch (IOException e) {
//			e.getStackTrace();
//		}
//	}
	public void update() {
		if (km.up==true) {
			direction="up";
			y-=speed;
		}
		else if (km.down == true) {
			direction="down";
			y+=speed;
		}
		else if (km.left==true) {
			direction="left";
			x-=speed;
		}
		else if (km.right==true) {
			direction="right";
			x+=speed;
		}
	}
	public void draw(Graphics2D g2) {
		
		g2.setColor(Color.PINK);
		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
//		BufferedImage img = null;
//		switch(direction) {
//		case"up"->img=up1;
//		case"down"->img=down1;
//		case"left"->img=left1;
//		case"right"->img=right1;

		}
//		g2.drawImage(img, x, y, gp.tileSize, gp.tileSize, null);
	}
}
