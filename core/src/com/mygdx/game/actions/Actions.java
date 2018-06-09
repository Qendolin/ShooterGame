package com.mygdx.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.actions.attacks.Attack;
import com.mygdx.game.entites.Enemy;

public class Actions{

	public static final Attack BEAM = new Attack(10, 1.5f, 250) {
		
		@Override
		public boolean doAction(Enemy enemy, World world, Engine engine, boolean begin) {
			// TODO Auto-generated method stub
			/*
			if(begin)
				System.out.println("BEAM!");
			else
				System.out.println("Shooting Beam...");*/
			return true;
		}
	};
	
	public static final Attack WHIRL = new Attack(5, 2, 50) {
		
		@Override
		public boolean doAction(Enemy enemy, World world, Engine engine, boolean begin) {
			// TODO Auto-generated method stub
			if(begin)
				System.out.println("WHIRL!");
			else
				System.out.println("whirling");
			return true;
		}
	};
	

}
