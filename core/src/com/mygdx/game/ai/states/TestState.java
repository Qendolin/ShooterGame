package com.mygdx.game.ai.states;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.actions.Action;
import com.mygdx.game.actions.attacks.Attack;
import com.mygdx.game.entites.AIControlledEntity;
import com.mygdx.game.entites.DefaultEntity;

public enum TestState implements State<AIControlledEntity<?>> {

	/**
	 * Wenn sonst nichts zu tun ist
	 */
	IDLE() {
		@Override
		public void update(AIControlledEntity<?> entity) {
			DefaultEntity<?> target = entity.getTarget();
			if(target != null) {
				entity.stateMachine.changeState(PURSUE);
			} else {
				entity.idle();
			}
		}
	},
	
	/*
	 * Wenn ein Enemy den Player nicht mehr sieht dann sucht er wo er ihn zuletzt gesehen hat
	 */
	SEARCH() {

		@Override
		public void update(AIControlledEntity<?> entity) {
			//epsilon ist der fehlerabstand. Also wenn sie nicht genau gleich sind z.B.: (0.99998,0,0) und (1,0,0),  dann sind sie trotzdem gleich
			if(entity.getLastTargetLastPosition().epsilonEquals(entity.getPositionComp().pos, 1)) {
				entity.stateMachine.changeState(IDLE);
			}
			else
				entity.moveTowards(entity.getLastTargetLastPosition());
		}
		
	},
	
	/*
	 * Fliehen
	 */
	FLEE() {
		@Override
		public void update(AIControlledEntity<?> entity) {
			DefaultEntity<?> target = entity.getLastTarget();
			Vector2 vecToTarget = new Vector2(target.getPositionComp().pos).sub(entity.getPositionComp().pos);
			vecToTarget.nor();
			vecToTarget.scl(-Float.MAX_VALUE);
			entity.moveTowards(vecToTarget);
			//TODO: Verbessern
		}
	},

	/**
	 * Verfolgen
	 */
	PURSUE() {
		@Override
		public void update(AIControlledEntity<?> entity) {
			DefaultEntity<?> target = entity.getTarget();
			if(target == null) {
				entity.stateMachine.changeState(SEARCH);
			} else {
				
				if(entity.canAttack()) {
					entity.stateMachine.changeState(ATTACK);
					return;
				}
				
				//Kann nicht angreifen also näher ran gehen
				entity.moveTowards(target.getPositionComp().pos);
			}
		}
		
	},
	
	/**
	 * Angreifen
	 */
	ATTACK() {
		@Override
		public void update(AIControlledEntity<?> entity) {
			if(entity.canAttack()) {
				entity.stopMoving();
				if(!entity.attack()) {
					//Konnte nicht angreifen
					entity.stateMachine.changeState(PURSUE);
				}
			}
			else
				entity.stateMachine.changeState(IDLE);
		}
		
	};
	
	@Override
	public void enter(AIControlledEntity<?> entity) {
		//System.out.println("Exited State: " + entity.stateMachine.getPreviousState());
		//System.out.println("Entered State: " + entity.stateMachine.getCurrentState());
		if(entity.stateMachine.getCurrentState() != IDLE && entity.stateMachine.getPreviousState() == IDLE)
			entity.wake();
	}

	@Override
	public void exit(AIControlledEntity<?> entity) {
	}

	@Override
	public boolean onMessage(AIControlledEntity<?> entity, Telegram telegram) {
		return false;
	}
}
