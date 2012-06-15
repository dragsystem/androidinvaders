package edu.tufts.cs.mchow.Planet1;

import edu.tufts.cs.mchow.Game.GameEngine;
import edu.tufts.cs.mchow.Game.Level;

public class Level8 extends Level {
	public Level8(GameEngine ge) {
		super(ge);
	}

	@Override
	public void init() {
		addBaddie(0, 0, 5);
//		addBaddie(1, 0, 4);
		addBaddie(2, 0, 5);
//		addBaddie(3, 0, 2);
//		addBaddie(4, 0, 3);
		addBaddie(5, 0, 5);
//		addBaddie(6, 0, 4);
		addBaddie(7, 0, 5);
		addBaddie(0, 1, 5);
		addBaddie(1, 1, 1);
		addBaddie(2, 1, 5);
		addBaddie(3, 1, 1);
		addBaddie(4, 1, 1);
		addBaddie(5, 1, 5);
		addBaddie(6, 1, 1);
		addBaddie(7, 1, 5);
		addBaddie(0, 2, 5);
//		addBaddie(1, 2, 2);
		addBaddie(2, 2, 5);
//		addBaddie(3, 2, 2);
//		addBaddie(4, 2, 4);
		addBaddie(5, 2, 5);
//		addBaddie(6, 2, 4);
		addBaddie(7, 2, 5);
		addBaddie(0, 3, 5);
		addBaddie(1, 3, 1);
		addBaddie(2, 3, 5);
		addBaddie(3, 3, 1);
		addBaddie(4, 3, 1);
		addBaddie(5, 3, 5);
		addBaddie(6, 3, 1);
		addBaddie(7, 3, 5);

		addBox(2);
		addBox(7);
	}
}
