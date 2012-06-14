package edu.tufts.cs.mchow.Game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.tufts.cs.mchow.Aliens.Alien;
import edu.tufts.cs.mchow.Aliens.Alien1;
import edu.tufts.cs.mchow.Aliens.Alien2;
import edu.tufts.cs.mchow.Aliens.Alien3;
import edu.tufts.cs.mchow.Aliens.Alien4;
import edu.tufts.cs.mchow.Aliens.Alien5;

public abstract class Level {
	protected GameEngine ge;
	protected boolean boss;
	protected int bossEnergy;
	protected int bossEnergyRemaining;
	protected ArrayList<Alien> baddies;
	protected ArrayList<Mothership> motherships;
	protected ArrayList<Box> boxes;
	protected int TOTAL_HITS;

	protected static final int ROW = -50;
	protected static final int ROWSTART = -50;
	protected static final int COL = 65;
	protected static final int COLSTART = 10;
	protected static final int BOX_COL = 60;
	protected static final int BOX_COLSTART = 25;

	public Level(GameEngine ge) {
		this.ge = ge;
		boss = false;
		bossEnergy = 0;
		bossEnergyRemaining = 0;
		baddies = new ArrayList<Alien>();
		motherships = new ArrayList<Mothership>();
		boxes = new ArrayList<Box>();
		init();
	}

	public void addBaddie(int colNum, int rowNum, int type) {
		Alien baddie = null;
		switch (type) {
		case 5:
			baddie = new Alien5(ge, COLSTART + COL * colNum, ROWSTART + ROW * rowNum);
			break;
		case 4:
			baddie = new Alien4(ge, COLSTART + COL * colNum, ROWSTART + ROW * rowNum);
			break;
		case 3:
			baddie = new Alien3(ge, COLSTART + COL * colNum, ROWSTART + ROW * rowNum);
			break;
		case 2:
			baddie = new Alien2(ge, COLSTART + COL * colNum, ROWSTART + ROW * rowNum);
			break;
		default:
			baddie = new Alien1(ge, COLSTART + COL * colNum, ROWSTART + ROW * rowNum);
			break;
		}
		baddies.add(baddie);
		TOTAL_HITS += baddie.getMaxHP();
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
	
	public int getTotalHits() {
		return TOTAL_HITS;
	}

	public ArrayList<SpecialShot5> getSwarmMissleTargets(double x, double y) {
		ArrayList<Alien> targets = new ArrayList<Alien>(5);
		ArrayList<SpecialShot5> missiles = new ArrayList<SpecialShot5>(5);
		ArrayList<Alien> ones = new ArrayList<Alien>();
		ArrayList<Alien> twos = new ArrayList<Alien>();
		ArrayList<Alien> threes = new ArrayList<Alien>();
		ArrayList<Alien> fours = new ArrayList<Alien>();
		ArrayList<Alien> maxes = new ArrayList<Alien>();
		
		for(Alien baddie : baddies) {
			if(baddie.isActive()) {
				switch (baddie.getType()) {
				case 1:
					ones.add(baddie);
					break;
				case 2:
					twos.add(baddie);
					break;
				case 3:
					threes.add(baddie);
					break;
				case 4:
					fours.add(baddie);
					break;
				default:
					maxes.add(baddie);
					break;
				}
			}
		}
		
		if(maxes.size() > 4) {
			targets.addAll(maxes);
		} else if (fours.size() > 4) {
			targets.addAll(fours);
		} else if (threes.size() > 4) {
			targets.addAll(threes);
		} else if (twos.size() > 4) {
			targets.addAll(twos);
		} else if (ones.size() > 4) {
			targets.addAll(ones);
		} else {
			for(Alien a : baddies) {
				if(a.isActive()) {targets.add(a);}
			}
		}
		List<Alien> finalTargets = targets.subList(0, Math.min(5, targets.size()));
		
		for(Alien target : finalTargets) {
			missiles.add(new SpecialShot5(ge, x, y, target));
		}
		return missiles;
	}
}
