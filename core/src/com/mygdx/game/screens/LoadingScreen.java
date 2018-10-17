package com.mygdx.game.screens;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.ShooterGame;
import com.mygdx.game.utils.Const.Paths;

public class LoadingScreen implements com.badlogic.gdx.Screen {

	public AssetManager assets;
	public ShooterGame game;
	public Constructor<? extends Screen> nextScreen;
	private ProgressBar bar;
	private Stage stage;
	
	public LoadingScreen(ShooterGame game, AssetManager assets, Constructor<? extends Screen> constructor) {
		this.assets = assets;
		this.game = game;
		this.nextScreen = constructor;
		
		stage = new Stage(new ScreenViewport());
		
		Skin skin = new Skin();
		Pixmap pixmap = new Pixmap(10, 10, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		
		TextureRegionDrawable textureBar = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(Paths.SPRITES+"HBMidground.png"))));
		ProgressBarStyle barStyle = new ProgressBarStyle(skin.newDrawable("white", Color.DARK_GRAY), textureBar);
		barStyle.knobBefore = barStyle.knob;
		bar = new ProgressBar(0, 1, 0.01f, false, barStyle);
		bar.setPosition(10, 10);
		bar.setSize(100, bar.getPrefHeight());
		bar.setAnimateDuration(2);
		stage.addActor(bar);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		if(assets.update()) {
			try {
				game.setScreen(nextScreen.newInstance(new Object[] {game, assets}));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		float progress = assets.getProgress();
		bar.setValue(progress);
		
		stage.draw();
		stage.act();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

}
