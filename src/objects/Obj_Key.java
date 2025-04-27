package objects;


import entity.Entity;
import main.GamePanel;

public class Obj_Key extends Entity {
	

	public Obj_Key(GamePanel gp) {
		super(gp);
		name = "Key";
		down1 = setup("/objects/key");
	
	}
}
