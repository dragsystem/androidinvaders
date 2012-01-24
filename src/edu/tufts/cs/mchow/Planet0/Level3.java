package edu.tufts.cs.mchow.Planet0;

import edu.tufts.cs.mchow.Game.GameEngine;
import edu.tufts.cs.mchow.Game.Level;

public class Level3 extends Level {
	public Level3(GameEngine ge) {
		super(ge);
	}

	@Override
	public void init() {
		addBaddie(0, 0, 4);
		addBaddie(1, 0, 4);
		addBaddie(2, 0, 4);
		addBaddie(3, 0, 4);
		// addBaddie(4, 0, 4);
		// addBaddie(5, 0, 4);
		// addBaddie(6, 0, 4);
		// addBaddie(7, 0, 4);
		// addBaddie(0, 1, 4);
		addBaddie(1, 1, 4);
		// addBaddie(2, 1, 4);
		addBaddie(3, 1, 4);
		// addBaddie(4, 1, 4);
		// addBaddie(5, 1, 4);
		addBaddie(6, 1, 4);
		// addBaddie(7, 1, 4);
		addBaddie(0, 2, 4);
		// addBaddie(1, 2, 4);
		addBaddie(2, 2, 4);
		// addBaddie(3, 2, 4);
		addBaddie(4, 2, 4);
		addBaddie(5, 2, 4);
		// addBaddie(6, 2, 4);
		addBaddie(7, 2, 4);
		// addBaddie(0, 3, 4);
		addBaddie(1, 3, 4);
		// addBaddie(2, 3, 4);
		addBaddie(3, 3, 4);
		addBaddie(4, 3, 4);
		addBaddie(5, 3, 4);
		// addBaddie(6, 3, 4);
		// addBaddie(7, 3, 4);

		addBox(6);
		addBox(7);
		addBox(8);
	}

}
