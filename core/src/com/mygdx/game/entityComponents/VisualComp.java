package com.mygdx.game.entityComponents;

import com.badlogic.ashley.core.Component;

public final class VisualComp<V extends Visual> implements Component{

	public V visual;
	
	public VisualComp (V visual) {
		this.visual = visual;
	}

	public V getVisual() {
		// TODO Auto-generated method stub
		return null;
	}
}
