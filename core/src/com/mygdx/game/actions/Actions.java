package com.mygdx.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entites.Enemy;

public class Actions{

	public static final Action BEAM = new Action(10) {
		
		@Override
		public void doAction(Enemy enemy, World world, Engine engine, Camera cam) {
			// TODO Auto-generated method stub
			System.out.println("BEAM!");
		}
	};
	
	public static final Action WHIRL = new Action(5) {
		
		@Override
		public void doAction(Enemy enemy, World world, Engine engine, Camera cam) {
			// TODO Auto-generated method stub
			System.out.println("WHIRL!");
		}
	};
	

}
