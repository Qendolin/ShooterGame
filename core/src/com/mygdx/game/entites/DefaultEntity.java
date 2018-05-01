package com.mygdx.game.entites;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entityComponents.ColliderComp;
import com.mygdx.game.entityComponents.FixedAccelerationComp;
import com.mygdx.game.entityComponents.HealthComp;
import com.mygdx.game.entityComponents.PositionComp;
import com.mygdx.game.entityComponents.UpdateEventComp;
import com.mygdx.game.entityComponents.VelocityComp;
import com.mygdx.game.entityComponents.VisualComp;

public class DefaultEntity<VISUAL extends VisualComp> extends Entity {

	protected World world;
	protected PositionComp positionComp;
	protected VelocityComp velocityComp;
	/**
	 * Optional
	 */
	protected FixedAccelerationComp accelerationComp;
	protected VISUAL visualComp;
	protected ColliderComp colliderComp;
	protected UpdateEventComp updateComp;
	/**
	 * optional
	 */
	protected HealthComp healthComp;
	
	/**
	 * Initialisiert die Visuelle, Positions und Geschwindigkeitskomponente.
	 * @param world
	 * @param position
	 * @param visual
	 */
	public DefaultEntity(Vector2 position, VISUAL visual) {
		visualComp=visual;
		add(visualComp);
		positionComp = new PositionComp(position);
		add(positionComp);
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
	 *  return = 3
	 *  
	 * @param directions
	 * @return
	 */
	protected int getVelocityDirectionArea(int directions, float degreeOffset) {
		return (int) Math.floor((velocityComp.vel.angle()+degreeOffset) / (360f/directions));
	}

	public PositionComp getPositionComp() {
		return positionComp;
	}

	public VelocityComp getVelocityComp() {
		return velocityComp;
	}

	public FixedAccelerationComp getAccelerationComp() {
		return accelerationComp;
	}

	public VISUAL getVisualComp() {
		return visualComp;
	}

	public ColliderComp getColliderComp() {
		return colliderComp;
	}

	public UpdateEventComp getUpdateComp() {
		return updateComp;
	}

	public HealthComp getHealthComp() {
		return healthComp;
	}
	
	
}
