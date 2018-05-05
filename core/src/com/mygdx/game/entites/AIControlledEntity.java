package com.mygdx.game.entites;

import java.util.Collection;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.actions.Action;
import com.mygdx.game.ai.states.TestState;
import com.mygdx.game.entityComponents.visuals.Visual;

import javafx.beans.property.SimpleObjectProperty;

public abstract class AIControlledEntity<VISUAL extends Visual> extends DefaultEntity<VISUAL>{

	public SimpleObjectProperty<DefaultEntity<?>> target = new SimpleObjectProperty<DefaultEntity<?>>();
	private DefaultEntity<?> lastTarget;
	private Vector2 lastTargetLastPosition;
	public StateMachine<AIControlledEntity<?>, State<AIControlledEntity<?>>> stateMachine;;
	
	public AIControlledEntity(Vector2 position, VISUAL visual, Engine engine, World world) {
		super(position, visual, engine, world);
		target.addListener((value, oldVal, newVal) -> {
			lastTarget = oldVal;
			if(lastTarget != null)
				lastTargetLastPosition = new Vector2(lastTarget.positionComp.pos);
		});
		stateMachine = new DefaultStateMachine<AIControlledEntity<?>, State<AIControlledEntity<?>>>(this);
		stateMachine.setInitialState(TestState.IDLE);
	}
	
	public abstract boolean canAttack();
	
	public abstract DefaultEntity<?> getTarget();
	
	public DefaultEntity<?> getLastTarget() {
		return lastTarget;
	}
	
	public Vector2 getLastTargetLastPosition() {
		return lastTargetLastPosition;
	}
	
	/**
	 * @return success
	 */
	public abstract boolean attack();
	
	public abstract void moveTowards(Vector2 targetPos);

	public abstract void idle();
	
	/**
	 * Wird aufgerufen wenn sich der Status von SLEEP auf etwas anderes ändert
	 */
	public abstract void wake();
	
	public abstract Collection<Action> getActions();
}
