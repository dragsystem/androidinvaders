package edu.tufts.cs.mchow;

public class Level6 extends Level {
	public Level6(GameEngine ge) {
		super(ge);
	}

	@Override
	public void init() {
		addBaddie(0, 0, 1);
		addBaddie(1, 0, 2);
		addBaddie(2, 0, 3);
		addBaddie(3, 0, 4);
		addBaddie(4, 0, 4);
		addBaddie(5, 0, 3);
		addBaddie(6, 0, 2);
		addBaddie(7, 0, 1);
		addBaddie(0, 1, 1);
		addBaddie(1, 1, 2);
		addBaddie(2, 1, 3);
		addBaddie(3, 1, 4);
		addBaddie(4, 1, 4);
		addBaddie(5, 1, 3);
		addBaddie(6, 1, 2);
		addBaddie(7, 1, 1);
		addBaddie(0, 2, 1);
		addBaddie(1, 2, 2);
		addBaddie(2, 2, 3);
		addBaddie(3, 2, 4);
		addBaddie(4, 2, 4);
		addBaddie(5, 2, 3);
		addBaddie(6, 2, 2);
		addBaddie(7, 2, 1);
		addBaddie(0, 3, 1);
		addBaddie(1, 3, 2);
		addBaddie(2, 3, 3);
		addBaddie(3, 3, 4);
		addBaddie(4, 3, 4);
		addBaddie(5, 3, 3);
		addBaddie(6, 3, 2);
		addBaddie(7, 3, 1);

		addBox(0);
		addBox(1);
		addBox(7);
		addBox(8);
	}
}
