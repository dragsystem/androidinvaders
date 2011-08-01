package edu.tufts.cs.mchow;

public class Level8 extends Level {
	public Level8(GameEngine ge) {
		super(ge);
	}

	@Override
	public void init() {
		addBaddie(0, 0, 1);
		// addBaddie(1, 0, 1);
		addBaddie(2, 0, 1);
		// addBaddie(3, 0, 1);
		addBaddie(4, 0, 1);
		// addBaddie(5, 0, 1);
		addBaddie(6, 0, 1);
		// addBaddie(7, 0, 1);
		// addBaddie(0, 1, 2);
		addBaddie(1, 1, 2);
		// addBaddie(2, 1, 2);
		addBaddie(3, 1, 2);
		// addBaddie(4, 1, 2);
		addBaddie(5, 1, 2);
		// addBaddie(6, 1, 2);
		addBaddie(7, 1, 2);
		addBaddie(0, 2, 3);
		// addBaddie(1, 2, 3);
		addBaddie(2, 2, 3);
		// addBaddie(3, 2, 3);
		addBaddie(4, 2, 3);
		// addBaddie(5, 2, 3);
		addBaddie(6, 2, 3);
		// addBaddie(7, 2, 3);
		// addBaddie(0, 3, 4);
		addBaddie(1, 3, 4);
		// addBaddie(2, 3, 4);
		addBaddie(3, 3, 4);
		// addBaddie(4, 3, 4);
		addBaddie(5, 3, 4);
		// addBaddie(6, 3, 4);
		addBaddie(7, 3, 4);

		addBox(2);
		addBox(7);
	}
}
