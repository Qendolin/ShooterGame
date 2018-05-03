package com.mygdx.game.entites;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entityComponents.PositionComp;
import com.mygdx.game.entityComponents.UpdateEventComp;
import com.mygdx.game.entityComponents.VisualComp;
import com.mygdx.game.entityComponents.events.UpdateListener;
import com.mygdx.game.entityComponents.visualComps.SpriteComp;

public class HealthBar extends Entity {
	
	public PositionComp trackingPosition;
	public PositionComp barPosition;
	public Vector2 offset;
	public UpdateEventComp updateComp = new UpdateEventComp(new UpdateListener() {
		@Override
		public void onUpdate(World world, Engine engine, Camera cam) {
			barPosition.pos = new Vector2(trackingPosition.pos).add(offset);
		}
	});
	public VisualComp visualComp;
	
	public HealthBar(PositionComp trackingPosition, Vector2 offset) {
		this.trackingPosition = trackingPosition;
		barPosition = new PositionComp(new Vector2(trackingPosition.pos).add(offset));
		add(barPosition);
		this.offset = offset;
		add(updateComp);
		visualComp = new SpriteComp(new Texture("hfg.png"));
		add(visualComp);
	}
}
