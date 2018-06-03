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

public class MachineGun extends Gun {

	public MachineGun() {
		super("MG", 5.f, 300, 300, 50, 10f, 500, true, 5);
	}

	@Override
	protected boolean tryShoot(World world, Engine engine, Camera cam) {
		if(owner == null)
			return false;
		

		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			Vector3 mousePos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

			Sprite shotSprite = new Sprite(new Texture("bullet.png"));
			shotSprite.setSize(25f, 25f);
			//Das projektil wird 5k units fliegen bevor es despawnt
			Projectile shot = new Projectile(world, engine, owner,
					getOwnerCenter(),
					new SpriteVis(shotSprite),
					getProjectileVelocityTowards(new Vector2(mousePos.x, mousePos.y), true),
					new Vector2(mousePos.x, mousePos.y).sub(owner.getPositionComp().pos).angle(),
					2, 5000/bulletSpeed);
			engine.addEntity(shot);
			return true;
		}
		return false;
	}


}
