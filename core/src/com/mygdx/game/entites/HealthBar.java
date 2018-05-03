package com.mygdx.game.entites;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.entityComponents.HealthComp;
import com.mygdx.game.entityComponents.PositionComp;
import com.mygdx.game.entityComponents.UpdateEventComp;
import com.mygdx.game.entityComponents.VisualComp;
import com.mygdx.game.entityComponents.events.UpdateListener;
import com.mygdx.game.entityComponents.visuals.CompositeVis;
import com.mygdx.game.entityComponents.visuals.SpriteVis;
import com.mygdx.game.entityComponents.visuals.Visual;
import com.mygdx.game.entityComponents.visuals.VisualNameGroup;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class HealthBar extends Entity implements Disposable {
	
	public PositionComp trackingPosition;
	public PositionComp barPosition;
	public HealthComp trackingHealth;
	public Vector2 offset;
	private UpdateEventComp updateComp = new UpdateEventComp(new UpdateListener() {
		@Override
		public void onUpdate(World world, Engine engine, Camera cam) {
			barPosition.pos = new Vector2(trackingPosition.pos).add(offset);
		}
	});
	private VisualComp<CompositeVis> visualComp;
	private float orgWidth;
	private Visual bar;
		
	public HealthBar(PositionComp trackingPosition, Vector2 offset, HealthComp trackingHealth) {
		this.trackingPosition = trackingPosition;
		barPosition = new PositionComp(new Vector2(trackingPosition.pos).add(offset));
		add(barPosition);
		this.offset = offset;
		add(updateComp);
		visualComp = new VisualComp<CompositeVis>(new CompositeVis(new VisualNameGroup("hpBack", new SpriteVis(new Texture("HBBackground.png"))), 
				new VisualNameGroup("hpMid", new SpriteVis(new Texture("HBMidground.png"))),
				new VisualNameGroup("hpFront", new SpriteVis(new Texture("HBForeground.png")))));
		add(visualComp);
		trackingHealth.health.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				setBarPrecent(newValue.floatValue() / trackingHealth.startHealth);
			}
		});
		bar = visualComp.visual.visuals.get("hpMid");
		orgWidth = bar.getWidth();
	}
	
	public void setBarPrecent(float precent) {
		bar.get().setSize(orgWidth*precent, bar.getHeight());
	}

	@Override
	public void dispose() {
		removeAll();
	}
}
