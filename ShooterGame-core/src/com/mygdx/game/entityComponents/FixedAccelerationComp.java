package com.mygdx.game.entityComponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public final class FixedAccelerationComp implements Component {

	public float ups;
	
	public FixedAccelerationComp(float unitsPerSecond) {
		this.ups = unitsPerSecond;
	}
	
	public void accelerateTo(Vector2 target, Vector2 current) {
		if(current == null || target == null || (target.x == current.x && target.y == current.y))
			return;
		//Richtung
		float dx = target.x - current.x;
		float dy = target.y - current.y;
		
		if(dx != 0)     //Richtung      mal        Maximale Distanz, Errechnete Distanz
			current.x += dx/Math.abs(dx) * Math.min(Math.abs(dx), ups * Gdx.graphics.getDeltaTime()); //das Math.min verhindert dass man über sein ziel hinausschießt.
		if(dy != 0)
			current.y += dy/Math.abs(dy) * Math.min(Math.abs(dy), ups * Gdx.graphics.getDeltaTime());
	}
	
}
