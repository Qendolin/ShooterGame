package com.mygdx.game.entites;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.actions.Action;
import com.mygdx.game.entityComponents.BodyComp;
import com.mygdx.game.entityComponents.FixedAccelerationComp;
import com.mygdx.game.entityComponents.HealthComp;
import com.mygdx.game.entityComponents.TrasformationComp;
import com.mygdx.game.entityComponents.UpdateEventComp;
import com.mygdx.game.entityComponents.VelocityComp;
import com.mygdx.game.entityComponents.VisualComp;
import com.mygdx.game.entityComponents.events.DeathEvent;
import com.mygdx.game.entityComponents.events.DeathListener;
import com.mygdx.game.entityComponents.visuals.Visual;
import com.mygdx.game.utils.Const.RenderLayer;

public class DefaultEntity<VISUAL extends Visual> extends Entity implements Disposable {

	protected World world;
	protected Engine engine;
	protected TrasformationComp transformComp;
	protected VelocityComp velocityComp;
	/**
	 * Optional
	 */
	protected FixedAccelerationComp accelerationComp;
	protected VisualComp<VISUAL> visualComp;
	protected BodyComp bodyComp;
	protected UpdateEventComp updateComp;
	protected float speed;
	/**
	 * optional
	 */
	protected HealthComp healthComp;
	protected DeathListener disposeOnDeath = new DeathListener() {
		@Override
		protected boolean onDeath(DeathEvent event) {
			dispose();
			return true;
		}
	};
	protected ArrayList<Action> actions = new ArrayList<Action>();
	
	/**
	 * Initialisiert die Visuelle, Positions und Geschwindigkeitskomponente.
	 * @param world
	 * @param position
	 * @param visual
	 */
	public DefaultEntity(Vector2 position, VISUAL visual, Engine engine, World world) {
		this.engine = engine;
		this.world = world;
		visualComp=new VisualComp<VISUAL>(visual);
		visualComp.visual.renderLayer = RenderLayer.ENTITIES;
		add(visualComp);
		transformComp = new TrasformationComp(position);
		add(transformComp);
		velocityComp = new VelocityComp();
		add(velocityComp);
	}
	
	/**
	 * Beispiele:
	 * 
	 * Das x makiert die entity
	 * Der Strich zeigt die richtung der bewegung an
	 * 
	 * directions = 4, degreeOffset = 45:
	 *   ╲ 0 ╱
	 *    ╲ ╱
	 *  1  x  3
	 *    ╱ ╲
	 *   ╱ 2 ╲
	 * 
	 * directions = 4, degreeOffset = 0:
	 *      │
	 *    1 │ 0
	 *   ━━━x━━━
	 *    2 │ 3
	 *      │
	 *      
	 * ╲
	 *  ╲
	 *   x
	 * 
	 * return = 1
	 * 
	 *   x
	 *    ╲
	 *     ╲
	 *  
	 * return = 3
	 *  
	 * @param directions
	 * @return
	 */
	protected int getVelocityDirectionArea(int directions, float degreeOffset) {
		return (int) Math.floor((velocityComp.vel.angle()+degreeOffset) / (360f/directions));
	}

	public TrasformationComp getPositionComp() {
		return transformComp;
	}

	public VelocityComp getVelocityComp() {
		return velocityComp;
	}

	public FixedAccelerationComp getAccelerationComp() {
		return accelerationComp;
	}

	public VISUAL getVisual() {
		return (VISUAL) visualComp.getVisual();
	}

	public BodyComp getBodyComp() {
		return bodyComp;
	}

	public UpdateEventComp getUpdateComp() {
		return updateComp;
	}

	public HealthComp getHealthComp() {
		return healthComp;
	}
	
	public void moveTowards(Vector2 pos) {
		Vector2 myPos = transformComp.pos;
		Vector2 nextVel = new Vector2(pos).sub(myPos);
		nextVel.nor();
		nextVel.scl(speed);
		velocityComp.vel.set(nextVel);
	}

	@Override
	public void dispose() {
		removeAll();
		bodyComp.dispose();
	}
}
