package com.mygdx.game.entityComponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public final class PositionComp implements Component {
	public Vector2 pos = new Vector2();
	
	public PositionComp() {
		
	}
	
	public PositionComp(float x, float y) {
		pos.x = x;
		pos.y = y;
	}
	
	public PositionComp(Vector2 pos) {
		this.pos.x = pos.x;
		this.pos.y = pos.y;
	}
}