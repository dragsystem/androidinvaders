package edu.tufts.cs.mchow.Planet0;


import edu.tufts.cs.mchow.Game.GameEngine;
import edu.tufts.cs.mchow.Game.Level;

public class Level1 extends Level {
	public Level1(GameEngine ge) {
		super(ge);
	}

	@Override
	
	public void init() {
		addBaddie(0, 0, 2);
		addBaddie(1, 0, 2);
		addBaddie(2, 0, 2);
		addBaddie(3, 0, 2);
		addBaddie(4, 0, 2);
		addBaddie(0, 1, 2);
		addBaddie(1, 1, 2);
		addBaddie(2, 1, 2);
		addBaddie(3, 1, 2);
		addBaddie(4, 1, 2);
		addBaddie(5, 1, 2);
		addBaddie(0, 2, 2);
		addBaddie(2, 2, 2);
		addBaddie(3, 2, 2);
		addBaddie(4, 2, 2);
		addBaddie(5, 2, 2);
		addBaddie(6, 2, 2);
		addBaddie(0, 3, 2);
		addBaddie(3, 3, 2);
		addBaddie(4, 3, 2);
		addBaddie(5, 3, 2);
		addBaddie(6, 3, 2);
		addBaddie(7, 3, 2);
	}
}
