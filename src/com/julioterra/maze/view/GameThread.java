package com.julioterra.maze.view;

import game.elements.GameElement;
import game.elements.GameStatus;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

	private long mLastTime;

	private boolean mRun = false;
	
	private SurfaceHolder mSurfaceHolder;

	public void setup(){};
	public void update(){};
	public void draw(Canvas c){};

	public GameThread(Context context, Handler handler) {
		GameElement.context = context;

	}

	public void doStart() {
		synchronized (mSurfaceHolder) {
			mLastTime = System.currentTimeMillis() + 100;
		}
	}

	public void pause() {
		synchronized (mSurfaceHolder) {
//			if (mMode == STATE_RUNNING) 
//				setState(STATE_PAUSE);
		}
	}

	@Override
	public void run() {
		setup();
		
		while (mRun) {
			Canvas c = null;				 
			try {
				c = mSurfaceHolder.lockCanvas(null);

				synchronized (mSurfaceHolder) {
					GameElement.canvas = c;
					updateThread();
					if(c != null)
						draw(c);

				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (c != null) {
					mSurfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}


	private void updateThread() {
		long now = System.currentTimeMillis();
		GameElement.time_now = System.currentTimeMillis();
		GameStatus.updateTimer();

		if((mLastTime > now) || GameElement.isPause)
			return;
		
		GameElement.time_elapsed_sec_percent = (now - mLastTime) / 1000.0f;
		GameElement.millis_elapsed = ((int)(now - mLastTime));
		mLastTime = now;

		// set-speed setting variable here
		if (GameElement.time_elapsed_sec_percent < 0.2f) GameElement.speedTimeFactor = (float)(GameElement.time_elapsed_sec_percent);
		else GameElement.speedTimeFactor = 0.2f;

		update();		
	}
	
	public void unpause() {
		synchronized (mSurfaceHolder) {
			mLastTime = System.currentTimeMillis() + 100;
		}	
	}
	
	
	public SurfaceHolder getMSurfaceHolder() {
		return mSurfaceHolder;
	}

	public void setMSurfaceHolder(SurfaceHolder surfaceHolder) {
		mSurfaceHolder = surfaceHolder;
	}

	public void begin(){
		mRun = true;
	}
	
	public void end(){
		mRun = false;
	}

}
