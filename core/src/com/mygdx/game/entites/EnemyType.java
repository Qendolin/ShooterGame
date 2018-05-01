package com.mygdx.game.entites;

import com.mygdx.game.entityComponents.HealthComp;
import com.mygdx.game.items.*;

public enum EnemyType {

	// Bosse mit HP/Geschwindigkeit/Guns
	Boss1(150, 1000, new MachineGun());
	/* Mehrere Typen von Bossen müssen noch erstellt werden */

	float speed;
	float health;
	Item item;

	EnemyType(float speed, float health, Item item) {
		this.speed = speed;
		this.health = health;
		this.item = item;
	}
}
