package com.myFlappybird.obj;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.jack.showcase.R;
import com.myFlappybird.config.Parameters;
import com.myFlappybird.config.ScreenSize;

public class Ground extends GameObject {

	private Bitmap groundImg;
	
	private Rect obj_rect;
	
	public Ground(Resources resources) {
		super(resources);
		this.obj_rect = new Rect();
		this.obj_x = 0;
		initPicture();
	}

	@Override
	public void refresh() {
		this.obj_x -= Parameters.SPEED;
		if(this.obj_x <= -(this.obj_width - ScreenSize.SCREEN_WIDTH)) {
			this.obj_x = -15;
		}
	}

	@Override
	public void paintObj(Canvas canvas) {
		canvas.drawBitmap(groundImg, obj_x, obj_y, paint);
	}

	@Override
	public void initPicture() {
		groundImg = BitmapFactory.decodeResource(resources, R.drawable.ground);
		
		this.obj_width = groundImg.getWidth();
		this.obj_height = groundImg.getHeight();
		
		this.obj_y = ScreenSize.SCREEN_HEIGHT - this.obj_height;
	}

	@Override
	public void release() {
		if(!groundImg.isRecycled()) {
			groundImg.recycle();
		}
	}

	public Rect getRect() {
		obj_rect.set(0, (int)obj_y, (int)ScreenSize.SCREEN_WIDTH, (int)ScreenSize.SCREEN_HEIGHT);
		return this.obj_rect;
	}
	
}
