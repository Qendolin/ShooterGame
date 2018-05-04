package com.mygdx.game.entityComponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.entityComponents.events.CollisionListener;
import com.mygdx.game.entityComponents.misc.BodyDeleteFlag;
import com.mygdx.game.entityComponents.misc.CollisionEntityConnection;
import com.mygdx.game.utils.BodyFactory;

public final class BodyComp implements Component, Disposable {
	
	private Body body;
	private Fixture fixture;
	private CollisionEntityConnection connection;
	
	/**
	 * Erstellt einen Body mit Circle Shape.
	 * @param radius
	 */
	public BodyComp(World world, Vector2 pos, Entity entity, float radius, BodyType type, short collisionLayer, short collisionLayerMask) {
		body = BodyFactory.createCircle(world, type, false, collisionLayer, collisionLayerMask, pos, radius, false);
		fixture = body.getFixtureList().get(0);
		connection = new CollisionEntityConnection(entity);
		fixture.setUserData(connection);
	}

	/**
	 * Erstellt einen Body mit einem rechteckigen PolygonShape dass so groß wie der sprite ist.
	 * @param world
	 * @param sprite
	 * @param type
	 */
	public BodyComp(World world, Vector2 pos, Entity entity, Sprite sprite, BodyType type, short collisionLayer, short collisionLayerMask) {
		body = BodyFactory.createRectangle(world, type, false, collisionLayer, collisionLayerMask, pos, sprite.getWidth(), sprite.getHeight(), false);
		fixture = body.getFixtureList().get(0);
		connection = new CollisionEntityConnection(entity);
		fixture.setUserData(connection);
	}
	
	public BodyComp asSensor() {
		setSensor(true);
		return this;
	}
	
	public void setSensor(boolean sensor) {
		fixture.setSensor(sensor);
	}
	
	public void setCollisionListener(CollisionListener listener) {
		connection.listener = listener;
	}
	
	public Vector2 getPosition() {
		return body.getTransform().getPosition();
	}
	
	public Body getBody() {
		return body;
	}
	
	public Fixture getFixture() {
		return fixture;
	}

	@Override
	public void dispose() {
		if(body == null) {
			return;
		}
		body.setUserData(new BodyDeleteFlag());
		body = null;
	}
}
