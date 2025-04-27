package manager;

import enemy.Mon_GreenSlime;
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
		int i=0;
		gp.obj[i] = new Obj_Door(gp);
		gp.obj[i].worldX = gp.tileSize * 21;
		gp.obj[i].worldY = gp.tileSize * 22;
		i++;
//		gp.obj[i] = new Obj_Door(gp);
//		gp.obj[i].worldX = gp.tileSize * 23;
//		gp.obj[i].worldY = gp.tileSize * 25;
//		i++;
//		gp.obj[i] = new Obj_Door(gp);
//		gp.obj[i].worldX = gp.tileSize * 25;
//		gp.obj[i].worldY = gp.tileSize * 25;
//		i++;

	}
	public void setNpc() {
		int i=0;
		gp.npc[i]=new Npc_OldMan(gp);
		gp.npc[i].worldX=gp.tileSize*9;
		gp.npc[i].worldY=gp.tileSize*10;
		i++;

	}
	public void setEnemy() {
		int i=0;
		gp.enemy[i]=new Mon_GreenSlime(gp);
		gp.enemy[i].worldX=gp.tileSize*23;
		gp.enemy[i].worldY=gp.tileSize*36;
		i++;
		gp.enemy[i]=new Mon_GreenSlime(gp);
		gp.enemy[i].worldX=gp.tileSize*23;
		gp.enemy[i].worldY=gp.tileSize*37;
		i++;
		gp.enemy[i]=new Mon_GreenSlime(gp);
		gp.enemy[i].worldX=gp.tileSize*23;
		gp.enemy[i].worldY=gp.tileSize*35;
		i++;
		gp.enemy[i]=new Mon_GreenSlime(gp);
		gp.enemy[i].worldX=gp.tileSize*23;
		gp.enemy[i].worldY=gp.tileSize*34;
	}
}
