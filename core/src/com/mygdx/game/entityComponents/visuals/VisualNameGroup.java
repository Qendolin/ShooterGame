package com.mygdx.game.entityComponents.visuals;

public class VisualNameGroup {

	public String name;
	public Visual visual;
	
	public VisualNameGroup(String name, Visual visual) {
		if(visual instanceof CompositeVis)
			throw new RuntimeException("Can't add a CompositeVis to a CompositeVis!");
		this.name = name;
		this.visual = visual;
	}
	
}
