package edu.tufts.cs.mchow.Planet1;

import edu.tufts.cs.mchow.Game.GameEngine;
import edu.tufts.cs.mchow.Game.Level;

public class Level0 extends Level {
	public Level0(GameEngine ge) {
		super(ge);
	}

	@Override
	public void init() {
		addBaddie(0, 0, 5);
		addBaddie(1, 0, 5);
		addBaddie(2, 0, 5);
		addBaddie(3, 0, 5);
		addBaddie(4, 0, 5);
		addBaddie(5, 0, 5);
		addBaddie(6, 0, 5);
		addBaddie(7, 0, 5);
		addBaddie(0, 1, 5);
		addBaddie(3, 1, 5);
		addBaddie(4, 1, 5);
		addBaddie(7, 1, 5);
		addBaddie(0, 2, 5);
		addBaddie(3, 2, 5);
		addBaddie(4, 2, 5);
		addBaddie(7, 2, 5);
		addBaddie(0, 3, 5);
		addBaddie(3, 3, 5);
		addBaddie(4, 3, 5);
		addBaddie(7, 3, 5);

	}
}
