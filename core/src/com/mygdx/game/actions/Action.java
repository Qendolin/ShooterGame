package com.mygdx.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entites.Enemy;

public abstract class Action {

	public float cooldownInSec;
	
	public Action(float cooldownInSec) {
		this.cooldownInSec = cooldownInSec;
	}
	
	public abstract void doAction(Enemy enemy, World world, Engine engine, Camera cam);
}
