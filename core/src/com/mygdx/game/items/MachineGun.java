package com.mygdx.game.items;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.entityComponents.PositionComp;
import com.mygdx.game.entityComponents.VelocityComp;
import com.mygdx.game.entityComponents.visualComps.SpriteComp;

public class MachineGun extends Gun {

	public MachineGun() {
		super("MG", 5.f, 200, 300, 50, 10f, 500, true);
	}

	@Override
	protected boolean tryShoot(Engine engine, Camera cam) {
		if(owner == null)
			return false;
		

		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			Vector3 mousePos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

			Entity shot = new Entity();
			shot.add(new VelocityComp(getProjectileVelocityTowards(new Vector2(mousePos.x, mousePos.y))));
			Sprite shotSprite = new Sprite(new Texture("badlogic.jpg"));
			shotSprite.setSize(25f, 25f);
			shot.add(new PositionComp(getOwnerCenter().sub((shotSprite.getWidth()*shotSprite.getScaleX())/2f, (shotSprite.getHeight()*shotSprite.getScaleY())/2f)));
			shot.add(new SpriteComp(shotSprite));
			engine.addEntity(shot);
			return true;
		}
		return false;
	}


}
