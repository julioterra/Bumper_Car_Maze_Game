package game.elements;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;

public class GameElement {

	public static Context context;
	public static Canvas canvas;
	public static Activity currentActivity;

	public static float time_elapsed_sec_percent = 0f;
	public static float speedTimeFactor = 0f;
	public static float millis_elapsed;
	public static long time_now;

	public static boolean isPause = false;

}
