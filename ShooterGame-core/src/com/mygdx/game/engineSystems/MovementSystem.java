package com.mygdx.game.engineSystems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entityComponents.BodyComp;
import com.mygdx.game.entityComponents.TrasformationComp;
import com.mygdx.game.entityComponents.VelocityComp;
import com.mygdx.game.utils.Const;

public class MovementSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;

	private ComponentMapper<BodyComp> bm = ComponentMapper.getFor(BodyComp.class);
	private ComponentMapper<VelocityComp> vm = ComponentMapper.getFor(VelocityComp.class);
	private ComponentMapper<TrasformationComp> tm = ComponentMapper.getFor(TrasformationComp.class);

	public MovementSystem() {
		super(50); //After physics
	}

	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(VelocityComp.class, TrasformationComp.class).get());
	}
	
	public void update(float deltaTime) {
		for (int i = 0; i < entities.size(); ++i) {
			Entity entity = entities.get(i);
			BodyComp bodyComp = bm.get(entity);
			VelocityComp velocityComp = vm.get(entity);
			TrasformationComp transformComp = tm.get(entity);
			
			Vector2 vel = velocityComp.vel;
			Vector2 pos = transformComp.pos;
			
			if(vel == null)
				continue;
			if(bodyComp != null) {
				bodyComp.getBody().setTransform(bodyComp.getPosition(), (float) Math.toRadians(transformComp.rotation));
				
				//Gschwindikeit
				vel = new Vector2(vel);
				vel.scl(Const.PIXEL_TO_METER_RATIO);
				bodyComp.getBody().setLinearVelocity(vel);
				
				//Überprüfung
				if(vel.len()*Gdx.graphics.getDeltaTime() > 2) {
					Gdx.app.log(Const.LogTags.PHYSIC, "Warning: "+entity+" is Moving faster than 2 m/s! The maximum speed is 2 m/s.");
				}
			} else {
				pos.x+=vel.x*Const.METER_TO_PIXEL_RATIO*deltaTime;
				pos.y+=vel.y*Const.METER_TO_PIXEL_RATIO*deltaTime;
			}
		}
	}
}
