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

public class ColliderComp implements Component, Disposable {

	private Body body;
	private World world;
	private Fixture fixture;
	private CollisionEntityConnection connection;
	
	/**
	 * Erstellt einen Body mit Circle Shape.
	 * @param radius
	 */
	public ColliderComp(World world, Vector2 pos, Entity entity, float radius, BodyType type, short collisionLayer, short collisionLayerMask) {
		this.world = world;
		body = BodyFactory.createCircle(world, type, false, collisionLayer, collisionLayerMask, pos, radius);
		fixture = body.getFixtureList().get(0);
		connection = new CollisionEntityConnection(entity);
		fixture.setUserData(connection);
	}

	/**
	 * Erstellt einen Body mit einem rechteckigen PolygonShape.
	 * @param world
	 * @param sprite
	 * @param type
	 */
	public ColliderComp(World world, Vector2 pos, Entity entity, Sprite sprite, BodyType type, short collisionLayer, short collisionLayerMask) {
		this.world = world;
		body = BodyFactory.createRectangle(world, type, false, collisionLayer, collisionLayerMask, pos, sprite.getWidth(), sprite.getHeight());
		fixture = body.getFixtureList().get(0);
		connection = new CollisionEntityConnection(entity);
		fixture.setUserData(connection);
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
		body.setUserData(new BodyDeleteFlag());
	}
	
}
