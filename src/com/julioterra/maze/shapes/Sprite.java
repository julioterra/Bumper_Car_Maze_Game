package com.julioterra.maze.shapes;

import game.elements.Coord;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Sprite extends Shapes{
	
	public Bitmap img;
	public boolean imgAvail;
	
	public Sprite() {
		super();
	}
	
	public Sprite(int imgInt, Coord loc, float sizeX, float sizeY) {
		super(new Coord(loc.x, loc.y), sizeX, sizeY);		
		img = BitmapFactory.decodeResource(context.getResources(), imgInt);
		this.loc = new Coord(loc.x, loc.y);
		imgAvail = true;
	}	

	public Sprite(Coord loc, float sizeX, float sizeY) {
		super(new Coord(loc.x, loc.y), sizeX, sizeY);		
		this.loc = new Coord(loc.x, loc.y);
		imgAvail = false;
	}	

	public void update(){
		
	}
	
	public void draw()  {
		super.draw();
		if (imgAvail)
			canvas.drawBitmap(img, loc.x, loc.y, paint);
	}

	public void draw(float offSetX, float offSetY) {
		offSetX = offSetX + this.loc.x;
		offSetY = offSetY + this.loc.y;
		if (imgAvail)
			canvas.drawBitmap(img, offSetX, offSetY, paint);
	}

	public static Bitmap loadBitMap(int i){
		return BitmapFactory.decodeResource(context.getResources(), i);
	}
}
