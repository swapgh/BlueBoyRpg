package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import objects.Obj_Key;

public class UI {
	
	GamePanel gp;
	Font roboto,robotoB;
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter=0;
	public boolean gameFinished=false;
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		this.gp=gp;
		roboto = new Font("Roboto", Font.PLAIN, 40);
		robotoB = new Font("Roboto", Font.BOLD, 40);
		Obj_Key key = new Obj_Key(gp);
		keyImage = key.image;
	}
	public void showMessage (String text) {
		message=text;
		messageOn=true;
	}
	public void draw(Graphics g2) {
		
		if (gameFinished==true) {
			//PARA QUE APAREZCA EN EL CENTRO DE LA PANTALLA
			g2.setFont(roboto);
			g2.setColor(Color.WHITE);
			String text;
			int textLength;
			
			text= "You found the tresause!!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			int x = gp.screenWidth/2-textLength/2;
			int y = gp.screenHeight/2-(gp.tileSize*3);
			g2.drawString(text, x, y);
			
			g2.setFont(robotoB);
			g2.setColor(Color.YELLOW);
			text = "CONGRATULATIONS";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2-textLength/2;
			y = gp.screenHeight/2-(gp.tileSize);
			g2.drawString(text, x, y);
			
			text = "Your time is :"+ dFormat.format(playTime)+"!!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2-textLength/2;
			y = gp.screenHeight-(gp.tileSize*3);
			g2.drawString(text, x, y);
			
			
			gp.gameLoop=null;
			
		}else {
			//****ESTE SEGMENTO SE HIZO PRIMERO Y SE PUSO DENTRO DEL ELSE******
			g2.setFont(roboto);
			g2.setColor(Color.WHITE);
			g2.drawImage(keyImage,gp.tileSize/2,gp.tileSize/2,gp.tileSize,gp.tileSize,null);
			g2.drawString("x "+ gp.player.hasKeys, gp.tileSize+20,gp.tileSize+20);
			//(2) TIME PLAYED
			playTime +=(double)1/60;
			g2.drawString("Time:"+dFormat.format(playTime), gp.tileSize*11, gp.tileSize);
			//MESSAGE DISPLAY
			if (messageOn == true) {
				g2.setFont(roboto.deriveFont(20F));
				g2.drawString(message, gp.tileSize/2, gp.tileSize*3);
				//COUNTER PARA QUE DESAPAREZCA EL TEXTO
				messageCounter++;
				if (messageCounter>60) {
					messageCounter=0;
					messageOn=false;
				}
			}
			//******************************************************************
		}
			

	}
}
