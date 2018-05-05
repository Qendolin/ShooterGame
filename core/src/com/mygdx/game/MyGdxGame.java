package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entites.Enemy;
import com.mygdx.game.entites.EnemyType;
import com.mygdx.game.entites.Player;
import com.mygdx.game.entityComponents.BodyComp;
import com.mygdx.game.entityComponents.PositionComp;
import com.mygdx.game.entityComponents.RotationComp;
import com.mygdx.game.entityComponents.UpdateEventComp;
import com.mygdx.game.entityComponents.VelocityComp;
import com.mygdx.game.entityComponents.VisualComp;
import com.mygdx.game.entityComponents.events.CollisionEvent;
import com.mygdx.game.entityComponents.misc.BodyDeleteFlag;
import com.mygdx.game.entityComponents.misc.CollisionEntityConnection;
import com.mygdx.game.entityComponents.visuals.SpriteSheetSpriteGroup;
import com.mygdx.game.entityComponents.visuals.SpriteSheetVis;
import com.mygdx.game.entityComponents.visuals.Visual;
import com.mygdx.game.items.Pistol;
import com.mygdx.game.items.Shotgun;
import com.mygdx.game.utils.BodyFactory;
import com.mygdx.game.utils.Const;
import com.mygdx.game.utils.Const.RenderLayer;

public class MyGdxGame extends ApplicationAdapter {
	
	private SpriteBatch batch;
	private Engine engine;
	private Player player;
	private Camera cam;
	private World world;
	private Box2DDebugRenderer physicDebugRenderer;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.graphics.setWindowedMode(1600/2, 900/2);
		
		engine = new Engine();
		
		batch = new SpriteBatch();
		Texture playerSpriteSheet = new Texture("george.png");
		Texture enemySpriteSheet = new Texture("bahamut.png");
		
		world = new World(new Vector2(), false);
		world.setContactListener(new ContactListener() {
			
			@Override
			public void preSolve(Contact contact, Manifold arg1) {
			}
			
			@Override
			public void postSolve(Contact arg0, ContactImpulse arg1) {		
			}
			
			@Override
			public void endContact(Contact contact) {
			}
			
			@Override
			public void beginContact(Contact contact) {
				Fixture fixA = contact.getFixtureA();
				Fixture fixB = contact.getFixtureB();
				if(fixA == null || fixB == null) return;
				if(fixA.getUserData() == null && ! (fixA.getUserData() instanceof CollisionEntityConnection)) return;
				if(fixB.getUserData() == null && ! (fixB.getUserData() instanceof CollisionEntityConnection)) return;
				CollisionEntityConnection connectionA = (CollisionEntityConnection) fixA.getUserData();
				CollisionEntityConnection connectionB = (CollisionEntityConnection) fixB.getUserData();
				if(connectionA.listener != null)
					connectionA.listener.handle(new CollisionEvent(contact, connectionA.entity, connectionB.entity));
				if(connectionB.listener != null)
					connectionB.listener.handle(new CollisionEvent(contact, connectionB.entity, connectionA.entity));
			}
		});
		
		BodyFactory.createBorder(world, new Vector2(), 1000, 1000);
		
		physicDebugRenderer = new Box2DDebugRenderer();
		
		player = new Player(world, engine, new Vector2(), new SpriteSheetVis(playerSpriteSheet, 4, 4, true, new SpriteSheetSpriteGroup(0, 3, 0.1f, Player.ANIM_WALK_DOWN), 
																			   new SpriteSheetSpriteGroup(4, 7, 0.1f, Player.ANIM_WALK_LEFT),
																			   new SpriteSheetSpriteGroup(8, 11, 0.1f, Player.ANIM_WALK_UP),
																			   new SpriteSheetSpriteGroup(12, 15, 0.1f, Player.ANIM_WALK_RIGHT),
																			   new SpriteSheetSpriteGroup(0, 3, 0.05f, Player.ANIM_RUN_DOWN), 
																			   new SpriteSheetSpriteGroup(4, 7, 0.05f, Player.ANIM_RUN_LEFT),
																			   new SpriteSheetSpriteGroup(8, 11, 0.05f, Player.ANIM_RUN_UP),
																			   new SpriteSheetSpriteGroup(12, 15, 0.05f, Player.ANIM_RUN_RIGHT)));
		player.setItem(new Pistol());
		engine.addEntity(player);
		//boss
		engine.addEntity(new Enemy(EnemyType.Boss1, world, engine, new Vector2(), new SpriteSheetVis(enemySpriteSheet, 4, 4, false, new SpriteSheetSpriteGroup(0, 3, 0.1f, Enemy.ANIM_WALK_DOWN), 
				   new SpriteSheetSpriteGroup(4, 7, 0.1f, Enemy.ANIM_WALK_LEFT),
				   new SpriteSheetSpriteGroup(8, 11, 0.1f, Enemy.ANIM_WALK_RIGHT),
				   new SpriteSheetSpriteGroup(12, 15, 0.1f, Enemy.ANIM_WALK_UP))));
		cam = new OrthographicCamera(Gdx.graphics.getWidth()*1.25f, Gdx.graphics.getHeight()*1.25f);
		cam.position.x = Gdx.graphics.getWidth()/2; 
		cam.position.y = Gdx.graphics.getHeight()/2;
	}

	@Override
	public void render () {
		updateUpdateListeners();
		
		//Dieser prozess kann vereinfacht werden
		//Dinge bewegen
		if(!Gdx.input.isKeyPressed(Input.Keys.SPACE)) //Space drücken stoppt alle bewegeung
			updateVelocities();
		//Physik simulieren
		world.step(Gdx.graphics.getDeltaTime(), 2, 6);
		//Simulierte physik anwenden
		fixPositions();
		
		//Bodyies mit BodyDeleteFlag als UserData löschen
		deleteBodies();
		
		//Camera bewegen
		//Die kontrolle der kamera gehört nicht in die spieler klasse!! (Weil man z.b. mehrer spieler haben kann)
		cam.position.x = player.getPositionComp().pos.x;
		cam.position.y = player.getPositionComp().pos.y;
		
		//Sprites rendern
		cam.update();
		Family renderableFamily = Family.all(VisualComp.class, PositionComp.class).get();
		ImmutableArray<Entity> renderables = engine.getEntitiesFor(renderableFamily);
		
		//Sprites sortiert nach layer
		@SuppressWarnings("unchecked")
		ArrayList<Sprite>[] sorted = (ArrayList<Sprite>[])new ArrayList[RenderLayer.MAX-RenderLayer.MIN + 1];
		for(int i = 0; i < sorted.length; i++) {
			sorted[i] = new ArrayList<>();
		}
		
		for(Entity renderable : renderables) {
			VisualComp<?> visualComp = renderable.getComponent(VisualComp.class);
			for(int i = 0; i < visualComp.visual.getNumberOfSprites() || i == 0; i++) {
				Sprite sprite = visualComp.visual.get(i);
				if(sprite == null)
					continue;
				Vector2 pos = renderable.getComponent(PositionComp.class).pos;
				//Zentrieren
				sprite.setPosition(pos.x-sprite.getWidth()/2f, pos.y-sprite.getHeight()/2f);
//				sprite.draw(batch);
				sorted[visualComp.visual.renderLayer-RenderLayer.MIN].add(sprite);
			}
		}
		
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		for(int i = 0; i <= RenderLayer.MAX - RenderLayer.MIN; i++) {
			ArrayList<Sprite> layerSprites = sorted[i];
			for(Sprite sprite : layerSprites)
				sprite.draw(batch);
		}
		batch.end();
		
		//Physic debug rener
		Matrix4 physicMVMatrix = cam.combined.cpy();
		physicMVMatrix.scl(Const.METER_TO_PIXEL_RATIO);
		physicDebugRenderer.render(world, physicMVMatrix);
		
		Gdx.graphics.setTitle("FPS: "+Gdx.graphics.getFramesPerSecond());
	}

	private void deleteBodies() {
		Array<Body> bodies = new Array<Body>(world.getBodyCount());
		world.getBodies(bodies);
		for(int i = 0; i < bodies.size; i++) {
			if(bodies.get(i).getUserData() != null && bodies.get(i).getUserData() instanceof BodyDeleteFlag)
				world.destroyBody(bodies.get(i));
			
		}
	}

	private void updateUpdateListeners() {
		ImmutableArray<Entity> updateables = engine.getEntitiesFor(Family.all(UpdateEventComp.class).get());
		for(Entity updateable : updateables) {
			UpdateEventComp updateEventComp = updateable.getComponent(UpdateEventComp.class);
			updateEventComp.update(world, engine, cam);
		}
	}

	private void fixPositions() {
		Family colliderFamily = Family.one(BodyComp.class, PositionComp.class).get();
		ImmutableArray<Entity> colliders = engine.getEntitiesFor(colliderFamily);
		for(Entity collider : colliders) {
			BodyComp colliderComp = collider.getComponent(BodyComp.class);
			PositionComp positionComp = collider.getComponent(PositionComp.class);
			if(colliderComp != null && positionComp != null)
				positionComp.pos = new Vector2(colliderComp.getPosition()).scl(Const.METER_TO_PIXEL_RATIO);
		}
	}

	/*private void updatePositions() {
		Family movableFamily = Family.all(VelocityComp.class, PositionComp.class).get();
		ImmutableArray<Entity> movables = engine.getEntitiesFor(movableFamily);
		for(Entity movable : movables) {
			Vector2 vel = movable.getComponent(VelocityComp.class).vel;
			Vector2 pos = movable.getComponent(PositionComp.class).pos;
			if(vel == null || pos == null)
				continue;
			pos.x+=vel.x*Gdx.graphics.getDeltaTime();
			pos.y+=vel.y*Gdx.graphics.getDeltaTime();
			ColliderComp colliderComp = movable.getComponent(ColliderComp.class);
			if(colliderComp != null) {
				RotationComp rotation = movable.getComponent(RotationComp.class);
				if(rotation != null)
					colliderComp.getBody().setTransform(pos, rotation.asRadians());
				else
					colliderComp.getBody().setTransform(pos, 0);
			}
		}
	}*/
	
	private void updateVelocities() {
		Family movableFamily = Family.all(VelocityComp.class).one(VelocityComp.class, PositionComp.class).get();
		ImmutableArray<Entity> movables = engine.getEntitiesFor(movableFamily);
		for(Entity movable : movables) {
			Vector2 vel = movable.getComponent(VelocityComp.class).vel;
			Vector2 pos = movable.getComponent(PositionComp.class).pos;
			if(vel == null)
				continue;
			BodyComp colliderComp = movable.getComponent(BodyComp.class);
			if(colliderComp != null) {
				RotationComp rotation = movable.getComponent(RotationComp.class);
				if(rotation != null)
					colliderComp.getBody().setTransform(colliderComp.getPosition(), rotation.asRadians());
				else
					colliderComp.getBody().setTransform(colliderComp.getPosition(), 0);
				vel = new Vector2(vel);
				vel.scl(Const.PIXEL_TO_METER_RATIO);
				colliderComp.getBody().setLinearVelocity(vel);
				if(vel.len()*Gdx.graphics.getDeltaTime() > 2) {
					Gdx.app.log(Const.LogTags.PHYSIC, "Warning: "+movable+" is Moving faster than 2 m/s! The maximum speed is 2 m/s.");
				}
			} else {
				pos.x+=vel.x*Const.METER_TO_PIXEL_RATIO*Gdx.graphics.getDeltaTime();
				pos.y+=vel.y*Const.METER_TO_PIXEL_RATIO*Gdx.graphics.getDeltaTime();
			}
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		Family spriteFamily = Family.all(VisualComp.class).get();
		ImmutableArray<Entity> sprites = engine.getEntitiesFor(spriteFamily);
		for(Entity spriteEntity : sprites) {
			Visual sprite = spriteEntity.getComponent(VisualComp.class).visual;
			if(sprite != null)
				sprite.dispose();
		}
	}
}
