package com.mygdx.game.entites;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.entityComponents.HealthComp;
import com.mygdx.game.entityComponents.TrasformationComp;
import com.mygdx.game.entityComponents.UpdateEventComp;
import com.mygdx.game.entityComponents.VisualComp;
import com.mygdx.game.entityComponents.events.UpdateListener;
import com.mygdx.game.entityComponents.visuals.CompositeVis;
import com.mygdx.game.entityComponents.visuals.SpriteVis;
import com.mygdx.game.entityComponents.visuals.Visual;
import com.mygdx.game.entityComponents.visuals.VisualNameGroup;
import com.mygdx.game.utils.Const.RenderLayer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class HealthBar extends Entity implements Disposable {
	
	public TrasformationComp trackingPosition;
	public TrasformationComp barPosition;
	public HealthComp trackingHealth;
	public Vector2 offset;
	private UpdateEventComp updateComp = new UpdateEventComp(new UpdateListener() {
		@Override
		public void onUpdate(World world, Engine engine, Camera cam) {
			barPosition.pos = new Vector2(trackingPosition.pos).add(offset);
		}
	});
	private VisualComp<CompositeVis> visualComp;
	private Visual bar;
		
	public HealthBar(TrasformationComp trackingPosition, Vector2 offset, HealthComp trackingHealth) {
		this.trackingPosition = trackingPosition;
		barPosition = new TrasformationComp(new Vector2(trackingPosition.pos).add(offset));
		add(barPosition);
		this.offset = offset;
		add(updateComp);
		visualComp = new VisualComp<CompositeVis>(new CompositeVis(new VisualNameGroup("hpBack", new SpriteVis(new Texture("HBBackground.png"))), 
				new VisualNameGroup("hpMid", new SpriteVis(new Texture("HBMidground.png"))),
				new VisualNameGroup("hpFront", new SpriteVis(new Texture("HBForeground.png")))));
		visualComp.visual.renderLayer = RenderLayer.WINDOW_UI;
		add(visualComp);
		trackingHealth.health.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				setBarPrecent(newValue.floatValue() / trackingHealth.startHealth);
			}
		});
		bar = visualComp.visual.visuals.get("hpMid");
		bar.get().setOrigin(bar.get().getOriginX()-bar.getWidth()/2, bar.get().getOriginY());
	}
	
	public void setBarPrecent(float precent) {
		bar.get().setScale(Math.min(1, Math.max(0, precent)), bar.get().getScaleY());
	}

	@Override
	public void dispose() {
		removeAll();
	}
}
