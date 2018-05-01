package com.mygdx.game.Action;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entites.Enemy;

public interface Action {

	public void doAction(Enemy enemy,World world,Engine engine,Camera cam);
}
