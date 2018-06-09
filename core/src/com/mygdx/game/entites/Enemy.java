package com.mygdx.game.entites;

import java.util.Collection;
import java.util.Set;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.ShooterGame;
import com.mygdx.game.actions.Action;
import com.mygdx.game.actions.attacks.Attack;
import com.mygdx.game.entityComponents.BodyComp;
import com.mygdx.game.entityComponents.FixedAccelerationComp;
import com.mygdx.game.entityComponents.HealthComp;
import com.mygdx.game.entityComponents.UpdateEventComp;
import com.mygdx.game.entityComponents.events.DeathEvent;
import com.mygdx.game.entityComponents.events.DeathListener;
import com.mygdx.game.entityComponents.events.UpdateListener;
import com.mygdx.game.entityComponents.visuals.SpriteSheetVis;
import com.mygdx.game.items.Item;
import com.mygdx.game.utils.Const;

public class Enemy extends AIControlledEntity<SpriteSheetVis> {
	
	public Item item;
	private int spriteDirection;
//	public float fullHealth;

//	private EnemyType type;

	public static final String ANIM_WALK_DOWN = "walkDown";
	public static final String ANIM_WALK_UP = "walkUp";
	public static final String ANIM_WALK_LEFT = "walkLeft";
	public static final String ANIM_WALK_RIGHT = "walkRight";
	public static final String ANIM_RUN_DOWN = "runDown";
	public static final String ANIM_RUN_UP = "runUp";
	public static final String ANIM_RUN_LEFT = "runLeft";
	public static final String ANIM_RUN_RIGHT = "runRight";
	
	public HealthBar healthBar;

	public static float enemyHitCircleRadiusMultiplyer = 0.8f;
	public static float enemyRangeCircleRadiusMultiplyer = 30f; // Ich würde den Spieler "detections" radius nicht als
																// multiplyer festlegen (also von der größe des sprites
																// abhängig machen) sonder als fixen wert festlegen

	public Enemy(EnemyType type,  World world, Engine engine, Vector2 position, SpriteSheetVis visual) {
		super(position, visual, engine, world);
//		this.type = type;
		speed = type.speed;
		item = type.item;
//		fullHealth=type.health;
		//attackRadius = (((visual.getHeight() + visual.getWidth()) / 4f) * enemyRangeCircleRadiusMultiplyer) / 2;
		healthComp = new HealthComp(type.health, disposeOnDeath);
		add(healthComp);
		
		bodyComp = new BodyComp(world, position, this,
				((visual.getHeight() + visual.getWidth()) / 4f) * enemyHitCircleRadiusMultiplyer, BodyType.DynamicBody,
				type.boss ? Const.Collision.BIG_ENTITY : Const.Collision.ENTITY, (short) (Const.Collision.ENTITY ^ Const.Collision.ALL));
		add(bodyComp);
		updateComp = new UpdateEventComp(new UpdateListener() {
			@Override
			public void onUpdate(World world, Engine engine, Camera cam, AssetManager assets) {
				update(world, cam, engine, assets);
			}
		});
		add(updateComp);
		accelerationComp = new FixedAccelerationComp(Float.MAX_VALUE); //Instant
		add(accelerationComp);
		healthBar = new HealthBar(transformComp, new Vector2(0, visual.getHeight()/2), healthComp);
		engine.addEntity(healthBar);
		
		healthComp.deathListener = new DeathListener() {		
			@Override
			protected boolean onDeath(DeathEvent event) {
				healthBar.dispose();
				engine.removeEntity(healthBar);
				dispose();
				return true;
			}
		};
		
		for(Action act : type.actions) {
			actions.add(act);
		}
	}
	
	//Experientell, mach ich vllt wieder weg
	public void performAction(Action acrion) {
		
	}

	public void update(World world, Camera cam, Engine engine, AssetManager assets) {
		/*
		 * Das wird alles duch die state machine gesteuert werden
		 */
		stateMachine.update();
		updateSprite();
		//
/*
		for (Player player : Player.getAllPlayers()) {
			Vector2 v = new Vector2(player.transformComp.pos);
			if (v.sub(transformComp.pos).len() < attackRadius) {
				target = player;
				break;
			}
		}
		Vector2 nextVel;
		if (target != null) {
			nextVel = new Vector2((target.transformComp.pos.x - transformComp.pos.x) / 1.3f,
					(target.transformComp.pos.y - transformComp.pos.y) / 1.3f);

			if (System.currentTimeMillis() - lastAction >= type.action.cooldownInSec*1000) {
				lastAction = System.currentTimeMillis();
				type.action.doAction(this, world, engine, cam);
			}
			velocityComp.vel=nextVel;
		}

		// Quadranten der richtung der bewegung ausrechnen
		enemyDirection = velocityComp.vel.isZero() ? enemyDirection : getVelocityDirectionArea(4, 45);
		switch (enemyDirection) {
		case (0):
			visualComp.visual.currentAnimation = ANIM_WALK_RIGHT;
			break;
		case (1):
			visualComp.visual.currentAnimation = ANIM_WALK_UP;
			break;
		case (2):
			visualComp.visual.currentAnimation = ANIM_WALK_LEFT;
			break;
		case (3):
			visualComp.visual.currentAnimation = ANIM_WALK_DOWN;
			break;
		}

		if (transformComp.pos.isZero()) {
			visualComp.visual.setCurrentAnimationFrame(0);
			visualComp.visual.animate = false;
		} else
			visualComp.visual.animate = true;
		
//		healthBar.get().setPosition(transformComp.pos.x+visualComp.visual.getWidth(), transformComp.pos.y+visualComp.visual.getHeight()+10);*/
	}

	private void updateSprite() {
		spriteDirection = velocityComp.vel.isZero() ? spriteDirection : getVelocityDirectionArea(4, 45);
		switch (spriteDirection) {
		case (0):
			visualComp.visual.currentAnimation = ANIM_WALK_RIGHT;
			break;
		case (1):
			visualComp.visual.currentAnimation = ANIM_WALK_UP;
			break;
		case (2):
			visualComp.visual.currentAnimation = ANIM_WALK_LEFT;
			break;
		case (3):
			visualComp.visual.currentAnimation = ANIM_WALK_DOWN;
			break;
		}
		//Bewegt sich nicht => keine animation
		if (velocityComp.vel.isZero()) {
			visualComp.visual.setCurrentAnimationFrame(0);
//			visualComp.visual.animate = false;
			visualComp.visual.speedFactor = 0.1f;
		} else {
			visualComp.visual.animate = true;
			visualComp.visual.speedFactor = 1f;
		}
	}
	
	public void setTarget(Player target) {
		this.target.set(target);
	}

	public DefaultEntity<?> getTarget() {
		
		if(target.get() == null) {
			for (Player player : Player.getAllPlayers()) {
				Vector2 v = new Vector2(player.transformComp.pos);
				//Das 500 muss noch geändert werden
				if (v.sub(transformComp.pos).len() < 250) {
					target.set(player);
					return target.get();
				}
			}
			return null;
		}
		
		DefaultEntity<?> targetEntity = target.get();
		
		Vector2 toTargetVector = new Vector2(targetEntity.transformComp.pos).sub(transformComp.pos);
		//500 muss noch geändert werden
		if(toTargetVector.len() > 250) {
			target.set(null);
			return null;
		}
		
		return targetEntity;
	}
	
	@Override
	public boolean canAttack() {
		DefaultEntity<?> target = getTarget();
		if(target == null)
			return false;
		Vector2 vecToTarget = new Vector2(target.transformComp.pos).sub(transformComp.pos);
		for(Action act : actions) {
			if(act instanceof Attack) {
				Attack attack = (Attack) act;
				if(attack.range >= vecToTarget.len() && !attack.isOnCooldown())
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean attack() {
		//TODO richtig machen, im moment wird einfach der angriff mit der kleinst möglichen range genommen
		float minRange = Float.MAX_VALUE;
		Attack attack = null;
		for(Action act : actions) {
			if(act instanceof Attack && !act.isOnCooldown() && ((Attack)act).range < minRange) {
				attack = (Attack) act;
				minRange = attack.range;
			}
		}
		if(attack == null)
			return false;
		return attack.act(this, world, engine);
	}

	@Override
	public void moveTowards(Vector2 targetPos) {
		Vector2 nextVel = new Vector2((targetPos.x - transformComp.pos.x), (targetPos.y - transformComp.pos.y));
		nextVel.nor();
		nextVel.scl(speed);
		velocityComp.vel.set(nextVel);
	}

	@Override
	public void idle() {
		stopMoving();
	}

	@Override
	public Collection<Action> getActions() {
		return actions;
	}

	@Override
	public void wake() {
		return;
	}
}
