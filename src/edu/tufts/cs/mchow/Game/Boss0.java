package edu.tufts.cs.mchow.Game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import edu.tufts.cs.mchow.R;

public class Boss0 extends GameSprite {
	private int maxHP, hp, mode, submode, transFrames, extraFrames, smashes;
	private double transX, transY;
	boolean smashRight;
	int frames, curFrame, frameDir;
	

	public Boss0(GameEngine ge) {
		super(ge);
		this.setImage(R.drawable.boss0);
		maxHP = hp = 0;
		switch(gameEngine.difficulty) {
		case 0:
			maxHP = hp = 24;
			break;
		case 1:
			maxHP = hp = 30;
			break;
		case 2:
			maxHP = hp = 36;
			break;
		}
		points = 500;
		mode = submode = extraFrames = smashes = 0;
		smashRight = true;
		frames = 11;
		x = ge.FRAME_WIDTH * 0.45;
		width = ge.FRAME_WIDTH/10;
		y = -ge.FRAME_HEIGHT * 0.25;
		height = ge.FRAME_HEIGHT/4;
		transition();
		active = true;
		this.set((float)x, (float)y, (float)x+width, (float)y+height);
		//debug
		//maxHP = hp = 3;
	}


	public void draw(Canvas c) {
		Paint p = new Paint();
		p.reset();
		Rect src = new Rect(image.getWidth()*curFrame/frames, 0, image.getWidth()*(curFrame+1)/frames, image.getHeight());
		RectF dst = new RectF((float)x*convertW, (float)y*convertH,
				(float)(x+width)*convertW, (float)(y+height)*convertH);
		c.drawBitmap(this.getImage(), src, dst, p);
		
		if (curFrame == 0)
			frameDir = 1;
		else if (curFrame == frames-1)
			frameDir = -1;
		curFrame+=frameDir;
	}


	public void hit() {
		hp--;
		if(hp == maxHP*2/3 || hp == maxHP/3) {
			mode++;
			transition();
		}
		if(hp==0) {
			gameEngine.explosions.add(new Boss0Explosion(gameEngine, x+width/2, y+height/2));
			active = false;
			gameEngine.addToScore(points);
		}
	}
	
	public void transition() {
		transX = gameEngine.FRAME_WIDTH * 0.45 - x;
		transY = gameEngine.FRAME_HEIGHT * 0.10 - y;
		transFrames = 20;
		submode = smashes = 0;
	}

	public void update() {
		if(transFrames > 0) {
			x += transX/20;
			y += transY/20;
			transFrames--;
		}
		else if (mode==0) {
			if (submode==0) {
				if(stay(30))
					submode = 1;
			}
			else if(submode==1) {
				if(seek())
					submode = 2;
			}
			else if (submode==2) {
				if(smash())
					submode = 3;
			}
			else if (submode==3) {
				if(stay(10))
					submode = 4;
			}
			else if (submode==4) {
				if(recoil())
					submode=0;
			}
		}
		else if(mode == 1) {
			if (submode==0) {
				if(stay(30))
					submode = 1;
			}
			else if (submode==1) {
				if(seek()) {
					submode = 2;
				}
			}
			else if (submode==2) {
				if(smash()) {
					submode = 3;
					smashRight = gameEngine.getPlayerShip().getX() + gameEngine.getPlayerShip().getWidth()/2 > x+width;
				}
			}
			else if (submode==3) {
				if(resmash(1))
					submode = 4;
			}
			else if (submode==4) {
				if(recoil()) {
					submode = 0;
				}
			}
		}
		else if(mode == 2) {
			if (submode==0) {
				if(stay(30))
					submode = 1;
			}
			else if (submode==1) {
				if(seek()) {
					submode = 2;
				}
			}
			else if (submode==2) {
				if(smash()) {
					submode = 3;
					smashRight = gameEngine.getPlayerShip().getX() + gameEngine.getPlayerShip().getWidth()/2 > x+width;
				}
			}
			else if (submode==3) {
				if(resmash(20))
					submode = 4;
			}
			else if (submode==4) {
				if(recoil()) {
					submode = 0;
				}
			}
		}

		this.set((float)x, (float)y, (float)x+width, (float)y+height);
	}
	
	private boolean seek() {
		double playerX = gameEngine.getPlayerShip().getX() + gameEngine.getPlayerShip().getWidth()/2;
		if(Math.abs(playerX - (x+width/2)) < 5) {
			x = playerX - width/2;
			return true;
		}
		else if(playerX > x+width/2) {
			x+=4;
		}
		else if(playerX < x+width/2) {
			x-=4;
		}
		return false;
	}

	private boolean smash() {
		y+=10;
		if(y > gameEngine.FRAME_HEIGHT*0.74)
			return true;
		return false;
	}
	
	private boolean stay(int stayFrames) {
		extraFrames++;
		if(extraFrames>stayFrames) {
			extraFrames=0;
			return true;
		}
		return false;
	}
	
	private boolean recoil() {
		y-=10;
		if(y < gameEngine.FRAME_HEIGHT*0.1)
			return true;
		return false;
	}
	
	private boolean resmash(int numSmashes) {
		extraFrames++;
		y = gameEngine.FRAME_HEIGHT*(0.5375+Math.pow((extraFrames-15)*0.03, 2));
		x = smashRight ? x+gameEngine.FRAME_WIDTH*0.005 : x-gameEngine.FRAME_WIDTH*0.005;
		if(extraFrames>30) {
			smashes++;
			extraFrames = 0;
			smashRight = gameEngine.getPlayerShip().getX() + gameEngine.getPlayerShip().getWidth()/2 > x+width;
		}
		if(smashes>=numSmashes) {
			smashes = 0;
			return true;
		}
		return false;
	}

}
