package com.mygdx.game.ai.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.mygdx.game.entites.Enemy;

public enum TestState implements State<Enemy> {

	RUN_AWAY() {
		@Override
		public void update(Enemy troll) {
		/*	if (troll.isSafe()) {
				troll.stateMachine.changeState(SLEEP);
			}
			else {
				troll.moveAwayFromEnemy();
			}*/
		}
	},

	SLEEP() {
		@Override
		public void update(Enemy troll) {
			/*if (troll.isThreatened()) {
				troll.stateMachine.changeState(RUN_AWAY);
			}
			else {
				troll.snore();
			}*/
		}
	};

	@Override
	public void enter(Enemy troll) {
	}

	@Override
	public void exit(Enemy troll) {
	}

	@Override
	public boolean onMessage(Enemy troll, Telegram telegram) {
		// We don't use messaging in this example
		return false;
	}
}
