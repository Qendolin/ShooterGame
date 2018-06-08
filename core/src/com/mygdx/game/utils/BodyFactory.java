package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyFactory {
	
	public static Body createCircle(World world, BodyType type, boolean bullet, short collisionLayer, short collisionLayerMask, Vector2 center, float radius, boolean sensor) {
		if(world == null || type == null || center == null)
			return null;
		radius *= Const.PIXEL_TO_METER_RATIO;
		center = new Vector2(center).scl(Const.PIXEL_TO_METER_RATIO);
		BodyDef bdef = createBodyDef(type, bullet, center);
		CircleShape shape = new CircleShape();
		shape.setRadius(radius);
		shape.setPosition(new Vector2()); //vec2(0) weil position im body gespeichert wird
		FixtureDef fdef = createFixtureDef(collisionLayer, collisionLayerMask, shape, sensor);
		fdef.density=1f;
		Body body = world.createBody(bdef);
		body.createFixture(fdef);
		shape.dispose();
		return body;
	}
	
	public static Body createRectangle(World world, BodyType type, boolean bullet, short collisionLayer, short collisionLayerMask, Vector2 center, float width, float height, boolean sensor) {
		if(world == null || type == null || center == null)
			return null;
		center = new Vector2(center).scl(Const.PIXEL_TO_METER_RATIO);
		width *= Const.PIXEL_TO_METER_RATIO;
		height *= Const.PIXEL_TO_METER_RATIO;
		BodyDef bdef = createBodyDef(type, bullet, center);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/2, height/2, new Vector2(), 0); //vec2(0) weil position im body gespeichert wird
		FixtureDef fdef = createFixtureDef(collisionLayer, collisionLayerMask, shape, sensor);
		fdef.density=1f;
		Body body = world.createBody(bdef);
		body.createFixture(fdef);
		shape.dispose();
		return body;
	}
	
	public static Body createPolygon(World world, BodyType type, boolean bullet, short collisionLayer, short collisionLayerMask, Vector2 center, Vector2[] verts, boolean sensor) {
		if(world == null || type == null || center == null)
			return null;
		center = new Vector2(center).scl(Const.PIXEL_TO_METER_RATIO);
		BodyDef bdef = createBodyDef(type, bullet, center);
		PolygonShape shape = new PolygonShape();
		if(verts.length < 3 || verts.length > 8) {
			Gdx.app.error(Const.LogTags.PHYSIC, "Polygon vertex count invald, count must be >= 3 and <= 8. Vertex count: "+verts.length+System.lineSeparator()+"Cutting excess vertices");
			Vector2[] tmp = new Vector2[8];
			for(int i = 0; i < 8; i++)
				tmp[i] = verts[i];
			verts = tmp;
		}
		for(Vector2 v : verts)
			v.scl(Const.PIXEL_TO_METER_RATIO);
		shape.set(verts);
		FixtureDef fdef = createFixtureDef(collisionLayer, collisionLayerMask, shape, sensor);
		fdef.density=1f;
		Body body = world.createBody(bdef);
		body.createFixture(fdef);
		shape.dispose();
		return body;
	}
	
	public static BodyDef createBodyDef(BodyType type, boolean bullet, Vector2 center) {
		BodyDef def = new BodyDef();
		def.type = type;
		def.bullet = bullet;
		def.fixedRotation=true;
		def.position.set(center);
		return def;
	}
	
	public static FixtureDef createFixtureDef(short collisionLayer, short collisionLayerMask, Shape shape, boolean sensor) {
		FixtureDef def = new FixtureDef();
		def.filter.categoryBits = collisionLayer;
		def.filter.maskBits = collisionLayerMask;
		def.shape = shape;
		def.isSensor = sensor;
		return def;
	}
	
	public static void createBorder(World world, Vector2 center, float width, float height) {
		center = new Vector2(center).scl(Const.PIXEL_TO_METER_RATIO);
		width *= Const.PIXEL_TO_METER_RATIO;
		height *= Const.PIXEL_TO_METER_RATIO;
		createEdge(world, center, Const.Collision.ALWAYS, Const.Collision.ALL, new Vector2(center.x-width/2f, center.y-height/2f),
				  new Vector2(center.x+width/2f, center.y-height/2f));
		createEdge(world, center, Const.Collision.ALWAYS, Const.Collision.ALL, new Vector2(center.x+width/2f, center.y-height/2f),
				  new Vector2(center.x+width/2f, center.y+height/2f));
		createEdge(world, center, Const.Collision.ALWAYS, Const.Collision.ALL, new Vector2(center.x+width/2f, center.y+height/2f),
				  new Vector2(center.x-width/2f, center.y+height/2f));
		createEdge(world, center, Const.Collision.ALWAYS, Const.Collision.ALL, new Vector2(center.x-width/2f, center.y+height/2f),
				  new Vector2(center.x-width/2f, center.y-height/2f));
	}
	
	public static Body createEdge(World world, Vector2 center, short collisionLayer, short collisionLayerMask, Vector2 v1, Vector2 v2) {
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(center.x, center.y);
		
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(v1.x, v1.y, v2.x, v2.y);
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = edgeShape;
        fixtureDef.filter.categoryBits = collisionLayer;
        fixtureDef.filter.maskBits = collisionLayerMask;

        Body bodyEdge = world.createBody(bodyDef);
        bodyEdge.createFixture(fixtureDef);
        edgeShape.dispose();
        return bodyEdge;
	}
}
