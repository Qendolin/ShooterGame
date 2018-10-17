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
	
	public static ShooterGame instance;
	
	public SpriteBatch batch;
	public Engine engine;
	public BitmapFont font;

	
	/**
	 * Verschiedens:
	 * Asset Manager - Lädt und bietet einfachen zugriff auf Assets
	 * ComponentMapper - Eine Hiflsklasse die schnell Entity Components (z.B.: Position, Geschwindigkeit, ...) findet
	 * Body - Ein Collider, ein Physikobjekt
	 * Entity - Eine von Ashley gemanagte Klasse/Objekt. In unserem Fall meißtens etwas das angezeigt Wird 
	 */
	
	/**
	 * Hier beginnt alles. Die Instanz wird in DesktopLauncher.java erzeugt dann wird diese Methode aufgerufen.
	 * Es werden ein paar Dinge festegleng (font, batch) die eig. in jedem Screen benötigt werden.
	 * Dann wird der Screen auf StartScreen gesetzt.
	 */
	@Override
	public void create () {
		ShooterGame.instance = this;
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.graphics.setWindowedMode(1600/2, 900/2);
		
		font = new BitmapFont();
		
		batch = new SpriteBatch();
		
		setScreen(new StartScreen());
	}

	@Override
	public void render () {
		super.render();
		Gdx.graphics.setTitle("FPS: "+Gdx.graphics.getFramesPerSecond());
	}

	/**
	 * Dispose wird aufgerufen wenn das Spiel beendet wird
	 */
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
