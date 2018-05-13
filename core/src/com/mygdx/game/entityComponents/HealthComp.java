package com.mygdx.game.entityComponents;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.entityComponents.events.DeathEvent;
import com.mygdx.game.entityComponents.events.DeathListener;
import com.mygdx.game.entityComponents.visuals.CompositeVis;

import javafx.beans.property.SimpleFloatProperty;

public final class HealthComp implements Component{

	public SimpleFloatProperty health;
	public DeathListener deathListener;
	public VisualComp<CompositeVis> visualComp;
	public float startHealth;
	
	public HealthComp(float health, DeathListener onDeath){
		this.health = new SimpleFloatProperty(health);
		startHealth=health;
		deathListener = onDeath;
	}

	public void damage(float amount) {
		health.set(health.get() - Math.abs(amount));
		checkDead();
	}
	
	private void checkDead() {
		if(health.get() <= 0)
			deathListener.handle(new DeathEvent());
	}

	public void heal(float amount) {
		health.set(health.get() + Math.abs(amount));
	}
}
