package edu.tufts.cs.mchow.Game;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Display;
import edu.tufts.cs.mchow.R;
import edu.tufts.cs.mchow.Planet0.Planet0;
import edu.tufts.cs.mchow.Settings.Settings;


public class GameEngine {
	private double MAX_ALIEN_SPEED = 7.6;
	public final int FRAME_WIDTH = 569;
	public final int FRAME_HEIGHT = 320;
	private Context context; // Information about the screen and objects
	private Display display; // Get the screen width and height
	private GameThread thread;
	private int lives, levelNum, planetNum, gameClock, shotCount, alienShotCount;
	public int score;
	private PlayerShip me;
	private ArrayList<Shot> shots;
	private ArrayList<SpecialShot> specialShots;
	private ArrayList<AlienShot> alienshots;
	protected ArrayList<Powerup> powerups;
	protected ArrayList<Explosion> explosions;
	protected GameSprite boss;
	private HUD headsUp;
	private Level level;
	private Planet planet;
	private int baddiesInit;
	private int baddiesLeft;
	private Bitmap gameBackground;
	boolean planetComplete;
	private int MAX_SHOTS;
	public int totalPlanetShots, planetTimeElapsed;
	private boolean doFire, doSpecialFire;
	
	public int pLevel, pPlanet, pScore, pClock, pLives, pAType, pNumKilled, pHighScore;
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
			
		shotCount = 0;
		alienShotCount = 0;
		lives = 1;
		score = 0;
		gameClock = 0;
		planetNum = 0;
		shots = new ArrayList<Shot>();
		specialShots = new ArrayList<SpecialShot>();
		alienshots = new ArrayList<AlienShot>();
		powerups = new ArrayList<Powerup>();
		explosions = new ArrayList<Explosion>();
		me = new PlayerShip(this);
		headsUp = new HUD(this);
		loadingEnemies = true;
		difficulty = 0;

		resetPlanet();
		alienType = 0;
		numInARow = 0;
		MAX_SHOTS = 1;
		
		doFire = doSpecialFire = false;
	}

	public void nextPlanet() {
		thread.setRunning(false);
		Intent PEB = new Intent(context, PlanetEndBonusActivity.class);
		PEB.putExtra(PlanetEndBonusActivity.EXTRA_STARTTIME, 0);
		PEB.putExtra(PlanetEndBonusActivity.EXTRA_STARTSHOTS, 0);
		PEB.putExtra(PlanetEndBonusActivity.EXTRA_MINSHOTS, planet.getMinShots());
		PEB.putExtra(PlanetEndBonusActivity.EXTRA_SCORE, score);
		PEB.putExtra(PlanetEndBonusActivity.EXTRA_ENDSHOTS, shotCount);
		PEB.putExtra(PlanetEndBonusActivity.EXTRA_ENDTIME, gameClock);
		PEB.putExtra(PlanetEndBonusActivity.EXTRA_GAMEWIN, null!=planet);
		planet = planet.getNextPlanet();
		context.startActivity(PEB);
		
		planetNum++;
		if(planetNum<0) {
			resetPlanet();
		} else if (planet==null) {
			onWin();
		}
	}
	
	public void resetPlanet() {
		levelNum = -1;
		planet = new Planet0();
		
		nextLevel();
	}
	
	public void nextLevel() {
		levelNum++;
		shots.clear();
		specialShots.clear();
		alienshots.clear();

		if(0 <= levelNum && levelNum <= 9) {
			level = planet.getLevel(levelNum, this);
		} else if (10 == levelNum) {
			numInARow = 0;
			boss = planet.getBoss(this);
		} else {
			nextPlanet();
		}
		if(levelNum != 10) {
			boss = null;
		}
		baddiesInit = level.baddies.size();
		baddiesLeft = baddiesInit;
		gameBackground = BitmapFactory.decodeResource(this.getContext()
				.getResources(), R.drawable.pluto);
		loadingEnemies = true;
		
		pLevel = levelNum;
		pPlanet = planetNum;
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
		planetComplete = false;
		boss = null;
		resetPlanet();
	}
	
	public void getPrefs() {
		levelNum = Settings.getInt("levelNum", 0) - 1;
		planetNum = Settings.getInt("planetNum", 0) - 1;
		score = Settings.getInt("score", 0);
		gameClock = Settings.getInt("clock", 0);
		lives = Settings.getInt("lives", 5);
		if(Settings.getBoolean("shield", false)) {me.shieldOn();}
		MAX_SHOTS = Settings.getBoolean("doubleShot", false) ? 2 : 1;
		alienType = Settings.getInt("alienType", 0);
		numInARow = Settings.getInt("numInARow", 0);
		pHighScore = Settings.getInt("highScore", 0);
		totalPlanetShots = Settings.getInt("totalPlanetShots", 0);
		planetTimeElapsed = Settings.getInt("planetTimeElapsed", 0);
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
		if (shots.size() < MAX_SHOTS && !planetComplete) {
			shotCount++;
			shots.add(new Shot(this, me.x + me.width / 2, me.y, shotCount));
		}
		doFire = false;
	}
	
	public void specialFire() {
		doSpecialFire = true;
	}

	private void doSpecialFire() {
		if (numInARow > 3 && !planetComplete) {
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
		thread.score = score;
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
	
	public void addBoss() {
//		boss = new Boss0(this);
		boss = planet.getBoss(this);
		numInARow = 0;
	}

	public void update() {
		if(doFire)
			doFire();
		if(doSpecialFire)
			doSpecialFire();
		
		gameClock++;
		me.update();
		baddiesLeft = 0;
		Iterator<Alien> it = level.getBaddies().iterator();
		while (it.hasNext()) {
			if(it.next().isActive())
				baddiesLeft++;
		}
		Iterator<Shot> it2 = shots.iterator();
		while (it2.hasNext()) {
			Shot s = it2.next();
			s.update();
			if (!s.isActive())
				it2.remove();
		}
		Iterator<SpecialShot> it3 = specialShots.iterator();
		while (it3.hasNext()) {
			SpecialShot ss = it3.next();
			ss.update();
			if (!ss.isActive())
				it3.remove();
		}
		if(!loadingEnemies && !respawning && !attacking) {
			boolean changeDir = false;
			for (Alien baddie : level.getBaddies()) {
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
				for (Alien baddie : level.getBaddies()) {
					baddie.moveDown();
					baddie.changeDir();
				}
			}
		}
		else if (loadingEnemies) {
			loadingEnemies = false;
			Iterator<Alien> it4 = level.getBaddies().iterator();
			while (it4.hasNext()) {
				Alien baddie = it4.next();
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
				for (Alien baddie : level.getBaddies()) {
					if(baddie.isActive()) {
						baddie.y -= 2;
						baddie.set((float) baddie.x, (float) baddie.y,
								(float) (baddie.width + baddie.x),
								(float) (baddie.height + baddie.y));
						if (baddie.y <= 30) { 
							movingUp = false;
							moveUpFrames = 0;
						}
					}
				}
				if (moveUpFrames >= 20) { 
					movingUp = false;
					moveUpFrames = 0;
				}
			}
			if(!movingUp && alienshots.isEmpty())
				respawning = false;
		}
		Iterator<AlienShot> it5 = alienshots.iterator();
		while (it5.hasNext()) {
			AlienShot as = it5.next();
			as.update();
			if(!as.isActive())
				it5.remove();
		}
		Iterator<Mothership> it6 = level.motherships.iterator();
		while (it6.hasNext()) {
			Mothership m = it6.next();
			m.update();
			if (!m.isActive())
				it6.remove();
		}
		Iterator<Powerup> it7 = powerups.iterator();
		while (it7.hasNext()) {
			Powerup p = it7.next();
			p.update();
			if(!p.isActive())
				it7.remove();
		}
		if(boss != null)
			boss.update();
		
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
						ss.hit(baddie.getX() + baddie.getWidth() / 2, baddie.getY()
								+ baddie.getHeight() / 2);
						explosions.add(new Explosion(this, baddie.getX(), baddie.getY()));
					}
				}

				// check for collision with boxes
				Iterator<Box> it3 = level.getBoxes().iterator();
				while (it3.hasNext()) {
					Box b = it3.next();
					if (baddie.isActive() && b.isActive() && baddie.intersect(b)) {
						b.hit();
						it.remove();
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
		Iterator<Explosion> it8 = explosions.iterator();
		while (it8.hasNext()) {
			Explosion e = it8.next();
			if(!e.isActive())
				it8.remove();
		}
		
		if(boss != null) {
			if(boss.intersect(me) && boss.isActive()) {
				me.hit();
				me.hit();
				boss.transition();
			}
			Iterator<Shot> it9 = shots.iterator();
			while (it9.hasNext()) {
				Shot a = it9.next();
				if (boss.intersect(a)) {
					boss.hit();
					explosions.add(new Explosion(this, a.getX()-12, a.getY()-25));
					it9.remove();
				}
			}
			if(!boss.isActive()) {
				boss = null;
			}

		}
	}

	public void draw(Canvas canvas) {
		update();
		
		if(score != 0)
			thread.score = score;
		
		drawGame(canvas);
		
		if (lives < 1) {
			onLose();
		}

		if (level.levelCleared() && alienshots.isEmpty() && explosions.isEmpty()) {
			if(boss != null) {
				if(!boss.isActive()) {
					nextLevel();
					planetComplete = true;
				}
			}
			else
				nextLevel();
		}
		
	}
	
	private void onWin() {
		thread.winGame();
	}
	
	private void onLose() {
		thread.gameOver();
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
		if(boss != null) {
			boss.draw(canvas);
		}
		Iterator<Explosion> it7 = explosions.iterator();
		while (it7.hasNext()) {
			it7.next().draw(canvas);
		}
	}
}
