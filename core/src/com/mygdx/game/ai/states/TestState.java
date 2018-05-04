package com.mygdx.game.ai.states;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.actions.Action;
import com.mygdx.game.actions.attacks.Attack;
import com.mygdx.game.entites.AIControlledEntity;
import com.mygdx.game.entites.DefaultEntity;

public enum TestState implements State<AIControlledEntity<?>> {

	SLEEP() {
		@Override
		public void update(AIControlledEntity<?> entity) {
			DefaultEntity<?> target = entity.getTarget();
			if(target != null) {
				Vector2 vecToTarget = new Vector2(target.getPositionComp().pos).sub(entity.getPositionComp().pos);
				for(Action act : entity.getActions()) {
					if(act instanceof Attack) {
						if(((Attack)act).range <= vecToTarget.len()) {
							entity.stateMachine.changeState(ATTACK);
						}
					}
				}
				
				 //Kann nicht attackieren 
				if(entity.stateMachine.getCurrentState() == SLEEP) {
					entity.stateMachine.changeState(PURSUE);
				}
			} else {
				entity.idle();
			}
		}
	},
	
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

	PURSUE() {
		@Override
		public void update(AIControlledEntity<?> entity) {
			DefaultEntity<?> target = entity.getTarget();
			if(target == null) {
				entity.stateMachine.changeState(SLEEP);
				return;
			}
			entity.moveTowards(target.getPositionComp().pos);
		}
		
	},
	
	ATTACK() {
		@Override
		public void update(AIControlledEntity<?> entity) {
			if(entity.getTarget() != null)
				entity.attack();
			else
				entity.stateMachine.changeState(SLEEP);
		}
		
	};
	
	@Override
	public void enter(AIControlledEntity<?> entity) {
		System.out.println("Exited State: " + entity.stateMachine.getPreviousState());
		System.out.println("Entered State: " + entity.stateMachine.getCurrentState());
		if(entity.stateMachine.getCurrentState() != SLEEP && entity.stateMachine.getPreviousState() == SLEEP)
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
