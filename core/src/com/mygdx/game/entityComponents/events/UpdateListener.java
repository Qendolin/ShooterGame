package com.mygdx.game.entityComponents.events;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

public abstract class UpdateListener implements EventListener {

	@Override
	public boolean handle(Event event) {
		if(! (event instanceof UpdateEvent))
			return false;
		onUpdate(((UpdateEvent)event).world,((UpdateEvent)event).engine,((UpdateEvent)event).cam, ((UpdateEvent)event).assets);
		return true;
	}
	
	public abstract void onUpdate(World world, Engine engine, Camera cam, AssetManager assets);

}
