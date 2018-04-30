package com.mygdx.game.items;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.mygdx.game.entityComponents.DamageComp;
import com.mygdx.game.entityComponents.TimeoutComp;

public class Projectile extends Entity {
	
	public DamageComp damageComp;
	
	public Projectile(Engine engine, float damage, float timeoutInSec) {
		damageComp = new DamageComp(damage);
		add(damageComp);
		add(new TimeoutComp(timeoutInSec, new EventListener() {
			
			@Override
			public boolean handle(Event e) {
				engine.removeEntity(Projectile.this);
				return true;
			}
		}));
	}
}
