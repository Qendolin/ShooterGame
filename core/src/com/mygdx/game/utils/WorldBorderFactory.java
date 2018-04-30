package com.mygdx.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class WorldBorderFactory {

	public static void createBorder(World world, Vector2 center, float width, float height) {
		createEdge(world, center, new Vector2(center.x-width/2f, center.y-height/2f),
				  new Vector2(center.x+width/2f, center.y-height/2f));
		createEdge(world, center, new Vector2(center.x+width/2f, center.y-height/2f),
				  new Vector2(center.x+width/2f, center.y+height/2f));
		createEdge(world, center, new Vector2(center.x+width/2f, center.y+height/2f),
				  new Vector2(center.x-width/2f, center.y+height/2f));
		createEdge(world, center, new Vector2(center.x-width/2f, center.y+height/2f),
				  new Vector2(center.x-width/2f, center.y-height/2f));
	}
	
	private static Body createEdge(World world, Vector2 center, Vector2 v1, Vector2 v2) {
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(center.x, center.y);
		
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(v1.x, v1.y, v2.x, v2.y);
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = edgeShape;

        Body bodyEdge = world.createBody(bodyDef);
        bodyEdge.createFixture(fixtureDef);
        edgeShape.dispose();
        return bodyEdge;
	}
}
