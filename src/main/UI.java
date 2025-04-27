package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import entity.Entity;
import objects.Obj_Heart;


public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font roboto,robotoB,maruMonica,purisa;
	BufferedImage heart_full,heart_half,heart_blank;
	public boolean messageOn = false;
//	public String message = "";
//	int messageCounter=0;
	ArrayList<String> message= new ArrayList<>();
	ArrayList<Integer>messageCounter= new ArrayList<>();
	public boolean gameFinished=false;
	//Npc dialog
	public String currentDialog;
	public int commandNum;
	public int titleScreenState=0; //0: the first screen //1: second screen (title)
	
	public UI(GamePanel gp) {
		this.gp=gp;

		try {
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica=Font.createFont(Font.TRUETYPE_FONT, is);
			 			is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
			 purisa=Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		roboto = new Font("Roboto", Font.PLAIN, 40);
		robotoB = new Font("Roboto", Font.BOLD, 40);
		//UI HUD OBJECTS
		Entity heart= new Obj_Heart(gp);
		heart_full=heart.image;
		heart_half=heart.image2;
		heart_blank=heart.image3;
	}
	//NPC DIALOGUE
	public void addMessage (String text) {
		message.add(text);
		messageCounter.add(0);
	}
	public void draw(Graphics2D g2) {
		
		this.g2=g2;
		
		g2.setFont(purisa);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		//TITLE STATE
		if (gp.gameState==gp.titileState) {
			drawTitileScreen();
		}
		//PLAY STATE
		if (gp.gameState==gp.playState) {
			drawPlayerLife();
			//FOR THE DIALOGUES
			drawMessage();
		}
		//PAUSE STATE
		if (gp.gameState== gp .pauseState) {
			drawPauseScreen();
		}
		//DIALOGUE STATE
		if (gp.gameState==gp.dialogueState) {
			drawDialogueScreen();
		}
		//CHARACTER STATE
		if (gp.gameState==gp.characterState) {
			drawCharacterScreen();
		}
	}
	public void drawMessage() {
		int messageX = gp.tileSize;
		int messageY = gp.tileSize*4;
		g2.setFont(purisa.deriveFont(Font.BOLD,20F));
		for(int i=0;i <message.size();i++) {
			if (message.get(i)!= null) {
				g2.setColor(Color.WHITE);
				g2.drawString(message.get(i), messageX, messageY);
				// INCREMENTAMOS EL INDEX PARA EL PROXIMO MENSAJE EN EL ARRAY
				int counter = messageCounter.get(i)+1;
				// SET THE COUNTER TO THE ARRAY +1 
				messageCounter.set(i, counter);
				messageY +=50;
				//CLEAR THE DIALOGUE IN THE SCREEN
				if (messageCounter.get(i)>180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
	}
	public void drawPlayerLife() {
		//POS OF HEART
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i=0;
		//DRAW MAX LIFE
		while (i<gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x+=gp.tileSize;
		}
		//RESET
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i=0;
		//DRAW CURRENT LIFE
		while (i<gp.player.life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if (i<gp.player.life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x+=gp.tileSize;
		}
	}
	public void drawDialogueScreen() {
		//WINDOW
		int x=gp.tileSize*2;
		int y=gp.tileSize/2;
		int width=gp.screenWidth -(gp.tileSize*4);
		int hight=gp.tileSize*4;
		drawSubWindow(x, y, width, hight);
		//DIALOGUE
		g2.setFont(purisa.deriveFont(25F));
		x += gp.tileSize-24;
		y += gp.tileSize;
		for(String line: currentDialog.split("\n")) {
			g2.drawString(line, x, y);	
			y+=40;
		}
		
	}
	public void drawSubWindow(int x, int y,int width,int height) {
		Color c=new Color(0,0,0,200);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		c=new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}
	public void drawTitileScreen() {
//		g2.setColor(new Color(70,120,80));
//		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		if (titleScreenState==0) {
			//TITLE NAME
			g2.setFont(maruMonica.deriveFont(Font.BOLD,96F));
			String text= "Blue boy Adventure";
			int x= getXforCenteredText(text);
			int y=gp.tileSize*3;
			//SHADOW
			g2.setColor(Color.GRAY);
			g2.drawString(text, x+5, y+5);
			//MAIN COLOR
			g2.setColor(Color.WHITE);
			g2.drawString(text, x, y);
			//BLUE BOY IMAGE
			x=gp.screenWidth/2-(gp.tileSize*2)/2;
			y+= gp.tileSize*2;
			g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2,null);
			//MENU
			g2.setFont(maruMonica.deriveFont(Font.BOLD,48F));
			text="NEW GAME";
			x= getXforCenteredText(text);
			y+= gp.tileSize*3.5;
			g2.drawString(text, x, y);
			if (commandNum ==0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			text="LOAD GAME";
			x= getXforCenteredText(text);
			y+= gp.tileSize;
			g2.drawString(text, x, y);
			if (commandNum ==1) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			text="QUIT";
			x= getXforCenteredText(text);
			y+= gp.tileSize;
			g2.drawString(text, x, y);
			if (commandNum ==2) {
				g2.drawString(">", x-gp.tileSize, y);
			}
		}
		else if (titleScreenState ==1) {
			g2.setColor(Color.WHITE);
			g2.setFont(maruMonica.deriveFont(42F));
			String text="Select your class!";
			int x= getXforCenteredText(text);
			int y=gp.tileSize*3;
			g2.drawString(text, x, y);
			
			text="Fighter";
			x= getXforCenteredText(text);
			y+=gp.tileSize*3;
			g2.drawString(text, x, y);
			if (commandNum==0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			text="Thief";
			x= getXforCenteredText(text);
			y+=gp.tileSize;
			g2.drawString(text, x, y);
			if (commandNum==1) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			text="Sorcerer";
			x= getXforCenteredText(text);
			y+=gp.tileSize;
			g2.drawString(text, x, y);
			if (commandNum==2) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			text="Back";
			x= getXforCenteredText(text);
			y+=gp.tileSize*2;
			g2.drawString(text, x, y);
			if (commandNum==3) {
				g2.drawString(">", x-gp.tileSize, y);
			}
		}
		
		
	}
	public void drawPauseScreen() {
		g2.setFont(purisa.deriveFont(70F));
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y= gp.screenHeight/2;
		g2.drawString(text, x, y);
	}
	public void drawCharacterScreen() {
		//FRAME
		final int frameX = gp.tileSize;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize*5;
		final int frameHeight = gp.tileSize*10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		//TEXT
		g2.setColor(Color.WHITE);
		g2.setFont(maruMonica.deriveFont(32F));
		int textX = frameX +20;
		int textY = frameY +gp.tileSize;
		final int lineHeight = 35;
		
		//TEXT NAMES
		//NAMES
		g2.drawString("Level",textX,textY);
		textY+= lineHeight;
		g2.drawString("Life",textX,textY);
		textY+= lineHeight;
		g2.drawString("Strenght",textX,textY);
		textY+= lineHeight;
		g2.drawString("Dexterity",textX,textY);
		textY+= lineHeight;
		g2.drawString("Attack",textX,textY);
		textY+= lineHeight;
		g2.drawString("Defense",textX,textY);
		textY+= lineHeight;
		g2.drawString("XP",textX,textY);
		textY+= lineHeight;
		g2.drawString("Next Level",textX,textY);
		textY+= lineHeight;
		g2.drawString("Coin",textX,textY);
		textY+= lineHeight+15;
		g2.drawString("Weapon",textX,textY);
		textY+= lineHeight+20;
		g2.drawString("Shield",textX,textY);
		textY+= lineHeight;
		
		//VALUES
		int tailX=(frameX+frameWidth)-30;
		//Reset textY
		textY = frameY +gp.tileSize;
		String value;
		
		value =String.valueOf(gp.player.level);
		textX=getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		value =String.valueOf(gp.player.life+"/"+gp.player.maxLife);
		textX=getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		value =String.valueOf(gp.player.strength);
		textX=getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		value =String.valueOf(gp.player.dexterity);
		textX=getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		value =String.valueOf(gp.player.attack);
		textX=getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		value =String.valueOf(gp.player.defense);
		textX=getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		value =String.valueOf(gp.player.xp);
		textX=getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		value =String.valueOf(gp.player.nextLevelXp);
		textX=getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		value =String.valueOf(gp.player.coin);
		textX=getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY+= lineHeight;
		g2.drawImage(gp.player.currentWeapon.down1, tailX-gp.tileSize, textY-13,null);
		textY+= gp.tileSize;
		g2.drawImage(gp.player.currentShield.down1, tailX-gp.tileSize, textY-13,null);
	}
	public int getXforCenteredText(String text) {
		
		int lenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x= gp.screenWidth/2- lenght/2;
		return x;
	}
	public int getXforAlignToRightText(String text,int tailX) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX-length;
		return x;
	}
}
