package com.julioterra.maze.shapes;

import java.util.ArrayList;
import java.util.List;
import com.julioterra.maze.R;
import android.graphics.Color;
import game.elements.Coord;
import game.elements.GameElement;
import game.elements.GameStatus;

public class Maze extends Shapes {
	private List<MazeElement> mazeStructure = new ArrayList<MazeElement>();  
	private List<MazeElement> mazeElements = new ArrayList<MazeElement>();  
	public int elementSize;
	public int direction;							// holds whether maze is moving horizontally or vertically
	public float speed;
	public int[] colorArray;
	public float screenWidth;
	public float screenHeight;
	public float speedPerSecond;

	static final int MOVE_RIGHT = 0;
	static final int MOVE_DOWN = 1;
	static final int MOVE_LEFT = 2;
	static final int MOVE_UP = 3;
	

	public Maze(int elementSize, float screenWidth, float screenHeight) {
		super(new Coord(0,0), elementSize);
		this.colorArray = new int[6];
		this.initColors();
		this.elementSize = elementSize; 
		this.initMaze();
		this.direction = 0;
		this.speed = 0;
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.speedPerSecond = 160f;
		this.loc.y = (float) (this.screenHeight/2) - (this.elementSize*2 + this.elementSize/2);
		this.loc.x = (float) (this.screenWidth/2) - (this.elementSize*2 + this.elementSize/2);

	}

	
	// **********************************************************************************
	// INITIALIZATION METHODS
	
	// initialize the colorArray with the color for each type of element in the maze
	public void initColors() {
		colorArray[0] = Color.argb(255, 0, 0, 0);
		colorArray[1] = Color.argb(255, 0, 0, 255);
		colorArray[2] = Color.argb(255, 0, 100, 255);
		colorArray[3] = Color.argb(255, 100, 0, 255);
		colorArray[4] = Color.argb(255, 0, 100, 255);
		colorArray[5] = Color.argb(255, 255, 0, 0);
	}

	// read the maze structure from a text file and create an array that holds all necessary objects.
	public void initMaze() {
		mazeStructure = new ArrayList<MazeElement>();
		mazeElements = new ArrayList<MazeElement>();
		
		String[] mazeLines = context.getResources().getStringArray(R.array.maze_three);

		// figure out the size of the maze by checking the size of the array
		this.size.y = mazeLines.length * this.elementSize;
		String mazeCols[] = mazeLines[0].split(",");
		this.size.x = mazeCols.length * this.elementSize;
		
		// loop through each line on the array to read it in
		for (int i=0; i < mazeLines.length; i++) {
			String elements[] = mazeLines[i].split(",");
			MazeElement mazeElement;			

			// loop through each element in a line to read it in
			for (int e=0; e < elements.length; e++) {
				if (Integer.parseInt(elements[e]) > 0) {					
					if (Integer.parseInt(elements[e]) == 1) {
						mazeElement = new MazeWall(R.drawable.game_wall, new Coord(e*this.elementSize, i*this.elementSize), 6, new Coord (this.elementSize, this.elementSize));
						mazeStructure.add(mazeElement);
					}
					else if (Integer.parseInt(elements[e]) == 2) {
						mazeElement = new MazeParents(R.drawable.game_parents, new Coord(e*elementSize, i*elementSize), 6, new Coord (elementSize, elementSize));
						mazeStructure.add(mazeElement);						
					}
					else if (Integer.parseInt(elements[e]) == 3) {
						mazeElement = new MazeCandy(R.drawable.game_candy, new Coord(e*elementSize, i*elementSize), 1, new Coord (elementSize, elementSize));
						mazeElements.add(mazeElement);
					}
				}					
			}
		}	
		
		GameStatus.init(mazeElements.size());

	}


	
	// ************************************************************************************
	// MOVE METHODS

	// Move the maze based on current direction and current speed
	public void moveAuto() {
		if (GameStatus.gameOver == true) return;
		this.speed = GameElement.speedTimeFactor * this.speedPerSecond;
		
		if (this.direction == MOVE_RIGHT) this.move(new Coord((this.speed*-1),0));
		else if (this.direction == MOVE_DOWN) this.move(new Coord(0, (this.speed*-1)));
		else if (this.direction == MOVE_LEFT) this.move(new Coord((this.speed),0));
		else if (this.direction == MOVE_UP) this.move(new Coord(0, (this.speed)));			
	}

	// adjust speed based on how much time has passed since last update
	public void setSpeed(float percentSecPassed) {
		this.speed = percentSecPassed * this.speedPerSecond;
	}
	

	
	// **************************************************************************************
	// DIRECTION METHODS
	
	// reverses the direction of the bumper car
	public void reverseDirection() {
		if (this.direction == MOVE_RIGHT) this.direction = MOVE_LEFT;
		else if (this.direction == MOVE_DOWN) this.direction = MOVE_UP;
		else if (this.direction == MOVE_LEFT) this.direction = MOVE_RIGHT;
		else if (this.direction == MOVE_UP) this.direction = MOVE_DOWN;			
	}
	
	// change direction that specified by the input argument
	public void changeDirection(int direction) {
		this.direction = direction;
	}

	public void changeDirection(Coord projectionVector) {
		if (projectionVector.x > 0) { this.direction = MOVE_RIGHT; }
		else if (projectionVector.x < 0) { this.direction = MOVE_LEFT; }
		else if (projectionVector.y > 0) { this.direction = MOVE_DOWN; }
		else if (projectionVector.y < 0) { this.direction = MOVE_UP; }
	}
	
	public void shiftDirection() {
		if (this.direction >= 3) this.direction = 0;			
		else this.direction = this.direction + 1;
	}

	// get the current direction of the game play
	public int getDirection() {
		return this.direction;
	}

	
	// ************************************************************************************
	// COLLISION METHODS

	public void checkColAndDraw(BumperCar bumperCarPass) {
		// set the already intersected variable to false
		if (GameStatus.gameOver == true) return;
		
		boolean alreadyIntersected = false;

		// determine the location of the bumper car relative to the maze and create a bumper car object
		Coord adjustedLoc = new Coord((bumperCarPass.loc.x + (this.loc.x * -1)) , (bumperCarPass.loc.y + (this.loc.y * -1)));
		BumperCar bumperCar = new BumperCar(adjustedLoc, (int)(bumperCarPass.size.x));

		// set the paint color then draw the background of the maze
		paint.setColor(Color.argb(255, 255, 255, 255));
		canvas.drawRect(this.loc.x, this.loc.y, this.loc.x + this.size.x, this.loc.y + this.size.y, paint);

		// loop through each element in the mazeStructure array to check for collisions with maze WALL and SPIKE elements, and draw these same elements
		for (int i = 0; i < this.mazeStructure.size(); i++) {				

			
			MazeElement mazeStructureElement = mazeStructure.get(i);				
			
			// if the collision status is now set to true then add one to the appropriate mazeStatus array element
			if (bumperCar.hasIntersected(mazeStructureElement) && alreadyIntersected == false) {  
				this.changeDirection(bumperCar.intersectProjection());			// change direction of movement to move in direction with the least overlap
				alreadyIntersected = true;										// set already intersect variable to true
				if (mazeStructureElement.elementType == 2) {
					GameStatus.hitParents();
				}
	// ***********************************************
	// ****** ADD CODE TO UPDATE LIFE HERE ***********
	// ***********************************************
			
			} 
			// draw the mazeStructure elements
			mazeStructureElement.update(GameElement.time_now);
			mazeStructureElement.draw(this.loc.x, this.loc.y);
		}


		alreadyIntersected = false;
		if (this.mazeElements.size() != 0 || this.mazeElements != null) {
			// loop through each element in the mazeElements array to check for collisions with maze STAR elements, and draw these same elements
			for (int i = this.mazeElements.size()-1; i >= 0 ; i--) {				
				MazeElement mazeElement = mazeElements.get(i);				
				// if the collision status is now set to true then add one to the appropriate mazeStatus array element
				if (bumperCar.hasIntersected(mazeElement) && alreadyIntersected == false) {  
					alreadyIntersected = true;										// set already intersect variable to true
					mazeElements.remove(i);
					GameStatus.captureCandy();
	// ***********************************************
	// ****** ADD CODE TO UPDATE SCORE HERE
	// ***********************************************
				
				} else {
				// draw the mazeStructure elements
				mazeElement.draw(this.loc.x, this.loc.y);
				}
			}
		}
	}

	
	// ************************************************************************************
	// DRAW METHODS

	// function that draws all maze WALL and SPIKE elements
	public void draw() {
		for (int i = 0; i < this.mazeStructure.size(); i++) {
			MazeElement mazeElement = mazeStructure.get(i);
			paint.setColor(mazeElement.getColor());
			mazeElement.draw(this.loc.x, this.loc.y);
		}
	}
	
}
