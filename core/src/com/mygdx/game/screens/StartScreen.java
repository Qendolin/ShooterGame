package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.ShooterGame;

/**
 * StartScreen kontrolliert das, was angezeigt wird sobald das Fenster da ist.
 * In dem Fall einfach nur text.
 */
public class StartScreen implements Screen {

	private final ShooterGame game = ShooterGame.instance;
	private OrthographicCamera cam;
	
	public StartScreen() {
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 800, 480);
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
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		cam.update();
		game.batch.setProjectionMatrix(cam.combined);

		game.batch.begin();
		game.font.draw(game.batch, "Shooter Game", 100, 150);
		game.font.draw(game.batch, "Klicke irgendwo um zu beginnen!", 100, 100);
		game.batch.end();

		/**
		 * Sobald der User die linke Maustaste drückt wird das Spiel gestarten. Dazu muss der Screen auf den Game Screen geändert werden.
		 * In diesem fall wird der Screen nur erzeugt und nicht "Angewant" da der GameScreen den Screen gleich auf Einen Loading Screen ändert
		 */
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			new GameScreen();
			dispose();
		}
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
