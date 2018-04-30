package com.mygdx.game.entites;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entityComponents.*;
import com.mygdx.game.entityComponents.visualComps.SpriteSheetComp;
import com.mygdx.game.items.Gun;

public class Boss1 extends Entity {

	EnemyTypes enemyType = EnemyTypes.Boss1;
	
	public HealthComp health=enemyType.health;
	public VelocityComp velocity=enemyType.velocity;
	public Gun gun;
	
	public Boss1(SpriteSheetComp visual, World world){
		add(health);
		add(velocity);
		gun=enemyType.gun;
	}
}
