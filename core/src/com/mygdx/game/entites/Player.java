package com.mygdx.game.entites;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entityComponents.BodyComp;
import com.mygdx.game.entityComponents.FixedAccelerationComp;
import com.mygdx.game.entityComponents.HealthComp;
import com.mygdx.game.entityComponents.UpdateEventComp;
import com.mygdx.game.entityComponents.events.UpdateListener;
import com.mygdx.game.entityComponents.visuals.SpriteSheetVis;
import com.mygdx.game.items.Item;
import com.mygdx.game.items.MachineGun;
import com.mygdx.game.items.Pistol;
import com.mygdx.game.items.Shotgun;
import com.mygdx.game.utils.Const;

public class Player extends DefaultEntity<SpriteSheetVis> {

	public static final String ANIM_WALK_DOWN = "walkDown";
	public static final String ANIM_WALK_UP = "walkUp";
	public static final String ANIM_WALK_LEFT = "walkLeft";
	public static final String ANIM_WALK_RIGHT = "walkRight";
	public static final String ANIM_RUN_DOWN = "runDown";
	public static final String ANIM_RUN_UP = "runUp";
	public static final String ANIM_RUN_LEFT = "runLeft";
	public static final String ANIM_RUN_RIGHT = "runRight";
	
	public static ArrayList<Player> playerList = new ArrayList<>();
	
	public static float playerHitCircleRadiusMultiplyer = 0.7f;
	public static float playerBaseSpeed = 150f;
	
	public float currentPlayerSpeed = playerBaseSpeed;
	
	private Item item;
	//Sollte eigentlich nicht von anfang an gefüllt sein ist aber zurzeit am einfachseten
	private Item[] arsenal = new Item[] {new MachineGun(), new Shotgun(), new Pistol()};
	private int itemIndex = 0;
	
	private boolean running = false;
	private int playerDirection;
	
	public Player( World world, Engine engine, Vector2 position, SpriteSheetVis visual) {
		super(position, visual, engine, world);
		accelerationComp = new FixedAccelerationComp(playerBaseSpeed*5f);
		add(accelerationComp);
		createColliderComp(world, visual);
		add(bodyComp);
		healthComp = new HealthComp(500, null);
		add(healthComp);
		HealthBar healthBar = new HealthBar(transformComp, new Vector2(0, visual.getHeight()/2), healthComp);
		engine.addEntity(healthBar);
		updateComp = new UpdateEventComp(new UpdateListener() {
			@Override
			public void onUpdate(World world, Engine engine, Camera cam, AssetManager assets) {
				update(world, cam, engine, assets);
			}});
		add(updateComp);
		
		if(arsenal.length != 0)
			setItem(arsenal[0]);
		
		playerList.add(this);
	}
	
	private void createColliderComp(World world, SpriteSheetVis visual) {
		bodyComp = new BodyComp(world, visualComp.visual.getCenter(), this,
				((visual.getHeight()+visual.getWidth())/4f)*playerHitCircleRadiusMultiplyer, 
				BodyType.DynamicBody, Const.Collision.PLAYER, (short) (Const.Collision.PROJECTILE ^ Const.Collision.ENTITY ^ Const.Collision.ALL));
	}
	
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
	
	public void update(World world, Camera cam, Engine engine, AssetManager assets) {
		//Movemente
		
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
				visualComp.visual.currentAnimation = running ? ANIM_RUN_RIGHT : ANIM_WALK_RIGHT;
				break;
			case(1):
				visualComp.visual.currentAnimation = running ? ANIM_RUN_UP : ANIM_WALK_UP;
				break;
			case(2):
				visualComp.visual.currentAnimation = running ? ANIM_RUN_LEFT : ANIM_WALK_LEFT;
				break;
			case(3):
				visualComp.visual.currentAnimation = running ? ANIM_RUN_DOWN : ANIM_WALK_DOWN;
				break;
		}
		
		if(newVel.isZero()) {
			visualComp.visual.setCurrentAnimationFrame(0);
			visualComp.visual.animate = false;
		} else
			visualComp.visual.animate = true;
		
		if(arsenal.length != 0) {
			if(Gdx.input.isKeyJustPressed(Keys.Q)) {
				itemIndex--;
				if(itemIndex < 0) itemIndex = arsenal.length-1;
				setItem(arsenal[itemIndex]);
			} else if(Gdx.input.isKeyJustPressed(Keys.E)) {
				itemIndex++;
				itemIndex %= arsenal.length;
				setItem(arsenal[itemIndex]);
			}
		}
		
		//Item
		if(item != null)
			item.update(world, engine, cam, assets);
	}
	
	public static ArrayList<Player> getAllPlayers(){
		return playerList;
	}
	
}
