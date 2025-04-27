package objects;

import entity.Entity;
import main.GamePanel;

public class Obj_Sword_Normal extends Entity{
	
	public Obj_Sword_Normal(GamePanel gp) {
	super(gp);
	name = "Normal Sword";
	down1=setup("/objects/sword_normal",gp.tileSize,gp.tileSize);
	attackValue = 1;
	}
}
