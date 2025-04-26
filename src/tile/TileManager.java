package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;


public class TileManager {
	
	GamePanel gp;
	public Tile[]tile;
	public int mapTileNum [][];
	
	public TileManager(GamePanel gp) {
		this.gp=gp;
		
		tile=new Tile[10];
		mapTileNum=new int[gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap("/map/world01.txt");
	}
	public void loadMap(String path) {
		try {
			InputStream is = getClass().getResourceAsStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int col=0;
			int row=0;
			while (col<gp.maxWorldCol&&row<gp.maxWorldRow) {
				String line = br.readLine();
				while (col<gp.maxWorldCol) {
					String numbers[]=line.split(" ");
					int num= Integer.parseInt(numbers[col]);
					mapTileNum[col][row]=num;
					col++;
				}
				if (col==gp.maxWorldCol) {
					col=0;
					row++;
				}
			
			}
			br.close();
		} catch (Exception e) {
		}
	}
	public void getTileImage() {
		//SI QUISIERA HACER UNO POR UNO EL ESCALADO DE LA IMAGEN---> UTILITYTOOLS
//		tile[i]=new Tile();
//		tile[i].image=ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
//		tile[i].collision=false;
//		BufferedImage scaledImage = new BufferedImage(gp.tileSize, gp.tileSize, tile[i].image.getType());
//		Graphics2D g2 = scaledImage.createGraphics();
//		g2.drawImage(tile[i].image, 0, 0, gp.tileSize, gp.tileSize, null);
			setup(0, "grass01", false);
			setup(1, "wall", false);
			setup(2, "water01", false);
			setup(3, "earth", false);
			setup(4, "tree", false);
			setup(5, "road00", false);
	
	}
	public void setup(int index,String imageName,boolean collision) {
	
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index]=new Tile();
			tile[index].image=ImageIO.read(getClass().getResourceAsStream("/tiles/"+imageName+".png"));
			tile[index].image=uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision=collision;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void draw(Graphics2D g2) {
//		g2.drawImage(tile[0].image,0,0,gp.tileSize,gp.tileSize,null);
//		g2.drawImage(tile[1].image,48,0,gp.tileSize,gp.tileSize,null);
//		g2.drawImage(tile[2].image,96,0,gp.tileSize,gp.tileSize,null); //tile colocados manuales
		int worldCol=0;
		int worldRow=0;
		
			//DIBUJADO AUTOMATICO DE LAS TILES
		while(worldCol<gp.maxWorldCol && worldRow<gp.maxWorldRow) {
			int tileNum = mapTileNum[worldCol][worldRow];
			int worldX = worldCol *gp.tileSize;
			int worldY = worldRow *gp.tileSize;
			//+gp.player.screenX   AJUSTA EL OFSET CON EL WORLD
			//gp.player.screenY;
			int screenX = worldX -gp.player.worldX+gp.player.screenX;
			int screenY = worldY -gp.player.worldY+ gp.player.screenY;
			//PARA NO DIBUJAR FUERA DE LA PANTALLA el g2.drawImage se mete dentro del if
			if (worldX +gp.tileSize> gp.player.worldX -gp.player.screenX &&
				worldX -gp.tileSize< gp.player.worldX +gp.player.screenX&&
				worldY +gp.tileSize> gp.player.worldY - gp.player.screenY&&
				worldY -gp.tileSize< gp.player.worldY+gp.player.screenY) {
			g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
		
			worldCol++;
			if (worldCol==gp.maxWorldCol) {
				worldCol=0;
				worldRow++;
			}
		}
	}
}
