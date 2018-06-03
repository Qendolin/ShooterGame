package com.mygdx.game.entityComponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

//TODO: Position als Property machen
public final class TrasformationComp implements Component {
	public Vector2 pos = new Vector2();
	public float rotation = 0;
	public float scale = 1;
	
	public TrasformationComp() {
		
	}
	
	public TrasformationComp(float x, float y) {
		pos.x = x;
		pos.y = y;
	}
	
	public TrasformationComp(float x, float y, float rotation) {
		pos.x = x;
		pos.y = y;
		this.rotation = rotation;
	}
	
	public TrasformationComp(float x, float y, float rotation, float scale) {
		pos.x = x;
		pos.y = y;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public TrasformationComp(Vector2 pos) {
		this.pos.x = pos.x;
		this.pos.y = pos.y;
	}
	
	public TrasformationComp(Vector2 pos, float rotation) {
		this.pos.x = pos.x;
		this.pos.y = pos.y;
		this.rotation = rotation;
	}
	
	public TrasformationComp(Vector2 pos, float rotation, float scale) {
		this.pos.x = pos.x;
		this.pos.y = pos.y;
		this.rotation = rotation;
		this.scale = scale;
	}
}