package manager;

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
		gp.obj[i]=new Obj_Key(gp);
		gp.obj[i].worldX=23*gp.tileSize;
		gp.obj[i].worldY=7*gp.tileSize;
		i++;
		gp.obj[i]=new Obj_Key(gp);
		gp.obj[i].worldX=23*gp.tileSize;
		gp.obj[i].worldY=40*gp.tileSize;
		i++;
		gp.obj[i]=new Obj_Key(gp);
		gp.obj[i].worldX=37*gp.tileSize;
		gp.obj[i].worldY=7*gp.tileSize;
		i++;
		gp.obj[i]=new Obj_Door(gp);
		gp.obj[i].worldX=12*gp.tileSize;
		gp.obj[i].worldY=23*gp.tileSize;
		i++;
		gp.obj[i]=new Obj_Door(gp);
		gp.obj[i].worldX=8*gp.tileSize;
		gp.obj[i].worldY=28*gp.tileSize;
		i++;
		gp.obj[i]=new Obj_Door(gp);
		gp.obj[i].worldX=10*gp.tileSize;
		gp.obj[i].worldY=11*gp.tileSize;
		i++;
		gp.obj[i]=new Obj_Chest(gp);
		gp.obj[i].worldX=10*gp.tileSize;
		gp.obj[i].worldY=9*gp.tileSize;
		i++;
		gp.obj[i]=new Obj_Boots(gp);
		gp.obj[i].worldX=37*gp.tileSize;
		gp.obj[i].worldY=42*gp.tileSize;
		i++;
		
	}
}
