package com.mygdx.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entites.Enemy;

public abstract class Action {

	public float cooldownInSec;
	protected boolean cooldown;
	protected long lastAct;
	
	public Action(float cooldownInSec) {
		this.cooldownInSec = cooldownInSec;
	}
	
	public boolean onCooldown() {
		return cooldown;
	}
	
	public void act(Enemy enemy, World world, Engine engine, Camera cam) {
		if(System.currentTimeMillis() - lastAct >= cooldownInSec) {
			cooldown = false;
			if(doAction(enemy, world, engine, cam)) {
				lastAct = System.currentTimeMillis();
				cooldown = true;
			}
		}
	}
	
	/**
	 * 
	 * @param enemy
	 * @param world
	 * @param engine
	 * @param cam
	 * @return success
	 */
	protected abstract boolean doAction(Enemy enemy, World world, Engine engine, Camera cam);
}
