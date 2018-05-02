package com.mygdx.game.entityComponents;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.entityComponents.events.DeathEvent;
import com.mygdx.game.entityComponents.events.DeathListener;
import com.mygdx.game.entityComponents.visualComps.SpriteComp;

public class HealthComp implements Component{

	public float health;
	public DeathListener deathListener;
	public SpriteComp sprite;
	public float startHealth;
	
	public HealthComp(float health, DeathListener onDeath){
		this.health=health;
		startHealth=health;
		deathListener = onDeath;
	}
	
	public HealthComp(float health, DeathListener onDeath,SpriteComp sprite){
		this.health=health;
		startHealth=health;
		deathListener = onDeath;
		this.sprite=sprite;
	}

	public void damage(float amount) {
		sprite.get().setSize(sprite.getWidth()*(health/startHealth), sprite.getHeight());
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
