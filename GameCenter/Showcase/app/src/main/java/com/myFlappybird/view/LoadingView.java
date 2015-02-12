package com.myFlappybird.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import com.example.jack.showcase.R;
import com.myFlappybird.config.Parameters;
import com.myFlappybird.config.ScreenSize;
import com.myFlappybird.util.SoundPlayer;

public class LoadingView extends BaseView {
	
	private Bitmap bgImg;
	private Bitmap textImg;
	private Bitmap logoImg;
	
	private String owner = "finished in 2014";
	private Rect rect;
	
	private float textImgX;
	private float textImgY;
	private float logoImgX;
	private float logoImgY;

	private float strWidth;
	private float strHeight;
	private float textX;
	private float textY;
	
	public LoadingView(Context context, SoundPlayer soundPlayer) {
		super(context, soundPlayer);
		rect = new Rect();
		this.thread = new Thread(this);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		super.surfaceCreated(arg0);
		initBitmap();
		if(this.thread.isAlive()) {
			this.thread.start();
		} else {
			this.thread = new Thread(this);
			this.thread.start();
		}
	}
	
	@Override
	public void initBitmap() {
		this.bgImg = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
		this.logoImg = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
		this.textImg = BitmapFactory.decodeResource(getResources(), R.drawable.text_logo);
		
		this.scaleX = ScreenSize.SCREEN_WIDTH / this.bgImg.getWidth();
		this.scaleY = ScreenSize.SCREEN_HEIGHT / this.bgImg.getHeight();
		
		this.textImgX = (ScreenSize.SCREEN_WIDTH - this.textImg.getWidth()) / 2;
		this.textImgY = ScreenSize.SCREEN_HEIGHT / 2 - this.textImg.getHeight() * 2;
		
		this.logoImgX = (ScreenSize.SCREEN_WIDTH - this.logoImg.getWidth()) / 2;
		this.logoImgY = ScreenSize.SCREEN_HEIGHT / 2 - this.logoImg.getWidth() * 0;
	
		this.paint.setTextSize(40);
		this.paint.getTextBounds(owner, 0, owner.length(), rect);
		
		this.strWidth = rect.width();
		this.strHeight = rect.height();
		
		this.textX = ScreenSize.SCREEN_WIDTH / 2 - this.strWidth / 2;
		this.textY = ScreenSize.SCREEN_HEIGHT / 2 + logoImg.getHeight() + this.strHeight * 2;
	}

	@Override
	public void run() {
		while(this.threadFlag) {
			paintView();
			try {
				Thread.sleep(Parameters.LOADING_GAME_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.threadFlag = false;
		}
		mainActivity.getHandler().sendEmptyMessage(Parameters.TO_MAIN_VIEW);
	}

	@Override
	public void paintView() {
		try {
			canvas = sfh.lockCanvas();
			canvas.save();
			canvas.scale(this.scaleX, this.scaleY);
			canvas.drawBitmap(bgImg, 0, 0, paint);
			canvas.restore();
			canvas.drawBitmap(textImg, textImgX, textImgY, paint);
			canvas.drawBitmap(logoImg, logoImgX, logoImgY, paint);
			canvas.drawText(owner, textX, textY, paint);
		} catch(Exception err) {
			err.printStackTrace();
		} finally {
			if(canvas != null) {
				sfh.unlockCanvasAndPost(canvas);
			}
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		super.surfaceDestroyed(arg0);
		release();
	}
	
	@Override
	public void release() {
		if(!this.bgImg.isRecycled()){
			this.bgImg.recycle();
		}
		if(!this.logoImg.isRecycled()){
			this.logoImg.recycle();
		}
		if(!this.textImg.isRecycled()){
			this.textImg.recycle();
		}
	}
	
}
