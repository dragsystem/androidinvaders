package edu.tufts.cs.mchow;

public class Level0 extends Level {
	public Level0(GameEngine ge) {
		super(ge);
	}

	@Override
	public void init() {
		addBaddie(0, 0, 1);
		addBaddie(1, 0, 1);
		addBaddie(2, 0, 1);
		addBaddie(3, 0, 1);
		addBaddie(4, 0, 1);
		addBaddie(5, 0, 1);
		addBaddie(6, 0, 1);
		addBaddie(7, 0, 1);
		addBaddie(0, 1, 1);
		addBaddie(3, 1, 1);
		addBaddie(4, 1, 1);
		addBaddie(7, 1, 1);
		addBaddie(0, 2, 1);
		addBaddie(3, 2, 1);
		addBaddie(4, 2, 1);
		addBaddie(7, 2, 1);
		addBaddie(0, 3, 1);
		addBaddie(3, 3, 1);
		addBaddie(4, 3, 1);
		addBaddie(7, 3, 1);

	}
}
