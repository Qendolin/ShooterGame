package com.mygdx.game.entityComponents.visuals;

import java.util.Arrays;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.utils.Const.LogTags;

public class SpriteSheetVis extends Visual {

	public HashMap<String, SpriteSheetSpriteGroup> animationGroups = new HashMap<String, SpriteSheetSpriteGroup>();
	public String currentAnimation = "";
	public boolean animate = true;
	public float speedFactor = 1;
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
	public SpriteSheetVis(Texture spriteSheet, int cols, int rows, boolean swapColsAndRows, SpriteSheetSpriteGroup... groups) {
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
	
	public SpriteSheetVis(SpriteSheetVis original) {
		for(String key : original.animationGroups.keySet()) {
			this.animationGroups.put(new String(key), original.animationGroups.get(key));
		}
		this.currentAnimation = new String(original.currentAnimation);
		this.animate = original.animate;
		this.speedFactor = original.speedFactor;
		this.frames = new Sprite[original.frames.length];
		for(int i = 0; i < original.frames.length; i++) {
			frames[i] = new Sprite(original.frames[i]);
		}
		this.lastLoopTime = original.lastLoopTime;
		this.spriteSheet = original.spriteSheet; //Ich glaube man sollte dieselbe instanz wiederverwenden
		this.currentFrame = original.currentFrame;
	}

	/**
	 * 
	 * @param frame relative to the start frame of the animation
	 */
	public void setCurrentAnimationFrame(int frame) {
		SpriteSheetSpriteGroup anim = animationGroups.get(currentAnimation);
		if(anim == null) {
			Gdx.app.error(LogTags.RENDERING, "Invalid animation group: \""+currentAnimation+"\"" ,new Exception("Invalid animation group: \""+currentAnimation+"\""));
			return;
		}
		currentFrame = anim.start + (frame % (anim.end - anim.start + 1));
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

	@Override
	public Sprite get(int index) {
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
			currentFrame = (int)((anim.end-anim.start + 1) * ((currTime-lastLoopTime) / (anim.getTotalDuration()/speedFactor)));
			if(currentFrame >= anim.end)
				lastLoopTime = currTime;
			currentFrame = currentFrame % (anim.end-anim.start + 1) + anim.start;
		}
		if(currentFrame < 0 || currentFrame >= frames.length) {
			currentFrame = lastFrame;
			return null;
		}
		return frames[currentFrame];
	}

	@Override
	public int getNumberOfSprites() {
		// TODO Auto-generated method stub
		return 0;
	}

}
