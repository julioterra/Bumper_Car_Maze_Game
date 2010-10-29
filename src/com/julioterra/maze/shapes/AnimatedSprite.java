package com.julioterra.maze.shapes;

import game.elements.Coord;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class AnimatedSprite extends Sprite {

	protected Bitmap spriteSheet;

	protected Rect drawRect;
	
	public int fps;
	public int numFrames;
	public int currentFrame;
	
	public long frameTimer;
	
	public int spriteHeight;
	public int spriteWidth;

	public int sheetWidth;
	public int sheetHeight;

	protected AnimatedSprite() {
		
	}

	//Constructor for single line sprite sheet not animated
	public AnimatedSprite(int spriteSheetId, Coord pos, int frameCount, Coord size) {
		drawRect = new Rect(0, 0, 0, 0);
		this.frameTimer = 0;
		this.currentFrame = 0;
		
		this.loc = new Coord(pos.x, pos.y);
		spriteSheet = BitmapFactory.decodeResource(context.getResources(), spriteSheetId);
		this.spriteHeight = spriteSheet.getHeight();
		this.spriteWidth = spriteSheet.getWidth()/frameCount;
		drawRect.top = 0;
		drawRect.bottom = spriteHeight;
		drawRect.left = 0;
		drawRect.right = spriteWidth;
		this.fps = 1000 / 25;
		this.numFrames = frameCount;
		
		this.sheetWidth = frameCount;
		this.sheetHeight = 1;
		this.imgAvail = true;
		this.size = new Coord(size.x, size.y);
		System.out.println("bumber car constructor view 2 - animated sprite " + this.size.x + " , " + this.size.y);
		
	}
	
	//Constructor for single line sprite sheet
	public AnimatedSprite(int spriteSheetId, Coord pos, int fps, int frameCount, Coord size) {
		drawRect = new Rect(0, 0, 0, 0);
		this.frameTimer = 0;
		this.currentFrame = 0;
		
		this.loc = new Coord(pos.x, pos.y);
		this.spriteSheet = BitmapFactory.decodeResource(context.getResources(), spriteSheetId);
		this.spriteHeight = spriteSheet.getHeight();
		this.spriteWidth = spriteSheet.getWidth()/frameCount;
		drawRect.top = 0;
		drawRect.bottom = spriteHeight;
		drawRect.left = 0;
		drawRect.right = spriteWidth;
		this.fps = 1000 / fps;
		this.numFrames = frameCount;
		
		this.sheetWidth = frameCount;
		this.sheetHeight = 1;
		this.imgAvail = true;
		this.size = new Coord(size.x, size.y);
	}
	

	//Constructor for multi line sprite sheet
	public AnimatedSprite(int spriteSheetId, Coord pos, int sheetWidth, int sheetHeight, int fps, int frameCount) {
		drawRect = new Rect(0, 0, 0, 0);
		this.frameTimer = 0;
		this.currentFrame = 0;
		
		this.loc = new Coord(pos.x, pos.y);
		spriteSheet = BitmapFactory.decodeResource(context.getResources(), spriteSheetId);
		this.spriteHeight = spriteSheet.getHeight()/sheetHeight;
		this.spriteWidth = spriteSheet.getWidth()/sheetWidth;

		System.out.println("spriteHeight: " + spriteHeight);
		System.out.println("spriteWidth: " + spriteWidth);
		
		drawRect.top = 0;
		drawRect.bottom = spriteHeight;
		drawRect.left = 0;
		drawRect.right = spriteWidth;
		this.fps = 1000 / fps;
		numFrames = frameCount;

		this.sheetWidth = sheetWidth;
		this.sheetHeight = sheetHeight;
		this.imgAvail = true;
		this.size = new Coord(size.x, size.y);

	}

	public void update(long GameTime) {
		if (GameTime > frameTimer + fps) {
			frameTimer = GameTime;
			currentFrame += 1;

			if (currentFrame >= numFrames) {
				currentFrame = 0;
			}
		}

		drawRect.left = currentFrame%sheetWidth * spriteWidth;
		drawRect.right = drawRect.left + spriteWidth;

		drawRect.top = currentFrame/sheetWidth * spriteHeight;
		drawRect.bottom = drawRect.top + spriteHeight;
	}
	
	public void draw() {
		if (imgAvail) {
			Rect dest = new Rect((int)loc.x, (int)loc.y, (int)loc.x + (int)size.x, (int)loc.y + (int)size.y);
			canvas.drawBitmap(spriteSheet, drawRect, dest, null);
		}
	}

	public void draw(float offSetX, float offSetY) {
		offSetX = offSetX + this.loc.x;
		offSetY = offSetY + this.loc.y;
		if (imgAvail) {
			Rect dest = new Rect((int)offSetX, (int)offSetY, (int)offSetX + (int)size.x, (int)offSetY + (int)size.y);
			canvas.drawBitmap(spriteSheet, drawRect, dest, null);
			System.out.println("animated sprite loc - x: " + offSetX + " , " + offSetY + " size: " + (spriteWidth) + " , " + (spriteHeight) );
		}
	}
}
