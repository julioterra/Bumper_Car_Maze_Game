package com.julioterra.maze.shapes;

import android.graphics.Color;
import game.elements.Coord;

public class MazeCandy extends MazeElement {

	public MazeCandy(Coord loc, float width, float height) {
		super(loc, width, height);
		this.elementType = 10;
		this.color = Color.argb(255, 255, 0, 0);
	}

	public MazeCandy(int imgID, Coord loc, int frameCount, Coord size) {
		super(imgID, loc, frameCount, size);
		this.elementType = 10;
		this.color = Color.argb(0, 0, 0, 0);
	}

	public void draw() {
		super.draw();
		if (!imgAvail)
			paint.setColor(this.color);
			canvas.drawRect(this.loc.x, this.loc.y, this.loc.x+size.x, this.loc.y+size.y, paint);
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