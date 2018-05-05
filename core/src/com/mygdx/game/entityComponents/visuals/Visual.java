package com.mygdx.game.entityComponents.visuals;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.utils.Const;

public abstract class Visual implements Disposable {

	public short renderLayer = Const.RenderLayer.DEFAULT;
	
	/**
	 * Wenns mehrere Sprites gibt dann kann man damit jeden abfragen
	 * @param index
	 * @return
	 */
	public abstract Sprite get(int index);
	
	/**
	 * Gets the first sprite
	 * @return
	 */
	public final Sprite get() {
		return get(0);
	}
	
	public abstract float getWidth();
	
	public abstract float getHeight();

	public abstract void dispose();
	
	public abstract int getNumberOfSprites();
	
	public Vector2 getCenter() {
		if(get() == null)
			return new Vector2();
		return new Vector2(get().getX()+getWidth()/2f,get().getY()+getHeight()/2f);
	}
}
