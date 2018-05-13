package com.mygdx.game.entityComponents;

import com.badlogic.ashley.core.Component;

public final class RotationComp implements Component {

	/**
	 * In richtung der X achse = 0
	 * In richtung der Y achse = 90
	 */
	public float degrees;
	
	public float asRadians() {
		return (float) Math.toRadians(degrees);
	}
}
