package edu.tufts.cs.mchow;

import java.util.*;

public abstract class Level {
	protected GameEngine ge;
	protected int timeLimit;
	protected int timeRemaining;
	protected boolean boss;
	protected int bossEnergy;
	protected int bossEnergyRemaining;
	protected ArrayList<Alien> baddies;
	protected ArrayList<Mothership> motherships;
	protected ArrayList<Box> boxes;

	protected static final int ROW = -50;
	protected static final int ROWSTART = -50;
	protected static final int COL = 65;
	protected static final int COLSTART = 10;
	protected static final int BOX_COL = 60;
	protected static final int BOX_COLSTART = 25;

	public Level(GameEngine ge) {
		this.ge = ge;
		timeLimit = 0;
		timeRemaining = 0;
		boss = false;
		bossEnergy = 0;
		bossEnergyRemaining = 0;
		baddies = new ArrayList<Alien>();
		motherships = new ArrayList<Mothership>();
		boxes = new ArrayList<Box>();
		init();
	}

	public void addBaddie(int colNum, int rowNum, int type) {
		switch (type) {
		case 1:
			baddies.add(new Alien1(ge, COLSTART + COL * colNum, ROWSTART + ROW
					* rowNum));
			break;
		case 2:
			baddies.add(new Alien2(ge, COLSTART + COL * colNum, ROWSTART + ROW
					* rowNum));
			break;
		case 3:
			baddies.add(new Alien3(ge, COLSTART + COL * colNum, ROWSTART + ROW
					* rowNum));
			break;
		case 4:
			baddies.add(new Alien4(ge, COLSTART + COL * colNum, ROWSTART + ROW
					* rowNum));
			break;
		}
	}

	public void addBox(int colNum) {
		boxes.add(new Box(ge, (BOX_COLSTART + BOX_COL * colNum) * ge.convertW,
				(ge.FRAME_HEIGHT - 80) * ge.convertH));
	}

	public abstract void init();

	public boolean levelCleared() {
		if (baddies.size() == 0 && motherships.size() == 0) {
			return true;
		}
		Iterator<Alien> it = baddies.iterator();
		while (it.hasNext()) {
			if (it.next().isActive()) {
				return false;
			}
		}
		Iterator<Mothership> it2 = motherships.iterator();
		while (it2.hasNext()) {
			if (it2.next().isActive()) {
				return false;
			}
		}
		return true;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public int getTimeRemaining() {
		return timeRemaining;
	}

	public boolean isBoss() {
		return boss;
	}

	public int getBossEnergy() {
		return bossEnergy;
	}

	public int getBossEnergyRemaining() {
		return bossEnergyRemaining;
	}

	public ArrayList<Alien> getBaddies() {
		return baddies;
	}

	public ArrayList<Box> getBoxes() {
		return boxes;
	}
}
