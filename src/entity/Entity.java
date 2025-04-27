package entity;
import java.awt.AlphaComposite;
import java.awt.Color;
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
	public boolean alive = true;
	public boolean dying = false;
	int dyingCounter=0;
	boolean hpBarOn = false;
	int hpBarCounter=0;
	//UI CHAR STATUS
	public int maxLife;
	public int life;
	//CHARACTER ATTRIBUTES
	public int maxMana;
	public int mana;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int xp;
	public int nextLevelXp;
	public int coin;
	public Entity currentWeapon;
	public Entity currentShield;
	//ITEM ATRIBUTES
	public int attackValue;
	public int defenseValue;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	public void setAction(){}
	public void damageReaction(){}
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
				gp.playSE(6);
				int damage = attack - gp.player.defense;
				if (damage < 0) {
					damage = 0;
				}
				gp.player.life -= damage;
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
			 //MONSTER HP BAR
			 if (type == 2 && hpBarOn == true) {
				    double oneScale = (double)gp.tileSize / maxLife;
				    double hpBarvalue = oneScale * life;
				    // Draw background bar (black)
				    g2.setColor(Color.BLACK);
				    g2.fillRect(screenX, screenY - 10, gp.tileSize, 8);
				    // Draw life bar (red)
				    g2.setColor(Color.RED);
				    g2.fillRect(screenX, screenY - 10, (int)hpBarvalue, 8);
				    //HP BAR DISPLAY COUNTER
				    hpBarCounter++;
				    if (hpBarCounter >600) {
						hpBarCounter=0;
						hpBarOn=false;
					}
				}
			 //MONSTER ALPHA BLINK
			if (invincible == true) {
				hpBarOn=true;
//				hpBarCounter=0;
				changeAlpha(g2, 0.4F);
			}
			if (dying== true) {
				dyingAnimation(g2);
			}
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			changeAlpha(g2, 1F);
		}
	}
	public void dyingAnimation(Graphics2D g2) {
		dyingCounter++;
		int i = 5;
		if (dyingCounter<=i) {changeAlpha(g2, 0F);}
		if (dyingCounter> i && dyingCounter <=i*2) {changeAlpha(g2, 1F);}
		if (dyingCounter> i*2 && dyingCounter <=i*3) {changeAlpha(g2, 0F);}
		if (dyingCounter> i*3 && dyingCounter <=i*4) {changeAlpha(g2, 1F);}
		if (dyingCounter> i*4 && dyingCounter <=i*5) {changeAlpha(g2, 0F);}
		if (dyingCounter> i*5 && dyingCounter <=i*6) {changeAlpha(g2, 1F);}
		if (dyingCounter> i*6 && dyingCounter <=i*7) {changeAlpha(g2, 0F);}
		if (dyingCounter> i*7 && dyingCounter <=i*8) {changeAlpha(g2, 1F);}
		if (dyingCounter> i*8) {
			dying = false;
			alive = false;
		}
	}
	//BLINKING ANIMATION
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
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
