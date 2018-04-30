package com.mygdx.game.entityComponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.mygdx.game.entityComponents.events.TimeoutEvent;

public class TimeoutComp implements Component {
	
	private double timeoutTime;
	private EventListener listener;
	
	public TimeoutComp(float inXSconds, EventListener onTimeout) {
		timeoutTime = System.currentTimeMillis() / 1000d + inXSconds;
		listener = onTimeout;
	}

	public void update() {
		if(timeoutTime >= System.currentTimeMillis() / 1000d) {
			listener.handle(new TimeoutEvent(timeoutTime));
		}
	}
}
