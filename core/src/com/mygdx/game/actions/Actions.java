package com.mygdx.game.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.actions.attacks.Attack;
import com.mygdx.game.entites.DamageArea;
import com.mygdx.game.entites.Enemy;
import com.mygdx.game.utils.Const.Collision;

public class Actions{

	public static final Attack BEAM = new Attack(10, 1.5f, 250) {
		
		@Override
		public boolean doAction(Enemy enemy, World world, Engine engine, boolean begin, boolean end) {
			// TODO Auto-generated method stub
			
			if(begin) {
				//DamageArea beam = new DamageArea(world, engine, enemy.getTransformComp().pos, 0, 1, 30, Integer.MAX_VALUE, Collision.ALL, Collision.ATTACKABLE, 10, 10);
			}
				
			return true;
		}
	};
	
	public static final Attack WHIRL = new Attack(5, 2, 50) {
		
		@Override
		public boolean doAction(Enemy enemy, World world, Engine engine, boolean begin, boolean end) {
			// TODO Auto-generated method stub
			if(begin)
				System.out.println("WHIRL!");
			else
				System.out.println("whirling");
			return true;
		}
	};
	

}
