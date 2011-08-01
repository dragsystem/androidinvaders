package edu.tufts.cs.mchow;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public abstract class GameSprite extends RectF {
	protected GameEngine gameEngine;
	protected double x, y;
	protected String label;
	protected int width, height, points, hp, hits;
	protected Bitmap image;
	protected boolean active;
	protected float convertW, convertH;

	public GameSprite(GameEngine gameEngine) {
		super();
		init(gameEngine);
	}

	public GameSprite(GameEngine gameEngine, double x, double y) {
		super();
		init(gameEngine);
		this.x = x;
		this.y = y;
	}

	public void init(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		x = 0;
		y = 0;
		width = 0;
		height = 0;
		points = 0;
		hp = 0;
		hits = 0;
		active = true;
		this.convertW = gameEngine.convertW;
		this.convertH = gameEngine.convertH;
	}

	public abstract void draw(Canvas c);

	public abstract void update();

	public abstract void hit();

	public GameEngine getGameEngine() {
		return gameEngine;
	}

	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
		width = image.getWidth();
		height = image.getHeight();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
