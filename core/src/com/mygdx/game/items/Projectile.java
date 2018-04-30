package com.mygdx.game.items;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.mygdx.game.entityComponents.ColliderComp;
import com.mygdx.game.entityComponents.DamageComp;
import com.mygdx.game.entityComponents.PositionComp;
import com.mygdx.game.entityComponents.TimeoutComp;
import com.mygdx.game.entityComponents.VelocityComp;
import com.mygdx.game.entityComponents.VisualComp;

public class Projectile extends Entity {
	
	public DamageComp damageComp;
	public ColliderComp colliderComp;
	public VisualComp visualComp;
	public PositionComp positionComp;
	public VelocityComp velocityComp;
	
	public Projectile(World world, Engine engine, Vector2 pos, VisualComp visual, Vector2 velocity, float damage, float timeoutInSec) {
		damageComp = new DamageComp(damage);
		add(damageComp);
		add(new TimeoutComp(timeoutInSec, new EventListener() {
			
			@Override
			public boolean handle(Event e) {
				engine.removeEntity(Projectile.this);
				return true;
			}
		}));
		colliderComp = new ColliderComp(world, pos, 0.1f, BodyType.DynamicBody);
		colliderComp.getBody().setBullet(true);
		add(colliderComp);
		visualComp = visual;
		velocityComp = new VelocityComp(velocity);
		add(visualComp);
		positionComp = new PositionComp(pos);
		add(positionComp);
	}
}
