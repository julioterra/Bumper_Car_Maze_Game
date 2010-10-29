package com.julioterra.maze.shapes;

import game.elements.Coord;

public class MazeElement extends AnimatedSprite{
	public int elementType;
	
	public MazeElement(Coord loc, float width, float height) {
			this.loc = new Coord(loc.x, loc.y);
			this.size = new Coord(width, height);
			this.imgAvail = false;
	}
	
	public MazeElement(int imgID, Coord loc, int frameCount, Coord size) {
		super(imgID, loc, frameCount, size);
	}

	public MazeElement(int imgID, Coord loc, int fps, int frameCount, Coord size) {
		super(imgID, loc, fps, frameCount, size);
	}

	
	public void draw() {
		super.draw();
		if (!imgAvail)
			paint.setColor(this.color);
			canvas.drawRect(this.loc.x, this.loc.y, this.loc.x+size.x, this.loc.y+size.y, paint);
	}
		
	public void draw(float offSetX, float offSetY) {
		super.draw(offSetX, offSetY);
		if (!imgAvail) {
			paint.setColor(this.color);
			offSetX = offSetX + this.loc.x;
			offSetY = offSetY + this.loc.y;
			canvas.drawRect(offSetX, offSetY, offSetX+size.x, offSetY+size.y, paint);
		}
	}
	
}
