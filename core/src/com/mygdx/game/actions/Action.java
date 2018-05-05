package com.mygdx.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entites.Enemy;

public abstract class Action {

	/**
	 * In seconds
	 */
	public float cooldown;
	/**
	 * In seconds
	 */
	public float duration;
	protected boolean onCooldown;
	protected boolean isActing;
	protected long lastAct;
	private long lastActBegin = -1;
	
	public Action(float cooldownInSec) {
		this.cooldown = cooldownInSec;
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
			onCooldown = false;
			if(isActing() || lastActBegin == -1) {
				if(doAction(enemy, world, engine)) {
					if(lastActBegin == -1)
						lastActBegin = System.currentTimeMillis();
					return true;
				}
			} else if(lastActBegin != -1) {
				lastAct = System.currentTimeMillis();
				onCooldown = true;
				lastActBegin = -1;
			}
		}
		return false;
	}
	
	public boolean isOnCooldown() {
		onCooldown = (System.currentTimeMillis() - lastAct < cooldown*1000);
		return onCooldown;
	}
	
	public boolean isActing() {
		isActing = lastActBegin != -1 & System.currentTimeMillis() - lastActBegin <= duration*1000;
		return isActing;
	}
	
	/**
	 * 
	 * @param enemy
	 * @param world
	 * @param engine
	 * @param cam
	 * @return success
	 */
	protected abstract boolean doAction(Enemy enemy, World world, Engine engine);
}
