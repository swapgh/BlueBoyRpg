package enemy;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

public class Mon_GreenSlime extends Entity {
	
	public Mon_GreenSlime(GamePanel gp) {
		super(gp);
		name= "Green Slime";
		speed= 1;
		maxLife=4;
		life = maxLife;
		type=2;
		solidArea.x=3;
		solidArea.y=18;
		solidArea.width=42;
		solidArea.height=30;
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
		getImage();
	}
	
	public void getImage() {
		up1=setup("/enemy/greenslime_down_1");
		up2=setup("/enemy/greenslime_down_1");
		down1=setup("/enemy/greenslime_down_1");
		down2=setup("/enemy/greenslime_down_1");
		left1=setup("/enemy/greenslime_down_1");
		left2=setup("/enemy/greenslime_down_1");
		right1=setup("/enemy/greenslime_down_1");
		right2=setup("/enemy/greenslime_down_1");
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
}
