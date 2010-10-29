package com.julioterra.maze.view;

import game.elements.GameElement;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

	/** The thread that actually draws the animation */
	private GameThread thread;

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// register our interest in hearing about changes to our surface
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);

		setFocusable(true); // make sure we get key events
	}

	public void setThread(GameThread gameThread) {
		this.thread = gameThread;
		thread.setMSurfaceHolder(getHolder());
	}


	public GameThread getThread() {
		return thread;
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		if (!hasWindowFocus)
			thread.pause();
	}

	/* Callback invoked when the surface dimensions change. */
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	/*
	 * Callback invoked when the Surface has been created and is ready to be
	 * used.
	 */
	public void surfaceCreated(SurfaceHolder holder) {
		thread.begin();
		thread.start();

	}

	/*
	 * Callback invoked when the Surface has been destroyed and must no longer
	 * be touched. WARNING: after this method returns, the Surface/Canvas must
	 * never be touched again!
	 */
	public void surfaceDestroyed(SurfaceHolder holder) {
		thread.end();
	}
	
	@Override
	public boolean onTrackballEvent(MotionEvent event){
    	if(!GameElement.isPause){

    		
    	}
		return true;
	}


}
