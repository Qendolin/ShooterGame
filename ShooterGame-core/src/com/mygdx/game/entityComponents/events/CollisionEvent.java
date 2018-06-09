package com.mygdx.game.entityComponents.events;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.Event;

public class CollisionEvent extends Event {

	private Contact contact;
	private Entity myEntity;
	private Entity otherEntity;
	
	public CollisionEvent(Contact contact, Entity myEntity, Entity otherEntity) {
		super();
		this.contact = contact;
		this.myEntity = myEntity;
		this.otherEntity = otherEntity;
	}

	public Contact getContact() {
		return contact;
	}

	public Entity getMyEntity() {
		return myEntity;
	}

	public Entity getOtherEntity() {
		return otherEntity;
	}

	
}
