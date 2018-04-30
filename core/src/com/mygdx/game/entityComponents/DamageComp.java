package com.mygdx.game.entityComponents;

import com.badlogic.ashley.core.Component;

public class DamageComp implements Component{

	public float damage;
	
	public DamageComp(float damage){
		this.damage=damage;
	}
}