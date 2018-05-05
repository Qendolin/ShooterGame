package com.mygdx.game.entites;

import com.mygdx.game.actions.Action;
import com.mygdx.game.actions.Actions;
import com.mygdx.game.items.Item;
import com.mygdx.game.items.MachineGun;

public enum EnemyType {

	// Bosse mit HP/Geschwindigkeit/Guns
	Boss1(150, 50, new MachineGun(), true, Actions.BEAM);
	/* Mehrere Typen von Bossen müssen noch erstellt werden */

	float speed;
	float health;
	Item item;
	Action[] actions;
	boolean boss;

	EnemyType(float speed, float health, Item item, boolean boss, Action... actions) {
		this.speed = speed;
		this.health = health;
		this.item = item;
		this.actions=actions;
		this.boss = boss;
	}
}
