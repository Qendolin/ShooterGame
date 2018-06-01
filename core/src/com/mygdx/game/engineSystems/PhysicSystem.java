package com.mygdx.game.engineSystems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entityComponents.BodyComp;
import com.mygdx.game.entityComponents.PositionComp;
import com.mygdx.game.entityComponents.UpdateEventComp;
import com.mygdx.game.entityComponents.VelocityComp;
import com.mygdx.game.utils.Const;

public class PhysicSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;
	private World world;
	
	private ComponentMapper<BodyComp> bm = ComponentMapper.getFor(BodyComp.class);
	private ComponentMapper<PositionComp> pm = ComponentMapper.getFor(PositionComp.class);
	
	public PhysicSystem(World world) {
		super(40);//Before movement
		this.world = world;
	}
	
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(BodyComp.class, PositionComp.class).get());
	}
	
	public void update(float deltaTime) {
		world.step(Gdx.graphics.getDeltaTime(), 2, 6);
		
		for(Entity collider : entities) {
			BodyComp colliderComp = bm.get(collider);
			PositionComp positionComp = pm.get(collider);
			if(colliderComp != null && positionComp != null)
				positionComp.pos = new Vector2(colliderComp.getPosition()).scl(Const.METER_TO_PIXEL_RATIO);
		}
	}
}
