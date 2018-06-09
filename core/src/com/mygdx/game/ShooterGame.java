package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entityComponents.VisualComp;
import com.mygdx.game.entityComponents.visuals.Visual;
import com.mygdx.game.screens.StartScreen;

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
