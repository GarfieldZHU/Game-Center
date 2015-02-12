package com.myFlappybird.obj;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class GameObject {

	protected Resources resources;
	protected Paint paint;
	protected int currentFrame;//当前动画帧
	
	protected float obj_x;//左上角坐标
	protected float obj_y;
	protected float obj_width;//宽度
	protected float obj_height;//高度
	protected float obj_mid_x;//中心横坐标
	protected float obj_mid_y;//中心纵坐标

	public GameObject(Resources resources) {
		this.resources = resources;
		paint = new Paint();
	}
	
	//初始化图片对象
	public abstract void initPicture();
	
	//画出对象
	public abstract void paintObj(Canvas canvas);
	
	//释放图片资源
	public abstract void release();
	
	//对象运动逻辑
	public abstract void refresh();
		
	
	public void setObjX(float x) {
		this.obj_x = x;
	}
	
	public float getObjX() {
		return this.obj_x;
	}
	
	public void setObjY(float y) {
		this.obj_y = y;
	}
	
	public float getObjY() {
		return this.obj_y;
	}
	
	public void setObjMidX(float x) {
		this.obj_mid_x = x;
	}
	
	public float getObjMidX() {
		return this.obj_mid_x;
	}
	
	public void setObjMidY(float y) {
		this.obj_mid_y = y;
	}
	
	public float getObjMidY() {
		return this.obj_mid_y;
	}
	
	public void setObjWidth(float w) {
		this.obj_width = w;
	}
	
	public float getObjWidth() {
		return this.obj_width;
	}
	
	public void setObjHeight(float h) {
		this.obj_height = h;
	}
	
	public float getObjHeight() {
		return this.obj_height;
	}
	
}
