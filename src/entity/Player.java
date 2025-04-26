package entity;



import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;
import manager.KeyManager;


public class Player extends Entity{
	
	GamePanel gp;
	KeyManager km;
	public final int screenX;
	public final int screenY;
	//SCORE DE LLAVES
	public int hasKeys=0;
	//SPRITE RESET
	int spriteReset=0;
	
	public Player(GamePanel gp,KeyManager km) {
		
		this.gp=gp;
		this.km=km;
		//STICK CHAR IN MID ON SCREEN
		this.screenX = gp.screenWidth/2-(gp.tileSize/2);
		this.screenY = gp.screenHeight/2-(gp.tileSize/2);
		
		//+++++++++++++++++++++++++
//		x = (tileSize - width) / 2 = (48 - 24) / 2 = 12;
		solidArea = new Rectangle(12,12,24,24);
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
		worldX=gp.tileSize*23;
		worldY=gp.tileSize*21;
		speed=10;
		direction="down";
	}
	public void getPlayerImage() {
		//DRAW 1 BY 1
//		try {
//		up1=ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
//		up2=ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
		//NEW METHOD
		up1= setup("boy_up_1");
		up2= setup("boy_up_2");
		down1= setup("boy_down_1");
		down2= setup("boy_down_2");
		left1= setup("boy_left_1");
		left2= setup("boy_left_2");
		right1= setup("boy_right_1");
		right2= setup("boy_right_2");
		
		
		
	}
	public BufferedImage setup(String imageName) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage scaledImage = null;
		try {
			scaledImage=ImageIO.read(getClass().getResourceAsStream("/player/"+imageName+".png"));
			scaledImage=uTool.scaleImage(scaledImage, gp.tileSize, gp.tileSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scaledImage;
	}
	public void update() {
		if(km.up==true||km.down==true||
				km.left==true||km.right==true) {
			
			
			if (km.up==true) {
				direction="up";
			}
			else if (km.down == true) {
				direction="down";
			}
			else if (km.left==true) {
				direction="left";
			}
			else if (km.right==true) {
				direction="right";
			}
			//COLLISION CHECK TILES
			collisionOn=false;
			gp.cManager.checkTile(this);

			if (collisionOn==false) {
				switch(direction) {
				case "up":	worldY-=speed;break;
				case "down":worldY+=speed;break;
				case "left":worldX-=speed;break;
				case "right":worldX+=speed;break;
				}
			}
			//******COLLISION*********
			//----COLISION WITH OBJECTS--- index
			int objIndex =gp.cManager.checkObject(this, true);
			pickUpObject(objIndex);
			//--------------------------------
			spriteCounter++;
			if (spriteCounter>12) {
				if (spriteNum==1) {
					spriteNum=2;
				}else if (spriteNum==2) {
					spriteNum=1;
				}
				spriteCounter=0;
			}
		}	//RESET PARA QUE NO SE QUEDE EN EL SEGUNDO SPRITE
			else {
			spriteReset++;
			if (spriteReset==60) {
				spriteNum=1;
				spriteReset=0;
			}
			
		}

	}
	public void pickUpObject(int i) {
		if (i != 999) {
			//gp.obj[index]=null; "para que el obejto "desaparezca" al ser tocado"
			String objectName= gp.obj[i].name;
			switch(objectName) {
			case "Key":
			gp.playSE(1);
			hasKeys++;
			gp.obj[i]=null;
			gp.ui.showMessage("You picket up a key");
//			System.out.println("Has: " + hasKeys+" keys");
			break;
			case "Door": 
			if (hasKeys >0) {
			gp.playSE(3);
			gp.obj[i]=null;
			hasKeys--;
			gp.ui.showMessage("You opened a door");
			}else {
				gp.ui.showMessage("You need a key");
			}
//			System.out.println("Has: " + hasKeys+" keys");
			break;
			case "Boots":
			gp.playSE(2);
			gp.obj[i]=null;
			speed+=10;
				gp.ui.showMessage("Speed Incremented to: "+speed);
			break;
			case "Chest":
			gp.ui.gameFinished=true;
			gp.stopMusic();
			gp.playSE(4);
			gp.obj[i]=null;
			break;
			}
		}
	}
	public void draw(Graphics2D g2) {
		
		g2.setColor(Color.PINK);
		g2.fillRect(worldX, worldY, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;

		switch (direction) {
		    case "up":
		        if (spriteNum == 1) {
		            image = up1;
		        } else if (spriteNum == 2) {
		            image = up2;
		        }
		        break;

		    case "down":
		        if (spriteNum == 1) {
		            image = down1;
		        } else if (spriteNum == 2) {
		            image = down2;
		        }
		        break;

		    case "left":
		        if (spriteNum == 1) {
		            image = left1;
		        } else if (spriteNum == 2) {
		            image = left2;
		        }
		        break;

		    case "right":
		        if (spriteNum == 1) {
		            image = right1;
		        } else if (spriteNum == 2) {
		            image = right2;
		        }
		        break;
		}
		//Para que se centre la pantalla hay q cambiar las primeras x,y
		g2.setColor(Color.PINK);
		g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
		g2.drawImage(image, screenX, screenY, null);
		g2.setColor(Color.red);
		g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

	}
}
