package com.mygdx.game.entityComponents;

import com.badlogic.ashley.core.Component;

public class HealthComp implements Component{

	float health;
	
	public HealthComp(float health){
		this.health=health;
	}
}
