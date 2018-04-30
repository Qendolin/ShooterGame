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

public class ColliderComp implements Component {
	//TODO: Eine Klasse Impelmentieren die einen 2 collider von "bodx2D" enthält
	private Body body;
	private World world;
	
	/**
	 * Erstellt einen Body mit Circle Shape.
	 * @param radius
	 */
	public ColliderComp(World world, Vector2 pos, float radius, BodyType type) {
		this.world = world;
		createBody(type, pos);
		// Create a circle shape and set its radius to 6
		CircleShape circle = new CircleShape();
		circle.setRadius(radius);
		circle.setPosition(pos);
		
		setShape(circle);
		circle.dispose();
	}

	/**
	 * Erstellt einen Body mit einem rechteckigen PolygonShape.
	 * @param world
	 * @param sprite
	 * @param type
	 */
	public ColliderComp(World world, Vector2 pos, Sprite sprite, BodyType type) {
		this.world = world;
		createBody(type, pos);
		// Create a circle shape and set its radius to 6
		PolygonShape rect = new PolygonShape();
		rect.setAsBox(sprite.getWidth()/2f, sprite.getHeight()/2f);
		
		setShape(rect);
		
		rect.dispose();
	}
	
	private void createBody(BodyType type, Vector2 pos) {
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		//dynamic for moving things, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = type;
		// Set our body's starting position in the world
		bodyDef.position.set(pos.x, pos.y);
		// Create our body in the world using our body definition
		body = world.createBody(bodyDef);
	}
	
	private void setShape(Shape shape) {
		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;

		// Create our fixture and attach it to the body
		body.createFixture(fixtureDef);
	}
	
	public Vector2 getPosition() {
		return body.getTransform().getPosition();
	}
	
	public Body getBody() {
		return body;
	}
	
}
