package com.mygdx.game.entityComponents.events;

import com.badlogic.gdx.scenes.scene2d.Event;

public class TimeoutEvent extends Event {
	public final double timeoutTime;
	
	public TimeoutEvent(double timeoutTime) {
		this.timeoutTime = timeoutTime;
	}
}