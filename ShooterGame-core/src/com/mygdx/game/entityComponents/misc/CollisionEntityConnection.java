package com.mygdx.game.entityComponents.misc;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.entityComponents.events.CollisionListener;

public class CollisionEntityConnection {

	public Entity entity;
	public CollisionListener listener;
	
	public CollisionEntityConnection(Entity entity) {
		this.entity = entity;
	}
	
}
