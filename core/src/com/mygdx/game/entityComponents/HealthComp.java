package com.mygdx.game.entityComponents;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.entityComponents.events.DeathEvent;
import com.mygdx.game.entityComponents.events.DeathListener;

public class HealthComp implements Component{

	public float health;
	public DeathListener deathListener;
	
	public HealthComp(float health, DeathListener onDeath){
		this.health=health;
		deathListener = onDeath;
	}

	public void damage(float amount) {
		health -= Math.abs(amount);
		checkDead();
	}
	
	private void checkDead() {
		if(health <= 0)
			deathListener.handle(new DeathEvent());
	}

	public void heal(float amount) {
		health += Math.abs(amount);
	}
}
