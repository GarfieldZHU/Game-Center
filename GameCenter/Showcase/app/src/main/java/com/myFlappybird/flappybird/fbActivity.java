package com.myFlappybird.flappybird;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.myFlappybird.config.Parameters;
import com.myFlappybird.util.SoundPlayer;
import com.myFlappybird.view.LoadingView;
import com.myFlappybird.view.MainView;

public class fbActivity extends Activity {

	private MainView mainView;//游戏主窗口
	private LoadingView loadingView;//游戏载入窗口
	private SoundPlayer soundPlayer;//音乐播放器
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.soundPlayer = new SoundPlayer(this);
		this.soundPlayer.initSounds();
		this.loadingView = new LoadingView(this, soundPlayer);
		this.setContentView(loadingView);
	}
	
	private Handler handler = new Handler() {
		
		 public void handleMessage(Message msg) {
			 if(msg.what == Parameters.TO_MAIN_VIEW) {
				 backToMainView();
			 }
			 if(msg.what == Parameters.END_GAME) {
				 exitGame();
			 }
		 }
		
	};

	public void backToMainView() {
		if(this.mainView == null) {
			this.mainView = new MainView(this, soundPlayer);
		} else {
			this.mainView = null;
			this.mainView = new MainView(this, soundPlayer);
		}
		this.setContentView(this.mainView);
		this.loadingView = null;
	}
	
	public void exitGame() {
		if(this.mainView != null) {
			this.mainView.setThreadFlag(false);
		}
		if(this.loadingView != null) {
			this.loadingView.setThreadFlag(false);
		}
		this.finish();
	}
	
	public Handler getHandler() {
		return this.handler;
	}
}
