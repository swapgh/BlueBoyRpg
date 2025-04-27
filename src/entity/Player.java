package entity;



import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;
import manager.KeyManager;
import objects.Obj_Shield_Wood;
import objects.Obj_Sword_Normal;


public class Player extends Entity{
	
	KeyManager km;
	public final int screenX;
	public final int screenY;
	//SPRITE RESET
	int spriteReset=0;
	public boolean attackCanceled=false;
	
	public Player(GamePanel gp,KeyManager km) {
		super(gp);
		
		this.km=km;
		//STICK CHAR IN MID ON SCREEN
		this.screenX = gp.screenWidth/2-(gp.tileSize/2);
		this.screenY = gp.screenHeight/2-(gp.tileSize/2);
		
		//+++++++++++++++++++++++++
//		x = (tileSize - width) / 2 = (48 - 24) / 2 = 12;
		solidArea = new Rectangle(12,12,24,24);
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
		//WEAPON ATACK AREA
		attackArea.width=36;
		attackArea.height=36;
		setDefaultValues();
		getPlayerImage();
		getPlayerAtackImage();
	}
	public void setDefaultValues() {
		worldX = gp.tileSize*23;
		worldY = gp.tileSize*21;
		speed = 10;
		direction = "down";
		maxLife = 12;
		life = maxLife;
		//PLAYER STATS
		level = 1;
		strength = 1;
		dexterity = 1;
		xp = 0;
		nextLevelXp = 5;
		coin = 0;
		currentWeapon = new Obj_Sword_Normal(gp);
		currentShield = new Obj_Shield_Wood(gp);
		attack = getAttack();
		defense= getDefense();
	}
	public int getAttack() {
		return attack= strength * currentWeapon.attackValue;
	}
	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
	}
	public void getPlayerImage() {
		//DRAW 1 BY 1
//		try {
//		up1=ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
//		up2=ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
		//NEW METHOD
		up1= setup("/player/boy_up_1",gp.tileSize,gp.tileSize);
		up2= setup("/player/boy_up_2",gp.tileSize,gp.tileSize);
		down1= setup("/player/boy_down_1",gp.tileSize,gp.tileSize);
		down2= setup("/player/boy_down_2",gp.tileSize,gp.tileSize);
		left1= setup("/player/boy_left_1",gp.tileSize,gp.tileSize);
		left2= setup("/player/boy_left_2",gp.tileSize,gp.tileSize);
		right1= setup("/player/boy_right_1",gp.tileSize,gp.tileSize);
		right2= setup("/player/boy_right_2",gp.tileSize,gp.tileSize);
	}
	public void getPlayerAtackImage() {
		attackUp1=setup("/player/boy_attack_up_1",gp.tileSize,gp.tileSize*2);
		attackUp2=setup("/player/boy_attack_up_2",gp.tileSize,gp.tileSize*2);
		attackDown1=setup("/player/boy_attack_down_1",gp.tileSize,gp.tileSize*2);
		attackDown2=setup("/player/boy_attack_down_2",gp.tileSize,gp.tileSize*2);
		attackLeft1=setup("/player/boy_attack_left_1",gp.tileSize*2,gp.tileSize);
		attackLeft2=setup("/player/boy_attack_left_2",gp.tileSize*2,gp.tileSize);
		attackRight1=setup("/player/boy_attack_right_1",gp.tileSize*2,gp.tileSize);
		attackRight2=setup("/player/boy_attack_right_2",gp.tileSize*2,gp.tileSize);
	}

	public void update() {
		if (attacking ==true) {
			attacking();
		}
	else if(km.upPressed==true||km.downPressed==true||
		   km.leftPressed==true||km.rightPressed==true||
		   km.enterPress==true||km.ePressed==true) {
			
			if (km.upPressed==true) {
				direction="up";
			}
			else if (km.downPressed == true) {
				direction="down";
			}
			else if (km.leftPressed==true) {
				direction="left";
			}
			else if (km.rightPressed==true) {
				direction="right";
			}
			//COLLISION CHECK TILES
			collisionOn=false;
			gp.cManager.checkTile(this);
			//******COLLISION*********
			//COLISION WITH OBJECTS  //index
			int objIndex =gp.cManager.checkObject(this, true);
			pickUpObject(objIndex);
			//--------------------------------
			//NPC COLLISION
			int npcIndex = gp.cManager.checkEntity(this, gp.npc);
			interactNpc(npcIndex);
			//--------------------------
			//ENEMY COLLISION
			int enemyIndex= gp.cManager.checkEntity(this, gp.enemy);
			enemyDamage(enemyIndex);
			//CHECK EVENT
			gp.eMan.checkEvent();
//			gp.km.ePressed=false;
//			gp.km.enterPress=false;

			if (!collisionOn) {//***************************************************CAMBIO************************************
				switch(direction) {
				case "up":	worldY-=speed;break;
				case "down":worldY+=speed;break;
				case "left":worldX-=speed;break;
				case "right":worldX+=speed;break;
				}
			}
			//*****PARA NO ATACAR CUANDO VAS A LA FUENTE*****
			if (km.enterPress==true||km.ePressed==true && attackCanceled ==false) {
				gp.playSE(7);
				attacking=true;
				spriteCounter = 0;
			}
			attackCanceled=false;
			//**************************************
			//PARA ROTAR SPRITES
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
		//INVINCIBLE COUNTER
		if (invincible == true) {
			invincibleCounter++;
			if (invincibleCounter>60) {
				invincible=false;
				invincibleCounter=0;
			}
		}
	}
	public void attacking() {
		spriteCounter++;
		if (spriteCounter <=5) {
			spriteNum=1;
		}
		if (spriteCounter > 5 && spriteCounter <=25) {
			spriteNum=2;
			//SAVE CURRENT POSITION BEFORE ATTACK
			int currentWorldX=worldX;
			int currentWorldY=worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight= solidArea.height;
			//CHANGE THE COLLISION ARE FOR THE ATTACK
			switch(direction) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += attackArea.width; break;
			}
			//ATTACK AREA BECOMES SOLID AREA
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			//CHECK THE MONSTER COLLISION
			int enemyIndex = gp.cManager.checkEntity(this, gp.enemy);
			damageEnemy(enemyIndex);
			//RESET ORIGINAL POSITION AND AREA
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if (spriteCounter >25) {
			spriteNum =1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	public void pickUpObject(int i) {
		if (i != 999) {
		}
	}
	public void interactNpc(int i) {
		if (gp.km.enterPress == true||gp.km.ePressed==true) {
			
			if (i != 999) {
				attackCanceled = true;
				gp.gameState=gp.dialogueState;
				gp.npc[i].speak();
			}
		}
	}
	public void enemyDamage(int i) {
		if (i !=999) {
			if (invincible==false) {
				gp.playSE(6);
				int damage = attack -gp.enemy[i].attack-defense;
				if (damage < 0) {
					damage = 0;
				}
				life-= damage;
				invincible=true;
			}
		}
	}
	public void damageEnemy(int i) {
		if (i !=999) {
			if (gp.enemy[i].invincible ==false) {
				gp.playSE(5);
				int damage = attack -gp.enemy[i].defense;
				if (damage < 0) {
					damage = 0;
				}
				gp.enemy[i].life -= damage;
				gp.ui.addMessage(damage + " damage!");
				gp.enemy[i].invincible = true;
				gp.enemy[i].damageReaction();
				if (gp.enemy[i].life<=0) {
					gp.enemy[i].dying=true;
					gp.ui.addMessage("Killed the "+gp.enemy[i].name+ "!!");
					xp += gp.enemy[i].xp;
					checkLevelUp();
				}
			}
		}
	}
	public void checkLevelUp() {
		if (xp > nextLevelXp) {
			level++;
			nextLevelXp= nextLevelXp*2;
			maxLife +=1;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			
			gp.playSE(8);
			gp.gameState=gp.dialogueState;
			gp.ui.currentDialog="You are level "+level+ " now! \n"+"You feel stronger!!";
		}
	}
	public void draw(Graphics2D g2) {
		
		g2.setColor(Color.PINK);
		g2.fillRect(worldX, worldY, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;

		switch (direction) {
		    case "up":
		    	if (attacking ==false) {
		    		if (spriteNum == 1) {image = up1;} 
			        if (spriteNum == 2) {image = up2;}
				}
		    	if (attacking ==true) {
		    		tempScreenY=screenY-gp.tileSize;
		    		if (spriteNum == 1) {image = attackUp1;} 
			        if (spriteNum == 2) {image = attackUp2;}
		    	}
		        break;
		    case "down":
		    	if (attacking ==false) {
		    		if (spriteNum == 1) {image = down1;}
			        if (spriteNum == 2) {image = down2;}	
		    	}
		    	if (attacking==true) {
		    		if (spriteNum == 1) {image = attackDown1;}
			        if (spriteNum == 2) {image = attackDown2;}	
				}
		        break;

		    case "left":
		      	if (attacking ==false) {
		      	   if (spriteNum == 1) {image = left1;}
		      	   if (spriteNum == 2) {image = left2;}
				}
		    	if (attacking==true) {
		    		tempScreenX=screenX-gp.tileSize;
		    		if (spriteNum == 1) {image = attackLeft1;}
			        if (spriteNum == 2) {image = attackLeft2;}
		    	}
		        break;
		    case "right":
		     	if (attacking ==false) {
		     	   if (spriteNum == 1) {image = right1;}
		     	   if (spriteNum == 2) {image = right2;}
		     	}
		    	if (attacking==true) {
		    		if (spriteNum == 1) {image = attackRight1;}
			        if (spriteNum == 2) {image = attackRight2;}
		    	}
		        break;
		}
		
		if (invincible == true) {
			changeAlpha(g2, 0.4F);
		}
		//Para que se centre la pantalla hay q cambiar las primeras x,y
		g2.setColor(Color.PINK);
		g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		g2.setColor(Color.red);
		g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
		//INVINCIBLE COUNTER
//		g2.setFont(new Font("Arial",Font.PLAIN,26));
//		g2.setColor(Color.WHITE);
//		g2.drawString("Invincible"+invincibleCounter, 10, 400);

	}
}
