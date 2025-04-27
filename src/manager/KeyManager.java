package manager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;

public class KeyManager implements KeyListener{
	
GamePanel gp;
public boolean up,down,left,right;
//DEBUG
public boolean checkDrawTime=false;

public KeyManager(GamePanel gp) {
	this.gp=gp;
}
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_W) {
			up=true;
		}
		if (code == KeyEvent.VK_S) {
			down=true;
		}
		if (code == KeyEvent.VK_A) {
			left=true;
		}
		if (code == KeyEvent.VK_D) {
			right=true;
		}
		if (code == KeyEvent.VK_P) {
			if(gp.gameState==gp.playState) {
				gp.gameState = gp.pauseState;
			}else if(gp.gameState==gp.pauseState){
				gp.gameState = gp.playState;
			}
		}
		
		//DEBUG
		if (code== KeyEvent.VK_T) {
			if (checkDrawTime==false) {
				checkDrawTime=true;
			}else if (checkDrawTime==true) {
				checkDrawTime=false;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_W) {
			up=false;
		}
		if (code == KeyEvent.VK_S) {
			down=false;
		}
		if (code == KeyEvent.VK_A) {
			left=false;
		}
		if (code == KeyEvent.VK_D) {
			right=false;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {}
}
