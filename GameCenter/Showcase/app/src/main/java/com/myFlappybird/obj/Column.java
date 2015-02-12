package com.myFlappybird.obj;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.jack.showcase.R;
import com.myFlappybird.config.Parameters;
import com.myFlappybird.config.ScreenSize;

public class Column extends GameObject {
	
	private Bitmap columnImg;	
	private Rect rectTop;
	private Rect rectBottom;
	
	private float groundHeight;
	private Random random;
	
	public Column(Resources resources, float x, float groundHeight) {
		super(resources);
		rectTop = new Rect();
		rectBottom = new Rect();
		this.groundHeight = groundHeight;
		random = new Random();
		this.obj_mid_x = x;
		this.obj_mid_y = random.nextInt((int)(ScreenSize.SCREEN_HEIGHT - this.groundHeight - Parameters.COLUMN_Y_GAP * 2)) + Parameters.COLUMN_Y_GAP;
		initPicture();
	}

	@Override
	public void refresh() {
		this.obj_mid_x -=Parameters.SPEED;
		if(this.obj_mid_x <= -(Parameters.COLUMN_X_GAP - this.obj_width / 2)) {
			this.obj_mid_x = Parameters.COLUMN_X_GAP * 2 + this.obj_width / 2;
			this.obj_mid_y = random.nextInt((int)(ScreenSize.SCREEN_HEIGHT - this.groundHeight - Parameters.COLUMN_Y_GAP * 2)) + Parameters.COLUMN_Y_GAP;
		}
	}

	@Override
	public void paintObj(Canvas canvas) {
		canvas.drawBitmap(columnImg, obj_mid_x - this.obj_width / 2, obj_mid_y - this.obj_height / 2, paint);
	}

	@Override
	public void initPicture() {
		columnImg = BitmapFactory.decodeResource(resources, R.drawable.column);
	
		this.obj_width = columnImg.getWidth();
		this.obj_height = columnImg.getHeight();
	}

	@Override
	public void release() {
		if(!columnImg.isRecycled()) {
			columnImg.recycle();
		}
	}
	
	public Rect getRectTop() {
		this.rectTop.set((int)(obj_mid_x - obj_width / 2), 0, (int)(obj_mid_x + obj_width / 2), (int)(obj_mid_y - Parameters.COLUMN_Y_GAP / 2));
		return this.rectTop;
	}
	
	public Rect getRectBottom() {
		this.rectBottom.set((int)(obj_mid_x - obj_width / 2), (int)(obj_mid_y + Parameters.COLUMN_Y_GAP / 2), (int)(obj_mid_x + obj_width / 2), (int)ScreenSize.SCREEN_HEIGHT);
		return this.rectBottom;
	}
	
}
