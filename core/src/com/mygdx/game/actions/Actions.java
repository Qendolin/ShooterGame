package com.mygdx.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.actions.attacks.Attack;
import com.mygdx.game.entites.Enemy;

public class Actions{

	public static final Attack BEAM = new Attack(10, 250) {
		
		@Override
		public boolean doAction(Enemy enemy, World world, Engine engine) {
			// TODO Auto-generated method stub
			System.out.println("BEAM!");
			return true;
		}
	};
	
	public static final Attack WHIRL = new Attack(5, 50) {
		
		@Override
		public boolean doAction(Enemy enemy, World world, Engine engine) {
			// TODO Auto-generated method stub
			System.out.println("WHIRL!");
			return true;
		}
	};
	

}
