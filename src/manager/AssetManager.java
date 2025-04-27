package manager;

import entity.Npc_OldMan;
import main.GamePanel;
import objects.Obj_Boots;
import objects.Obj_Chest;
import objects.Obj_Door;
import objects.Obj_Key;

public class AssetManager {
	
	GamePanel gp;
	
	public AssetManager(GamePanel gp) {
		this.gp=gp;
	}
	
	public void setObject() {
		
	}
	public void setNpc() {
		gp.npc[0]=new Npc_OldMan(gp);
		gp.npc[0].worldX=gp.tileSize*21;
		gp.npc[0].worldY=gp.tileSize*21;
	}
}
