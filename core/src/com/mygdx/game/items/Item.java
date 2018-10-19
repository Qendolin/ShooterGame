package com.mygdx.game.items;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entites.DefaultEntity;
import com.mygdx.game.entites.Player;

public abstract class Item<Attr extends ItemAttribs> {

	//protected Attr attribs;
	protected DefaultEntity<?> owner;
	protected String name;
	
	/*public Item(Attr attribs) {
		this.attribs = attribs;
	}*/
	
	public Item(String name) {
		this.name = name;
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public abstract void update(World world, Engine engine, Camera cam, AssetManager assets);
	
}
