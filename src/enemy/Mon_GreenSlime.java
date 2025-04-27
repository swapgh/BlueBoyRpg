package enemy;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

public class Mon_GreenSlime extends Entity {
	
	GamePanel gp;
	public Mon_GreenSlime(GamePanel gp) {
		super(gp);
		
		this.gp=gp;
		name= "Green Slime";
		speed= 1;
		maxLife=4;
		life = maxLife;
		type=2;
		attack= 5;
		defense= 0;
		xp=2;
		solidArea.x=3;
		solidArea.y=18;
		solidArea.width=42;
		solidArea.height=30;
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
		getImage();
	}
	
	public void getImage() {
		up1=setup("/enemy/greenslime_down_1",gp.tileSize,gp.tileSize);
		up2=setup("/enemy/greenslime_down_1",gp.tileSize,gp.tileSize);
		down1=setup("/enemy/greenslime_down_1",gp.tileSize,gp.tileSize);
		down2=setup("/enemy/greenslime_down_1",gp.tileSize,gp.tileSize);
		left1=setup("/enemy/greenslime_down_1",gp.tileSize,gp.tileSize);
		left2=setup("/enemy/greenslime_down_1",gp.tileSize,gp.tileSize);
		right1=setup("/enemy/greenslime_down_1",gp.tileSize,gp.tileSize);
		right2=setup("/enemy/greenslime_down_1",gp.tileSize,gp.tileSize);
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
	//ENEMY MOVE AWAY FROM PLAYER
	public void damageReaction() {
		aiLockMove =0;
		direction = gp.player.direction;
	}
}
