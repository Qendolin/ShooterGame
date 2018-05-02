package com.mygdx.game.entites;

import java.util.ArrayList;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.entityComponents.ColliderComp;
import com.mygdx.game.entityComponents.FixedAccelerationComp;
import com.mygdx.game.entityComponents.UpdateEventComp;
import com.mygdx.game.entityComponents.events.UpdateListener;
import com.mygdx.game.entityComponents.visualComps.SpriteSheetComp;
import com.mygdx.game.items.Item;
import com.mygdx.game.utils.Const;

public class Player extends DefaultEntity<SpriteSheetComp> {

	public static final String ANIM_WALK_DOWN = "walkDown";
	public static final String ANIM_WALK_UP = "walkUp";
	public static final String ANIM_WALK_LEFT = "walkLeft";
	public static final String ANIM_WALK_RIGHT = "walkRight";
	public static final String ANIM_RUN_DOWN = "runDown";
	public static final String ANIM_RUN_UP = "runUp";
	public static final String ANIM_RUN_LEFT = "runLeft";
	public static final String ANIM_RUN_RIGHT = "runRight";
	
	public static ArrayList<Player> playerList = new ArrayList<>();
	
	public static float playerHitCircleRadiusMultiplyer = 0.8f;
	public static float playerBaseSpeed = 150;
	
	public float currentPlayerSpeed = playerBaseSpeed;
	
	private Item item;
	
	public ColliderComp collisionComp;
	
	private boolean running = false;
	private int playerDirection;
	
	public Player( World world, Vector2 position, SpriteSheetComp visual) {
		super(position, visual);
		accelerationComp = new FixedAccelerationComp(playerBaseSpeed*5f);
		add(accelerationComp);
		createColliderComp(world, visual);
		add(collisionComp);
		updateComp = new UpdateEventComp(new UpdateListener() {
			@Override
			public void onUpdate(World world, Engine engine, Camera cam) {
				update(world, cam, engine);
			}});
		add(updateComp);
		
		playerList.add(this);
	}
	
	private void createColliderComp(World world, SpriteSheetComp visual) {
		collisionComp = new ColliderComp(world, visualComp.getCenter(), this,
				((visual.getHeight()+visual.getWidth())/4f)*playerHitCircleRadiusMultiplyer, 
				BodyType.DynamicBody, Const.PLAYER, (short) (Const.ENTITY ^ Const.ALL));
	}
/*
	public Player(SpriteSheetComp visual, VelocityComp velocity, PositionComp position, World world) {
		add(visual);
		visualComp = visual;
		add(velocity);
		velocityComp = velocity;
		add(position);
		positionComp = position;
		add(accelerationComp);
		createColliderComp(world, visual);
		add(collisionComp);
		playerList.add(this);
	}
	
	public Player(SpriteSheetComp visual, VelocityComp velocity, PositionComp position, FixedAccelerationComp acceleration, World world) {
		add(visual);
		visualComp = visual;
		add(velocity);
		velocityComp = velocity;
		add(position);
		positionComp = position;
		add(acceleration);
		accelerationComp = acceleration;
		createColliderComp(world, visual);
		add(collisionComp);
		playerList.add(this);
	}*/
	
	public void setItem(Item item) {
		this.item = item;
		item.setOwner(this);
	}

	public Item getItem() {
		return item;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void update(World world, Camera cam, Engine engine) {
		//Movement
		
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			currentPlayerSpeed = playerBaseSpeed * 1.8f;
			running = true;
		}
		else {
			currentPlayerSpeed = playerBaseSpeed;
			running = false;
		}
		
		
		Vector2 newVel = new Vector2();
		
		if(Gdx.input.isKeyPressed(Input.Keys.W))
			newVel.y += currentPlayerSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.A))
			newVel.x -= currentPlayerSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.S))
			newVel.y -= currentPlayerSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.D))
			newVel.x += currentPlayerSpeed;

		accelerationComp.accelerateTo(newVel, velocityComp.vel);
		
		// Quadranten der richtung der bewegung ausrechnen
		playerDirection = newVel.isZero() ? playerDirection : getVelocityDirectionArea(4, 45);
		switch(playerDirection) {
			case(0):
				visualComp.currentAnimation = running ? ANIM_RUN_RIGHT : ANIM_WALK_RIGHT;
				break;
			case(1):
				visualComp.currentAnimation = running ? ANIM_RUN_UP : ANIM_WALK_UP;
				break;
			case(2):
				visualComp.currentAnimation = running ? ANIM_RUN_LEFT : ANIM_WALK_LEFT;
				break;
			case(3):
				visualComp.currentAnimation = running ? ANIM_RUN_DOWN : ANIM_WALK_DOWN;
				break;
		}
		
		if(newVel.isZero()) {
			visualComp.setCurrentAnimationFrame(0);
			visualComp.animate = false;
		} else
			visualComp.animate = true;
		
		//Item
		if(item != null)
			item.update(world, engine, cam);
	}
	
	public static ArrayList<Player> getAllPlayers(){
		return playerList;
	}
	
}
