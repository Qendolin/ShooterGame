package com.mygdx.game.utils;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entites.Enemy;
import com.mygdx.game.entites.EnemyType;
import com.mygdx.game.entityComponents.visuals.SpriteSheetVis;

public class EnemyFactory {

	private EnemyType type;
	private SpriteSheetVis visual;
	private World world;
	private Engine engine;
	
	private EnemyFactory(EnemyType type, SpriteSheetVis visual, World world, Engine engine) {
		this.type = type;
		this.visual = visual;
		this.world = world;
		this.engine = engine;
	}
	
	public void spawn(Vector2 position) {
		engine.addEntity(new Enemy(type, world, engine, position, new SpriteSheetVis(visual)));
	}
	
	public void spawn(Vector2 position, float radius, int amount) {
		for(int i = 0; i < amount; i++) {
			Vector2 spawnPos = new Vector2(position);
			spawnPos.add(new Vector2(0, 0).setToRandomDirection().scl((float) (Math.random()*radius)));
			spawn(spawnPos);
		}
	}
	
	public static EnemyFactory create(World world, Engine engine, EnemyType type, SpriteSheetVis visual) {
		return new EnemyFactory(type, visual, world, engine);
	}
	
}
