package com.mygdx.game.entites;

import com.mygdx.game.entityComponents.HealthComp;
import com.mygdx.game.entityComponents.VelocityComp;
import com.mygdx.game.items.*;

public enum EnemyTypes {

	// Bosse mit HP/Geschwindigkeit/Guns
	Boss1(new VelocityComp(),new HealthComp(1000),new MachineGun());
	/* Mehrere Typen von Bossen müssen noch erstellt werden*/
	
	VelocityComp velocity;
	HealthComp health;
	Gun gun;
	
	EnemyTypes(VelocityComp velocity,HealthComp health,Gun gun){
		this.velocity=velocity;
		this.health=health;
		this.gun=gun;
	}
}
