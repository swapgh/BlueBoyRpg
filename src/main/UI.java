package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import objects.Obj_Key;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font roboto,robotoB;
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
	}
	public void showMessage (String text) {
		message=text;
		messageOn=true;
	}
	public void draw(Graphics2D g2) {
		
		this.g2=g2;
		
		g2.setFont(roboto);
		g2.setColor(Color.WHITE);
		
		if (gp.gameState==gp.playState) {
			
		}
		if (gp.gameState== gp .pauseState) {
			drawPauseScreen();
		}
	}
	public void drawPauseScreen() {
		g2.setFont(roboto.deriveFont(70F));
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y= gp.screenHeight/2;
		g2.drawString(text, x, y);
	}
	public int getXforCenteredText(String text) {
		
		int lenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x= gp.screenWidth/2- lenght/2;
		return x;
	}
}
