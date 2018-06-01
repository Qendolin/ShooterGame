package com.mygdx.game.engineSystems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entityComponents.UpdateEventComp;

public class UpdateSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;
	private World world;
	private Engine engine;
	private Camera cam;

	private ComponentMapper<UpdateEventComp> um = ComponentMapper.getFor(UpdateEventComp.class);

	public UpdateSystem(World world, Engine engine, Camera cam) {
		super(60);
		this.world = world;
		this.engine = engine;
		this.cam = cam;
	}

	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(UpdateEventComp.class).get());
	}
	
	public void update(float deltaTime) {
		for (int i = 0; i < entities.size(); ++i) {
			Entity entity = entities.get(i);
			UpdateEventComp updateEventComp = um.get(entity);
			updateEventComp.update(world, engine, cam);
		}
	}
}
