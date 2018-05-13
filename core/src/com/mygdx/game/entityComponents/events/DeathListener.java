package com.mygdx.game.entityComponents.events;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

public abstract class DeathListener implements EventListener {

	@Override
	public boolean handle(Event event) {
		if(event == null || ! (event instanceof DeathEvent)) return false;
		return onDeath((DeathEvent) event);
	}

	/**
	 * @param event
	 * @return did Entity die?
	 */
	protected abstract boolean onDeath(DeathEvent event);
}
