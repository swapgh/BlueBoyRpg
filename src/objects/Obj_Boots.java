package objects;

import entity.Entity;
import main.GamePanel;

public class Obj_Boots extends Entity{
	
	GamePanel gp;
	
	public Obj_Boots(GamePanel gp) {
		super(gp);
		name = "Boots";
		down1 = setup("/objects/boots",gp.tileSize,gp.tileSize);

	}
}
