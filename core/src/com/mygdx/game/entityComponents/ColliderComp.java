package com.mygdx.game.entityComponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.utils.BodyFactory;

public class ColliderComp implements Component, Disposable {
	//TODO: Eine Klasse Impelmentieren die einen 2 collider von "bodx2D" enthält
	private Body body;
	private World world;
	
	/**
	 * Erstellt einen Body mit Circle Shape.
	 * @param radius
	 */
	public ColliderComp(World world, Vector2 pos, float radius, BodyType type, short collisionLayer, short collisionLayerMask) {
		this.world = world;
		body = BodyFactory.createCircle(world, type, false, collisionLayer, collisionLayerMask, pos, radius);
	}

	/**
	 * Erstellt einen Body mit einem rechteckigen PolygonShape.
	 * @param world
	 * @param sprite
	 * @param type
	 */
	public ColliderComp(World world, Vector2 pos, Sprite sprite, BodyType type, short collisionLayer, short collisionLayerMask) {
		this.world = world;
		body = BodyFactory.createRectangle(world, type, false, collisionLayer, collisionLayerMask, pos, sprite.getWidth(), sprite.getHeight());
	}
	
	
	public Vector2 getPosition() {
		return body.getTransform().getPosition();
	}
	
	public Body getBody() {
		return body;
	}

	@Override
	public void dispose() {
		world.destroyBody(body);
	}
	
}
