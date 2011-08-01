package edu.tufts.cs.mchow;

import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.view.*;

public class GameEngine {
	private double MAX_ALIEN_SPEED = 7.6;
	public final int FRAME_WIDTH = 569;
	public final int FRAME_HEIGHT = 320;
	// private static final String DEBUG_TAG = "***** GE";
	private Context context; // Information about the screen and objects
	private Display display; // Get the screen width and height
	private GameThread thread;
	private int lives, levelNum, gameClock, shotCount, alienShotCount;
	public int score;
	private PlayerShip me;
	private ArrayList<Shot> shots;
	private ArrayList<SpecialShot> specialShots;
	private ArrayList<AlienShot> alienshots;
	protected ArrayList<Powerup> powerups;
	protected ArrayList<Explosion> explosions;
	private HUD headsUp;
	private Level level;
	private int baddiesInit;
	private int baddiesLeft;
	private Bitmap gameBackground;
	private BitmapDrawable transToLose, transToWin;
	private int alpha;
	boolean win, lose;
	private int MAX_SHOTS;
	private boolean doFire, doSpecialFire;
	
	public int pLevel, pScore, pClock, pLives, pAType, pNumKilled, pHighScore;
	public boolean pShield, pDoubleShot;
	
	public boolean loadingEnemies, respawning, movingUp, attacking;
	private Alien attacker;
	public int moveUpFrames;
	public int alienType;
	public int numInARow;
	public float convertW;
	public float convertH;
	public int difficulty;

	public GameEngine(Context context, Display display) {
		this.context = context;
		this.display = display;
		convertW = (float) display.getWidth() / (float) FRAME_WIDTH;
		convertH = (float) display.getHeight() / (float) FRAME_HEIGHT;
		transToLose = new BitmapDrawable(context.getResources(),
				BitmapFactory.decodeResource(context.getResources(), R.drawable.gameover));
		transToWin = new BitmapDrawable(context.getResources(),
				BitmapFactory.decodeResource(context.getResources(), R.drawable.youwin));
		transToLose.setAlpha(0);
		transToLose.setBounds(0, 0, display.getWidth(), display.getHeight());
		transToWin.setAlpha(0);
		transToWin.setBounds(0, 0, display.getWidth(), display.getHeight());
			
		shotCount = 0;
		alienShotCount = 0;
		lives = 1;
		score = 0;
		gameClock = 0;
		levelNum = -1;
		shots = new ArrayList<Shot>();
		specialShots = new ArrayList<SpecialShot>();
		alienshots = new ArrayList<AlienShot>();
		powerups = new ArrayList<Powerup>();
		explosions = new ArrayList<Explosion>();
		me = new PlayerShip(this);
		headsUp = new HUD(this);
		loadingEnemies = true;
		difficulty = 0;

		nextLevel();
		alienType = 0;
		numInARow = 0;
		MAX_SHOTS = 1;
		
		doFire = doSpecialFire = false;
	}

	public void nextLevel() {
		levelNum++;
		shots.clear();
		specialShots.clear();
		alienshots.clear();

		switch (levelNum) {
		case 0:
			level = new Level0(this);
			break;
		case 1:
			level = new Level1(this);
			break;
		case 2:
			level = new Level2(this);
			break;
		case 3:
			level = new Level3(this);
			break;
		case 4:
			level = new Level4(this);
			break;
		case 5:
			level = new Level5(this);
			break;
		case 6:
			level = new Level6(this);
			break;
		case 7:
			level = new Level7(this);
			break;
		case 8:
			level = new Level8(this);
			break;
		case 9:
			level = new Level9(this);
			break;
		default:
			win = true;
			if(alpha > 255) {
				thread.winGame();
				reset(difficulty);
			}
			break;
		}
		baddiesInit = level.baddies.size();
		baddiesLeft = baddiesInit;
		gameBackground = BitmapFactory.decodeResource(this.getContext()
				.getResources(), R.drawable.pluto);
		loadingEnemies = true;
		
		pLevel = levelNum;
		pScore = score;
		pClock = gameClock;
		pLives = lives;
		pShield = me.isShieldOn();
		pDoubleShot = (MAX_SHOTS == 2);
		pAType = alienType;
		pNumKilled = numInARow;
	}
	
	public void setThread(GameThread thread) {
		this.thread = thread;
	}

	public void reset(int difficulty) {
		this.difficulty = difficulty;
		switch(difficulty) {
		case 0:
			MAX_ALIEN_SPEED = 7.6;
			break;
		case 1:
			MAX_ALIEN_SPEED = 10.7;
			break;
		case 2:
			MAX_ALIEN_SPEED = 13.7;
			break;
		}
		shotCount = 0;
		alienShotCount = 0;
		lives = 5;
		score = 0;
		gameClock = 0;
		levelNum = -1;
		shots = new ArrayList<Shot>();
		specialShots = new ArrayList<SpecialShot>();
		alienshots = new ArrayList<AlienShot>();
		powerups = new ArrayList<Powerup>();
		explosions = new ArrayList<Explosion>();
		me = new PlayerShip(this);
		alienType = 0;
		numInARow = 0;
		MAX_SHOTS = 1;
		moveUpFrames = 0;
		movingUp = respawning = attacking = false;
		win = lose = false;
		alpha = 0;
		nextLevel();
	}
	
	public void getPrefs(int level, int score, int clock, int lives, boolean shield,
			boolean doubleShot, int aType, int numKilled, int highScore) {
		this.levelNum = level-1;
		this.score = score;
		this.gameClock = clock;
		this.lives = lives;
		if(shield) {me.shieldOn();}
		MAX_SHOTS = doubleShot ? 2 : 1;
		this.alienType = aType;
		this.numInARow = numKilled;
		this.pHighScore = highScore;
		nextLevel();
	}
	
	public int getLevelNum() {
		return levelNum;
	}
	
	public boolean hasDoubleShot() {
		return (MAX_SHOTS == 2);
	}

	public int getGameClock() {
		return gameClock;
	}

	public void fire() {
		if(!respawning)
			doFire = true;
	}
	
	private void doFire() {
		if (shots.size() < MAX_SHOTS) {
			shotCount++;
			shots.add(new Shot(this, me.x + me.width / 2, me.y, shotCount));
		}
		doFire = false;
	}
	
	public void specialFire() {
		doSpecialFire = true;
	}

	public void doSpecialFire() {
		if (numInARow > 3) {
			shotCount++;
			numInARow = 0;
			switch (alienType) {
			case 1:
				specialShots.add(new SpecialShot1(this, me.x + me.width / 2,
						me.y));
				break;
			case 2:
				specialShots.add(new SpecialShot2(this, me.x + me.width / 2,
						me.y));
				break;
			case 3:
				specialShots.add(new SpecialShot3(this, me.x + me.width / 2,
						me.y));
				break;
			case 4:
				SpecialShot4 first = new SpecialShot4(this, me.x + me.width/2, me.y, 1);
				SpecialShot4 second = new SpecialShot4(this, me.x + me.width/2, me.y, -1);
				first.setPartner(second);
				//second.setPartner(first);
				specialShots.add(first);
				specialShots.add(second);
				break;
			}
			alienType = 0;
		}
		doSpecialFire = false;
	}

	public void addMothership() {
		level.motherships.add(new Mothership(this, 0, 0));
	}
	
	public ArrayList<SpecialShot> getSpecialShots() {
		return specialShots;
	}

	public void alienFire(double x, double y) {
		alienShotCount++;
		alienshots.add(new AlienShot(this, x, y, alienShotCount));
	}

	public PlayerShip getPlayerShip() {
		return me;
	}
	
	public Level getLevel() {
		return level;
	}

	public Context getContext() {
		return context;
	}

	public Display getDisplay() {
		return display;
	}

	public int getScore() {
		return score;
	}

	public int getLives() {
		return lives;
	}

	public void addToScore(int points) {
		score += points;
	}

	public void increaseLives() {
		lives++;
	}

	public void decreaseLives() {
		lives--;
		alienType = 0;
		numInARow = 0;
	}

	public int getBaddiesLeft() {
		return baddiesLeft;
	}

	public boolean isGameOver() {
		return (lives < 1);
	}

	public boolean isLevelCleared() {
		return level.levelCleared();
	}

	public void makePowerup(double x, double y) {
		powerups.add(new Powerup(this, x, y));
	}

	public void setDoubleShot(boolean isOn) {
		MAX_SHOTS = isOn ? 2 : 1;
	}

	public void update() {
		if(doFire)
			doFire();
		if(doSpecialFire)
			doSpecialFire();
		
		gameClock++;
		me.update();
		Iterator<Shot> it1 = shots.iterator();
		while (it1.hasNext()) {
			Shot s = it1.next();
			s.update();
			if (!s.isActive())
				it1.remove();
		}

		Iterator<SpecialShot> it1b = specialShots.iterator();
		while (it1b.hasNext()) {
			SpecialShot s = it1b.next();
			s.update();
			if (!s.isActive())
				it1b.remove();
		}

		if(!loadingEnemies && !respawning && !attacking) {
			boolean changeDir = false;
			Iterator<Alien> it2 = level.getBaddies().iterator();
			while (it2.hasNext()) {
				Alien baddie = it2.next();
				if (baddie.isActive()) {
					double moveAmt = MAX_ALIEN_SPEED / Math.pow(baddiesLeft, 1.4);
					baddie.setMoveAmt(moveAmt);
					baddie.update();
					if ((baddie.getX() < 10) || (baddie.getX() > FRAME_WIDTH - 40)) {
						changeDir = true;
					}
					if (baddie.getY() > FRAME_HEIGHT*0.85) {
						attacking = true;
						attacker = baddie;
					}
				}
			}
			if (changeDir) {
				Iterator<Alien> it3 = level.getBaddies().iterator();
				while (it3.hasNext()) {
					Alien baddie = it3.next();
					baddie.moveDown();
					baddie.changeDir();
				}
			}
		}
		else if (loadingEnemies) {
			loadingEnemies = false;
			Iterator<Alien> it = level.getBaddies().iterator();
			while (it.hasNext()) {
				Alien baddie = it.next();
				baddie.y += 5;
				baddie.set((float) baddie.x, (float) baddie.y,
						(float) (baddie.width + baddie.x),
						(float) (baddie.height + baddie.y));
				if (baddie.y < 30)
					loadingEnemies = true;
			}
		}
		else if (attacking) {
			if(attacker.x > me.x)
				attacker.x -= 10;
			else
				attacker.x += 10;
			attacker.set((float) attacker.x, (float) attacker.y,
					(float) (attacker.width + attacker.x),
					(float) (attacker.height + attacker.y));
			if(!attacker.isActive()) {
				attacking = false;
			}
		}
		else {
			if(movingUp) {
				moveUpFrames++;
				Iterator<Alien> it2 = level.getBaddies().iterator();
				while (it2.hasNext()) {
					Alien baddie = it2.next();
					if(baddie.isActive()) {
						baddie.y -= 2;
						baddie.set((float) baddie.x, (float) baddie.y,
								(float) (baddie.width + baddie.x),
								(float) (baddie.height + baddie.y));
						if ((moveUpFrames >= 20) || (baddie.y <= 30)) { 
							movingUp = false;
							moveUpFrames = 0;
						}
					}
				}
			}
			if(!movingUp && alienshots.isEmpty())
				respawning = false;
		}
		Iterator<AlienShot> it3 = alienshots.iterator();
		while (it3.hasNext()) {
			AlienShot as = it3.next();
			as.update();
			if(!as.isActive())
				it3.remove();
		}

		Iterator<Mothership> it4 = level.motherships.iterator();
		while (it4.hasNext()) {
			Mothership m = it4.next();
			m.update();
			if (!m.isActive())
				it4.remove();
		}

		Iterator<Powerup> it5 = powerups.iterator();
		while (it5.hasNext()) {
			Powerup p = it5.next();
			p.update();
			if(!p.isActive())
				it5.remove();
		}

		checkCollisions();

	}

	public void checkCollisions() {
		// loop through all baddies
		Iterator<Alien> it = level.getBaddies().iterator();
		while (it.hasNext()) {
			Alien baddie = it.next();
			// check for collision with player
			if (baddie.isActive() && baddie.intersect(me) && !respawning) {
				me.hit();
				baddie.hit();
				explosions.add(new Explosion(this, baddie.getX(), baddie.getY()));
			} else {
				// check for collision with player's shots
				Iterator<Shot> it2 = shots.iterator();
				while (it2.hasNext()) {
					Shot s = it2.next();
					if (baddie.isActive() && baddie.intersect(s)) {
						baddie.hit();
						if (!baddie.isActive())
							baddiesLeft--;
						s.active = false;
						it2.remove();
						explosions.add(new Explosion(this, baddie.getX(), baddie.getY()));
					}
				}

				// check for collision with special shots
				Iterator<SpecialShot> it2b = specialShots.iterator();
				while (it2b.hasNext()) {
					SpecialShot ss = it2b.next();
					if (baddie.isActive() && baddie.intersect(ss)) {
						baddie.hit();
						if (!baddie.isActive())
							baddiesLeft--;
						ss.hit(baddie.getX() + baddie.getWidth() / 2, baddie.getY()
								+ baddie.getHeight() / 2);
						explosions.add(new Explosion(this, baddie.getX(), baddie.getY()));
					}
				}

				// check for collision with boxes
				Iterator<Box> it3 = level.getBoxes().iterator();
				while (it3.hasNext()) {
					Box b = it3.next();
					if (baddie.isActive() && b.isActive()
							&& baddie.intersect(b)) {
						b.hit();
						baddie.hit();
						explosions.add(new Explosion(this, baddie.getX(), baddie.getY()));
					}
				}
			}
		}

		// loop through all baddies' shots
		Iterator<AlienShot> it2 = alienshots.iterator();
		while (it2.hasNext()) {
			// check for collision with player
			AlienShot as = it2.next();
			if (as.isActive() && as.intersect(me) && !respawning) {
				me.hit();
				as.hit();
			}
			// check for collision with player's shots
			Iterator<Shot> it3 = shots.iterator();
			while (it3.hasNext()) {
				Shot s = it3.next();
				if (as.isActive() && as.intersect(s)) {
					as.hit();
					s.active = false;
				}
			}
			// check for collision with special shots
			Iterator<SpecialShot> it4 = specialShots.iterator();
			while (it4.hasNext()) {
				SpecialShot ss = it4.next();
				if (as.isActive() && as.intersect(ss)) {
					as.hit();
				}
			}
		}

		// loop through all boxes
		Iterator<Box> it3 = level.boxes.iterator();
		while (it3.hasNext()) {
			Box b = it3.next();
			// check for collision with baddies' shots
			Iterator<AlienShot> it4 = alienshots.iterator();
			while (it4.hasNext()) {
				AlienShot as = it4.next();
				if (as.isActive() && b.isActive() && as.intersect(b)) {
					as.hit();
					b.hit();
				}
			}
			// check for collision with player's shots
			Iterator<Shot> it5 = shots.iterator();
			while (it5.hasNext()) {
				Shot ps = it5.next();
				if (ps.isActive() && b.isActive() && ps.intersect(b)) {
					ps.active = false;
					b.hit();
					b.update();
				}
			}
			// check for collision with special shots
			Iterator<SpecialShot> it5b = specialShots.iterator();
			while (it5b.hasNext()) {
				SpecialShot ss = it5b.next();
				if (b.isActive() && b.intersect(ss)) {
					b.setActive(false);
				}
			}
		}

		// loop through all motherships
		Iterator<Mothership> it6 = level.motherships.iterator();
		while (it6.hasNext()) {
			Mothership m = it6.next();
			// check for collision with shot
			Iterator<Shot> it7 = shots.iterator();
			while (it7.hasNext()) {
				Shot a = it7.next();
				if (m.isActive() && m.intersect(a)) {
					m.hit();
					a.active = false;
					explosions.add(new Explosion(this, m.getX(), m.getY()));
				}
			}
			// check for collision with special shot
			Iterator<SpecialShot> it8 = specialShots.iterator();
			while (it8.hasNext()) {
				SpecialShot b = it8.next();
				if (m.isActive() && m.intersect(b)) {
					m.hit();
					b.hit(m.getX() + m.getWidth()/2, m.getY() + m.getHeight()/2);
					explosions.add(new Explosion(this, m.getX(), m.getY()));
				}
			}
		}

		// loop through all powerups
		Iterator<Powerup> it7 = powerups.iterator();
		while (it7.hasNext()) {
			Powerup p = it7.next();
			if (p.isActive() && p.intersect(me))
				p.hit();
		}
		
		//loop through all explosions
		ArrayList<Explosion> updExplosions = new ArrayList<Explosion>(); 
		Iterator<Explosion> it8 = explosions.iterator();
		while (it8.hasNext()) {
			Explosion e = it8.next();
			if(e.isActive())
				updExplosions.add(e);
		}
		explosions = updExplosions;
	}

	public void draw(Canvas canvas) {
		if(!lose && !thread.getShowMenu())
			update();
		if(score != 0)
			thread.score = score;
		
		drawGame(canvas);
		
		if (lives < 1 || lose) {
			lose = true;
			if(alpha > 255) {
				thread.gameOver();
				reset(difficulty);
			}
		}

		if (level.levelCleared() && alienshots.isEmpty()) {
			nextLevel();
		}
		
	}

	public void drawGame(Canvas canvas) {
		Paint p = new Paint();

		Rect r = new Rect(0, 0, getDisplay().getWidth(), getDisplay()
				.getHeight());
		canvas.drawBitmap(gameBackground, null, r, p);

		headsUp.setHUD(alienType, numInARow);
		headsUp.draw(canvas);

		me.draw(canvas);
		Iterator<Shot> it1 = shots.iterator();
		while (it1.hasNext()) {
			it1.next().draw(canvas);
		}
		Iterator<SpecialShot> it1b = specialShots.iterator();
		while (it1b.hasNext()) {
			it1b.next().draw(canvas);
		}
		Iterator<Alien> it2 = level.getBaddies().iterator();
		while (it2.hasNext()) {
			it2.next().draw(canvas);
		}
		Iterator<AlienShot> it3 = alienshots.iterator();
		while (it3.hasNext()) {
			it3.next().draw(canvas);
		}
		Iterator<Box> it4 = level.getBoxes().iterator();
		while (it4.hasNext()) {
			it4.next().draw(canvas);
		}
		Iterator<Mothership> it5 = level.motherships.iterator();
		while (it5.hasNext()) {
			it5.next().draw(canvas);
		}
		Iterator<Powerup> it6 = powerups.iterator();
		while (it6.hasNext()) {
			it6.next().draw(canvas);
		}
		Iterator<Explosion> it7 = explosions.iterator();
		while (it7.hasNext()) {
			it7.next().draw(canvas);
		}
		if(win) {
			alpha+=10;
			transToWin.setAlpha(Math.min(alpha, 255));
			transToWin.draw(canvas);
		}
		if(lose) {
			alpha+=10;
			transToLose.setAlpha(Math.min(alpha, 255));
			transToLose.draw(canvas);
		}
		
	}
}
