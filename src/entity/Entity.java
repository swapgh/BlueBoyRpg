package entity;
import java.awt.AlphaComposite;
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
	//OBJECT IMAGES
	public BufferedImage image,image2,image3;
	public String name;
	public boolean collision = false;
	public String direction="down";
	// PLAYER IMAGES
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	// PLAYER ATACK IMAGES
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	boolean attacking = false;
	// IMAGES CHANGER
	public int spriteCounter = 0;
	public int spriteNum = 1;
	// COLLISION
	public Rectangle attackArea = new Rectangle(0,0,0,0);
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public boolean collisionOn = false;
	public int aiLockMove= 0;
	// PARA OBJETOS
	public int solidAreaDefaultX, solidAreaDefaultY;
	// PARA INTERACCION NPC
	String dialogue[]= new String [20];
	int dialogueIndex=0;
	// INTERACCION ENEMY
	public boolean invincible=false;
	public int invincibleCounter=0;
	public int type; //0=player,1=npc,2=enemy
	//UI CHAR STATUS
	public int maxLife;
	public int life;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	public void setAction() {
		
	}
	public void speak() {
		if (dialogue[dialogueIndex]==null) {
			dialogueIndex=0;
		}
		gp.ui.currentDialog=dialogue[dialogueIndex];
		dialogueIndex++;
		switch(gp.player.direction) {
		case "up": direction="down";break;
		case "down": direction="up";break;
		case "left": direction="right";break;
		case "right": direction="left";break;
		}
	}
	public void update() {
		setAction();
		
		collisionOn=false;
		gp.cManager.checkTile(this);
		gp.cManager.checkObject(this,false);
		//SE CAMBIA CHECKPLAYER PARA QUE DETECTE SI ENEMY LO TOCO Y NO SOLO CUANDOP PLAYER LO TOCA (BOOLEAN)
		boolean contactPlayer=gp.cManager.checkPlayer(this);
		gp.cManager.checkEntity(this, gp.npc);
		gp.cManager.checkEntity(this, gp.enemy);
		if (this.type == 2 && contactPlayer ==true) {
			if (gp.player.invincible == false) {
				gp.player.life -=1;
				gp.player.invincible = true;
			}
		}
		
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
		//INVINCIBLE COUNTER
		if (invincible == true) {
			invincibleCounter++;
			if (invincibleCounter>40) {
				invincible=false;
				invincibleCounter=0;
			}
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
			if (invincible == true) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4F));
			}
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
		}
	}

	public BufferedImage setup(String imagePath,int width,int height) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage scaledImage = null;
		try {
			scaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			scaledImage = uTool.scaleImage(scaledImage,width,height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scaledImage;
	}

}
