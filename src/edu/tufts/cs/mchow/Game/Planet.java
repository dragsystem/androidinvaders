package edu.tufts.cs.mchow.Game;


public abstract class Planet {
	protected int MIN_SHOTS;
	public abstract Level getLevel(int levelNum, GameEngine ge);
	public abstract GameSprite getBoss(GameEngine ge);
	public int getMinShots() {return MIN_SHOTS;};
	public abstract Planet getNextPlanet();
}
