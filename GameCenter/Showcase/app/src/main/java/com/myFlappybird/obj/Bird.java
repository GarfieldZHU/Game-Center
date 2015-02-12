package com.myFlappybird.obj;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.jack.showcase.R;
import com.myFlappybird.config.Parameters;
import com.myFlappybird.config.ScreenSize;

public class Bird extends GameObject {
	
	private final double v0 = Parameters.v0;
	private final double g = Parameters.g;
	private final double t = Parameters.t;
	private float groundHeight;
	
	private double speed;
	private double s;
	private float angle;
	
	private Bitmap[] birds;
	private Bitmap currentBird;
	
	private Rect obj_rect;
	
	public Bird(Resources resources, float groundHeight) {
		super(resources);
		this.obj_rect = new Rect();
		this.groundHeight = groundHeight;
		initPicture();
	}

	@Override
	public void refresh() {
		double v1 = speed;
		double v = v1 - g * t;
		speed = v;
		s= v1 * t - 0.5 * g * t * t;
		obj_y = obj_y - (float) s;
		if(obj_y <= -1 * this.obj_height - (this.obj_width - this.obj_height) / 2) {
			obj_y = -1 * this.obj_height - (this.obj_width - this.obj_height) / 2;
			speed = 0;
		}
		if(obj_y >= ScreenSize.SCREEN_HEIGHT - this.groundHeight - this.obj_height) {
			obj_y = ScreenSize.SCREEN_HEIGHT - this.groundHeight - this.obj_height;
		}
		if(speed >= 0) {
			currentBird = birds[(currentFrame / 3)];
			currentFrame++;
			if(currentFrame == 9) {
				currentFrame = 0;
			}
		} else {
			currentBird = birds[2];
		}
		angle = (float) (s * 4);
		if(angle >= 30) {
			angle = 30;
		}
		if(angle <= -90) {
			angle = -90;
		}
		
		this.obj_mid_y = this.obj_y + this.obj_height / 2;
		
		obj_rect.left = (int) (obj_x + (obj_width - obj_height) / 2);
		obj_rect.top = (int) (obj_y + (obj_width - obj_height) / 2);
		obj_rect.right = (int) (obj_rect.left + obj_height);	
		obj_rect.bottom = (int) (obj_rect.top + obj_height - (obj_width - obj_height) / 2);
	}


	@Override
	public void paintObj(Canvas canvas) {
		canvas.save();
		canvas.rotate(-angle, obj_mid_x, obj_mid_y);
		canvas.drawBitmap(currentBird, obj_x, obj_y, paint);
		canvas.restore();
	}

	@Override
	public void initPicture() {
		birds = new Bitmap[3];
		birds[0] = BitmapFactory.decodeResource(resources, R.drawable.bird0);
		birds[1] = BitmapFactory.decodeResource(resources, R.drawable.bird1);
		birds[2] = BitmapFactory.decodeResource(resources, R.drawable.bird2);
		
		currentBird = birds[0];
		
		this.obj_width = currentBird.getWidth();
		this.obj_height = currentBird.getHeight();
		
		this.obj_x = this.obj_width * 2;
		this.obj_y = ScreenSize.SCREEN_HEIGHT / 2 - this.obj_height / 2;
		
		this.obj_mid_x = this.obj_x + this.obj_width / 2;
		this.obj_mid_y = this.obj_y + this.obj_height / 2;
	}

	@Override
	public void release() {
		for(int i=0; i<3; i++) {
			if(!birds[i].isRecycled()) {
				birds[i].recycle();
			}
		}
	}

	public Rect getObjRect() {
		return this.obj_rect;
	}
	
	public void clicked() {
		speed = v0;
	}
	
	public boolean passColumn(Column column) {
		if(this.obj_mid_x <= column.getObjMidX() && column.getObjMidX() - this.obj_mid_x < 5) {
			return true;
		}
		return false;
	}
	
	public boolean hitColumn(Column column) {
		if(this.obj_rect.intersect(column.getRectTop()) || this.obj_rect.intersect(column.getRectBottom())) {
			return true;
		}
		return false;
	}
	
	public boolean hitGround(Ground ground) {
		if((this.obj_rect.bottom + 1 ) >= ground.getRect().top) {
			return true;
		}
		return false;
	}

}
