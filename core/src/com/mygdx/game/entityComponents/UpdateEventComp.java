package com.mygdx.game.entityComponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entityComponents.events.UpdateEvent;
import com.mygdx.game.entityComponents.events.UpdateListener;

public final class UpdateEventComp implements Component {

	public UpdateListener updateEventListener;
	
	public UpdateEventComp(UpdateListener updateEventListener) {
		this.updateEventListener = updateEventListener;
	}
	
	public void update(World world, Engine engine, Camera cam, AssetManager assets) {
		if(updateEventListener != null)
			updateEventListener.handle(new UpdateEvent(world, cam, engine, assets));
	}
	
}
