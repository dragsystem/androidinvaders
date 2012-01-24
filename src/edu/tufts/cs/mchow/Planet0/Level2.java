package edu.tufts.cs.mchow.Planet0;

import edu.tufts.cs.mchow.Game.GameEngine;
import edu.tufts.cs.mchow.Game.Level;

public class Level2 extends Level {
	public Level2(GameEngine ge) {
		super(ge);
	}

	@Override
	public void init() {
		addBaddie(4, 0, 3);
		addBaddie(5, 0, 3);
		addBaddie(6, 0, 3);
		addBaddie(7, 0, 3);
		addBaddie(3, 1, 3);
		addBaddie(4, 1, 3);
		addBaddie(5, 1, 3);
		addBaddie(6, 1, 3);
		addBaddie(7, 1, 3);
		addBaddie(2, 2, 3);
		addBaddie(3, 2, 3);
		addBaddie(4, 2, 3);
		addBaddie(5, 2, 3);
		addBaddie(6, 2, 3);
		addBaddie(7, 2, 3);
		addBaddie(1, 3, 3);
		addBaddie(2, 3, 3);
		addBaddie(3, 3, 3);
		addBaddie(4, 3, 3);
		addBaddie(5, 3, 3);
		addBaddie(6, 3, 3);
		addBaddie(7, 3, 3);
	}
}
