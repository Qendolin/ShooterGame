package com.mygdx.game.entityComponents.visualComps;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entityComponents.VisualComp;

public class SpriteSheetComp extends VisualComp {

	public HashMap<String, SpriteSheetSpriteGroup> animationGroups = new HashMap<String, SpriteSheetSpriteGroup>();
	public String currentAnimation = "";
	public boolean animate = true;
	private Sprite[] frames;
	private double lastLoopTime = System.currentTimeMillis() / 1000d;
	private Texture spriteSheet;
	private int currentFrame;

	/**
	 * If swapColsAndRows is true it will go through every row before advancing the column.
	 * @param spriteSheet
	 * @param cols
	 * @param rows
	 * @param swapColsAndRows
	 * @param groups
	 */
	public SpriteSheetComp(Texture spriteSheet, int cols, int rows, boolean swapColsAndRows, SpriteSheetSpriteGroup... groups) {
		TextureRegion[][] temp = TextureRegion.split(spriteSheet, 
				spriteSheet.getWidth() / cols,
				spriteSheet.getHeight() / rows);
		frames = new Sprite[cols * rows];
		int index = 0;
		if(swapColsAndRows) {
			for (int i = 0; i < cols; i++) {
				for (int j = 0; j < rows; j++) {
					frames[index++] = new Sprite(temp[j][i]);
				}
			}
		} else {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					frames[index++] = new Sprite(temp[i][j]);
				}
			}
		}
		this.spriteSheet = spriteSheet;
		for(SpriteSheetSpriteGroup group : groups) {
			animationGroups.put(group.name, group);
		}
	}
	
	/**
	 * 
	 * @param frame relative to the start frame of the animation
	 */
	public void setCurrentAnimationFrame(int frame) {
		SpriteSheetSpriteGroup anim = animationGroups.get(currentAnimation);
		currentFrame = anim.start + (frame % (anim.end - anim.start + 1));
	}
	
	@Override
	public Sprite get() {
		SpriteSheetSpriteGroup anim = animationGroups.get(currentAnimation);
		if(anim == null)
			return null;
		if(!animate) {
			return frames[currentFrame];
		}
		
		int lastFrame = currentFrame;
		currentFrame = anim.start;
		if(anim.frameDuration != 0 && anim.end != anim.start) {
			double currTime = System.currentTimeMillis() / 1000d;
			currentFrame += (int)((anim.end-anim.start + 1) * ((currTime-lastLoopTime) / anim.getTotalDuration()));
			if(currentFrame >= anim.end)
				lastLoopTime = currTime;
		}
		if(currentFrame < 0 || currentFrame >= frames.length) {
			currentFrame = lastFrame;
			return null;
		}
		return frames[currentFrame];
	}

	@Override
	public float getWidth() {
		return frames[currentFrame].getWidth();
	}

	@Override
	public float getHeight() {
		return frames[currentFrame].getHeight();
	}

	@Override
	public void dispose() {
		if(spriteSheet != null)
			spriteSheet.dispose();
	}

}