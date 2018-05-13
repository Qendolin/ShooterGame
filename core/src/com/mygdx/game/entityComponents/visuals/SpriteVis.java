package com.mygdx.game.entityComponents.visuals;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.entityComponents.VisualComp;

public class SpriteVis extends Visual {

	private Sprite sprite;
	
	public SpriteVis(Texture tex) {
		sprite = new Sprite(tex);
	}
	
	public SpriteVis(Sprite sprite) {
		this.sprite = sprite;
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

	@Override
	public Sprite get(int index) {
		return sprite;
	}

	@Override
	public int getNumberOfSprites() {
		// TODO Auto-generated method stub
		return 0;
	}

}
