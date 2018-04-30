package com.mygdx.game.entityComponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;

public abstract class VisualComp implements Component, Disposable {

	public abstract Sprite get();
	
	public abstract float getWidth();
	public abstract float getHeight();

	public abstract void dispose();
	
}
