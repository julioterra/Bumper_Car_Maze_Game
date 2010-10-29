package com.julioterra.maze.shapes;

import android.graphics.Color;
import android.graphics.Paint;
import game.elements.Coord;
import game.elements.GameElement;

public class Shapes extends GameElement {

	public Coord loc;
	public Coord size;
	public int color;
	public static Paint paint = new Paint();

	public Shapes() {
		super();
	}

	public Shapes (Coord loc, float shapeSize) {
		super();
		this.loc = new Coord(loc.x, loc.y);
		this.size = new Coord(shapeSize, shapeSize);
		this.color = 0;
	}

	public Shapes (Coord loc, float width, float height) {
		super();
		this.loc = new Coord(loc.x, loc.y);
		this.size = new Coord(width, height);
		this.color = 0;
	}

	
	public void draw() {
		paint.setColor(color);
	}
		
	public void scale(float scaleMultiplier) {
		this.size.x = this.size.x * scaleMultiplier;
		this.size.y = this.size.y * scaleMultiplier;
	}
	
	public void move(Coord movement) {
		this.loc.x += movement.x;
		this.loc.y += movement.y;
	}

	public void move(float x, float y) {
		this.loc.x += x;
		this.loc.y += y;
	}

	public Coord getCurrentLoc() {
		return this.loc;
	}
	
	public int getColor() {
		return color;
	}
	
	public void setColor(int a, int r, int g, int b) {
		color = Color.argb(a, r, g, b);
	}

	public void setColor(int i) {
		color = i;
	}
	
//	public boolean hasIntersected(Circle circle) {
//		boolean intersected = false;
//		return intersected;
//	}
//
//	public boolean hasIntersected(MazeElement rectangle) {
//		boolean intersected = false;
//		return intersected;
//	}
//	
//	public boolean hasIntersected(float _x, float _y) {
//		boolean intersected = false;
//		return intersected;
//	}
		
}
