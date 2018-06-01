package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.mygdx.game.engineSystems.MovementSystem;
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
import com.mygdx.game.screens.StartScreen;
import com.mygdx.game.utils.BodyFactory;
import com.mygdx.game.utils.Const;
import com.mygdx.game.utils.Const.RenderLayer;
import com.mygdx.game.utils.EnemyFactory;

public class ShooterGame extends Game {
	
	
	public SpriteBatch batch;
	public Engine engine;
	//TODO: AssetManager
	public AssetManager assetManager = new AssetManager();
	public BitmapFont font;

	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.graphics.setWindowedMode(1600/2, 900/2);
		
		font = new BitmapFont();
		
		batch = new SpriteBatch();
		
		setScreen(new StartScreen(this));
	}

	@Override
	public void render () {
		super.render();
		Gdx.graphics.setTitle("FPS: "+Gdx.graphics.getFramesPerSecond());
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
	

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		Family spriteFamily = Family.all(VisualComp.class).get();
		if(engine != null) {
			ImmutableArray<Entity> sprites = engine.getEntitiesFor(spriteFamily);
			for(Entity spriteEntity : sprites) {
				Visual sprite = spriteEntity.getComponent(VisualComp.class).visual;
				if(sprite != null)
					sprite.dispose();
			}
		}
	}
}
