package manager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;

public class KeyManager implements KeyListener {

	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPress, escPressed, pPressed, fPressed,
			ePressed, tPressed;
//DEBUG
	public boolean checkDrawTime = false;

	public KeyManager(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		// TITLE STATE
		if (gp.gameState == gp.titileState) {
			titleState(code);
		}
		// PLAY STATE
		else if (gp.gameState == gp.playState) {
			playState(code);
		}
		// PAUSE STATE
		else if (gp.gameState == gp.pauseState) {
			pauseState(code);
		}
		// DIALOGUE STATE
		else if (gp.gameState == gp.dialogueState) {
			dialogueState(code);
		}
		// CHARACTER HUD STATE
		else if (gp.gameState == gp.characterState) {
			characterState(code);
		}
	}

	public void titleState(int code) {
		if (gp.ui.titleScreenState == 0) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
			}
			if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_E) {
				if (gp.ui.commandNum == 0) {
					gp.ui.titleScreenState = 1;
				}
				if (gp.ui.commandNum == 1) {
					// add later
				}
				if (gp.ui.commandNum == 2) {
					System.exit(0);
				}
			}
			if (code == KeyEvent.VK_ESCAPE) {
				escPressed = true;
				System.exit(0); // exit the game
			}
		} else if (gp.ui.titleScreenState == 1) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 3;
				}
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 3) {
					gp.ui.commandNum = 0;
				}
			}
			if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_E) {
				if (gp.ui.commandNum == 0) {
					System.out.println("Do some fighter stuff");
					gp.gameState = gp.playState;
//					gp.playMusic(0);
					// add later
				}
				if (gp.ui.commandNum == 1) {
					System.out.println("Do some thief stuff");
					gp.gameState = gp.playState;
//					gp.playMusic(0);
					// add later
				}
				if (gp.ui.commandNum == 2) {
					System.out.println("Do some sorc stuff");
					gp.gameState = gp.playState;
//					gp.playMusic(0);
					// add later
				}
				if (gp.ui.commandNum == 3) {
					gp.ui.titleScreenState = 0;
				}
			}
			if (code == KeyEvent.VK_ESCAPE) {
				escPressed = true;
				System.exit(0); // exit the game
			}
		}
	}

	public void playState(int code) {
		if (code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_E) {
			ePressed = true;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPress = true;
		}
		if (code == KeyEvent.VK_P) {
			gp.gameState = gp.pauseState;
		}
		if (code == KeyEvent.VK_ESCAPE) {
			escPressed = true;
			System.exit(0); // exit the game
		}
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
		}

		// DEBUG
		if (code == KeyEvent.VK_T) {
//			gp.gameState=gp.playState;
			if (checkDrawTime == false) {
				checkDrawTime = true;
			} else if (checkDrawTime == true) {
				checkDrawTime = false;
			}
		}
//		if (code==KeyEvent.VK_L) {
//			gp.tileM.loadMap("/maps/worldV2.txt");
//		}
	}

	public void pauseState(int code) {
		if (code == KeyEvent.VK_P) {
			gp.gameState = gp.playState;
		}
	}

	public void dialogueState(int code) {
		if (code == KeyEvent.VK_E || code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;
		}
	}

	public void characterState(int code) {
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_E) {
			ePressed = false;
		}
		if (code == KeyEvent.VK_T) {
			tPressed = false;
		}
		if (code == KeyEvent.VK_P) {
			pPressed = false;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPress = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
