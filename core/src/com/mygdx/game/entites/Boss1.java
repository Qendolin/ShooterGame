package com.mygdx.game.entites;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.entityComponents.*;
import com.mygdx.game.entityComponents.visualComps.SpriteSheetComp;
import com.mygdx.game.items.Gun;

public class Boss1 extends Entity {

	EnemyTypes enemyType = EnemyTypes.Boss1;
	
	//die Werte von Boss1 werden für diesen Boss verwendet
	public HealthComp health=enemyType.health;
	public VelocityComp velocity=enemyType.velocity;
	public Gun gun;
	public SpriteSheetComp visualComp;
	public ColliderComp collisionComp;
	public PositionComp positionComp;
	public float attackRadius;

	
	public static float enemyHitCircleRadiusMultiplyer = 0.8f;
	public static float enemyRangeCircleRadiusMultiplyer = 30f;
	
	
	public Boss1(SpriteSheetComp visual, World world,PositionComp position){
		add(visual);
		visualComp=visual;
		add(health);
		add(velocity);
		positionComp=position;
		add(position);
		gun=enemyType.gun;
		collisionComp = new ColliderComp(world, visualComp.getCenter(), ((visual.getHeight()+visual.getWidth())/4f)*enemyHitCircleRadiusMultiplyer, BodyType.DynamicBody);
		add(collisionComp);
		attackRadius = (((visual.getHeight()+visual.getWidth())/4f)*enemyRangeCircleRadiusMultiplyer)/2;
	}
	
	
	public void update(){
		/*
		 * Enemy Movement,Angriffsmuster und Rendering ist noch zu machen
		 */
		//
		
		for(Player player : Player.getAllPlayers()){
			Vector2 v = new Vector2(player.positionComp.pos);
			if(v.sub(positionComp.pos).len()<attackRadius){
				System.out.println("noob");
			}
		}
	}
}
