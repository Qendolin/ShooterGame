package com.mygdx.game.entites;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.entityComponents.BodyComp;
import com.mygdx.game.entityComponents.DamageComp;
import com.mygdx.game.entityComponents.HealthComp;
import com.mygdx.game.entityComponents.PositionComp;
import com.mygdx.game.entityComponents.TimeoutComp;
import com.mygdx.game.entityComponents.VelocityComp;
import com.mygdx.game.entityComponents.Visual;
import com.mygdx.game.entityComponents.VisualComp;
import com.mygdx.game.entityComponents.events.CollisionListener;
import com.mygdx.game.utils.Const;

public class Projectile extends Entity implements Disposable{
	
	public DamageComp damageComp;
	public BodyComp sensorComp;
	public VisualComp<?> visualComp;
	public PositionComp positionComp;
	public VelocityComp velocityComp;
	public Entity source;
	
	private Engine engine;
	
	public Projectile(World world, Engine engine, Entity source, Vector2 pos, Visual visual, Vector2 velocity, float damage, float timeoutInSec) {
		this.engine = engine;
		damageComp = new DamageComp(damage);
		add(damageComp);
		add(new TimeoutComp(engine, timeoutInSec, new EventListener() {
			@Override
			public boolean handle(Event e) {
				dispose();
				return true;
			}
		}));
		
		this.source = source;

		sensorComp = new BodyComp(world, pos, this, 4f, BodyType.DynamicBody, Const.PROJECTILE, (short) (Const.PROJECTILE ^ Const.ALL)).asSensor();
		sensorComp.getBody().setBullet(true);
		sensorComp.setCollisionListener(new CollisionListener() {
			@Override
			protected void onCollide(Contact contact, Entity myEntity, Entity otherEntity) {
				if(otherEntity == source || otherEntity instanceof Projectile)
					return;
				HealthComp enemyHealthComp = otherEntity.getComponent(HealthComp.class);
				if(enemyHealthComp != null)
					enemyHealthComp.damage(damage);
				dispose();
			}
			
		});
		add(sensorComp);
		visualComp = new VisualComp<Visual>(visual);
		add(visualComp);
		velocityComp = new VelocityComp(velocity);
		add(velocityComp);
		positionComp = new PositionComp(pos);
		add(positionComp);
	}

	public void setCollisionListener(CollisionListener listener) {
		sensorComp.setCollisionListener(listener);
	}
	
	@Override
	public void dispose() {
		sensorComp.dispose();
		removeAll();
		engine.removeEntity(Projectile.this);
	}
}
