package com.mygdx.game.entityComponents;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public abstract class Visual implements Disposable {

	public abstract Sprite get();
	
	public abstract float getWidth();
	
	public abstract float getHeight();

	public abstract void dispose();
	
	public Vector2 getCenter() {
		if(get() == null)
			return new Vector2();
		return new Vector2(get().getX()+getWidth()/2f,get().getY()+getHeight()/2f);
	}
}
