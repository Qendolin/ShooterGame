package com.mygdx.game.entityComponents.visualComps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.entityComponents.VisualComp;

public class SpriteComp extends VisualComp {

	private Sprite sprite;
	
	public SpriteComp(Texture tex) {
		sprite = new Sprite(tex);
	}
	
	public SpriteComp(Sprite sprite) {
		this.sprite = sprite;
	}
	
	@Override
	public Sprite get() {
		return sprite;
	}

	@Override
	public float getWidth() {
		return sprite.getWidth()*sprite.getScaleX();
	}

	@Override
	public float getHeight() {
		return sprite.getHeight()*sprite.getScaleY();
	}


	@Override
	public void dispose() {
		sprite.getTexture().dispose();
	}

}
