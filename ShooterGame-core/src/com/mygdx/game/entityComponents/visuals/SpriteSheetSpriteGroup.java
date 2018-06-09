package com.mygdx.game.entityComponents.visuals;

public class SpriteSheetSpriteGroup {
	public int start;
	public int end;
	public String name = "";
	public float frameDuration;
	
	public SpriteSheetSpriteGroup(int start, int end, float frameDuration, String name) {
		this.start = start;
		this.end = end;
		this.name = name;
		this.frameDuration = frameDuration;
	}
	
	public SpriteSheetSpriteGroup(int index, String name) {
		start = index;
		start = index;
		this.name = name;
		frameDuration = 0;
	}

	public float getTotalDuration() {
		return (end-start+1) * frameDuration;
	}
}
