package com.mygdx.game.entites;

import com.mygdx.game.entityComponents.HealthComp;
import com.mygdx.game.entityComponents.VelocityComp;
import com.mygdx.game.items.*;

public enum EnemyTypes {

	Boss1(new VelocityComp(),new HealthComp(1000),new MachineGun());
	
	VelocityComp velocity;
	HealthComp health;
	Gun gun;
	
	EnemyTypes(VelocityComp velocity,HealthComp health,Gun gun){
		this.velocity=velocity;
		this.health=health;
		this.gun=gun;
	}
}
