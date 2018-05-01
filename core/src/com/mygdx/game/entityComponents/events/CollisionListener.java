package com.mygdx.game.entityComponents.events;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

public abstract class CollisionListener implements EventListener {
	@Override
	public boolean handle(Event event) {
		if(event == null || ! (event instanceof CollisionEvent)) return false;
		onCollide(((CollisionEvent)event).getContact(), ((CollisionEvent)event).getMyEntity(), ((CollisionEvent)event).getOtherEntity());
		return true;
	}
	
	protected abstract void onCollide(Contact contact, Entity myEntity, Entity otherEntity);

}
