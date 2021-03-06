package com.mygdx.game.items;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entites.Projectile;
import com.mygdx.game.entityComponents.visuals.SpriteVis;

public class Shotgun extends Gun {

	public Shotgun() {
		super("Shotgun", 2, 40, 40, 2, 3, 1000, false, 15);
	}

	@Override
	protected boolean tryShoot(World world, Engine engine, Camera cam) {
		if(owner == null)
			return false;
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.justTouched()) {
			Vector3 mousePos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			
			for(int i = 0; i < 5; i++) {
				Sprite shotSprite = new Sprite(new Texture("badlogic.jpg"));
				shotSprite.setSize(25f, 25f);
				//Das projektiel fliegt zwischen 30 und 70 units
				Projectile shot = new Projectile(world, engine, owner,
						getOwnerCenter(),
						new SpriteVis(shotSprite),
						getProjectileVelocityTowards(new Vector2(mousePos.x, mousePos.y), true),
						2, (float) ((Math.random()*40f+30f)/bulletSpeed));
				engine.addEntity(shot);
			}
			return true;
		}
		return false;
	}

}
