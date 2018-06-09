package com.mygdx.game.entites;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entityComponents.BodyComp;
import com.mygdx.game.entityComponents.HealthComp;
import com.mygdx.game.entityComponents.events.CollisionListener;

/**
 * Bereich der dinge damaged, nützlich für z.b. attacken 
 */
public class DamageArea extends Entity implements Disposable{

	public BodyComp bodyComp;
	public float damage;
	public int maxHits = 1;
	public int hits;
	private CollisionListener onCollide = new CollisionListener() {
		@Override
		protected void onCollide(Contact contact, Entity myEntity, Entity otherEntity) {
			HealthComp health = otherEntity.getComponent(HealthComp.class);
			if(health == null) return;
			health.damage(damage);
			hits++;
			if(hits >= maxHits) {
				dispose();
			}
		}
	};
	private Engine engine;
	
	public DamageArea(World world, Engine engine, Vector2 pos, float damage, int maxHits, short collisionLayer, short collisionLayerMask, float radius) {
		this.damage = damage;
		this.maxHits = maxHits;
		this.engine = engine;
		bodyComp = new BodyComp(world, pos, this, radius, BodyType.DynamicBody, collisionLayer, collisionLayerMask).asSensor();
		bodyComp.setCollisionListener(onCollide);
		add(bodyComp);
	}
	
	public DamageArea(World world, Engine engine, Vector2 pos, float damage, int maxHits, short collisionLayer, short collisionLayerMask, float width, float height) {
		this.damage = damage;
		this.maxHits = maxHits;
		this.engine = engine;
		bodyComp = new BodyComp(world, pos, this, width, height, BodyType.DynamicBody, collisionLayer, collisionLayerMask).asSensor();
		bodyComp.setCollisionListener(onCollide);
		add(bodyComp);
	}

	public boolean exists() {
		return hits < maxHits;
	}
	
	@Override
	public void dispose() {
		bodyComp.dispose();
		engine.removeEntity(this);
	}
	
}
