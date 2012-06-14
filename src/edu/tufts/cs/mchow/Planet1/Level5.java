package edu.tufts.cs.mchow.Planet1;

import edu.tufts.cs.mchow.Game.GameEngine;
import edu.tufts.cs.mchow.Game.Level;

public class Level5 extends Level {
	public Level5(GameEngine ge) {
		super(ge);
	}

	@Override
	public void init() {
		addBaddie(0, 0, 1);
		addBaddie(1, 0, 5);
		addBaddie(2, 0, 2);
		addBaddie(3, 0, 2);
		addBaddie(4, 0, 3);
		addBaddie(5, 0, 3);
		addBaddie(6, 0, 4);
		addBaddie(7, 0, 4);
		addBaddie(0, 1, 4);
		addBaddie(1, 1, 5);
		addBaddie(2, 1, 5);
		addBaddie(3, 1, 2);
		addBaddie(4, 1, 2);
		addBaddie(5, 1, 3);
		addBaddie(6, 1, 3);
		addBaddie(7, 1, 4);
		addBaddie(0, 2, 4);
		addBaddie(1, 2, 4);
		addBaddie(2, 2, 5);
		addBaddie(3, 2, 5);
		addBaddie(4, 2, 2);
		addBaddie(5, 2, 2);
		addBaddie(6, 2, 3);
		addBaddie(7, 2, 3);
		addBaddie(0, 3, 3);
		addBaddie(1, 3, 4);
		addBaddie(2, 3, 4);
		addBaddie(3, 3, 5);
		addBaddie(4, 3, 5);
		addBaddie(5, 3, 2);
		addBaddie(6, 3, 2);
		addBaddie(7, 3, 3);

		addBox(0);
		addBox(1);
		addBox(2);
	}
}
