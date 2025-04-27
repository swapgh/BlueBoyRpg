package entity;
//***************************************************CAMBIO************************************
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	GamePanel gp;
	public int worldX, worldY;
	public int speed;
	// KEYS
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction;
	// IMAGES
	public int spriteCounter = 0;
	public int spriteNum = 1;

	// COLLISION
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public boolean collisionOn = false;
	public int aiLockMove= 0;
	// PARA OBJETOS
	public int solidAreaDefaultX, solidAreaDefaultY;

	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	public void setAction() {
		
	}
	public void update() {
		setAction();
		
		collisionOn=false;
		gp.cManager.checkTile(this);
		gp.cManager.checkObject(this,false);
		gp.cManager.checkPlayer(this);
		if (!collisionOn) { //***************************************************CAMBIO************************************
			switch(direction) {
			case "up":	worldY-=speed;break;
			case "down":worldY+=speed;break;
			case "left":worldX-=speed;break;
			case "right":worldX+=speed;break;
			}
		}

		spriteCounter++;
		if (spriteCounter>12) {
			if (spriteNum==1) {
				spriteNum=2;
			}else if (spriteNum==2) {
				spriteNum=1;
			}
			spriteCounter=0;
		}
		
	}
	// WE DRAW NPC AND HE CAN MOVE
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		// PARA NO DIBUJAR FUERA DE LA PANTALLA el g2.drawImage se mete dentro del if
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
				&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
				&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
				&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

			 switch (direction) {
             case "up": image = (spriteNum == 1) ? up1 : up2; break;
             case "down": image = (spriteNum == 1) ? down1 : down2; break;
             case "left": image = (spriteNum == 1) ? left1 : left2; break;
             case "right": image = (spriteNum == 1) ? right1 : right2; break;
         }
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}

	public BufferedImage setup(String imagePath) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage scaledImage = null;
		try {
			scaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			scaledImage = uTool.scaleImage(scaledImage, gp.tileSize, gp.tileSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scaledImage;
	}

}
