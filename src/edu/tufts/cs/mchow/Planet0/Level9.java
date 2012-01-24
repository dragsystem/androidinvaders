package edu.tufts.cs.mchow.Planet0;

import edu.tufts.cs.mchow.Game.GameEngine;
import edu.tufts.cs.mchow.Game.Level;

public class Level9 extends Level {
	public Level9(GameEngine ge) {
		super(ge);
	}

	@Override
	public void init() {
		addBaddie(0, 0, 2);
		addBaddie(1, 0, 1);
		addBaddie(2, 0, 1);
		addBaddie(3, 0, 1);
		addBaddie(4, 0, 3);
		addBaddie(5, 0, 3);
		addBaddie(6, 0, 3);
		addBaddie(7, 0, 2);
		addBaddie(0, 1, 2);
		addBaddie(1, 1, 1);
		addBaddie(2, 1, 1);
		addBaddie(3, 1, 1);
		addBaddie(4, 1, 3);
		addBaddie(5, 1, 3);
		addBaddie(6, 1, 3);
		addBaddie(7, 1, 2);
		addBaddie(0, 2, 4);
		addBaddie(1, 2, 3);
		addBaddie(2, 2, 3);
		addBaddie(3, 2, 3);
		addBaddie(4, 2, 1);
		addBaddie(5, 2, 1);
		addBaddie(6, 2, 1);
		addBaddie(7, 2, 4);
		addBaddie(0, 3, 4);
		addBaddie(1, 3, 3);
		addBaddie(2, 3, 3);
		addBaddie(3, 3, 3);
		addBaddie(4, 3, 1);
		addBaddie(5, 3, 1);
		addBaddie(6, 3, 1);
		addBaddie(7, 3, 4);

	}
}
