package com.mygdx.game.items;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entites.Projectile;
import com.mygdx.game.entityComponents.visuals.SpriteVis;
import com.mygdx.game.utils.Const.Paths;

public class Pistol extends Gun {

	public Pistol() {
		super("Pistol", 1.5f, 50, 50, 10, 10, 1500, false, 0);
	}

	@Override
	public boolean tryShoot(World world, Engine engine, Camera cam, AssetManager assets) {
		if(owner == null)
			return false;
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.justTouched()) {
			Vector3 mousePos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			
			Sprite shotSprite = new Sprite(assets.get(Paths.SPRITES+"bullet.png", Texture.class));
			shotSprite.setSize(25f, 25f);
			Projectile shot = new Projectile(world, engine, owner,
					getOwnerCenter(),
					new SpriteVis(shotSprite),
					getProjectileVelocityTowards(new Vector2(mousePos.x, mousePos.y), true),
					new Vector2(mousePos.x, mousePos.y).sub(owner.getTransformComp().pos).angle(),
					2, 5000/bulletSpeed);
			
			engine.addEntity(shot);
			
			return true;
		}
		return false;
	}

}
