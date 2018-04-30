package com.mygdx.game.items;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import com.mygdx.game.entites.Player;

public abstract class Item {

	protected String name = "";
	protected Player owner;
	
	public Item(String name) {
		
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public abstract void update(Engine engine, Camera cam);
	
}
