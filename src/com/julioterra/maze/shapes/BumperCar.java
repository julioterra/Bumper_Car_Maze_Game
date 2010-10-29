package com.julioterra.maze.shapes;

import android.graphics.Rect;
import game.elements.Coord;

public class BumperCar extends MazeElement{
	public Coord colProjection;
	
	public BumperCar(Coord loc, float size) {
		super(loc, size, size);
	}

	public BumperCar(int imgID, Coord loc, int frameCount, Coord size) {
		super(imgID, loc, frameCount, size);
		colProjection = new Coord(0,0);
//		this.size = new Coord(spriteWidth/2, spriteHeight/2);
		System.out.println(" Bumper car size: " + this.size.x + " , " + this.size.y);
	}
	
	public void update(int direction) {
		if (direction >= 0 && direction < 4) {
			drawRect.left = direction * spriteWidth;
			drawRect.right = drawRect.left + spriteWidth;
	
			drawRect.top = currentFrame/sheetWidth * spriteHeight;
			drawRect.bottom = drawRect.top + spriteHeight;
		}
	}

	public void draw() {
		if (imgAvail) {
			int drawX = (int) (this.loc.x - (this.size.x));
			int drawY = (int) (this.loc.y - (this.size.y));
			Rect dest = new Rect(drawX, drawY, drawX + spriteWidth, drawY + spriteHeight);
			canvas.drawBitmap(spriteSheet, drawRect, dest, null);
		}
	}

	public boolean hasIntersected(MazeElement mazeElement) {
//		super.hasIntersected(rectangle);
		boolean intersected = false;

		if (((this.loc.x + this.size.x >= mazeElement.loc.x) && (this.loc.x - this.size.x <= (mazeElement.loc.x + mazeElement.size.x))) && 
			((this.loc.y + this.size.y >= mazeElement.loc.y) && (this.loc.y - this.size.y <= (mazeElement.loc.y + mazeElement.size.y)))) {
				// figure out the half-width and half-height size of the rectangle (create a coord with this data)
				Coord rectCenter = new Coord((mazeElement.loc.x + (mazeElement.size.x/2)),(mazeElement.loc.y + (mazeElement.size.y/2)));
				// figure out the distance from the center of the rectangle to one of its edges (create a coord with this data)
				float rectTangent = rectCenter.distance(new Coord(mazeElement.loc.x + mazeElement.size.x, mazeElement.loc.y + mazeElement.size.y));
				// figure out the distance between the center of the circle and rectangle
				float centerDistance = this.loc.distance(rectCenter);
				// if distance between the center of the circle and rectangle is smaller than the circle radius plus the distance from rectangle center of rect to its edge 
				if (centerDistance < rectTangent + this.size.x) {

					// if the circle is overlapping with the maze element less on the horizontal axis
					if ( Math.abs(rectCenter.x - this.loc.x) > Math.abs(rectCenter.y - this.loc.y)) {
						// set the projection vector to be horizontal
						if (this.loc.x - rectCenter.x > 0 ) colProjection = new Coord(1, 0);						
						if (this.loc.x - rectCenter.x < 0) colProjection = new Coord(-1, 0);						
					// otherwise, if it overlaps more on the vertical axis
					} else {
						if (this.loc.y - rectCenter.y > 0) colProjection = new Coord(0, 1);						
						if (this.loc.y - rectCenter.y < 0) colProjection = new Coord(0, -1);						
					}

					intersected = true;
					return intersected;
				}
		} 
		return intersected;
	}

		
	public Coord intersectProjection() {
		return colProjection;
	}

	public boolean hasIntersected(BumperCar circle) {
		boolean intersected = false;
		if(this.loc.distance(new Coord(circle.loc.x, circle.loc.y)) < (this.size.x + circle.size.x)) 
			intersected = true;
		return intersected;
	}
	
	public boolean pointHasIntersected(float x, float y) {
		boolean intersected = false;
		if(this.loc.distance(new Coord(x, y)) < this.size.x) intersected = true;
		return intersected;
	}

}
