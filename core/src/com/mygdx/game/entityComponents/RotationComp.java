package com.mygdx.game.entityComponents;

import com.badlogic.ashley.core.Component;

public class RotationComp implements Component {
	/**
	 * Grad zur y achse
	 * 
	 * Beispiel:
	 * 
	 *    |
	 *    x  = 0
	 * 
	 *     /
	 *    x  = 22.5
	 * 
	 * 
	 *    x- = 90
	 *    
	 *    
	 *   -x  = 270
	 * 
	 */
	public float degrees;
	
	public float asRadians() {
		return (float) Math.toRadians(degrees);
	}
}
