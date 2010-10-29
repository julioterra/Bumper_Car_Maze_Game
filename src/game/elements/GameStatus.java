package game.elements;

public class GameStatus extends GameElement {

	public static float gameScore; 	// game score equals lives multiplied by candy and remaining time 
	public static long timeStart;
	public static long timeLimit;
	public static long timePassed;
	public static boolean gameStart;
	public static boolean gameOver;
	public static boolean gameWon;	
	public static int gameLevel;	
	public static int health;
	public static int candyCurrent;
	public static int candyGoal;

	public static int healthLimit;


	
	public static void init(int _candyGoal) {
		timeStart = System.currentTimeMillis();
		timeLimit = 60000;
		timePassed = timeLimit;
		gameStart = true;
		gameOver = false;
		gameWon = false;
		gameLevel = 1;
		health = 0;
		healthLimit = 3;
		candyGoal = _candyGoal;
		candyCurrent = 0;
	}

	public static void reset() {
		timeStart = System.currentTimeMillis();
		gameScore = 0;
		timeStart = 0;
		timeLimit = 60000;
		timePassed = timeLimit;
		gameStart = false;
		gameOver = false;
		gameWon = false;
		gameLevel = 0;
		health = 0;
		candyGoal = 0;
		candyCurrent = 0;
	}

	public static int calculateScore() {
		gameScore = health * (timePassed/1000f) * candyCurrent;
		return (int)(gameScore);
	}

	public static void updateTimer() {
//		if (gameStart == true && gameOver == false) {
		System.out.println("updateTimer - " + timePassed  + " game Element " + GameElement.time_now);
			timePassed = (GameElement.time_now - timeStart);
			if (timePassed >= timeLimit) {
				gameOver = true;
				gameWon = false;
			}
//		}
	}

	public static void hitParents() {
//		System.out.println("gameStatus - parents were hit " + health + " times");
		health ++;
		if (health > healthLimit) {
			gameOver = true;
			gameWon = false;
		}
	}
	
	public static void captureCandy() {
//		System.out.println("gameStatus - candies were captured " + health + " times");
		candyCurrent++;
		if (candyCurrent >= candyGoal) {
			gameOver = true;
			gameWon = true;
		}
	}	
	
}
