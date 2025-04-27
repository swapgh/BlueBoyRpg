package manager;


import main.EventRect;
import main.GamePanel;

public class EventManager {
	
	GamePanel gp;
	EventRect eventRect[][];
	//ONE TIME EVENT
	int previusEventX,previusEventY;
	boolean canTouchEvent = true;
	public EventManager(GamePanel gp) {
		this.gp=gp;
		
		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
		int col=0;
		int row=0;
		while (col<gp.maxWorldCol && row <gp.maxWorldRow) {
			eventRect[col][row]=new EventRect();
			eventRect[col][row].x=23;
			eventRect[col][row].y=23;
			eventRect[col][row].width=2;
			eventRect[col][row].height=2;
			eventRect[col][row].eventRectDefaultX=eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY=eventRect[col][row].y;
			
			col++;
			if (col ==gp.maxWorldCol) {
				col=0;
				row++;
			}
		}

	}
	public void checkEvent() {
		//CHECK IF PLAYER IS 1 TILE AWAY FROM THE LAST EVENT
		int xDistance = Math.abs(gp.player.worldX - previusEventX);
		int yDistance = Math.abs(gp.player.worldY - previusEventY);
		int distance = Math.max(xDistance, yDistance);
		if (distance > gp.tileSize) {
			canTouchEvent = true;
		}
		if (canTouchEvent == true) {
			if (hit(27,16,"right")==true) {damagePit(gp.dialogueState);}
			if (hit(23,19,"any")==true) {damagePit(gp.dialogueState);}
			if (hit(23,12,"up")==true) {healingPool(gp.dialogueState);}
		}

//		if (hit(27,22,"any")==true) {damagePit(gp.dialogueState);}

	}
	public boolean hit(int col,int row,String reqDirection) {
		
		boolean hit=false;
		
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
		eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;
		
		if (gp.player.solidArea.intersects(eventRect[col][row])) {
			if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit=true;
				//RESET WORLD X-Y IF EVENT TRIGGERED
				previusEventX = gp.player.worldX;
				previusEventY= gp.player.worldY;
			}
		}
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
		return hit;
	}
	public void damagePit(int gameState) {
		gp.gameState=gameState;
		gp.ui.currentDialog="You fall to a pit";
		gp.player.life -=1;
		canTouchEvent = false;
	}
	public void healingPool(int gameState) {
		if (gp.km.enterPress==true||gp.km.ePressed==true) {
			gp.gameState=gameState;
			gp.ui.currentDialog = "You feel replenish";
			gp.player.life=gp.player.maxLife;
		}
	}
	public void teleport(int gameState) {
		gp.gameState=gameState;
		gp.ui.currentDialog="You have been teleported";
		gp.player.worldX=gp.tileSize*37;
		gp.player.worldY=gp.tileSize*10;
	}
}
