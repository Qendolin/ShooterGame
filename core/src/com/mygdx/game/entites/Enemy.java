package com.mygdx.game.entites;

import java.util.Random;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.entityComponents.*;
import com.mygdx.game.entityComponents.events.UpdateListener;
import com.mygdx.game.entityComponents.visualComps.SpriteSheetComp;
import com.mygdx.game.items.Gun;
import com.mygdx.game.items.Item;
import com.mygdx.game.utils.Const;

public class Enemy extends DefaultEntity<SpriteSheetComp> {
	
	public Item item;
	public Player target;
//	public static float DAMPING = 0.02f; //Was soll das machen???
	public float attackRadius;
	public float speed;
	private int enemyDirection;
	public long lastAttack;

	EnemyType type;

	public static final String ANIM_WALK_DOWN = "walkDown";
	public static final String ANIM_WALK_UP = "walkUp";
	public static final String ANIM_WALK_LEFT = "walkLeft";
	public static final String ANIM_WALK_RIGHT = "walkRight";
	public static final String ANIM_RUN_DOWN = "runDown";
	public static final String ANIM_RUN_UP = "runUp";
	public static final String ANIM_RUN_LEFT = "runLeft";
	public static final String ANIM_RUN_RIGHT = "runRight";

	public static float enemyHitCircleRadiusMultiplyer = 0.8f;
	public static float enemyRangeCircleRadiusMultiplyer = 30f; // Ich würde den Spieler "detections" radius nicht als
																// multiplyer festlegen (also von der größe des sprites
																// abhängig machen) sonder als fixen wert festlegen

	public Enemy(EnemyType type,  World world, Vector2 position, SpriteSheetComp visual) {
		super(position, visual);
		this.type = type;
		speed = type.speed;
		item = type.item;
		attackRadius = (((visual.getHeight() + visual.getWidth()) / 4f) * enemyRangeCircleRadiusMultiplyer) / 2;
		
		healthComp = new HealthComp(type.health, disposeOnDeath);
		add(healthComp);
		colliderComp = new ColliderComp(world, visualComp.getCenter(), this,
				((visual.getHeight() + visual.getWidth()) / 4f) * enemyHitCircleRadiusMultiplyer, BodyType.DynamicBody,
				Const.BIG_ENTITY, (short) (Const.BIG_ENTITY ^ Const.DEFAULT));
		add(colliderComp);
		updateComp = new UpdateEventComp(new UpdateListener() {
			@Override
			public void onUpdate(World world, Engine engine, Camera cam) {
				update(world, cam, engine);
			}
		});
		add(updateComp);
		accelerationComp = new FixedAccelerationComp(Float.MAX_VALUE); //Instant
		add(accelerationComp);
	}

	public void update(World world, Camera cam, Engine engine) {
		/*
		 * Enemy Movement,Angriffsmuster und Rendering ist noch zu machen
		 */
		//

		for (Player player : Player.getAllPlayers()) {
			Vector2 v = new Vector2(player.positionComp.pos);
			if (v.sub(positionComp.pos).len() < attackRadius) {
				target = player;
				break;
			}
		}
		Vector2 nextVel;
		if (target != null) {
			nextVel = new Vector2(target.positionComp.pos.x - positionComp.pos.x - target.visualComp.getWidth() / 1.3f,
					target.positionComp.pos.y - positionComp.pos.y - target.visualComp.getHeight() / 1.3f);

			if (System.currentTimeMillis() - lastAttack >= type.action.cooldownInSec*1000) {
				lastAttack = System.currentTimeMillis();
				type.action.doAction(this, world, engine, cam);
			} else {
//				DAMPING = 0.02f;
			}
			//Dise berechnung wird in der Main Klasse übernommen. Einfach die velociy von der velocity komponente setzen
//			positionComp.pos.x += nextXY.x * DAMPING;
//			positionComp.pos.y += nextXY.y * DAMPING; 
			velocityComp.vel=nextVel;
		}

		// Quadranten der richtung der bewegung ausrechnen
		enemyDirection = velocityComp.vel.isZero() ? enemyDirection : getVelocityDirectionArea(4, 45);
		switch (enemyDirection) {
		case (0):
			visualComp.currentAnimation = ANIM_WALK_RIGHT;
			break;
		case (1):
			visualComp.currentAnimation = ANIM_WALK_UP;
			break;
		case (2):
			visualComp.currentAnimation = ANIM_WALK_LEFT;
			break;
		case (3):
			visualComp.currentAnimation = ANIM_WALK_DOWN;
			break;
		}

		if (positionComp.pos.isZero()) {
			visualComp.setCurrentAnimationFrame(0);
			visualComp.animate = false;
		} else
			visualComp.animate = true;
	}

	public void setTarget(Player target) {
		this.target = target;
	}

	public Player getTarget() {
		return target;
	}
}
