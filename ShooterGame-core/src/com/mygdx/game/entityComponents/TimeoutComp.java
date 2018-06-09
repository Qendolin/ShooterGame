package com.mygdx.game.entityComponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.entityComponents.events.TimeoutEvent;
import com.mygdx.game.entityComponents.events.UpdateListener;

public final class TimeoutComp extends Entity implements Component, Disposable {
	
	private double timeoutTime;
	private EventListener listener;
	private UpdateEventComp updateComp = new UpdateEventComp(new UpdateListener() {
		@Override
		public void onUpdate(World world, Engine engine, Camera cam) {
			update();
		}
	});
	
	public TimeoutComp(Engine engine, float inXSconds, EventListener onTimeout) {
		timeoutTime = System.currentTimeMillis() / 1000d + inXSconds;
		listener = onTimeout;
		add(updateComp);
		engine.addEntity(this);
	}

	public void update() {
		if(timeoutTime <= System.currentTimeMillis() / 1000d) {
			listener.handle(new TimeoutEvent(timeoutTime));
			dispose();
		}
	}

	@Override
	public void dispose() {
		removeAll();
	}

}
