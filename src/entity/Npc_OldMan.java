package entity;

import main.GamePanel;
import main.UtilityTool;

public class Npc_OldMan extends Entity {

	public Npc_OldMan(GamePanel gp) {
		super(gp);
		direction = "down";
		speed = 2;
		getImage();
		setDialogue();
	}

	public void getImage() {
		// DRAW 1 BY 1
//		try {
//		up1=ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
//		up2=ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
		// NEW METHOD
		up1 = setup("/npc/oldman_up_1",gp.tileSize,gp.tileSize);
		up2 = setup("/npc/oldman_up_2",gp.tileSize,gp.tileSize);
		down1 = setup("/npc/oldman_down_1",gp.tileSize,gp.tileSize);
		down2 = setup("/npc/oldman_down_2",gp.tileSize,gp.tileSize);
		left1 = setup("/npc/oldman_left_1",gp.tileSize,gp.tileSize);
		left2 = setup("/npc/oldman_left_2",gp.tileSize,gp.tileSize);
		right1 = setup("/npc/oldman_right_1",gp.tileSize,gp.tileSize);
		right2 = setup("/npc/oldman_right_2",gp.tileSize,gp.tileSize);

	}

	public void setAction() {
		aiLockMove++;
		if (aiLockMove == 200) {
			int u = UtilityTool.randomNumber(1, 100);

			if (u <= 25) {
				direction = "up";
			}
			else if (u > 25 && u <= 50) {
				direction = "down";
			}
			else if (u > 50 && u < 75) {
				direction = "left";
			}
			else if (u > 75 && u <= 100) {
				direction = "right";
			}
			aiLockMove=0;
		}
	}
	public void setDialogue() {
		dialogue[0]="Hello,lad";
		dialogue[1]="Hello,lad2,Hello,lad2,Hello\n,lad2Hello,lad2,Hello,lad2,Hello,lad2";
		dialogue[2]="Hello,lad3";
		dialogue[3]="Hello,lad4";
		
	}
	public void speak() {
		super.speak();
	}

}
