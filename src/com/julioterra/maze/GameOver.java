package com.julioterra.maze;

	import game.elements.GameStatus;
import android.app.Activity;
	import android.content.Intent;
	import android.os.Bundle;
	import android.view.View;
	import android.view.View.OnClickListener;
import android.widget.Button;

public class GameOver extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameover);
//		GameStatus.reset();
		
		Button startButton = (Button) findViewById(R.id.startButton);

		startButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(GameOver.this, Game.class);
				startActivity(intent);
				finish();
			}
		});
	}
	
}
