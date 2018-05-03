package com.mygdx.game.entityComponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public final class VelocityComp implements Component {
	public Vector2 vel = new Vector2();
	
	public VelocityComp() {
		
	}
	
	public VelocityComp(float x, float y) {
		vel.x = x;
		vel.y = y;
	}
	
	public VelocityComp(Vector2 vel) {
		this.vel.x = vel.x;
		this.vel.y = vel.y;
	}
}
