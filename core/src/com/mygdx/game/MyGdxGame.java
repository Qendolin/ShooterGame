package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entites.Player;
import com.mygdx.game.entityComponents.PositionComp;
import com.mygdx.game.entityComponents.VelocityComp;
import com.mygdx.game.entityComponents.VisualComp;
import com.mygdx.game.entityComponents.visualComps.AnimationComp;
import com.mygdx.game.entityComponents.visualComps.SpriteComp;
import com.mygdx.game.entityComponents.visualComps.SpriteSheetComp;
import com.mygdx.game.entityComponents.visualComps.SpriteSheetSpriteGroup;
import com.mygdx.game.items.MachineGun;
import com.mygdx.game.items.Pistol;

public class MyGdxGame extends ApplicationAdapter {
	
	private SpriteBatch batch;
	private Engine engine;
	private Player player;
	private Camera cam;
	
	@Override
	public void create () {
		
		Gdx.graphics.setWindowedMode(1600, 900);
		
		engine = new Engine();
		
		batch = new SpriteBatch();
		Texture playerSpriteSheet = new Texture("george.png");
		player = new Player(new SpriteSheetComp(playerSpriteSheet, 4, 4, true, new SpriteSheetSpriteGroup(0, 3, 0.1f, Player.ANIM_WALK_DOWN), 
																			   new SpriteSheetSpriteGroup(4, 7, 0.1f, Player.ANIM_WALK_LEFT),
																			   new SpriteSheetSpriteGroup(8, 11, 0.1f, Player.ANIM_WALK_UP),
																			   new SpriteSheetSpriteGroup(12, 15, 0.1f, Player.ANIM_WALK_RIGHT)));
		player.setItem(new Pistol());
		engine.addEntity(player);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.x = Gdx.graphics.getWidth()/2; 
		cam.position.y = Gdx.graphics.getHeight()/2;
	}

	@Override
	public void render () {
		
		player.update(cam, engine);
		
		updatePositions();
		
		cam.update();
		@SuppressWarnings("unchecked")
		Family renderableFamily = Family.one(VisualComp.class, PositionComp.class).get();
		ImmutableArray<Entity> renderables = engine.getEntitiesFor(renderableFamily);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		for(Entity renderable : renderables) {
			VisualComp visualComp = renderable.getComponent(AnimationComp.class);
			if(visualComp == null)
				visualComp = renderable.getComponent(SpriteComp.class);
			if(visualComp == null)
				visualComp = renderable.getComponent(SpriteSheetComp.class);
			if(visualComp == null)
				continue;
			Sprite sprite = visualComp.get();
			if(sprite == null)
				continue;
			Vector2 pos = renderable.getComponent(PositionComp.class).pos;
			sprite.setPosition(pos.x, pos.y);
//			batch.draw(sprite, pos.x, pos.y);
			sprite.draw(batch);
		}
		batch.end();
	}

	private void updatePositions() {
		@SuppressWarnings("unchecked")
		Family movableFamily = Family.one(VelocityComp.class, PositionComp.class).get();
		ImmutableArray<Entity> movables = engine.getEntitiesFor(movableFamily);
		for(Entity movable : movables) {
			Vector2 vel = movable.getComponent(VelocityComp.class).vel;
			Vector2 pos = movable.getComponent(PositionComp.class).pos;
			if(vel == null || pos == null)
				continue;
			pos.x+=vel.x*Gdx.graphics.getDeltaTime();
			pos.y+=vel.y*Gdx.graphics.getDeltaTime();
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		@SuppressWarnings("unchecked")
		Family spriteFamily = Family.one(SpriteComp.class).get();
		ImmutableArray<Entity> sprites = engine.getEntitiesFor(spriteFamily);
		for(Entity spriteEntity : sprites) {
			VisualComp sprite = spriteEntity.getComponent(SpriteComp.class);
			if(sprite != null)
				sprite.dispose();
		}
	}
}
