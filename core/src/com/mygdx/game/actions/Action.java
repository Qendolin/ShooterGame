package com.mygdx.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entites.Enemy;

/**
 * @Deprecated - NICHT VERWENDEN! Brauche ersatz
 */
@Deprecated
public abstract class Action {

	/**
	 * In seconds
	 */
	public float cooldown;
	/**
	 * In seconds
	 */
	public float duration; //TODO: Duration is glaube ich keine so gute lösung
	protected boolean onCooldown;
	protected boolean isActing;
	protected long lastAct;
	private long lastActBegin = -1;
	
	public Action(float cooldownInSec, float durationInSec) {
		cooldown = cooldownInSec;
		duration = durationInSec;
	}
	
	/**
	 * 
	 * @param enemy
	 * @param world
	 * @param engine
	 * @return true while action is active
	 */
	public boolean act(Enemy enemy, World world, Engine engine) {
		if(!isOnCooldown()) {
			if(isActing() || lastActBegin == -1) {
				if(doAction(enemy, world, engine, lastActBegin==-1, false)) {
					if(lastActBegin == -1)
						lastActBegin = System.currentTimeMillis();
					return true;
				}
			} else if(lastActBegin != -1) {
				doAction(enemy, world, engine, false, true);
				lastAct = System.currentTimeMillis();
				onCooldown = true;
				lastActBegin = -1;
			}
		}
		else {
			lastActBegin = -1;
		}
		return false;
	}
	
	public boolean isOnCooldown() {
		onCooldown = ((System.currentTimeMillis() - lastAct < cooldown*1000)) && (!isActing());
		return onCooldown;
	}
	
	public boolean isActing() {
		long currTime = System.currentTimeMillis();
		isActing = (lastActBegin != -1) && (currTime - lastActBegin <= duration*1000);
		return isActing;
	}
	
	/**
	 * 
	 * @param enemy
	 * @param world
	 * @param engine
	 * @param cam
	 * @param begin Is beginning of action
	 * @return success
	 */
	protected abstract boolean doAction(Enemy enemy, World world, Engine engine, boolean begin, boolean end);
}
