package com.mygdx.game.entites;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.entityComponents.*;
import com.mygdx.game.entityComponents.events.UpdateListener;
import com.mygdx.game.entityComponents.visualComps.SpriteSheetComp;
import com.mygdx.game.items.Gun;
import com.mygdx.game.items.Item;
import com.mygdx.game.utils.Const;

public class Enemy extends Entity {

	// die Werte von Boss1 werden für diesen Boss verwendet
	public HealthComp healthComp;
	public Item item;
	public SpriteSheetComp visualComp;
	public ColliderComp collisionComp;
	public PositionComp positionComp;
	public VelocityComp velocityComp;
	private UpdateEventComp updateComp = new UpdateEventComp(new UpdateListener() {
		@Override
		public void onUpdate(World world, Engine engine, Camera cam) {
			update(world, cam, engine);
		}
	});
	public float attackRadius;
	public float speed;

	public static float enemyHitCircleRadiusMultiplyer = 0.8f;
	public static float enemyRangeCircleRadiusMultiplyer = 30f; //Ich würde den Spieler "detections" radius nicht als multiplyer festlegen (also von der größe des sprites abhängig machen) sonder als fixen wert festlegen

	public Enemy(EnemyType type, SpriteSheetComp visual, World world, Vector2 position) {
		speed = type.speed;
		item = type.item;
		add(visual);
		visualComp = visual;
		healthComp = new HealthComp(type.health);
		add(healthComp);
		velocityComp = new VelocityComp();
		add(velocityComp);
		positionComp = new PositionComp(position);
		add(positionComp);
		collisionComp = new ColliderComp(world, visualComp.getCenter(),
				((visual.getHeight() + visual.getWidth()) / 4f) * enemyHitCircleRadiusMultiplyer, BodyType.DynamicBody,
				Const.BIG_ENTITY, (short) (Const.BIG_ENTITY ^ Const.DEFAULT));
		add(collisionComp);
		attackRadius = (((visual.getHeight() + visual.getWidth()) / 4f) * enemyRangeCircleRadiusMultiplyer) / 2;
		add(updateComp);
	}

	public void update(World world, Camera cam, Engine engine) {
		/*
		 * Enemy Movement,Angriffsmuster und Rendering ist noch zu machen
		 */
		//

		for (Player player : Player.getAllPlayers()) {
			Vector2 v = new Vector2(player.positionComp.pos);
			if (v.sub(positionComp.pos).len() < attackRadius) {
				System.out.println("noob");
			}
		}
	}
}
