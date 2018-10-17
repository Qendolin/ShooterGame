package com.mygdx.game.actions.attacks;

import com.mygdx.game.actions.Action;

/**
 * @Deprecated - NICHT VERWENDEN! Brauche ersatz
 */
@Deprecated
public abstract class Attack extends Action {

	public float range;
	
	public Attack(float cooldownInSec, float durationInSec, float range) {
		super(cooldownInSec, durationInSec);
		this.range = range;
	}

}
