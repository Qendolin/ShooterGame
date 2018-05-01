package com.mygdx.game.entityComponents.events;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Event;

public class UpdateEvent extends Event{

	public World world;
	public Camera cam;
	public Engine engine;
	
	public UpdateEvent(World world, Camera cam, Engine engine) {
		super();
		this.world = world;
		this.cam = cam;
		this.engine = engine;
	}
}
