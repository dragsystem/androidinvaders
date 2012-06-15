package edu.tufts.cs.mchow.Planet1;

import edu.tufts.cs.mchow.R;
import edu.tufts.cs.mchow.Game.GameEngine;
import edu.tufts.cs.mchow.Game.GameSprite;
import edu.tufts.cs.mchow.Game.Level;
import edu.tufts.cs.mchow.Game.Planet;
import edu.tufts.cs.mchow.Planet0.Boss0;

public class Planet1 extends Planet {
	public Level getLevel(int levelNum, GameEngine ge) {
		Level level = null;
		switch (levelNum) {
		case 0:
			level = new Level0(ge);
			break;
		case 1:
			level = new Level1(ge);
			break;
		case 2:
			level = new Level2(ge);
			break;
		case 3:
			level = new Level3(ge);
			break;
		case 4:
			level = new Level4(ge);
			break;
		case 5:
			level = new Level5(ge);
			break;
		case 6:
			level = new Level6(ge);
			break;
		case 7:
			level = new Level7(ge);
			break;
		case 8:
			level = new Level8(ge);
			break;
		case 9:
			level = new Level9(ge);
			break;	
		}
		MIN_SHOTS += level.getTotalHits();
		return level;
	}
	
	public GameSprite getBoss(GameEngine ge) {
		return new Boss0(ge);
	}
	
	public Planet getNextPlanet() {
		return null;
	}

	@Override
	public int getBackgroundRes() {
		return R.drawable.neptune;
	}
}
