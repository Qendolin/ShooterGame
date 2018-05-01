package com.mygdx.game.items;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.entityComponents.ColliderComp;
import com.mygdx.game.entityComponents.DamageComp;
import com.mygdx.game.entityComponents.PositionComp;
import com.mygdx.game.entityComponents.TimeoutComp;
import com.mygdx.game.entityComponents.VelocityComp;
import com.mygdx.game.entityComponents.VisualComp;
import com.mygdx.game.utils.Const;

public class Projectile extends Entity implements Disposable{
	
	public DamageComp damageComp;
	public ColliderComp colliderComp;
	public VisualComp visualComp;
	public PositionComp positionComp;
	public VelocityComp velocityComp;
	
	private Engine engine;
	
	public Projectile(World world, Engine engine, Vector2 pos, VisualComp visual, Vector2 velocity, float damage, float timeoutInSec) {
		this.engine = engine;
		damageComp = new DamageComp(damage);
		add(damageComp);
		add(new TimeoutComp(timeoutInSec, new EventListener() {
			
			@Override
			public boolean handle(Event e) {
				dispose();
				return true;
			}
		}));

		colliderComp = new ColliderComp(world, pos, 4f, BodyType.DynamicBody, Const.PROJECTILE, (short) (Const.PROJECTILE ^ Const.DEFAULT));
		colliderComp.getBody().setBullet(true);
		add(colliderComp);
		visualComp = visual;
		add(visualComp);
		velocityComp = new VelocityComp(velocity);
		add(velocityComp);
		positionComp = new PositionComp(pos);
		add(positionComp);
	}

	@Override
	public void dispose() {
		engine.removeEntity(Projectile.this);
		colliderComp.dispose();
		removeAll();
	}
}
