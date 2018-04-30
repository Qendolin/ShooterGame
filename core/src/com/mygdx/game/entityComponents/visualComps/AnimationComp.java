package com.mygdx.game.entityComponents.visualComps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entityComponents.VisualComp;

public class AnimationComp extends VisualComp {

	private Animation<Sprite> anim;
	private Texture spriteSheet;
	private float stateTime;
	
	public AnimationComp(Texture spriteSheet, int cols, int rows, float frameDuration) {
		this.spriteSheet = spriteSheet;
		TextureRegion[][] temp = TextureRegion.split(spriteSheet, 
				spriteSheet.getWidth() / cols,
				spriteSheet.getHeight() / rows);
		Sprite[] frames = new Sprite[cols * rows];
		int index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				frames[index++] = new Sprite(temp[i][j]);
			}
		}

		anim = new Animation<Sprite>(frameDuration, frames);
	}
	
	/**
	 * Remember to dispose!
	 * @param anim
	 */
	public AnimationComp(Animation<Sprite> anim) {
		this.anim = anim;
	}
	
	@Override
	public Sprite get() {
		stateTime+=Gdx.graphics.getDeltaTime();
		return anim.getKeyFrame(stateTime, true);
	}

	@Override
	public float getWidth() {
		return anim.getKeyFrame(stateTime).getWidth();
	}

	@Override
	public float getHeight() {
		return anim.getKeyFrame(stateTime).getHeight();
	}

	@Override
	public void dispose() {
		if(spriteSheet != null)
			spriteSheet.dispose();
	}

}
