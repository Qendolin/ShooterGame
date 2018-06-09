package com.mygdx.game.entityComponents.visuals;

import java.util.LinkedHashMap;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class CompositeVis extends Visual {

	public LinkedHashMap<String, Visual> visuals = new LinkedHashMap<>();
	
	public CompositeVis(VisualNameGroup... visualGroups) {
		for(VisualNameGroup visualGroup : visualGroups) {
			visuals.put(visualGroup.name, visualGroup.visual);
		}
	}
	
	@Override
	public Sprite get(int index) {
		return visuals.values().toArray(new Visual[getNumberOfSprites()])[index].get();
	}

	@Override
	public float getWidth() {
		return 0;
	}

	@Override
	public float getHeight() {
		return 0;
	}

	@Override
	public void dispose() {
		for(Visual vis : visuals.values()) {
			vis.dispose();
		}
	}

	@Override
	public int getNumberOfSprites() {
		return visuals.size();
	}

}
