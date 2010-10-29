package com.julioterra.maze;

import game.elements.Coord;
import game.elements.GameStatus;

import com.julioterra.maze.view.GameView;
import com.julioterra.maze.view.GameThread;
import com.julioterra.maze.shapes.Maze;
import com.julioterra.maze.shapes.BumperCar;
import com.julioterra.maze.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;

public class Game extends Activity implements OnGestureListener {

	private GestureDetector gestureScanner;
	public GameView gameView;
	public GameThread gameThread;
	public Maze maze;					
	public int gameClosed;				// variable to ensure game only launches one win or lost activity
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// creates a gestureDetector object with using listener interface from this object
		gestureScanner = new GestureDetector(this);				

		// sets the content view by referencing the gamescreen.xml layout file
		setContentView(R.layout.gamescreen);					
		// initialize gameView object using the attributes outlined in the gamescreen.xml layout file
		gameView = (GameView) findViewById(R.id.gameView);	

		
		// initialize gameThread and declare an anonymous version of this class to redefine the draw, update and setup methods
		gameThread = new GameThread(this, new Handler()) 		
		{

			public int elementSize, mazeElementSize;
			public BumperCar bumperCar;
			
			public void setup() {

				// create a instance of display to capture the screen width and height
				Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay(); 
				int width = display.getWidth(); 
				int height = display.getHeight();
				
				// set the standard size of elements such as bumper and maze elements
				this.elementSize = 45;
				this.mazeElementSize = (int)(elementSize * 1.25); 
				
				// create an instance of the bumper car object
				this.bumperCar = new BumperCar(R.drawable.red_car, new Coord(width/2, height/2), 4, new Coord (elementSize/2, elementSize/2));
				// create the maze by passing it the maze element size, and screen width and height
				maze = new Maze(this.mazeElementSize, width, height);
				gameClosed = 0;  
					
			}		

			// update the game by checking if game is over and moving the auto
			public void update() {
				checkWin();
				maze.moveAuto();
				this.bumperCar.update(maze.getDirection());		// confirm direction of bumper car to set appropriate image
			}	
			
			// draw the maze and bumper to screen
			public void draw(Canvas c) {
				c.drawARGB(255, 0, 0, 0);				// background color
				maze.checkColAndDraw(bumperCar);		// check whether the bumper car has collided with a maze element and draw maze
				this.bumperCar.draw();					// draw the bumper car
			}				
			
			// check if the game has been won
			public void checkWin() {
				if (GameStatus.gameOver == true) {
					if (gameClosed == 0) {
						if (GameStatus.gameWon == true) {
							Intent intent = new Intent();
							intent.setClass(Game.this, GameOver.class);
							startActivity(intent);
						} else {
							Intent intent = new Intent();
							intent.setClass(Game.this, GameLost.class);
							startActivity(intent);
						}
						gameThread.end();
						finish();
					} 
					// increase the counter that ensures a new activity is not launched more than one
					gameClosed ++;
				}
			}
			
			
		};

		// link the gameThread to the gameView using the setThread method from surfaceView class
		// this method enables the gameView to call a method form the gameThread that links a handle 
		gameView.setThread(gameThread);
}

	public void onResume(Bundle savedInstanceState) {
		this.maze.initMaze();
	}
	
// ************************************************
// CALLBACK METHODS FOR ON-GESTURE-LISTENER INTERFACE
// ************************************************
	
public boolean onTouchEvent(MotionEvent me) {
	boolean result = gestureScanner.onTouchEvent(me);

	try {
		Thread.sleep(30);
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return result;
}



public void processTouch(Coord coord) {
	if(Math.abs(coord.x) > Math.abs(coord.y)) {
		if (coord.x > 0) maze.changeDirection(0);
		else maze.changeDirection(2);
	} else {
		if (coord.y > 0) maze.changeDirection(1);
		else maze.changeDirection(3);		
	}
//	if (coord.y < 200) maze.changeDirection(3);
//	else if (coord.y > 400) maze.changeDirection(1);
//	else if (coord.x > 250) maze.changeDirection(0);	
//	else if (coord.x < 150) maze.changeDirection(2);
}


public boolean onDown(MotionEvent e) {
	return true;
}

public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {	
	this.processTouch(new Coord(velocityX, velocityY));	
	return true;
}

public void onShowPress(MotionEvent e) {
}

public void onLongPress(MotionEvent e) {
}

public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
	return true;
}

public boolean onSingleTapUp(MotionEvent e) {
	return true;
}



}
