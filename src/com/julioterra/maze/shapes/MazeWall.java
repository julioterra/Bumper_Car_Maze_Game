package com.julioterra.maze.shapes;

import android.graphics.Color;
import game.elements.Coord;

public class MazeWall extends MazeElement {

	public MazeWall(Coord loc, float width, float height) {
		super(loc, width, height);
		this.elementType = 1;
		this.color = Color.argb(255, 0, 0, 255);
	}

	public MazeWall(int imgID, Coord loc, int frameCount, Coord size) {
		super(imgID, loc, 1, frameCount, size);
		this.elementType = 1;
		this.color = Color.argb(0, 0, 0, 0);
	}

	public void draw(float offSetX, float offSetY) {
		super.draw(offSetX, offSetY);
		offSetX = offSetX + this.loc.x;
		offSetY = offSetY + this.loc.y;
		if (!imgAvail) {
			paint.setColor(this.color);
			canvas.drawRect(offSetX, offSetY, offSetX+size.x, offSetY+size.y, paint);
		}
	}

}
